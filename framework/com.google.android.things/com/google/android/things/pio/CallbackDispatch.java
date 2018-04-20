package com.google.android.things.pio;

import android.os.Handler;
import android.os.MessageQueue.OnFileDescriptorEventListener;
import android.system.OsConstants;
import android.util.Log;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import libcore.io.IoUtils;

abstract class CallbackDispatch implements OnFileDescriptorEventListener {
    private static final String TAG = "CallbackDispatch";
    private boolean mClosed = false;
    private final FileDescriptor mFileDescriptor;
    private final Handler mHandler;
    private final FileInputStream mInputStream;
    private final Object mLock = new Object();
    private final String mName;
    private final FileOutputStream mOutputStream;

    abstract void dispatchInterruptError(int i);

    abstract boolean dispatchInterruptEvent();

    CallbackDispatch(String name, FileDescriptor fileDescriptor, Handler handler) {
        this.mName = name;
        this.mFileDescriptor = fileDescriptor;
        this.mInputStream = new FileInputStream(this.mFileDescriptor);
        this.mOutputStream = new FileOutputStream(this.mFileDescriptor);
        if (handler != null) {
            this.mHandler = handler;
        } else {
            this.mHandler = new Handler();
        }
    }

    final void start() {
        this.mHandler.getLooper().getQueue().addOnFileDescriptorEventListener(this.mFileDescriptor, 5, this);
    }

    final void close() {
        synchronized (this.mLock) {
            this.mHandler.getLooper().getQueue().removeOnFileDescriptorEventListener(this.mFileDescriptor);
            cleanupResources();
        }
    }

    private void cleanupResources() {
        IoUtils.closeQuietly(this.mFileDescriptor);
        IoUtils.closeQuietly(this.mInputStream);
        IoUtils.closeQuietly(this.mOutputStream);
        this.mClosed = true;
    }

    private void communicationPipeClosed() {
        Log.w(TAG, "I/O " + this.mName + " has been unregistered; stopping interrupt listener");
        cleanupResources();
        dispatchInterruptError(OsConstants.ENODEV);
    }

    public final int onFileDescriptorEvents(FileDescriptor fd, int event) {
        synchronized (this.mLock) {
            if (this.mClosed) {
                return 0;
            } else if ((event & 4) != 0) {
                communicationPipeClosed();
                return 0;
            } else {
                try {
                    if (this.mInputStream.read() == -1) {
                        communicationPipeClosed();
                        return 0;
                    } else if (dispatchInterruptEvent()) {
                        this.mOutputStream.write(1);
                        return 5;
                    } else {
                        cleanupResources();
                        return 0;
                    }
                } catch (IOException e) {
                    communicationPipeClosed();
                    return 0;
                }
            }
        }
    }
}
