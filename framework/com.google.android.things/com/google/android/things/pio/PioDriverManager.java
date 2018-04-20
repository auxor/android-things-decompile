package com.google.android.things.pio;

import android.os.IBinder;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.ServiceSpecificException;
import com.google.android.things.pio.IUserDriverManager.Stub;
import java.io.FileDescriptor;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class PioDriverManager {
    private final ConcurrentMap<IBinder, DriverClient> mDriverClients = new ConcurrentHashMap();
    private final RegisteredDriverManager<RegisteredDeviceDriver<GpioDriver>> mGpioManager = new RegisteredDriverManager();
    private final RegisteredDriverManager<RegisteredBusDriver<I2cBusDriver, Integer, I2cDevice>> mI2cManager = new RegisteredDriverManager();
    private final IPeripheralManager mPioManager;
    private final RegisteredDriverManager<RegisteredDeviceDriver<PwmDriver>> mPwmManager = new RegisteredDriverManager();
    private final Map<String, SpiNames> mSpiDeviceNameMap = new ConcurrentHashMap();
    private final RegisteredDriverManager<RegisteredBusDriver<SpiBusDriver, String, SpiDevice>> mSpiManager = new RegisteredDriverManager();
    private final RegisteredDriverManager<RegisteredDeviceDriver<UartDeviceDriver>> mUartManager = new RegisteredDriverManager();
    private final IUserDriverManager mUserDriverManager = new Stub() {
        public IPeripheralManagerClient GetClient(IBinder lifeline) {
            DriverClient new_client = new DriverClient(lifeline);
            DriverClient client = (DriverClient) PioDriverManager.this.mDriverClients.putIfAbsent(lifeline, new_client);
            if (client != null) {
                return client;
            }
            return new_client;
        }
    };

    private class DriverClient extends IPeripheralManagerClient.Stub {
        private IBinder mLifeline;

        private class UserDriverGpioCallback extends UserDriverCallback {
            private final GpioCallback mGpioCallback = new GpioCallback() {
                public boolean onGpioEdge(Gpio gpio) {
                    return UserDriverGpioCallback.this.triggerEvent();
                }

                public void onGpioError(Gpio gpio, int error) {
                    UserDriverGpioCallback.this.triggerError();
                }
            };

            public UserDriverGpioCallback(String ioName) {
                super(ioName);
            }

            void registerCallback() throws IOException {
                Throwable th;
                Throwable th2 = null;
                DriverLock driverLock = null;
                try {
                    driverLock = getDriverLock();
                    ((GpioDriver) driverLock.getDriver()).registerGpioCallback(this.mGpioCallback, getHandler());
                    if (driverLock != null) {
                        try {
                            driverLock.close();
                        } catch (Throwable th3) {
                            th2 = th3;
                        }
                    }
                    if (th2 != null) {
                        throw th2;
                    }
                    return;
                } catch (Throwable th22) {
                    Throwable th4 = th22;
                    th22 = th;
                    th = th4;
                }
                if (driverLock != null) {
                    try {
                        driverLock.close();
                    } catch (Throwable th5) {
                        if (th22 == null) {
                            th22 = th5;
                        } else if (th22 != th5) {
                            th22.addSuppressed(th5);
                        }
                    }
                }
                if (th22 != null) {
                    throw th22;
                }
                throw th;
            }

            void unregisterCallback() {
                Throwable th;
                Throwable th2 = null;
                DriverLock driverLock = null;
                try {
                    driverLock = getDriverLock();
                    ((GpioDriver) driverLock.getDriver()).unregisterGpioCallback(this.mGpioCallback);
                    if (driverLock != null) {
                        try {
                            driverLock.close();
                        } catch (Throwable th3) {
                            th2 = th3;
                        }
                    }
                    if (th2 != null) {
                        try {
                            throw th2;
                        } catch (IOException e) {
                            return;
                        }
                    }
                    return;
                } catch (Throwable th22) {
                    Throwable th4 = th22;
                    th22 = th;
                    th = th4;
                }
                if (driverLock != null) {
                    try {
                        driverLock.close();
                    } catch (Throwable th5) {
                        if (th22 == null) {
                            th22 = th5;
                        } else if (th22 != th5) {
                            th22.addSuppressed(th5);
                        }
                    }
                }
                if (th22 != null) {
                    throw th22;
                }
                throw th;
            }

            DriverLock<GpioDriver> getDriverLock() throws IOException {
                return DriverClient.this.lockGpio(getIoName());
            }
        }

        private class UserDriverUartDeviceCallback extends UserDriverCallback {
            private final UartDeviceCallback mUartCallback = new UartDeviceCallback() {
                public boolean onUartDeviceDataAvailable(UartDevice uart) {
                    return UserDriverUartDeviceCallback.this.triggerEvent();
                }

                public void onUartDeviceError(UartDevice uart, int error) {
                    UserDriverUartDeviceCallback.this.triggerError();
                }
            };

            public UserDriverUartDeviceCallback(String ioName) {
                super(ioName);
            }

            void registerCallback() throws IOException {
                Throwable th;
                Throwable th2 = null;
                DriverLock driverLock = null;
                try {
                    driverLock = getDriverLock();
                    ((UartDeviceDriver) driverLock.getDriver()).registerUartDeviceCallback(this.mUartCallback, getHandler());
                    if (driverLock != null) {
                        try {
                            driverLock.close();
                        } catch (Throwable th3) {
                            th2 = th3;
                        }
                    }
                    if (th2 != null) {
                        throw th2;
                    }
                    return;
                } catch (Throwable th22) {
                    Throwable th4 = th22;
                    th22 = th;
                    th = th4;
                }
                if (driverLock != null) {
                    try {
                        driverLock.close();
                    } catch (Throwable th5) {
                        if (th22 == null) {
                            th22 = th5;
                        } else if (th22 != th5) {
                            th22.addSuppressed(th5);
                        }
                    }
                }
                if (th22 != null) {
                    throw th22;
                }
                throw th;
            }

            void unregisterCallback() {
                Throwable th;
                Throwable th2 = null;
                DriverLock driverLock = null;
                try {
                    driverLock = getDriverLock();
                    ((UartDeviceDriver) driverLock.getDriver()).unregisterUartDeviceCallback(this.mUartCallback);
                    if (driverLock != null) {
                        try {
                            driverLock.close();
                        } catch (Throwable th3) {
                            th2 = th3;
                        }
                    }
                    if (th2 != null) {
                        try {
                            throw th2;
                        } catch (IOException e) {
                            return;
                        }
                    }
                    return;
                } catch (Throwable th22) {
                    Throwable th4 = th22;
                    th22 = th;
                    th = th4;
                }
                if (driverLock != null) {
                    try {
                        driverLock.close();
                    } catch (Throwable th5) {
                        if (th22 == null) {
                            th22 = th5;
                        } else if (th22 != th5) {
                            th22.addSuppressed(th5);
                        }
                    }
                }
                if (th22 != null) {
                    throw th22;
                }
                throw th;
            }

            DriverLock<UartDeviceDriver> getDriverLock() throws IOException {
                return DriverClient.this.lockUartDevice(getIoName());
            }
        }

        public DriverClient(IBinder lifeline) {
            this.mLifeline = lifeline;
        }

        private DriverLock<GpioDriver> lockGpio(String name) throws PioException {
            return ((RegisteredDeviceDriver) PioDriverManager.this.mGpioManager.getRegisteredDriverOrThrow(name)).lockDriver(this.mLifeline);
        }

        private DriverLock<PwmDriver> lockPwm(String name) throws PioException {
            return ((RegisteredDeviceDriver) PioDriverManager.this.mPwmManager.getRegisteredDriverOrThrow(name)).lockDriver(this.mLifeline);
        }

        private DriverLock<UartDeviceDriver> lockUartDevice(String name) throws PioException {
            return ((RegisteredDeviceDriver) PioDriverManager.this.mUartManager.getRegisteredDriverOrThrow(name)).lockDriver(this.mLifeline);
        }

        private DriverLock<I2cDevice> lockI2cDevice(String name, int address) throws PioException {
            return ((RegisteredBusDriver) PioDriverManager.this.mI2cManager.getRegisteredDriverOrThrow(name)).lockDevice(this.mLifeline, Integer.valueOf(address));
        }

        private SpiNames getSpiNamesOrThrow(String deviceName) throws PioException {
            SpiNames names = (SpiNames) PioDriverManager.this.mSpiDeviceNameMap.get(deviceName);
            if (names != null) {
                return names;
            }
            throw PioException.createUnknownNameException(deviceName);
        }

        private DriverLock<SpiDevice> lockSpiDevice(String deviceName) throws PioException {
            SpiNames names = getSpiNamesOrThrow(deviceName);
            return ((RegisteredBusDriver) PioDriverManager.this.mSpiManager.getRegisteredDriverOrThrow(names.getBusName())).lockDevice(this.mLifeline, names.getCsName());
        }

        public void ListGpio(List<String> list) {
            throw new UnsupportedOperationException("Only implemented by the native client");
        }

        public IPeripheralManagerClient GetGpioClient(String name, IBinder lifeline) {
            throw new UnsupportedOperationException("Only implemented by the native client");
        }

        public void OpenGpio(String name) throws RemoteException {
            try {
                ((RegisteredDeviceDriver) PioDriverManager.this.mGpioManager.getRegisteredDriverOrThrow(name)).openDriver(this.mLifeline, new OpenMethod<GpioDriver>() {
                    public void call(GpioDriver driver) throws IOException {
                        driver.open();
                    }
                });
            } catch (IOException e) {
                throw PioException.createServiceSpecificException(e);
            }
        }

        public void ReleaseGpio(String name) throws RemoteException {
            try {
                ((RegisteredDeviceDriver) PioDriverManager.this.mGpioManager.getRegisteredDriverOrThrow(name)).closeDriver(this.mLifeline);
            } catch (IOException e) {
                throw PioException.createServiceSpecificException(e);
            }
        }

        public void SetGpioEdge(String name, int type) throws RemoteException {
            Throwable th;
            Throwable th2 = null;
            DriverLock driverLock = null;
            try {
                driverLock = lockGpio(name);
                ((GpioDriver) driverLock.getDriver()).setEdgeTriggerType(type);
                if (driverLock != null) {
                    try {
                        driverLock.close();
                    } catch (Throwable th3) {
                        th2 = th3;
                    }
                }
                if (th2 != null) {
                    try {
                        throw th2;
                    } catch (IOException e) {
                        throw PioException.createServiceSpecificException(e);
                    }
                }
                return;
            } catch (Throwable th22) {
                Throwable th4 = th22;
                th22 = th;
                th = th4;
            }
            if (driverLock != null) {
                try {
                    driverLock.close();
                } catch (Throwable th5) {
                    if (th22 == null) {
                        th22 = th5;
                    } else if (th22 != th5) {
                        th22.addSuppressed(th5);
                    }
                }
            }
            if (th22 != null) {
                throw th22;
            }
            throw th;
        }

        public void SetGpioActiveType(String name, int type) throws RemoteException {
            Throwable th;
            Throwable th2 = null;
            DriverLock driverLock = null;
            try {
                driverLock = lockGpio(name);
                ((GpioDriver) driverLock.getDriver()).setActiveType(type);
                if (driverLock != null) {
                    try {
                        driverLock.close();
                    } catch (Throwable th3) {
                        th2 = th3;
                    }
                }
                if (th2 != null) {
                    try {
                        throw th2;
                    } catch (IOException e) {
                        throw PioException.createServiceSpecificException(e);
                    }
                }
                return;
            } catch (Throwable th22) {
                Throwable th4 = th22;
                th22 = th;
                th = th4;
            }
            if (driverLock != null) {
                try {
                    driverLock.close();
                } catch (Throwable th5) {
                    if (th22 == null) {
                        th22 = th5;
                    } else if (th22 != th5) {
                        th22.addSuppressed(th5);
                    }
                }
            }
            if (th22 != null) {
                throw th22;
            }
            throw th;
        }

        public void SetGpioDirection(String name, int direction) throws RemoteException {
            Throwable th;
            Throwable th2 = null;
            DriverLock driverLock = null;
            try {
                driverLock = lockGpio(name);
                ((GpioDriver) driverLock.getDriver()).setDirection(direction);
                if (driverLock != null) {
                    try {
                        driverLock.close();
                    } catch (Throwable th3) {
                        th2 = th3;
                    }
                }
                if (th2 != null) {
                    try {
                        throw th2;
                    } catch (IOException e) {
                        throw PioException.createServiceSpecificException(e);
                    }
                }
                return;
            } catch (Throwable th22) {
                Throwable th4 = th22;
                th22 = th;
                th = th4;
            }
            if (driverLock != null) {
                try {
                    driverLock.close();
                } catch (Throwable th5) {
                    if (th22 == null) {
                        th22 = th5;
                    } else if (th22 != th5) {
                        th22.addSuppressed(th5);
                    }
                }
            }
            if (th22 != null) {
                throw th22;
            }
            throw th;
        }

        public void SetGpioValue(String name, boolean value) throws RemoteException {
            Throwable th;
            Throwable th2 = null;
            DriverLock driverLock = null;
            try {
                driverLock = lockGpio(name);
                ((GpioDriver) driverLock.getDriver()).setValue(value);
                if (driverLock != null) {
                    try {
                        driverLock.close();
                    } catch (Throwable th3) {
                        th2 = th3;
                    }
                }
                if (th2 != null) {
                    try {
                        throw th2;
                    } catch (IOException e) {
                        throw PioException.createServiceSpecificException(e);
                    }
                }
                return;
            } catch (Throwable th22) {
                Throwable th4 = th22;
                th22 = th;
                th = th4;
            }
            if (driverLock != null) {
                try {
                    driverLock.close();
                } catch (Throwable th5) {
                    if (th22 == null) {
                        th22 = th5;
                    } else if (th22 != th5) {
                        th22.addSuppressed(th5);
                    }
                }
            }
            if (th22 != null) {
                throw th22;
            }
            throw th;
        }

        public boolean GetGpioValue(String name) throws RemoteException {
            Throwable th;
            Throwable th2 = null;
            DriverLock driverLock = null;
            try {
                driverLock = lockGpio(name);
                boolean value = ((GpioDriver) driverLock.getDriver()).getValue();
                if (driverLock != null) {
                    try {
                        driverLock.close();
                    } catch (Throwable th3) {
                        th2 = th3;
                    }
                }
                if (th2 == null) {
                    return value;
                }
                try {
                    throw th2;
                } catch (IOException e) {
                    throw PioException.createServiceSpecificException(e);
                }
            } catch (Throwable th22) {
                Throwable th4 = th22;
                th22 = th;
                th = th4;
            }
            if (driverLock != null) {
                try {
                    driverLock.close();
                } catch (Throwable th5) {
                    if (th22 == null) {
                        th22 = th5;
                    } else if (th22 != th5) {
                        th22.addSuppressed(th5);
                    }
                }
            }
            if (th22 != null) {
                throw th22;
            }
            throw th;
        }

        public FileDescriptor GetGpioPollingFd(String name) {
            Throwable th;
            Throwable th2 = null;
            DriverLock driverLock = null;
            try {
                DriverLock<GpioDriver> lock = lockGpio(name);
                if (lock.getCallback() == null) {
                    lock.setCallback(new UserDriverGpioCallback(name));
                }
                FileDescriptor registerNewPipeLocked = lock.getCallback().registerNewPipeLocked();
                if (lock != null) {
                    try {
                        lock.close();
                    } catch (Throwable th3) {
                        th2 = th3;
                    }
                }
                if (th2 == null) {
                    return registerNewPipeLocked;
                }
                try {
                    throw th2;
                } catch (IOException e) {
                    throw PioException.createServiceSpecificException(e);
                }
            } catch (Throwable th22) {
                Throwable th4 = th22;
                th22 = th;
                th = th4;
            }
            if (driverLock != null) {
                try {
                    driverLock.close();
                } catch (Throwable th5) {
                    if (th22 == null) {
                        th22 = th5;
                    } else if (th22 != th5) {
                        th22.addSuppressed(th5);
                    }
                }
            }
            if (th22 != null) {
                throw th22;
            }
            throw th;
        }

        public void ListPwm(List<String> list) {
            throw new UnsupportedOperationException("Only implemented by the native client");
        }

        public IPeripheralManagerClient GetPwmClient(String name, IBinder lifeline) {
            throw new UnsupportedOperationException("Only implemented by the native client");
        }

        public void OpenPwm(String name) throws RemoteException {
            try {
                ((RegisteredDeviceDriver) PioDriverManager.this.mPwmManager.getRegisteredDriverOrThrow(name)).openDriver(this.mLifeline, new OpenMethod<PwmDriver>() {
                    public void call(PwmDriver driver) throws IOException {
                        driver.open();
                    }
                });
            } catch (IOException e) {
                throw PioException.createServiceSpecificException(e);
            }
        }

        public void ReleasePwm(String name) throws RemoteException {
            try {
                ((RegisteredDeviceDriver) PioDriverManager.this.mPwmManager.getRegisteredDriverOrThrow(name)).closeDriver(this.mLifeline);
            } catch (IOException e) {
                throw PioException.createServiceSpecificException(e);
            }
        }

        public void PwmSetEnabled(String name, boolean enabled) throws RemoteException {
            Throwable th;
            Throwable th2 = null;
            DriverLock driverLock = null;
            try {
                driverLock = lockPwm(name);
                ((PwmDriver) driverLock.getDriver()).setEnabled(enabled);
                if (driverLock != null) {
                    try {
                        driverLock.close();
                    } catch (Throwable th3) {
                        th2 = th3;
                    }
                }
                if (th2 != null) {
                    try {
                        throw th2;
                    } catch (IOException e) {
                        throw PioException.createServiceSpecificException(e);
                    }
                }
                return;
            } catch (Throwable th22) {
                Throwable th4 = th22;
                th22 = th;
                th = th4;
            }
            if (driverLock != null) {
                try {
                    driverLock.close();
                } catch (Throwable th5) {
                    if (th22 == null) {
                        th22 = th5;
                    } else if (th22 != th5) {
                        th22.addSuppressed(th5);
                    }
                }
            }
            if (th22 != null) {
                throw th22;
            }
            throw th;
        }

        public void PwmSetDutyCycle(String name, double cycle) throws RemoteException {
            Throwable th;
            Throwable th2 = null;
            DriverLock driverLock = null;
            try {
                driverLock = lockPwm(name);
                ((PwmDriver) driverLock.getDriver()).setPwmDutyCycle(cycle);
                if (driverLock != null) {
                    try {
                        driverLock.close();
                    } catch (Throwable th3) {
                        th2 = th3;
                    }
                }
                if (th2 != null) {
                    try {
                        throw th2;
                    } catch (IOException e) {
                        throw PioException.createServiceSpecificException(e);
                    }
                }
                return;
            } catch (Throwable th22) {
                Throwable th4 = th22;
                th22 = th;
                th = th4;
            }
            if (driverLock != null) {
                try {
                    driverLock.close();
                } catch (Throwable th5) {
                    if (th22 == null) {
                        th22 = th5;
                    } else if (th22 != th5) {
                        th22.addSuppressed(th5);
                    }
                }
            }
            if (th22 != null) {
                throw th22;
            }
            throw th;
        }

        public void PwmSetFrequencyHz(String name, double frequency) throws RemoteException {
            Throwable th;
            Throwable th2 = null;
            DriverLock driverLock = null;
            try {
                driverLock = lockPwm(name);
                ((PwmDriver) driverLock.getDriver()).setPwmFrequencyHz(frequency);
                if (driverLock != null) {
                    try {
                        driverLock.close();
                    } catch (Throwable th3) {
                        th2 = th3;
                    }
                }
                if (th2 != null) {
                    try {
                        throw th2;
                    } catch (IOException e) {
                        throw PioException.createServiceSpecificException(e);
                    }
                }
                return;
            } catch (Throwable th22) {
                Throwable th4 = th22;
                th22 = th;
                th = th4;
            }
            if (driverLock != null) {
                try {
                    driverLock.close();
                } catch (Throwable th5) {
                    if (th22 == null) {
                        th22 = th5;
                    } else if (th22 != th5) {
                        th22.addSuppressed(th5);
                    }
                }
            }
            if (th22 != null) {
                throw th22;
            }
            throw th;
        }

        public void ListSpiBuses(List<String> list) {
            throw new UnsupportedOperationException("Only implemented by the native client");
        }

        public IPeripheralManagerClient GetSpiClient(String name, IBinder lifeline) {
            throw new UnsupportedOperationException("Only implemented by the native client");
        }

        public void OpenSpiDevice(String deviceName) throws RemoteException {
            try {
                SpiNames names = getSpiNamesOrThrow(deviceName);
                ((RegisteredBusDriver) PioDriverManager.this.mSpiManager.getRegisteredDriverOrThrow(names.getBusName())).openDevice(this.mLifeline, names.getCsName(), new OpenMethods<SpiBusDriver, String, SpiDevice>() {
                    public void openBus(SpiBusDriver busDriver) throws IOException {
                        busDriver.open();
                    }

                    public SpiDevice openDevice(SpiBusDriver busDriver, String csName) throws IOException {
                        return busDriver.createSpiDevice(csName);
                    }
                });
            } catch (IOException e) {
                throw PioException.createServiceSpecificException(e);
            }
        }

        public void ReleaseSpiDevice(String deviceName) {
            try {
                SpiNames names = getSpiNamesOrThrow(deviceName);
                ((RegisteredBusDriver) PioDriverManager.this.mSpiManager.getRegisteredDriverOrThrow(names.getBusName())).closeDevice(this.mLifeline, names.getCsName());
            } catch (IOException e) {
                throw PioException.createServiceSpecificException(e);
            }
        }

        public void SpiDeviceTransfer(String deviceName, byte[] tx_data, byte[] rx_data, int len) {
            Throwable th;
            Throwable th2 = null;
            DriverLock driverLock = null;
            try {
                driverLock = lockSpiDevice(deviceName);
                ((SpiDevice) driverLock.getDriver()).transfer(tx_data, rx_data, len);
                if (driverLock != null) {
                    try {
                        driverLock.close();
                    } catch (Throwable th3) {
                        th2 = th3;
                    }
                }
                if (th2 != null) {
                    try {
                        throw th2;
                    } catch (IOException e) {
                        throw PioException.createServiceSpecificException(e);
                    }
                }
                return;
            } catch (Throwable th22) {
                Throwable th4 = th22;
                th22 = th;
                th = th4;
            }
            if (driverLock != null) {
                try {
                    driverLock.close();
                } catch (Throwable th5) {
                    if (th22 == null) {
                        th22 = th5;
                    } else if (th22 != th5) {
                        th22.addSuppressed(th5);
                    }
                }
            }
            if (th22 != null) {
                throw th22;
            }
            throw th;
        }

        public void SpiDeviceSetFrequency(String deviceName, int frequency_hz) {
            Throwable th;
            Throwable th2 = null;
            DriverLock driverLock = null;
            try {
                driverLock = lockSpiDevice(deviceName);
                ((SpiDevice) driverLock.getDriver()).setFrequency(frequency_hz);
                if (driverLock != null) {
                    try {
                        driverLock.close();
                    } catch (Throwable th3) {
                        th2 = th3;
                    }
                }
                if (th2 != null) {
                    try {
                        throw th2;
                    } catch (IOException e) {
                        throw PioException.createServiceSpecificException(e);
                    }
                }
                return;
            } catch (Throwable th22) {
                Throwable th4 = th22;
                th22 = th;
                th = th4;
            }
            if (driverLock != null) {
                try {
                    driverLock.close();
                } catch (Throwable th5) {
                    if (th22 == null) {
                        th22 = th5;
                    } else if (th22 != th5) {
                        th22.addSuppressed(th5);
                    }
                }
            }
            if (th22 != null) {
                throw th22;
            }
            throw th;
        }

        public void SpiDeviceSetBitJustification(String deviceName, boolean lsb_first) {
            Throwable th;
            Throwable th2 = null;
            DriverLock driverLock = null;
            try {
                driverLock = lockSpiDevice(deviceName);
                ((SpiDevice) driverLock.getDriver()).setBitJustification(lsb_first);
                if (driverLock != null) {
                    try {
                        driverLock.close();
                    } catch (Throwable th3) {
                        th2 = th3;
                    }
                }
                if (th2 != null) {
                    try {
                        throw th2;
                    } catch (IOException e) {
                        throw PioException.createServiceSpecificException(e);
                    }
                }
                return;
            } catch (Throwable th22) {
                Throwable th4 = th22;
                th22 = th;
                th = th4;
            }
            if (driverLock != null) {
                try {
                    driverLock.close();
                } catch (Throwable th5) {
                    if (th22 == null) {
                        th22 = th5;
                    } else if (th22 != th5) {
                        th22.addSuppressed(th5);
                    }
                }
            }
            if (th22 != null) {
                throw th22;
            }
            throw th;
        }

        public void SpiDeviceSetMode(String deviceName, int mode) {
            Throwable th;
            Throwable th2 = null;
            DriverLock driverLock = null;
            try {
                driverLock = lockSpiDevice(deviceName);
                ((SpiDevice) driverLock.getDriver()).setMode(mode);
                if (driverLock != null) {
                    try {
                        driverLock.close();
                    } catch (Throwable th3) {
                        th2 = th3;
                    }
                }
                if (th2 != null) {
                    try {
                        throw th2;
                    } catch (IOException e) {
                        throw PioException.createServiceSpecificException(e);
                    }
                }
                return;
            } catch (Throwable th22) {
                Throwable th4 = th22;
                th22 = th;
                th = th4;
            }
            if (driverLock != null) {
                try {
                    driverLock.close();
                } catch (Throwable th5) {
                    if (th22 == null) {
                        th22 = th5;
                    } else if (th22 != th5) {
                        th22.addSuppressed(th5);
                    }
                }
            }
            if (th22 != null) {
                throw th22;
            }
            throw th;
        }

        public void SpiDeviceSetBitsPerWord(String deviceName, int nbits) {
            Throwable th;
            Throwable th2 = null;
            DriverLock driverLock = null;
            try {
                driverLock = lockSpiDevice(deviceName);
                ((SpiDevice) driverLock.getDriver()).setBitsPerWord(nbits);
                if (driverLock != null) {
                    try {
                        driverLock.close();
                    } catch (Throwable th3) {
                        th2 = th3;
                    }
                }
                if (th2 != null) {
                    try {
                        throw th2;
                    } catch (IOException e) {
                        throw PioException.createServiceSpecificException(e);
                    }
                }
                return;
            } catch (Throwable th22) {
                Throwable th4 = th22;
                th22 = th;
                th = th4;
            }
            if (driverLock != null) {
                try {
                    driverLock.close();
                } catch (Throwable th5) {
                    if (th22 == null) {
                        th22 = th5;
                    } else if (th22 != th5) {
                        th22.addSuppressed(th5);
                    }
                }
            }
            if (th22 != null) {
                throw th22;
            }
            throw th;
        }

        public void SpiDeviceSetDelay(String deviceName, int delay_usecs) {
            Throwable th;
            Throwable th2 = null;
            DriverLock driverLock = null;
            try {
                driverLock = lockSpiDevice(deviceName);
                ((SpiDevice) driverLock.getDriver()).setDelay(delay_usecs);
                if (driverLock != null) {
                    try {
                        driverLock.close();
                    } catch (Throwable th3) {
                        th2 = th3;
                    }
                }
                if (th2 != null) {
                    try {
                        throw th2;
                    } catch (IOException e) {
                        throw PioException.createServiceSpecificException(e);
                    }
                }
                return;
            } catch (Throwable th22) {
                Throwable th4 = th22;
                th22 = th;
                th = th4;
            }
            if (driverLock != null) {
                try {
                    driverLock.close();
                } catch (Throwable th5) {
                    if (th22 == null) {
                        th22 = th5;
                    } else if (th22 != th5) {
                        th22.addSuppressed(th5);
                    }
                }
            }
            if (th22 != null) {
                throw th22;
            }
            throw th;
        }

        public void SpiDeviceSetCsChange(String deviceName, boolean change) {
            Throwable th;
            Throwable th2 = null;
            DriverLock driverLock = null;
            try {
                driverLock = lockSpiDevice(deviceName);
                ((SpiDevice) driverLock.getDriver()).setCsChange(change);
                if (driverLock != null) {
                    try {
                        driverLock.close();
                    } catch (Throwable th3) {
                        th2 = th3;
                    }
                }
                if (th2 != null) {
                    try {
                        throw th2;
                    } catch (IOException e) {
                        throw PioException.createServiceSpecificException(e);
                    }
                }
                return;
            } catch (Throwable th22) {
                Throwable th4 = th22;
                th22 = th;
                th = th4;
            }
            if (driverLock != null) {
                try {
                    driverLock.close();
                } catch (Throwable th5) {
                    if (th22 == null) {
                        th22 = th5;
                    } else if (th22 != th5) {
                        th22.addSuppressed(th5);
                    }
                }
            }
            if (th22 != null) {
                throw th22;
            }
            throw th;
        }

        public void ListI2cBuses(List<String> list) {
            throw new UnsupportedOperationException("Only implemented by the native client");
        }

        public IPeripheralManagerClient GetI2cClient(String name, IBinder lifeline) {
            throw new UnsupportedOperationException("Only implemented by the native client");
        }

        public void OpenI2cDevice(String name, int address) throws RemoteException {
            try {
                ((RegisteredBusDriver) PioDriverManager.this.mI2cManager.getRegisteredDriverOrThrow(name)).openDevice(this.mLifeline, Integer.valueOf(address), new OpenMethods<I2cBusDriver, Integer, I2cDevice>() {
                    public void openBus(I2cBusDriver busDriver) throws IOException {
                        busDriver.open();
                    }

                    public I2cDevice openDevice(I2cBusDriver busDriver, Integer address) throws IOException {
                        return busDriver.createI2cDevice(address.intValue());
                    }
                });
            } catch (IOException e) {
                throw PioException.createServiceSpecificException(e);
            }
        }

        public void ReleaseI2cDevice(String name, int address) {
            try {
                ((RegisteredBusDriver) PioDriverManager.this.mI2cManager.getRegisteredDriverOrThrow(name)).closeDevice(this.mLifeline, Integer.valueOf(address));
            } catch (IOException e) {
                throw PioException.createServiceSpecificException(e);
            }
        }

        public void I2cRead(String name, int address, byte[] data, int size) {
            Throwable th;
            Throwable th2 = null;
            DriverLock driverLock = null;
            try {
                driverLock = lockI2cDevice(name, address);
                ((I2cDevice) driverLock.getDriver()).read(data, size);
                if (driverLock != null) {
                    try {
                        driverLock.close();
                    } catch (Throwable th3) {
                        th2 = th3;
                    }
                }
                if (th2 != null) {
                    try {
                        throw th2;
                    } catch (IOException e) {
                        throw PioException.createServiceSpecificException(e);
                    }
                }
                return;
            } catch (Throwable th22) {
                Throwable th4 = th22;
                th22 = th;
                th = th4;
            }
            if (driverLock != null) {
                try {
                    driverLock.close();
                } catch (Throwable th5) {
                    if (th22 == null) {
                        th22 = th5;
                    } else if (th22 != th5) {
                        th22.addSuppressed(th5);
                    }
                }
            }
            if (th22 != null) {
                throw th22;
            }
            throw th;
        }

        public byte I2cReadRegByte(String name, int address, int reg) {
            Throwable th;
            Throwable th2 = null;
            DriverLock driverLock = null;
            try {
                driverLock = lockI2cDevice(name, address);
                byte readRegByte = ((I2cDevice) driverLock.getDriver()).readRegByte(reg);
                if (driverLock != null) {
                    try {
                        driverLock.close();
                    } catch (Throwable th3) {
                        th2 = th3;
                    }
                }
                if (th2 == null) {
                    return readRegByte;
                }
                try {
                    throw th2;
                } catch (IOException e) {
                    throw PioException.createServiceSpecificException(e);
                }
            } catch (Throwable th22) {
                Throwable th4 = th22;
                th22 = th;
                th = th4;
            }
            if (driverLock != null) {
                try {
                    driverLock.close();
                } catch (Throwable th5) {
                    if (th22 == null) {
                        th22 = th5;
                    } else if (th22 != th5) {
                        th22.addSuppressed(th5);
                    }
                }
            }
            if (th22 != null) {
                throw th22;
            }
            throw th;
        }

        public int I2cReadRegWord(String name, int address, int reg) {
            Throwable th;
            Throwable th2 = null;
            DriverLock driverLock = null;
            try {
                driverLock = lockI2cDevice(name, address);
                short readRegWord = ((I2cDevice) driverLock.getDriver()).readRegWord(reg);
                if (driverLock != null) {
                    try {
                        driverLock.close();
                    } catch (Throwable th3) {
                        th2 = th3;
                    }
                }
                if (th2 == null) {
                    return readRegWord;
                }
                try {
                    throw th2;
                } catch (IOException e) {
                    throw PioException.createServiceSpecificException(e);
                }
            } catch (Throwable th22) {
                Throwable th4 = th22;
                th22 = th;
                th = th4;
            }
            if (driverLock != null) {
                try {
                    driverLock.close();
                } catch (Throwable th5) {
                    if (th22 == null) {
                        th22 = th5;
                    } else if (th22 != th5) {
                        th22.addSuppressed(th5);
                    }
                }
            }
            if (th22 != null) {
                throw th22;
            }
            throw th;
        }

        public void I2cReadRegBuffer(String name, int address, int reg, byte[] data, int size) {
            Throwable th;
            Throwable th2 = null;
            DriverLock driverLock = null;
            try {
                driverLock = lockI2cDevice(name, address);
                ((I2cDevice) driverLock.getDriver()).readRegBuffer(reg, data, size);
                if (driverLock != null) {
                    try {
                        driverLock.close();
                    } catch (Throwable th3) {
                        th2 = th3;
                    }
                }
                if (th2 != null) {
                    try {
                        throw th2;
                    } catch (IOException e) {
                        throw PioException.createServiceSpecificException(e);
                    }
                }
                return;
            } catch (Throwable th22) {
                Throwable th4 = th22;
                th22 = th;
                th = th4;
            }
            if (driverLock != null) {
                try {
                    driverLock.close();
                } catch (Throwable th5) {
                    if (th22 == null) {
                        th22 = th5;
                    } else if (th22 != th5) {
                        th22.addSuppressed(th5);
                    }
                }
            }
            if (th22 != null) {
                throw th22;
            }
            throw th;
        }

        public void I2cWrite(String name, int address, byte[] data, int size) {
            Throwable th;
            Throwable th2 = null;
            DriverLock driverLock = null;
            try {
                driverLock = lockI2cDevice(name, address);
                ((I2cDevice) driverLock.getDriver()).write(data, size);
                if (driverLock != null) {
                    try {
                        driverLock.close();
                    } catch (Throwable th3) {
                        th2 = th3;
                    }
                }
                if (th2 != null) {
                    try {
                        throw th2;
                    } catch (IOException e) {
                        throw PioException.createServiceSpecificException(e);
                    }
                }
                return;
            } catch (Throwable th22) {
                Throwable th4 = th22;
                th22 = th;
                th = th4;
            }
            if (driverLock != null) {
                try {
                    driverLock.close();
                } catch (Throwable th5) {
                    if (th22 == null) {
                        th22 = th5;
                    } else if (th22 != th5) {
                        th22.addSuppressed(th5);
                    }
                }
            }
            if (th22 != null) {
                throw th22;
            }
            throw th;
        }

        public void I2cWriteRegByte(String name, int address, int reg, byte val) {
            Throwable th;
            Throwable th2 = null;
            DriverLock driverLock = null;
            try {
                driverLock = lockI2cDevice(name, address);
                ((I2cDevice) driverLock.getDriver()).writeRegByte(reg, val);
                if (driverLock != null) {
                    try {
                        driverLock.close();
                    } catch (Throwable th3) {
                        th2 = th3;
                    }
                }
                if (th2 != null) {
                    try {
                        throw th2;
                    } catch (IOException e) {
                        throw PioException.createServiceSpecificException(e);
                    }
                }
                return;
            } catch (Throwable th22) {
                Throwable th4 = th22;
                th22 = th;
                th = th4;
            }
            if (driverLock != null) {
                try {
                    driverLock.close();
                } catch (Throwable th5) {
                    if (th22 == null) {
                        th22 = th5;
                    } else if (th22 != th5) {
                        th22.addSuppressed(th5);
                    }
                }
            }
            if (th22 != null) {
                throw th22;
            }
            throw th;
        }

        public void I2cWriteRegWord(String name, int address, int reg, int val) {
            Throwable th;
            Throwable th2 = null;
            DriverLock driverLock = null;
            try {
                driverLock = lockI2cDevice(name, address);
                ((I2cDevice) driverLock.getDriver()).writeRegWord(reg, (short) val);
                if (driverLock != null) {
                    try {
                        driverLock.close();
                    } catch (Throwable th3) {
                        th2 = th3;
                    }
                }
                if (th2 != null) {
                    try {
                        throw th2;
                    } catch (IOException e) {
                        throw PioException.createServiceSpecificException(e);
                    }
                }
                return;
            } catch (Throwable th22) {
                Throwable th4 = th22;
                th22 = th;
                th = th4;
            }
            if (driverLock != null) {
                try {
                    driverLock.close();
                } catch (Throwable th5) {
                    if (th22 == null) {
                        th22 = th5;
                    } else if (th22 != th5) {
                        th22.addSuppressed(th5);
                    }
                }
            }
            if (th22 != null) {
                throw th22;
            }
            throw th;
        }

        public void I2cWriteRegBuffer(String name, int address, int reg, byte[] data, int size) {
            Throwable th;
            Throwable th2 = null;
            DriverLock driverLock = null;
            try {
                driverLock = lockI2cDevice(name, address);
                ((I2cDevice) driverLock.getDriver()).writeRegBuffer(reg, data, size);
                if (driverLock != null) {
                    try {
                        driverLock.close();
                    } catch (Throwable th3) {
                        th2 = th3;
                    }
                }
                if (th2 != null) {
                    try {
                        throw th2;
                    } catch (IOException e) {
                        throw PioException.createServiceSpecificException(e);
                    }
                }
                return;
            } catch (Throwable th22) {
                Throwable th4 = th22;
                th22 = th;
                th = th4;
            }
            if (driverLock != null) {
                try {
                    driverLock.close();
                } catch (Throwable th5) {
                    if (th22 == null) {
                        th22 = th5;
                    } else if (th22 != th5) {
                        th22.addSuppressed(th5);
                    }
                }
            }
            if (th22 != null) {
                throw th22;
            }
            throw th;
        }

        public void ListUartDevices(List<String> list) {
            throw new UnsupportedOperationException("Only implemented by the native client");
        }

        public IPeripheralManagerClient GetUartClient(String name, IBinder lifeline) {
            throw new UnsupportedOperationException("Only implemented by the native client");
        }

        public void OpenUartDevice(String name) throws RemoteException {
            try {
                ((RegisteredDeviceDriver) PioDriverManager.this.mUartManager.getRegisteredDriverOrThrow(name)).openDriver(this.mLifeline, new OpenMethod<UartDeviceDriver>() {
                    public void call(UartDeviceDriver driver) throws IOException {
                        driver.open();
                    }
                });
            } catch (IOException e) {
                throw PioException.createServiceSpecificException(e);
            }
        }

        public void ReleaseUartDevice(String name) {
            try {
                ((RegisteredDeviceDriver) PioDriverManager.this.mUartManager.getRegisteredDriverOrThrow(name)).closeDriver(this.mLifeline);
            } catch (IOException e) {
                throw PioException.createServiceSpecificException(e);
            }
        }

        public void UartDeviceSetBaudrate(String name, int baudrate) {
            Throwable th;
            Throwable th2 = null;
            DriverLock driverLock = null;
            try {
                driverLock = lockUartDevice(name);
                ((UartDeviceDriver) driverLock.getDriver()).setBaudrate(baudrate);
                if (driverLock != null) {
                    try {
                        driverLock.close();
                    } catch (Throwable th3) {
                        th2 = th3;
                    }
                }
                if (th2 != null) {
                    try {
                        throw th2;
                    } catch (IOException e) {
                        throw PioException.createServiceSpecificException(e);
                    }
                }
                return;
            } catch (Throwable th22) {
                Throwable th4 = th22;
                th22 = th;
                th = th4;
            }
            if (driverLock != null) {
                try {
                    driverLock.close();
                } catch (Throwable th5) {
                    if (th22 == null) {
                        th22 = th5;
                    } else if (th22 != th5) {
                        th22.addSuppressed(th5);
                    }
                }
            }
            if (th22 != null) {
                throw th22;
            }
            throw th;
        }

        public void UartDeviceSetStopBits(String name, int stop_bits) {
            Throwable th;
            Throwable th2 = null;
            DriverLock driverLock = null;
            try {
                driverLock = lockUartDevice(name);
                ((UartDeviceDriver) driverLock.getDriver()).setStopBits(stop_bits);
                if (driverLock != null) {
                    try {
                        driverLock.close();
                    } catch (Throwable th3) {
                        th2 = th3;
                    }
                }
                if (th2 != null) {
                    try {
                        throw th2;
                    } catch (IOException e) {
                        throw PioException.createServiceSpecificException(e);
                    }
                }
                return;
            } catch (Throwable th22) {
                Throwable th4 = th22;
                th22 = th;
                th = th4;
            }
            if (driverLock != null) {
                try {
                    driverLock.close();
                } catch (Throwable th5) {
                    if (th22 == null) {
                        th22 = th5;
                    } else if (th22 != th5) {
                        th22.addSuppressed(th5);
                    }
                }
            }
            if (th22 != null) {
                throw th22;
            }
            throw th;
        }

        public void UartDeviceSetDataSize(String name, int data_size) {
            Throwable th;
            Throwable th2 = null;
            DriverLock driverLock = null;
            try {
                driverLock = lockUartDevice(name);
                ((UartDeviceDriver) driverLock.getDriver()).setDataSize(data_size);
                if (driverLock != null) {
                    try {
                        driverLock.close();
                    } catch (Throwable th3) {
                        th2 = th3;
                    }
                }
                if (th2 != null) {
                    try {
                        throw th2;
                    } catch (IOException e) {
                        throw PioException.createServiceSpecificException(e);
                    }
                }
                return;
            } catch (Throwable th22) {
                Throwable th4 = th22;
                th22 = th;
                th = th4;
            }
            if (driverLock != null) {
                try {
                    driverLock.close();
                } catch (Throwable th5) {
                    if (th22 == null) {
                        th22 = th5;
                    } else if (th22 != th5) {
                        th22.addSuppressed(th5);
                    }
                }
            }
            if (th22 != null) {
                throw th22;
            }
            throw th;
        }

        public void UartDeviceSetParity(String name, int mode) {
            Throwable th;
            Throwable th2 = null;
            DriverLock driverLock = null;
            try {
                driverLock = lockUartDevice(name);
                ((UartDeviceDriver) driverLock.getDriver()).setParity(mode);
                if (driverLock != null) {
                    try {
                        driverLock.close();
                    } catch (Throwable th3) {
                        th2 = th3;
                    }
                }
                if (th2 != null) {
                    try {
                        throw th2;
                    } catch (IOException e) {
                        throw PioException.createServiceSpecificException(e);
                    }
                }
                return;
            } catch (Throwable th22) {
                Throwable th4 = th22;
                th22 = th;
                th = th4;
            }
            if (driverLock != null) {
                try {
                    driverLock.close();
                } catch (Throwable th5) {
                    if (th22 == null) {
                        th22 = th5;
                    } else if (th22 != th5) {
                        th22.addSuppressed(th5);
                    }
                }
            }
            if (th22 != null) {
                throw th22;
            }
            throw th;
        }

        public void UartDeviceSetHardwareFlowControl(String name, int mode) {
            Throwable th;
            Throwable th2 = null;
            DriverLock driverLock = null;
            try {
                driverLock = lockUartDevice(name);
                ((UartDeviceDriver) driverLock.getDriver()).setHardwareFlowControl(mode);
                if (driverLock != null) {
                    try {
                        driverLock.close();
                    } catch (Throwable th3) {
                        th2 = th3;
                    }
                }
                if (th2 != null) {
                    try {
                        throw th2;
                    } catch (IOException e) {
                        throw PioException.createServiceSpecificException(e);
                    }
                }
                return;
            } catch (Throwable th22) {
                Throwable th4 = th22;
                th22 = th;
                th = th4;
            }
            if (driverLock != null) {
                try {
                    driverLock.close();
                } catch (Throwable th5) {
                    if (th22 == null) {
                        th22 = th5;
                    } else if (th22 != th5) {
                        th22.addSuppressed(th5);
                    }
                }
            }
            if (th22 != null) {
                throw th22;
            }
            throw th;
        }

        public void UartDeviceSetModemControl(String name, int bits) {
            Throwable th;
            Throwable th2 = null;
            DriverLock driverLock = null;
            try {
                driverLock = lockUartDevice(name);
                ((UartDeviceDriver) driverLock.getDriver()).setModemControl(bits);
                if (driverLock != null) {
                    try {
                        driverLock.close();
                    } catch (Throwable th3) {
                        th2 = th3;
                    }
                }
                if (th2 != null) {
                    try {
                        throw th2;
                    } catch (IOException e) {
                        throw PioException.createServiceSpecificException(e);
                    }
                }
                return;
            } catch (Throwable th22) {
                Throwable th4 = th22;
                th22 = th;
                th = th4;
            }
            if (driverLock != null) {
                try {
                    driverLock.close();
                } catch (Throwable th5) {
                    if (th22 == null) {
                        th22 = th5;
                    } else if (th22 != th5) {
                        th22.addSuppressed(th5);
                    }
                }
            }
            if (th22 != null) {
                throw th22;
            }
            throw th;
        }

        public void UartDeviceClearModemControl(String name, int bits) {
            Throwable th;
            Throwable th2 = null;
            DriverLock driverLock = null;
            try {
                driverLock = lockUartDevice(name);
                ((UartDeviceDriver) driverLock.getDriver()).clearModemControl(bits);
                if (driverLock != null) {
                    try {
                        driverLock.close();
                    } catch (Throwable th3) {
                        th2 = th3;
                    }
                }
                if (th2 != null) {
                    try {
                        throw th2;
                    } catch (IOException e) {
                        throw PioException.createServiceSpecificException(e);
                    }
                }
                return;
            } catch (Throwable th22) {
                Throwable th4 = th22;
                th22 = th;
                th = th4;
            }
            if (driverLock != null) {
                try {
                    driverLock.close();
                } catch (Throwable th5) {
                    if (th22 == null) {
                        th22 = th5;
                    } else if (th22 != th5) {
                        th22.addSuppressed(th5);
                    }
                }
            }
            if (th22 != null) {
                throw th22;
            }
            throw th;
        }

        public void UartDeviceSendBreak(String name, int duration_msecs) {
            Throwable th;
            Throwable th2 = null;
            DriverLock driverLock = null;
            try {
                driverLock = lockUartDevice(name);
                ((UartDeviceDriver) driverLock.getDriver()).sendBreak(duration_msecs);
                if (driverLock != null) {
                    try {
                        driverLock.close();
                    } catch (Throwable th3) {
                        th2 = th3;
                    }
                }
                if (th2 != null) {
                    try {
                        throw th2;
                    } catch (IOException e) {
                        throw PioException.createServiceSpecificException(e);
                    }
                }
                return;
            } catch (Throwable th22) {
                Throwable th4 = th22;
                th22 = th;
                th = th4;
            }
            if (driverLock != null) {
                try {
                    driverLock.close();
                } catch (Throwable th5) {
                    if (th22 == null) {
                        th22 = th5;
                    } else if (th22 != th5) {
                        th22.addSuppressed(th5);
                    }
                }
            }
            if (th22 != null) {
                throw th22;
            }
            throw th;
        }

        public void UartDeviceFlush(String name, int direction) {
            Throwable th;
            Throwable th2 = null;
            DriverLock driverLock = null;
            try {
                driverLock = lockUartDevice(name);
                ((UartDeviceDriver) driverLock.getDriver()).flush(direction);
                if (driverLock != null) {
                    try {
                        driverLock.close();
                    } catch (Throwable th3) {
                        th2 = th3;
                    }
                }
                if (th2 != null) {
                    try {
                        throw th2;
                    } catch (IOException e) {
                        throw PioException.createServiceSpecificException(e);
                    }
                }
                return;
            } catch (Throwable th22) {
                Throwable th4 = th22;
                th22 = th;
                th = th4;
            }
            if (driverLock != null) {
                try {
                    driverLock.close();
                } catch (Throwable th5) {
                    if (th22 == null) {
                        th22 = th5;
                    } else if (th22 != th5) {
                        th22.addSuppressed(th5);
                    }
                }
            }
            if (th22 != null) {
                throw th22;
            }
            throw th;
        }

        public int UartDeviceWrite(String name, byte[] data, int size) {
            Throwable th;
            Throwable th2 = null;
            DriverLock driverLock = null;
            try {
                driverLock = lockUartDevice(name);
                int write = ((UartDeviceDriver) driverLock.getDriver()).write(data, size);
                if (driverLock != null) {
                    try {
                        driverLock.close();
                    } catch (Throwable th3) {
                        th2 = th3;
                    }
                }
                if (th2 == null) {
                    return write;
                }
                try {
                    throw th2;
                } catch (IOException e) {
                    throw PioException.createServiceSpecificException(e);
                }
            } catch (Throwable th22) {
                Throwable th4 = th22;
                th22 = th;
                th = th4;
            }
            if (driverLock != null) {
                try {
                    driverLock.close();
                } catch (Throwable th5) {
                    if (th22 == null) {
                        th22 = th5;
                    } else if (th22 != th5) {
                        th22.addSuppressed(th5);
                    }
                }
            }
            if (th22 != null) {
                throw th22;
            }
            throw th;
        }

        public int UartDeviceRead(String name, byte[] data, int size) {
            Throwable th;
            Throwable th2 = null;
            DriverLock driverLock = null;
            try {
                driverLock = lockUartDevice(name);
                int read = ((UartDeviceDriver) driverLock.getDriver()).read(data, size);
                if (driverLock != null) {
                    try {
                        driverLock.close();
                    } catch (Throwable th3) {
                        th2 = th3;
                    }
                }
                if (th2 == null) {
                    return read;
                }
                try {
                    throw th2;
                } catch (IOException e) {
                    throw PioException.createServiceSpecificException(e);
                }
            } catch (Throwable th22) {
                Throwable th4 = th22;
                th22 = th;
                th = th4;
            }
            if (driverLock != null) {
                try {
                    driverLock.close();
                } catch (Throwable th5) {
                    if (th22 == null) {
                        th22 = th5;
                    } else if (th22 != th5) {
                        th22.addSuppressed(th5);
                    }
                }
            }
            if (th22 != null) {
                throw th22;
            }
            throw th;
        }

        public FileDescriptor UartDeviceGetPollingFd(String name) {
            Throwable th;
            Throwable th2 = null;
            DriverLock driverLock = null;
            try {
                DriverLock<UartDeviceDriver> lock = lockUartDevice(name);
                if (lock.getCallback() == null) {
                    lock.setCallback(new UserDriverUartDeviceCallback(name));
                }
                FileDescriptor registerNewPipeLocked = lock.getCallback().registerNewPipeLocked();
                if (lock != null) {
                    try {
                        lock.close();
                    } catch (Throwable th3) {
                        th2 = th3;
                    }
                }
                if (th2 == null) {
                    return registerNewPipeLocked;
                }
                try {
                    throw th2;
                } catch (IOException e) {
                    throw PioException.createServiceSpecificException(e);
                }
            } catch (Throwable th22) {
                Throwable th4 = th22;
                th22 = th;
                th = th4;
            }
            if (driverLock != null) {
                try {
                    driverLock.close();
                } catch (Throwable th5) {
                    if (th22 == null) {
                        th22 = th5;
                    } else if (th22 != th5) {
                        th22.addSuppressed(th5);
                    }
                }
            }
            if (th22 != null) {
                throw th22;
            }
            throw th;
        }
    }

    private static class SpiNames {
        private final String mBusName;
        private final String mCsName;

        public SpiNames(String busName, String csName) {
            this.mBusName = busName;
            this.mCsName = csName;
        }

        public String getBusName() {
            return this.mBusName;
        }

        public String getCsName() {
            return this.mCsName;
        }
    }

    public PioDriverManager() {
        IBinder pioManagerBinder = ServiceManager.getService("com.google.android.things.pio.IPeripheralManager");
        if (pioManagerBinder == null) {
            throw new IllegalStateException("Failed to connect to the PIO service");
        }
        this.mPioManager = IPeripheralManager.Stub.asInterface(pioManagerBinder);
    }

    PioDriverManager(IPeripheralManager testPioBinder) {
        this.mPioManager = testPioBinder;
    }

    public void registerGpioDriver(final String name, GpioDriver driver) throws IOException {
        this.mGpioManager.registerDriver(name, new RegisteredDeviceDriver(driver, name), new PioMethod() {
            public void call() throws ServiceSpecificException, RemoteException {
                PioDriverManager.this.mPioManager.RegisterGpioUserDriver(name, PioDriverManager.this.mUserDriverManager);
            }
        });
    }

    public void unregisterGpioDriver(final String name) throws IOException {
        this.mGpioManager.unregisterDriver(name, new PioMethod() {
            public void call() throws ServiceSpecificException, RemoteException {
                PioDriverManager.this.mPioManager.UnregisterGpio(name);
            }
        });
    }

    public void registerPwmDriver(final String name, PwmDriver driver) throws IOException {
        this.mPwmManager.registerDriver(name, new RegisteredDeviceDriver(driver, name), new PioMethod() {
            public void call() throws ServiceSpecificException, RemoteException {
                PioDriverManager.this.mPioManager.RegisterPwmUserDriver(name, PioDriverManager.this.mUserDriverManager);
            }
        });
    }

    public void unregisterPwmDriver(final String name) throws IOException {
        this.mPwmManager.unregisterDriver(name, new PioMethod() {
            public void call() throws ServiceSpecificException, RemoteException {
                PioDriverManager.this.mPioManager.UnregisterPwm(name);
            }
        });
    }

    public void registerUartDeviceDriver(final String name, UartDeviceDriver driver) throws IOException {
        this.mUartManager.registerDriver(name, new RegisteredDeviceDriver(driver, name), new PioMethod() {
            public void call() throws ServiceSpecificException, RemoteException {
                PioDriverManager.this.mPioManager.RegisterUartUserDriver(name, PioDriverManager.this.mUserDriverManager);
            }
        });
    }

    public void unregisterUartDeviceDriver(final String name) throws IOException {
        this.mUartManager.unregisterDriver(name, new PioMethod() {
            public void call() throws ServiceSpecificException, RemoteException {
                PioDriverManager.this.mPioManager.UnregisterUart(name);
            }
        });
    }

    public void registerI2cBusDriver(final String name, I2cBusDriver driver) throws IOException {
        this.mI2cManager.registerDriver(name, new RegisteredBusDriver(driver, name), new PioMethod() {
            public void call() throws ServiceSpecificException, RemoteException {
                PioDriverManager.this.mPioManager.RegisterI2cUserDriver(name, PioDriverManager.this.mUserDriverManager);
            }
        });
    }

    public void unregisterI2cBusDriver(final String name) throws IOException {
        this.mI2cManager.unregisterDriver(name, new PioMethod() {
            public void call() throws ServiceSpecificException, RemoteException {
                PioDriverManager.this.mPioManager.UnregisterI2c(name);
            }
        });
    }

    public void registerSpiBusDriver(final String busName, final SpiBusDriver driver) throws IOException {
        final List<String> addedDevices = new LinkedList();
        try {
            this.mSpiManager.registerDriver(busName, new RegisteredBusDriver(driver, busName), new PioMethod() {
                public void call() throws ServiceSpecificException, RemoteException {
                    for (String csName : driver.getChipSelectNames()) {
                        String deviceName = busName + "." + csName;
                        PioDriverManager.this.mPioManager.RegisterSpiUserDriver(deviceName, PioDriverManager.this.mUserDriverManager);
                        PioDriverManager.this.mSpiDeviceNameMap.put(deviceName, new SpiNames(busName, csName));
                        addedDevices.add(deviceName);
                    }
                }
            });
        } catch (IOException e) {
            try {
                for (String deviceName : addedDevices) {
                    this.mSpiDeviceNameMap.remove(deviceName);
                    this.mPioManager.UnregisterSpi(deviceName);
                }
            } catch (Exception e2) {
            }
            throw e;
        }
    }

    public void unregisterSpiBusDriver(final String busName) throws IOException {
        this.mSpiManager.unregisterDriver(busName, new PioMethod() {
            public void call() throws ServiceSpecificException, RemoteException {
                Iterator<Entry<String, SpiNames>> iter = PioDriverManager.this.mSpiDeviceNameMap.entrySet().iterator();
                RemoteException remoteException = null;
                while (iter.hasNext()) {
                    Entry<String, SpiNames> entry = (Entry) iter.next();
                    if (((SpiNames) entry.getValue()).getBusName().equals(busName)) {
                        iter.remove();
                        try {
                            PioDriverManager.this.mPioManager.UnregisterSpi((String) entry.getKey());
                        } catch (RemoteException e) {
                            if (remoteException == null) {
                                remoteException = e;
                            }
                        }
                    }
                }
                if (remoteException != null) {
                    throw remoteException;
                }
            }
        });
    }
}
