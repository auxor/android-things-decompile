package com.google.android.things.userdriver;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.things.audio.AudioConfiguration;

public interface IEffectDriverCallback extends IInterface {

    public static abstract class Stub extends Binder implements IEffectDriverCallback {
        private static final String DESCRIPTOR = "com.google.android.things.userdriver.IEffectDriverCallback";
        static final int TRANSACTION_onGetDefaultConfig = 6;
        static final int TRANSACTION_onProcess = 2;
        static final int TRANSACTION_onReset = 3;
        static final int TRANSACTION_onSetCallback = 1;
        static final int TRANSACTION_onSetConfig = 5;
        static final int TRANSACTION_onSetEnable = 4;

        private static class Proxy implements IEffectDriverCallback {
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

            public void onSetCallback(int callbackId, int error) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(callbackId);
                    _data.writeInt(error);
                    this.mRemote.transact(1, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            public void onProcess(int callbackId, int error, byte[] data) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(callbackId);
                    _data.writeInt(error);
                    _data.writeByteArray(data);
                    this.mRemote.transact(2, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            public void onReset(int callbackId, int error) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(callbackId);
                    _data.writeInt(error);
                    this.mRemote.transact(3, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            public void onSetEnable(int callbackId, int error) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(callbackId);
                    _data.writeInt(error);
                    this.mRemote.transact(4, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            public void onSetConfig(int callbackId, int error) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(callbackId);
                    _data.writeInt(error);
                    this.mRemote.transact(5, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            public void onGetDefaultConfig(int callbackId, int error, AudioConfiguration config) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(callbackId);
                    _data.writeInt(error);
                    if (config != null) {
                        _data.writeInt(1);
                        config.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    this.mRemote.transact(6, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IEffectDriverCallback asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin == null || !(iin instanceof IEffectDriverCallback)) {
                return new Proxy(obj);
            }
            return (IEffectDriverCallback) iin;
        }

        public IBinder asBinder() {
            return this;
        }

        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            switch (code) {
                case 1:
                    data.enforceInterface(DESCRIPTOR);
                    onSetCallback(data.readInt(), data.readInt());
                    return true;
                case 2:
                    data.enforceInterface(DESCRIPTOR);
                    onProcess(data.readInt(), data.readInt(), data.createByteArray());
                    return true;
                case 3:
                    data.enforceInterface(DESCRIPTOR);
                    onReset(data.readInt(), data.readInt());
                    return true;
                case 4:
                    data.enforceInterface(DESCRIPTOR);
                    onSetEnable(data.readInt(), data.readInt());
                    return true;
                case 5:
                    data.enforceInterface(DESCRIPTOR);
                    onSetConfig(data.readInt(), data.readInt());
                    return true;
                case 6:
                    AudioConfiguration audioConfiguration;
                    data.enforceInterface(DESCRIPTOR);
                    int _arg0 = data.readInt();
                    int _arg1 = data.readInt();
                    if (data.readInt() != 0) {
                        audioConfiguration = (AudioConfiguration) AudioConfiguration.CREATOR.createFromParcel(data);
                    } else {
                        audioConfiguration = null;
                    }
                    onGetDefaultConfig(_arg0, _arg1, audioConfiguration);
                    return true;
                case 1598968902:
                    reply.writeString(DESCRIPTOR);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }
    }

    void onGetDefaultConfig(int i, int i2, AudioConfiguration audioConfiguration) throws RemoteException;

    void onProcess(int i, int i2, byte[] bArr) throws RemoteException;

    void onReset(int i, int i2) throws RemoteException;

    void onSetCallback(int i, int i2) throws RemoteException;

    void onSetConfig(int i, int i2) throws RemoteException;

    void onSetEnable(int i, int i2) throws RemoteException;
}
