package com.google.android.things;

public final class AndroidThings {
    private static final int VERSION_MAJOR = intPlaceholder();
    private static final int VERSION_MINOR = intPlaceholder();
    private static final int VERSION_REVISION = intPlaceholder();
    private static final String VERSION_STRING = stringPlaceholder();
    private static final String VERSION_TAG = stringPlaceholder();

    private static native void initAndroidThingsConstants();

    static {
        System.loadLibrary("androidthings_jni");
        initAndroidThingsConstants();
    }

    private static int intPlaceholder() {
        return 0;
    }

    private static String stringPlaceholder() {
        return null;
    }

    private AndroidThings() {
    }

    public static int getVersionMajor() {
        return VERSION_MAJOR;
    }

    public static int getVersionMinor() {
        return VERSION_MINOR;
    }

    public static int getVersionRevision() {
        return VERSION_REVISION;
    }

    public static String getVersionTag() {
        return VERSION_TAG;
    }

    public static String getVersionString() {
        return VERSION_STRING;
    }
}
