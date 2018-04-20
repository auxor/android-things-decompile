package com.google.android.things.pio;

import java.io.Closeable;

abstract class IoBase implements Closeable {
    IoBase() {
    }

    public String getName() {
        return null;
    }

    public String toString() {
        String name = getName();
        if (name == null) {
            return super.toString();
        }
        return name;
    }
}
