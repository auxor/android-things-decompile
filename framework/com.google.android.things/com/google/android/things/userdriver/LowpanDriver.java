package com.google.android.things.userdriver;

import android.os.RemoteException;
import com.google.android.things.userdriver.ILowpanDriver.Stub;

public abstract class LowpanDriver {
    public static final int ERROR_ALREADY = 2;
    public static final int ERROR_FAILED = 1;
    public static final int ERROR_GARBAGE = 5;
    public static final int ERROR_IOFAIL = 4;
    public static final int ERROR_TOOBIG = 3;
    private ILowpanDriverCallback mCallback;
    private final ILowpanDriver mInterface = new Stub() {
        public void start(final ILowpanDriverCallback callback) {
            LowpanDriver.this.start(new LowpanDriverCallback() {
                final ILowpanDriverCallback mCallback = callback;

                public void onStarted() {
                    try {
                        this.mCallback.onStarted();
                    } catch (RemoteException x) {
                        throw x.rethrowAsRuntimeException();
                    }
                }

                public void onReceiveFrame(byte[] frame) {
                    try {
                        this.mCallback.onReceiveFrame(frame);
                    } catch (RemoteException x) {
                        throw x.rethrowAsRuntimeException();
                    }
                }

                public void onReset() {
                    try {
                        this.mCallback.onReset();
                    } catch (RemoteException x) {
                        throw x.rethrowAsRuntimeException();
                    }
                }

                public void onError(int error) {
                    try {
                        this.mCallback.onError(error);
                    } catch (RemoteException x) {
                        throw x.rethrowAsRuntimeException();
                    }
                }
            });
        }

        public void stop() {
            LowpanDriver.this.stop();
        }

        public void reset() {
            LowpanDriver.this.reset();
        }

        public void sendFrame(byte[] frame) {
            LowpanDriver.this.sendFrame(frame);
        }
    };

    public abstract void reset();

    public abstract void sendFrame(byte[] bArr);

    public abstract void start(LowpanDriverCallback lowpanDriverCallback);

    public abstract void stop();

    final ILowpanDriver asInterface() {
        return this.mInterface;
    }
}
