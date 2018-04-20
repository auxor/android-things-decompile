package com.google.android.things.lowpan;

import java.util.Collection;

public class LowpanBeaconInfo {
    public static final int FLAG_CAN_ASSIST = 1;
    final android.net.lowpan.LowpanBeaconInfo mActualLowpanBeaconInfo;

    LowpanBeaconInfo(android.net.lowpan.LowpanBeaconInfo info) {
        this.mActualLowpanBeaconInfo = info;
    }

    android.net.lowpan.LowpanBeaconInfo asActual() {
        return this.mActualLowpanBeaconInfo;
    }

    public LowpanIdentity getLowpanIdentity() {
        return new LowpanIdentity(this.mActualLowpanBeaconInfo.getLowpanIdentity());
    }

    public int getRssi() {
        return this.mActualLowpanBeaconInfo.getRssi();
    }

    public int getLqi() {
        return this.mActualLowpanBeaconInfo.getLqi();
    }

    public byte[] getBeaconAddress() {
        return this.mActualLowpanBeaconInfo.getBeaconAddress();
    }

    public Collection<Integer> getFlags() {
        return this.mActualLowpanBeaconInfo.getFlags();
    }

    public boolean isFlagSet(int flag) {
        return this.mActualLowpanBeaconInfo.isFlagSet(flag);
    }

    public String toString() {
        return this.mActualLowpanBeaconInfo.toString();
    }

    public boolean equals(Object rhs) {
        return this.mActualLowpanBeaconInfo.equals(rhs);
    }

    public int hashCode() {
        return this.mActualLowpanBeaconInfo.hashCode();
    }
}
