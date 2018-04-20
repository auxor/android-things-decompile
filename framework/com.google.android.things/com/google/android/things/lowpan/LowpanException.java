package com.google.android.things.lowpan;

import android.net.lowpan.InterfaceDisabledException;
import android.net.lowpan.JoinFailedAtAuthException;
import android.net.lowpan.JoinFailedAtScanException;
import android.net.lowpan.JoinFailedException;
import android.net.lowpan.NetworkAlreadyExistsException;
import android.net.lowpan.OperationCanceledException;
import android.net.lowpan.WrongStateException;

public class LowpanException extends Exception {
    public LowpanException(String message) {
        super(message);
    }

    public LowpanException(String message, Throwable cause) {
        super(message, cause);
    }

    public LowpanException(Exception cause) {
        super(cause);
    }

    public StackTraceElement[] getStackTrace() {
        Throwable cause = getCause();
        if (cause instanceof android.net.lowpan.LowpanException) {
            return cause.getStackTrace();
        }
        return super.getStackTrace();
    }

    static LowpanException createFromActual(android.net.lowpan.LowpanException x) {
        if (x instanceof JoinFailedAtScanException) {
            return new JoinFailedAtScanException((Exception) x);
        }
        if (x instanceof JoinFailedAtAuthException) {
            return new JoinFailedAtAuthException((Exception) x);
        }
        if (x instanceof JoinFailedException) {
            return new JoinFailedException((Exception) x);
        }
        if (x instanceof InterfaceDisabledException) {
            return new InterfaceDisabledException((Exception) x);
        }
        if (x instanceof NetworkAlreadyExistsException) {
            return new NetworkAlreadyExistsException((Exception) x);
        }
        if (x instanceof OperationCanceledException) {
            return new OperationCanceledException((Exception) x);
        }
        if (x instanceof WrongStateException) {
            return new WrongStateException((Exception) x);
        }
        return new LowpanException((Exception) x);
    }
}
