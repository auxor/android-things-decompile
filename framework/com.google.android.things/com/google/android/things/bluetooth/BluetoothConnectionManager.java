package com.google.android.things.bluetooth;

import android.bluetooth.BluetoothDevice;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.util.Log;
import com.android.internal.util.Preconditions;
import com.google.android.things.bluetooth.BluetoothPairingCallback.PairingError;
import com.google.android.things.bluetooth.IBluetoothConnectionCallback.Stub;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class BluetoothConnectionManager {
    private static final String BLUETOOTH_CONTROL_SERVICE = "bluetoothconnectionservice";
    private static final String MANAGE_BLUETOOTH_PERMISSION = "com.google.android.things.permission.MANAGE_BLUETOOTH";
    private static final String TAG = BluetoothConnectionManager.class.getSimpleName();
    private final BluetoothConnectionCallback mBluetoothConnectionCallback;
    private final BluetoothConnectionCallbackInternal mBluetoothConnectionCallbackInternal;
    private final IBluetoothConnectionManager mBluetoothConnectionManager;
    private final BluetoothPairingCallback mBluetoothPairingCallback;
    private final BluetoothPairingCallbackInternal mBluetoothPairingCallbackInternal;

    static class BluetoothConnectionCallbackInternal extends Stub {
        private final BluetoothConnectionCallback mBluetoothConnectionCallback;
        private final IBluetoothConnectionManager mBluetoothConnectionManager;

        BluetoothConnectionCallbackInternal(IBluetoothConnectionManager bluetoothConnectionManager, BluetoothConnectionCallback bluetoothConnectionCallback) {
            this.mBluetoothConnectionManager = bluetoothConnectionManager;
            this.mBluetoothConnectionCallback = bluetoothConnectionCallback;
        }

        public void onConnectionRequested(BluetoothDevice bluetoothDevice, ConnectionParams connectionParams) {
            this.mBluetoothConnectionCallback.onConnectionRequested(new ConsentConnectionController(bluetoothDevice, this.mBluetoothConnectionManager, connectionParams), bluetoothDevice);
        }

        public void onConnectionRequestCancelled(BluetoothDevice bluetoothDevice, int requestType) throws RemoteException {
            this.mBluetoothConnectionCallback.onConnectionRequestCancelled(bluetoothDevice, requestType);
        }

        public void onConnected(BluetoothDevice bluetoothDevice, int bluetoothProfile) throws RemoteException {
            this.mBluetoothConnectionCallback.onConnected(bluetoothDevice, bluetoothProfile);
        }

        public void onDisconnected(BluetoothDevice bluetoothDevice, int bluetoothProfile) throws RemoteException {
            this.mBluetoothConnectionCallback.onDisconnected(bluetoothDevice, bluetoothProfile);
        }
    }

    static class BluetoothPairingCallbackInternal extends IBluetoothPairingCallback.Stub {
        private final IBluetoothConnectionManager mBluetoothConnectionManager;
        private final BluetoothPairingCallback mBluetoothPairingCallback;

        BluetoothPairingCallbackInternal(IBluetoothConnectionManager bluetoothConnectionManager, BluetoothPairingCallback bluetoothPairingCallback) {
            this.mBluetoothConnectionManager = bluetoothConnectionManager;
            this.mBluetoothPairingCallback = bluetoothPairingCallback;
        }

        public void onPairingRequest(BluetoothDevice bluetoothDevice, PairingParams pairingParams) throws RemoteException {
            Log.v(BluetoothConnectionManager.TAG, "Pairing Request - " + bluetoothDevice + ", pairing - " + pairingParams);
            switch (pairingParams.getType()) {
                case 0:
                case 7:
                    this.mBluetoothPairingCallback.onPairingInitiated(new PinPairingController(bluetoothDevice, this.mBluetoothConnectionManager, Integer.valueOf(pairingParams.getType() == 7 ? 16 : -1)), bluetoothDevice);
                    return;
                case 1:
                    Log.w(BluetoothConnectionManager.TAG, "Pairing attempt for un-implemented PAIRING_VARIANT_PASSKEY.");
                    return;
                case 2:
                    this.mBluetoothPairingCallback.onPairingInitiated(new PasskeyConfirmationPairingController(bluetoothDevice, this.mBluetoothConnectionManager, pairingParams.getPin()), bluetoothDevice);
                    return;
                case 3:
                    this.mBluetoothPairingCallback.onPairingInitiated(new ConsentPairingController(bluetoothDevice, this.mBluetoothConnectionManager), bluetoothDevice);
                    return;
                case 4:
                    this.mBluetoothConnectionManager.confirmPairing(bluetoothDevice);
                    this.mBluetoothPairingCallback.onPairingInitiated(new DisplayPairingController(bluetoothDevice, this.mBluetoothConnectionManager, pairingParams.getPin()), bluetoothDevice);
                    return;
                case 5:
                    this.mBluetoothConnectionManager.confirmPairingPin(bluetoothDevice, pairingParams.getPin());
                    this.mBluetoothPairingCallback.onPairingInitiated(new DisplayPairingController(bluetoothDevice, this.mBluetoothConnectionManager, pairingParams.getPin()), bluetoothDevice);
                    return;
                case 6:
                    Log.w(BluetoothConnectionManager.TAG, "Pairing attempt for un-implemented PAIRING_VARIANT_PASSKEY.");
                    return;
                default:
                    return;
            }
        }

        public void onPaired(BluetoothDevice bluetoothDevice) throws RemoteException {
            Log.v(BluetoothConnectionManager.TAG, "onPaired - " + bluetoothDevice);
            this.mBluetoothPairingCallback.onPaired(bluetoothDevice);
        }

        public void onUnpaired(BluetoothDevice bluetoothDevice) throws RemoteException {
            Log.v(BluetoothConnectionManager.TAG, "onUnpaired - " + bluetoothDevice);
            this.mBluetoothPairingCallback.onUnpaired(bluetoothDevice);
        }

        public void onPairingError(BluetoothDevice bluetoothDevice, int error) throws RemoteException {
            Log.v(BluetoothConnectionManager.TAG, "onPairingError - " + bluetoothDevice + ", error - " + error);
            this.mBluetoothPairingCallback.onPairingError(bluetoothDevice, new PairingError(error));
        }
    }

    public BluetoothConnectionManager(BluetoothPairingCallback bluetoothPairingCallback, BluetoothConnectionCallback bluetoothConnectionCallback) {
        this(bluetoothPairingCallback, bluetoothConnectionCallback, getBluetoothConnectionManager());
    }

    BluetoothConnectionManager(BluetoothPairingCallback bluetoothPairingCallback, BluetoothConnectionCallback bluetoothConnectionCallback, IBluetoothConnectionManager bluetoothConnectionManager) {
        Preconditions.checkNotNull(bluetoothPairingCallback, "BluetoothPairingCallback cannot be null.");
        Preconditions.checkNotNull(bluetoothConnectionCallback, "BluetoothConnectionCallback cannot be null.");
        Preconditions.checkNotNull(bluetoothConnectionManager, "BluetoothConnectionManager cannot be null.");
        this.mBluetoothPairingCallback = bluetoothPairingCallback;
        this.mBluetoothConnectionCallback = bluetoothConnectionCallback;
        this.mBluetoothConnectionManager = bluetoothConnectionManager;
        this.mBluetoothPairingCallbackInternal = new BluetoothPairingCallbackInternal(this.mBluetoothConnectionManager, this.mBluetoothPairingCallback);
        this.mBluetoothConnectionCallbackInternal = new BluetoothConnectionCallbackInternal(this.mBluetoothConnectionManager, this.mBluetoothConnectionCallback);
        try {
            this.mBluetoothConnectionManager.registerPairingCallback(this.mBluetoothPairingCallbackInternal);
            this.mBluetoothConnectionManager.registerConnectionCallback(this.mBluetoothConnectionCallbackInternal);
        } catch (RemoteException e) {
            Log.e(TAG, "Unable to register pairing callback.");
            throw new IllegalStateException(e);
        }
    }

    private static IBluetoothConnectionManager getBluetoothConnectionManager() {
        IBluetoothServiceManager bluetoothServiceManager = IBluetoothServiceManager.Stub.asInterface(ServiceManager.getService(BLUETOOTH_CONTROL_SERVICE));
        if (bluetoothServiceManager == null) {
            Log.e(TAG, "Unable to fetch BluetoothServiceManager interface.");
            throw new IllegalStateException("Unable to fetch BluetoothServiceManager interface.");
        }
        try {
            IBluetoothConnectionManager bluetoothConnectionManager = bluetoothServiceManager.getBluetoothConnectionManager();
            if (bluetoothConnectionManager != null) {
                return bluetoothConnectionManager;
            }
            Log.e(TAG, "Unable to fetch BluetoothConnectionManager interface.");
            throw new IllegalStateException("Unable to fetch BluetoothConnectionManager interface.");
        } catch (RemoteException e) {
            Log.e(TAG, "RemoteException fetching BluetoothConnectionManager interface.", e);
            throw new IllegalStateException("RemoteException fetching BluetoothConnectionManager interface.", e);
        }
    }

    public boolean initiatePairing(BluetoothDevice bluetoothDevice) {
        Preconditions.checkNotNull(bluetoothDevice, "BluetoothDevice should not be null.");
        try {
            return this.mBluetoothConnectionManager.initiatePairing(bluetoothDevice);
        } catch (RemoteException e) {
            Log.d(TAG, "Unable to connect to service.", e);
            throw new IllegalStateException(e);
        }
    }

    public boolean cancelPairing(BluetoothDevice bluetoothDevice) {
        Preconditions.checkNotNull(bluetoothDevice, "BluetoothDevice should not be null.");
        try {
            return this.mBluetoothConnectionManager.cancelPairing(bluetoothDevice);
        } catch (RemoteException e) {
            Log.d(TAG, "Unable to connect to service.", e);
            throw new IllegalStateException(e);
        }
    }

    public boolean unpair(BluetoothDevice bluetoothDevice) {
        Preconditions.checkNotNull(bluetoothDevice, "BluetoothDevice should not be null.");
        try {
            return this.mBluetoothConnectionManager.unpair(bluetoothDevice);
        } catch (RemoteException e) {
            Log.d(TAG, "Unable to connect to service.", e);
            throw new IllegalStateException(e);
        }
    }

    private static List<Integer> convert(int[] array) {
        return (List) Arrays.stream(array).boxed().collect(Collectors.toList());
    }

    public List<Integer> getConnectableProfiles(BluetoothDevice bluetoothDevice) {
        Preconditions.checkNotNull(bluetoothDevice, "BluetoothDevice should not be null.");
        try {
            return convert(this.mBluetoothConnectionManager.getConnectableProfiles(bluetoothDevice));
        } catch (RemoteException e) {
            Log.d(TAG, "Unable to connect to service.", e);
            throw new IllegalStateException(e);
        }
    }

    public int getConnectionState(BluetoothDevice bluetoothDevice, int bluetoothProfile) {
        Preconditions.checkNotNull(bluetoothDevice, "BluetoothDevice should not be null.");
        try {
            return this.mBluetoothConnectionManager.getConnectionState(bluetoothDevice, bluetoothProfile);
        } catch (RemoteException e) {
            Log.d(TAG, "Unable to connect to service.", e);
            throw new IllegalStateException(e);
        }
    }

    public boolean connect(BluetoothDevice bluetoothDevice) {
        Preconditions.checkNotNull(bluetoothDevice, "BluetoothDevice should not be null.");
        try {
            return this.mBluetoothConnectionManager.connect(bluetoothDevice);
        } catch (RemoteException e) {
            Log.d(TAG, "Unable to connect to service.", e);
            throw new IllegalStateException(e);
        }
    }

    public boolean connect(BluetoothDevice bluetoothDevice, int bluetoothProfile) {
        Preconditions.checkNotNull(bluetoothDevice, "BluetoothDevice should not be null.");
        try {
            return this.mBluetoothConnectionManager.connectProfile(bluetoothDevice, bluetoothProfile);
        } catch (RemoteException e) {
            Log.d(TAG, "Unable to connect to service.", e);
            throw new IllegalStateException(e);
        }
    }

    public boolean disconnect(BluetoothDevice bluetoothDevice) {
        try {
            return this.mBluetoothConnectionManager.disconnect(bluetoothDevice);
        } catch (RemoteException e) {
            Log.d(TAG, "Unable to connect to service.", e);
            throw new IllegalStateException(e);
        }
    }

    public boolean disconnect(BluetoothDevice bluetoothDevice, int bluetoothProfile) {
        Preconditions.checkNotNull(bluetoothDevice, "BluetoothDevice should not be null.");
        try {
            return this.mBluetoothConnectionManager.disconnectProfile(bluetoothDevice, bluetoothProfile);
        } catch (RemoteException e) {
            Log.d(TAG, "Unable to connect to service.", e);
            throw new IllegalStateException(e);
        }
    }

    public void close() {
        try {
            this.mBluetoothConnectionManager.unregisterPairingCallback(this.mBluetoothPairingCallbackInternal);
            this.mBluetoothConnectionManager.unregisterConnectionCallback(this.mBluetoothConnectionCallbackInternal);
        } catch (RemoteException e) {
            Log.e(TAG, "Error un-registering pairing callback.", e);
        }
    }
}
