package com.google.android.things.bluetooth;

import android.bluetooth.BluetoothDevice;
import android.os.RemoteException;
import com.android.internal.util.Preconditions;

public final class PinPairingController extends BasePairingController {
    private final IBluetoothConnectionManager mBluetoothConnectionManager;
    private final Integer mPinSize;

    public /* bridge */ /* synthetic */ BluetoothDevice getBluetoothDevice() {
        return super.getBluetoothDevice();
    }

    PinPairingController(BluetoothDevice bluetoothDevice, IBluetoothConnectionManager bluetoothConnectionManager, Integer pinSize) {
        super(bluetoothDevice);
        this.mPinSize = pinSize;
        this.mBluetoothConnectionManager = bluetoothConnectionManager;
    }

    public Integer getPinSize() {
        return this.mPinSize;
    }

    public void cancel() {
        binderInvoke(new -$Lambda$mUZrfZAdiPaPYua8wBrk7jfik3w((byte) 5, this));
    }

    /* synthetic */ void lambda$-com_google_android_things_bluetooth_PinPairingController_2043() throws RemoteException {
        this.mBluetoothConnectionManager.cancelPairing(this.mBluetoothDevice);
    }

    public void pair(String pin) {
        Preconditions.checkNotNull(pin);
        binderInvoke(new -$Lambda$POOJvJDwlJW3FBoOL1EisB5P_eY(this, pin));
    }

    /* synthetic */ void lambda$-com_google_android_things_bluetooth_PinPairingController_2506(String pin) throws RemoteException {
        this.mBluetoothConnectionManager.confirmPairingPin(this.mBluetoothDevice, pin);
    }
}
