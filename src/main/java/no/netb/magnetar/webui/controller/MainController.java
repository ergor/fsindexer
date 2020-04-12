package no.netb.magnetar.webui.controller;

import com.sun.net.httpserver.HttpExchange;
import no.netb.magnetar.webui.app.HttpStatus;
import no.netb.magnetar.webui.app.Response;
import org.thymeleaf.ITemplateEngine;
import org.thymeleaf.context.Context;


public class MainController implements MagnetarController {

    @Override
    public Response applyTemplate(HttpExchange request, ITemplateEngine templateEngine) {
        Context ctx = new Context(null);
        return new Response(HttpStatus.HTTP_200, templateEngine.process("main", ctx));
    }
}
