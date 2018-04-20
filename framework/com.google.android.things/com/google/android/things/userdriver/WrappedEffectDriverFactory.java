package com.google.android.things.userdriver;

import android.os.Handler;
import android.os.RemoteException;
import android.util.Log;
import com.google.android.things.userdriver.IEffectDriverFactory.Stub;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

class WrappedEffectDriverFactory extends Stub {
    private static final String TAG = "WrappedEffectDriverFactory";
    private IEffectDriverFactoryCallback mCallback;
    private final Map<IEffectDriver, UserEffectDriver> mDrivers;
    private final UserEffectDriverFactory mFactory;

    final class DriverCloser implements Runnable {
        private final UserEffectDriver mDriver;

        public DriverCloser(UserEffectDriver driver) {
            this.mDriver = driver;
        }

        public void run() {
            this.mDriver.close();
        }
    }

    public WrappedEffectDriverFactory(UserEffectDriverFactory factory) {
        if (factory == null) {
            throw new IllegalArgumentException("Cannot wrap null factory.");
        }
        this.mFactory = factory;
        this.mDrivers = new ConcurrentHashMap();
    }

    void setCallback(IEffectDriverFactoryCallback callback) {
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

    public void setCallback(IEffectDriverFactoryCallback callback, int callbackId) {
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

    public void getEffect(int callbackId) {
        if (this.mCallback == null) {
            throw new IllegalStateException("No registered callback object.");
        }
        UserEffectDriver effectDriver = this.mFactory.createUserEffectDriver();
        IEffectDriver effectInterface = null;
        int status = 0;
        if (effectDriver == null) {
            Log.e(TAG, "User factory returned null effect driver.");
            status = -19;
        } else {
            effectInterface = new WrappedEffectDriver(effectDriver);
            this.mDrivers.put(effectInterface, effectDriver);
        }
        try {
            synchronized (this.mCallback) {
                this.mCallback.onGetEffect(callbackId, status, effectInterface);
            }
        } catch (RemoteException e) {
            e.rethrowAsRuntimeException();
        }
    }

    public void freeEffect(IEffectDriver driver) {
        if (driver == null) {
            throw new IllegalArgumentException("Cannot remove null driver.");
        }
        UserEffectDriver removed = (UserEffectDriver) this.mDrivers.remove(driver);
        if (removed == null) {
            Log.e(TAG, "Attempted to remove driver not associated with this factory.");
            return;
        }
        Handler handler = removed.getHandler();
        Runnable runner = new DriverCloser(removed);
        if (handler != null) {
            handler.post(runner);
        } else {
            runner.run();
        }
    }
}
