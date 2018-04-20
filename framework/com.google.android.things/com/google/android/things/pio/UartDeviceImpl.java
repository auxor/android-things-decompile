package com.google.android.things.pio;

import android.os.Binder;
import android.os.Handler;
import android.os.RemoteException;
import android.os.ServiceSpecificException;
import android.util.Log;
import java.io.FileDescriptor;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import libcore.io.IoUtils;

class UartDeviceImpl extends UartDevice {
    private static final String TAG = "UartDevice";
    private final Object mCallbackLock = new Object();
    private final Map<UartDeviceCallback, CallbackDispatch> mCallbacks = new HashMap();
    private final String mName;
    private final IPeripheralManagerClient mPioClient;

    private class UartDeviceCallbackDispatch extends CallbackDispatch {
        private final UartDeviceCallback mCallback;

        UartDeviceCallbackDispatch(UartDeviceCallback callback, FileDescriptor fileDescriptor, Handler handler) {
            super(UartDeviceImpl.this.mName, fileDescriptor, handler);
            this.mCallback = callback;
            start();
        }

        boolean dispatchInterruptEvent() {
            return this.mCallback.onUartDeviceDataAvailable(UartDeviceImpl.this);
        }

        void dispatchInterruptError(int error) {
            this.mCallback.onUartDeviceError(UartDeviceImpl.this, error);
        }
    }

    UartDeviceImpl(IPeripheralManagerClient service, Binder lifeline, String name) throws IOException {
        this.mName = name;
        try {
            this.mPioClient = service.GetUartClient(name, lifeline);
            this.mPioClient.OpenUartDevice(name);
        } catch (ServiceSpecificException e) {
            throw new PioException(e);
        } catch (RemoteException e2) {
            throw new PioException(e2);
        }
    }

    public String getName() {
        return this.mName;
    }

    public void close() throws IOException {
        try {
            this.mPioClient.ReleaseUartDevice(this.mName);
        } catch (ServiceSpecificException e) {
            throw new PioException(e);
        } catch (RemoteException e2) {
            throw new PioException(e2);
        }
    }

    public void setBaudrate(int rate) throws IOException {
        try {
            this.mPioClient.UartDeviceSetBaudrate(this.mName, rate);
        } catch (ServiceSpecificException e) {
            throw new PioException(e);
        } catch (RemoteException e2) {
            throw new PioException(e2);
        }
    }

    public void setParity(int mode) throws IOException {
        try {
            this.mPioClient.UartDeviceSetParity(this.mName, mode);
        } catch (ServiceSpecificException e) {
            throw new PioException(e);
        } catch (RemoteException e2) {
            throw new PioException(e2);
        }
    }

    public void setDataSize(int size) throws IOException {
        try {
            this.mPioClient.UartDeviceSetDataSize(this.mName, size);
        } catch (ServiceSpecificException e) {
            throw new PioException(e);
        } catch (RemoteException e2) {
            throw new PioException(e2);
        }
    }

    public void setStopBits(int bits) throws IOException {
        try {
            this.mPioClient.UartDeviceSetStopBits(this.mName, bits);
        } catch (ServiceSpecificException e) {
            throw new PioException(e);
        } catch (RemoteException e2) {
            throw new PioException(e2);
        }
    }

    public void setHardwareFlowControl(int mode) throws IOException {
        try {
            this.mPioClient.UartDeviceSetHardwareFlowControl(this.mName, mode);
        } catch (ServiceSpecificException e) {
            throw new PioException(e);
        } catch (RemoteException e2) {
            throw new PioException(e2);
        }
    }

    public void setModemControl(int lines) throws IOException {
        try {
            this.mPioClient.UartDeviceSetModemControl(this.mName, lines);
        } catch (ServiceSpecificException e) {
            throw new PioException(e);
        } catch (RemoteException e2) {
            throw new PioException(e2);
        }
    }

    public void clearModemControl(int lines) throws IOException {
        try {
            this.mPioClient.UartDeviceClearModemControl(this.mName, lines);
        } catch (ServiceSpecificException e) {
            throw new PioException(e);
        } catch (RemoteException e2) {
            throw new PioException(e2);
        }
    }

    public void sendBreak(int duration_msecs) throws IOException {
        try {
            this.mPioClient.UartDeviceSendBreak(this.mName, duration_msecs);
        } catch (ServiceSpecificException e) {
            throw new PioException(e);
        } catch (RemoteException e2) {
            throw new PioException(e2);
        }
    }

    public void flush(int direction) throws IOException {
        try {
            this.mPioClient.UartDeviceFlush(this.mName, direction);
        } catch (ServiceSpecificException e) {
            throw new PioException(e);
        } catch (RemoteException e2) {
            throw new PioException(e2);
        }
    }

    public int read(byte[] buffer, int length) throws IOException {
        try {
            return this.mPioClient.UartDeviceRead(this.mName, buffer, length);
        } catch (ServiceSpecificException e) {
            throw new PioException(e);
        } catch (RemoteException e2) {
            throw new PioException(e2);
        }
    }

    public int write(byte[] buffer, int length) throws IOException {
        try {
            return this.mPioClient.UartDeviceWrite(this.mName, buffer, length);
        } catch (ServiceSpecificException e) {
            throw new PioException(e);
        } catch (RemoteException e2) {
            throw new PioException(e2);
        }
    }

    public void registerUartDeviceCallback(UartDeviceCallback callback, Handler handler) throws IOException {
        try {
            FileDescriptor fd = this.mPioClient.UartDeviceGetPollingFd(this.mName);
            synchronized (this.mCallbackLock) {
                if (this.mCallbacks.containsKey(callback)) {
                    Log.w(TAG, "Ignoring callback re-registration on " + this.mName);
                    IoUtils.closeQuietly(fd);
                } else {
                    this.mCallbacks.put(callback, new UartDeviceCallbackDispatch(callback, fd, handler));
                }
            }
        } catch (ServiceSpecificException e) {
            throw new PioException(e);
        } catch (RemoteException e2) {
            throw new PioException(e2);
        }
    }

    public void unregisterUartDeviceCallback(UartDeviceCallback callback) {
        synchronized (this.mCallbackLock) {
            CallbackDispatch dispatch = (CallbackDispatch) this.mCallbacks.remove(callback);
            if (dispatch != null) {
                dispatch.close();
            }
        }
    }
}
