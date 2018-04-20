package com.google.android.things.userdriver;

import android.os.Handler;
import android.os.RemoteException;
import android.util.Log;
import com.google.android.things.audio.AudioConfiguration;
import com.google.android.things.userdriver.IEffectDriver.Stub;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

class WrappedEffectDriver extends Stub {
    private static final String TAG = "WrappedEffectDriver";
    private IEffectDriverCallback mCallback;
    private final UserEffectDriver mDriver;
    private boolean mEnabled = false;
    private final Object mEnabledLock = new Object();
    private final Handler mHandler;

    final class ConfigSetter implements Runnable {
        private final int mCallbackId;
        private final AudioConfiguration mConfig;

        public ConfigSetter(AudioConfiguration config, int callbackId) {
            this.mConfig = config;
            this.mCallbackId = callbackId;
        }

        public void run() {
            int status = 0;
            try {
                WrappedEffectDriver.this.mDriver.setConfiguration(this.mConfig.asAudioFormat());
                try {
                    synchronized (WrappedEffectDriver.this.mCallback) {
                        WrappedEffectDriver.this.mCallback.onSetConfig(this.mCallbackId, status);
                    }
                } catch (RemoteException e) {
                    e.rethrowAsRuntimeException();
                }
            } catch (IllegalArgumentException e2) {
                status = -22;
            }
        }
    }

    final class DataProcessor implements Runnable {
        private final int mCallbackId;
        private final byte[] mData;

        public DataProcessor(byte[] data, int callbackId) {
            this.mData = data;
            this.mCallbackId = callbackId;
        }

        public void run() {
            int status = 0;
            if (this.mData == null) {
                Log.w(WrappedEffectDriver.TAG, "System called process on null buffer.");
                status = -22;
            } else {
                try {
                    synchronized (WrappedEffectDriver.this.mEnabledLock) {
                        ByteBuffer buffer = ByteBuffer.wrap(this.mData);
                        buffer.order(ByteOrder.nativeOrder());
                        if (WrappedEffectDriver.this.mEnabled) {
                            WrappedEffectDriver.this.mDriver.process(buffer);
                        } else if (!WrappedEffectDriver.this.mDriver.processTail(buffer)) {
                            status = -61;
                        }
                    }
                } catch (IllegalStateException e) {
                    status = -38;
                }
            }
            try {
                synchronized (WrappedEffectDriver.this.mCallback) {
                    WrappedEffectDriver.this.mCallback.onProcess(this.mCallbackId, status, this.mData);
                }
            } catch (RemoteException e2) {
                e2.rethrowAsRuntimeException();
            }
        }
    }

    final class Enabler implements Runnable {
        private final int mCallbackId;
        private final boolean mEnable;

        public Enabler(boolean enable, int callbackId) {
            this.mEnable = enable;
            this.mCallbackId = callbackId;
        }

        public void run() {
            int status = 0;
            try {
                synchronized (WrappedEffectDriver.this.mEnabledLock) {
                    WrappedEffectDriver.this.mDriver.setEnable(this.mEnable);
                    WrappedEffectDriver.this.mEnabled = this.mEnable;
                }
            } catch (IllegalStateException e) {
                status = -38;
            }
            try {
                synchronized (WrappedEffectDriver.this.mCallback) {
                    WrappedEffectDriver.this.mCallback.onSetEnable(this.mCallbackId, status);
                }
            } catch (RemoteException e2) {
                e2.rethrowAsRuntimeException();
            }
        }
    }

    final class Resetter implements Runnable {
        private final int mCallbackId;

        public Resetter(int callbackId) {
            this.mCallbackId = callbackId;
        }

        public void run() {
            WrappedEffectDriver.this.mDriver.reset();
            try {
                synchronized (WrappedEffectDriver.this.mCallback) {
                    WrappedEffectDriver.this.mCallback.onReset(this.mCallbackId, 0);
                }
            } catch (RemoteException e) {
                e.rethrowAsRuntimeException();
            }
        }
    }

    public WrappedEffectDriver(UserEffectDriver driver) {
        if (driver == null) {
            throw new IllegalArgumentException("Cannot wrap null driver.");
        }
        this.mDriver = driver;
        this.mHandler = driver.getHandler();
    }

    void setCallback(IEffectDriverCallback callback) {
        synchronized (this) {
            if (this.mCallback != null) {
                synchronized (this.mCallback) {
                    this.mCallback = callback;
                }
            } else {
                this.mCallback = callback;
            }
        }
    }

    private void postOrRun(Runnable runner) {
        if (this.mHandler != null) {
            this.mHandler.post(runner);
        } else {
            runner.run();
        }
    }

    private void postOrRunCallbackRequired(Runnable runner) {
        if (this.mCallback == null) {
            throw new IllegalStateException("No registered callback object.");
        }
        postOrRun(runner);
    }

    public void setCallback(IEffectDriverCallback callback, int callbackId) {
        if (callback == null) {
            throw new IllegalArgumentException("Cannot set null callback.");
        }
        setCallback(callback);
        try {
            synchronized (this.mCallback) {
                this.mCallback.onSetCallback(callbackId, 0);
            }
        } catch (RemoteException e) {
            e.rethrowAsRuntimeException();
        }
    }

    public void process(byte[] data, int callbackId) {
        postOrRunCallbackRequired(new DataProcessor(data, callbackId));
    }

    public void reset(int callbackId) {
        postOrRunCallbackRequired(new Resetter(callbackId));
    }

    public void setEnable(boolean enable, int callbackId) {
        postOrRunCallbackRequired(new Enabler(enable, callbackId));
    }

    public void setConfig(AudioConfiguration config, int callbackId) {
        postOrRunCallbackRequired(new ConfigSetter(config, callbackId));
    }

    public void getDefaultConfig(int callbackId) {
        if (this.mCallback == null) {
            throw new IllegalStateException("No registered callback object.");
        }
        try {
            synchronized (this.mCallback) {
                this.mCallback.onGetDefaultConfig(callbackId, 0, this.mDriver.getDefaultConfiguration());
            }
        } catch (RemoteException e) {
            e.rethrowAsRuntimeException();
        }
    }
}
