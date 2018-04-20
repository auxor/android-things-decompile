package com.google.android.things.update;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class UpdateManagerStatus implements Parcelable {
    public static final Creator<UpdateManagerStatus> CREATOR = new Creator<UpdateManagerStatus>() {
        public UpdateManagerStatus createFromParcel(Parcel in) {
            return new UpdateManagerStatus(in);
        }

        public UpdateManagerStatus[] newArray(int size) {
            return new UpdateManagerStatus[size];
        }
    };
    public static final int STATE_BLOCKED = 8;
    public static final int STATE_BLOCK_TIMING_OUT = 9;
    public static final int STATE_CHECKING_FOR_UPDATES = 1;
    public static final int STATE_DOWNLOADING_UPDATE = 3;
    public static final int STATE_FINALIZING_UPDATE = 5;
    public static final int STATE_IDLE = 0;
    public static final int STATE_OVERRIDE_CHECK = 10;
    public static final int STATE_REPORTING_ERROR = 7;
    public static final int STATE_UPDATED_NEEDS_REBOOT = 6;
    public static final int STATE_UPDATE_AVAILABLE = 2;
    public final int currentPolicy;
    public final int currentState;
    public final VersionInfo currentVersionInfo;
    public final PendingUpdateInfo pendingUpdateInfo;

    @Retention(RetentionPolicy.SOURCE)
    public @interface State {
    }

    public UpdateManagerStatus(int currentState, VersionInfo currentVersionInfo, PendingUpdateInfo pendingUpdateInfo, int currentPolicy) {
        this.currentState = currentState;
        this.currentVersionInfo = currentVersionInfo;
        this.pendingUpdateInfo = pendingUpdateInfo;
        this.currentPolicy = currentPolicy;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.currentState);
        dest.writeString(this.currentVersionInfo.androidThingsVersion);
        dest.writeString(this.currentVersionInfo.oemVersion);
        dest.writeInt(this.currentPolicy);
        if (this.pendingUpdateInfo != null) {
            dest.writeString(this.pendingUpdateInfo.versionInfo.androidThingsVersion);
            dest.writeString(this.pendingUpdateInfo.versionInfo.oemVersion);
            dest.writeFloat(this.pendingUpdateInfo.downloadProgress);
            dest.writeLong(this.pendingUpdateInfo.downloadSize);
            return;
        }
        dest.writeString("");
        dest.writeString("");
        dest.writeFloat(0.0f);
        dest.writeLong(0);
    }

    private UpdateManagerStatus(Parcel in) {
        this.currentState = in.readInt();
        this.currentVersionInfo = new VersionInfo(in.readString(), in.readString());
        this.currentPolicy = in.readInt();
        String pendingSystemVersion = in.readString();
        String pendingOemVersion = in.readString();
        float progress = in.readFloat();
        long sizeInBytes = in.readLong();
        if ("".equals(pendingSystemVersion) && "".equals(pendingOemVersion) && 0.0d == ((double) progress) && 0 == sizeInBytes) {
            this.pendingUpdateInfo = null;
        } else {
            this.pendingUpdateInfo = new PendingUpdateInfo(new VersionInfo(pendingSystemVersion, pendingOemVersion), progress, sizeInBytes);
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{currentState=").append(nameForState(this.currentState));
        sb.append(",currentVersionInfo=").append(this.currentVersionInfo);
        sb.append(",pendingUpdateInfo=").append(this.pendingUpdateInfo);
        sb.append(",currentPolicy=").append(UpdateManager.nameForPolicy(this.currentPolicy));
        sb.append("}");
        return sb.toString();
    }

    private static String nameForState(int state) {
        switch (state) {
            case 0:
                return "STATE_IDLE";
            case 1:
                return "STATE_CHECKING_FOR_UPDATES";
            case 2:
                return "STATE_UPDATE_AVAILABLE";
            case 3:
                return "STATE_DOWNLOADING_UPDATE";
            case 5:
                return "STATE_FINALIZING_UPDATE";
            case 6:
                return "STATE_UPDATED_NEEDS_REBOOT";
            case 7:
                return "STATE_REPORTING_ERROR";
            case 8:
                return "STATE_BLOCKED";
            case 9:
                return "STATE_BLOCK_TIMING_OUT";
            case 10:
                return "STATE_OVERRIDE_CHECK";
            default:
                return Integer.toString(state);
        }
    }
}
