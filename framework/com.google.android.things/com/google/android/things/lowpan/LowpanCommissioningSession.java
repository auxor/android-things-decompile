package com.google.android.things.lowpan;

import android.os.Handler;

public class LowpanCommissioningSession {
    final android.net.lowpan.LowpanCommissioningSession mActualLowpanCommissioningSession;

    public static abstract class Callback {
        public void onReceiveFromCommissioner(byte[] packet) {
        }

        public void onClosed() {
        }
    }

    private static class CallbackInternal extends android.net.lowpan.LowpanCommissioningSession.Callback {
        private final Callback mUserCallbackInstance;

        CallbackInternal(Callback userCallbackInstance) {
            this.mUserCallbackInstance = userCallbackInstance;
        }

        public void onReceiveFromCommissioner(byte[] packet) {
            this.mUserCallbackInstance.onReceiveFromCommissioner(packet);
        }

        public void onClosed() {
            this.mUserCallbackInstance.onClosed();
        }
    }

    LowpanCommissioningSession(android.net.lowpan.LowpanCommissioningSession actualLowpanCommissioningSession) {
        this.mActualLowpanCommissioningSession = actualLowpanCommissioningSession;
    }

    public LowpanBeaconInfo getBeaconInfo() {
        return new LowpanBeaconInfo(this.mActualLowpanCommissioningSession.getBeaconInfo());
    }

    public void sendToCommissioner(byte[] packet) {
        this.mActualLowpanCommissioningSession.sendToCommissioner(packet);
    }

    public void setCallback(Callback cb, Handler handler) {
        this.mActualLowpanCommissioningSession.setCallback(cb == null ? null : new CallbackInternal(cb), handler);
    }

    public void setCallback(Callback cb) {
        setCallback(cb, null);
    }

    public void close() {
        this.mActualLowpanCommissioningSession.close();
    }
}
