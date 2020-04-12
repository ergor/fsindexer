package no.netb.magnetar.web.controller;

import com.sun.net.httpserver.HttpExchange;
import no.netb.magnetar.web.app.Response;
import no.netb.magnetar.web.app.Template;
import no.netb.magnetar.web.constants.HttpHeader;
import no.netb.magnetar.web.constants.MimeType;
import org.thymeleaf.ITemplateEngine;
import org.thymeleaf.context.Context;

import java.util.Map;

public class NewHostController extends MagnetarController {

    public NewHostController(ITemplateEngine templateEngine) {
        super(templateEngine);
    }

    @Override
    protected Response handleGetRequest(Context ctx, ControllerArgs cArgs) throws Exception {
        return Response.ok(Template.NEW_HOST, templateEngine, ctx);
    }

    @Override
    protected Response handlePostRequest(Context ctx, ControllerArgs cArgs) throws Exception {
        HttpExchange request = cArgs.request;

        if (!hasHeaderValue(request.getRequestHeaders(), HttpHeader.CONTENT_TYPE, MimeType.APPLICATION__X_WWW_FORM_URLENCODED.string)) {
            return unsupportedMediaType(request);
        }

        Map<String, String> rArgs = extractPostArgs(request);

        return Response.redirect("/");
    }
}
