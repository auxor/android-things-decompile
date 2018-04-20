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

class GpioImpl extends Gpio {
    private static final String TAG = "GpioImpl";
    private final Object mCallbackLock = new Object();
    private final Map<GpioCallback, CallbackDispatch> mCallbacks = new HashMap();
    private final String mName;
    private final IPeripheralManagerClient mPioClient;

    private class GpioCallbackDispatch extends CallbackDispatch {
        private final GpioCallback mCallback;

        GpioCallbackDispatch(GpioCallback callback, FileDescriptor fileDescriptor, Handler handler) {
            super(GpioImpl.this.mName, fileDescriptor, handler);
            this.mCallback = callback;
            start();
        }

        boolean dispatchInterruptEvent() {
            return this.mCallback.onGpioEdge(GpioImpl.this);
        }

        void dispatchInterruptError(int error) {
            this.mCallback.onGpioError(GpioImpl.this, error);
        }
    }

    GpioImpl(IPeripheralManagerClient service, Binder lifeline, String name) throws IOException {
        this.mName = name;
        try {
            this.mPioClient = service.GetGpioClient(name, lifeline);
            this.mPioClient.OpenGpio(name);
        } catch (ServiceSpecificException e) {
            throw new PioException(e);
        } catch (RemoteException e2) {
            throw new PioException(e2);
        }
    }

    public String getName() {
        return this.mName;
    }

    public void setDirection(int direction) throws IOException {
        try {
            this.mPioClient.SetGpioDirection(this.mName, direction);
        } catch (ServiceSpecificException e) {
            throw new PioException(e);
        } catch (RemoteException e2) {
            throw new PioException(e2);
        }
    }

    public void setEdgeTriggerType(int edgeTriggerType) throws IOException {
        try {
            this.mPioClient.SetGpioEdge(this.mName, edgeTriggerType);
        } catch (ServiceSpecificException e) {
            throw new PioException(e);
        } catch (RemoteException e2) {
            throw new PioException(e2);
        }
    }

    public void setActiveType(int activeType) throws IOException {
        try {
            this.mPioClient.SetGpioActiveType(this.mName, activeType);
        } catch (ServiceSpecificException e) {
            throw new PioException(e);
        } catch (RemoteException e2) {
            throw new PioException(e2);
        }
    }

    public void setValue(boolean value) throws IOException {
        try {
            this.mPioClient.SetGpioValue(this.mName, value);
        } catch (ServiceSpecificException e) {
            throw new PioException(e);
        } catch (RemoteException e2) {
            throw new PioException(e2);
        }
    }

    public boolean getValue() throws IOException {
        try {
            return this.mPioClient.GetGpioValue(this.mName);
        } catch (ServiceSpecificException e) {
            throw new PioException(e);
        } catch (RemoteException e2) {
            throw new PioException(e2);
        }
    }

    public void registerGpioCallback(GpioCallback callback, Handler handler) throws IOException {
        try {
            FileDescriptor fd = this.mPioClient.GetGpioPollingFd(this.mName);
            synchronized (this.mCallbackLock) {
                if (this.mCallbacks.containsKey(callback)) {
                    Log.w(TAG, "Ignoring callback re-registration on " + this.mName);
                    IoUtils.closeQuietly(fd);
                } else {
                    this.mCallbacks.put(callback, new GpioCallbackDispatch(callback, fd, handler));
                }
            }
        } catch (ServiceSpecificException e) {
            throw new PioException(e);
        } catch (RemoteException e2) {
            throw new PioException(e2);
        }
    }

    public void unregisterGpioCallback(GpioCallback callback) {
        synchronized (this.mCallbackLock) {
            CallbackDispatch dispatch = (CallbackDispatch) this.mCallbacks.remove(callback);
            if (dispatch != null) {
                dispatch.close();
            }
        }
    }

    public void close() throws IOException {
        try {
            this.mPioClient.ReleaseGpio(this.mName);
        } catch (ServiceSpecificException e) {
            throw new PioException(e);
        } catch (RemoteException e2) {
            throw new PioException(e2);
        }
    }
}
