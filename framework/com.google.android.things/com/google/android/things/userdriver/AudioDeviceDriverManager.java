package com.google.android.things.userdriver;

import android.media.AudioDeviceInfo;
import android.os.Handler;
import android.os.RemoteException;
import android.os.ServiceManager;
import com.google.android.things.userdriver.IAudioDeviceDriverManager.Stub;
import java.util.HashMap;
import java.util.Map;

class AudioDeviceDriverManager {
    private final Map<AudioDeviceDriver, IAudioDeviceDriver> mDriverList = new HashMap();
    private final IAudioDeviceDriverManager mService = Stub.asInterface(ServiceManager.getService("audiodeviceuserdriver"));

    private class WrappedAudioDeviceDriver extends IAudioDeviceDriver.Stub {
        private final AudioDeviceDriver mDriver;
        private final Handler mHandler;

        public WrappedAudioDeviceDriver(AudioDeviceDriver driver, Handler handler) {
            this.mDriver = driver;
            this.mHandler = handler;
        }

        private void completeCallback(int callbackId, int error) {
            try {
                AudioDeviceDriverManager.this.mService.onStandbyCallbackComplete(this, callbackId, error);
            } catch (RemoteException e) {
                e.rethrowAsRuntimeException();
            }
        }

        public void onStandby(final boolean inStandby, final int callbackId) {
            this.mHandler.post(new Runnable() {
                public void run() {
                    try {
                        WrappedAudioDeviceDriver.this.mDriver.onStandby(inStandby);
                        WrappedAudioDeviceDriver.this.completeCallback(callbackId, 0);
                    } catch (Exception e) {
                        WrappedAudioDeviceDriver.this.completeCallback(callbackId, -1);
                        throw e;
                    }
                }
            });
        }
    }

    public AudioDeviceDriverManager() {
        if (this.mService == null) {
            throw new IllegalStateException("AudioDeviceDriverManager cannot connect to the audio bus manager service");
        }
    }

    public void addAudioDeviceDriver(AudioDeviceDriver driver, AudioDeviceInfo device, Handler handler) {
        if (handler == null) {
            handler = new Handler();
        }
        IAudioDeviceDriver binderDriver = new WrappedAudioDeviceDriver(driver, handler);
        try {
            this.mService.registerDriver(device.getId(), binderDriver);
        } catch (RemoteException e) {
            e.rethrowAsRuntimeException();
        }
        this.mDriverList.put(driver, binderDriver);
    }

    public void removeAudioDeviceDriver(AudioDeviceDriver driver) {
        try {
            this.mService.unregisterDriver((IAudioDeviceDriver) this.mDriverList.remove(driver));
        } catch (RemoteException e) {
            e.rethrowAsRuntimeException();
        }
    }
}
