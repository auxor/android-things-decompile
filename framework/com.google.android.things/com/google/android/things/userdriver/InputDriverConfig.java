package com.google.android.things.userdriver;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

class InputDriverConfig implements Parcelable {
    public static final Creator<InputDriverConfig> CREATOR = new Creator<InputDriverConfig>() {
        public InputDriverConfig createFromParcel(Parcel in) {
            return new InputDriverConfig(in);
        }

        public InputDriverConfig[] newArray(int size) {
            return new InputDriverConfig[size];
        }
    };
    private static final String TAG = "InputDriverConfig";
    private int mKey;
    private int[] mValue;

    static InputDriverConfig[] createFromMap(Map<Integer, Set<Integer>> map) {
        InputDriverConfig[] configs = new InputDriverConfig[map.size()];
        Set<Integer> enabledEvents = (Set) map.get(Integer.valueOf(InputDriver.ENABLE_EVENTS));
        int index = 1;
        if (enabledEvents != null) {
            configs[0] = new InputDriverConfig(InputDriver.ENABLE_EVENTS, convertToIntArray(enabledEvents));
            for (Entry<Integer, Set<Integer>> entry : map.entrySet()) {
                if (((Integer) entry.getKey()).intValue() != InputDriver.ENABLE_EVENTS) {
                    configs[index] = new InputDriverConfig(((Integer) entry.getKey()).intValue(), convertToIntArray((Set) entry.getValue()));
                    index++;
                }
            }
            return configs;
        }
        throw new IllegalStateException("Please configure driver correctly before calling register.");
    }

    private static int[] convertToIntArray(Set<Integer> input) {
        int[] ret = new int[input.size()];
        int index = 0;
        for (Integer val : input) {
            ret[index] = val.intValue();
            index++;
        }
        return ret;
    }

    private InputDriverConfig(int key, int[] value) {
        this.mKey = key;
        this.mValue = value;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(this.mKey);
        out.writeIntArray(this.mValue);
    }

    private InputDriverConfig(Parcel in) {
        this.mKey = in.readInt();
        in.readIntArray(this.mValue);
    }
}
