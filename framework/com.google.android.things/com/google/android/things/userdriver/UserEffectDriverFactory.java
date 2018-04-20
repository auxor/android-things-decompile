package com.google.android.things.userdriver;

public abstract class UserEffectDriverFactory {
    public abstract UserEffectDriver createUserEffectDriver();

    public void onError() {
    }
}
