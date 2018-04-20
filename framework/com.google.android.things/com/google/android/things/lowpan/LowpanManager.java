package com.google.android.things.lowpan;

import android.net.lowpan.ILowpanManager;
import android.net.lowpan.LowpanException;
import android.net.lowpan.LowpanInterface;
import android.os.Handler;
import android.util.Log;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;
import java.util.WeakHashMap;

public class LowpanManager {
    static final String ACCESS_LOWPAN_STATE = "com.google.android.things.permission.ACCESS_LOWPAN_STATE";
    static final String CHANGE_LOWPAN_STATE = "com.google.android.things.permission.CHANGE_LOWPAN_STATE";
    static final String MANAGE_LOWPAN_INTERFACES = "com.google.android.things.permission.MANAGE_LOWPAN_INTERFACES";
    static final String READ_LOWPAN_CREDENTIAL = "com.google.android.things.permission.READ_LOWPAN_CREDENTIAL";
    private static final String TAG = LowpanManager.class.getSimpleName();
    private final android.net.lowpan.LowpanManager mActualLowpanManager;
    private final Map<LowpanInterface, WeakReference<LowpanInterface>> mBinderCache = new WeakHashMap();
    private final Map<Callback, CallbackInternal> mListenerMap = new HashMap();

    public static abstract class Callback {
        public void onInterfaceAdded(LowpanInterface lowpan_interface) {
        }

        public void onInterfaceRemoved(LowpanInterface lowpan_interface) {
        }
    }

    private static class CallbackInternal extends android.net.lowpan.LowpanManager.Callback {
        final LowpanManager mLowpanManager;
        final Callback mUserCallbackInstance;

        CallbackInternal(LowpanManager lowpanManager, Callback userCallbackInstance) {
            this.mLowpanManager = lowpanManager;
            this.mUserCallbackInstance = userCallbackInstance;
        }

        public void onInterfaceAdded(LowpanInterface ifaceActual) {
            this.mUserCallbackInstance.onInterfaceAdded(this.mLowpanManager.getInterface(ifaceActual));
        }

        public void onInterfaceRemoved(LowpanInterface ifaceActual) {
            this.mUserCallbackInstance.onInterfaceRemoved(this.mLowpanManager.getInterface(ifaceActual));
        }
    }

    public static LowpanManager getManager() {
        android.net.lowpan.LowpanManager actualLowpanManager = android.net.lowpan.LowpanManager.getManager();
        if (actualLowpanManager == null) {
            return null;
        }
        return new LowpanManager(actualLowpanManager);
    }

    LowpanManager(android.net.lowpan.LowpanManager actualLowpanManager) {
        this.mActualLowpanManager = actualLowpanManager;
    }

    LowpanManager(ILowpanManager lowpanService) {
        this.mActualLowpanManager = new android.net.lowpan.LowpanManager(null, lowpanService, null);
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    com.google.android.things.lowpan.LowpanInterface getInterface(android.net.lowpan.LowpanInterface r8) {
        /*
        r7 = this;
        r1 = 0;
        r5 = r7.mBinderCache;
        monitor-enter(r5);
        r4 = r7.mBinderCache;	 Catch:{ all -> 0x002a }
        r3 = r4.get(r8);	 Catch:{ all -> 0x002a }
        r3 = (java.lang.ref.WeakReference) r3;	 Catch:{ all -> 0x002a }
        if (r3 == 0) goto L_0x0032;
    L_0x000e:
        r4 = r3.get();	 Catch:{ all -> 0x002a }
        r0 = r4;
        r0 = (com.google.android.things.lowpan.LowpanInterface) r0;	 Catch:{ all -> 0x002a }
        r1 = r0;
        r2 = r1;
    L_0x0017:
        if (r2 != 0) goto L_0x0030;
    L_0x0019:
        r1 = new com.google.android.things.lowpan.LowpanInterface;	 Catch:{ all -> 0x002d }
        r1.<init>(r8);	 Catch:{ all -> 0x002d }
        r4 = r7.mBinderCache;	 Catch:{ all -> 0x002a }
        r6 = new java.lang.ref.WeakReference;	 Catch:{ all -> 0x002a }
        r6.<init>(r1);	 Catch:{ all -> 0x002a }
        r4.put(r8, r6);	 Catch:{ all -> 0x002a }
    L_0x0028:
        monitor-exit(r5);
        return r1;
    L_0x002a:
        r4 = move-exception;
    L_0x002b:
        monitor-exit(r5);
        throw r4;
    L_0x002d:
        r4 = move-exception;
        r1 = r2;
        goto L_0x002b;
    L_0x0030:
        r1 = r2;
        goto L_0x0028;
    L_0x0032:
        r2 = r1;
        goto L_0x0017;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.things.lowpan.LowpanManager.getInterface(android.net.lowpan.LowpanInterface):com.google.android.things.lowpan.LowpanInterface");
    }

    public LowpanInterface getInterface(String name) {
        LowpanInterface ifaceActual = this.mActualLowpanManager.getInterface(name);
        if (ifaceActual != null) {
            return getInterface(ifaceActual);
        }
        return null;
    }

    public LowpanInterface getInterface() {
        LowpanInterface ifaceActual = null;
        if (this.mActualLowpanManager != null) {
            ifaceActual = this.mActualLowpanManager.getInterface();
        }
        if (ifaceActual != null) {
            return getInterface(ifaceActual);
        }
        return null;
    }

    public String[] getInterfaceList() {
        if (this.mActualLowpanManager != null) {
            return this.mActualLowpanManager.getInterfaceList();
        }
        return new String[0];
    }

    public void registerCallback(Callback cb, Handler handler) throws LowpanException {
        if (this.mListenerMap.containsKey(cb)) {
            Log.w(TAG, "Attempt to register already registered callback " + cb);
            return;
        }
        CallbackInternal callbackInternal = new CallbackInternal(this, cb);
        try {
            this.mActualLowpanManager.registerCallback(callbackInternal, handler);
            synchronized (this.mListenerMap) {
                this.mListenerMap.put(cb, callbackInternal);
            }
        } catch (LowpanException x) {
            throw LowpanException.createFromActual(x);
        }
    }

    public void registerCallback(Callback cb) throws LowpanException {
        registerCallback(cb, null);
    }

    public void unregisterCallback(Callback cb) {
        synchronized (this.mListenerMap) {
            CallbackInternal callbackInternal = (CallbackInternal) this.mListenerMap.remove(cb);
        }
        if (callbackInternal != null) {
            this.mActualLowpanManager.unregisterCallback(callbackInternal);
        } else {
            Log.w(TAG, "Attempt to unregister callback " + cb);
        }
    }
}
