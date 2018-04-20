package com.google.android.things.userdriver;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IEffectDriverFactory extends IInterface {

    public static abstract class Stub extends Binder implements IEffectDriverFactory {
        private static final String DESCRIPTOR = "com.google.android.things.userdriver.IEffectDriverFactory";
        static final int TRANSACTION_freeEffect = 3;
        static final int TRANSACTION_getEffect = 2;
        static final int TRANSACTION_setCallback = 1;

        private static class Proxy implements IEffectDriverFactory {
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

            public void setCallback(IEffectDriverFactoryCallback factoryCallback, int callbackId) throws RemoteException {
                IBinder iBinder = null;
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (factoryCallback != null) {
                        iBinder = factoryCallback.asBinder();
                    }
                    _data.writeStrongBinder(iBinder);
                    _data.writeInt(callbackId);
                    this.mRemote.transact(1, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            public void getEffect(int callbackId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(callbackId);
                    this.mRemote.transact(2, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            public void freeEffect(IEffectDriver effect) throws RemoteException {
                IBinder iBinder = null;
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (effect != null) {
                        iBinder = effect.asBinder();
                    }
                    _data.writeStrongBinder(iBinder);
                    this.mRemote.transact(3, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IEffectDriverFactory asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin == null || !(iin instanceof IEffectDriverFactory)) {
                return new Proxy(obj);
            }
            return (IEffectDriverFactory) iin;
        }

        public IBinder asBinder() {
            return this;
        }

        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            switch (code) {
                case 1:
                    data.enforceInterface(DESCRIPTOR);
                    setCallback(com.google.android.things.userdriver.IEffectDriverFactoryCallback.Stub.asInterface(data.readStrongBinder()), data.readInt());
                    return true;
                case 2:
                    data.enforceInterface(DESCRIPTOR);
                    getEffect(data.readInt());
                    return true;
                case 3:
                    data.enforceInterface(DESCRIPTOR);
                    freeEffect(com.google.android.things.userdriver.IEffectDriver.Stub.asInterface(data.readStrongBinder()));
                    return true;
                case 1598968902:
                    reply.writeString(DESCRIPTOR);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }
    }

    void freeEffect(IEffectDriver iEffectDriver) throws RemoteException;

    void getEffect(int i) throws RemoteException;

    void setCallback(IEffectDriverFactoryCallback iEffectDriverFactoryCallback, int i) throws RemoteException;
}
