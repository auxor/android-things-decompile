package com.google.android.things.userdriver;

import android.os.IBinder;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.system.OsConstants;
import android.util.Log;
import com.google.android.things.userdriver.ISensorDriverManager.Stub;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

class SensorDriverManager {
    private static final int POLL_QUEUE_CAPACITY = 200;
    private static final String TAG = "SensorDriverManager";
    private final BlockingQueue<SensorDriverEvent> mEventQueue = new ArrayBlockingQueue(POLL_QUEUE_CAPACITY, true);
    private final IBinder mManagerBinder = new Stub() {
        public int activate(int sensorHandle, int enabled) {
            SensorThreadTask task = (SensorThreadTask) SensorDriverManager.this.mSensorTasks.get(Integer.valueOf(sensorHandle));
            if (task == null) {
                return -OsConstants.ENODEV;
            }
            try {
                task.activate(enabled != 0);
                return 0;
            } catch (IOException e) {
                return -OsConstants.EIO;
            }
        }

        public int setDelay(int sensorHandle, long samplingPeriodNs) {
            SensorThreadTask task = (SensorThreadTask) SensorDriverManager.this.mSensorTasks.get(Integer.valueOf(sensorHandle));
            if (task == null) {
                return -OsConstants.ENODEV;
            }
            task.setTiming(samplingPeriodNs);
            return 0;
        }

        public SensorDriverEvent[] poll(int maxEvents) {
            ArrayList<SensorDriverEvent> events = new ArrayList();
            try {
                events.add((SensorDriverEvent) SensorDriverManager.this.mEventQueue.take());
                SensorDriverManager.this.mEventQueue.drainTo(events, maxEvents - 1);
                return (SensorDriverEvent[]) events.toArray(new SensorDriverEvent[0]);
            } catch (InterruptedException e) {
                Log.d(SensorDriverManager.TAG, "Poll was interrupted, returning without any events");
                return new SensorDriverEvent[0];
            }
        }

        public int batch(int sensorHandle, int flags, long samplingPeriodNs, long maxReportLatencyNs) {
            SensorThreadTask task = (SensorThreadTask) SensorDriverManager.this.mSensorTasks.get(Integer.valueOf(sensorHandle));
            if (task == null) {
                return -OsConstants.ENODEV;
            }
            task.setTiming(samplingPeriodNs, maxReportLatencyNs);
            return 0;
        }

        public int flush(int sensorHandle) {
            if (((SensorThreadTask) SensorDriverManager.this.mSensorTasks.get(Integer.valueOf(sensorHandle))) == null) {
                return -OsConstants.ENODEV;
            }
            try {
                SensorDriverManager.this.mEventQueue.put(new SensorDriverEventFlush(sensorHandle));
                return 0;
            } catch (InterruptedException e) {
                Log.d(SensorDriverManager.TAG, "Flush was interrupted");
                return -OsConstants.EINTR;
            }
        }
    };
    private int mSensorHandleCounter = 1;
    private final ConcurrentMap<Integer, SensorThreadTask> mSensorTasks = new ConcurrentHashMap();
    private ISensorDriverService mService = ISensorDriverService.Stub.asInterface(ServiceManager.getService("sensordriverservice"));
    private final ScheduledThreadPoolExecutor mThreadPool = new ScheduledThreadPoolExecutor(0);

    private class SensorThreadTask implements Runnable {
        private ScheduledFuture<?> mFuture;
        private final int mHandle;
        private final Object mLock = new Object();
        private long mMaxReportLatencyNs;
        private UserSensorReading mPreviousReading;
        private long mSamplingPeriodNs;
        private final UserSensor mSensor;

        SensorThreadTask(int handle, UserSensor sensor) {
            this.mHandle = handle;
            this.mSensor = sensor;
            this.mSamplingPeriodNs = (long) (sensor.getMaxDelay() * 1000);
        }

        void setTiming(long samplingPeriodNs) {
            synchronized (this.mLock) {
                updateTimingLocked(samplingPeriodNs, this.mMaxReportLatencyNs);
            }
        }

        void setTiming(long samplingPeriodNs, long maxReportLatencyNs) {
            synchronized (this.mLock) {
                updateTimingLocked(samplingPeriodNs, maxReportLatencyNs);
            }
        }

        private void updateTimingLocked(long samplingPeriodNs, long maxReportLatencyNs) {
            this.mSamplingPeriodNs = Math.max(Math.min(samplingPeriodNs, ((long) this.mSensor.getMaxDelay()) * 1000), Math.max(((long) this.mSensor.getMinDelay()) * 1000, 1000000));
            this.mMaxReportLatencyNs = maxReportLatencyNs;
            if (this.mFuture != null) {
                this.mFuture.cancel(false);
                this.mFuture = SensorDriverManager.this.mThreadPool.scheduleAtFixedRate(this, 0, this.mSamplingPeriodNs, TimeUnit.NANOSECONDS);
            }
        }

        void activate(boolean enable) throws IOException {
            synchronized (this.mLock) {
                this.mSensor.getDriver().setEnabled(enable);
                this.mPreviousReading = null;
                if (enable) {
                    if (this.mFuture == null) {
                        this.mFuture = SensorDriverManager.this.mThreadPool.scheduleAtFixedRate(this, 0, this.mSamplingPeriodNs, TimeUnit.NANOSECONDS);
                    }
                } else if (this.mFuture != null) {
                    this.mFuture.cancel(false);
                    this.mFuture = null;
                }
            }
        }

        public void run() {
            UserSensorReading reading = null;
            synchronized (this.mLock) {
                if (this.mFuture != null) {
                    try {
                        reading = this.mSensor.getDriver().read();
                        if (this.mSensor.getReportingMode() == 1) {
                            if (reading.equals(this.mPreviousReading)) {
                                reading = null;
                            } else {
                                this.mPreviousReading = reading;
                            }
                        }
                    } catch (IOException e) {
                        return;
                    }
                }
            }
            if (reading != null) {
                try {
                    SensorDriverManager.this.mEventQueue.put(new SensorDriverEventData(this.mHandle, this.mSensor.getType(), reading));
                } catch (InterruptedException e2) {
                }
            }
        }
    }

    public SensorDriverManager() {
        if (this.mService == null) {
            throw new IllegalStateException("Couldn't connect to sensordriverservice");
        }
        try {
            this.mService.registerSensorDriverManager(Stub.asInterface(this.mManagerBinder));
        } catch (RemoteException e) {
            throw e.rethrowAsRuntimeException();
        }
    }

    public boolean addSensor(UserSensor sensor) {
        int handle = this.mSensorHandleCounter;
        this.mSensorHandleCounter = handle + 1;
        this.mThreadPool.setCorePoolSize(this.mThreadPool.getCorePoolSize() + 1);
        this.mSensorTasks.put(Integer.valueOf(handle), new SensorThreadTask(handle, sensor));
        try {
            this.mEventQueue.put(new SensorDriverEventDynamicMeta(handle, true, sensor));
            return true;
        } catch (InterruptedException e) {
            Log.d(TAG, "Interrupted while trying to register sensor " + sensor.getName());
            this.mSensorTasks.remove(Integer.valueOf(handle));
            return false;
        }
    }
}
