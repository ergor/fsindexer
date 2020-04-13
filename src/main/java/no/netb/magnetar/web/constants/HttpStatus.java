package no.netb.magnetar.web.constants;

public enum HttpStatus {

    HTTP_200(200, "OK"),
    HTTP_303(303, "See Other"), // temporary redirect, forcing use of GET for the new request
    HTPP_400(400, "Bad Request"),
    HTTP_404(404, "Not Found"),
    HTTP_405(405, "Method Not Allowed"),
    HTTP_415(415, "Unsupported Media Type"),
    HTTP_500(500, "Internal Server Error");

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
