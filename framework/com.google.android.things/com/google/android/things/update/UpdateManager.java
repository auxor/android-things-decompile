package com.google.android.things.update;

import android.os.IBinder;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.util.Log;
import com.android.internal.util.Preconditions;
import com.google.android.things.internal.update.IUpdateManagementService;
import com.google.android.things.internal.update.IUpdateManagementService.Stub;
import com.google.android.things.internal.update.IUpdateManagerStatusListener;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class UpdateManager {
    private static final String BLOCK_UPDATES_PERMISSION = "com.google.android.things.permission.BLOCK_UPDATES";
    private static final String MANAGE_UPDATE_POLICY_PERMISSION = "com.google.android.things.permission.MANAGE_UPDATE_POLICY";
    private static final String PERFORM_UPDATE_NOW_PERMISSION = "com.google.android.things.permission.PERFORM_UPDATE_NOW";
    public static final int POLICY_APPLY_AND_REBOOT = 1;
    public static final int POLICY_APPLY_ONLY = 2;
    public static final int POLICY_CHECKS_ONLY = 3;
    public static final int POLICY_UNSPECIFIED = 0;
    private static final String TAG = UpdateManager.class.getSimpleName();
    private static final String UPDATE_MANAGER_SERVICE_NAME = "updatemanagementservice";
    private final Set<StatusListener> mStatusListeners = new HashSet();
    private IUpdateManagementService mUpdateManagementService;

    @Retention(RetentionPolicy.SOURCE)
    public @interface Policy {
    }

    public UpdateManager() {
        connectToUpdateEngine();
    }

    UpdateManager(IUpdateManagementService updateManagementService) {
        this.mUpdateManagementService = updateManagementService;
    }

    private void connectToUpdateEngine() {
        IBinder umBinder = ServiceManager.getService(UPDATE_MANAGER_SERVICE_NAME);
        if (umBinder == null) {
            Log.e(TAG, "Service not initialized");
            throw new IllegalStateException("Unable to connect to UpdateManager");
        }
        this.mUpdateManagementService = Stub.asInterface(umBinder);
        Log.v(TAG, "Service reference initialized");
        try {
            this.mUpdateManagementService.addListener(new IUpdateManagerStatusListener.Stub() {
                public void onStatusUpdate(UpdateManagerStatus status) {
                    UpdateManager.this.dispatchToListeners(status);
                }
            });
        } catch (RemoteException e) {
            Log.e(TAG, "Unable to register listener with UpdateManagementService", e);
            e.rethrowAsRuntimeException();
        }
    }

    void dispatchToListeners(UpdateManagerStatus status) {
        int i = 0;
        StatusListener[] dispatchListeners = (StatusListener[]) this.mStatusListeners.toArray(new StatusListener[0]);
        int length = dispatchListeners.length;
        while (i < length) {
            dispatchListeners[i].onStatusUpdate(status);
            i++;
        }
    }

    public void setPolicy(int policy, long updateDeadline, TimeUnit unit) {
        Preconditions.checkNotNull(unit);
        if (updateDeadline <= 0) {
            throw new IllegalArgumentException("pendingUpdateTimeout must be greater than 0");
        }
        try {
            this.mUpdateManagementService.setPolicy(policy, unit.toMillis(updateDeadline));
        } catch (RemoteException e) {
            throw e.rethrowAsRuntimeException();
        }
    }

    public void setPolicy(UpdatePolicy policy) {
        Preconditions.checkNotNull(policy);
        try {
            this.mUpdateManagementService.setPolicy(policy.policy, policy.applyDeadline);
        } catch (RemoteException e) {
            throw e.rethrowAsRuntimeException();
        }
    }

    public void addStatusListener(StatusListener listener) {
        Preconditions.checkNotNull(listener);
        synchronized (this.mStatusListeners) {
            this.mStatusListeners.add(listener);
        }
    }

    public void removeStatusListener(StatusListener listener) {
        synchronized (this.mStatusListeners) {
            this.mStatusListeners.remove(listener);
        }
    }

    public UpdateManagerStatus getStatus() {
        try {
            return this.mUpdateManagementService.getStatus();
        } catch (RemoteException e) {
            throw e.rethrowAsRuntimeException();
        }
    }

    public boolean performUpdateNow(int policy) {
        try {
            return this.mUpdateManagementService.performUpdateNow(policy);
        } catch (RemoteException e) {
            throw e.rethrowAsRuntimeException();
        }
    }

    public void blockUpdates(long blockDuration, TimeUnit unit) {
        Preconditions.checkNotNull(unit);
        try {
            this.mUpdateManagementService.blockUpdates(unit.toMillis(blockDuration));
        } catch (RemoteException e) {
            e.rethrowAsRuntimeException();
        }
    }

    public void unblockUpdates() {
        try {
            this.mUpdateManagementService.unblockUpdates();
        } catch (RemoteException e) {
            e.rethrowAsRuntimeException();
        }
    }

    static String nameForPolicy(int policy) {
        switch (policy) {
            case 0:
                return "POLICY_UNSPECIFIED";
            case 1:
                return "POLICY_APPLY_AND_REBOOT";
            case 2:
                return "POLICY_APPLY_ONLY";
            case 3:
                return "POLICY_CHECKS_ONLY";
            default:
                return Integer.toString(policy);
        }
    }
}
