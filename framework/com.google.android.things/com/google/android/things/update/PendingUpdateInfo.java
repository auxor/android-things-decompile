package com.google.android.things.update;

public class PendingUpdateInfo {
    public final float downloadProgress;
    public final long downloadSize;
    public final VersionInfo versionInfo;

    public PendingUpdateInfo(VersionInfo versionInfo, float downloadProgress, long downloadSize) {
        this.versionInfo = versionInfo;
        this.downloadProgress = downloadProgress;
        this.downloadSize = downloadSize;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{versionInfo=").append(this.versionInfo);
        sb.append(",downloadProgress=").append(this.downloadProgress);
        sb.append(",downloadSize=").append(this.downloadSize);
        sb.append("}");
        return sb.toString();
    }
}
