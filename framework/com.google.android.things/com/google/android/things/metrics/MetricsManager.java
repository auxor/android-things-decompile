package com.google.android.things.metrics;

import android.os.RemoteException;
import android.os.ServiceManager;
import android.util.Log;
import com.google.android.things.internal.metrics.IMetricsManagementService;
import com.google.android.things.internal.metrics.IMetricsManagementService.Stub;

public final class MetricsManager {
    private static final String MODIFY_METRICS_SETTINGS_PERMISSION = "com.google.android.things.permission.MODIFY_METRICS_SETTINGS";
    private static final String SERVICE_NAME = "metricsmanagementservice";
    private static final String TAG = MetricsManager.class.getSimpleName();

    public void enableMetrics(boolean enabled) {
        try {
            getService().enableMetrics(enabled);
        } catch (RemoteException e) {
            Log.e(TAG, "Exception while attempting to set metrics to " + enabled);
            throw e.rethrowAsRuntimeException();
        }
    }

    private IMetricsManagementService getService() {
        IMetricsManagementService metricsManagementService = Stub.asInterface(ServiceManager.getService(SERVICE_NAME));
        Log.v(TAG, "Service reference initialized");
        if (metricsManagementService != null) {
            return metricsManagementService;
        }
        Log.e(TAG, "Service not initialized");
        throw new IllegalStateException("MetricsManagement service not ready");
    }
}
