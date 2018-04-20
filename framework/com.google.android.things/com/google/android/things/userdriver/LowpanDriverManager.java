package com.google.android.things.userdriver;

import android.os.RemoteException;
import android.os.ServiceManager;
import com.google.android.things.userdriver.ILowpanDriverService.Stub;

class LowpanDriverManager {
    public static final String DEFAULT_INTERFACE_NAME = "DEFAULT_INTERFACE_NAME";
    private ILowpanDriverService mService = Stub.asInterface(ServiceManager.getService("lowpandriverservice"));

    public LowpanDriverManager() {
        if (this.mService == null) {
            throw new IllegalStateException("LowpanDriverManager cannot connect to the LoWPAN driver service");
        }
    }

    public synchronized void addLowpanDriver(LowpanDriver driver, String ifaceName) {
        try {
            this.mService.addLowpanDriver(driver.asInterface(), ifaceName);
        } catch (RemoteException x) {
            throw x.rethrowAsRuntimeException();
        }
    }

    public void removeLowpanDriver(LowpanDriver driver) {
        try {
            this.mService.removeLowpanDriver(driver.asInterface());
        } catch (RemoteException x) {
            throw x.rethrowAsRuntimeException();
        }
    }
}
