package com.google.android.things.pio;

import java.io.IOException;

public abstract class PwmDriver extends Pwm {
    public abstract void close() throws IOException;

    public abstract void open() throws IOException;

    public String getName() {
        return null;
    }
}
