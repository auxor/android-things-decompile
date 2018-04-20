package com.google.android.things.userdriver;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.things.audio.AudioConfiguration;

public interface IEffectDriver extends IInterface {

    public static abstract class Stub extends Binder implements IEffectDriver {
        private static final String DESCRIPTOR = "com.google.android.things.userdriver.IEffectDriver";
        static final int TRANSACTION_getDefaultConfig = 6;
        static final int TRANSACTION_process = 2;
        static final int TRANSACTION_reset = 3;
        static final int TRANSACTION_setCallback = 1;
        static final int TRANSACTION_setConfig = 5;
        static final int TRANSACTION_setEnable = 4;

        private static class Proxy implements IEffectDriver {
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

            public void setCallback(IEffectDriverCallback callback, int callbackId) throws RemoteException {
                IBinder iBinder = null;
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (callback != null) {
                        iBinder = callback.asBinder();
                    }
                    _data.writeStrongBinder(iBinder);
                    _data.writeInt(callbackId);
                    this.mRemote.transact(1, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            public void process(byte[] data, int callbackId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeByteArray(data);
                    _data.writeInt(callbackId);
                    this.mRemote.transact(2, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            public void reset(int callbackId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(callbackId);
                    this.mRemote.transact(3, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            public void setEnable(boolean enable, int callbackId) throws RemoteException {
                int i = 1;
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!enable) {
                        i = 0;
                    }
                    _data.writeInt(i);
                    _data.writeInt(callbackId);
                    this.mRemote.transact(4, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            public void setConfig(AudioConfiguration config, int callbackId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (config != null) {
                        _data.writeInt(1);
                        config.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(callbackId);
                    this.mRemote.transact(5, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            public void getDefaultConfig(int callbackId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(callbackId);
                    this.mRemote.transact(6, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IEffectDriver asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin == null || !(iin instanceof IEffectDriver)) {
                return new Proxy(obj);
            }
            return (IEffectDriver) iin;
        }

        public IBinder asBinder() {
            return this;
        }

        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            switch (code) {
                case 1:
                    data.enforceInterface(DESCRIPTOR);
                    setCallback(com.google.android.things.userdriver.IEffectDriverCallback.Stub.asInterface(data.readStrongBinder()), data.readInt());
                    return true;
                case 2:
                    data.enforceInterface(DESCRIPTOR);
                    process(data.createByteArray(), data.readInt());
                    return true;
                case 3:
                    data.enforceInterface(DESCRIPTOR);
                    reset(data.readInt());
                    return true;
                case 4:
                    data.enforceInterface(DESCRIPTOR);
                    setEnable(data.readInt() != 0, data.readInt());
                    return true;
                case 5:
                    AudioConfiguration audioConfiguration;
                    data.enforceInterface(DESCRIPTOR);
                    if (data.readInt() != 0) {
                        audioConfiguration = (AudioConfiguration) AudioConfiguration.CREATOR.createFromParcel(data);
                    } else {
                        audioConfiguration = null;
                    }
                    setConfig(audioConfiguration, data.readInt());
                    return true;
                case 6:
                    data.enforceInterface(DESCRIPTOR);
                    getDefaultConfig(data.readInt());
                    return true;
                case 1598968902:
                    reply.writeString(DESCRIPTOR);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }
    }

    void getDefaultConfig(int i) throws RemoteException;

    void process(byte[] bArr, int i) throws RemoteException;

    void reset(int i) throws RemoteException;

    void setCallback(IEffectDriverCallback iEffectDriverCallback, int i) throws RemoteException;

    void setConfig(AudioConfiguration audioConfiguration, int i) throws RemoteException;

    void setEnable(boolean z, int i) throws RemoteException;
}
