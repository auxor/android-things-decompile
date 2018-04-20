package com.google.android.things.pio;

import java.io.Closeable;
import java.io.IOException;

public abstract class SpiBusDriver implements Closeable {
    public abstract SpiDevice createSpiDevice(String str) throws IOException;

    public abstract String[] getChipSelectNames();

    public void open() throws IOException {
    }

    public void close() throws IOException {
    }
}
