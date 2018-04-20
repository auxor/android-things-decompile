package com.google.android.things.userdriver;

import android.os.IBinder.DeathRecipient;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.util.Log;
import com.google.android.things.userdriver.IEffectDriverManager.Stub;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

class UserEffectDriverManager {
    private static final String TAG = "UserEffectDriverManager";
    private final DeathRecipient mDeathWatcher = new DeathRecipient() {
        public void binderDied() {
            synchronized (UserEffectDriverManager.this) {
                UserEffectDriverManager.this.mService = null;
                Set<UserEffectDriverFactory> disconnectedFactories = UserEffectDriverManager.this.mFactories.keySet();
                UserEffectDriverManager.this.mFactories = new ConcurrentHashMap();
            }
            for (UserEffectDriverFactory factory : disconnectedFactories) {
                factory.onError();
            }
        }
    };
    private Map<UserEffectDriverFactory, WrappedEffectDriverFactory> mFactories = new ConcurrentHashMap();
    private IEffectDriverManager mService;

    UserEffectDriverManager() {
    }

    public synchronized void addFactory(UserEffectDriverFactory factory, int type) {
        initService();
        if (factory == null) {
            throw new IllegalArgumentException("Cannot register a null factory.");
        }
        WrappedEffectDriverFactory wrappedFactory = (WrappedEffectDriverFactory) this.mFactories.get(factory);
        if (wrappedFactory == null) {
            wrappedFactory = new WrappedEffectDriverFactory(factory);
        }
        try {
            this.mService.registerFactory(wrappedFactory, type);
            this.mFactories.put(factory, wrappedFactory);
        } catch (RemoteException e) {
            e.rethrowAsRuntimeException();
        }
    }

    public synchronized void removeFactory(UserEffectDriverFactory factory) {
        initService();
        if (factory == null) {
            throw new IllegalArgumentException("Cannot unregister a null factory.");
        }
        try {
            WrappedEffectDriverFactory wrappedFactory = (WrappedEffectDriverFactory) this.mFactories.remove(factory);
            if (wrappedFactory == null) {
                Log.w(TAG, "Unable to unregister factory that isn't registered.");
                return;
            }
            this.mService.unregisterFactory(wrappedFactory);
        } catch (RemoteException e) {
            e.rethrowAsRuntimeException();
        }
    }

    private void initService() {
        if (this.mService == null) {
            this.mService = Stub.asInterface(ServiceManager.getService("audioeffectsdriverservice"));
            if (this.mService == null) {
                throw new IllegalStateException("UserEffectDriverManager cannot connect to the user effects service");
            }
            try {
                this.mService.asBinder().linkToDeath(this.mDeathWatcher, 0);
            } catch (RemoteException e) {
                this.mService = null;
                throw new IllegalStateException("UserEffectDriverManager cannot link to the user effects service");
            }
        }
    }

    void setServiceForTesting(IEffectDriverManager service) {
        this.mService = service;
    }

    void killServiceForTesting() {
        this.mDeathWatcher.binderDied();
    }
}
