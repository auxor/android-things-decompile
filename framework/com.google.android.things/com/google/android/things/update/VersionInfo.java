package com.google.android.things.update;

public class VersionInfo {
    public final String androidThingsVersion;
    public final String oemVersion;

    public VersionInfo(String androidThingsVersion, String oemVersion) {
        this.androidThingsVersion = androidThingsVersion;
        this.oemVersion = oemVersion;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{androidThingsVersion=").append(this.androidThingsVersion);
        sb.append(",oemVersion=").append(this.oemVersion);
        sb.append("}");
        return sb.toString();
    }
}
