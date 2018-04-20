package com.google.android.things.bluetooth;

import android.bluetooth.BluetoothDevice;
import android.os.RemoteException;

abstract class BasePairingController {
    protected final BluetoothDevice mBluetoothDevice;

    interface BinderFunc {
        void run() throws RemoteException;
    }

    protected BasePairingController(BluetoothDevice bluetoothDevice) {
        this.mBluetoothDevice = bluetoothDevice;
    }

    public BluetoothDevice getBluetoothDevice() {
        return this.mBluetoothDevice;
    }

    protected void binderInvoke(BinderFunc binderFunc) {
        try {
            binderFunc.run();
        } catch (RemoteException e) {
            throw new IllegalStateException(e);
        }
    }
}
