package com.google.android.things.lowpan;

public class LowpanRuntimeException extends RuntimeException {
    public LowpanRuntimeException(String message) {
        super(message);
    }

    public LowpanRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public LowpanRuntimeException(Exception cause) {
        super(cause);
    }

    public StackTraceElement[] getStackTrace() {
        Throwable cause = getCause();
        if (cause instanceof android.net.lowpan.LowpanRuntimeException) {
            return cause.getStackTrace();
        }
        return super.getStackTrace();
    }

    static LowpanRuntimeException createFromActual(android.net.lowpan.LowpanRuntimeException x) {
        return new LowpanRuntimeException((Exception) x);
    }
}
