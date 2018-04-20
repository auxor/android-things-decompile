package com.google.android.things.lowpan;

import android.net.lowpan.LowpanBeaconInfo;
import android.net.lowpan.LowpanEnergyScanResult;
import android.net.lowpan.LowpanException;
import android.os.Handler;
import java.util.Collection;

public class LowpanScanner {
    final android.net.lowpan.LowpanScanner mActualLowpanScanner;

    public static abstract class Callback {
        public void onNetScanBeacon(LowpanBeaconInfo beacon) {
        }

        public void onEnergyScanResult(LowpanEnergyScanResult result) {
        }

        public void onScanFinished() {
        }
    }

    private static class CallbackInternal extends android.net.lowpan.LowpanScanner.Callback {
        final Callback mUserCallbackInstance;

        CallbackInternal(Callback userCallbackInstance) {
            this.mUserCallbackInstance = userCallbackInstance;
        }

        public void onNetScanBeacon(LowpanBeaconInfo beacon) {
            this.mUserCallbackInstance.onNetScanBeacon(new LowpanBeaconInfo(beacon));
        }

        public void onEnergyScanResult(LowpanEnergyScanResult result) {
            this.mUserCallbackInstance.onEnergyScanResult(new LowpanEnergyScanResult(result));
        }

        public void onScanFinished() {
            this.mUserCallbackInstance.onScanFinished();
        }
    }

    LowpanScanner(android.net.lowpan.LowpanScanner actualLowpanScanner) {
        this.mActualLowpanScanner = actualLowpanScanner;
    }

    public void setCallback(Callback cb, Handler handler) {
        android.net.lowpan.LowpanScanner.Callback callbackInternal = cb == null ? null : new CallbackInternal(cb);
        if (handler == null) {
            handler = new Handler();
        }
        this.mActualLowpanScanner.setCallback(callbackInternal, handler);
    }

    public void setCallback(Callback cb) {
        setCallback(cb, null);
    }

    public void setChannelMask(Collection<Integer> mask) {
        this.mActualLowpanScanner.setChannelMask(mask);
    }

    public Collection<Integer> getChannelMask() {
        return this.mActualLowpanScanner.getChannelMask();
    }

    public void addChannel(int channel) {
        this.mActualLowpanScanner.addChannel(channel);
    }

    public void setTxPower(int txPower) {
        this.mActualLowpanScanner.setTxPower(txPower);
    }

    public int getTxPower() {
        return this.mActualLowpanScanner.getTxPower();
    }

    public void startNetScan() throws LowpanException {
        try {
            this.mActualLowpanScanner.startNetScan();
        } catch (LowpanException x) {
            throw LowpanException.createFromActual(x);
        }
    }

    public void stopNetScan() {
        this.mActualLowpanScanner.stopNetScan();
    }

    public void startEnergyScan() throws LowpanException {
        try {
            this.mActualLowpanScanner.startEnergyScan();
        } catch (LowpanException x) {
            throw LowpanException.createFromActual(x);
        }
    }

    public void stopEnergyScan() {
        this.mActualLowpanScanner.stopEnergyScan();
    }
}
