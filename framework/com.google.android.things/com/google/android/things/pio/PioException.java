package com.google.android.things.pio;

import android.os.DeadObjectException;
import android.os.RemoteException;
import android.os.ServiceSpecificException;
import android.system.OsConstants;
import java.io.IOException;

class PioException extends IOException {
    private final int mErrno;

    public PioException(int errno, String message) {
        super(message);
        this.mErrno = errno;
    }

    PioException(ServiceSpecificException e) {
        super(e);
        this.mErrno = e.errorCode;
    }

    PioException(RemoteException e) {
        String str;
        if (e instanceof DeadObjectException) {
            str = "I/O controller has died";
        } else {
            str = "Communication error with I/O controller: " + e.getMessage();
        }
        super(str, e);
        this.mErrno = OsConstants.ENODEV;
    }

    public int getErrno() {
        return this.mErrno;
    }

    static ServiceSpecificException createServiceSpecificException(IOException e) {
        if (e instanceof PioException) {
            return new ServiceSpecificException(((PioException) e).getErrno(), e.getMessage());
        }
        return new ServiceSpecificException(OsConstants.EIO, e.getMessage());
    }

    static PioException createUnknownNameException(String name) {
        return new PioException(OsConstants.ENODEV, "Unknown I/O name " + name);
    }

    static PioException createUnregisteredIoException(String name) {
        return new PioException(OsConstants.ENODEV, name + " has been unregistered");
    }

    static PioException createNotOwnedException(String name) {
        return new PioException(OsConstants.EACCES, "Caller does not own " + name);
    }

    static PioException createInUseException(String name) {
        return new PioException(OsConstants.EBUSY, name + " is already in use");
    }
}
