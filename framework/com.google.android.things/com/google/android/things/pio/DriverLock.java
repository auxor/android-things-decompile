package com.google.android.things.pio;

abstract class DriverLock<T> implements AutoCloseable {
    private UserDriverCallback mCallback;

    public abstract void close();

    public abstract T getDriver();

    DriverLock() {
    }

    public UserDriverCallback getCallback() {
        return this.mCallback;
    }

    public void setCallback(UserDriverCallback callback) {
        this.mCallback = callback;
    }
}
