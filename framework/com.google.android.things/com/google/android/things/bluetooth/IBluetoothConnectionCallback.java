package com.google.android.things.bluetooth;

import android.bluetooth.BluetoothDevice;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IBluetoothConnectionCallback extends IInterface {

    public static abstract class Stub extends Binder implements IBluetoothConnectionCallback {
        private static final String DESCRIPTOR = "com.google.android.things.bluetooth.IBluetoothConnectionCallback";
        static final int TRANSACTION_onConnected = 3;
        static final int TRANSACTION_onConnectionRequestCancelled = 2;
        static final int TRANSACTION_onConnectionRequested = 1;
        static final int TRANSACTION_onDisconnected = 4;

        private static class Proxy implements IBluetoothConnectionCallback {
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

            public void onConnectionRequested(BluetoothDevice bluetoothDevice, ConnectionParams connectionParams) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (bluetoothDevice != null) {
                        _data.writeInt(1);
                        bluetoothDevice.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (connectionParams != null) {
                        _data.writeInt(1);
                        connectionParams.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    this.mRemote.transact(1, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            public void onConnectionRequestCancelled(BluetoothDevice bluetoothDevice, int requestType) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (bluetoothDevice != null) {
                        _data.writeInt(1);
                        bluetoothDevice.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(requestType);
                    this.mRemote.transact(2, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            public void onConnected(BluetoothDevice bluetoothDevice, int bluetoothProfile) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (bluetoothDevice != null) {
                        _data.writeInt(1);
                        bluetoothDevice.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(bluetoothProfile);
                    this.mRemote.transact(3, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            public void onDisconnected(BluetoothDevice bluetoothDevice, int bluetoothProfile) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (bluetoothDevice != null) {
                        _data.writeInt(1);
                        bluetoothDevice.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(bluetoothProfile);
                    this.mRemote.transact(4, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IBluetoothConnectionCallback asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin == null || !(iin instanceof IBluetoothConnectionCallback)) {
                return new Proxy(obj);
            }
            return (IBluetoothConnectionCallback) iin;
        }

        public IBinder asBinder() {
            return this;
        }

        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            BluetoothDevice bluetoothDevice;
            switch (code) {
                case 1:
                    ConnectionParams connectionParams;
                    data.enforceInterface(DESCRIPTOR);
                    if (data.readInt() != 0) {
                        bluetoothDevice = (BluetoothDevice) BluetoothDevice.CREATOR.createFromParcel(data);
                    } else {
                        bluetoothDevice = null;
                    }
                    if (data.readInt() != 0) {
                        connectionParams = (ConnectionParams) ConnectionParams.CREATOR.createFromParcel(data);
                    } else {
                        connectionParams = null;
                    }
                    onConnectionRequested(bluetoothDevice, connectionParams);
                    return true;
                case 2:
                    data.enforceInterface(DESCRIPTOR);
                    if (data.readInt() != 0) {
                        bluetoothDevice = (BluetoothDevice) BluetoothDevice.CREATOR.createFromParcel(data);
                    } else {
                        bluetoothDevice = null;
                    }
                    onConnectionRequestCancelled(bluetoothDevice, data.readInt());
                    return true;
                case 3:
                    data.enforceInterface(DESCRIPTOR);
                    if (data.readInt() != 0) {
                        bluetoothDevice = (BluetoothDevice) BluetoothDevice.CREATOR.createFromParcel(data);
                    } else {
                        bluetoothDevice = null;
                    }
                    onConnected(bluetoothDevice, data.readInt());
                    return true;
                case 4:
                    data.enforceInterface(DESCRIPTOR);
                    if (data.readInt() != 0) {
                        bluetoothDevice = (BluetoothDevice) BluetoothDevice.CREATOR.createFromParcel(data);
                    } else {
                        bluetoothDevice = null;
                    }
                    onDisconnected(bluetoothDevice, data.readInt());
                    return true;
                case 1598968902:
                    reply.writeString(DESCRIPTOR);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }
    }

    void onConnected(BluetoothDevice bluetoothDevice, int i) throws RemoteException;

    void onConnectionRequestCancelled(BluetoothDevice bluetoothDevice, int i) throws RemoteException;

    void onConnectionRequested(BluetoothDevice bluetoothDevice, ConnectionParams connectionParams) throws RemoteException;

    void onDisconnected(BluetoothDevice bluetoothDevice, int i) throws RemoteException;
}
