package com.google.android.things.lowpan;

public class LowpanChannelInfo {
    public static final float UNKNOWN_BANDWIDTH = 0.0f;
    public static final float UNKNOWN_FREQUENCY = 0.0f;
    public static final int UNKNOWN_POWER = Integer.MAX_VALUE;
    final android.net.lowpan.LowpanChannelInfo mActualLowpanChannelInfo;

    LowpanChannelInfo(android.net.lowpan.LowpanChannelInfo actualLowpanChannelInfo) {
        this.mActualLowpanChannelInfo = actualLowpanChannelInfo;
    }

    public String getName() {
        return this.mActualLowpanChannelInfo.getName();
    }

    public int getIndex() {
        return this.mActualLowpanChannelInfo.getIndex();
    }

    public int getMaxTransmitPower() {
        return this.mActualLowpanChannelInfo.getMaxTransmitPower();
    }

    public boolean isMaskedByRegulatoryDomain() {
        return this.mActualLowpanChannelInfo.isMaskedByRegulatoryDomain();
    }

    public float getSpectrumCenterFrequency() {
        return this.mActualLowpanChannelInfo.getSpectrumCenterFrequency();
    }

    public float getSpectrumBandwidth() {
        return this.mActualLowpanChannelInfo.getSpectrumBandwidth();
    }

    public String toString() {
        return this.mActualLowpanChannelInfo.toString();
    }

    public boolean equals(Object rhs) {
        return this.mActualLowpanChannelInfo.equals(rhs);
    }

    public int hashCode() {
        return this.mActualLowpanChannelInfo.hashCode();
    }
}
