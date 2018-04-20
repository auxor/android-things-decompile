package com.google.android.things.pio;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import java.io.FileDescriptor;
import java.util.ArrayList;
import java.util.List;

public interface IPeripheralManagerClient extends IInterface {

    public static abstract class Stub extends Binder implements IPeripheralManagerClient {
        private static final String DESCRIPTOR = "com.google.android.things.pio.IPeripheralManagerClient";
        static final int TRANSACTION_GetGpioClient = 2;
        static final int TRANSACTION_GetGpioPollingFd = 10;
        static final int TRANSACTION_GetGpioValue = 9;
        static final int TRANSACTION_GetI2cClient = 30;
        static final int TRANSACTION_GetPwmClient = 12;
        static final int TRANSACTION_GetSpiClient = 19;
        static final int TRANSACTION_GetUartClient = 42;
        static final int TRANSACTION_I2cRead = 33;
        static final int TRANSACTION_I2cReadRegBuffer = 36;
        static final int TRANSACTION_I2cReadRegByte = 34;
        static final int TRANSACTION_I2cReadRegWord = 35;
        static final int TRANSACTION_I2cWrite = 37;
        static final int TRANSACTION_I2cWriteRegBuffer = 40;
        static final int TRANSACTION_I2cWriteRegByte = 38;
        static final int TRANSACTION_I2cWriteRegWord = 39;
        static final int TRANSACTION_ListGpio = 1;
        static final int TRANSACTION_ListI2cBuses = 29;
        static final int TRANSACTION_ListPwm = 11;
        static final int TRANSACTION_ListSpiBuses = 18;
        static final int TRANSACTION_ListUartDevices = 41;
        static final int TRANSACTION_OpenGpio = 3;
        static final int TRANSACTION_OpenI2cDevice = 31;
        static final int TRANSACTION_OpenPwm = 13;
        static final int TRANSACTION_OpenSpiDevice = 20;
        static final int TRANSACTION_OpenUartDevice = 43;
        static final int TRANSACTION_PwmSetDutyCycle = 16;
        static final int TRANSACTION_PwmSetEnabled = 15;
        static final int TRANSACTION_PwmSetFrequencyHz = 17;
        static final int TRANSACTION_ReleaseGpio = 4;
        static final int TRANSACTION_ReleaseI2cDevice = 32;
        static final int TRANSACTION_ReleasePwm = 14;
        static final int TRANSACTION_ReleaseSpiDevice = 21;
        static final int TRANSACTION_ReleaseUartDevice = 44;
        static final int TRANSACTION_SetGpioActiveType = 6;
        static final int TRANSACTION_SetGpioDirection = 7;
        static final int TRANSACTION_SetGpioEdge = 5;
        static final int TRANSACTION_SetGpioValue = 8;
        static final int TRANSACTION_SpiDeviceSetBitJustification = 24;
        static final int TRANSACTION_SpiDeviceSetBitsPerWord = 26;
        static final int TRANSACTION_SpiDeviceSetCsChange = 28;
        static final int TRANSACTION_SpiDeviceSetDelay = 27;
        static final int TRANSACTION_SpiDeviceSetFrequency = 23;
        static final int TRANSACTION_SpiDeviceSetMode = 25;
        static final int TRANSACTION_SpiDeviceTransfer = 22;
        static final int TRANSACTION_UartDeviceClearModemControl = 51;
        static final int TRANSACTION_UartDeviceFlush = 53;
        static final int TRANSACTION_UartDeviceGetPollingFd = 56;
        static final int TRANSACTION_UartDeviceRead = 55;
        static final int TRANSACTION_UartDeviceSendBreak = 52;
        static final int TRANSACTION_UartDeviceSetBaudrate = 45;
        static final int TRANSACTION_UartDeviceSetDataSize = 47;
        static final int TRANSACTION_UartDeviceSetHardwareFlowControl = 49;
        static final int TRANSACTION_UartDeviceSetModemControl = 50;
        static final int TRANSACTION_UartDeviceSetParity = 48;
        static final int TRANSACTION_UartDeviceSetStopBits = 46;
        static final int TRANSACTION_UartDeviceWrite = 54;

        private static class Proxy implements IPeripheralManagerClient {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            public void ListGpio(List<String> gpios) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(1, _data, _reply, 0);
                    _reply.readException();
                    _reply.readStringList(gpios);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public IPeripheralManagerClient GetGpioClient(String name, IBinder lifeline) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(name);
                    _data.writeStrongBinder(lifeline);
                    this.mRemote.transact(2, _data, _reply, 0);
                    _reply.readException();
                    IPeripheralManagerClient _result = Stub.asInterface(_reply.readStrongBinder());
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void OpenGpio(String name) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(name);
                    this.mRemote.transact(3, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void ReleaseGpio(String name) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(name);
                    this.mRemote.transact(4, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void SetGpioEdge(String name, int type) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(name);
                    _data.writeInt(type);
                    this.mRemote.transact(5, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void SetGpioActiveType(String name, int type) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(name);
                    _data.writeInt(type);
                    this.mRemote.transact(6, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void SetGpioDirection(String name, int direction) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(name);
                    _data.writeInt(direction);
                    this.mRemote.transact(7, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void SetGpioValue(String name, boolean value) throws RemoteException {
                int i = 0;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(name);
                    if (value) {
                        i = 1;
                    }
                    _data.writeInt(i);
                    this.mRemote.transact(8, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean GetGpioValue(String name) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(name);
                    this.mRemote.transact(9, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readInt() != 0;
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } catch (Throwable th) {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public FileDescriptor GetGpioPollingFd(String name) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(name);
                    this.mRemote.transact(10, _data, _reply, 0);
                    _reply.readException();
                    FileDescriptor _result = _reply.readRawFileDescriptor();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void ListPwm(List<String> pwms) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(11, _data, _reply, 0);
                    _reply.readException();
                    _reply.readStringList(pwms);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public IPeripheralManagerClient GetPwmClient(String name, IBinder lifeline) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(name);
                    _data.writeStrongBinder(lifeline);
                    this.mRemote.transact(12, _data, _reply, 0);
                    _reply.readException();
                    IPeripheralManagerClient _result = Stub.asInterface(_reply.readStrongBinder());
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void OpenPwm(String name) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(name);
                    this.mRemote.transact(Stub.TRANSACTION_OpenPwm, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void ReleasePwm(String name) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(name);
                    this.mRemote.transact(Stub.TRANSACTION_ReleasePwm, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void PwmSetEnabled(String name, boolean enabled) throws RemoteException {
                int i = 0;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(name);
                    if (enabled) {
                        i = 1;
                    }
                    _data.writeInt(i);
                    this.mRemote.transact(Stub.TRANSACTION_PwmSetEnabled, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void PwmSetDutyCycle(String name, double cycle) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(name);
                    _data.writeDouble(cycle);
                    this.mRemote.transact(16, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void PwmSetFrequencyHz(String name, double frequency) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(name);
                    _data.writeDouble(frequency);
                    this.mRemote.transact(17, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void ListSpiBuses(List<String> buses) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(Stub.TRANSACTION_ListSpiBuses, _data, _reply, 0);
                    _reply.readException();
                    _reply.readStringList(buses);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public IPeripheralManagerClient GetSpiClient(String name, IBinder lifeline) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(name);
                    _data.writeStrongBinder(lifeline);
                    this.mRemote.transact(Stub.TRANSACTION_GetSpiClient, _data, _reply, 0);
                    _reply.readException();
                    IPeripheralManagerClient _result = Stub.asInterface(_reply.readStrongBinder());
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void OpenSpiDevice(String name) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(name);
                    this.mRemote.transact(20, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void ReleaseSpiDevice(String name) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(name);
                    this.mRemote.transact(21, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void SpiDeviceTransfer(String name, byte[] tx_data, byte[] rx_data, int len) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(name);
                    _data.writeByteArray(tx_data);
                    if (rx_data == null) {
                        _data.writeInt(-1);
                    } else {
                        _data.writeInt(rx_data.length);
                    }
                    _data.writeInt(len);
                    this.mRemote.transact(22, _data, _reply, 0);
                    _reply.readException();
                    _reply.readByteArray(rx_data);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void SpiDeviceSetFrequency(String name, int frequency_hz) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(name);
                    _data.writeInt(frequency_hz);
                    this.mRemote.transact(Stub.TRANSACTION_SpiDeviceSetFrequency, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void SpiDeviceSetBitJustification(String name, boolean lsb_first) throws RemoteException {
                int i = 0;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(name);
                    if (lsb_first) {
                        i = 1;
                    }
                    _data.writeInt(i);
                    this.mRemote.transact(24, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void SpiDeviceSetMode(String name, int mode) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(name);
                    _data.writeInt(mode);
                    this.mRemote.transact(Stub.TRANSACTION_SpiDeviceSetMode, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void SpiDeviceSetBitsPerWord(String name, int nbits) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(name);
                    _data.writeInt(nbits);
                    this.mRemote.transact(Stub.TRANSACTION_SpiDeviceSetBitsPerWord, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void SpiDeviceSetDelay(String name, int delay_usecs) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(name);
                    _data.writeInt(delay_usecs);
                    this.mRemote.transact(Stub.TRANSACTION_SpiDeviceSetDelay, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void SpiDeviceSetCsChange(String name, boolean change) throws RemoteException {
                int i = 0;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(name);
                    if (change) {
                        i = 1;
                    }
                    _data.writeInt(i);
                    this.mRemote.transact(Stub.TRANSACTION_SpiDeviceSetCsChange, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void ListI2cBuses(List<String> buses) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(Stub.TRANSACTION_ListI2cBuses, _data, _reply, 0);
                    _reply.readException();
                    _reply.readStringList(buses);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public IPeripheralManagerClient GetI2cClient(String name, IBinder lifeline) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(name);
                    _data.writeStrongBinder(lifeline);
                    this.mRemote.transact(Stub.TRANSACTION_GetI2cClient, _data, _reply, 0);
                    _reply.readException();
                    IPeripheralManagerClient _result = Stub.asInterface(_reply.readStrongBinder());
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void OpenI2cDevice(String name, int address) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(name);
                    _data.writeInt(address);
                    this.mRemote.transact(Stub.TRANSACTION_OpenI2cDevice, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void ReleaseI2cDevice(String name, int address) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(name);
                    _data.writeInt(address);
                    this.mRemote.transact(32, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void I2cRead(String name, int address, byte[] data, int size) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(name);
                    _data.writeInt(address);
                    if (data == null) {
                        _data.writeInt(-1);
                    } else {
                        _data.writeInt(data.length);
                    }
                    _data.writeInt(size);
                    this.mRemote.transact(Stub.TRANSACTION_I2cRead, _data, _reply, 0);
                    _reply.readException();
                    _reply.readByteArray(data);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public byte I2cReadRegByte(String name, int address, int reg) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(name);
                    _data.writeInt(address);
                    _data.writeInt(reg);
                    this.mRemote.transact(Stub.TRANSACTION_I2cReadRegByte, _data, _reply, 0);
                    _reply.readException();
                    byte _result = _reply.readByte();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int I2cReadRegWord(String name, int address, int reg) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(name);
                    _data.writeInt(address);
                    _data.writeInt(reg);
                    this.mRemote.transact(Stub.TRANSACTION_I2cReadRegWord, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void I2cReadRegBuffer(String name, int address, int reg, byte[] data, int size) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(name);
                    _data.writeInt(address);
                    _data.writeInt(reg);
                    if (data == null) {
                        _data.writeInt(-1);
                    } else {
                        _data.writeInt(data.length);
                    }
                    _data.writeInt(size);
                    this.mRemote.transact(Stub.TRANSACTION_I2cReadRegBuffer, _data, _reply, 0);
                    _reply.readException();
                    _reply.readByteArray(data);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void I2cWrite(String name, int address, byte[] data, int size) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(name);
                    _data.writeInt(address);
                    _data.writeByteArray(data);
                    _data.writeInt(size);
                    this.mRemote.transact(Stub.TRANSACTION_I2cWrite, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void I2cWriteRegByte(String name, int address, int reg, byte val) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(name);
                    _data.writeInt(address);
                    _data.writeInt(reg);
                    _data.writeByte(val);
                    this.mRemote.transact(Stub.TRANSACTION_I2cWriteRegByte, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void I2cWriteRegWord(String name, int address, int reg, int val) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(name);
                    _data.writeInt(address);
                    _data.writeInt(reg);
                    _data.writeInt(val);
                    this.mRemote.transact(Stub.TRANSACTION_I2cWriteRegWord, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void I2cWriteRegBuffer(String name, int address, int reg, byte[] data, int size) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(name);
                    _data.writeInt(address);
                    _data.writeInt(reg);
                    _data.writeByteArray(data);
                    _data.writeInt(size);
                    this.mRemote.transact(Stub.TRANSACTION_I2cWriteRegBuffer, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void ListUartDevices(List<String> devices) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(Stub.TRANSACTION_ListUartDevices, _data, _reply, 0);
                    _reply.readException();
                    _reply.readStringList(devices);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public IPeripheralManagerClient GetUartClient(String name, IBinder lifeline) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(name);
                    _data.writeStrongBinder(lifeline);
                    this.mRemote.transact(Stub.TRANSACTION_GetUartClient, _data, _reply, 0);
                    _reply.readException();
                    IPeripheralManagerClient _result = Stub.asInterface(_reply.readStrongBinder());
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void OpenUartDevice(String name) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(name);
                    this.mRemote.transact(Stub.TRANSACTION_OpenUartDevice, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void ReleaseUartDevice(String name) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(name);
                    this.mRemote.transact(Stub.TRANSACTION_ReleaseUartDevice, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void UartDeviceSetBaudrate(String name, int baudrate) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(name);
                    _data.writeInt(baudrate);
                    this.mRemote.transact(Stub.TRANSACTION_UartDeviceSetBaudrate, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void UartDeviceSetStopBits(String name, int stop_bits) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(name);
                    _data.writeInt(stop_bits);
                    this.mRemote.transact(Stub.TRANSACTION_UartDeviceSetStopBits, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void UartDeviceSetDataSize(String name, int data_size) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(name);
                    _data.writeInt(data_size);
                    this.mRemote.transact(Stub.TRANSACTION_UartDeviceSetDataSize, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void UartDeviceSetParity(String name, int mode) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(name);
                    _data.writeInt(mode);
                    this.mRemote.transact(Stub.TRANSACTION_UartDeviceSetParity, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void UartDeviceSetHardwareFlowControl(String name, int mode) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(name);
                    _data.writeInt(mode);
                    this.mRemote.transact(Stub.TRANSACTION_UartDeviceSetHardwareFlowControl, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void UartDeviceSetModemControl(String name, int bits) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(name);
                    _data.writeInt(bits);
                    this.mRemote.transact(Stub.TRANSACTION_UartDeviceSetModemControl, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void UartDeviceClearModemControl(String name, int bits) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(name);
                    _data.writeInt(bits);
                    this.mRemote.transact(Stub.TRANSACTION_UartDeviceClearModemControl, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void UartDeviceSendBreak(String name, int duration_msecs) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(name);
                    _data.writeInt(duration_msecs);
                    this.mRemote.transact(Stub.TRANSACTION_UartDeviceSendBreak, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void UartDeviceFlush(String name, int queue) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(name);
                    _data.writeInt(queue);
                    this.mRemote.transact(Stub.TRANSACTION_UartDeviceFlush, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int UartDeviceWrite(String name, byte[] data, int size) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(name);
                    _data.writeByteArray(data);
                    _data.writeInt(size);
                    this.mRemote.transact(Stub.TRANSACTION_UartDeviceWrite, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int UartDeviceRead(String name, byte[] data, int size) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(name);
                    if (data == null) {
                        _data.writeInt(-1);
                    } else {
                        _data.writeInt(data.length);
                    }
                    _data.writeInt(size);
                    this.mRemote.transact(Stub.TRANSACTION_UartDeviceRead, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    _reply.readByteArray(data);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public FileDescriptor UartDeviceGetPollingFd(String name) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(name);
                    this.mRemote.transact(Stub.TRANSACTION_UartDeviceGetPollingFd, _data, _reply, 0);
                    _reply.readException();
                    FileDescriptor _result = _reply.readRawFileDescriptor();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IPeripheralManagerClient asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin == null || !(iin instanceof IPeripheralManagerClient)) {
                return new Proxy(obj);
            }
            return (IPeripheralManagerClient) iin;
        }

        public IBinder asBinder() {
            return this;
        }

        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            List<String> _arg0;
            IPeripheralManagerClient _result;
            FileDescriptor _result2;
            String _arg02;
            byte[] _arg1;
            int _arg2_length;
            byte[] bArr;
            int _arg12;
            int _result3;
            switch (code) {
                case 1:
                    data.enforceInterface(DESCRIPTOR);
                    _arg0 = new ArrayList();
                    ListGpio(_arg0);
                    reply.writeNoException();
                    reply.writeStringList(_arg0);
                    return true;
                case 2:
                    data.enforceInterface(DESCRIPTOR);
                    _result = GetGpioClient(data.readString(), data.readStrongBinder());
                    reply.writeNoException();
                    reply.writeStrongBinder(_result != null ? _result.asBinder() : null);
                    return true;
                case 3:
                    data.enforceInterface(DESCRIPTOR);
                    OpenGpio(data.readString());
                    reply.writeNoException();
                    return true;
                case 4:
                    data.enforceInterface(DESCRIPTOR);
                    ReleaseGpio(data.readString());
                    reply.writeNoException();
                    return true;
                case 5:
                    data.enforceInterface(DESCRIPTOR);
                    SetGpioEdge(data.readString(), data.readInt());
                    reply.writeNoException();
                    return true;
                case 6:
                    data.enforceInterface(DESCRIPTOR);
                    SetGpioActiveType(data.readString(), data.readInt());
                    reply.writeNoException();
                    return true;
                case 7:
                    data.enforceInterface(DESCRIPTOR);
                    SetGpioDirection(data.readString(), data.readInt());
                    reply.writeNoException();
                    return true;
                case 8:
                    data.enforceInterface(DESCRIPTOR);
                    SetGpioValue(data.readString(), data.readInt() != 0);
                    reply.writeNoException();
                    return true;
                case 9:
                    data.enforceInterface(DESCRIPTOR);
                    boolean _result4 = GetGpioValue(data.readString());
                    reply.writeNoException();
                    reply.writeInt(_result4 ? 1 : 0);
                    return true;
                case 10:
                    data.enforceInterface(DESCRIPTOR);
                    _result2 = GetGpioPollingFd(data.readString());
                    reply.writeNoException();
                    reply.writeRawFileDescriptor(_result2);
                    return true;
                case 11:
                    data.enforceInterface(DESCRIPTOR);
                    _arg0 = new ArrayList();
                    ListPwm(_arg0);
                    reply.writeNoException();
                    reply.writeStringList(_arg0);
                    return true;
                case 12:
                    data.enforceInterface(DESCRIPTOR);
                    _result = GetPwmClient(data.readString(), data.readStrongBinder());
                    reply.writeNoException();
                    reply.writeStrongBinder(_result != null ? _result.asBinder() : null);
                    return true;
                case TRANSACTION_OpenPwm /*13*/:
                    data.enforceInterface(DESCRIPTOR);
                    OpenPwm(data.readString());
                    reply.writeNoException();
                    return true;
                case TRANSACTION_ReleasePwm /*14*/:
                    data.enforceInterface(DESCRIPTOR);
                    ReleasePwm(data.readString());
                    reply.writeNoException();
                    return true;
                case TRANSACTION_PwmSetEnabled /*15*/:
                    data.enforceInterface(DESCRIPTOR);
                    PwmSetEnabled(data.readString(), data.readInt() != 0);
                    reply.writeNoException();
                    return true;
                case 16:
                    data.enforceInterface(DESCRIPTOR);
                    PwmSetDutyCycle(data.readString(), data.readDouble());
                    reply.writeNoException();
                    return true;
                case 17:
                    data.enforceInterface(DESCRIPTOR);
                    PwmSetFrequencyHz(data.readString(), data.readDouble());
                    reply.writeNoException();
                    return true;
                case TRANSACTION_ListSpiBuses /*18*/:
                    data.enforceInterface(DESCRIPTOR);
                    _arg0 = new ArrayList();
                    ListSpiBuses(_arg0);
                    reply.writeNoException();
                    reply.writeStringList(_arg0);
                    return true;
                case TRANSACTION_GetSpiClient /*19*/:
                    data.enforceInterface(DESCRIPTOR);
                    _result = GetSpiClient(data.readString(), data.readStrongBinder());
                    reply.writeNoException();
                    reply.writeStrongBinder(_result != null ? _result.asBinder() : null);
                    return true;
                case 20:
                    data.enforceInterface(DESCRIPTOR);
                    OpenSpiDevice(data.readString());
                    reply.writeNoException();
                    return true;
                case 21:
                    data.enforceInterface(DESCRIPTOR);
                    ReleaseSpiDevice(data.readString());
                    reply.writeNoException();
                    return true;
                case 22:
                    data.enforceInterface(DESCRIPTOR);
                    _arg02 = data.readString();
                    _arg1 = data.createByteArray();
                    _arg2_length = data.readInt();
                    if (_arg2_length < 0) {
                        bArr = null;
                    } else {
                        bArr = new byte[_arg2_length];
                    }
                    SpiDeviceTransfer(_arg02, _arg1, bArr, data.readInt());
                    reply.writeNoException();
                    reply.writeByteArray(bArr);
                    return true;
                case TRANSACTION_SpiDeviceSetFrequency /*23*/:
                    data.enforceInterface(DESCRIPTOR);
                    SpiDeviceSetFrequency(data.readString(), data.readInt());
                    reply.writeNoException();
                    return true;
                case 24:
                    data.enforceInterface(DESCRIPTOR);
                    SpiDeviceSetBitJustification(data.readString(), data.readInt() != 0);
                    reply.writeNoException();
                    return true;
                case TRANSACTION_SpiDeviceSetMode /*25*/:
                    data.enforceInterface(DESCRIPTOR);
                    SpiDeviceSetMode(data.readString(), data.readInt());
                    reply.writeNoException();
                    return true;
                case TRANSACTION_SpiDeviceSetBitsPerWord /*26*/:
                    data.enforceInterface(DESCRIPTOR);
                    SpiDeviceSetBitsPerWord(data.readString(), data.readInt());
                    reply.writeNoException();
                    return true;
                case TRANSACTION_SpiDeviceSetDelay /*27*/:
                    data.enforceInterface(DESCRIPTOR);
                    SpiDeviceSetDelay(data.readString(), data.readInt());
                    reply.writeNoException();
                    return true;
                case TRANSACTION_SpiDeviceSetCsChange /*28*/:
                    data.enforceInterface(DESCRIPTOR);
                    SpiDeviceSetCsChange(data.readString(), data.readInt() != 0);
                    reply.writeNoException();
                    return true;
                case TRANSACTION_ListI2cBuses /*29*/:
                    data.enforceInterface(DESCRIPTOR);
                    _arg0 = new ArrayList();
                    ListI2cBuses(_arg0);
                    reply.writeNoException();
                    reply.writeStringList(_arg0);
                    return true;
                case TRANSACTION_GetI2cClient /*30*/:
                    data.enforceInterface(DESCRIPTOR);
                    _result = GetI2cClient(data.readString(), data.readStrongBinder());
                    reply.writeNoException();
                    reply.writeStrongBinder(_result != null ? _result.asBinder() : null);
                    return true;
                case TRANSACTION_OpenI2cDevice /*31*/:
                    data.enforceInterface(DESCRIPTOR);
                    OpenI2cDevice(data.readString(), data.readInt());
                    reply.writeNoException();
                    return true;
                case 32:
                    data.enforceInterface(DESCRIPTOR);
                    ReleaseI2cDevice(data.readString(), data.readInt());
                    reply.writeNoException();
                    return true;
                case TRANSACTION_I2cRead /*33*/:
                    data.enforceInterface(DESCRIPTOR);
                    _arg02 = data.readString();
                    _arg12 = data.readInt();
                    _arg2_length = data.readInt();
                    if (_arg2_length < 0) {
                        bArr = null;
                    } else {
                        bArr = new byte[_arg2_length];
                    }
                    I2cRead(_arg02, _arg12, bArr, data.readInt());
                    reply.writeNoException();
                    reply.writeByteArray(bArr);
                    return true;
                case TRANSACTION_I2cReadRegByte /*34*/:
                    data.enforceInterface(DESCRIPTOR);
                    byte _result5 = I2cReadRegByte(data.readString(), data.readInt(), data.readInt());
                    reply.writeNoException();
                    reply.writeByte(_result5);
                    return true;
                case TRANSACTION_I2cReadRegWord /*35*/:
                    data.enforceInterface(DESCRIPTOR);
                    _result3 = I2cReadRegWord(data.readString(), data.readInt(), data.readInt());
                    reply.writeNoException();
                    reply.writeInt(_result3);
                    return true;
                case TRANSACTION_I2cReadRegBuffer /*36*/:
                    byte[] bArr2;
                    data.enforceInterface(DESCRIPTOR);
                    _arg02 = data.readString();
                    _arg12 = data.readInt();
                    int _arg2 = data.readInt();
                    int _arg3_length = data.readInt();
                    if (_arg3_length < 0) {
                        bArr2 = null;
                    } else {
                        bArr2 = new byte[_arg3_length];
                    }
                    I2cReadRegBuffer(_arg02, _arg12, _arg2, bArr2, data.readInt());
                    reply.writeNoException();
                    reply.writeByteArray(bArr2);
                    return true;
                case TRANSACTION_I2cWrite /*37*/:
                    data.enforceInterface(DESCRIPTOR);
                    I2cWrite(data.readString(), data.readInt(), data.createByteArray(), data.readInt());
                    reply.writeNoException();
                    return true;
                case TRANSACTION_I2cWriteRegByte /*38*/:
                    data.enforceInterface(DESCRIPTOR);
                    I2cWriteRegByte(data.readString(), data.readInt(), data.readInt(), data.readByte());
                    reply.writeNoException();
                    return true;
                case TRANSACTION_I2cWriteRegWord /*39*/:
                    data.enforceInterface(DESCRIPTOR);
                    I2cWriteRegWord(data.readString(), data.readInt(), data.readInt(), data.readInt());
                    reply.writeNoException();
                    return true;
                case TRANSACTION_I2cWriteRegBuffer /*40*/:
                    data.enforceInterface(DESCRIPTOR);
                    I2cWriteRegBuffer(data.readString(), data.readInt(), data.readInt(), data.createByteArray(), data.readInt());
                    reply.writeNoException();
                    return true;
                case TRANSACTION_ListUartDevices /*41*/:
                    data.enforceInterface(DESCRIPTOR);
                    _arg0 = new ArrayList();
                    ListUartDevices(_arg0);
                    reply.writeNoException();
                    reply.writeStringList(_arg0);
                    return true;
                case TRANSACTION_GetUartClient /*42*/:
                    data.enforceInterface(DESCRIPTOR);
                    _result = GetUartClient(data.readString(), data.readStrongBinder());
                    reply.writeNoException();
                    reply.writeStrongBinder(_result != null ? _result.asBinder() : null);
                    return true;
                case TRANSACTION_OpenUartDevice /*43*/:
                    data.enforceInterface(DESCRIPTOR);
                    OpenUartDevice(data.readString());
                    reply.writeNoException();
                    return true;
                case TRANSACTION_ReleaseUartDevice /*44*/:
                    data.enforceInterface(DESCRIPTOR);
                    ReleaseUartDevice(data.readString());
                    reply.writeNoException();
                    return true;
                case TRANSACTION_UartDeviceSetBaudrate /*45*/:
                    data.enforceInterface(DESCRIPTOR);
                    UartDeviceSetBaudrate(data.readString(), data.readInt());
                    reply.writeNoException();
                    return true;
                case TRANSACTION_UartDeviceSetStopBits /*46*/:
                    data.enforceInterface(DESCRIPTOR);
                    UartDeviceSetStopBits(data.readString(), data.readInt());
                    reply.writeNoException();
                    return true;
                case TRANSACTION_UartDeviceSetDataSize /*47*/:
                    data.enforceInterface(DESCRIPTOR);
                    UartDeviceSetDataSize(data.readString(), data.readInt());
                    reply.writeNoException();
                    return true;
                case TRANSACTION_UartDeviceSetParity /*48*/:
                    data.enforceInterface(DESCRIPTOR);
                    UartDeviceSetParity(data.readString(), data.readInt());
                    reply.writeNoException();
                    return true;
                case TRANSACTION_UartDeviceSetHardwareFlowControl /*49*/:
                    data.enforceInterface(DESCRIPTOR);
                    UartDeviceSetHardwareFlowControl(data.readString(), data.readInt());
                    reply.writeNoException();
                    return true;
                case TRANSACTION_UartDeviceSetModemControl /*50*/:
                    data.enforceInterface(DESCRIPTOR);
                    UartDeviceSetModemControl(data.readString(), data.readInt());
                    reply.writeNoException();
                    return true;
                case TRANSACTION_UartDeviceClearModemControl /*51*/:
                    data.enforceInterface(DESCRIPTOR);
                    UartDeviceClearModemControl(data.readString(), data.readInt());
                    reply.writeNoException();
                    return true;
                case TRANSACTION_UartDeviceSendBreak /*52*/:
                    data.enforceInterface(DESCRIPTOR);
                    UartDeviceSendBreak(data.readString(), data.readInt());
                    reply.writeNoException();
                    return true;
                case TRANSACTION_UartDeviceFlush /*53*/:
                    data.enforceInterface(DESCRIPTOR);
                    UartDeviceFlush(data.readString(), data.readInt());
                    reply.writeNoException();
                    return true;
                case TRANSACTION_UartDeviceWrite /*54*/:
                    data.enforceInterface(DESCRIPTOR);
                    _result3 = UartDeviceWrite(data.readString(), data.createByteArray(), data.readInt());
                    reply.writeNoException();
                    reply.writeInt(_result3);
                    return true;
                case TRANSACTION_UartDeviceRead /*55*/:
                    data.enforceInterface(DESCRIPTOR);
                    _arg02 = data.readString();
                    int _arg1_length = data.readInt();
                    if (_arg1_length < 0) {
                        _arg1 = null;
                    } else {
                        _arg1 = new byte[_arg1_length];
                    }
                    _result3 = UartDeviceRead(_arg02, _arg1, data.readInt());
                    reply.writeNoException();
                    reply.writeInt(_result3);
                    reply.writeByteArray(_arg1);
                    return true;
                case TRANSACTION_UartDeviceGetPollingFd /*56*/:
                    data.enforceInterface(DESCRIPTOR);
                    _result2 = UartDeviceGetPollingFd(data.readString());
                    reply.writeNoException();
                    reply.writeRawFileDescriptor(_result2);
                    return true;
                case 1598968902:
                    reply.writeString(DESCRIPTOR);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }
    }

    IPeripheralManagerClient GetGpioClient(String str, IBinder iBinder) throws RemoteException;

    FileDescriptor GetGpioPollingFd(String str) throws RemoteException;

    boolean GetGpioValue(String str) throws RemoteException;

    IPeripheralManagerClient GetI2cClient(String str, IBinder iBinder) throws RemoteException;

    IPeripheralManagerClient GetPwmClient(String str, IBinder iBinder) throws RemoteException;

    IPeripheralManagerClient GetSpiClient(String str, IBinder iBinder) throws RemoteException;

    IPeripheralManagerClient GetUartClient(String str, IBinder iBinder) throws RemoteException;

    void I2cRead(String str, int i, byte[] bArr, int i2) throws RemoteException;

    void I2cReadRegBuffer(String str, int i, int i2, byte[] bArr, int i3) throws RemoteException;

    byte I2cReadRegByte(String str, int i, int i2) throws RemoteException;

    int I2cReadRegWord(String str, int i, int i2) throws RemoteException;

    void I2cWrite(String str, int i, byte[] bArr, int i2) throws RemoteException;

    void I2cWriteRegBuffer(String str, int i, int i2, byte[] bArr, int i3) throws RemoteException;

    void I2cWriteRegByte(String str, int i, int i2, byte b) throws RemoteException;

    void I2cWriteRegWord(String str, int i, int i2, int i3) throws RemoteException;

    void ListGpio(List<String> list) throws RemoteException;

    void ListI2cBuses(List<String> list) throws RemoteException;

    void ListPwm(List<String> list) throws RemoteException;

    void ListSpiBuses(List<String> list) throws RemoteException;

    void ListUartDevices(List<String> list) throws RemoteException;

    void OpenGpio(String str) throws RemoteException;

    void OpenI2cDevice(String str, int i) throws RemoteException;

    void OpenPwm(String str) throws RemoteException;

    void OpenSpiDevice(String str) throws RemoteException;

    void OpenUartDevice(String str) throws RemoteException;

    void PwmSetDutyCycle(String str, double d) throws RemoteException;

    void PwmSetEnabled(String str, boolean z) throws RemoteException;

    void PwmSetFrequencyHz(String str, double d) throws RemoteException;

    void ReleaseGpio(String str) throws RemoteException;

    void ReleaseI2cDevice(String str, int i) throws RemoteException;

    void ReleasePwm(String str) throws RemoteException;

    void ReleaseSpiDevice(String str) throws RemoteException;

    void ReleaseUartDevice(String str) throws RemoteException;

    void SetGpioActiveType(String str, int i) throws RemoteException;

    void SetGpioDirection(String str, int i) throws RemoteException;

    void SetGpioEdge(String str, int i) throws RemoteException;

    void SetGpioValue(String str, boolean z) throws RemoteException;

    void SpiDeviceSetBitJustification(String str, boolean z) throws RemoteException;

    void SpiDeviceSetBitsPerWord(String str, int i) throws RemoteException;

    void SpiDeviceSetCsChange(String str, boolean z) throws RemoteException;

    void SpiDeviceSetDelay(String str, int i) throws RemoteException;

    void SpiDeviceSetFrequency(String str, int i) throws RemoteException;

    void SpiDeviceSetMode(String str, int i) throws RemoteException;

    void SpiDeviceTransfer(String str, byte[] bArr, byte[] bArr2, int i) throws RemoteException;

    void UartDeviceClearModemControl(String str, int i) throws RemoteException;

    void UartDeviceFlush(String str, int i) throws RemoteException;

    FileDescriptor UartDeviceGetPollingFd(String str) throws RemoteException;

    int UartDeviceRead(String str, byte[] bArr, int i) throws RemoteException;

    void UartDeviceSendBreak(String str, int i) throws RemoteException;

    void UartDeviceSetBaudrate(String str, int i) throws RemoteException;

    void UartDeviceSetDataSize(String str, int i) throws RemoteException;

    void UartDeviceSetHardwareFlowControl(String str, int i) throws RemoteException;

    void UartDeviceSetModemControl(String str, int i) throws RemoteException;

    void UartDeviceSetParity(String str, int i) throws RemoteException;

    void UartDeviceSetStopBits(String str, int i) throws RemoteException;

    int UartDeviceWrite(String str, byte[] bArr, int i) throws RemoteException;
}
