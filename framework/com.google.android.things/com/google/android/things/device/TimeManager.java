package com.google.android.things.device;

import android.os.RemoteException;
import android.os.ServiceManager;
import com.google.android.things.internal.device.IDeviceManagementService;
import com.google.android.things.internal.device.IDeviceManagementService.Stub;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.TimeZone;

public class TimeManager {
    private static final String CHANGE_TIME_PERMISSION = "com.google.android.things.permission.CHANGE_TIME";
    public static final int FORMAT_12 = 12;
    public static final int FORMAT_24 = 24;
    private static final String SERVICE_NAME = "devicemanagementservice";
    private static final String TAG = TimeManager.class.getSimpleName();

    @Retention(RetentionPolicy.SOURCE)
    public @interface TimeFormat {
    }

    public void setTime(long millis) {
        if (millis < 0) {
            throw new IllegalArgumentException("millis cannot be negative");
        }
        try {
            getService().setTime(millis);
        } catch (RemoteException e) {
            throw e.rethrowAsRuntimeException();
        }
    }

    public void setTimeZone(String timeZone) {
        boolean isValidTimeZone = false;
        for (String tz : TimeZone.getAvailableIDs()) {
            if (tz.equals(timeZone)) {
                isValidTimeZone = true;
                break;
            }
        }
        if (isValidTimeZone) {
            try {
                getService().setTimeZone(timeZone);
                return;
            } catch (RemoteException e) {
                throw e.rethrowAsRuntimeException();
            }
        }
        throw new IllegalArgumentException("timeZone must be a supported Olson id");
    }

    public void setAutoTimeEnabled(boolean enabled) {
        try {
            getService().setAutoTimeEnabled(enabled);
        } catch (RemoteException e) {
            throw e.rethrowAsRuntimeException();
        }
    }

    public void setTimeFormat(int timeFormat) {
        if (timeFormat == 12 || timeFormat == 24) {
            try {
                getService().setTimeFormat(timeFormat);
                return;
            } catch (RemoteException e) {
                throw e.rethrowAsRuntimeException();
            }
        }
        throw new IllegalArgumentException("Invalid timeFormat: " + timeFormat);
    }

    private static IDeviceManagementService getService() {
        IDeviceManagementService deviceManagementService = Stub.asInterface(ServiceManager.getService(SERVICE_NAME));
        if (deviceManagementService != null) {
            return deviceManagementService;
        }
        throw new IllegalStateException("DeviceManagement service not ready");
    }
}
