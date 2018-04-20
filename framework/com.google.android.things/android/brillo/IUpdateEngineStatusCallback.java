package android.brillo;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IUpdateEngineStatusCallback extends IInterface {

    public static abstract class Stub extends Binder implements IUpdateEngineStatusCallback {
        private static final String DESCRIPTOR = "android.brillo.IUpdateEngineStatusCallback";
        static final int TRANSACTION_HandleStatusUpdate = 1;

        private static class Proxy implements IUpdateEngineStatusCallback {
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

            public void HandleStatusUpdate(ParcelableUpdateEngineStatus status) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (status != null) {
                        _data.writeInt(1);
                        status.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    this.mRemote.transact(1, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IUpdateEngineStatusCallback asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin == null || !(iin instanceof IUpdateEngineStatusCallback)) {
                return new Proxy(obj);
            }
            return (IUpdateEngineStatusCallback) iin;
        }

        public IBinder asBinder() {
            return this;
        }

        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            switch (code) {
                case 1:
                    ParcelableUpdateEngineStatus parcelableUpdateEngineStatus;
                    data.enforceInterface(DESCRIPTOR);
                    if (data.readInt() != 0) {
                        parcelableUpdateEngineStatus = (ParcelableUpdateEngineStatus) ParcelableUpdateEngineStatus.CREATOR.createFromParcel(data);
                    } else {
                        parcelableUpdateEngineStatus = null;
                    }
                    HandleStatusUpdate(parcelableUpdateEngineStatus);
                    return true;
                case 1598968902:
                    reply.writeString(DESCRIPTOR);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }
    }

    void HandleStatusUpdate(ParcelableUpdateEngineStatus parcelableUpdateEngineStatus) throws RemoteException;
}
