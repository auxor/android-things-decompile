package com.google.android.things.bluetooth;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import java.util.Objects;

public final class ConnectionParams implements Parcelable {
    public static final Creator<ConnectionParams> CREATOR = new Creator<ConnectionParams>() {
        public ConnectionParams createFromParcel(Parcel in) {
            return new ConnectionParams(in);
        }

        public ConnectionParams[] newArray(int size) {
            return new ConnectionParams[size];
        }
    };
    private final int mRequestType;
    private final String mReturnClass;
    private final String mReturnPackage;

    public ConnectionParams(int requestType, String returnPackage, String returnClass) {
        this.mRequestType = requestType;
        this.mReturnPackage = returnPackage;
        this.mReturnClass = returnClass;
    }

    protected ConnectionParams(Parcel in) {
        this.mRequestType = in.readInt();
        this.mReturnPackage = in.readString();
        this.mReturnClass = in.readString();
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.mRequestType);
        dest.writeString(this.mReturnPackage);
        dest.writeString(this.mReturnClass);
    }

    public int getRequestType() {
        return this.mRequestType;
    }

    public String getReturnPackage() {
        return this.mReturnPackage;
    }

    public String getReturnClass() {
        return this.mReturnClass;
    }

    public boolean equals(Object o) {
        boolean z = false;
        if (this == o) {
            return true;
        }
        if (!(o instanceof ConnectionParams)) {
            return false;
        }
        ConnectionParams that = (ConnectionParams) o;
        if (this.mRequestType == that.mRequestType && Objects.equals(this.mReturnPackage, that.mReturnPackage)) {
            z = Objects.equals(this.mReturnClass, that.mReturnClass);
        }
        return z;
    }

    public int hashCode() {
        return Objects.hash(new Object[]{Integer.valueOf(this.mRequestType), this.mReturnPackage, this.mReturnClass});
    }

    public String toString() {
        return "ConnectionParams{mRequestType=" + this.mRequestType + ", mReturnPackage='" + this.mReturnPackage + '\'' + ", mReturnClass='" + this.mReturnClass + '\'' + '}';
    }
}
