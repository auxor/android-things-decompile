package android.brillo;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IUpdateEngine extends IInterface {

    public static abstract class Stub extends Binder implements IUpdateEngine {
        private static final String DESCRIPTOR = "android.brillo.IUpdateEngine";
        static final int TRANSACTION_AttemptRollback = 3;
        static final int TRANSACTION_AttemptUpdate = 2;
        static final int TRANSACTION_CanRollback = 4;
        static final int TRANSACTION_GetChannel = 9;
        static final int TRANSACTION_GetCohortHint = 11;
        static final int TRANSACTION_GetDurationSinceUpdate = 16;
        static final int TRANSACTION_GetEolStatus = 21;
        static final int TRANSACTION_GetLastAttemptError = 20;
        static final int TRANSACTION_GetP2PUpdatePermission = 13;
        static final int TRANSACTION_GetPrevVersion = 17;
        static final int TRANSACTION_GetRollbackPartition = 18;
        static final int TRANSACTION_GetStatus = 6;
        static final int TRANSACTION_GetUpdateOverCellularPermission = 15;
        static final int TRANSACTION_RebootIfNeeded = 7;
        static final int TRANSACTION_RegisterStatusCallback = 19;
        static final int TRANSACTION_ResetStatus = 5;
        static final int TRANSACTION_SetChannel = 8;
        static final int TRANSACTION_SetCohortHint = 10;
        static final int TRANSACTION_SetP2PUpdatePermission = 12;
        static final int TRANSACTION_SetUpdateAttemptFlags = 1;
        static final int TRANSACTION_SetUpdateOverCellularPermission = 14;

        private static class Proxy implements IUpdateEngine {
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

            public void SetUpdateAttemptFlags(int flags) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(flags);
                    this.mRemote.transact(1, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean AttemptUpdate(String app_version, String omaha_url, int flags) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(app_version);
                    _data.writeString(omaha_url);
                    _data.writeInt(flags);
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

            public void AttemptRollback(boolean powerwash) throws RemoteException {
                int i = 0;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (powerwash) {
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

            public boolean CanRollback() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(4, _data, _reply, 0);
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

            public void ResetStatus() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(5, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public ParcelableUpdateEngineStatus GetStatus() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    ParcelableUpdateEngineStatus parcelableUpdateEngineStatus;
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(6, _data, _reply, 0);
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        parcelableUpdateEngineStatus = (ParcelableUpdateEngineStatus) ParcelableUpdateEngineStatus.CREATOR.createFromParcel(_reply);
                    } else {
                        parcelableUpdateEngineStatus = null;
                    }
                    _reply.recycle();
                    _data.recycle();
                    return parcelableUpdateEngineStatus;
                } catch (Throwable th) {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void RebootIfNeeded() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(7, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void SetChannel(String target_channel, boolean powewash) throws RemoteException {
                int i = 0;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(target_channel);
                    if (powewash) {
                        i = 1;
                    }
                    _data.writeInt(i);
                    this.mRemote.transact(8, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public String GetChannel(boolean get_current_channel) throws RemoteException {
                int i = 0;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (get_current_channel) {
                        i = 1;
                    }
                    _data.writeInt(i);
                    this.mRemote.transact(9, _data, _reply, 0);
                    _reply.readException();
                    String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void SetCohortHint(String cohort_hint) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(cohort_hint);
                    this.mRemote.transact(10, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public String GetCohortHint() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(11, _data, _reply, 0);
                    _reply.readException();
                    String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void SetP2PUpdatePermission(boolean enabled) throws RemoteException {
                int i = 0;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (enabled) {
                        i = 1;
                    }
                    _data.writeInt(i);
                    this.mRemote.transact(12, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean GetP2PUpdatePermission() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(Stub.TRANSACTION_GetP2PUpdatePermission, _data, _reply, 0);
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

            public void SetUpdateOverCellularPermission(boolean enabled) throws RemoteException {
                int i = 0;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (enabled) {
                        i = 1;
                    }
                    _data.writeInt(i);
                    this.mRemote.transact(Stub.TRANSACTION_SetUpdateOverCellularPermission, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean GetUpdateOverCellularPermission() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(Stub.TRANSACTION_GetUpdateOverCellularPermission, _data, _reply, 0);
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

            public long GetDurationSinceUpdate() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(16, _data, _reply, 0);
                    _reply.readException();
                    long _result = _reply.readLong();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public String GetPrevVersion() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(17, _data, _reply, 0);
                    _reply.readException();
                    String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public String GetRollbackPartition() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(Stub.TRANSACTION_GetRollbackPartition, _data, _reply, 0);
                    _reply.readException();
                    String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void RegisterStatusCallback(IUpdateEngineStatusCallback callback) throws RemoteException {
                IBinder iBinder = null;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (callback != null) {
                        iBinder = callback.asBinder();
                    }
                    _data.writeStrongBinder(iBinder);
                    this.mRemote.transact(Stub.TRANSACTION_RegisterStatusCallback, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int GetLastAttemptError() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(20, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int GetEolStatus() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(21, _data, _reply, 0);
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

        public static IUpdateEngine asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin == null || !(iin instanceof IUpdateEngine)) {
                return new Proxy(obj);
            }
            return (IUpdateEngine) iin;
        }

        public IBinder asBinder() {
            return this;
        }

        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            boolean _result;
            String _result2;
            int _result3;
            switch (code) {
                case 1:
                    data.enforceInterface(DESCRIPTOR);
                    SetUpdateAttemptFlags(data.readInt());
                    reply.writeNoException();
                    return true;
                case 2:
                    data.enforceInterface(DESCRIPTOR);
                    _result = AttemptUpdate(data.readString(), data.readString(), data.readInt());
                    reply.writeNoException();
                    reply.writeInt(_result ? 1 : 0);
                    return true;
                case 3:
                    data.enforceInterface(DESCRIPTOR);
                    AttemptRollback(data.readInt() != 0);
                    reply.writeNoException();
                    return true;
                case 4:
                    data.enforceInterface(DESCRIPTOR);
                    _result = CanRollback();
                    reply.writeNoException();
                    reply.writeInt(_result ? 1 : 0);
                    return true;
                case 5:
                    data.enforceInterface(DESCRIPTOR);
                    ResetStatus();
                    reply.writeNoException();
                    return true;
                case 6:
                    data.enforceInterface(DESCRIPTOR);
                    ParcelableUpdateEngineStatus _result4 = GetStatus();
                    reply.writeNoException();
                    if (_result4 != null) {
                        reply.writeInt(1);
                        _result4.writeToParcel(reply, 1);
                    } else {
                        reply.writeInt(0);
                    }
                    return true;
                case 7:
                    data.enforceInterface(DESCRIPTOR);
                    RebootIfNeeded();
                    reply.writeNoException();
                    return true;
                case 8:
                    data.enforceInterface(DESCRIPTOR);
                    SetChannel(data.readString(), data.readInt() != 0);
                    reply.writeNoException();
                    return true;
                case 9:
                    data.enforceInterface(DESCRIPTOR);
                    _result2 = GetChannel(data.readInt() != 0);
                    reply.writeNoException();
                    reply.writeString(_result2);
                    return true;
                case 10:
                    data.enforceInterface(DESCRIPTOR);
                    SetCohortHint(data.readString());
                    reply.writeNoException();
                    return true;
                case 11:
                    data.enforceInterface(DESCRIPTOR);
                    _result2 = GetCohortHint();
                    reply.writeNoException();
                    reply.writeString(_result2);
                    return true;
                case 12:
                    data.enforceInterface(DESCRIPTOR);
                    SetP2PUpdatePermission(data.readInt() != 0);
                    reply.writeNoException();
                    return true;
                case TRANSACTION_GetP2PUpdatePermission /*13*/:
                    data.enforceInterface(DESCRIPTOR);
                    _result = GetP2PUpdatePermission();
                    reply.writeNoException();
                    reply.writeInt(_result ? 1 : 0);
                    return true;
                case TRANSACTION_SetUpdateOverCellularPermission /*14*/:
                    data.enforceInterface(DESCRIPTOR);
                    SetUpdateOverCellularPermission(data.readInt() != 0);
                    reply.writeNoException();
                    return true;
                case TRANSACTION_GetUpdateOverCellularPermission /*15*/:
                    data.enforceInterface(DESCRIPTOR);
                    _result = GetUpdateOverCellularPermission();
                    reply.writeNoException();
                    reply.writeInt(_result ? 1 : 0);
                    return true;
                case 16:
                    data.enforceInterface(DESCRIPTOR);
                    long _result5 = GetDurationSinceUpdate();
                    reply.writeNoException();
                    reply.writeLong(_result5);
                    return true;
                case 17:
                    data.enforceInterface(DESCRIPTOR);
                    _result2 = GetPrevVersion();
                    reply.writeNoException();
                    reply.writeString(_result2);
                    return true;
                case TRANSACTION_GetRollbackPartition /*18*/:
                    data.enforceInterface(DESCRIPTOR);
                    _result2 = GetRollbackPartition();
                    reply.writeNoException();
                    reply.writeString(_result2);
                    return true;
                case TRANSACTION_RegisterStatusCallback /*19*/:
                    data.enforceInterface(DESCRIPTOR);
                    RegisterStatusCallback(android.brillo.IUpdateEngineStatusCallback.Stub.asInterface(data.readStrongBinder()));
                    reply.writeNoException();
                    return true;
                case 20:
                    data.enforceInterface(DESCRIPTOR);
                    _result3 = GetLastAttemptError();
                    reply.writeNoException();
                    reply.writeInt(_result3);
                    return true;
                case 21:
                    data.enforceInterface(DESCRIPTOR);
                    _result3 = GetEolStatus();
                    reply.writeNoException();
                    reply.writeInt(_result3);
                    return true;
                case 1598968902:
                    reply.writeString(DESCRIPTOR);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }
    }

    void AttemptRollback(boolean z) throws RemoteException;

    boolean AttemptUpdate(String str, String str2, int i) throws RemoteException;

    boolean CanRollback() throws RemoteException;

    String GetChannel(boolean z) throws RemoteException;

    String GetCohortHint() throws RemoteException;

    long GetDurationSinceUpdate() throws RemoteException;

    int GetEolStatus() throws RemoteException;

    int GetLastAttemptError() throws RemoteException;

    boolean GetP2PUpdatePermission() throws RemoteException;

    String GetPrevVersion() throws RemoteException;

    String GetRollbackPartition() throws RemoteException;

    ParcelableUpdateEngineStatus GetStatus() throws RemoteException;

    boolean GetUpdateOverCellularPermission() throws RemoteException;

    void RebootIfNeeded() throws RemoteException;

    void RegisterStatusCallback(IUpdateEngineStatusCallback iUpdateEngineStatusCallback) throws RemoteException;

    void ResetStatus() throws RemoteException;

    void SetChannel(String str, boolean z) throws RemoteException;

    void SetCohortHint(String str) throws RemoteException;

    void SetP2PUpdatePermission(boolean z) throws RemoteException;

    void SetUpdateAttemptFlags(int i) throws RemoteException;

    void SetUpdateOverCellularPermission(boolean z) throws RemoteException;
}
