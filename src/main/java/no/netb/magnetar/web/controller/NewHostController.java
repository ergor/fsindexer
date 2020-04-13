package no.netb.magnetar.web.controller;

import com.sun.net.httpserver.HttpExchange;
import no.netb.magnetar.models.Host;
import no.netb.magnetar.repository.HostRepository;
import no.netb.magnetar.web.app.Response;
import no.netb.magnetar.web.app.Template;
import no.netb.magnetar.web.constants.HttpHeader;
import no.netb.magnetar.web.constants.HttpStatus;
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

        String name = rArgs.get("name");
        String sshName = rArgs.get("sshName");

        if (name == null || sshName == null) {
            return handleError(HttpStatus.HTPP_400, cArgs.request, null);
        }

        HostRepository hostRepository = cArgs.repository.get(HostRepository.class);

        Host host = new Host();
        host.setName(name);
        host.setSshConfigName(sshName);

        host.saveOrFail(cArgs.repository.getDatabase());

        return Response.redirect(Template.MAIN);
    }
}
