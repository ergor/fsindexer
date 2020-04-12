package no.netb.magnetar.webui.controller;

import com.sun.net.httpserver.HttpExchange;
import no.netb.magnetar.webui.app.HttpStatus;
import no.netb.magnetar.webui.app.Response;
import org.thymeleaf.ITemplateEngine;
import org.thymeleaf.context.Context;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class FaultController implements MagnetarController {


    public Response doFault(HttpStatus httpStatus, ITemplateEngine templateEngine) {
        Context ctx = new Context(null);
        ctx.setVariable("httpStatus", httpStatus);
        return new Response(httpStatus, templateEngine.process("fault", ctx));
    }

    @Override
    public Response applyTemplate(HttpExchange request, ITemplateEngine templateEngine) {
        throw new NotImplementedException();
    }
}
