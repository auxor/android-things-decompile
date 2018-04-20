package com.google.android.things.pio;

import java.io.IOException;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public abstract class SpiDevice extends IoBase {
    public static final int MODE0 = 0;
    public static final int MODE1 = 1;
    public static final int MODE2 = 2;
    public static final int MODE3 = 3;

    @Retention(RetentionPolicy.SOURCE)
    public @interface Mode {
    }

    public abstract void close() throws IOException;

    public /* bridge */ /* synthetic */ String getName() {
        return super.getName();
    }

    public abstract void setBitJustification(boolean z) throws IOException;

    public abstract void setBitsPerWord(int i) throws IOException;

    public abstract void setCsChange(boolean z) throws IOException;

    public abstract void setDelay(int i) throws IOException;

    public abstract void setFrequency(int i) throws IOException;

    public abstract void setMode(int i) throws IOException;

    public /* bridge */ /* synthetic */ String toString() {
        return super.toString();
    }

    public abstract void transfer(byte[] bArr, byte[] bArr2, int i) throws IOException;

    public void write(byte[] buffer, int length) throws IOException {
        transfer(buffer, null, length);
    }

    public void read(byte[] buffer, int length) throws IOException {
        transfer(null, buffer, length);
    }
}
