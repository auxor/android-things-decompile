package com.google.android.things.userdriver;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface ILowpanDriverCallback extends IInterface {

    public static abstract class Stub extends Binder implements ILowpanDriverCallback {
        private static final String DESCRIPTOR = "com.google.android.things.userdriver.ILowpanDriverCallback";
        static final int TRANSACTION_onError = 4;
        static final int TRANSACTION_onReceiveFrame = 2;
        static final int TRANSACTION_onReset = 3;
        static final int TRANSACTION_onStarted = 1;

        private static class Proxy implements ILowpanDriverCallback {
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

            public void onStarted() throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(1, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            public void onReceiveFrame(byte[] frame) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeByteArray(frame);
                    this.mRemote.transact(2, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            public void onReset() throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(3, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            public void onError(int error) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(error);
                    this.mRemote.transact(4, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static ILowpanDriverCallback asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin == null || !(iin instanceof ILowpanDriverCallback)) {
                return new Proxy(obj);
            }
            return (ILowpanDriverCallback) iin;
        }

        public IBinder asBinder() {
            return this;
        }

        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            switch (code) {
                case 1:
                    data.enforceInterface(DESCRIPTOR);
                    onStarted();
                    return true;
                case 2:
                    data.enforceInterface(DESCRIPTOR);
                    onReceiveFrame(data.createByteArray());
                    return true;
                case 3:
                    data.enforceInterface(DESCRIPTOR);
                    onReset();
                    return true;
                case 4:
                    data.enforceInterface(DESCRIPTOR);
                    onError(data.readInt());
                    return true;
                case 1598968902:
                    reply.writeString(DESCRIPTOR);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }
    }

    void onError(int i) throws RemoteException;

    void onReceiveFrame(byte[] bArr) throws RemoteException;

    void onReset() throws RemoteException;

    void onStarted() throws RemoteException;
}
