package com.google.android.things.userdriver;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface ISensorDriverManager extends IInterface {

    public static abstract class Stub extends Binder implements ISensorDriverManager {
        private static final String DESCRIPTOR = "com.google.android.things.userdriver.ISensorDriverManager";
        static final int TRANSACTION_activate = 1;
        static final int TRANSACTION_batch = 4;
        static final int TRANSACTION_flush = 5;
        static final int TRANSACTION_poll = 3;
        static final int TRANSACTION_setDelay = 2;

        private static class Proxy implements ISensorDriverManager {
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

            public int activate(int sensorHandle, int enabled) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(sensorHandle);
                    _data.writeInt(enabled);
                    this.mRemote.transact(1, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int setDelay(int sensorHandle, long samplingPeriodNs) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(sensorHandle);
                    _data.writeLong(samplingPeriodNs);
                    this.mRemote.transact(2, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public SensorDriverEvent[] poll(int maxEvents) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(maxEvents);
                    this.mRemote.transact(3, _data, _reply, 0);
                    _reply.readException();
                    SensorDriverEvent[] _result = (SensorDriverEvent[]) _reply.createTypedArray(SensorDriverEvent.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int batch(int sensorHandle, int flags, long samplingPeriodNs, long maxReportLatencyNs) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(sensorHandle);
                    _data.writeInt(flags);
                    _data.writeLong(samplingPeriodNs);
                    _data.writeLong(maxReportLatencyNs);
                    this.mRemote.transact(4, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int flush(int sensorHandle) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(sensorHandle);
                    this.mRemote.transact(5, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
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

        public static ISensorDriverManager asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin == null || !(iin instanceof ISensorDriverManager)) {
                return new Proxy(obj);
            }
            return (ISensorDriverManager) iin;
        }

        public IBinder asBinder() {
            return this;
        }

        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            int _result;
            switch (code) {
                case 1:
                    data.enforceInterface(DESCRIPTOR);
                    _result = activate(data.readInt(), data.readInt());
                    reply.writeNoException();
                    reply.writeInt(_result);
                    return true;
                case 2:
                    data.enforceInterface(DESCRIPTOR);
                    _result = setDelay(data.readInt(), data.readLong());
                    reply.writeNoException();
                    reply.writeInt(_result);
                    return true;
                case 3:
                    data.enforceInterface(DESCRIPTOR);
                    SensorDriverEvent[] _result2 = poll(data.readInt());
                    reply.writeNoException();
                    reply.writeTypedArray(_result2, 1);
                    return true;
                case 4:
                    data.enforceInterface(DESCRIPTOR);
                    _result = batch(data.readInt(), data.readInt(), data.readLong(), data.readLong());
                    reply.writeNoException();
                    reply.writeInt(_result);
                    return true;
                case 5:
                    data.enforceInterface(DESCRIPTOR);
                    _result = flush(data.readInt());
                    reply.writeNoException();
                    reply.writeInt(_result);
                    return true;
                case 1598968902:
                    reply.writeString(DESCRIPTOR);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }
    }

    int activate(int i, int i2) throws RemoteException;

    int batch(int i, int i2, long j, long j2) throws RemoteException;

    int flush(int i) throws RemoteException;

    SensorDriverEvent[] poll(int i) throws RemoteException;

    int setDelay(int i, long j) throws RemoteException;
}
