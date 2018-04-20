package com.google.android.things.lowpan;

public class LowpanIdentity {
    public static final int UNSPECIFIED_CHANNEL = -1;
    public static final int UNSPECIFIED_PANID = -1;
    final android.net.lowpan.LowpanIdentity mActualLowpanIdentity;

    public static class Builder {
        private final android.net.lowpan.LowpanIdentity.Builder mBuilder = new android.net.lowpan.LowpanIdentity.Builder();

        public Builder setName(String x) {
            this.mBuilder.setName(x);
            return this;
        }

        public Builder setRawName(byte[] x) {
            this.mBuilder.setRawName(x);
            return this;
        }

        public Builder setXpanid(byte[] x) {
            this.mBuilder.setXpanid(x);
            return this;
        }

        public Builder setPanid(int x) {
            this.mBuilder.setPanid(x);
            return this;
        }

        public Builder setChannel(int x) {
            this.mBuilder.setChannel(x);
            return this;
        }

        public LowpanIdentity build() {
            return new LowpanIdentity(this.mBuilder.build());
        }
    }

    android.net.lowpan.LowpanIdentity asActual() {
        return this.mActualLowpanIdentity;
    }

    LowpanIdentity(android.net.lowpan.LowpanIdentity actualLowpanIdentity) {
        this.mActualLowpanIdentity = actualLowpanIdentity;
    }

    public String getName() {
        return this.mActualLowpanIdentity.getName();
    }

    public byte[] getRawName() {
        return this.mActualLowpanIdentity.getRawName();
    }

    public boolean isNameValid() {
        return this.mActualLowpanIdentity.isNameValid();
    }

    public String getType() {
        return this.mActualLowpanIdentity.getType();
    }

    public byte[] getXpanid() {
        return this.mActualLowpanIdentity.getXpanid();
    }

    public int getPanid() {
        return this.mActualLowpanIdentity.getPanid();
    }

    public int getChannel() {
        return this.mActualLowpanIdentity.getChannel();
    }

    public String toString() {
        return this.mActualLowpanIdentity.toString();
    }

    public boolean equals(Object rhs) {
        return this.mActualLowpanIdentity.equals(rhs);
    }

    public int hashCode() {
        return this.mActualLowpanIdentity.hashCode();
    }
}
