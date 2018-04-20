package com.google.android.things.device;

import android.os.PersistableBundle;
import com.android.internal.util.Preconditions;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

public final class BootParameters {
    private static final int DEFAULT_FLOAT_VALUE = Math.round(-1000.0f);
    private static final float FLOAT_SCALE_FACTOR = 1000.0f;
    private final float mBrightness;
    private final Map<String, String> mParameters;
    private final float mVolume;

    public static final class Builder {
        private float mBrightness = -1.0f;
        private final Map<String, String> mProperties = new LinkedHashMap();
        private float mVolume = -1.0f;

        public BootParameters build() {
            return new BootParameters(this.mBrightness, this.mVolume, this.mProperties);
        }

        public Builder setBrightness(float brightness) {
            Preconditions.checkArgumentInRange(brightness, 0.0f, 1.0f, "brightness");
            this.mBrightness = brightness;
            return this;
        }

        public Builder setVolume(float volume) {
            Preconditions.checkArgumentInRange(volume, 0.0f, 1.0f, "volume");
            this.mVolume = volume;
            return this;
        }

        public Builder setParameter(String name, String value) {
            Preconditions.checkNotNull(name);
            Preconditions.checkNotNull(value);
            this.mProperties.put(name, value);
            return this;
        }
    }

    BootParameters() {
        this.mParameters = new LinkedHashMap();
        this.mBrightness = ((float) DEFAULT_FLOAT_VALUE) / FLOAT_SCALE_FACTOR;
        this.mVolume = ((float) DEFAULT_FLOAT_VALUE) / FLOAT_SCALE_FACTOR;
    }

    BootParameters(float brightness, float volume, Map<String, String> parameters) {
        this.mBrightness = brightness;
        this.mVolume = volume;
        this.mParameters = parameters;
    }

    BootParameters(PersistableBundle parameters) {
        this.mParameters = new LinkedHashMap();
        if (parameters == null) {
            this.mBrightness = ((float) DEFAULT_FLOAT_VALUE) / FLOAT_SCALE_FACTOR;
            this.mVolume = ((float) DEFAULT_FLOAT_VALUE) / FLOAT_SCALE_FACTOR;
            return;
        }
        this.mBrightness = ((float) parameters.getInt("brightness", DEFAULT_FLOAT_VALUE)) / FLOAT_SCALE_FACTOR;
        this.mVolume = ((float) parameters.getInt("volume", DEFAULT_FLOAT_VALUE)) / FLOAT_SCALE_FACTOR;
        String[] names = parameters.getStringArray("param_names");
        String[] values = parameters.getStringArray("param_values");
        if (names == null || values == null) {
            names = new String[0];
            values = new String[0];
        }
        if (!(names == null || values == null || names.length != values.length)) {
            for (int i = 0; i < names.length; i++) {
                this.mParameters.put(names[i], values[i]);
            }
        }
    }

    PersistableBundle toPersistableBundle() {
        String[] names = new String[this.mParameters.size()];
        String[] values = new String[names.length];
        int i = 0;
        for (Entry<String, String> entry : this.mParameters.entrySet()) {
            names[i] = (String) entry.getKey();
            values[i] = (String) entry.getValue();
            i++;
        }
        int brightness = Math.round(this.mBrightness * FLOAT_SCALE_FACTOR);
        int volume = Math.round(this.mVolume * FLOAT_SCALE_FACTOR);
        PersistableBundle bundle = new PersistableBundle(4);
        bundle.putInt("brightness", brightness);
        bundle.putInt("volume", volume);
        bundle.putStringArray("param_names", names);
        bundle.putStringArray("param_values", values);
        return bundle;
    }

    public final float getBrightness() {
        return this.mBrightness;
    }

    public final float getVolume() {
        return this.mVolume;
    }

    public final Map<String, String> getParameters() {
        return new LinkedHashMap(this.mParameters);
    }

    public final String getParameter(String name) {
        return (String) this.mParameters.get(name);
    }
}
