package com.google.android.things.lowpan;

import com.android.internal.util.HexDump;

public class LowpanCredential {
    public static final int UNSPECIFIED_KEY_INDEX = 0;
    final android.net.lowpan.LowpanCredential mActualLowpanCredential;

    android.net.lowpan.LowpanCredential asActual() {
        return this.mActualLowpanCredential;
    }

    LowpanCredential(android.net.lowpan.LowpanCredential actualLowpanCredential) {
        this.mActualLowpanCredential = actualLowpanCredential;
    }

    public static LowpanCredential createMasterKey(byte[] masterKey) {
        return new LowpanCredential(android.net.lowpan.LowpanCredential.createMasterKey(masterKey));
    }

    public static LowpanCredential createMasterKey(String masterKeyHex) {
        return createMasterKey(HexDump.hexStringToByteArray(masterKeyHex));
    }

    public static LowpanCredential createMasterKey(byte[] masterKey, int keyIndex) {
        return new LowpanCredential(android.net.lowpan.LowpanCredential.createMasterKey(masterKey, keyIndex));
    }

    public byte[] getMasterKey() {
        return this.mActualLowpanCredential.getMasterKey();
    }

    public int getMasterKeyIndex() {
        return this.mActualLowpanCredential.getMasterKeyIndex();
    }

    public boolean isMasterKey() {
        return this.mActualLowpanCredential.isMasterKey();
    }

    public String toString() {
        return this.mActualLowpanCredential.toString();
    }

    public boolean equals(Object rhs) {
        return this.mActualLowpanCredential.equals(rhs);
    }

    public int hashCode() {
        return this.mActualLowpanCredential.hashCode();
    }
}
