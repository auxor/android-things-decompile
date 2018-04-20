package com.google.android.things.pio;

import android.os.IBinder;
import android.os.RemoteException;
import java.io.Closeable;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;
import libcore.io.IoUtils;

class RegisteredBusDriver<TBus extends Closeable, K, TDevice extends Closeable> implements RegisteredDriver {
    private final TBus mBus;
    private final Map<K, RegisteredDeviceDriver<TDevice>> mDevices = new HashMap();
    private final ReentrantLock mLock = new ReentrantLock();
    private final String mName;
    private boolean mUnregistered = false;

    interface OpenMethods<TBus, K, TDevice> {
        void openBus(TBus tBus) throws IOException;

        TDevice openDevice(TBus tBus, K k) throws IOException;
    }

    public RegisteredBusDriver(TBus bus, String name) {
        this.mBus = bus;
        this.mName = name;
    }

    public String getName() {
        return this.mName;
    }

    public String getDeviceName(K key) {
        return this.mName + "." + key.toString();
    }

    public DriverLock<TDevice> lockDevice(IBinder owner, K key) throws PioException {
        this.mLock.lock();
        try {
            if (this.mUnregistered) {
                throw PioException.createUnregisteredIoException(this.mName);
            }
            RegisteredDeviceDriver<TDevice> registeredDevice = (RegisteredDeviceDriver) this.mDevices.get(key);
            if (registeredDevice == null) {
                throw PioException.createNotOwnedException(getDeviceName(key));
            }
            DriverLock<TDevice> lockDriver = registeredDevice.lockDriver(owner);
            return lockDriver;
        } finally {
            this.mLock.unlock();
        }
    }

    public void openDevice(IBinder owner, K key, OpenMethods<TBus, K, TDevice> openMethods) throws RemoteException, IOException {
        this.mLock.lock();
        try {
            if (this.mUnregistered) {
                throw PioException.createUnregisteredIoException(this.mName);
            } else if (this.mDevices.containsKey(key)) {
                throw PioException.createInUseException(((RegisteredDeviceDriver) this.mDevices.get(key)).getName());
            } else {
                if (this.mDevices.isEmpty()) {
                    openMethods.openBus(this.mBus);
                }
                RegisteredDeviceDriver<TDevice> registeredDevice = new RegisteredDeviceDriver((Closeable) openMethods.openDevice(this.mBus, key), getDeviceName(key));
                registeredDevice.openDriver(owner, null);
                this.mDevices.put(key, registeredDevice);
                this.mLock.unlock();
            }
        } catch (Exception e) {
            if (this.mDevices.isEmpty()) {
                IoUtils.closeQuietly(this.mBus);
            }
            throw e;
        } catch (Throwable th) {
            this.mLock.unlock();
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void closeDevice(android.os.IBinder r7, K r8) throws java.io.IOException {
        /*
        r6 = this;
        r3 = 0;
        r0 = 0;
        r0 = r6.lockDevice(r7, r8);	 Catch:{ Throwable -> 0x0035, all -> 0x0052 }
        r2 = r6.mDevices;	 Catch:{ Throwable -> 0x0035, all -> 0x0052 }
        r1 = r2.remove(r8);	 Catch:{ Throwable -> 0x0035, all -> 0x0052 }
        r1 = (com.google.android.things.pio.RegisteredDeviceDriver) r1;	 Catch:{ Throwable -> 0x0035, all -> 0x0052 }
        r1.closeDriver(r7);	 Catch:{ all -> 0x0026 }
        r2 = r6.mDevices;	 Catch:{ Throwable -> 0x0035, all -> 0x0052 }
        r2 = r2.isEmpty();	 Catch:{ Throwable -> 0x0035, all -> 0x0052 }
        if (r2 == 0) goto L_0x001e;
    L_0x0019:
        r2 = r6.mBus;	 Catch:{ Throwable -> 0x0035, all -> 0x0052 }
        libcore.io.IoUtils.closeQuietly(r2);	 Catch:{ Throwable -> 0x0035, all -> 0x0052 }
    L_0x001e:
        if (r0 == 0) goto L_0x0023;
    L_0x0020:
        r0.close();	 Catch:{ Throwable -> 0x0043 }
    L_0x0023:
        if (r3 == 0) goto L_0x0051;
    L_0x0025:
        throw r3;
    L_0x0026:
        r2 = move-exception;
        r4 = r6.mDevices;	 Catch:{ Throwable -> 0x0035, all -> 0x0052 }
        r4 = r4.isEmpty();	 Catch:{ Throwable -> 0x0035, all -> 0x0052 }
        if (r4 == 0) goto L_0x0034;
    L_0x002f:
        r4 = r6.mBus;	 Catch:{ Throwable -> 0x0035, all -> 0x0052 }
        libcore.io.IoUtils.closeQuietly(r4);	 Catch:{ Throwable -> 0x0035, all -> 0x0052 }
    L_0x0034:
        throw r2;	 Catch:{ Throwable -> 0x0035, all -> 0x0052 }
    L_0x0035:
        r2 = move-exception;
        throw r2;	 Catch:{ all -> 0x0037 }
    L_0x0037:
        r3 = move-exception;
        r5 = r3;
        r3 = r2;
        r2 = r5;
    L_0x003b:
        if (r0 == 0) goto L_0x0040;
    L_0x003d:
        r0.close();	 Catch:{ Throwable -> 0x0045 }
    L_0x0040:
        if (r3 == 0) goto L_0x0050;
    L_0x0042:
        throw r3;
    L_0x0043:
        r3 = move-exception;
        goto L_0x0023;
    L_0x0045:
        r4 = move-exception;
        if (r3 != 0) goto L_0x004a;
    L_0x0048:
        r3 = r4;
        goto L_0x0040;
    L_0x004a:
        if (r3 == r4) goto L_0x0040;
    L_0x004c:
        r3.addSuppressed(r4);
        goto L_0x0040;
    L_0x0050:
        throw r2;
    L_0x0051:
        return;
    L_0x0052:
        r2 = move-exception;
        goto L_0x003b;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.things.pio.RegisteredBusDriver.closeDevice(android.os.IBinder, java.lang.Object):void");
    }

    public void unregister() throws IOException {
        this.mLock.lock();
        List<IOException> deviceCloseExceptions;
        try {
            deviceCloseExceptions = new LinkedList();
            for (RegisteredDeviceDriver<TDevice> registeredDevice : this.mDevices.values()) {
                registeredDevice.unregister();
            }
            this.mBus.close();
            if (deviceCloseExceptions.isEmpty()) {
                this.mUnregistered = true;
                this.mLock.unlock();
                return;
            }
            throw new IOException(deviceCloseExceptions.size() + " device(s) failed to close", (Throwable) deviceCloseExceptions.get(0));
        } catch (IOException e) {
            deviceCloseExceptions.add(e);
        } catch (Throwable th) {
            this.mUnregistered = true;
            this.mLock.unlock();
        }
    }
}
