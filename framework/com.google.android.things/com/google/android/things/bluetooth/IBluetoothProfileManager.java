package com.google.android.things.bluetooth;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IBluetoothProfileManager extends IInterface {

    public static abstract class Stub extends Binder implements IBluetoothProfileManager {
        private static final String DESCRIPTOR = "com.google.android.things.bluetooth.IBluetoothProfileManager";
        static final int TRANSACTION_getEnabledProfiles = 3;
        static final int TRANSACTION_modifyEnabledProfiles = 1;
        static final int TRANSACTION_setEnabledProfiles = 2;

        private static class Proxy implements IBluetoothProfileManager {
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

            public void modifyEnabledProfiles(int[] profilesToEnable, int[] profilesToDisable) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeIntArray(profilesToEnable);
                    _data.writeIntArray(profilesToDisable);
                    this.mRemote.transact(1, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setEnabledProfiles(int[] profiles) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeIntArray(profiles);
                    this.mRemote.transact(2, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int[] getEnabledProfiles() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(3, _data, _reply, 0);
                    _reply.readException();
                    int[] _result = _reply.createIntArray();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IBluetoothProfileManager asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin == null || !(iin instanceof IBluetoothProfileManager)) {
                return new Proxy(obj);
            }
            return (IBluetoothProfileManager) iin;
        }

        public IBinder asBinder() {
            return this;
        }

        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            switch (code) {
                case 1:
                    data.enforceInterface(DESCRIPTOR);
                    modifyEnabledProfiles(data.createIntArray(), data.createIntArray());
                    reply.writeNoException();
                    return true;
                case 2:
                    data.enforceInterface(DESCRIPTOR);
                    setEnabledProfiles(data.createIntArray());
                    reply.writeNoException();
                    return true;
                case 3:
                    data.enforceInterface(DESCRIPTOR);
                    int[] _result = getEnabledProfiles();
                    reply.writeNoException();
                    reply.writeIntArray(_result);
                    return true;
                case 1598968902:
                    reply.writeString(DESCRIPTOR);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }
    }

    int[] getEnabledProfiles() throws RemoteException;

    void modifyEnabledProfiles(int[] iArr, int[] iArr2) throws RemoteException;

    void setEnabledProfiles(int[] iArr) throws RemoteException;
}
