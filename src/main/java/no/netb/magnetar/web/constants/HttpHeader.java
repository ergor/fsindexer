package no.netb.magnetar.web.constants;

public enum HttpHeader {
    LOCATION("Location"),
    CONTENT_TYPE("Content-Type");

    public String string;

    HttpHeader(String string) {
        this.string = string;
    }
}
