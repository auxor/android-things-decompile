package com.google.android.things.bluetooth;

import android.bluetooth.BluetoothDevice;
import android.os.RemoteException;

public final class ConsentConnectionController {
    private final IBluetoothConnectionManager mBluetoothConnectionManager;
    private final BluetoothDevice mBluetoothDevice;
    private final ConnectionParams mConnectionParams;

    ConsentConnectionController(BluetoothDevice bluetoothDevice, IBluetoothConnectionManager bluetoothConnectionManager, ConnectionParams connectionParams) {
        this.mBluetoothDevice = bluetoothDevice;
        this.mBluetoothConnectionManager = bluetoothConnectionManager;
        this.mConnectionParams = connectionParams;
    }

    public BluetoothDevice getBluetoothDevice() {
        return this.mBluetoothDevice;
    }

    public int getRequestType() {
        return this.mConnectionParams.getRequestType();
    }

    public void confirm() {
        try {
            this.mBluetoothConnectionManager.sendConnectionResponse(this.mBluetoothDevice, this.mConnectionParams, true);
        } catch (RemoteException e) {
            throw new IllegalStateException(e);
        }
    }

    public void deny() {
        try {
            this.mBluetoothConnectionManager.sendConnectionResponse(this.mBluetoothDevice, this.mConnectionParams, false);
        } catch (RemoteException e) {
            throw new IllegalStateException(e);
        }
    }
}
