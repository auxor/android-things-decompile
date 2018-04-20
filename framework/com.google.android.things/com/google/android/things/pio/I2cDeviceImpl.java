package com.google.android.things.pio;

import android.os.Binder;
import android.os.RemoteException;
import android.os.ServiceSpecificException;
import java.io.IOException;

class I2cDeviceImpl extends I2cDevice {
    static final String TAG = "I2C";
    private final int mAddress;
    private final String mName;
    private final IPeripheralManagerClient mPioClient;

    I2cDeviceImpl(IPeripheralManagerClient service, Binder lifeline, String name, int address) throws IOException {
        this.mName = name;
        this.mAddress = address;
        try {
            this.mPioClient = service.GetI2cClient(name, lifeline);
            this.mPioClient.OpenI2cDevice(name, address);
        } catch (ServiceSpecificException e) {
            throw new PioException(e);
        } catch (RemoteException e2) {
            throw new PioException(e2);
        }
    }

    public String getName() {
        return String.format("%s[0x%02X]", new Object[]{this.mName, Integer.valueOf(this.mAddress)});
    }

    public void close() throws IOException {
        try {
            this.mPioClient.ReleaseI2cDevice(this.mName, this.mAddress);
        } catch (ServiceSpecificException e) {
            throw new PioException(e);
        } catch (RemoteException e2) {
            throw new PioException(e2);
        }
    }

    public void read(byte[] buffer, int length) throws IOException {
        try {
            this.mPioClient.I2cRead(this.mName, this.mAddress, buffer, length);
        } catch (ServiceSpecificException e) {
            throw new PioException(e);
        } catch (RemoteException e2) {
            throw new PioException(e2);
        }
    }

    public byte readRegByte(int reg) throws IOException {
        try {
            return this.mPioClient.I2cReadRegByte(this.mName, this.mAddress, reg);
        } catch (ServiceSpecificException e) {
            throw new PioException(e);
        } catch (RemoteException e2) {
            throw new PioException(e2);
        }
    }

    public short readRegWord(int reg) throws IOException {
        try {
            return (short) this.mPioClient.I2cReadRegWord(this.mName, this.mAddress, reg);
        } catch (ServiceSpecificException e) {
            throw new PioException(e);
        } catch (RemoteException e2) {
            throw new PioException(e2);
        }
    }

    public void readRegBuffer(int reg, byte[] buffer, int length) throws IOException {
        try {
            this.mPioClient.I2cReadRegBuffer(this.mName, this.mAddress, reg, buffer, length);
        } catch (ServiceSpecificException e) {
            throw new PioException(e);
        } catch (RemoteException e2) {
            throw new PioException(e2);
        }
    }

    public void write(byte[] buffer, int length) throws IOException {
        try {
            this.mPioClient.I2cWrite(this.mName, this.mAddress, buffer, length);
        } catch (ServiceSpecificException e) {
            throw new PioException(e);
        } catch (RemoteException e2) {
            throw new PioException(e2);
        }
    }

    public void writeRegByte(int reg, byte data) throws IOException {
        try {
            this.mPioClient.I2cWriteRegByte(this.mName, this.mAddress, reg, data);
        } catch (ServiceSpecificException e) {
            throw new PioException(e);
        } catch (RemoteException e2) {
            throw new PioException(e2);
        }
    }

    public void writeRegWord(int reg, short data) throws IOException {
        try {
            this.mPioClient.I2cWriteRegWord(this.mName, this.mAddress, reg, data);
        } catch (ServiceSpecificException e) {
            throw new PioException(e);
        } catch (RemoteException e2) {
            throw new PioException(e2);
        }
    }

    public void writeRegBuffer(int reg, byte[] buffer, int length) throws IOException {
        try {
            this.mPioClient.I2cWriteRegBuffer(this.mName, this.mAddress, reg, buffer, length);
        } catch (ServiceSpecificException e) {
            throw new PioException(e);
        } catch (RemoteException e2) {
            throw new PioException(e2);
        }
    }
}
