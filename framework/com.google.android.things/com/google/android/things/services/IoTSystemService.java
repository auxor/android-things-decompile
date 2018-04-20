package com.google.android.things.services;

import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.UserHandle;
import android.util.Log;
import com.android.server.SystemService;
import java.io.FileDescriptor;
import java.io.PrintWriter;

public class IoTSystemService extends SystemService {
    private static final String BLUETOOTH_CONTROL_SERVICE_CLASS = "com.google.android.things.internal.bluetooth.BluetoothControlService";
    private static final String BLUETOOTH_SERVICES_PACKAGE = "com.google.android.things.internal.bluetooth";
    private static final String DEVICE_MANAGEMENT_APK_PACKAGE = "com.google.android.things.internal.devicemanagement";
    private static final String DEVICE_MANAGEMENT_SERVICE = "com.google.android.things.internal.device.DeviceManagementService";
    private static final String IOT_SETTINGS_PACKAGE = "com.google.android.things.internal.settings";
    private static final String IOT_SETTINGS_SERVICE = "com.google.android.things.internal.settings.IotSettingsService";
    private static final String MEDIA_CONTROL_PACKAGE = "com.google.android.things.internal.media";
    private static final String MEDIA_CONTROL_SERVICE = "com.google.android.things.internal.media.MediaControlService";
    private static final String METRICS_MANAGEMENT_SERVICE = "com.google.android.things.internal.metrics.MetricsManagementService";
    private static final String SERVICE_NAME = "iotsystemservice";
    private static final String TAG = IoTSystemService.class.getSimpleName();
    private static final String UPDATE_MANAGEMENT_SERVICE = "com.google.android.things.internal.update.UpdateManagementService";

    private final class IoTSystemBinder extends Binder {
        private IoTSystemBinder() {
        }

        protected void dump(FileDescriptor fd, PrintWriter writer, String[] args) {
            Log.v(IoTSystemService.TAG, "Binder Server dump()");
        }
    }

    public IoTSystemService(Context context) {
        super(context);
    }

    public void onStart() {
        publishBinderService(SERVICE_NAME, new IoTSystemBinder());
    }

    public void onBootPhase(int phase) {
        if (phase == 500) {
            Log.d(TAG, "onBootPhase() Android system services ready");
            startOtherIotServices();
        }
    }

    private void startOtherIotServices() {
        Log.v(TAG, "Starting DeviceManagementService");
        Intent intent = new Intent();
        intent.setClassName(DEVICE_MANAGEMENT_APK_PACKAGE, DEVICE_MANAGEMENT_SERVICE);
        getContext().startServiceAsUser(intent, new UserHandle(-2));
        intent = new Intent();
        intent.setClassName(MEDIA_CONTROL_PACKAGE, MEDIA_CONTROL_SERVICE);
        getContext().startServiceAsUser(intent, new UserHandle(-2));
        startBluetoothServices();
        startMetricsManagementService();
        startUpdateManagementService();
    }

    private void startBluetoothServices() {
        Log.v(TAG, "Starting Bluetooth Services.");
        Intent intent = new Intent();
        intent.setClassName(BLUETOOTH_SERVICES_PACKAGE, BLUETOOTH_CONTROL_SERVICE_CLASS);
        getContext().startServiceAsUser(intent, new UserHandle(-2));
    }

    private void startMetricsManagementService() {
        Log.v(TAG, "Starting MetricsManagementService");
        Intent intent = new Intent();
        intent.setClassName(DEVICE_MANAGEMENT_APK_PACKAGE, METRICS_MANAGEMENT_SERVICE);
        getContext().startServiceAsUser(intent, new UserHandle(-2));
    }

    private void startUpdateManagementService() {
        Log.v(TAG, "Starting UpdateManagementService");
        Intent intent = new Intent();
        intent.setClassName(DEVICE_MANAGEMENT_APK_PACKAGE, UPDATE_MANAGEMENT_SERVICE);
        getContext().startServiceAsUser(intent, new UserHandle(-2));
    }
}
