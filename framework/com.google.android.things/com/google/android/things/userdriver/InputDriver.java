package com.google.android.things.userdriver;

import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.KeyEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class InputDriver {
    private static final int AXIS_X = placeholder();
    private static final int AXIS_Y = placeholder();
    private static final int BTN_TOUCH = placeholder();
    private static final int ENABLE_ABS = placeholder();
    static final int ENABLE_EVENTS = placeholder();
    private static final int ENABLE_KEY = placeholder();
    private static final int ENABLE_PROP = placeholder();
    private static final int ENABLE_REL = placeholder();
    static final int EV_ABS = placeholder();
    static final int EV_KEY = placeholder();
    static final int EV_REL = placeholder();
    static final int EV_SYN = placeholder();
    private static final int PROP_DIRECT = placeholder();
    private static final int PROP_POINTER = placeholder();
    private static final String TAG = "InputDriver";
    private final int[] mAbsFlat;
    private final int[] mAbsFuzz;
    private final int[] mAbsMax;
    private final Map<Integer, Integer> mAbsMaxMap;
    private final int[] mAbsMin;
    private final Map<Integer, Integer> mAbsMinMap;
    private final int mBusType;
    private final int mFfEffectsMax;
    private int mHandle;
    private IBinder mLifeline;
    private final Object mLock;
    private final String mName;
    private final int mProductId;
    private IInputDriverService mService;
    private final Map<Integer, Set<Integer>> mSupportedEvents;
    private final int mVendorId;
    private final int mVersion;

    public static class Builder {
        private ArrayList<Integer> mAbsFlat;
        private ArrayList<Integer> mAbsFuzz;
        private ArrayList<Integer> mAbsMax;
        private ArrayList<Integer> mAbsMin;
        private int mBusType;
        private int mFfEffectsMax;
        private String mName = "";
        private int mProductId;
        private final Map<Integer, Set<Integer>> mSupportedEvents = new HashMap();
        private int mVendorId;
        private int mVersion;

        public Builder(int source) {
            setSource(source);
        }

        private void setSource(int source) {
            boolean invalid_source = true;
            if ((source & 1) != 0) {
                invalid_source = false;
                addToMap(InputDriver.ENABLE_EVENTS, InputDriver.EV_KEY);
            }
            if (4098 == source) {
                invalid_source = false;
                addToMap(InputDriver.ENABLE_EVENTS, InputDriver.EV_ABS);
                addToMap(InputDriver.ENABLE_EVENTS, InputDriver.EV_KEY);
                addToMap(InputDriver.ENABLE_KEY, InputDriver.BTN_TOUCH);
                addToMap(InputDriver.ENABLE_PROP, InputDriver.PROP_DIRECT);
            }
            if (1048584 == source) {
                invalid_source = false;
                addToMap(InputDriver.ENABLE_EVENTS, InputDriver.EV_ABS);
                addToMap(InputDriver.ENABLE_EVENTS, InputDriver.EV_KEY);
                addToMap(InputDriver.ENABLE_KEY, InputDriver.BTN_TOUCH);
                addToMap(InputDriver.ENABLE_PROP, InputDriver.PROP_POINTER);
            }
            if (invalid_source) {
                throw new IllegalArgumentException("Invalid source to construct driver.");
            }
        }

        private void addToMap(int key, int value) {
            Set<Integer> set = (Set) this.mSupportedEvents.get(Integer.valueOf(key));
            if (set == null) {
                set = new HashSet();
                this.mSupportedEvents.put(Integer.valueOf(key), set);
            }
            set.add(Integer.valueOf(value));
        }

        public Builder setKeys(int[] keys) {
            if (!InputDriver.checkValidCall(InputDriver.EV_KEY, this.mSupportedEvents) || InputDriver.checkValidCall(InputDriver.EV_ABS, this.mSupportedEvents)) {
                throw new IllegalArgumentException("Driver does not support keys. Please set SOURCE_CLASS_BUTTON");
            }
            Set<Integer> keySet = (Set) this.mSupportedEvents.get(Integer.valueOf(InputDriver.ENABLE_KEY));
            if (keySet == null) {
                keySet = new HashSet();
                this.mSupportedEvents.put(Integer.valueOf(InputDriver.ENABLE_KEY), keySet);
            }
            for (int key : keys) {
                keySet.add(Integer.valueOf(InputDriver.convertToKernelCode(key)));
            }
            return this;
        }

        public Builder setName(String name) {
            this.mName = name;
            return this;
        }

        public Builder setBusType(int busType) {
            this.mBusType = busType;
            return this;
        }

        public Builder setVendorId(int vendorId) {
            this.mVendorId = vendorId;
            return this;
        }

        public Builder setProductId(int productId) {
            this.mProductId = productId;
            return this;
        }

        public Builder setVersion(int version) {
            this.mVersion = version;
            return this;
        }

        public Builder setFfEffectsMax(int ffEffectsMax) {
            this.mFfEffectsMax = ffEffectsMax;
            return this;
        }

        private int convertToKernelAxis(int axis) {
            if (axis == 0) {
                return InputDriver.AXIS_X;
            }
            if (axis == 1) {
                return InputDriver.AXIS_Y;
            }
            throw new IllegalArgumentException("Only x & y axis events are supported.");
        }

        public Builder setAbsMax(int axis, int max) {
            if (InputDriver.checkValidCall(InputDriver.EV_ABS, this.mSupportedEvents)) {
                axis = convertToKernelAxis(axis);
                addToMap(InputDriver.ENABLE_ABS, axis);
                if (this.mAbsMax == null) {
                    this.mAbsMax = new ArrayList();
                }
                this.mAbsMax.add(Integer.valueOf(axis));
                this.mAbsMax.add(Integer.valueOf(max));
                return this;
            }
            throw new IllegalArgumentException("Driver does not support motion events. Please set appropriate source.");
        }

        public Builder setAbsMin(int axis, int min) {
            if (InputDriver.checkValidCall(InputDriver.EV_ABS, this.mSupportedEvents)) {
                axis = convertToKernelAxis(axis);
                addToMap(InputDriver.ENABLE_ABS, axis);
                if (this.mAbsMin == null) {
                    this.mAbsMin = new ArrayList();
                }
                this.mAbsMin.add(Integer.valueOf(axis));
                this.mAbsMin.add(Integer.valueOf(min));
                return this;
            }
            throw new IllegalArgumentException("Driver does not support motion events. Please set appropriate source.");
        }

        public Builder setAbsFuzz(int axis, int fuzz) {
            if (InputDriver.checkValidCall(InputDriver.EV_ABS, this.mSupportedEvents) || (InputDriver.checkValidCall(InputDriver.EV_REL, this.mSupportedEvents) ^ 1) == 0) {
                axis = convertToKernelAxis(axis);
                addToMap(InputDriver.ENABLE_ABS, axis);
                if (this.mAbsFuzz == null) {
                    this.mAbsFuzz = new ArrayList();
                }
                this.mAbsFuzz.add(Integer.valueOf(axis));
                this.mAbsFuzz.add(Integer.valueOf(fuzz));
                return this;
            }
            throw new IllegalArgumentException("Driver does not support motion events. Please set appropriate source.");
        }

        public Builder setAbsFlat(int axis, int flat) {
            if (InputDriver.checkValidCall(InputDriver.EV_ABS, this.mSupportedEvents) || (InputDriver.checkValidCall(InputDriver.EV_REL, this.mSupportedEvents) ^ 1) == 0) {
                axis = convertToKernelAxis(axis);
                addToMap(InputDriver.ENABLE_ABS, axis);
                if (this.mAbsFlat == null) {
                    this.mAbsFlat = new ArrayList();
                }
                this.mAbsFlat.add(Integer.valueOf(axis));
                this.mAbsFlat.add(Integer.valueOf(flat));
                return this;
            }
            throw new IllegalArgumentException("Driver does not support motion events. Please set appropriate source.");
        }

        private static int[] convertToArray(ArrayList<Integer> list) {
            if (list == null) {
                return new int[0];
            }
            int[] ret = new int[list.size()];
            int index = 0;
            for (Integer elem : list) {
                ret[index] = elem.intValue();
                index++;
            }
            return ret;
        }

        public InputDriver build() {
            if (InputDriver.checkValidCall(InputDriver.EV_KEY, this.mSupportedEvents) && (this.mSupportedEvents.containsKey(Integer.valueOf(InputDriver.ENABLE_KEY)) ^ 1) != 0) {
                throw new IllegalArgumentException("Please pass list of keys that driver supports");
            } else if (!InputDriver.checkValidCall(InputDriver.EV_ABS, this.mSupportedEvents) || (this.mSupportedEvents.containsKey(Integer.valueOf(InputDriver.ENABLE_ABS)) && this.mAbsMax != null)) {
                return new InputDriver(this.mName, this.mSupportedEvents, this.mBusType, this.mVendorId, this.mProductId, this.mVersion, this.mFfEffectsMax, convertToArray(this.mAbsMax), convertToArray(this.mAbsMin), convertToArray(this.mAbsFuzz), convertToArray(this.mAbsFlat));
            } else {
                throw new IllegalArgumentException("Driver not configured correctly. Please call absMax.");
            }
        }
    }

    static native int convertToKernelCode(int i);

    private static native void initConstants();

    static {
        System.loadLibrary("androidthings_jni");
        initConstants();
    }

    private InputDriver(String name, Map<Integer, Set<Integer>> supportedEvents, int busType, int vendorId, int productId, int version, int ffEffectsMax, int[] absMax, int[] absMin, int[] absFuzz, int[] absFlat) {
        this.mLifeline = new Binder();
        this.mName = name;
        this.mSupportedEvents = supportedEvents;
        this.mBusType = busType;
        this.mVendorId = vendorId;
        this.mProductId = productId;
        this.mVersion = version;
        this.mFfEffectsMax = ffEffectsMax;
        this.mAbsMax = absMax;
        this.mAbsMin = absMin;
        this.mAbsFuzz = absFuzz;
        this.mAbsFlat = absFlat;
        this.mLock = new Object();
        this.mAbsMaxMap = convertPackedArrayToMap(this.mAbsMax);
        this.mAbsMinMap = convertPackedArrayToMap(this.mAbsMin);
    }

    private static Map<Integer, Integer> convertPackedArrayToMap(int[] array) {
        Map<Integer, Integer> map = new HashMap();
        for (int i = 0; i < array.length; i += 2) {
            map.put(Integer.valueOf(array[i]), Integer.valueOf(array[i + 1]));
        }
        return map;
    }

    public boolean emit(KeyEvent[] events) {
        if (checkValidCall(EV_KEY, this.mSupportedEvents)) {
            ArrayList<InputDriverEvent> inputEvents = new ArrayList();
            int i = 0;
            while (i < events.length) {
                InputDriverEvent inputEvent = new InputDriverEvent(events[i]);
                if (((Set) this.mSupportedEvents.get(Integer.valueOf(ENABLE_KEY))).contains(Integer.valueOf(inputEvent.code()))) {
                    inputEvents.add(inputEvent);
                    i++;
                } else {
                    Log.w(TAG, "Dropping all events because key code " + events[i].getKeyCode() + " is not supported by this driver.");
                    return false;
                }
            }
            if (inputEvents.size() == 0) {
                Log.w(TAG, "No events to emit.");
                return false;
            }
            inputEvents.add(InputDriverEvent.createSyncEvent());
            synchronized (this.mLock) {
                if (this.mService == null) {
                    Log.w(TAG, "Dropping event. Please register driver before calling emit.");
                    return false;
                }
                try {
                    this.mService.emit(this.mHandle, (InputDriverEvent[]) inputEvents.toArray(new InputDriverEvent[0]));
                    return true;
                } catch (RemoteException e) {
                    Log.w(TAG, "Input driver service could not emit events.");
                    return false;
                }
            }
        }
        Log.w(TAG, "Dropping event. Invalid driver type.");
        return false;
    }

    private static Integer getValueOrZero(int key, Map<Integer, Integer> map) {
        Integer value = (Integer) map.get(Integer.valueOf(key));
        if (value == null) {
            return Integer.valueOf(0);
        }
        return value;
    }

    private boolean isAxisValueValid(int axis, int value) {
        Integer lowerBound = getValueOrZero(axis, this.mAbsMinMap);
        if (value > getValueOrZero(axis, this.mAbsMaxMap).intValue() || value < lowerBound.intValue()) {
            return false;
        }
        return true;
    }

    public boolean emit(int x, int y, boolean press) {
        if (!checkValidCall(EV_KEY, this.mSupportedEvents) || (checkValidCall(EV_ABS, this.mSupportedEvents) ^ 1) != 0) {
            Log.w(TAG, "Dropping event. Invalid driver type.");
            return false;
        } else if (isAxisValueValid(AXIS_X, x) && (isAxisValueValid(AXIS_Y, y) ^ 1) == 0) {
            int i;
            InputDriverEvent[] inputEvents = new InputDriverEvent[4];
            inputEvents[0] = new InputDriverEvent(EV_ABS, AXIS_X, x);
            inputEvents[1] = new InputDriverEvent(EV_ABS, AXIS_Y, y);
            int i2 = EV_KEY;
            int i3 = BTN_TOUCH;
            if (press) {
                i = 1;
            } else {
                i = 0;
            }
            inputEvents[2] = new InputDriverEvent(i2, i3, i);
            inputEvents[3] = new InputDriverEvent(EV_SYN, 0, 0);
            synchronized (this.mLock) {
                if (this.mService == null) {
                    Log.w(TAG, "Dropping event. Please register driver before calling emit.");
                    return false;
                }
                try {
                    this.mService.emit(this.mHandle, inputEvents);
                    return true;
                } catch (RemoteException e) {
                    Log.w(TAG, "Input driver service could not emit events.");
                    return false;
                }
            }
        } else {
            Log.w(TAG, "Dropping event. Coordinates exceed the configured size.");
            return false;
        }
    }

    private static boolean checkValidCall(int eventType, Map<Integer, Set<Integer>> supportedEvents) {
        if (supportedEvents.containsKey(Integer.valueOf(ENABLE_EVENTS))) {
            return ((Set) supportedEvents.get(Integer.valueOf(ENABLE_EVENTS))).contains(Integer.valueOf(eventType));
        }
        return false;
    }

    void uninitialize() {
        synchronized (this.mLock) {
            try {
                this.mService.destroyInputDevice(this.mHandle);
                this.mLifeline = null;
                this.mService = null;
            } catch (RemoteException e) {
                throw e.rethrowAsRuntimeException();
            }
        }
    }

    void initialize(IInputDriverService service) {
        synchronized (this.mLock) {
            this.mService = service;
            try {
                this.mHandle = this.mService.createInputDevice(this.mLifeline, this.mName, InputDriverConfig.createFromMap(this.mSupportedEvents), this.mBusType, this.mVendorId, this.mProductId, this.mVersion, this.mFfEffectsMax, this.mAbsMax, this.mAbsMin, this.mAbsFuzz, this.mAbsFlat);
                if (this.mHandle < 0) {
                    throw new IllegalStateException("Could not initialize driver.");
                }
            } catch (RemoteException e) {
                throw e.rethrowAsRuntimeException();
            }
        }
    }

    private static int placeholder() {
        return 0;
    }
}
