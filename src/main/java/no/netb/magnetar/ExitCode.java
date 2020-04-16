package no.netb.magnetar;

public enum  ExitCode {
    INTERNAL_ERROR(1),
    ARGS_INVALID(2)
    ;

    public int value;

    ExitCode(int value) {
        this.value = value;
    }
}
