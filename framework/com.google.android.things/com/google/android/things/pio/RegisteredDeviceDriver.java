package com.google.android.things.pio;

import android.os.IBinder;
import android.os.IBinder.DeathRecipient;
import android.os.RemoteException;
import android.os.ServiceSpecificException;
import java.io.Closeable;
import java.io.IOException;
import java.util.concurrent.locks.ReentrantLock;

class RegisteredDeviceDriver<T extends Closeable> implements RegisteredDriver {
    private final DeathRecipient mDeathWatcher = new DeathRecipient() {
        public void binderDied() {
            try {
                RegisteredDeviceDriver.this.closeDriver(RegisteredDeviceDriver.this.mOwner);
            } catch (ServiceSpecificException e) {
            }
        }
    };
    private final T mDriver;
    private final DriverLock<T> mDriverLock = new DriverLock<T>() {
        public T getDriver() {
            return RegisteredDeviceDriver.this.mDriver;
        }

        public void close() {
            RegisteredDeviceDriver.this.mLock.unlock();
        }
    };
    private final ReentrantLock mLock;
    private final String mName;
    private IBinder mOwner;
    private boolean mUnregistered = false;

    interface OpenMethod<T> {
        void call(T t) throws IOException;
    }

    public RegisteredDeviceDriver(T driver, String name) {
        this.mDriver = driver;
        this.mName = name;
        this.mLock = new ReentrantLock();
    }

    public RegisteredDeviceDriver(T driver, String name, ReentrantLock mBusLock) {
        this.mDriver = driver;
        this.mName = name;
        this.mLock = mBusLock;
    }

    public String getName() {
        return this.mName;
    }

    public DriverLock<T> lockDriver(IBinder owner) throws PioException {
        this.mLock.lock();
        try {
            if (this.mUnregistered) {
                throw PioException.createUnregisteredIoException(this.mName);
            } else if (this.mOwner != owner) {
                throw PioException.createNotOwnedException(this.mName);
            } else {
                this.mLock.lock();
                DriverLock<T> driverLock = this.mDriverLock;
                return driverLock;
            }
        } finally {
            this.mLock.unlock();
        }
    }

    public void openDriver(IBinder owner, OpenMethod<T> openMethod) throws RemoteException, IOException {
        this.mLock.lock();
        try {
            if (this.mUnregistered) {
                throw PioException.createUnregisteredIoException(this.mName);
            } else if (this.mOwner != null) {
                throw PioException.createInUseException(this.mName);
            } else {
                owner.linkToDeath(this.mDeathWatcher, 0);
                if (openMethod != null) {
                    openMethod.call(this.mDriver);
                }
                this.mOwner = owner;
                this.mLock.unlock();
            }
        } catch (IOException e) {
            owner.unlinkToDeath(this.mDeathWatcher, 0);
            throw e;
        } catch (Throwable th) {
            this.mLock.unlock();
        }
    }

    public void closeDriver(IBinder owner) throws IOException {
        Throwable th;
        Throwable th2 = null;
        DriverLock driverLock = null;
        try {
            DriverLock<T> lockedDriver = lockDriver(owner);
            if (lockedDriver.getCallback() != null) {
                lockedDriver.getCallback().close();
            }
            this.mOwner.unlinkToDeath(this.mDeathWatcher, 0);
            this.mOwner = null;
            this.mDriver.close();
            if (lockedDriver != null) {
                try {
                    lockedDriver.close();
                } catch (Throwable th3) {
                    th2 = th3;
                }
            }
            if (th2 != null) {
                throw th2;
            }
            return;
        } catch (Throwable th22) {
            Throwable th4 = th22;
            th22 = th;
            th = th4;
        }
        if (driverLock != null) {
            try {
                driverLock.close();
            } catch (Throwable th5) {
                if (th22 == null) {
                    th22 = th5;
                } else if (th22 != th5) {
                    th22.addSuppressed(th5);
                }
            }
        }
        if (th22 != null) {
            throw th22;
        }
        throw th;
    }

    public void unregister() throws IOException {
        this.mLock.lock();
        try {
            if (this.mOwner != null) {
                closeDriver(this.mOwner);
            }
            this.mUnregistered = true;
            this.mLock.unlock();
        } catch (Throwable th) {
            this.mUnregistered = true;
            this.mLock.unlock();
        }
    }
}
