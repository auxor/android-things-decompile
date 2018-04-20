package com.google.android.things.userdriver;

import com.google.android.things.bluetooth.BluetoothProfile;
import com.google.android.things.device.TimeManager;
import com.google.android.things.lowpan.LowpanChannelInfo;
import java.util.UUID;

public class UserSensor {
    private final UserSensorDriver mDriver;
    private final int mMaxDelay;
    private final float mMaxRange;
    private final int mMinDelay;
    private final String mName;
    private final float mPower;
    private final int mReportingMode;
    private final String mRequiredPermission;
    private final float mResolution;
    private final String mStringType;
    private final int mType;
    private final UUID mUuid;
    private final String mVendor;
    private final int mVersion;

    public static class Builder {
        private UserSensorDriver mDriver;
        private int mMaxDelay = LowpanChannelInfo.UNKNOWN_POWER;
        private float mMaxRange = 0.0f;
        private int mMinDelay = 0;
        private String mName = "Unnamed Sensor";
        private float mPower = 0.0f;
        private int mReportingMode;
        private String mRequiredPermission = "";
        private float mResolution = 0.0f;
        private String mStringType;
        private Integer mType;
        private UUID mUuid;
        private String mVendor = "Unknown Vendor";
        private int mVersion = 0;

        public UserSensor build() {
            if (this.mType == null) {
                throw new IllegalStateException("Sensor type was never assigned.");
            } else if (this.mMinDelay > this.mMaxDelay) {
                throw new IllegalStateException("Min delay is greater than max delay.");
            } else {
                if (this.mUuid == null) {
                    this.mUuid = new UUID(0, 0);
                }
                if (this.mDriver != null) {
                    return new UserSensor(this.mName, this.mVendor, this.mVersion, this.mType.intValue(), this.mStringType, this.mReportingMode, this.mMaxRange, this.mResolution, this.mPower, this.mMinDelay, this.mMaxDelay, this.mRequiredPermission, this.mUuid, this.mDriver);
                }
                throw new IllegalStateException("Sensor driver was never assigned.");
            }
        }

        public Builder setName(String name) {
            this.mName = name;
            return this;
        }

        public Builder setVendor(String vendor) {
            this.mVendor = vendor;
            return this;
        }

        public Builder setVersion(int version) {
            this.mVersion = version;
            return this;
        }

        public Builder setType(int type) {
            switch (type) {
                case 1:
                    this.mStringType = "android.sensor.accelerometer";
                    this.mReportingMode = 0;
                    break;
                case 2:
                    this.mStringType = "android.sensor.magnetic_field";
                    this.mReportingMode = 0;
                    break;
                case 3:
                    throw new IllegalArgumentException("TYPE_ORIENTATION is deprecated; use TYPE_ROTATION_VECTOR instead");
                case 4:
                    this.mStringType = "android.sensor.gyroscope";
                    this.mReportingMode = 0;
                    break;
                case 5:
                    this.mStringType = "android.sensor.light";
                    this.mReportingMode = 1;
                    break;
                case 6:
                    this.mStringType = "android.sensor.pressure";
                    this.mReportingMode = 0;
                    break;
                case 7:
                    throw new IllegalArgumentException("TYPE_TEMPERATURE is deprecated; use TYPE_AMBIENT_TEMPERATURE instead");
                case 8:
                    this.mStringType = "android.sensor.proximity";
                    this.mReportingMode = 1;
                    break;
                case 9:
                    this.mStringType = "android.sensor.gravity";
                    this.mReportingMode = 0;
                    break;
                case 10:
                    this.mStringType = "android.sensor.linear_acceleration";
                    this.mReportingMode = 0;
                    break;
                case BluetoothProfile.A2DP_SINK /*11*/:
                    this.mStringType = "android.sensor.rotation_vector";
                    this.mReportingMode = 0;
                    break;
                case 12:
                    this.mStringType = "android.sensor.relative_humidity";
                    this.mReportingMode = 1;
                    break;
                case 13:
                    this.mStringType = "android.sensor.ambient_temperature";
                    this.mReportingMode = 1;
                    break;
                case 14:
                    this.mStringType = "android.sensor.magnetic_field_uncalibrated";
                    this.mReportingMode = 0;
                    break;
                case 15:
                    this.mStringType = "android.sensor.game_rotation_vector";
                    this.mReportingMode = 0;
                    break;
                case 16:
                    this.mStringType = "android.sensor.gyroscope_uncalibrated";
                    this.mReportingMode = 0;
                    break;
                case BluetoothProfile.PBAP_CLIENT /*17*/:
                    this.mStringType = "android.sensor.significant_motion";
                    this.mReportingMode = 2;
                    break;
                case 18:
                    this.mStringType = "android.sensor.step_detector";
                    this.mReportingMode = 3;
                    break;
                case 19:
                    this.mStringType = "android.sensor.step_counter";
                    this.mReportingMode = 1;
                    break;
                case 20:
                    this.mStringType = "android.sensor.geomagnetic_rotation_vector";
                    this.mReportingMode = 0;
                    break;
                case 21:
                    this.mStringType = "android.sensor.heart_rate";
                    this.mReportingMode = 1;
                    break;
                case 22:
                    this.mStringType = "android.sensor.tilt_detector";
                    this.mReportingMode = 3;
                    break;
                case 23:
                    this.mStringType = "android.sensor.wake_gesture";
                    this.mReportingMode = 2;
                    break;
                case TimeManager.FORMAT_24 /*24*/:
                    this.mStringType = "android.sensor.glance_gesture";
                    this.mReportingMode = 2;
                    break;
                case 25:
                    this.mStringType = "android.sensor.pick_up_gesture";
                    this.mReportingMode = 2;
                    break;
                case 27:
                    this.mStringType = "android.sensor.device_orientation";
                    this.mReportingMode = 0;
                    break;
                case 28:
                    this.mStringType = "android.sensor.pose_6dof";
                    this.mReportingMode = 0;
                    break;
                case 29:
                    this.mStringType = "android.sensor.stationary_detect";
                    this.mReportingMode = 2;
                    break;
                case 30:
                    this.mStringType = "android.sensor.motion_detect";
                    this.mReportingMode = 2;
                    break;
                case 31:
                    this.mStringType = "android.sensor.heart_beat";
                    this.mReportingMode = 3;
                    break;
                default:
                    throw new IllegalArgumentException("Unknown sensor type " + type);
            }
            this.mType = Integer.valueOf(type);
            return this;
        }

        public Builder setCustomType(int type, String stringType, int reportingMode) {
            if (type < 65536) {
                throw new IllegalArgumentException("Custom sensor type value must be >= TYPE_DEVICE_PRIVATE_BASE");
            } else if (stringType == null) {
                throw new IllegalArgumentException("Sensor string type may not be null.");
            } else {
                this.mType = Integer.valueOf(type);
                this.mStringType = stringType;
                this.mReportingMode = reportingMode;
                return this;
            }
        }

        public Builder setMaxRange(float maxRange) {
            this.mMaxRange = maxRange;
            return this;
        }

        public Builder setResolution(float resolution) {
            this.mResolution = resolution;
            return this;
        }

        public Builder setPower(float power) {
            this.mPower = power;
            return this;
        }

        public Builder setMinDelay(int minDelay) {
            this.mMinDelay = minDelay;
            return this;
        }

        public Builder setMaxDelay(int maxDelay) {
            this.mMaxDelay = maxDelay;
            return this;
        }

        public Builder setRequiredPermission(String requiredPermission) {
            this.mRequiredPermission = requiredPermission;
            return this;
        }

        public Builder setUuid(UUID uuid) {
            this.mUuid = uuid;
            return this;
        }

        public Builder setDriver(UserSensorDriver driver) {
            this.mDriver = driver;
            return this;
        }
    }

    UserSensor(String name, String vendor, int version, int type, String stringType, int reportingMode, float maxRange, float resolution, float power, int minDelay, int maxDelay, String requiredPermission, UUID uuid, UserSensorDriver driver) {
        this.mName = name;
        this.mMaxDelay = maxDelay;
        this.mMinDelay = minDelay;
        this.mType = type;
        this.mVersion = version;
        this.mMaxRange = maxRange;
        this.mPower = power;
        this.mResolution = resolution;
        this.mRequiredPermission = requiredPermission;
        this.mStringType = stringType;
        this.mVendor = vendor;
        this.mReportingMode = reportingMode;
        this.mUuid = uuid;
        this.mDriver = driver;
    }

    public final int getReportingMode() {
        return this.mReportingMode;
    }

    public final int getMaxDelay() {
        return this.mMaxDelay;
    }

    public final int getMinDelay() {
        return this.mMinDelay;
    }

    public final int getType() {
        return this.mType;
    }

    public final int getVersion() {
        return this.mVersion;
    }

    public final float getMaxRange() {
        return this.mMaxRange;
    }

    public final float getPower() {
        return this.mPower;
    }

    public final float getResolution() {
        return this.mResolution;
    }

    public final String getName() {
        return this.mName;
    }

    public final String getRequiredPermission() {
        return this.mRequiredPermission;
    }

    public final String getStringType() {
        return this.mStringType;
    }

    public final String getVendor() {
        return this.mVendor;
    }

    public final UUID getUuid() {
        return this.mUuid;
    }

    public final UserSensorDriver getDriver() {
        return this.mDriver;
    }
}
