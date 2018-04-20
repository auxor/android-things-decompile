package com.google.android.things.audio;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IUserEffect extends IInterface {
    public static final int TYPE_AUX = 1;
    public static final int TYPE_INSERT = 2;
    public static final int TYPE_MUSIC = 8;
    public static final int TYPE_PRE_PROCESSING = 4;

    public static abstract class Stub extends Binder implements IUserEffect {
        private static final String DESCRIPTOR = "com.google.android.things.audio.IUserEffect";

        private static class Proxy implements IUserEffect {
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
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IUserEffect asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin == null || !(iin instanceof IUserEffect)) {
                return new Proxy(obj);
            }
            return (IUserEffect) iin;
        }

        public IBinder asBinder() {
            return this;
        }

        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            switch (code) {
                case 1598968902:
                    reply.writeString(DESCRIPTOR);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }
    }
}
