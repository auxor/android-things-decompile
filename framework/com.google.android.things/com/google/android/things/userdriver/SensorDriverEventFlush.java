package com.google.android.things.userdriver;

import android.os.Parcel;

class SensorDriverEventFlush extends SensorDriverEvent {
    public SensorDriverEventFlush(int handle) {
        super(handle, 0);
    }

    protected void writeSpecificParcelData(Parcel out, int flags) {
    }
}
