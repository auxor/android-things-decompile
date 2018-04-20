package com.google.android.things.pio;

import android.os.Handler;
import java.io.IOException;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public abstract class Gpio extends IoBase {
    public static final int ACTIVE_HIGH = 1;
    public static final int ACTIVE_LOW = 0;
    public static final int DIRECTION_IN = 0;
    public static final int DIRECTION_OUT_INITIALLY_HIGH = 1;
    public static final int DIRECTION_OUT_INITIALLY_LOW = 2;
    public static final int EDGE_BOTH = 3;
    public static final int EDGE_FALLING = 2;
    public static final int EDGE_NONE = 0;
    public static final int EDGE_RISING = 1;

    @Retention(RetentionPolicy.SOURCE)
    public @interface ActiveType {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface Direction {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface Edge {
    }

    public abstract void close() throws IOException;

    public /* bridge */ /* synthetic */ String getName() {
        return super.getName();
    }

    public abstract boolean getValue() throws IOException;

    public abstract void registerGpioCallback(GpioCallback gpioCallback, Handler handler) throws IOException;

    public abstract void setActiveType(int i) throws IOException;

    public abstract void setDirection(int i) throws IOException;

    public abstract void setEdgeTriggerType(int i) throws IOException;

    public abstract void setValue(boolean z) throws IOException;

    public /* bridge */ /* synthetic */ String toString() {
        return super.toString();
    }

    public abstract void unregisterGpioCallback(GpioCallback gpioCallback);

    public final void registerGpioCallback(GpioCallback callback) throws IOException {
        registerGpioCallback(callback, null);
    }
}
