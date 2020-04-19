package no.netb.magnetar.constants;

public enum MimeType {
    TEXT__HTML("text/html"),
    TEXT__CSS("text/css"),
    APPLICATION__OCTET_STREAM("application/octet-stream"),
    APPLICATION__X_WWW_FORM_URLENCODED("application/x-www-form-urlencoded");

    public String string;

    MimeType(String string) {
        this.string = string;
    }
}
