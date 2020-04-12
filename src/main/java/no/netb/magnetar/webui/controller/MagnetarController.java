package no.netb.magnetar.webui.controller;

import com.sun.net.httpserver.HttpExchange;
import no.netb.magnetar.webui.app.Response;
import org.thymeleaf.ITemplateEngine;

public interface MagnetarController {

    Response applyTemplate(final HttpExchange request, final ITemplateEngine templateEngine);
}
