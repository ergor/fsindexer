package no.netb.magnetar.webui.controller;

import org.thymeleaf.ITemplateEngine;
import org.thymeleaf.context.Context;


public class MainController implements MagnetarController {

    @Override
    public String applyTemplate(String request, ITemplateEngine templateEngine) {
        Context ctx = new Context(null);
        return templateEngine.process("main", ctx);
    }
}
