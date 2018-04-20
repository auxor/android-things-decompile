package com.google.android.things.pio;

import java.io.Closeable;
import java.io.IOException;

public abstract class I2cBusDriver implements Closeable {
    public abstract I2cDevice createI2cDevice(int i) throws IOException;

    public void open() throws IOException {
    }

    public void close() throws IOException {
    }
}
