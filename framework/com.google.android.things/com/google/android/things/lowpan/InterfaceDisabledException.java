package com.google.android.things.lowpan;

public class InterfaceDisabledException extends LowpanException {
    public InterfaceDisabledException(String message) {
        super(message);
    }

    public InterfaceDisabledException(String message, Throwable cause) {
        super(message, cause);
    }

    public InterfaceDisabledException(Exception cause) {
        super(cause);
    }
}
