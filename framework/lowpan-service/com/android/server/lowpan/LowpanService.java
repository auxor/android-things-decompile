package com.android.server.lowpan;

import android.content.Context;
import android.util.Log;
import com.android.server.SystemService;

public final class LowpanService extends SystemService {
    private static final String TAG = LowpanService.class.getSimpleName();
    private final LowpanServiceImpl mImpl;

    public LowpanService(Context context) {
        super(context);
        this.mImpl = new LowpanServiceImpl(context);
    }

    public void onStart() {
        Log.i(TAG, "Registering lowpan");
        publishBinderService("lowpan", this.mImpl);
    }

    public void onBootPhase(int phase) {
        if (phase == 500) {
            this.mImpl.checkAndStartLowpan();
        }
    }
}
