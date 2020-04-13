package no.netb.magnetar.web.app;

public enum Template {
    MAIN("main", "/"),
    FAULT("fault", null),
    NEW_HOST("newHost", "/newHost");

    public String templateName;
    public String requestPath;

    Template(String templateName, String requestPath) {
        this.templateName = templateName;
        this.requestPath = requestPath;
    }
}
