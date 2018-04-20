package com.android.server.lowpan;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.ConnectivityManager.NetworkCallback;
import android.net.NetworkRequest.Builder;
import android.net.lowpan.ILowpanInterface;
import android.net.lowpan.ILowpanManager.Stub;
import android.net.lowpan.ILowpanManagerListener;
import android.os.Binder;
import android.os.HandlerThread;
import android.os.IBinder.DeathRecipient;
import android.os.Looper;
import android.os.RemoteException;
import android.util.Log;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

public class LowpanServiceImpl extends Stub {
    private static final String TAG = LowpanServiceImpl.class.getSimpleName();
    private final Context mContext;
    private final HandlerThread mHandlerThread = new HandlerThread("LowpanServiceThread");
    private final Map<String, LowpanInterfaceTracker> mInterfaceMap = new HashMap();
    private final boolean mIsAndroidThings;
    private final Set<ILowpanManagerListener> mListenerSet = new HashSet();
    private final AtomicBoolean mStarted = new AtomicBoolean(false);

    public LowpanServiceImpl(Context context) {
        this.mContext = context;
        this.mIsAndroidThings = context.getPackageManager().hasSystemFeature("android.hardware.type.embedded");
    }

    public Looper getLooper() {
        Looper looper = this.mHandlerThread.getLooper();
        if (looper != null) {
            return looper;
        }
        this.mHandlerThread.start();
        return this.mHandlerThread.getLooper();
    }

    public void createOutstandingNetworkRequest() {
        ConnectivityManager cm = (ConnectivityManager) this.mContext.getSystemService("connectivity");
        if (cm == null) {
            throw new IllegalStateException("Bad luck, ConnectivityService not started.");
        }
        cm.requestNetwork(new Builder().clearCapabilities().addTransportType(6).build(), new NetworkCallback());
    }

    public void checkAndStartLowpan() {
        synchronized (this.mInterfaceMap) {
            if (this.mStarted.compareAndSet(false, true)) {
                for (Entry<String, LowpanInterfaceTracker> entry : this.mInterfaceMap.entrySet()) {
                    ((LowpanInterfaceTracker) entry.getValue()).register();
                }
            }
        }
        createOutstandingNetworkRequest();
    }

    private void enforceAccessPermission() {
        try {
            this.mContext.enforceCallingOrSelfPermission("android.permission.ACCESS_LOWPAN_STATE", "LowpanService");
        } catch (SecurityException x) {
            if (this.mIsAndroidThings) {
                this.mContext.enforceCallingOrSelfPermission("com.google.android.things.permission.ACCESS_LOWPAN_STATE", "LowpanService");
                return;
            }
            throw x;
        }
    }

    private void enforceManagePermission() {
        try {
            this.mContext.enforceCallingOrSelfPermission("android.permission.MANAGE_LOWPAN_INTERFACES", "LowpanService");
        } catch (SecurityException x) {
            if (this.mIsAndroidThings) {
                this.mContext.enforceCallingOrSelfPermission("com.google.android.things.permission.MANAGE_LOWPAN_INTERFACES", "LowpanService");
                return;
            }
            throw x;
        }
    }

    public ILowpanInterface getInterface(String name) {
        ILowpanInterface iface = null;
        enforceAccessPermission();
        synchronized (this.mInterfaceMap) {
            LowpanInterfaceTracker tracker = (LowpanInterfaceTracker) this.mInterfaceMap.get(name);
            if (tracker != null) {
                iface = tracker.mILowpanInterface;
            }
        }
        return iface;
    }

    public String[] getInterfaceList() {
        String[] strArr;
        enforceAccessPermission();
        synchronized (this.mInterfaceMap) {
            strArr = (String[]) this.mInterfaceMap.keySet().toArray(new String[this.mInterfaceMap.size()]);
        }
        return strArr;
    }

    private void onInterfaceRemoved(ILowpanInterface lowpanInterface, String name) {
        Log.i(TAG, "Removed LoWPAN interface `" + name + "` (" + lowpanInterface.toString() + ")");
        synchronized (this.mListenerSet) {
            for (ILowpanManagerListener listener : this.mListenerSet) {
                try {
                    listener.onInterfaceRemoved(lowpanInterface);
                } catch (Exception x) {
                    Log.e(TAG, "Exception caught: " + x);
                }
            }
        }
    }

    private void onInterfaceAdded(ILowpanInterface lowpanInterface, String name) {
        Log.i(TAG, "Added LoWPAN interface `" + name + "` (" + lowpanInterface.toString() + ")");
        synchronized (this.mListenerSet) {
            for (ILowpanManagerListener listener : this.mListenerSet) {
                try {
                    listener.onInterfaceAdded(lowpanInterface);
                } catch (Exception x) {
                    Log.e(TAG, "Exception caught: " + x);
                }
            }
        }
    }

    public void addInterface(final ILowpanInterface lowpanInterface) {
        enforceManagePermission();
        try {
            LowpanInterfaceTracker previous;
            LowpanInterfaceTracker agent;
            Binder.allowBlocking(lowpanInterface.asBinder());
            final String name = lowpanInterface.getName();
            lowpanInterface.asBinder().linkToDeath(new DeathRecipient() {
                public void binderDied() {
                    Log.w(LowpanServiceImpl.TAG, "LoWPAN interface `" + name + "` (" + lowpanInterface.toString() + ") died.");
                    LowpanServiceImpl.this.removeInterface(lowpanInterface);
                }
            }, 0);
            synchronized (this.mInterfaceMap) {
                previous = (LowpanInterfaceTracker) this.mInterfaceMap.get(name);
                agent = new LowpanInterfaceTracker(this.mContext, lowpanInterface, getLooper());
                this.mInterfaceMap.put(name, agent);
            }
            if (previous != null) {
                previous.unregister();
                onInterfaceRemoved(previous.mILowpanInterface, name);
            }
            if (this.mStarted.get()) {
                agent.register();
            }
            onInterfaceAdded(lowpanInterface, name);
        } catch (Exception x) {
            Log.e(TAG, "Exception caught: " + x);
        }
    }

    private void removeInterfaceByName(String name) {
        enforceManagePermission();
        if (name != null) {
            synchronized (this.mInterfaceMap) {
                LowpanInterfaceTracker agent = (LowpanInterfaceTracker) this.mInterfaceMap.get(name);
                if (agent == null) {
                    return;
                }
                ILowpanInterface lowpanInterface = agent.mILowpanInterface;
                if (this.mStarted.get()) {
                    agent.unregister();
                }
                this.mInterfaceMap.remove(name);
                onInterfaceRemoved(lowpanInterface, name);
            }
        }
    }

    public void removeInterface(ILowpanInterface lowpanInterface) {
        String name = null;
        try {
            name = lowpanInterface.getName();
        } catch (RemoteException e) {
            synchronized (this.mInterfaceMap) {
                for (Entry<String, LowpanInterfaceTracker> entry : this.mInterfaceMap.entrySet()) {
                    if (((LowpanInterfaceTracker) entry.getValue()).mILowpanInterface == lowpanInterface) {
                        name = (String) entry.getKey();
                        break;
                    }
                }
            }
        }
        removeInterfaceByName(name);
    }

    public void addListener(final ILowpanManagerListener listener) {
        enforceAccessPermission();
        synchronized (this.mListenerSet) {
            if (!this.mListenerSet.contains(listener)) {
                try {
                    listener.asBinder().linkToDeath(new DeathRecipient() {
                        public void binderDied() {
                            synchronized (LowpanServiceImpl.this.mListenerSet) {
                                LowpanServiceImpl.this.mListenerSet.remove(listener);
                            }
                        }
                    }, 0);
                    this.mListenerSet.add(listener);
                } catch (RemoteException x) {
                    Log.e(TAG, "Exception caught: " + x);
                }
            }
        }
    }

    public void removeListener(ILowpanManagerListener listener) {
        enforceAccessPermission();
        synchronized (this.mListenerSet) {
            this.mListenerSet.remove(listener);
        }
    }
}
