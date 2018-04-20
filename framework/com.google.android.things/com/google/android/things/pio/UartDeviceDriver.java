package com.google.android.things.pio;

import android.os.Handler;
import java.io.IOException;

public abstract class UartDeviceDriver extends UartDevice {
    public abstract void close() throws IOException;

    public abstract void open() throws IOException;

    public abstract void registerUartDeviceCallback(UartDeviceCallback uartDeviceCallback, Handler handler) throws IOException;

    public String getName() {
        return null;
    }
}
