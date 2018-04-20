package com.google.android.things.userdriver;

import android.media.AudioFormat;
import android.os.Handler;
import com.google.android.things.audio.AudioConfiguration;
import java.nio.ByteBuffer;

public abstract class UserEffectDriver implements AutoCloseable {
    private final AudioConfiguration mDefaultConfig;
    private final Handler mHandler;

    public abstract void process(ByteBuffer byteBuffer) throws IllegalStateException;

    public abstract void setConfiguration(AudioFormat audioFormat) throws IllegalArgumentException;

    public UserEffectDriver(AudioFormat defaultConfig, Handler handler) {
        if (defaultConfig == null) {
            throw new IllegalArgumentException("Cannot create a UserEffectDriver with a null default configuration.");
        }
        this.mDefaultConfig = new AudioConfiguration(defaultConfig);
        this.mHandler = handler;
    }

    UserEffectDriver(AudioConfiguration defaultConfig, Handler handler) {
        if (defaultConfig == null) {
            throw new IllegalArgumentException("Cannot create a UserEffectDriver with a null default configuration.");
        }
        this.mDefaultConfig = defaultConfig;
        this.mHandler = handler;
    }

    final AudioConfiguration getDefaultConfiguration() {
        return this.mDefaultConfig;
    }

    final Handler getHandler() {
        return this.mHandler;
    }

    public boolean processTail(ByteBuffer data) throws IllegalStateException {
        return false;
    }

    public void reset() {
    }

    public void setEnable(boolean enable) throws IllegalStateException {
    }

    public void close() {
    }
}
