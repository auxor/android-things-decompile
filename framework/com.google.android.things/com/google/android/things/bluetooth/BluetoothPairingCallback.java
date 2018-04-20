package com.google.android.things.bluetooth;

import android.bluetooth.BluetoothDevice;

public abstract class BluetoothPairingCallback {

    public static class PairingError {
        public static final int UNBOND_REASON_AUTH_CANCELED = 3;
        public static final int UNBOND_REASON_AUTH_FAILED = 1;
        public static final int UNBOND_REASON_AUTH_REJECTED = 2;
        public static final int UNBOND_REASON_AUTH_TIMEOUT = 6;
        public static final int UNBOND_REASON_DISCOVERY_IN_PROGRESS = 5;
        public static final int UNBOND_REASON_REMOTE_AUTH_CANCELED = 8;
        public static final int UNBOND_REASON_REMOTE_DEVICE_DOWN = 4;
        public static final int UNBOND_REASON_REMOVED = 9;
        public static final int UNBOND_REASON_REPEATED_ATTEMPTS = 7;
        private final int errorCode;

        PairingError(int errorCode) {
            this.errorCode = errorCode;
        }

        public int getErrorCode() {
            return this.errorCode;
        }
    }

    public void onPairingInitiated(PinPairingController pairingController, BluetoothDevice bluetoothDevice) {
    }

    public void onPairingInitiated(PasskeyConfirmationPairingController pairingController, BluetoothDevice bluetoothDevice) {
    }

    public void onPairingInitiated(ConsentPairingController pairingController, BluetoothDevice bluetoothDevice) {
    }

    public void onPairingInitiated(DisplayPairingController pairingController, BluetoothDevice bluetoothDevice) {
    }

    public void onPaired(BluetoothDevice bluetoothDevice) {
    }

    public void onUnpaired(BluetoothDevice bluetoothDevice) {
    }

    public void onPairingError(BluetoothDevice bluetoothDevice, PairingError error) {
    }
}
