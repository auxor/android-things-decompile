package com.google.android.things.pio;

import java.io.IOException;

public abstract class I2cDevice extends IoBase {
    public abstract void close() throws IOException;

    public /* bridge */ /* synthetic */ String getName() {
        return super.getName();
    }

    public abstract void read(byte[] bArr, int i) throws IOException;

    public abstract void readRegBuffer(int i, byte[] bArr, int i2) throws IOException;

    public abstract byte readRegByte(int i) throws IOException;

    public abstract short readRegWord(int i) throws IOException;

    public /* bridge */ /* synthetic */ String toString() {
        return super.toString();
    }

    public abstract void write(byte[] bArr, int i) throws IOException;

    public abstract void writeRegBuffer(int i, byte[] bArr, int i2) throws IOException;

    public abstract void writeRegByte(int i, byte b) throws IOException;

    public abstract void writeRegWord(int i, short s) throws IOException;
}
