package com.google.android.things.userdriver;

import android.os.ServiceManager;
import com.google.android.things.userdriver.IInputDriverService.Stub;

class InputDriverManager {
    private IInputDriverService mService = Stub.asInterface(ServiceManager.getService("inputdriverservice"));

    public InputDriverManager() {
        if (this.mService == null) {
            throw new IllegalStateException("InputDriverManager cannot connect to the input driver service");
        }
    }

    public synchronized void addInputDriver(InputDriver driver) {
        driver.initialize(this.mService);
    }

    public void removeInputDriver(InputDriver driver) {
        driver.uninitialize();
    }
}
