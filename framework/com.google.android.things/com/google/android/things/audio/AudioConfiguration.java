package com.google.android.things.audio;

import android.media.AudioFormat;
import android.media.AudioFormat.Builder;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class AudioConfiguration implements Parcelable {
    public static final Creator<AudioConfiguration> CREATOR = new Creator<AudioConfiguration>() {
        public AudioConfiguration createFromParcel(Parcel in) {
            return new AudioConfiguration(in.readInt(), in.readInt(), in.readInt());
        }

        public AudioConfiguration[] newArray(int size) {
            return new AudioConfiguration[size];
        }
    };
    public static final int ENCODING_INVALID = 0;
    public static final int ENCODING_PCM_16BIT_SIGNED = 2;
    public static final int ENCODING_PCM_24BIT_SIGNED_PACKED = 20;
    public static final int ENCODING_PCM_24BIT_SIGNED_UNPACKED_LE = 21;
    public static final int ENCODING_PCM_32BIT_FLOAT = 4;
    public static final int ENCODING_PCM_32BIT_SIGNED = 22;
    public static final int ENCODING_PCM_8BIT_UNSIGNED = 3;
    private final int mChannelMask;
    private final int mEncoding;
    private final int mSampleRate;

    @Retention(RetentionPolicy.SOURCE)
    public @interface Encoding {
    }

    private static boolean isEncodingValid(int encoding) {
        switch (encoding) {
            case 2:
            case 3:
            case 4:
            case 20:
            case 21:
            case 22:
                return true;
            default:
                return false;
        }
    }

    public AudioConfiguration(int sampleRate, int channelMask, int encoding) {
        if (!isEncodingValid(encoding)) {
            throw new IllegalArgumentException("Cannot create an AudioConfiguration with an invalid encoding.");
        } else if (sampleRate <= 0) {
            throw new IllegalArgumentException("Cannot create an AudioConfiguration with an invalid sample rate.");
        } else if (channelMask <= 0) {
            throw new IllegalArgumentException("Cannot create an AudioConfiguration with no channels.");
        } else {
            this.mSampleRate = sampleRate;
            this.mChannelMask = channelMask;
            this.mEncoding = encoding;
        }
    }

    public AudioConfiguration(AudioFormat format) {
        this(format.getSampleRate(), format.getChannelMask(), encodingFromAudioFormatEncoding(format.getEncoding()));
    }

    public AudioFormat asAudioFormat() {
        return new Builder().setEncoding(encodingToAudioFormatEncoding(this.mEncoding)).setChannelMask(this.mChannelMask).setSampleRate(this.mSampleRate).build();
    }

    public int getSampleRate() {
        return this.mSampleRate;
    }

    public int getChannelMask() {
        return this.mChannelMask;
    }

    public int getEncoding() {
        return this.mEncoding;
    }

    public static int encodingFromAudioFormatEncoding(int audioFormatEncoding) {
        switch (audioFormatEncoding) {
            case 2:
                return 2;
            case 3:
                return 3;
            case 4:
                return 4;
            default:
                return 0;
        }
    }

    public static int encodingToAudioFormatEncoding(int encoding) {
        switch (encoding) {
            case 2:
                return 2;
            case 3:
                return 3;
            case 4:
                return 4;
            default:
                return 0;
        }
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(this.mSampleRate);
        out.writeInt(this.mChannelMask);
        out.writeInt(this.mEncoding);
    }
}
