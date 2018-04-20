package com.google.android.things.pio;

import android.os.RemoteException;
import android.os.ServiceSpecificException;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

class RegisteredDriverManager<T extends RegisteredDriver> {
    private final ConcurrentMap<String, T> mDrivers = new ConcurrentHashMap();
    private final Object mRegisterLock = new Object();

    interface PioMethod {
        void call() throws ServiceSpecificException, RemoteException;
    }

    RegisteredDriverManager() {
    }

    T getRegisteredDriverOrThrow(String name) throws PioException {
        RegisteredDriver driver = (RegisteredDriver) this.mDrivers.get(name);
        if (driver != null) {
            return driver;
        }
        throw PioException.createUnknownNameException(name);
    }

    void registerDriver(String name, T driver, PioMethod registerMethod) throws IOException {
        synchronized (this.mRegisterLock) {
            try {
                registerMethod.call();
                this.mDrivers.put(name, driver);
            } catch (ServiceSpecificException e) {
                throw new PioException(e);
            } catch (RemoteException e2) {
                throw new PioException(e2);
            }
        }
    }

    void unregisterDriver(String name, PioMethod unregisterMethod) throws IOException {
        synchronized (this.mRegisterLock) {
            RegisteredDriver driver = (RegisteredDriver) this.mDrivers.get(name);
            if (driver != null) {
                try {
                    driver.unregister();
                } catch (ServiceSpecificException e) {
                    throw new PioException(e);
                } catch (RemoteException e2) {
                    throw new PioException(e2);
                } catch (ServiceSpecificException e3) {
                    throw new PioException(e3);
                } catch (RemoteException e22) {
                    throw new PioException(e22);
                } catch (Throwable th) {
                    this.mDrivers.remove(name);
                    unregisterMethod.call();
                }
            }
            this.mDrivers.remove(name);
            unregisterMethod.call();
        }
    }
}
