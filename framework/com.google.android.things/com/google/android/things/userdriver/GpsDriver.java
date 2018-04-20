package com.google.android.things.userdriver;

import android.location.Location;
import android.os.RemoteException;
import android.os.ServiceManager;
import com.google.android.things.userdriver.IGpsDriverService.Stub;

public class GpsDriver {
    private IGpsDriverService mService;

    void initialize() {
        this.mService = Stub.asInterface(ServiceManager.getService("gpsdriverservice"));
        if (this.mService == null) {
            throw new IllegalStateException("Couldn't find GPS driver service. If the device has a built-in GPS sensor, you must use that instead.");
        }
        try {
            this.mService.init();
        } catch (RemoteException e) {
            throw e.rethrowAsRuntimeException();
        }
    }

    public void reportLocation(Location location) {
        if (location.hasAccuracy() || location.getTime() != 0) {
            try {
                this.mService.reportLocation(location.getLatitude(), location.getLongitude(), location.getAccuracy(), location.getTime());
                return;
            } catch (RemoteException e) {
                throw e.rethrowAsRuntimeException();
            }
        }
        throw new IllegalArgumentException("Incomplete location object.");
    }

    public void reportNmea(String rawGpsData) {
        if (rawGpsData == null || rawGpsData.length() == 0) {
            throw new IllegalArgumentException("Incomplete NMEA data.");
        }
        try {
            this.mService.reportNmea(rawGpsData, System.currentTimeMillis());
        } catch (RemoteException e) {
            throw e.rethrowAsRuntimeException();
        }
    }
}
