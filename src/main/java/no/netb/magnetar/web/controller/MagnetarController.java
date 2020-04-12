package no.netb.magnetar.web.controller;

import com.sun.istack.internal.Nullable;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import no.netb.libjcommon.ExceptionUtil;
import no.netb.libjcommon.StreamUtil;
import no.netb.magnetar.repository.RepositoryMap;
import no.netb.magnetar.web.constants.HttpHeader;
import no.netb.magnetar.web.constants.HttpStatus;
import no.netb.magnetar.web.app.Response;
import no.netb.magnetar.web.app.Template;
import no.netb.magnetar.web.constants.MimeType;
import org.thymeleaf.ITemplateEngine;
import org.thymeleaf.context.Context;

import java.io.*;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class MagnetarController {

    private static final Logger LOG = Logger.getLogger(MagnetarController.class.getName());

    protected final ITemplateEngine templateEngine;

    public MagnetarController(ITemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    static class ControllerArgs {
        final HttpExchange request;
        final RepositoryMap repositories;

        public ControllerArgs(HttpExchange request, RepositoryMap repositories) {
            this.request = request;
            this.repositories = repositories;
        }
    }

    public Response applyTemplate(
            final HttpExchange request,
            final RepositoryMap repositories) {
        ControllerArgs args = new ControllerArgs(request, repositories);
        try {
            Context context = new Context();
            switch (request.getRequestMethod().toLowerCase()) {
                case "get":
                    return handleGetRequest(context, args);
                case "post":
                    return handlePostRequest(context, args);
                default:
                    return methodUnsupported(request);
            }
        } catch (Exception e) {
            return handleError(HttpStatus.HTTP_500, request, e);
        }
    }

    protected Response handleGetRequest(Context ctx, ControllerArgs args) throws Exception {
        return methodUnsupported(args.request);
    }
    protected Response handlePostRequest(Context ctx, ControllerArgs args) throws Exception {
        return methodUnsupported(args.request);
    }

    private Response methodUnsupported(HttpExchange request) {
        return handleError(HttpStatus.HTTP_405, request, null);
    }

    protected Response unsupportedMediaType(HttpExchange request) {
        return handleError(HttpStatus.HTTP_415, request, null);
    }

    public Response handleError(
            final HttpStatus httpStatus,
            final HttpExchange request,
            final @Nullable Exception e) {

        if (e != null) {
            String requestUrl = request.getRequestURI().toString();
            LOG.log(Level.WARNING, "exception occurred when processing request: " + requestUrl, e);
        }

        String stackTrace = e == null ? "" : ExceptionUtil.getStackTrace(e);
        Context ctx = new Context(null);
        ctx.setVariable("httpStatus", httpStatus);
        ctx.setVariable("stackTrace", stackTrace);
        return Response.withStatus(httpStatus, Template.FAULT, templateEngine, ctx);
    }

    protected boolean hasHeaderValue(Headers headers, HttpHeader header, String value) {
        List<String> contentTypes = headers.get(header.string);
        return contentTypes.stream().anyMatch(p -> p.equals(value));
    }

    protected Map<String, String> extractPostArgs(HttpExchange request) throws IOException {
        Headers headers = request.getRequestHeaders();
        Map<String, String> args = new HashMap<>();

        if (hasHeaderValue(headers, HttpHeader.CONTENT_TYPE, MimeType.APPLICATION__X_WWW_FORM_URLENCODED.string)) {
            StringWriter stringWriter = new StringWriter();
            InputStream is = request.getRequestBody();

            String requestBody = StreamUtil.writeToString(is);

            String decoded = URLDecoder.decode(requestBody, StandardCharsets.UTF_8.name());
        }

        return args;
    }
}
