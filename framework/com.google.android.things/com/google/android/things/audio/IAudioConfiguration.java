package com.google.android.things.audio;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IAudioConfiguration extends IInterface {
    public static final int ENCODING_INVALID = 0;
    public static final int ENCODING_PCM_16_SIGNED = 2;
    public static final int ENCODING_PCM_24_SIGNED_LE = 21;
    public static final int ENCODING_PCM_24_SIGNED_PACKED = 20;
    public static final int ENCODING_PCM_32_FLOAT = 4;
    public static final int ENCODING_PCM_32_SIGNED = 22;
    public static final int ENCODING_PCM_8_UNSIGNED = 3;

    public static abstract class Stub extends Binder implements IAudioConfiguration {
        private static final String DESCRIPTOR = "com.google.android.things.audio.IAudioConfiguration";

        private static class Proxy implements IAudioConfiguration {
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

        public static IAudioConfiguration asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin == null || !(iin instanceof IAudioConfiguration)) {
                return new Proxy(obj);
            }
            return (IAudioConfiguration) iin;
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
