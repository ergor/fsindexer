package no.netb.magnetar.web.app;

import no.netb.magnetar.web.constants.HttpStatus;
import org.thymeleaf.ITemplateEngine;
import org.thymeleaf.context.Context;

public class Response {

    private final HttpStatus httpStatus;
    private final String html;
    private final String location;

    private Response(HttpStatus httpStatus, String html, String location) {
        this.httpStatus = httpStatus;
        this.html = html;
        this.location = location;
    }

    public static Response ok(Template template, ITemplateEngine templateEngine, Context ctx) {
        return new Response(HttpStatus.HTTP_200, templateEngine.process(template.templateName, ctx), "");
    }

    public static Response redirect(String location) {
        return new Response(HttpStatus.HTTP_303, "", location);
    }

    public static Response withStatus(HttpStatus httpStatus, Template template, ITemplateEngine templateEngine, Context ctx) {
        return new Response(httpStatus, templateEngine.process(template.templateName, ctx), "");
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public String getHtml() {
        return html;
    }

    public int getResponseLength() {
        return html.length();
    }

    public String getLocation() {
        return this.location;
    }

    public boolean isRedirect() {
        return (httpStatus.getCode() / 100) == 3;
    }
}
