package com.google.android.things.bluetooth;

import android.bluetooth.BluetoothDevice;
import android.os.RemoteException;

public final class DisplayPairingController extends BasePairingController {
    private final IBluetoothConnectionManager mBluetoothConnectionManager;
    private final String mPairingKey;

    public /* bridge */ /* synthetic */ BluetoothDevice getBluetoothDevice() {
        return super.getBluetoothDevice();
    }

    DisplayPairingController(BluetoothDevice bluetoothDevice, IBluetoothConnectionManager bluetoothConnectionManager, String pairingKey) {
        super(bluetoothDevice);
        this.mPairingKey = pairingKey;
        this.mBluetoothConnectionManager = bluetoothConnectionManager;
    }

    public void cancel() {
        binderInvoke(new -$Lambda$mUZrfZAdiPaPYua8wBrk7jfik3w((byte) 2, this));
    }

    /* synthetic */ void lambda$-com_google_android_things_bluetooth_DisplayPairingController_2213() throws RemoteException {
        this.mBluetoothConnectionManager.cancelPairing(this.mBluetoothDevice);
    }

    public String getPairingKey() {
        return this.mPairingKey;
    }
}
