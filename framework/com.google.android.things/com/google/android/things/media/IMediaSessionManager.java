package com.google.android.things.media;

import android.content.ComponentName;
import android.media.session.MediaSession.Token;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import java.util.List;

public interface IMediaSessionManager extends IInterface {

    public static abstract class Stub extends Binder implements IMediaSessionManager {
        private static final String DESCRIPTOR = "com.google.android.things.media.IMediaSessionManager";
        static final int TRANSACTION_addOnActiveSessionsChangedListener = 1;
        static final int TRANSACTION_getActiveSessions = 3;
        static final int TRANSACTION_removeOnActiveSessionsChangedListener = 2;

        private static class Proxy implements IMediaSessionManager {
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

            public void addOnActiveSessionsChangedListener(IMediaSessionChangeListener sessionListener) throws RemoteException {
                IBinder iBinder = null;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (sessionListener != null) {
                        iBinder = sessionListener.asBinder();
                    }
                    _data.writeStrongBinder(iBinder);
                    this.mRemote.transact(1, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void removeOnActiveSessionsChangedListener(IMediaSessionChangeListener sessionListener) throws RemoteException {
                IBinder iBinder = null;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (sessionListener != null) {
                        iBinder = sessionListener.asBinder();
                    }
                    _data.writeStrongBinder(iBinder);
                    this.mRemote.transact(2, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public List<Token> getActiveSessions(ComponentName notificationListener) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (notificationListener != null) {
                        _data.writeInt(1);
                        notificationListener.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    this.mRemote.transact(3, _data, _reply, 0);
                    _reply.readException();
                    List<Token> _result = _reply.createTypedArrayList(Token.CREATOR);
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

        public static IMediaSessionManager asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin == null || !(iin instanceof IMediaSessionManager)) {
                return new Proxy(obj);
            }
            return (IMediaSessionManager) iin;
        }

        public IBinder asBinder() {
            return this;
        }

        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            switch (code) {
                case 1:
                    data.enforceInterface(DESCRIPTOR);
                    addOnActiveSessionsChangedListener(com.google.android.things.media.IMediaSessionChangeListener.Stub.asInterface(data.readStrongBinder()));
                    reply.writeNoException();
                    return true;
                case 2:
                    data.enforceInterface(DESCRIPTOR);
                    removeOnActiveSessionsChangedListener(com.google.android.things.media.IMediaSessionChangeListener.Stub.asInterface(data.readStrongBinder()));
                    reply.writeNoException();
                    return true;
                case 3:
                    ComponentName componentName;
                    data.enforceInterface(DESCRIPTOR);
                    if (data.readInt() != 0) {
                        componentName = (ComponentName) ComponentName.CREATOR.createFromParcel(data);
                    } else {
                        componentName = null;
                    }
                    List<Token> _result = getActiveSessions(componentName);
                    reply.writeNoException();
                    reply.writeTypedList(_result);
                    return true;
                case 1598968902:
                    reply.writeString(DESCRIPTOR);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }
    }

    void addOnActiveSessionsChangedListener(IMediaSessionChangeListener iMediaSessionChangeListener) throws RemoteException;

    List<Token> getActiveSessions(ComponentName componentName) throws RemoteException;

    void removeOnActiveSessionsChangedListener(IMediaSessionChangeListener iMediaSessionChangeListener) throws RemoteException;
}
