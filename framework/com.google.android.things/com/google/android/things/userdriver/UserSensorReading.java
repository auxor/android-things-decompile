package com.google.android.things.userdriver;

import java.util.Arrays;
import java.util.Objects;

public class UserSensorReading {
    private final int mStatus;
    private final float[] mValues;

    public UserSensorReading(float[] values) {
        this(values, 3);
    }

    public UserSensorReading(float[] values, int status) {
        this.mValues = Arrays.copyOf(values, values.length);
        this.mStatus = status;
    }

    float[] getValues() {
        return this.mValues;
    }

    int getStatus() {
        return this.mStatus;
    }

    public boolean equals(Object object) {
        boolean z = false;
        if (!(object instanceof UserSensorReading)) {
            return false;
        }
        UserSensorReading that = (UserSensorReading) object;
        if (Arrays.equals(this.mValues, that.mValues) && this.mStatus == that.mStatus) {
            z = true;
        }
        return z;
    }

    public int hashCode() {
        return Objects.hash(new Object[]{Integer.valueOf(Arrays.hashCode(this.mValues)), Integer.valueOf(this.mStatus)});
    }
}
