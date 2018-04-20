package com.google.android.things.lowpan;

public class LowpanEnergyScanResult {
    android.net.lowpan.LowpanEnergyScanResult mActualLowpanEnergyScanResult;

    LowpanEnergyScanResult(android.net.lowpan.LowpanEnergyScanResult actualLowpanEnergyScanResult) {
        this.mActualLowpanEnergyScanResult = actualLowpanEnergyScanResult;
    }

    public int getChannel() {
        return this.mActualLowpanEnergyScanResult.getChannel();
    }

    public int getMaxRssi() {
        return this.mActualLowpanEnergyScanResult.getMaxRssi();
    }

    public String toString() {
        return this.mActualLowpanEnergyScanResult.toString();
    }
}
