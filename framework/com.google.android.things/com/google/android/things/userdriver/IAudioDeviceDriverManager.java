package com.google.android.things.userdriver;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IAudioDeviceDriverManager extends IInterface {

    public static abstract class Stub extends Binder implements IAudioDeviceDriverManager {
        private static final String DESCRIPTOR = "com.google.android.things.userdriver.IAudioDeviceDriverManager";
        static final int TRANSACTION_onStandbyCallbackComplete = 3;
        static final int TRANSACTION_registerDriver = 1;
        static final int TRANSACTION_unregisterDriver = 2;

        private static class Proxy implements IAudioDeviceDriverManager {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            public void registerDriver(int device_id, IAudioDeviceDriver driver) throws RemoteException {
                IBinder iBinder = null;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(device_id);
                    if (driver != null) {
                        iBinder = driver.asBinder();
                    }
                    _data.writeStrongBinder(iBinder);
                    this.mRemote.transact(1, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void unregisterDriver(IAudioDeviceDriver driver) throws RemoteException {
                IBinder iBinder = null;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (driver != null) {
                        iBinder = driver.asBinder();
                    }
                    _data.writeStrongBinder(iBinder);
                    this.mRemote.transact(2, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void onStandbyCallbackComplete(IAudioDeviceDriver driver, int callback_id, int error) throws RemoteException {
                IBinder iBinder = null;
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (driver != null) {
                        iBinder = driver.asBinder();
                    }
                    _data.writeStrongBinder(iBinder);
                    _data.writeInt(callback_id);
                    _data.writeInt(error);
                    this.mRemote.transact(3, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IAudioDeviceDriverManager asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin == null || !(iin instanceof IAudioDeviceDriverManager)) {
                return new Proxy(obj);
            }
            return (IAudioDeviceDriverManager) iin;
        }

        public IBinder asBinder() {
            return this;
        }

        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            switch (code) {
                case 1:
                    data.enforceInterface(DESCRIPTOR);
                    registerDriver(data.readInt(), com.google.android.things.userdriver.IAudioDeviceDriver.Stub.asInterface(data.readStrongBinder()));
                    reply.writeNoException();
                    return true;
                case 2:
                    data.enforceInterface(DESCRIPTOR);
                    unregisterDriver(com.google.android.things.userdriver.IAudioDeviceDriver.Stub.asInterface(data.readStrongBinder()));
                    reply.writeNoException();
                    return true;
                case 3:
                    data.enforceInterface(DESCRIPTOR);
                    onStandbyCallbackComplete(com.google.android.things.userdriver.IAudioDeviceDriver.Stub.asInterface(data.readStrongBinder()), data.readInt(), data.readInt());
                    return true;
                case 1598968902:
                    reply.writeString(DESCRIPTOR);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }
    }

    void onStandbyCallbackComplete(IAudioDeviceDriver iAudioDeviceDriver, int i, int i2) throws RemoteException;

    void registerDriver(int i, IAudioDeviceDriver iAudioDeviceDriver) throws RemoteException;

    void unregisterDriver(IAudioDeviceDriver iAudioDeviceDriver) throws RemoteException;
}
