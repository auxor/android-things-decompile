package com.google.android.things.internal.update;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.things.update.UpdateManagerStatus;

public interface IUpdateManagerStatusListener extends IInterface {

    public static abstract class Stub extends Binder implements IUpdateManagerStatusListener {
        private static final String DESCRIPTOR = "com.google.android.things.internal.update.IUpdateManagerStatusListener";
        static final int TRANSACTION_onStatusUpdate = 1;

        private static class Proxy implements IUpdateManagerStatusListener {
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

            public void onStatusUpdate(UpdateManagerStatus status) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (status != null) {
                        _data.writeInt(1);
                        status.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    this.mRemote.transact(1, _data, _reply, 0);
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

        public static IUpdateManagerStatusListener asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin == null || !(iin instanceof IUpdateManagerStatusListener)) {
                return new Proxy(obj);
            }
            return (IUpdateManagerStatusListener) iin;
        }

        public IBinder asBinder() {
            return this;
        }

        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            switch (code) {
                case 1:
                    UpdateManagerStatus updateManagerStatus;
                    data.enforceInterface(DESCRIPTOR);
                    if (data.readInt() != 0) {
                        updateManagerStatus = (UpdateManagerStatus) UpdateManagerStatus.CREATOR.createFromParcel(data);
                    } else {
                        updateManagerStatus = null;
                    }
                    onStatusUpdate(updateManagerStatus);
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

    void onStatusUpdate(UpdateManagerStatus updateManagerStatus) throws RemoteException;
}
