package com.google.android.things.bluetooth;

import android.bluetooth.BluetoothDevice;
import android.os.RemoteException;

public final class PasskeyConfirmationPairingController extends BasePairingController {
    private final IBluetoothConnectionManager mBluetoothConnectionManager;
    private final String mPasskey;

    public /* bridge */ /* synthetic */ BluetoothDevice getBluetoothDevice() {
        return super.getBluetoothDevice();
    }

    PasskeyConfirmationPairingController(BluetoothDevice bluetoothDevice, IBluetoothConnectionManager bluetoothConnectionManager, String passkey) {
        super(bluetoothDevice);
        this.mPasskey = passkey;
        this.mBluetoothConnectionManager = bluetoothConnectionManager;
    }

    public String getPasskey() {
        return this.mPasskey;
    }

    public void cancel() {
        binderInvoke(new -$Lambda$mUZrfZAdiPaPYua8wBrk7jfik3w((byte) 3, this));
    }

    /* synthetic */ void lambda$-com_google_android_things_bluetooth_PasskeyConfirmationPairingController_1919() throws RemoteException {
        this.mBluetoothConnectionManager.cancelPairing(this.mBluetoothDevice);
    }

    /* synthetic */ void lambda$-com_google_android_things_bluetooth_PasskeyConfirmationPairingController_2266() throws RemoteException {
        this.mBluetoothConnectionManager.confirmPairing(this.mBluetoothDevice);
    }

    public void pair() {
        binderInvoke(new -$Lambda$mUZrfZAdiPaPYua8wBrk7jfik3w((byte) 4, this));
    }
}
