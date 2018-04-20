package com.google.android.things.userdriver;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.os.SystemClock;
import android.util.Log;

abstract class SensorDriverEvent implements Parcelable {
    public static final Creator<SensorDriverEvent> CREATOR = new Creator<SensorDriverEvent>() {
        public SensorDriverEvent createFromParcel(Parcel in) {
            Log.w(SensorDriverEvent.TAG, "createFromParcel() not yet implemented; returning null event");
            return null;
        }

        public SensorDriverEvent[] newArray(int size) {
            return new SensorDriverEvent[size];
        }
    };
    private static final String TAG = "SensorDriverEvent";
    protected static final int TYPE_META_DATA = 0;
    private int mHandle;
    private long mTimestamp = SystemClock.elapsedRealtimeNanos();
    private int mType;

    protected abstract void writeSpecificParcelData(Parcel parcel, int i);

    protected SensorDriverEvent(int handle, int type) {
        this.mHandle = handle;
        this.mType = type;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(this.mHandle);
        out.writeInt(this.mType);
        out.writeLong(this.mTimestamp);
        writeSpecificParcelData(out, flags);
    }
}
