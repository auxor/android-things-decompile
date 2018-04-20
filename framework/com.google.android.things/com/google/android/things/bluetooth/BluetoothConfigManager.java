package com.google.android.things.bluetooth;

import android.bluetooth.BluetoothClass;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.util.Log;
import com.android.internal.util.Preconditions;
import com.google.android.things.bluetooth.IBluetoothServiceManager.Stub;

public class BluetoothConfigManager {
    private static final String BLUETOOTH_CONTROL_SERVICE = "bluetoothconnectionservice";
    private static final String MANAGE_BLUETOOTH_PERMISSION = "com.google.android.things.permission.MANAGE_BLUETOOTH";
    private final String TAG = BluetoothConfigManager.class.getCanonicalName();
    private final IBluetoothConfigManager mBluetoothConfigManager;

    public BluetoothConfigManager() {
        IBluetoothServiceManager bluetoothServiceManager = Stub.asInterface(ServiceManager.getService(BLUETOOTH_CONTROL_SERVICE));
        if (bluetoothServiceManager == null) {
            Log.e(this.TAG, "Unable to fetch BluetoothConfigManager interface.");
            throw new IllegalStateException("Unable to fetch BluetoothConfigManager interface.");
        }
        try {
            this.mBluetoothConfigManager = bluetoothServiceManager.getBluetoothConfigManager();
            if (this.mBluetoothConfigManager == null) {
                Log.e(this.TAG, "Unable to fetch BluetoothConfigManager interface.");
                throw new IllegalStateException("Unable to fetch BluetoothConfigManager interface.");
            }
        } catch (RemoteException e) {
            Log.e(this.TAG, "RemoteException fetching BluetoothConfigManager interface.", e);
            throw new IllegalStateException("RemoteException fetching BluetoothConfigManager interface.", e);
        }
    }

    public BluetoothClass getBluetoothClass() {
        try {
            return this.mBluetoothConfigManager.getBluetoothClass();
        } catch (RemoteException e) {
            Log.e(this.TAG, "Exception while fetching BluetoothClass.", e);
            throw new IllegalStateException(e);
        }
    }

    public boolean setBluetoothClass(BluetoothClass bluetoothClass) {
        Preconditions.checkArgument(bluetoothClass != null, "bluetoothClass should not be null.");
        try {
            return this.mBluetoothConfigManager.setBluetoothClass(bluetoothClass);
        } catch (RemoteException e) {
            Log.e(this.TAG, "Exception while setting BluetoothClass.", e);
            throw new IllegalStateException(e);
        }
    }
}
