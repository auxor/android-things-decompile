package com.google.android.things.pio;

import android.os.Handler;
import java.io.IOException;

public abstract class GpioDriver extends Gpio {
    public abstract void close() throws IOException;

    public abstract void open() throws IOException;

    public abstract void registerGpioCallback(GpioCallback gpioCallback, Handler handler) throws IOException;

    public abstract void unregisterGpioCallback(GpioCallback gpioCallback);

    public String getName() {
        return null;
    }
}
