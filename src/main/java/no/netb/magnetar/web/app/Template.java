package no.netb.magnetar.web.app;

public enum Template {
    MAIN("main"),
    FAULT("fault"),
    NEW_HOST("newHost");

    public String templateName;

    Template(String templateName) {
        this.templateName = templateName;
    }
}
