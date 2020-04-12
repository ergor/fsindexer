package no.netb.magnetar.webui.app;

public class Response {

    private final HttpStatus httpStatus;
    private final String html;

    public Response(HttpStatus httpStatus, String html) {
        this.httpStatus = httpStatus;
        this.html = html;
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
}
