package com.google.android.things.pio;

import java.io.IOException;

public abstract class Pwm extends IoBase {
    public abstract void close() throws IOException;

    public /* bridge */ /* synthetic */ String getName() {
        return super.getName();
    }

    public abstract void setEnabled(boolean z) throws IOException;

    public abstract void setPwmDutyCycle(double d) throws IOException;

    public abstract void setPwmFrequencyHz(double d) throws IOException;

    public /* bridge */ /* synthetic */ String toString() {
        return super.toString();
    }
}
