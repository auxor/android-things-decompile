package com.google.android.things.bluetooth;

import android.bluetooth.BluetoothClass;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IBluetoothConfigManager extends IInterface {

    public static abstract class Stub extends Binder implements IBluetoothConfigManager {
        private static final String DESCRIPTOR = "com.google.android.things.bluetooth.IBluetoothConfigManager";
        static final int TRANSACTION_getBluetoothClass = 1;
        static final int TRANSACTION_setBluetoothClass = 2;

        private static class Proxy implements IBluetoothConfigManager {
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

            public BluetoothClass getBluetoothClass() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    BluetoothClass bluetoothClass;
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(1, _data, _reply, 0);
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        bluetoothClass = (BluetoothClass) BluetoothClass.CREATOR.createFromParcel(_reply);
                    } else {
                        bluetoothClass = null;
                    }
                    _reply.recycle();
                    _data.recycle();
                    return bluetoothClass;
                } catch (Throwable th) {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean setBluetoothClass(BluetoothClass bluetoothClass) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (bluetoothClass != null) {
                        _data.writeInt(1);
                        bluetoothClass.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    this.mRemote.transact(2, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readInt() != 0;
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } catch (Throwable th) {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IBluetoothConfigManager asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin == null || !(iin instanceof IBluetoothConfigManager)) {
                return new Proxy(obj);
            }
            return (IBluetoothConfigManager) iin;
        }

        public IBinder asBinder() {
            return this;
        }

        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            int i = 0;
            switch (code) {
                case 1:
                    data.enforceInterface(DESCRIPTOR);
                    BluetoothClass _result = getBluetoothClass();
                    reply.writeNoException();
                    if (_result != null) {
                        reply.writeInt(1);
                        _result.writeToParcel(reply, 1);
                    } else {
                        reply.writeInt(0);
                    }
                    return true;
                case 2:
                    BluetoothClass bluetoothClass;
                    data.enforceInterface(DESCRIPTOR);
                    if (data.readInt() != 0) {
                        bluetoothClass = (BluetoothClass) BluetoothClass.CREATOR.createFromParcel(data);
                    } else {
                        bluetoothClass = null;
                    }
                    boolean _result2 = setBluetoothClass(bluetoothClass);
                    reply.writeNoException();
                    if (_result2) {
                        i = 1;
                    }
                    reply.writeInt(i);
                    return true;
                case 1598968902:
                    reply.writeString(DESCRIPTOR);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }
    }

    BluetoothClass getBluetoothClass() throws RemoteException;

    boolean setBluetoothClass(BluetoothClass bluetoothClass) throws RemoteException;
}
