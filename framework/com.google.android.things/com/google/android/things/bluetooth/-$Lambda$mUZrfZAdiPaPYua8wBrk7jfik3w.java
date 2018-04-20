package com.google.android.things.bluetooth;

final /* synthetic */ class -$Lambda$mUZrfZAdiPaPYua8wBrk7jfik3w implements BinderFunc {
    private final /* synthetic */ byte $id;
    private final /* synthetic */ Object -$f0;

    private final /* synthetic */ void $m$0() {
        ((ConsentPairingController) this.-$f0).lambda$-com_google_android_things_bluetooth_ConsentPairingController_1508();
    }

    private final /* synthetic */ void $m$1() {
        ((ConsentPairingController) this.-$f0).lambda$-com_google_android_things_bluetooth_ConsentPairingController_2010();
    }

    private final /* synthetic */ void $m$2() {
        ((DisplayPairingController) this.-$f0).lambda$-com_google_android_things_bluetooth_DisplayPairingController_2213();
    }

    private final /* synthetic */ void $m$3() {
        ((PasskeyConfirmationPairingController) this.-$f0).lambda$-com_google_android_things_bluetooth_PasskeyConfirmationPairingController_1919();
    }

    private final /* synthetic */ void $m$4() {
        ((PasskeyConfirmationPairingController) this.-$f0).lambda$-com_google_android_things_bluetooth_PasskeyConfirmationPairingController_2266();
    }

    private final /* synthetic */ void $m$5() {
        ((PinPairingController) this.-$f0).lambda$-com_google_android_things_bluetooth_PinPairingController_2043();
    }

    public /* synthetic */ -$Lambda$mUZrfZAdiPaPYua8wBrk7jfik3w(byte b, Object obj) {
        this.$id = b;
        this.-$f0 = obj;
    }

    public final void run() {
        switch (this.$id) {
            case (byte) 0:
                $m$0();
                return;
            case (byte) 1:
                $m$1();
                return;
            case (byte) 2:
                $m$2();
                return;
            case (byte) 3:
                $m$3();
                return;
            case (byte) 4:
                $m$4();
                return;
            case (byte) 5:
                $m$5();
                return;
            default:
                throw new AssertionError();
        }
    }
}
