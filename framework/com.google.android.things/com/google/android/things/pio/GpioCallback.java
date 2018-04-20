package com.google.android.things.pio;

public abstract class GpioCallback {
    public boolean onGpioEdge(Gpio gpio) {
        return true;
    }

    public void onGpioError(Gpio gpio, int error) {
    }
}
