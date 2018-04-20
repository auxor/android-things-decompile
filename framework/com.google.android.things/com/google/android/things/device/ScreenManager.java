package com.google.android.things.device;

import android.os.RemoteException;
import android.os.ServiceManager;
import com.google.android.things.internal.device.IDeviceManagementService;
import com.google.android.things.internal.device.IDeviceManagementService.Stub;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.concurrent.TimeUnit;

public class ScreenManager {
    public static final int BRIGHTNESS_MODE_AUTOMATIC = 1;
    public static final int BRIGHTNESS_MODE_MANUAL = 0;
    private static final String MODIFY_SCREEN_SETTINGS_PERMISSION = "com.google.android.things.permission.MODIFY_SCREEN_SETTINGS";
    public static final int ROTATION_0 = 0;
    public static final int ROTATION_180 = 2;
    public static final int ROTATION_270 = 3;
    public static final int ROTATION_90 = 1;
    public static final int ROTATION_CURRENT = -1;
    private static final String SERVICE_NAME = "devicemanagementservice";
    private static final String TAG = ScreenManager.class.getSimpleName();
    private final int mDisplayId;

    @Retention(RetentionPolicy.SOURCE)
    public @interface BrightnessMode {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface Rotation {
    }

    public ScreenManager(int displayId) {
        if (displayId != 0) {
            throw new IllegalArgumentException("DisplayId must be Display.DEFAULT_DISPLAY");
        }
        this.mDisplayId = displayId;
    }

    public void setBrightness(int brightness) {
        if (brightness < 0 || brightness > 255) {
            throw new IllegalArgumentException("Brightness must be between 0 and 255");
        }
        try {
            getService().setScreenBrightness(brightness);
        } catch (RemoteException e) {
            throw e.rethrowAsRuntimeException();
        }
    }

    public void setBrightnessMode(int brightnessMode) {
        if (brightnessMode == 0 || brightnessMode == 1) {
            try {
                getService().setScreenBrightnessMode(brightnessMode);
                return;
            } catch (RemoteException e) {
                throw e.rethrowAsRuntimeException();
            }
        }
        throw new IllegalArgumentException("Invalid brightnessMode: " + brightnessMode);
    }

    public void setScreenOffTimeout(long duration, TimeUnit unit) {
        if (duration < 0) {
            throw new IllegalArgumentException("Duration cannot be negative");
        }
        if (duration > 2147483647L) {
            duration = 2147483647L;
        }
        try {
            getService().setScreenOffTimeout(TimeUnit.MILLISECONDS.convert(duration, unit));
        } catch (RemoteException e) {
            throw e.rethrowAsRuntimeException();
        }
    }

    public void setFontScale(float scalingFactor) {
        if (scalingFactor <= 0.0f) {
            throw new IllegalArgumentException("ScalingFactor must be greater than 0");
        }
        try {
            getService().setFontScale(scalingFactor);
        } catch (RemoteException e) {
            throw e.rethrowAsRuntimeException();
        }
    }

    public void setDisplayDensity(int density) {
        if (density <= 0) {
            throw new IllegalArgumentException("Density must be greater than 0");
        }
        try {
            getService().setDisplayDensity(this.mDisplayId, density);
        } catch (RemoteException e) {
            throw e.rethrowAsRuntimeException();
        }
    }

    public void lockRotation(int rotation) {
        switch (rotation) {
            case -1:
            case 0:
            case 1:
            case 2:
            case 3:
                try {
                    getService().lockRotation(rotation);
                    return;
                } catch (RemoteException e) {
                    throw e.rethrowAsRuntimeException();
                }
            default:
                throw new IllegalArgumentException("Invalid rotation: " + rotation);
        }
    }

    public void unlockRotation() {
        try {
            getService().unlockRotation();
        } catch (RemoteException e) {
            throw e.rethrowAsRuntimeException();
        }
    }

    private static IDeviceManagementService getService() {
        IDeviceManagementService deviceManagementService = Stub.asInterface(ServiceManager.getService(SERVICE_NAME));
        if (deviceManagementService != null) {
            return deviceManagementService;
        }
        throw new IllegalStateException("DeviceManagement service not ready");
    }
}
