package com.google.android.things.lowpan;

public class JoinFailedException extends LowpanException {
    public JoinFailedException(String message) {
        super(message);
    }

    public JoinFailedException(String message, Throwable cause) {
        super(message, cause);
    }

    public JoinFailedException(Exception cause) {
        super(cause);
    }
}
