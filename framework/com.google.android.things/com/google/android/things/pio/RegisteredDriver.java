package com.google.android.things.pio;

import java.io.IOException;

interface RegisteredDriver {
    void unregister() throws IOException;
}
