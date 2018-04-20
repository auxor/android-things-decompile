package com.google.android.things.userdriver;

import java.io.IOException;

public abstract class UserSensorDriver {
    public abstract UserSensorReading read() throws IOException;

    public void setEnabled(boolean enabled) throws IOException {
    }
}
