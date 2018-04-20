package com.google.android.things.audio;

import android.media.audiofx.AudioEffect;
import java.util.UUID;

public class UserEffect extends AudioEffect {
    public static final int ATTACHMENT_TYPE_AUX = 1;
    public static final int ATTACHMENT_TYPE_MUSIC_STREAM = 8;
    public static final int ATTACHMENT_TYPE_PLAYBACK_SESSION = 2;
    public static final int ATTACHMENT_TYPE_RECORDING_SESSION = 4;
    static final UUID TYPE_CUSTOM_AUX = UUID.fromString("fb0aa186-a3d6-11e7-abc4-cec278b6b50a");
    static final UUID TYPE_CUSTOM_INSERT = UUID.fromString("fb0aa514-a3d6-11e7-abc4-cec278b6b50a");
    static final UUID TYPE_CUSTOM_MUSIC = UUID.fromString("7cb2d6d2-b45c-11e7-abc4-cec278b6b50a");
    static final UUID TYPE_CUSTOM_PRE_PROCESSING = UUID.fromString("fb0aa6a4-a3d6-11e7-abc4-cec278b6b50a");

    private static UUID UuidFromType(int type) throws IllegalArgumentException {
        switch (type) {
            case 2:
                return TYPE_CUSTOM_INSERT;
            case 4:
                return TYPE_CUSTOM_PRE_PROCESSING;
            default:
                throw new IllegalArgumentException("Unrecognized effect type.");
        }
    }

    UserEffect(int audioSession, int attachmentType) throws IllegalArgumentException, UnsupportedOperationException, RuntimeException {
        super(UuidFromType(attachmentType), EFFECT_TYPE_NULL, 0, audioSession);
    }

    public static UserEffect createRecordingSessionEffect(int audioSession) throws IllegalArgumentException, UnsupportedOperationException, RuntimeException {
        return new UserEffect(audioSession, 4);
    }

    public static UserEffect createPlaybackSessionEffect(int audioSession) throws IllegalArgumentException, UnsupportedOperationException, RuntimeException {
        return new UserEffect(audioSession, 2);
    }
}
