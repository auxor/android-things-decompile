package com.google.android.things.userdriver;

import android.os.Parcel;

class SensorDriverEventData extends SensorDriverEvent {
    private UserSensorReading mReading;

    public SensorDriverEventData(int handle, int type, UserSensorReading reading) {
        super(handle, type);
        this.mReading = reading;
    }

    protected void writeSpecificParcelData(Parcel out, int flags) {
        out.writeFloatArray(this.mReading.getValues());
        out.writeByte((byte) this.mReading.getStatus());
    }
}
