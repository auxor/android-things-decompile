package com.google.android.things.lowpan;

import android.net.lowpan.LowpanCredential;
import android.net.lowpan.LowpanProvision;

public class LowpanProvisioningParams {
    final LowpanProvision mActualLowpanProvisioningParams;

    public static class Builder {
        private final android.net.lowpan.LowpanProvision.Builder mBuilder = new android.net.lowpan.LowpanProvision.Builder();

        public Builder setLowpanIdentity(LowpanIdentity identity) {
            this.mBuilder.setLowpanIdentity(identity.mActualLowpanIdentity);
            return this;
        }

        public Builder setLowpanCredential(LowpanCredential credential) {
            this.mBuilder.setLowpanCredential(credential.mActualLowpanCredential);
            return this;
        }

        public LowpanProvisioningParams build() {
            return new LowpanProvisioningParams(this.mBuilder.build());
        }
    }

    LowpanProvision asActual() {
        return this.mActualLowpanProvisioningParams;
    }

    LowpanProvisioningParams(LowpanProvision actualLowpanProvisioningParams) {
        this.mActualLowpanProvisioningParams = actualLowpanProvisioningParams;
    }

    public LowpanIdentity getLowpanIdentity() {
        return new LowpanIdentity(this.mActualLowpanProvisioningParams.getLowpanIdentity());
    }

    public LowpanCredential getLowpanCredential() {
        LowpanCredential actualCredential = this.mActualLowpanProvisioningParams.getLowpanCredential();
        if (actualCredential == null) {
            return null;
        }
        return new LowpanCredential(actualCredential);
    }

    public String toString() {
        return this.mActualLowpanProvisioningParams.toString();
    }

    public boolean equals(Object rhs) {
        return this.mActualLowpanProvisioningParams.equals(rhs);
    }

    public int hashCode() {
        return this.mActualLowpanProvisioningParams.hashCode();
    }
}
