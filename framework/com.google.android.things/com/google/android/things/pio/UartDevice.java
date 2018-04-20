package com.google.android.things.pio;

import android.os.Handler;
import java.io.IOException;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public abstract class UartDevice extends IoBase {
    public static final int FLUSH_IN = 0;
    public static final int FLUSH_IN_OUT = 2;
    public static final int FLUSH_OUT = 1;
    public static final int HW_FLOW_CONTROL_AUTO_RTSCTS = 1;
    public static final int HW_FLOW_CONTROL_NONE = 0;
    public static final int MODEM_CONTROL_CD = 64;
    public static final int MODEM_CONTROL_CTS = 32;
    public static final int MODEM_CONTROL_DSR = 256;
    public static final int MODEM_CONTROL_DTR = 2;
    public static final int MODEM_CONTROL_LE = 1;
    public static final int MODEM_CONTROL_RI = 128;
    public static final int MODEM_CONTROL_RTS = 4;
    public static final int MODEM_CONTROL_SR = 16;
    public static final int MODEM_CONTROL_ST = 8;
    public static final int PARITY_EVEN = 1;
    public static final int PARITY_MARK = 3;
    public static final int PARITY_NONE = 0;
    public static final int PARITY_ODD = 2;
    public static final int PARITY_SPACE = 4;

    @Retention(RetentionPolicy.SOURCE)
    public @interface FlushDirection {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface HwFlowControl {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface ModemControl {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface Parity {
    }

    public abstract void clearModemControl(int i) throws IOException;

    public abstract void close() throws IOException;

    public abstract void flush(int i) throws IOException;

    public /* bridge */ /* synthetic */ String getName() {
        return super.getName();
    }

    public abstract int read(byte[] bArr, int i) throws IOException;

    public abstract void registerUartDeviceCallback(UartDeviceCallback uartDeviceCallback, Handler handler) throws IOException;

    public abstract void sendBreak(int i) throws IOException;

    public abstract void setBaudrate(int i) throws IOException;

    public abstract void setDataSize(int i) throws IOException;

    public abstract void setHardwareFlowControl(int i) throws IOException;

    public abstract void setModemControl(int i) throws IOException;

    public abstract void setParity(int i) throws IOException;

    public abstract void setStopBits(int i) throws IOException;

    public /* bridge */ /* synthetic */ String toString() {
        return super.toString();
    }

    public abstract void unregisterUartDeviceCallback(UartDeviceCallback uartDeviceCallback);

    public abstract int write(byte[] bArr, int i) throws IOException;

    public final void registerUartDeviceCallback(UartDeviceCallback callback) throws IOException {
        registerUartDeviceCallback(callback, null);
    }
}
