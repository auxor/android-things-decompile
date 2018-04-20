package com.google.android.things.bluetooth;

import android.bluetooth.BluetoothDevice;

public abstract class BluetoothConnectionCallback {
    public static final int REQUEST_TYPE_MESSAGE_ACCESS = 3;
    public static final int REQUEST_TYPE_PHONEBOOK_ACCESS = 2;
    public static final int REQUEST_TYPE_PROFILE_CONNECTION = 1;
    public static final int REQUEST_TYPE_SIM_ACCESS = 4;

    public void onConnectionRequested(ConsentConnectionController connectionController, BluetoothDevice bluetoothDevice) {
    }

    public void onConnectionRequestCancelled(BluetoothDevice bluetoothDevice, int requestType) {
    }

    public void onConnected(BluetoothDevice bluetoothDevice, int profile) {
    }

    public void onDisconnected(BluetoothDevice bluetoothDevice, int profile) {
    }
}
