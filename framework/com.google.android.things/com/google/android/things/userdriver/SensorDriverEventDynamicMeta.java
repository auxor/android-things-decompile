package com.google.android.things.userdriver;

import android.os.Parcel;

class SensorDriverEventDynamicMeta extends SensorDriverEvent {
    private static final int REPORTING_MODE_SHIFT = 1;
    private int mConnected;
    private UserSensor mSensor;

    public SensorDriverEventDynamicMeta(int handle, boolean connected, UserSensor sensor) {
        super(handle, 32);
        this.mConnected = connected ? 1 : 0;
        this.mSensor = sensor;
    }

    protected void writeSpecificParcelData(Parcel out, int flags) {
        out.writeInt(this.mConnected);
        if (this.mConnected > 0) {
            out.writeLong(this.mSensor.getUuid().getMostSignificantBits());
            out.writeLong(this.mSensor.getUuid().getLeastSignificantBits());
            out.writeString(this.mSensor.getName());
            out.writeString(this.mSensor.getVendor());
            out.writeInt(this.mSensor.getVersion());
            out.writeInt(this.mSensor.getType());
            out.writeFloat(this.mSensor.getMaxRange());
            out.writeFloat(this.mSensor.getResolution());
            out.writeFloat(this.mSensor.getPower());
            out.writeInt(this.mSensor.getMinDelay());
            out.writeString(this.mSensor.getStringType());
            out.writeString(this.mSensor.getRequiredPermission());
            out.writeInt(this.mSensor.getMaxDelay());
            out.writeInt(this.mSensor.getReportingMode() << 1);
        }
    }
}
