package com.google.android.things.userdriver;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface ILowpanDriverService extends IInterface {
    public static final String DEFAULT_INTERFACE_NAME = "DEFAULT_INTERFACE_NAME";

    public static abstract class Stub extends Binder implements ILowpanDriverService {
        private static final String DESCRIPTOR = "com.google.android.things.userdriver.ILowpanDriverService";
        static final int TRANSACTION_addLowpanDriver = 1;
        static final int TRANSACTION_removeLowpanDriver = 2;

        private static class Proxy implements ILowpanDriverService {
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

            public void addLowpanDriver(ILowpanDriver driver, String ifaceName) throws RemoteException {
                IBinder iBinder = null;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (driver != null) {
                        iBinder = driver.asBinder();
                    }
                    _data.writeStrongBinder(iBinder);
                    _data.writeString(ifaceName);
                    this.mRemote.transact(1, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void removeLowpanDriver(ILowpanDriver driver) throws RemoteException {
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
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static ILowpanDriverService asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin == null || !(iin instanceof ILowpanDriverService)) {
                return new Proxy(obj);
            }
            return (ILowpanDriverService) iin;
        }

        public IBinder asBinder() {
            return this;
        }

        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            switch (code) {
                case 1:
                    data.enforceInterface(DESCRIPTOR);
                    addLowpanDriver(com.google.android.things.userdriver.ILowpanDriver.Stub.asInterface(data.readStrongBinder()), data.readString());
                    reply.writeNoException();
                    return true;
                case 2:
                    data.enforceInterface(DESCRIPTOR);
                    removeLowpanDriver(com.google.android.things.userdriver.ILowpanDriver.Stub.asInterface(data.readStrongBinder()));
                    reply.writeNoException();
                    return true;
                case 1598968902:
                    reply.writeString(DESCRIPTOR);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }
    }

    void addLowpanDriver(ILowpanDriver iLowpanDriver, String str) throws RemoteException;

    void removeLowpanDriver(ILowpanDriver iLowpanDriver) throws RemoteException;
}
