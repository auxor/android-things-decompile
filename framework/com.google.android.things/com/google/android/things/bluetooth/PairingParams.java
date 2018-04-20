package com.google.android.things.bluetooth;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import java.util.Objects;

public final class PairingParams implements Parcelable {
    public static final Creator<PairingParams> CREATOR = new Creator<PairingParams>() {
        public PairingParams createFromParcel(Parcel in) {
            return new PairingParams(in);
        }

        public PairingParams[] newArray(int size) {
            return new PairingParams[size];
        }
    };
    private final String mPin;
    private final int mType;

    public PairingParams(int type) {
        this.mType = type;
        this.mPin = "";
    }

    public PairingParams(int type, String pin) {
        this.mType = type;
        this.mPin = pin;
    }

    protected PairingParams(Parcel in) {
        this.mType = in.readInt();
        this.mPin = in.readString();
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.mType);
        dest.writeString(this.mPin);
    }

    public int getType() {
        return this.mType;
    }

    public String getPin() {
        return this.mPin;
    }

    public boolean equals(Object o) {
        boolean z = false;
        if (this == o) {
            return true;
        }
        if (!(o instanceof PairingParams)) {
            return false;
        }
        PairingParams that = (PairingParams) o;
        if (this.mType == that.mType) {
            z = Objects.equals(this.mPin, that.mPin);
        }
        return z;
    }

    public int hashCode() {
        return Objects.hash(new Object[]{Integer.valueOf(this.mType), this.mPin});
    }

    public String toString() {
        return "PairingParams{type=" + this.mType + '}';
    }
}
