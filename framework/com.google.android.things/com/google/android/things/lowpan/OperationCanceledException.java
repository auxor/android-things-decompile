package com.google.android.things.lowpan;

public class OperationCanceledException extends LowpanException {
    public OperationCanceledException(String message) {
        super(message);
    }

    public OperationCanceledException(String message, Throwable cause) {
        super(message, cause);
    }

    public OperationCanceledException(Exception cause) {
        super(cause);
    }
}
