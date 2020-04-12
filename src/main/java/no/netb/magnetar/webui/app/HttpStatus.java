package no.netb.magnetar.webui.app;

public enum HttpStatus {

    HTTP_200(200, "OK"),
    HTTP_404(404, "Not Found");

    private final int code;
    private final String msg;

    HttpStatus(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
