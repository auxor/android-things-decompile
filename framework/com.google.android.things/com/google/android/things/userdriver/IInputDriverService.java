package com.google.android.things.userdriver;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IInputDriverService extends IInterface {

    public static abstract class Stub extends Binder implements IInputDriverService {
        private static final String DESCRIPTOR = "com.google.android.things.userdriver.IInputDriverService";
        static final int TRANSACTION_createInputDevice = 1;
        static final int TRANSACTION_destroyInputDevice = 2;
        static final int TRANSACTION_emit = 3;

        private static class Proxy implements IInputDriverService {
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

            public int createInputDevice(IBinder lifeline, String name, InputDriverConfig[] supportedEvents, int busType, int vendor, int product, int version, int ffEffectsMax, int[] absMax, int[] absMin, int[] absFuzz, int[] absFlat) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(lifeline);
                    _data.writeString(name);
                    _data.writeTypedArray(supportedEvents, 0);
                    _data.writeInt(busType);
                    _data.writeInt(vendor);
                    _data.writeInt(product);
                    _data.writeInt(version);
                    _data.writeInt(ffEffectsMax);
                    _data.writeIntArray(absMax);
                    _data.writeIntArray(absMin);
                    _data.writeIntArray(absFuzz);
                    _data.writeIntArray(absFlat);
                    this.mRemote.transact(1, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void destroyInputDevice(int deviceHandle) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(deviceHandle);
                    this.mRemote.transact(2, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void emit(int deviceHandle, InputDriverEvent[] events) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(deviceHandle);
                    _data.writeTypedArray(events, 0);
                    this.mRemote.transact(3, _data, _reply, 0);
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

        public static IInputDriverService asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin == null || !(iin instanceof IInputDriverService)) {
                return new Proxy(obj);
            }
            return (IInputDriverService) iin;
        }

        public IBinder asBinder() {
            return this;
        }

        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            switch (code) {
                case 1:
                    data.enforceInterface(DESCRIPTOR);
                    int _result = createInputDevice(data.readStrongBinder(), data.readString(), (InputDriverConfig[]) data.createTypedArray(InputDriverConfig.CREATOR), data.readInt(), data.readInt(), data.readInt(), data.readInt(), data.readInt(), data.createIntArray(), data.createIntArray(), data.createIntArray(), data.createIntArray());
                    reply.writeNoException();
                    reply.writeInt(_result);
                    return true;
                case 2:
                    data.enforceInterface(DESCRIPTOR);
                    destroyInputDevice(data.readInt());
                    reply.writeNoException();
                    return true;
                case 3:
                    data.enforceInterface(DESCRIPTOR);
                    emit(data.readInt(), (InputDriverEvent[]) data.createTypedArray(InputDriverEvent.CREATOR));
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

    int createInputDevice(IBinder iBinder, String str, InputDriverConfig[] inputDriverConfigArr, int i, int i2, int i3, int i4, int i5, int[] iArr, int[] iArr2, int[] iArr3, int[] iArr4) throws RemoteException;

    void destroyInputDevice(int i) throws RemoteException;

    void emit(int i, InputDriverEvent[] inputDriverEventArr) throws RemoteException;
}
