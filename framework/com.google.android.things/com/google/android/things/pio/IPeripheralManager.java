package com.google.android.things.pio;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IPeripheralManager extends IInterface {

    public static abstract class Stub extends Binder implements IPeripheralManager {
        private static final String DESCRIPTOR = "com.google.android.things.pio.IPeripheralManager";
        static final int TRANSACTION_GetClient = 1;
        static final int TRANSACTION_RegisterGpioSysfs = 2;
        static final int TRANSACTION_RegisterGpioUserDriver = 3;
        static final int TRANSACTION_RegisterI2cDevBus = 11;
        static final int TRANSACTION_RegisterI2cUserDriver = 12;
        static final int TRANSACTION_RegisterPwmSysfs = 14;
        static final int TRANSACTION_RegisterPwmUserDriver = 15;
        static final int TRANSACTION_RegisterSpiDevBus = 5;
        static final int TRANSACTION_RegisterSpiUserDriver = 6;
        static final int TRANSACTION_RegisterUartBus = 8;
        static final int TRANSACTION_RegisterUartUserDriver = 9;
        static final int TRANSACTION_UnregisterGpio = 4;
        static final int TRANSACTION_UnregisterI2c = 13;
        static final int TRANSACTION_UnregisterPwm = 16;
        static final int TRANSACTION_UnregisterSpi = 7;
        static final int TRANSACTION_UnregisterUart = 10;

        private static class Proxy implements IPeripheralManager {
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

            public IPeripheralManagerClient GetClient(IBinder lifeline) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(lifeline);
                    this.mRemote.transact(1, _data, _reply, 0);
                    _reply.readException();
                    IPeripheralManagerClient _result = com.google.android.things.pio.IPeripheralManagerClient.Stub.asInterface(_reply.readStrongBinder());
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void RegisterGpioSysfs(String name, int index) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(name);
                    _data.writeInt(index);
                    this.mRemote.transact(2, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void RegisterGpioUserDriver(String name, IUserDriverManager manager) throws RemoteException {
                IBinder iBinder = null;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(name);
                    if (manager != null) {
                        iBinder = manager.asBinder();
                    }
                    _data.writeStrongBinder(iBinder);
                    this.mRemote.transact(3, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void UnregisterGpio(String name) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(name);
                    this.mRemote.transact(4, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void RegisterSpiDevBus(String name, int bus, int cs) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(name);
                    _data.writeInt(bus);
                    _data.writeInt(cs);
                    this.mRemote.transact(5, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void RegisterSpiUserDriver(String name, IUserDriverManager manager) throws RemoteException {
                IBinder iBinder = null;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(name);
                    if (manager != null) {
                        iBinder = manager.asBinder();
                    }
                    _data.writeStrongBinder(iBinder);
                    this.mRemote.transact(6, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void UnregisterSpi(String name) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(name);
                    this.mRemote.transact(7, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void RegisterUartBus(String name, String dev_name) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(name);
                    _data.writeString(dev_name);
                    this.mRemote.transact(8, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void RegisterUartUserDriver(String name, IUserDriverManager manager) throws RemoteException {
                IBinder iBinder = null;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(name);
                    if (manager != null) {
                        iBinder = manager.asBinder();
                    }
                    _data.writeStrongBinder(iBinder);
                    this.mRemote.transact(9, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void UnregisterUart(String name) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(name);
                    this.mRemote.transact(10, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void RegisterI2cDevBus(String name, int bus) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(name);
                    _data.writeInt(bus);
                    this.mRemote.transact(11, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void RegisterI2cUserDriver(String name, IUserDriverManager manager) throws RemoteException {
                IBinder iBinder = null;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(name);
                    if (manager != null) {
                        iBinder = manager.asBinder();
                    }
                    _data.writeStrongBinder(iBinder);
                    this.mRemote.transact(12, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void UnregisterI2c(String name) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(name);
                    this.mRemote.transact(Stub.TRANSACTION_UnregisterI2c, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void RegisterPwmSysfs(String name, int controller_index, int pin_index) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(name);
                    _data.writeInt(controller_index);
                    _data.writeInt(pin_index);
                    this.mRemote.transact(Stub.TRANSACTION_RegisterPwmSysfs, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void RegisterPwmUserDriver(String name, IUserDriverManager manager) throws RemoteException {
                IBinder iBinder = null;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(name);
                    if (manager != null) {
                        iBinder = manager.asBinder();
                    }
                    _data.writeStrongBinder(iBinder);
                    this.mRemote.transact(Stub.TRANSACTION_RegisterPwmUserDriver, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void UnregisterPwm(String name) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(name);
                    this.mRemote.transact(16, _data, _reply, 0);
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

        public static IPeripheralManager asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin == null || !(iin instanceof IPeripheralManager)) {
                return new Proxy(obj);
            }
            return (IPeripheralManager) iin;
        }

        public IBinder asBinder() {
            return this;
        }

        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            IBinder iBinder = null;
            switch (code) {
                case 1:
                    data.enforceInterface(DESCRIPTOR);
                    IPeripheralManagerClient _result = GetClient(data.readStrongBinder());
                    reply.writeNoException();
                    if (_result != null) {
                        iBinder = _result.asBinder();
                    }
                    reply.writeStrongBinder(iBinder);
                    return true;
                case 2:
                    data.enforceInterface(DESCRIPTOR);
                    RegisterGpioSysfs(data.readString(), data.readInt());
                    reply.writeNoException();
                    return true;
                case 3:
                    data.enforceInterface(DESCRIPTOR);
                    RegisterGpioUserDriver(data.readString(), com.google.android.things.pio.IUserDriverManager.Stub.asInterface(data.readStrongBinder()));
                    reply.writeNoException();
                    return true;
                case 4:
                    data.enforceInterface(DESCRIPTOR);
                    UnregisterGpio(data.readString());
                    reply.writeNoException();
                    return true;
                case 5:
                    data.enforceInterface(DESCRIPTOR);
                    RegisterSpiDevBus(data.readString(), data.readInt(), data.readInt());
                    reply.writeNoException();
                    return true;
                case 6:
                    data.enforceInterface(DESCRIPTOR);
                    RegisterSpiUserDriver(data.readString(), com.google.android.things.pio.IUserDriverManager.Stub.asInterface(data.readStrongBinder()));
                    reply.writeNoException();
                    return true;
                case 7:
                    data.enforceInterface(DESCRIPTOR);
                    UnregisterSpi(data.readString());
                    reply.writeNoException();
                    return true;
                case 8:
                    data.enforceInterface(DESCRIPTOR);
                    RegisterUartBus(data.readString(), data.readString());
                    reply.writeNoException();
                    return true;
                case 9:
                    data.enforceInterface(DESCRIPTOR);
                    RegisterUartUserDriver(data.readString(), com.google.android.things.pio.IUserDriverManager.Stub.asInterface(data.readStrongBinder()));
                    reply.writeNoException();
                    return true;
                case 10:
                    data.enforceInterface(DESCRIPTOR);
                    UnregisterUart(data.readString());
                    reply.writeNoException();
                    return true;
                case 11:
                    data.enforceInterface(DESCRIPTOR);
                    RegisterI2cDevBus(data.readString(), data.readInt());
                    reply.writeNoException();
                    return true;
                case 12:
                    data.enforceInterface(DESCRIPTOR);
                    RegisterI2cUserDriver(data.readString(), com.google.android.things.pio.IUserDriverManager.Stub.asInterface(data.readStrongBinder()));
                    reply.writeNoException();
                    return true;
                case TRANSACTION_UnregisterI2c /*13*/:
                    data.enforceInterface(DESCRIPTOR);
                    UnregisterI2c(data.readString());
                    reply.writeNoException();
                    return true;
                case TRANSACTION_RegisterPwmSysfs /*14*/:
                    data.enforceInterface(DESCRIPTOR);
                    RegisterPwmSysfs(data.readString(), data.readInt(), data.readInt());
                    reply.writeNoException();
                    return true;
                case TRANSACTION_RegisterPwmUserDriver /*15*/:
                    data.enforceInterface(DESCRIPTOR);
                    RegisterPwmUserDriver(data.readString(), com.google.android.things.pio.IUserDriverManager.Stub.asInterface(data.readStrongBinder()));
                    reply.writeNoException();
                    return true;
                case 16:
                    data.enforceInterface(DESCRIPTOR);
                    UnregisterPwm(data.readString());
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

    IPeripheralManagerClient GetClient(IBinder iBinder) throws RemoteException;

    void RegisterGpioSysfs(String str, int i) throws RemoteException;

    void RegisterGpioUserDriver(String str, IUserDriverManager iUserDriverManager) throws RemoteException;

    void RegisterI2cDevBus(String str, int i) throws RemoteException;

    void RegisterI2cUserDriver(String str, IUserDriverManager iUserDriverManager) throws RemoteException;

    void RegisterPwmSysfs(String str, int i, int i2) throws RemoteException;

    void RegisterPwmUserDriver(String str, IUserDriverManager iUserDriverManager) throws RemoteException;

    void RegisterSpiDevBus(String str, int i, int i2) throws RemoteException;

    void RegisterSpiUserDriver(String str, IUserDriverManager iUserDriverManager) throws RemoteException;

    void RegisterUartBus(String str, String str2) throws RemoteException;

    void RegisterUartUserDriver(String str, IUserDriverManager iUserDriverManager) throws RemoteException;

    void UnregisterGpio(String str) throws RemoteException;

    void UnregisterI2c(String str) throws RemoteException;

    void UnregisterPwm(String str) throws RemoteException;

    void UnregisterSpi(String str) throws RemoteException;

    void UnregisterUart(String str) throws RemoteException;
}
