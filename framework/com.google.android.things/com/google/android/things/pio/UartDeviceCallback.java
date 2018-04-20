package com.google.android.things.pio;

public abstract class UartDeviceCallback {
    public boolean onUartDeviceDataAvailable(UartDevice uart) {
        return true;
    }

    public void onUartDeviceError(UartDevice uart, int error) {
    }
}
