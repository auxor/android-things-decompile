package com.google.android.things.pio;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.MessageQueue.OnFileDescriptorEventListener;
import android.system.ErrnoException;
import android.system.Os;
import android.system.OsConstants;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import libcore.io.IoUtils;

abstract class UserDriverCallback {
    private final List<CallbackPipe> mCallbackPipes = new LinkedList();
    private final Handler mHandler;
    private final HandlerThread mHandlerThread;
    private String mIoName;

    private class CallbackPipe implements OnFileDescriptorEventListener {
        private final FileDescriptor mLocalFd;
        private final FileInputStream mPipeInputStream;
        private final FileOutputStream mPipeOutputStream;
        private boolean mWaitingForAck = false;

        public CallbackPipe(FileDescriptor localFd) {
            this.mLocalFd = localFd;
            this.mPipeInputStream = new FileInputStream(this.mLocalFd);
            this.mPipeOutputStream = new FileOutputStream(this.mLocalFd);
            UserDriverCallback.this.mHandlerThread.getLooper().getQueue().addOnFileDescriptorEventListener(this.mLocalFd, 5, this);
        }

        public int onFileDescriptorEvents(java.io.FileDescriptor r9, int r10) {
            /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Exception block dominator not found, method:com.google.android.things.pio.UserDriverCallback.CallbackPipe.onFileDescriptorEvents(java.io.FileDescriptor, int):int. bs: [B:9:0x0018, B:29:0x0032, B:46:0x0053]
	at jadx.core.dex.visitors.regions.ProcessTryCatchRegions.searchTryCatchDominators(ProcessTryCatchRegions.java:86)
	at jadx.core.dex.visitors.regions.ProcessTryCatchRegions.process(ProcessTryCatchRegions.java:45)
	at jadx.core.dex.visitors.regions.RegionMakerVisitor.postProcessRegions(RegionMakerVisitor.java:63)
	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:58)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.ProcessClass.process(ProcessClass.java:34)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:56)
	at jadx.core.ProcessClass.process(ProcessClass.java:39)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:282)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler.lambda$appendSourcesSave$0(JadxDecompiler.java:200)
	at jadx.api.JadxDecompiler$$Lambda$6/841283083.run(Unknown Source)
*/
            /*
            r8 = this;
            r6 = 0;
            r4 = 0;
            r2 = 0;
            r3 = com.google.android.things.pio.UserDriverCallback.this;	 Catch:{ Throwable -> 0x006b, all -> 0x0085 }
            r2 = r3.getDriverLock();	 Catch:{ Throwable -> 0x006b, all -> 0x0085 }
            r3 = r8.mLocalFd;	 Catch:{ Throwable -> 0x006b, all -> 0x0085 }
            r3 = r3.valid();	 Catch:{ Throwable -> 0x006b, all -> 0x0085 }
            if (r3 != 0) goto L_0x001e;
        L_0x0011:
            if (r2 == 0) goto L_0x0016;
        L_0x0013:
            r2.close();	 Catch:{ Throwable -> 0x001b }
        L_0x0016:
            if (r4 == 0) goto L_0x001d;
        L_0x0018:
            throw r4;	 Catch:{ IOException -> 0x0019 }
        L_0x0019:
            r1 = move-exception;
            return r6;
        L_0x001b:
            r4 = move-exception;
            goto L_0x0016;
        L_0x001d:
            return r6;
        L_0x001e:
            r3 = r10 & 4;
            if (r3 == 0) goto L_0x0032;
        L_0x0022:
            r3 = com.google.android.things.pio.UserDriverCallback.this;	 Catch:{ Throwable -> 0x006b, all -> 0x0085 }
            r3.unregisterPipeLocked(r8);	 Catch:{ Throwable -> 0x006b, all -> 0x0085 }
            if (r2 == 0) goto L_0x002c;
        L_0x0029:
            r2.close();	 Catch:{ Throwable -> 0x002f }
        L_0x002c:
            if (r4 == 0) goto L_0x0031;
        L_0x002e:
            throw r4;	 Catch:{ IOException -> 0x0019 }
        L_0x002f:
            r4 = move-exception;
            goto L_0x002c;
        L_0x0031:
            return r6;
        L_0x0032:
            r3 = r8.mPipeInputStream;	 Catch:{ IOException -> 0x004b }
            r0 = r3.read();	 Catch:{ IOException -> 0x004b }
            r3 = 0;
            r8.mWaitingForAck = r3;	 Catch:{ Throwable -> 0x006b, all -> 0x0085 }
            r3 = -1;	 Catch:{ Throwable -> 0x006b, all -> 0x0085 }
            if (r0 != r3) goto L_0x005f;	 Catch:{ Throwable -> 0x006b, all -> 0x0085 }
        L_0x003e:
            r3 = com.google.android.things.pio.UserDriverCallback.this;	 Catch:{ Throwable -> 0x006b, all -> 0x0085 }
            r3.unregisterPipeLocked(r8);	 Catch:{ Throwable -> 0x006b, all -> 0x0085 }
            if (r2 == 0) goto L_0x0048;
        L_0x0045:
            r2.close();	 Catch:{ Throwable -> 0x005c }
        L_0x0048:
            if (r4 == 0) goto L_0x005e;
        L_0x004a:
            throw r4;	 Catch:{ IOException -> 0x0019 }
        L_0x004b:
            r1 = move-exception;
            r3 = com.google.android.things.pio.UserDriverCallback.this;	 Catch:{ Throwable -> 0x006b, all -> 0x0085 }
            r3.unregisterPipeLocked(r8);	 Catch:{ Throwable -> 0x006b, all -> 0x0085 }
            if (r2 == 0) goto L_0x0056;
        L_0x0053:
            r2.close();	 Catch:{ Throwable -> 0x0059 }
        L_0x0056:
            if (r4 == 0) goto L_0x005b;
        L_0x0058:
            throw r4;	 Catch:{ IOException -> 0x0019 }
        L_0x0059:
            r4 = move-exception;
            goto L_0x0056;
        L_0x005b:
            return r6;
        L_0x005c:
            r4 = move-exception;
            goto L_0x0048;
        L_0x005e:
            return r6;
        L_0x005f:
            r3 = 5;
            if (r2 == 0) goto L_0x0065;
        L_0x0062:
            r2.close();	 Catch:{ Throwable -> 0x0068 }
        L_0x0065:
            if (r4 == 0) goto L_0x006a;
        L_0x0067:
            throw r4;	 Catch:{ IOException -> 0x0019 }
        L_0x0068:
            r4 = move-exception;
            goto L_0x0065;
        L_0x006a:
            return r3;
        L_0x006b:
            r3 = move-exception;
            throw r3;	 Catch:{ all -> 0x006d }
        L_0x006d:
            r4 = move-exception;
            r7 = r4;
            r4 = r3;
            r3 = r7;
        L_0x0071:
            if (r2 == 0) goto L_0x0076;
        L_0x0073:
            r2.close();	 Catch:{ Throwable -> 0x0079 }
        L_0x0076:
            if (r4 == 0) goto L_0x0084;
        L_0x0078:
            throw r4;	 Catch:{ IOException -> 0x0019 }
        L_0x0079:
            r5 = move-exception;	 Catch:{ IOException -> 0x0019 }
            if (r4 != 0) goto L_0x007e;	 Catch:{ IOException -> 0x0019 }
        L_0x007c:
            r4 = r5;	 Catch:{ IOException -> 0x0019 }
            goto L_0x0076;	 Catch:{ IOException -> 0x0019 }
        L_0x007e:
            if (r4 == r5) goto L_0x0076;	 Catch:{ IOException -> 0x0019 }
        L_0x0080:
            r4.addSuppressed(r5);	 Catch:{ IOException -> 0x0019 }
            goto L_0x0076;	 Catch:{ IOException -> 0x0019 }
        L_0x0084:
            throw r3;	 Catch:{ IOException -> 0x0019 }
        L_0x0085:
            r3 = move-exception;
            goto L_0x0071;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.things.pio.UserDriverCallback.CallbackPipe.onFileDescriptorEvents(java.io.FileDescriptor, int):int");
        }

        public boolean triggerEventLocked() {
            if (!this.mWaitingForAck) {
                try {
                    this.mPipeOutputStream.write(49);
                    this.mWaitingForAck = true;
                } catch (IOException e) {
                    return false;
                }
            }
            return true;
        }

        public void closeLocked() {
            UserDriverCallback.this.mHandlerThread.getLooper().getQueue().removeOnFileDescriptorEventListener(this.mLocalFd);
            IoUtils.closeQuietly(this.mLocalFd);
            IoUtils.closeQuietly(this.mPipeInputStream);
            IoUtils.closeQuietly(this.mPipeOutputStream);
        }
    }

    abstract DriverLock<?> getDriverLock() throws IOException;

    abstract void registerCallback() throws IOException;

    abstract void unregisterCallback();

    public UserDriverCallback(String ioName) {
        this.mIoName = ioName;
        this.mHandlerThread = new HandlerThread(this.mIoName + " userdriver callback");
        this.mHandlerThread.start();
        Looper looper = this.mHandlerThread.getLooper();
        if (looper == null) {
            throw new IllegalStateException("Failed to start " + this.mIoName + " callback thread");
        }
        this.mHandler = new Handler(looper);
    }

    public Handler getHandler() {
        return this.mHandler;
    }

    public String getIoName() {
        return this.mIoName;
    }

    boolean triggerEvent() {
        Throwable th;
        Throwable th2 = null;
        List<CallbackPipe> pipesToRemove = new LinkedList();
        DriverLock driverLock = null;
        try {
            driverLock = getDriverLock();
            for (CallbackPipe pipe : this.mCallbackPipes) {
                if (!pipe.triggerEventLocked()) {
                    pipesToRemove.add(pipe);
                }
            }
            for (CallbackPipe pipe2 : pipesToRemove) {
                unregisterPipeLocked(pipe2);
            }
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
            return true;
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
            try {
                throw th22;
            } catch (IOException e) {
            }
        } else {
            throw th;
        }
    }

    void triggerError() {
        Throwable th;
        Throwable th2 = null;
        DriverLock driverLock = null;
        try {
            DriverLock<?> lock = getDriverLock();
            while (!this.mCallbackPipes.isEmpty()) {
                unregisterPipeLocked((CallbackPipe) this.mCallbackPipes.get(0));
            }
            if (lock != null) {
                try {
                    lock.close();
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
            try {
                throw th22;
            } catch (IOException e) {
                return;
            }
        }
        throw th;
    }

    public FileDescriptor registerNewPipeLocked() throws IOException {
        FileDescriptor localFd = new FileDescriptor();
        FileDescriptor callerFd = new FileDescriptor();
        try {
            Os.socketpair(OsConstants.AF_UNIX, OsConstants.SOCK_SEQPACKET, 0, localFd, callerFd);
            if (this.mCallbackPipes.isEmpty()) {
                try {
                    registerCallback();
                } catch (Exception e) {
                    IoUtils.closeQuietly(localFd);
                    IoUtils.closeQuietly(callerFd);
                    throw e;
                }
            }
            this.mCallbackPipes.add(new CallbackPipe(localFd));
            return callerFd;
        } catch (ErrnoException e2) {
            throw new PioException(e2.errno, "Failed to create callback socketpair for " + this.mIoName);
        }
    }

    private void unregisterPipeLocked(CallbackPipe callbackPipe) {
        callbackPipe.closeLocked();
        this.mCallbackPipes.remove(callbackPipe);
        if (this.mCallbackPipes.isEmpty()) {
            unregisterCallback();
        }
    }

    void close() {
        triggerError();
        this.mHandlerThread.getLooper().quitSafely();
    }
}
