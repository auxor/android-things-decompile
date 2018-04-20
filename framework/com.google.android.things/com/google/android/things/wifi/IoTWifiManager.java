package com.google.android.things.wifi;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.wifi.WifiConfiguration;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.SystemProperties;
import android.util.Log;
import com.google.android.things.wifi.IIoTWifiManager.Stub;

public final class IoTWifiManager {
    private static final String SERVICE_CLASS = "com.google.android.things.internal.wifi.IoTWifiService";
    private static final ComponentName SERVICE_COMPONENT = new ComponentName(SERVICE_PACKAGE, SERVICE_CLASS);
    private static final String SERVICE_PACKAGE = "com.google.android.things.internal.wifi";
    private static final String TAG = IoTWifiManager.class.getSimpleName();
    private final boolean mBindSuccess;
    private final Callback mCallback;
    private final Context mContext;
    private final ServiceConnection mServiceConnection = new ServiceConnection() {
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.d(IoTWifiManager.TAG, "onServiceConnected()");
            IoTWifiManager.this.mWifiService = Stub.asInterface(service);
            if (IoTWifiManager.this.mWifiService == null) {
                Log.e(IoTWifiManager.TAG, "Unable to connect to IIoTWifiManager");
            } else {
                IoTWifiManager.this.mCallback.onConnected(IoTWifiManager.this);
            }
        }

        public void onServiceDisconnected(ComponentName name) {
            Log.d(IoTWifiManager.TAG, "onServiceDisconnected() - ");
            IoTWifiManager.this.mContext.unbindService(this);
            IoTWifiManager.this.mWifiService = null;
            IoTWifiManager.this.mCallback.onDisconnected();
        }
    };
    private IIoTWifiManager mWifiService;

    public interface Callback {
        void onConnected(IoTWifiManager ioTWifiManager);

        void onDisconnected();
    }

    public static boolean connect(Context context, Callback callback) {
        return new IoTWifiManager(context, callback).mBindSuccess;
    }

    private static boolean doesInternalServiceExist(Context context) {
        try {
            context.getPackageManager().getServiceInfo(SERVICE_COMPONENT, 1048576);
            return true;
        } catch (NameNotFoundException e) {
            return false;
        }
    }

    private IoTWifiManager(Context context, Callback callback) {
        this.mContext = context;
        this.mCallback = callback;
        if (doesInternalServiceExist(context)) {
            Intent intent = new Intent(IIoTWifiManager.class.getName());
            intent.setComponent(SERVICE_COMPONENT);
            this.mBindSuccess = context.bindService(intent, this.mServiceConnection, 1);
            if (!this.mBindSuccess) {
                Log.e(TAG, "Failed to bind to service: " + intent.toString());
                context.unbindService(this.mServiceConnection);
            }
            return;
        }
        Log.e(TAG, "IoTWifiManager is not supported on this build");
        this.mBindSuccess = false;
    }

    public static boolean isConcurrentHotspotSupported(Context context) {
        if (SystemProperties.getBoolean("ro.iot.concurrent_hotspot", false)) {
            return doesInternalServiceExist(context);
        }
        return false;
    }

    public boolean isConcurrentHotspotRunning() {
        if (!isConcurrentHotspotSupported(this.mContext)) {
            return false;
        }
        if (this.mWifiService == null) {
            throw new IllegalStateException("Manager is disconnected from service");
        }
        try {
            return this.mWifiService.isConcurrentHotspotRunning();
        } catch (RemoteException e) {
            throw e.rethrowAsRuntimeException();
        }
    }

    public boolean startConcurrentHotspot(WifiConfiguration config) {
        if (!isConcurrentHotspotSupported(this.mContext)) {
            throw new UnsupportedOperationException("Concurrent hotspot is not supported on this device");
        } else if (this.mWifiService == null) {
            throw new IllegalStateException("Manager is disconnected from service");
        } else {
            try {
                return this.mWifiService.startConcurrentHotspot(config);
            } catch (RemoteException e) {
                throw e.rethrowAsRuntimeException();
            }
        }
    }

    public boolean stopConcurrentHotspot() {
        if (!isConcurrentHotspotSupported(this.mContext)) {
            throw new UnsupportedOperationException("Concurrent hotspot is not supported on this device");
        } else if (this.mWifiService == null) {
            throw new IllegalStateException("Manager is disconnected from service");
        } else {
            try {
                return this.mWifiService.stopConcurrentHotspot();
            } catch (RemoteException e) {
                throw e.rethrowAsRuntimeException();
            }
        }
    }

    public void close() {
        if (this.mWifiService != null) {
            this.mContext.unbindService(this.mServiceConnection);
            this.mWifiService = null;
        }
    }
}
