package com.google.android.things.bluetooth;

import android.bluetooth.BluetoothClass;

public class BluetoothClassCreator {
    public static BluetoothClass createBluetoothClass(byte[] classOfDeviceBytes) {
        return new BluetoothClass(((classOfDeviceBytes[0] << 16) + (classOfDeviceBytes[1] << 8)) + classOfDeviceBytes[2]);
    }

    public static BluetoothClass createBluetoothClass(int classOfDevice) {
        return new BluetoothClass(classOfDevice);
    }
}
