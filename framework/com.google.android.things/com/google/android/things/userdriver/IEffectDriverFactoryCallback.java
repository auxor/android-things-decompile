package com.google.android.things.userdriver;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IEffectDriverFactoryCallback extends IInterface {

    public static abstract class Stub extends Binder implements IEffectDriverFactoryCallback {
        private static final String DESCRIPTOR = "com.google.android.things.userdriver.IEffectDriverFactoryCallback";
        static final int TRANSACTION_onGetEffect = 2;
        static final int TRANSACTION_onSetCallback = 1;

        private static class Proxy implements IEffectDriverFactoryCallback {
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

            public void onGetEffect(int callbackId, int error, IEffectDriver driver) throws RemoteException {
                IBinder iBinder = null;
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(callbackId);
                    _data.writeInt(error);
                    if (driver != null) {
                        iBinder = driver.asBinder();
                    }
                    _data.writeStrongBinder(iBinder);
                    this.mRemote.transact(2, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IEffectDriverFactoryCallback asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin == null || !(iin instanceof IEffectDriverFactoryCallback)) {
                return new Proxy(obj);
            }
            return (IEffectDriverFactoryCallback) iin;
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
                    onGetEffect(data.readInt(), data.readInt(), com.google.android.things.userdriver.IEffectDriver.Stub.asInterface(data.readStrongBinder()));
                    return true;
                case 1598968902:
                    reply.writeString(DESCRIPTOR);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }
    }

    void onGetEffect(int i, int i2, IEffectDriver iEffectDriver) throws RemoteException;

    void onSetCallback(int i, int i2) throws RemoteException;
}
