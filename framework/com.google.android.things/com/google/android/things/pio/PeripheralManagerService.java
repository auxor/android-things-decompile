package com.google.android.things.pio;

import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.ServiceManager;
import com.google.android.things.pio.IPeripheralManager.Stub;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PeripheralManagerService {
    static final String PIO_SERVICE_NAME = "com.google.android.things.pio.IPeripheralManager";
    static final String TAG = "PeripheralManagerService";
    private final Binder mLifeline = new Binder();
    private IPeripheralManagerClient mPioClient;

    public PeripheralManagerService() {
        connectToPioService();
    }

    private boolean connectToPioService() {
        if (this.mPioClient != null && this.mPioClient.asBinder().isBinderAlive()) {
            return true;
        }
        IBinder pioBinder = ServiceManager.getService(PIO_SERVICE_NAME);
        if (pioBinder != null) {
            try {
                this.mPioClient = Stub.asInterface(pioBinder).GetClient(this.mLifeline);
                return true;
            } catch (RemoteException e) {
                this.mPioClient = null;
            }
        }
        return false;
    }

    public List<String> getGpioList() {
        List<String> ret = new ArrayList();
        if (connectToPioService()) {
            try {
                this.mPioClient.ListGpio(ret);
            } catch (RemoteException e) {
            }
        }
        return ret;
    }

    public List<String> getPwmList() {
        List<String> ret = new ArrayList();
        if (connectToPioService()) {
            try {
                this.mPioClient.ListPwm(ret);
            } catch (RemoteException e) {
            }
        }
        return ret;
    }

    public List<String> getSpiBusList() {
        List<String> ret = new ArrayList();
        if (connectToPioService()) {
            try {
                this.mPioClient.ListSpiBuses(ret);
            } catch (RemoteException e) {
            }
        }
        return ret;
    }

    public List<String> getI2cBusList() {
        List<String> ret = new ArrayList();
        if (connectToPioService()) {
            try {
                this.mPioClient.ListI2cBuses(ret);
            } catch (RemoteException e) {
            }
        }
        return ret;
    }

    public List<String> getUartDeviceList() {
        List<String> ret = new ArrayList();
        if (connectToPioService()) {
            try {
                this.mPioClient.ListUartDevices(ret);
            } catch (RemoteException e) {
            }
        }
        return ret;
    }

    public Gpio openGpio(String name) throws IOException {
        return new GpioImpl(this.mPioClient, this.mLifeline, name);
    }

    public Pwm openPwm(String name) throws IOException {
        return new PwmImpl(this.mPioClient, this.mLifeline, name);
    }

    public SpiDevice openSpiDevice(String name) throws IOException {
        return new SpiDeviceImpl(this.mPioClient, this.mLifeline, name);
    }

    public I2cDevice openI2cDevice(String name, int address) throws IOException {
        return new I2cDeviceImpl(this.mPioClient, this.mLifeline, name, address);
    }

    public UartDevice openUartDevice(String name) throws IOException {
        return new UartDeviceImpl(this.mPioClient, this.mLifeline, name);
    }
}
