package no.netb.magnetar.web.constants;

public enum MimeType {
    TEXT__HTML("text/html"),
    APPLICATION__X_WWW_FORM_URLENCODED("application/x-www-form-urlencoded");

    public String string;

    MimeType(String string) {
        this.string = string;
    }
}
