package com.google.android.things.pio;

import android.os.Binder;
import android.os.RemoteException;
import android.os.ServiceSpecificException;
import java.io.IOException;

class SpiDeviceImpl extends SpiDevice {
    static final String TAG = "Spi";
    private final String mName;
    private final IPeripheralManagerClient mPioClient;

    SpiDeviceImpl(IPeripheralManagerClient service, Binder lifeline, String name) throws IOException {
        this.mName = name;
        try {
            this.mPioClient = service.GetSpiClient(name, lifeline);
            this.mPioClient.OpenSpiDevice(name);
        } catch (ServiceSpecificException e) {
            throw new PioException(e);
        } catch (RemoteException e2) {
            throw new PioException(e2);
        }
    }

    public String getName() {
        return this.mName;
    }

    public void setFrequency(int frequencyHz) throws IOException {
        try {
            this.mPioClient.SpiDeviceSetFrequency(this.mName, frequencyHz);
        } catch (ServiceSpecificException e) {
            throw new PioException(e);
        } catch (RemoteException e2) {
            throw new PioException(e2);
        }
    }

    public void setBitJustification(boolean lsbFirst) throws IOException {
        try {
            this.mPioClient.SpiDeviceSetBitJustification(this.mName, lsbFirst);
        } catch (ServiceSpecificException e) {
            throw new PioException(e);
        } catch (RemoteException e2) {
            throw new PioException(e2);
        }
    }

    public void setDelay(int delayUs) throws IOException {
        try {
            this.mPioClient.SpiDeviceSetDelay(this.mName, delayUs);
        } catch (ServiceSpecificException e) {
            throw new PioException(e);
        } catch (RemoteException e2) {
            throw new PioException(e2);
        }
    }

    public void setMode(int mode) throws IOException {
        try {
            this.mPioClient.SpiDeviceSetMode(this.mName, mode);
        } catch (ServiceSpecificException e) {
            throw new PioException(e);
        } catch (RemoteException e2) {
            throw new PioException(e2);
        }
    }

    public void setBitsPerWord(int bitsPerWord) throws IOException {
        try {
            this.mPioClient.SpiDeviceSetBitsPerWord(this.mName, bitsPerWord);
        } catch (ServiceSpecificException e) {
            throw new PioException(e);
        } catch (RemoteException e2) {
            throw new PioException(e2);
        }
    }

    public void setCsChange(boolean change) throws IOException {
        try {
            this.mPioClient.SpiDeviceSetCsChange(this.mName, change);
        } catch (ServiceSpecificException e) {
            throw new PioException(e);
        } catch (RemoteException e2) {
            throw new PioException(e2);
        }
    }

    public void transfer(byte[] txBuffer, byte[] rxBuffer, int length) throws IOException {
        if (rxBuffer == null) {
            try {
                rxBuffer = new byte[length];
            } catch (ServiceSpecificException e) {
                throw new PioException(e);
            } catch (RemoteException e2) {
                throw new PioException(e2);
            }
        }
        this.mPioClient.SpiDeviceTransfer(this.mName, txBuffer, rxBuffer, length);
    }

    public void close() throws IOException {
        try {
            this.mPioClient.ReleaseSpiDevice(this.mName);
        } catch (ServiceSpecificException e) {
            throw new PioException(e);
        } catch (RemoteException e2) {
            throw new PioException(e2);
        }
    }
}
