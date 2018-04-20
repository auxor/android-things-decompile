package com.google.android.things.pio;

import android.os.Binder;
import android.os.RemoteException;
import android.os.ServiceSpecificException;
import java.io.IOException;

class PwmImpl extends Pwm {
    private final String mName;
    private final IPeripheralManagerClient mPioClient;

    PwmImpl(IPeripheralManagerClient service, Binder lifeline, String name) throws IOException {
        this.mName = name;
        try {
            this.mPioClient = service.GetPwmClient(name, lifeline);
            this.mPioClient.OpenPwm(name);
        } catch (ServiceSpecificException e) {
            throw new PioException(e);
        } catch (RemoteException e2) {
            throw new PioException(e2);
        }
    }

    public String getName() {
        return this.mName;
    }

    public void setPwmDutyCycle(double duty_cycle) throws IOException {
        try {
            this.mPioClient.PwmSetDutyCycle(this.mName, duty_cycle);
        } catch (ServiceSpecificException e) {
            throw new PioException(e);
        } catch (RemoteException e2) {
            throw new PioException(e2);
        }
    }

    public void setPwmFrequencyHz(double freq_hz) throws IOException {
        try {
            this.mPioClient.PwmSetFrequencyHz(this.mName, freq_hz);
        } catch (ServiceSpecificException e) {
            throw new PioException(e);
        } catch (RemoteException e2) {
            throw new PioException(e2);
        }
    }

    public void setEnabled(boolean enabled) throws IOException {
        try {
            this.mPioClient.PwmSetEnabled(this.mName, enabled);
        } catch (ServiceSpecificException e) {
            throw new PioException(e);
        } catch (RemoteException e2) {
            throw new PioException(e2);
        }
    }

    public void close() throws IOException {
        try {
            this.mPioClient.ReleasePwm(this.mName);
        } catch (ServiceSpecificException e) {
            throw new PioException(e);
        } catch (RemoteException e2) {
            throw new PioException(e2);
        }
    }
}
