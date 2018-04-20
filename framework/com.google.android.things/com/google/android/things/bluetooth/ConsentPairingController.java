package com.google.android.things.bluetooth;

import android.bluetooth.BluetoothDevice;
import android.os.RemoteException;

public final class ConsentPairingController extends BasePairingController {
    private final IBluetoothConnectionManager mBluetoothConnectionManager;

    public /* bridge */ /* synthetic */ BluetoothDevice getBluetoothDevice() {
        return super.getBluetoothDevice();
    }

    ConsentPairingController(BluetoothDevice bluetoothDevice, IBluetoothConnectionManager bluetoothConnectionManager) {
        super(bluetoothDevice);
        this.mBluetoothConnectionManager = bluetoothConnectionManager;
    }

    public void cancel() {
        binderInvoke(new -$Lambda$mUZrfZAdiPaPYua8wBrk7jfik3w((byte) 0, this));
    }

    /* synthetic */ void lambda$-com_google_android_things_bluetooth_ConsentPairingController_1508() throws RemoteException {
        this.mBluetoothConnectionManager.cancelPairing(this.mBluetoothDevice);
    }

    /* synthetic */ void lambda$-com_google_android_things_bluetooth_ConsentPairingController_2010() throws RemoteException {
        this.mBluetoothConnectionManager.confirmPairing(this.mBluetoothDevice);
    }

    public void pair() {
        binderInvoke(new -$Lambda$mUZrfZAdiPaPYua8wBrk7jfik3w((byte) 1, this));
    }
}
