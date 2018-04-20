package com.google.android.things.internal.device;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.LocaleList;
import android.os.Parcel;
import android.os.PersistableBundle;
import android.os.RemoteException;

public interface IDeviceManagementService extends IInterface {

    public static abstract class Stub extends Binder implements IDeviceManagementService {
        private static final String DESCRIPTOR = "com.google.android.things.internal.device.IDeviceManagementService";
        static final int TRANSACTION_factoryReset = 3;
        static final int TRANSACTION_getLastParameters = 4;
        static final int TRANSACTION_lockRotation = 14;
        static final int TRANSACTION_reboot = 2;
        static final int TRANSACTION_setAutoTimeEnabled = 7;
        static final int TRANSACTION_setDisplayDensity = 13;
        static final int TRANSACTION_setFontScale = 12;
        static final int TRANSACTION_setScreenBrightness = 9;
        static final int TRANSACTION_setScreenBrightnessMode = 10;
        static final int TRANSACTION_setScreenOffTimeout = 11;
        static final int TRANSACTION_setSystemLocales = 1;
        static final int TRANSACTION_setTime = 5;
        static final int TRANSACTION_setTimeFormat = 8;
        static final int TRANSACTION_setTimeZone = 6;
        static final int TRANSACTION_unlockRotation = 15;

        private static class Proxy implements IDeviceManagementService {
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

            public void setSystemLocales(LocaleList localeList) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (localeList != null) {
                        _data.writeInt(1);
                        localeList.writeToParcel(_data, 0);
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

            public void reboot(PersistableBundle parameters) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (parameters != null) {
                        _data.writeInt(1);
                        parameters.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    this.mRemote.transact(2, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void factoryReset(boolean wipeExternalStorage) throws RemoteException {
                int i = 0;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (wipeExternalStorage) {
                        i = 1;
                    }
                    _data.writeInt(i);
                    this.mRemote.transact(3, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public PersistableBundle getLastParameters() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    PersistableBundle persistableBundle;
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(4, _data, _reply, 0);
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        persistableBundle = (PersistableBundle) PersistableBundle.CREATOR.createFromParcel(_reply);
                    } else {
                        persistableBundle = null;
                    }
                    _reply.recycle();
                    _data.recycle();
                    return persistableBundle;
                } catch (Throwable th) {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setTime(long millis) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeLong(millis);
                    this.mRemote.transact(5, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setTimeZone(String timeZone) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(timeZone);
                    this.mRemote.transact(6, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setAutoTimeEnabled(boolean enabled) throws RemoteException {
                int i = 0;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (enabled) {
                        i = 1;
                    }
                    _data.writeInt(i);
                    this.mRemote.transact(7, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setTimeFormat(int timeFormat) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(timeFormat);
                    this.mRemote.transact(8, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setScreenBrightness(int brightness) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(brightness);
                    this.mRemote.transact(9, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setScreenBrightnessMode(int brightnessMode) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(brightnessMode);
                    this.mRemote.transact(10, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setScreenOffTimeout(long millis) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeLong(millis);
                    this.mRemote.transact(11, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setFontScale(float scalingFactor) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeFloat(scalingFactor);
                    this.mRemote.transact(12, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setDisplayDensity(int displayId, int density) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(displayId);
                    _data.writeInt(density);
                    this.mRemote.transact(Stub.TRANSACTION_setDisplayDensity, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void lockRotation(int rotation) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(rotation);
                    this.mRemote.transact(Stub.TRANSACTION_lockRotation, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void unlockRotation() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(Stub.TRANSACTION_unlockRotation, _data, _reply, 0);
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

        public static IDeviceManagementService asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin == null || !(iin instanceof IDeviceManagementService)) {
                return new Proxy(obj);
            }
            return (IDeviceManagementService) iin;
        }

        public IBinder asBinder() {
            return this;
        }

        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            switch (code) {
                case 1:
                    LocaleList localeList;
                    data.enforceInterface(DESCRIPTOR);
                    if (data.readInt() != 0) {
                        localeList = (LocaleList) LocaleList.CREATOR.createFromParcel(data);
                    } else {
                        localeList = null;
                    }
                    setSystemLocales(localeList);
                    reply.writeNoException();
                    return true;
                case 2:
                    PersistableBundle persistableBundle;
                    data.enforceInterface(DESCRIPTOR);
                    if (data.readInt() != 0) {
                        persistableBundle = (PersistableBundle) PersistableBundle.CREATOR.createFromParcel(data);
                    } else {
                        persistableBundle = null;
                    }
                    reboot(persistableBundle);
                    reply.writeNoException();
                    return true;
                case 3:
                    data.enforceInterface(DESCRIPTOR);
                    factoryReset(data.readInt() != 0);
                    reply.writeNoException();
                    return true;
                case 4:
                    data.enforceInterface(DESCRIPTOR);
                    PersistableBundle _result = getLastParameters();
                    reply.writeNoException();
                    if (_result != null) {
                        reply.writeInt(1);
                        _result.writeToParcel(reply, 1);
                    } else {
                        reply.writeInt(0);
                    }
                    return true;
                case 5:
                    data.enforceInterface(DESCRIPTOR);
                    setTime(data.readLong());
                    reply.writeNoException();
                    return true;
                case 6:
                    data.enforceInterface(DESCRIPTOR);
                    setTimeZone(data.readString());
                    reply.writeNoException();
                    return true;
                case 7:
                    data.enforceInterface(DESCRIPTOR);
                    setAutoTimeEnabled(data.readInt() != 0);
                    reply.writeNoException();
                    return true;
                case 8:
                    data.enforceInterface(DESCRIPTOR);
                    setTimeFormat(data.readInt());
                    reply.writeNoException();
                    return true;
                case 9:
                    data.enforceInterface(DESCRIPTOR);
                    setScreenBrightness(data.readInt());
                    reply.writeNoException();
                    return true;
                case 10:
                    data.enforceInterface(DESCRIPTOR);
                    setScreenBrightnessMode(data.readInt());
                    reply.writeNoException();
                    return true;
                case 11:
                    data.enforceInterface(DESCRIPTOR);
                    setScreenOffTimeout(data.readLong());
                    reply.writeNoException();
                    return true;
                case 12:
                    data.enforceInterface(DESCRIPTOR);
                    setFontScale(data.readFloat());
                    reply.writeNoException();
                    return true;
                case TRANSACTION_setDisplayDensity /*13*/:
                    data.enforceInterface(DESCRIPTOR);
                    setDisplayDensity(data.readInt(), data.readInt());
                    reply.writeNoException();
                    return true;
                case TRANSACTION_lockRotation /*14*/:
                    data.enforceInterface(DESCRIPTOR);
                    lockRotation(data.readInt());
                    reply.writeNoException();
                    return true;
                case TRANSACTION_unlockRotation /*15*/:
                    data.enforceInterface(DESCRIPTOR);
                    unlockRotation();
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

    void factoryReset(boolean z) throws RemoteException;

    PersistableBundle getLastParameters() throws RemoteException;

    void lockRotation(int i) throws RemoteException;

    void reboot(PersistableBundle persistableBundle) throws RemoteException;

    void setAutoTimeEnabled(boolean z) throws RemoteException;

    void setDisplayDensity(int i, int i2) throws RemoteException;

    void setFontScale(float f) throws RemoteException;

    void setScreenBrightness(int i) throws RemoteException;

    void setScreenBrightnessMode(int i) throws RemoteException;

    void setScreenOffTimeout(long j) throws RemoteException;

    void setSystemLocales(LocaleList localeList) throws RemoteException;

    void setTime(long j) throws RemoteException;

    void setTimeFormat(int i) throws RemoteException;

    void setTimeZone(String str) throws RemoteException;

    void unlockRotation() throws RemoteException;
}
