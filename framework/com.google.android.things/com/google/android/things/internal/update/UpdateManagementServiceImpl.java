package com.google.android.things.internal.update;

import android.brillo.IUpdateEngine;
import android.brillo.IUpdateEngineStatusCallback;
import android.brillo.ParcelableUpdateEngineStatus;
import android.content.ContextWrapper;
import android.os.IBinder;
import android.os.PowerManager;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.util.Log;
import com.android.internal.annotations.GuardedBy;
import com.google.android.things.AndroidThings;
import com.google.android.things.internal.update.IUpdateManagementService.Stub;
import com.google.android.things.update.PendingUpdateInfo;
import com.google.android.things.update.UpdateManagerStatus;
import com.google.android.things.update.VersionInfo;
import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

public class UpdateManagementServiceImpl extends Stub {
    private static final String BLOCK_UPDATES_PERMISSION = "com.google.android.things.permission.BLOCK_UPDATES";
    private static final String MANAGE_UPDATE_POLICY_PERMISSION = "com.google.android.things.permission.MANAGE_UPDATE_POLICY";
    private static final String PERFORM_UPDATE_NOW_PERMISSION = "com.google.android.things.permission.PERFORM_UPDATE_NOW";
    private static final String TAG = UpdateManagementServiceImpl.class.getSimpleName();
    private static final String UPDATE_ENGINE_SERVICE_NAME = "android.brillo.UpdateEngineService";
    static final int UPDATE_RESTRICT_DOWNLOADING = 2;
    static final int UPDATE_RESTRICT_NONE = 0;
    static final String UPDATE_STATUS_CHECKING_FOR_UPDATE = "UPDATE_STATUS_CHECKING_FOR_UPDATE";
    static final String UPDATE_STATUS_DOWNLOADING = "UPDATE_STATUS_DOWNLOADING";
    static final String UPDATE_STATUS_FINALIZING = "UPDATE_STATUS_FINALIZING";
    static final String UPDATE_STATUS_IDLE = "UPDATE_STATUS_IDLE";
    static final String UPDATE_STATUS_REPORTING_ERROR_EVENT = "UPDATE_STATUS_REPORTING_ERROR_EVENT";
    static final String UPDATE_STATUS_UPDATED_NEED_REBOOT = "UPDATE_STATUS_UPDATED_NEED_REBOOT";
    static final String UPDATE_STATUS_UPDATE_AVAILABLE = "UPDATE_STATUS_UPDATE_AVAILABLE";
    static final String UPDATE_STATUS_VERIFYING = "UPDATE_STATUS_VERIFYING";
    private static final Map<String, Integer> stateMapping = buildStateMap();
    private final ContextWrapper mContextWrapper;
    @GuardedBy("mStateLock")
    private int mCurrentPolicy = this.mSetPolicy;
    final IUpdateEngineStatusCallback mIUpdateEngineStatusCallback = new IUpdateEngineStatusCallback.Stub() {
        /* JADX WARNING: inconsistent code. */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void HandleStatusUpdate(android.brillo.ParcelableUpdateEngineStatus r9) throws android.os.RemoteException {
            /*
            r8 = this;
            r7 = 1;
            r4 = com.google.android.things.internal.update.UpdateManagementServiceImpl.TAG;
            r5 = new java.lang.StringBuilder;
            r5.<init>();
            r6 = "Received StatusUpdate from update_engine: ";
            r5 = r5.append(r6);
            r6 = r9.current_operation;
            r5 = r5.append(r6);
            r5 = r5.toString();
            android.util.Log.v(r4, r5);
            r4 = com.google.android.things.internal.update.UpdateManagementServiceImpl.this;
            r5 = r4.mStateLock;
            monitor-enter(r5);
            r4 = "UPDATE_STATUS_UPDATED_NEED_REBOOT";
            r6 = r9.current_operation;	 Catch:{ all -> 0x00e6 }
            r4 = r4.equals(r6);	 Catch:{ all -> 0x00e6 }
            if (r4 == 0) goto L_0x003f;
        L_0x0030:
            r4 = com.google.android.things.internal.update.UpdateManagementServiceImpl.this;	 Catch:{ all -> 0x00e6 }
            r4 = r4.mCurrentPolicy;	 Catch:{ all -> 0x00e6 }
            if (r4 != r7) goto L_0x003f;
        L_0x0038:
            r4 = com.google.android.things.internal.update.UpdateManagementServiceImpl.this;	 Catch:{ all -> 0x00e6 }
            r4 = r4.mRebootControl;	 Catch:{ all -> 0x00e6 }
            r4.reboot();	 Catch:{ all -> 0x00e6 }
        L_0x003f:
            r4 = r9.current_operation;	 Catch:{ all -> 0x00e6 }
            r6 = "UPDATE_STATUS_CHECKING_FOR_UPDATE";
            r6 = r4.equals(r6);	 Catch:{ all -> 0x00e6 }
            if (r6 == 0) goto L_0x00ab;
        L_0x004a:
            r4 = com.google.android.things.internal.update.UpdateManagementServiceImpl.this;	 Catch:{ all -> 0x00e6 }
            r4 = r4.mInteractivePolicy;	 Catch:{ all -> 0x00e6 }
            if (r4 == 0) goto L_0x0067;
        L_0x0052:
            r4 = com.google.android.things.internal.update.UpdateManagementServiceImpl.TAG;	 Catch:{ all -> 0x00e6 }
            r6 = "Switching from set policy to interactive";
            android.util.Log.v(r4, r6);	 Catch:{ all -> 0x00e6 }
            r4 = com.google.android.things.internal.update.UpdateManagementServiceImpl.this;	 Catch:{ all -> 0x00e6 }
            r6 = com.google.android.things.internal.update.UpdateManagementServiceImpl.this;	 Catch:{ all -> 0x00e6 }
            r6 = r6.mInteractivePolicy;	 Catch:{ all -> 0x00e6 }
            r4.mCurrentPolicy = r6;	 Catch:{ all -> 0x00e6 }
        L_0x0067:
            r4 = r9.current_operation;	 Catch:{ all -> 0x00e6 }
            r6 = "UPDATE_STATUS_CHECKING_FOR_UPDATE";
            r6 = r4.equals(r6);	 Catch:{ all -> 0x00e6 }
            if (r6 == 0) goto L_0x00e9;
        L_0x0072:
            r4 = com.google.android.things.internal.update.UpdateManagementServiceImpl.this;	 Catch:{ all -> 0x00e6 }
            r6 = 0;
            r4.mUpdateAvailable = r6;	 Catch:{ all -> 0x00e6 }
            r4 = com.google.android.things.internal.update.UpdateManagementServiceImpl.this;	 Catch:{ all -> 0x00e6 }
            r6 = 0;
            r4.mUpdateDownloadStarted = r6;	 Catch:{ all -> 0x00e6 }
        L_0x007e:
            r4 = com.google.android.things.internal.update.UpdateManagementServiceImpl.this;	 Catch:{ all -> 0x00e6 }
            r0 = r4.UpdateManagerStatusFrom(r9);	 Catch:{ all -> 0x00e6 }
            r4 = com.google.android.things.internal.update.UpdateManagementServiceImpl.this;	 Catch:{ all -> 0x00e6 }
            r6 = r9.current_operation;	 Catch:{ all -> 0x00e6 }
            r4.mLastUpdateManagerState = r6;	 Catch:{ all -> 0x00e6 }
            monitor-exit(r5);
            r4 = com.google.android.things.internal.update.UpdateManagementServiceImpl.this;
            r4 = r4.mListeners;
            r3 = r4.beginBroadcast();
            r2 = 0;
        L_0x0097:
            if (r2 >= r3) goto L_0x0157;
        L_0x0099:
            r4 = com.google.android.things.internal.update.UpdateManagementServiceImpl.this;	 Catch:{ RemoteException -> 0x0161 }
            r4 = r4.mListeners;	 Catch:{ RemoteException -> 0x0161 }
            r4 = r4.getBroadcastItem(r2);	 Catch:{ RemoteException -> 0x0161 }
            r4 = (com.google.android.things.internal.update.IUpdateManagerStatusListener) r4;	 Catch:{ RemoteException -> 0x0161 }
            r4.onStatusUpdate(r0);	 Catch:{ RemoteException -> 0x0161 }
        L_0x00a8:
            r2 = r2 + 1;
            goto L_0x0097;
        L_0x00ab:
            r6 = "UPDATE_STATUS_IDLE";
            r4 = r4.equals(r6);	 Catch:{ all -> 0x00e6 }
            if (r4 == 0) goto L_0x0067;
        L_0x00b4:
            r4 = com.google.android.things.internal.update.UpdateManagementServiceImpl.this;	 Catch:{ all -> 0x00e6 }
            r4 = r4.mInteractivePolicy;	 Catch:{ all -> 0x00e6 }
            if (r4 == 0) goto L_0x00da;
        L_0x00bc:
            r4 = com.google.android.things.internal.update.UpdateManagementServiceImpl.this;	 Catch:{ all -> 0x00e6 }
            r4 = r4.mCurrentPolicy;	 Catch:{ all -> 0x00e6 }
            r6 = com.google.android.things.internal.update.UpdateManagementServiceImpl.this;	 Catch:{ all -> 0x00e6 }
            r6 = r6.mInteractivePolicy;	 Catch:{ all -> 0x00e6 }
            if (r4 != r6) goto L_0x00da;
        L_0x00ca:
            r4 = com.google.android.things.internal.update.UpdateManagementServiceImpl.TAG;	 Catch:{ all -> 0x00e6 }
            r6 = "Switching back to set policy from interactive";
            android.util.Log.v(r4, r6);	 Catch:{ all -> 0x00e6 }
            r4 = com.google.android.things.internal.update.UpdateManagementServiceImpl.this;	 Catch:{ all -> 0x00e6 }
            r6 = 0;
            r4.mInteractivePolicy = r6;	 Catch:{ all -> 0x00e6 }
        L_0x00da:
            r4 = com.google.android.things.internal.update.UpdateManagementServiceImpl.this;	 Catch:{ all -> 0x00e6 }
            r6 = com.google.android.things.internal.update.UpdateManagementServiceImpl.this;	 Catch:{ all -> 0x00e6 }
            r6 = r6.mSetPolicy;	 Catch:{ all -> 0x00e6 }
            r4.mCurrentPolicy = r6;	 Catch:{ all -> 0x00e6 }
            goto L_0x0067;
        L_0x00e6:
            r4 = move-exception;
            monitor-exit(r5);
            throw r4;
        L_0x00e9:
            r6 = "UPDATE_STATUS_UPDATE_AVAILABLE";
            r6 = r4.equals(r6);	 Catch:{ all -> 0x00e6 }
            if (r6 == 0) goto L_0x00f9;
        L_0x00f2:
            r4 = com.google.android.things.internal.update.UpdateManagementServiceImpl.this;	 Catch:{ all -> 0x00e6 }
            r6 = 1;
            r4.mUpdateAvailable = r6;	 Catch:{ all -> 0x00e6 }
            goto L_0x007e;
        L_0x00f9:
            r6 = "UPDATE_STATUS_DOWNLOADING";
            r6 = r4.equals(r6);	 Catch:{ all -> 0x00e6 }
            if (r6 == 0) goto L_0x011c;
        L_0x0102:
            r4 = com.google.android.things.internal.update.UpdateManagementServiceImpl.this;	 Catch:{ all -> 0x00e6 }
            r6 = 1;
            r4.mUpdateDownloadStarted = r6;	 Catch:{ all -> 0x00e6 }
        L_0x0108:
            r4 = com.google.android.things.internal.update.UpdateManagementServiceImpl.this;	 Catch:{ all -> 0x00e6 }
            r4 = r4.mUpdateAvailable;	 Catch:{ all -> 0x00e6 }
            if (r4 == 0) goto L_0x007e;
        L_0x0110:
            r4 = com.google.android.things.internal.update.UpdateManagementServiceImpl.this;	 Catch:{ all -> 0x00e6 }
            r4 = r4.mUpdateDownloadStarted;	 Catch:{ all -> 0x00e6 }
            r4 = r4 ^ 1;
            if (r4 == 0) goto L_0x007e;
        L_0x011a:
            monitor-exit(r5);
            return;
        L_0x011c:
            r6 = "UPDATE_STATUS_REPORTING_ERROR_EVENT";
            r6 = r4.equals(r6);	 Catch:{ all -> 0x00e6 }
            if (r6 != 0) goto L_0x0108;
        L_0x0125:
            r6 = "UPDATE_STATUS_UPDATED_NEED_REBOOT";
            r6 = r4.equals(r6);	 Catch:{ all -> 0x00e6 }
            if (r6 == 0) goto L_0x0135;
        L_0x012e:
            r4 = com.google.android.things.internal.update.UpdateManagementServiceImpl.this;	 Catch:{ all -> 0x00e6 }
            r6 = 1;
            r4.mUpdatedWaitingForReboot = r6;	 Catch:{ all -> 0x00e6 }
            goto L_0x007e;
        L_0x0135:
            r6 = "UPDATE_STATUS_IDLE";
            r4 = r4.equals(r6);	 Catch:{ all -> 0x00e6 }
            if (r4 == 0) goto L_0x007e;
        L_0x013e:
            r4 = com.google.android.things.internal.update.UpdateManagementServiceImpl.this;	 Catch:{ all -> 0x00e6 }
            r4 = r4.mUpdatedWaitingForReboot;	 Catch:{ all -> 0x00e6 }
            if (r4 == 0) goto L_0x007e;
        L_0x0144:
            r4 = com.google.android.things.internal.update.UpdateManagementServiceImpl.this;	 Catch:{ all -> 0x00e6 }
            r6 = 0;
            r4.mUpdateAvailable = r6;	 Catch:{ all -> 0x00e6 }
            r4 = com.google.android.things.internal.update.UpdateManagementServiceImpl.this;	 Catch:{ all -> 0x00e6 }
            r6 = 0;
            r4.mUpdateDownloadStarted = r6;	 Catch:{ all -> 0x00e6 }
            r4 = com.google.android.things.internal.update.UpdateManagementServiceImpl.this;	 Catch:{ all -> 0x00e6 }
            r6 = 0;
            r4.mUpdatedWaitingForReboot = r6;	 Catch:{ all -> 0x00e6 }
            goto L_0x007e;
        L_0x0157:
            r4 = com.google.android.things.internal.update.UpdateManagementServiceImpl.this;
            r4 = r4.mListeners;
            r4.finishBroadcast();
            return;
        L_0x0161:
            r1 = move-exception;
            goto L_0x00a8;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.things.internal.update.UpdateManagementServiceImpl.1.HandleStatusUpdate(android.brillo.ParcelableUpdateEngineStatus):void");
        }
    };
    @GuardedBy("mStateLock")
    private int mInteractivePolicy = 0;
    @GuardedBy("mStateLock")
    private String mLastUpdateManagerState = UPDATE_STATUS_IDLE;
    private final RemoteCallbackList<IUpdateManagerStatusListener> mListeners = new RemoteCallbackList();
    RebootControl mRebootControl;
    @GuardedBy("mStateLock")
    private int mSetPolicy = 1;
    private final Object mStateLock = new Object();
    @GuardedBy("mStateLock")
    private boolean mUpdateAvailable = false;
    @GuardedBy("mStateLock")
    private boolean mUpdateDownloadStarted = false;
    IUpdateEngine mUpdateEngine;
    @GuardedBy("mStateLock")
    boolean mUpdatedWaitingForReboot = false;

    public static class RebootControl {
        private final PowerManager mPowerManager;

        public RebootControl(ContextWrapper contextWrapper) {
            this.mPowerManager = (PowerManager) contextWrapper.getSystemService("power");
        }

        public void reboot() {
            this.mPowerManager.reboot("");
        }
    }

    private static Map<String, Integer> buildStateMap() {
        Map<String, Integer> mapping = new TreeMap();
        mapping.put(UPDATE_STATUS_IDLE, Integer.valueOf(0));
        mapping.put(UPDATE_STATUS_CHECKING_FOR_UPDATE, Integer.valueOf(1));
        mapping.put(UPDATE_STATUS_UPDATE_AVAILABLE, Integer.valueOf(2));
        mapping.put(UPDATE_STATUS_DOWNLOADING, Integer.valueOf(3));
        mapping.put(UPDATE_STATUS_VERIFYING, Integer.valueOf(5));
        mapping.put(UPDATE_STATUS_FINALIZING, Integer.valueOf(5));
        mapping.put(UPDATE_STATUS_UPDATED_NEED_REBOOT, Integer.valueOf(6));
        mapping.put(UPDATE_STATUS_REPORTING_ERROR_EVENT, Integer.valueOf(7));
        return Collections.unmodifiableMap(mapping);
    }

    public UpdateManagementServiceImpl(ContextWrapper contextWrapper) {
        this.mContextWrapper = contextWrapper;
    }

    public UpdateManagerStatus getStatus() {
        try {
            return UpdateManagerStatusFrom(this.mUpdateEngine.GetStatus());
        } catch (RemoteException e) {
            throw e.rethrowAsRuntimeException();
        }
    }

    public void setPolicy(int policy, long maxDurationAnUpdateCanBeBlockedMs) {
        checkCallersPermissions(MANAGE_UPDATE_POLICY_PERMISSION);
        int flags = getUpdateRestrictionFlagsForPolicy(policy);
        try {
            synchronized (this.mStateLock) {
                this.mUpdateEngine.SetUpdateAttemptFlags(flags);
                this.mSetPolicy = policy;
                if (UPDATE_STATUS_IDLE.equals(this.mLastUpdateManagerState) || UPDATE_STATUS_UPDATED_NEED_REBOOT.equals(this.mLastUpdateManagerState)) {
                    this.mCurrentPolicy = policy;
                }
            }
        } catch (RemoteException e) {
            throw e.rethrowAsRuntimeException();
        }
    }

    public void blockUpdates(long durationMs) {
        checkCallersPermissions(BLOCK_UPDATES_PERMISSION);
        Log.e(TAG, "blockUpdates is not yet implemented");
    }

    public void unblockUpdates() {
        checkCallersPermissions(BLOCK_UPDATES_PERMISSION);
        Log.e(TAG, "unblockUpdates is not yet implemented");
    }

    public void addListener(IUpdateManagerStatusListener listener) {
        Log.v(TAG, "Adding listener: " + listener);
        this.mListeners.register(listener);
    }

    public void removeListener(IUpdateManagerStatusListener listener) {
        this.mListeners.unregister(listener);
    }

    public boolean performUpdateNow(int policy) {
        checkCallersPermissions(PERFORM_UPDATE_NOW_PERMISSION);
        try {
            synchronized (this.mStateLock) {
                if (this.mUpdatedWaitingForReboot && policy == 1) {
                    this.mRebootControl.reboot();
                    return true;
                } else if (this.mUpdateEngine.AttemptUpdate("", "", getUpdateRestrictionFlagsForPolicy(policy))) {
                    this.mInteractivePolicy = policy;
                    return true;
                } else {
                    return false;
                }
            }
        } catch (RemoteException e) {
            throw e.rethrowAsRuntimeException();
        }
    }

    private void checkCallersPermissions(String permissionName) {
        if (this.mContextWrapper.checkCallingPermission(permissionName) == -1) {
            Log.e(TAG, "Calling process requires " + permissionName);
            throw new SecurityException("Calling process requires permission " + permissionName);
        }
    }

    public boolean connectToUpdateEngine() {
        if (this.mUpdateEngine != null && this.mUpdateEngine.asBinder().isBinderAlive()) {
            return true;
        }
        this.mRebootControl = new RebootControl(this.mContextWrapper);
        IBinder ueBinder = ServiceManager.getService(UPDATE_ENGINE_SERVICE_NAME);
        if (ueBinder != null) {
            this.mUpdateEngine = IUpdateEngine.Stub.asInterface(ueBinder);
            try {
                this.mUpdateEngine.RegisterStatusCallback(this.mIUpdateEngineStatusCallback);
                return true;
            } catch (Exception e) {
                Log.e(TAG, "Unable to register for update_engine callbacks", e);
                this.mUpdateEngine = null;
            }
        }
        return false;
    }

    UpdateManagerStatus UpdateManagerStatusFrom(ParcelableUpdateEngineStatus ueStatus) {
        UpdateManagerStatus updateManagerStatus;
        synchronized (this.mStateLock) {
            PendingUpdateInfo pendingUpdateInfo;
            if (UPDATE_STATUS_VERIFYING.equals(ueStatus.current_operation)) {
                Log.wtf(TAG, "Received unused/reserved VERIFYING status from update_engine");
            }
            int currentState = ((Integer) stateMapping.getOrDefault(ueStatus.current_operation, Integer.valueOf(0))).intValue();
            if (this.mUpdateAvailable && currentState == 0) {
                currentState = 2;
            }
            VersionInfo currentVersionInfo = new VersionInfo(AndroidThings.getVersionString(), ueStatus.current_version);
            VersionInfo pendingVersionInfo = new VersionInfo(ueStatus.new_system_version, ueStatus.new_version);
            switch (currentState) {
                case 2:
                case 3:
                case 7:
                    pendingUpdateInfo = new PendingUpdateInfo(pendingVersionInfo, (float) ueStatus.progress, ueStatus.new_size);
                    break;
                case 5:
                case 6:
                    pendingUpdateInfo = new PendingUpdateInfo(pendingVersionInfo, 1.0f, ueStatus.new_size);
                    break;
                default:
                    pendingUpdateInfo = null;
                    break;
            }
            updateManagerStatus = new UpdateManagerStatus(currentState, currentVersionInfo, pendingUpdateInfo, this.mCurrentPolicy);
        }
        return updateManagerStatus;
    }

    int getUpdateRestrictionFlagsForPolicy(int policy) {
        switch (policy) {
            case 1:
            case 2:
                return 0;
            case 3:
                return 2;
            default:
                throw new IllegalArgumentException("Invalid policy value: " + policy);
        }
    }
}
