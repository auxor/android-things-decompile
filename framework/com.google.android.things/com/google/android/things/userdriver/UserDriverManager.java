package com.google.android.things.userdriver;

import android.media.AudioDeviceInfo;
import android.os.Handler;
import android.util.Log;

public class UserDriverManager {
    private static String TAG = "UserDriverManager";
    private static UserDriverManager mUserDriverManager;
    private AudioDeviceDriverManager mAudioDeviceDriverManager;
    private GpsDriver mGpsDriver;
    private InputDriverManager mInputDriverManager;
    private LowpanDriverManager mLowpanDriverManager;
    private SensorDriverManager mSensorDriverManager;
    private UserEffectDriverManager mUserEffectDriverManager;

    public static UserDriverManager getManager() {
        if (mUserDriverManager == null) {
            mUserDriverManager = new UserDriverManager();
        }
        return mUserDriverManager;
    }

    public void registerSensor(UserSensor sensor) {
        if (this.mSensorDriverManager == null) {
            this.mSensorDriverManager = new SensorDriverManager();
        }
        this.mSensorDriverManager.addSensor(sensor);
    }

    public void unregisterSensor(UserSensor sensor) {
        Log.w(TAG, "UserSensor unregistration not yet implemented");
    }

    public void registerInputDriver(InputDriver driver) {
        if (this.mInputDriverManager == null) {
            this.mInputDriverManager = new InputDriverManager();
        }
        this.mInputDriverManager.addInputDriver(driver);
    }

    public void unregisterInputDriver(InputDriver driver) {
        if (this.mInputDriverManager != null) {
            this.mInputDriverManager.removeInputDriver(driver);
        }
    }

    public void registerGpsDriver(GpsDriver driver) {
        if (this.mGpsDriver != null) {
            throw new IllegalArgumentException("You can only register one GPS driver at a time.");
        }
        driver.initialize();
        this.mGpsDriver = driver;
    }

    public void unregisterGpsDriver() {
        this.mGpsDriver = null;
    }

    public void registerUserEffectDriverFactory(UserEffectDriverFactory effectFactory, int attachmentType) {
        if (this.mUserEffectDriverManager == null) {
            this.mUserEffectDriverManager = new UserEffectDriverManager();
        }
        this.mUserEffectDriverManager.addFactory(effectFactory, attachmentType);
    }

    public void unregisterUserEffectDriverFactory(UserEffectDriverFactory effectFactory) {
        if (this.mUserEffectDriverManager != null) {
            this.mUserEffectDriverManager.removeFactory(effectFactory);
        }
    }

    public void registerAudioDeviceDriver(AudioDeviceDriver driver, AudioDeviceInfo device, Handler handler) {
        if (this.mAudioDeviceDriverManager == null) {
            this.mAudioDeviceDriverManager = new AudioDeviceDriverManager();
        }
        this.mAudioDeviceDriverManager.addAudioDeviceDriver(driver, device, handler);
    }

    public void unregisterAudioDeviceDriver(AudioDeviceDriver driver) {
        if (this.mAudioDeviceDriverManager != null) {
            this.mAudioDeviceDriverManager.removeAudioDeviceDriver(driver);
        }
    }

    private UserDriverManager() {
    }

    public void registerLowpanDriver(LowpanDriver driver, String ifaceName) {
        if (this.mLowpanDriverManager == null) {
            this.mLowpanDriverManager = new LowpanDriverManager();
        }
        this.mLowpanDriverManager.addLowpanDriver(driver, ifaceName);
    }

    public void registerLowpanDriver(LowpanDriver driver) {
        registerLowpanDriver(driver, "DEFAULT_INTERFACE_NAME");
    }

    public void unregisterLowpanDriver(LowpanDriver driver) {
        if (this.mLowpanDriverManager != null) {
            this.mLowpanDriverManager.removeLowpanDriver(driver);
        }
    }
}
