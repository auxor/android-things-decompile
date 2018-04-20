package android.brillo;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class ParcelableUpdateEngineStatus implements Parcelable {
    public static final Creator<ParcelableUpdateEngineStatus> CREATOR = new Creator<ParcelableUpdateEngineStatus>() {
        public ParcelableUpdateEngineStatus createFromParcel(Parcel in) {
            return new ParcelableUpdateEngineStatus(in);
        }

        public ParcelableUpdateEngineStatus[] newArray(int size) {
            return new ParcelableUpdateEngineStatus[size];
        }
    };
    public final String current_operation;
    public final String current_system_version;
    public final String current_version;
    public final long last_checked_time;
    public final long new_size;
    public final String new_system_version;
    public final String new_version;
    public final double progress;

    public static class Builder {
        private String current_operation;
        private double current_progress;
        private String current_system_version;
        private String current_version;
        private long last_checked_time;
        private long new_size;
        private String new_system_version;
        private String new_version;

        public ParcelableUpdateEngineStatus build() {
            return new ParcelableUpdateEngineStatus(this.last_checked_time, this.current_operation, this.current_progress, this.current_version, this.current_system_version, this.new_size, this.new_version, this.new_system_version);
        }

        public Builder lastCheckedTime(long value) {
            this.last_checked_time = value;
            return this;
        }

        public Builder currentOperation(String value) {
            this.current_operation = value;
            return this;
        }

        public Builder progress(double value) {
            this.current_progress = value;
            return this;
        }

        public Builder currentVersion(String value) {
            this.current_version = value;
            return this;
        }

        public Builder currentSystemVersion(String value) {
            this.current_system_version = value;
            return this;
        }

        public Builder newSize(long value) {
            this.new_size = value;
            return this;
        }

        public Builder newVersion(String value) {
            this.new_version = value;
            return this;
        }

        public Builder newSystemVersion(String value) {
            this.new_system_version = value;
            return this;
        }
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel out, int flags) {
        out.writeLong(this.last_checked_time);
        out.writeString(this.current_operation);
        out.writeDouble(this.progress);
        out.writeString(this.current_version);
        out.writeString(this.current_system_version);
        out.writeLong(this.new_size);
        out.writeString(this.new_version);
        out.writeString(this.new_system_version);
    }

    private ParcelableUpdateEngineStatus(Parcel in) {
        this.last_checked_time = in.readLong();
        this.current_operation = in.readString();
        this.progress = in.readDouble();
        this.current_version = in.readString();
        this.current_system_version = in.readString();
        this.new_size = in.readLong();
        this.new_version = in.readString();
        this.new_system_version = in.readString();
    }

    private ParcelableUpdateEngineStatus(long last_checked_time, String current_operation, double progress, String current_version, String current_system_version, long new_size, String new_version, String new_system_version) {
        this.last_checked_time = last_checked_time;
        this.current_operation = current_operation;
        this.progress = progress;
        this.current_version = current_version;
        this.current_system_version = current_system_version;
        this.new_size = new_size;
        this.new_version = new_version;
        this.new_system_version = new_system_version;
    }

    public static Builder BUILDER() {
        return new Builder();
    }
}
