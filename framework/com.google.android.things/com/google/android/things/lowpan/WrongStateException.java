package com.google.android.things.lowpan;

public class WrongStateException extends LowpanException {
    public WrongStateException(String message) {
        super(message);
    }

    public WrongStateException(String message, Throwable cause) {
        super(message, cause);
    }

    public WrongStateException(Exception cause) {
        super(cause);
    }
}
