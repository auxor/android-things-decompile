package com.google.android.things.lowpan;

import com.google.android.things.lowpan.LowpanInterface.Callback;

final /* synthetic */ class -$Lambda$BbY8jmNkP4LBgtyiBmEwmS1DCOw implements Runnable {
    private final /* synthetic */ Object -$f0;
    private final /* synthetic */ Object -$f1;

    private final /* synthetic */ void $m$0() {
        ((Callback) this.-$f0).onProvisionException((Exception) this.-$f1);
    }

    public /* synthetic */ -$Lambda$BbY8jmNkP4LBgtyiBmEwmS1DCOw(Object obj, Object obj2) {
        this.-$f0 = obj;
        this.-$f1 = obj2;
    }

    public final void run() {
        $m$0();
    }
}
