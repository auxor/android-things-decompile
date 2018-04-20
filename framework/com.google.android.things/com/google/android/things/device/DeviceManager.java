package com.google.android.things.device;

import android.os.LocaleList;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.util.Log;
import com.android.internal.util.Preconditions;
import com.google.android.things.internal.device.IDeviceManagementService;
import com.google.android.things.internal.device.IDeviceManagementService.Stub;

public class DeviceManager {
    private static final String MASTER_CLEAR_PERMISSION = "com.google.android.things.permission.MASTER_CLEAR";
    private static final String MODIFY_LOCALE_SETTINGS_PERMISSION = "com.google.android.things.permission.MODIFY_LOCALE_SETTINGS";
    private static final String REBOOT_PERMISSION = "com.google.android.things.permission.REBOOT";
    private static final String SERVICE_NAME = "devicemanagementservice";
    private static final String TAG = DeviceManager.class.getSimpleName();

    public void setSystemLocales(LocaleList localeList) {
        if (localeList.isEmpty()) {
            throw new IllegalArgumentException("LocaleList cannot be empty");
        }
        try {
            getService().setSystemLocales(localeList);
        } catch (RemoteException e) {
            e.rethrowAsRuntimeException();
        }
    }

    public void reboot(BootParameters parameters) {
        Preconditions.checkNotNull(parameters);
        try {
            getService().reboot(parameters.toPersistableBundle());
        } catch (RemoteException e) {
            Log.e(TAG, "Exception while attempting reboot");
            e.rethrowAsRuntimeException();
        }
    }

    public void reboot() {
        reboot(new BootParameters());
    }

    public BootParameters getLastBootParameters() {
        try {
            return new BootParameters(getService().getLastParameters());
        } catch (RemoteException e) {
            Log.e(TAG, "Exception while attempting to get last boot parameters");
            throw e.rethrowAsRuntimeException();
        }
    }

    public void factoryReset(boolean wipeExternalStorage) {
        try {
            getService().factoryReset(wipeExternalStorage);
        } catch (RemoteException e) {
            Log.e(TAG, "Exception while attempting factory reset");
            e.rethrowAsRuntimeException();
        }
    }

    private IDeviceManagementService getService() {
        IDeviceManagementService deviceManagementService = Stub.asInterface(ServiceManager.getService(SERVICE_NAME));
        Log.v(TAG, "Service reference initialized");
        if (deviceManagementService != null) {
            return deviceManagementService;
        }
        Log.e(TAG, "Service not initialized");
        throw new IllegalStateException("DeviceManagement service not ready");
    }
}
