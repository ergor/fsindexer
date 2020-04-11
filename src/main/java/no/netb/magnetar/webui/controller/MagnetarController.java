package no.netb.magnetar.webui.controller;

import org.thymeleaf.ITemplateEngine;

public interface MagnetarController {

    String applyTemplate(final String request, final ITemplateEngine templateEngine);
}
