package com.google.android.things.lowpan;

public class JoinFailedAtAuthException extends JoinFailedException {
    public JoinFailedAtAuthException(String message) {
        super(message);
    }

    public JoinFailedAtAuthException(String message, Throwable cause) {
        super(message, cause);
    }

    public JoinFailedAtAuthException(Exception cause) {
        super(cause);
    }
}
