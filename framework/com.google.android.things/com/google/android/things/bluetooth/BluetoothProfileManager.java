package com.google.android.things.bluetooth;

import android.os.IBinder;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.util.Log;
import com.google.android.things.bluetooth.IBluetoothProfileManager.Stub;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public final class BluetoothProfileManager {
    private static final String BLUETOOTH_PROFILE_MANAGER_SERVICE = "bluetoothprofilemanagerservice";
    private static final String MANAGE_BLUETOOTH_PERMISSION = "com.google.android.things.permission.MANAGE_BLUETOOTH";
    private final String TAG = BluetoothProfileManager.class.getCanonicalName();
    private IBluetoothProfileManager mProfileManagerProxy;

    public BluetoothProfileManager() {
        IBinder binder = ServiceManager.getService(BLUETOOTH_PROFILE_MANAGER_SERVICE);
        this.mProfileManagerProxy = Stub.asInterface(binder);
        if (binder == null) {
            Log.e(this.TAG, "Unable to fetch service interface.");
            throw new IllegalStateException("Unable to fetch service interface.");
        }
    }

    private static int[] convert(List<Integer> list) {
        return list.stream().mapToInt(-$Lambda$D4Wtr4nFQdRxLb1GaNUhk_jnDGM.$INST$0).toArray();
    }

    private static List<Integer> convert(int[] array) {
        return (List) Arrays.stream(array).boxed().collect(Collectors.toList());
    }

    private static void checkNotNull(List<Integer> list) {
        if (list == null) {
            throw new IllegalArgumentException("Profiles list cannot be null.");
        }
    }

    private static void validateProfilesList(List<Integer> toEnable, List<Integer> toDisable) {
        for (Integer profile : toEnable) {
            if (toDisable.contains(profile)) {
                throw new IllegalArgumentException(String.format("Profile %d added in both enabled and disabled lists.", new Object[]{(Integer) profile$iterator.next()}));
            }
        }
    }

    public void enableProfiles(List<Integer> toEnable) {
        enableAndDisableProfiles(toEnable, new ArrayList());
    }

    public void disableProfiles(List<Integer> toDisable) {
        enableAndDisableProfiles(new ArrayList(), toDisable);
    }

    public void enableAndDisableProfiles(List<Integer> toEnable, List<Integer> toDisable) {
        checkNotNull(toEnable);
        checkNotNull(toDisable);
        validateProfilesList(toEnable, toDisable);
        try {
            this.mProfileManagerProxy.modifyEnabledProfiles(convert((List) toEnable), convert((List) toDisable));
        } catch (RemoteException e) {
            Log.e(this.TAG, "Exception while modifying enabled profile.", e);
            throw new IllegalStateException(e);
        }
    }

    public void setEnabledProfiles(List<Integer> profiles) {
        checkNotNull(profiles);
        try {
            this.mProfileManagerProxy.setEnabledProfiles(convert((List) profiles));
        } catch (RemoteException e) {
            Log.e(this.TAG, "Exception while setting enabled profile.", e);
            throw new IllegalStateException(e);
        }
    }

    public List<Integer> getEnabledProfiles() {
        try {
            return convert(this.mProfileManagerProxy.getEnabledProfiles());
        } catch (RemoteException e) {
            Log.e(this.TAG, "Exception while fetching enabled profiles.", e);
            throw new IllegalStateException(e);
        }
    }
}
