package com.google.android.things.userdriver;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.view.KeyEvent;

class InputDriverEvent implements Parcelable {
    public static final Creator<InputDriverEvent> CREATOR = new Creator<InputDriverEvent>() {
        public InputDriverEvent createFromParcel(Parcel in) {
            return new InputDriverEvent(in);
        }

        public InputDriverEvent[] newArray(int size) {
            return new InputDriverEvent[size];
        }
    };
    private int mEvCode;
    private int mEvType;
    private int mEvValue;

    public InputDriverEvent(int evType, int evCode, int evValue) {
        this.mEvType = evType;
        this.mEvCode = evCode;
        this.mEvValue = evValue;
    }

    InputDriverEvent(KeyEvent keyEvent) {
        this.mEvType = InputDriver.EV_KEY;
        this.mEvCode = InputDriver.convertToKernelCode(keyEvent.getKeyCode());
        int action = keyEvent.getAction();
        if (action == 0) {
            this.mEvValue = 1;
        } else if (action == 1) {
            this.mEvValue = 0;
        } else {
            throw new IllegalArgumentException("Unsupported key event code");
        }
    }

    static InputDriverEvent createSyncEvent() {
        return new InputDriverEvent(InputDriver.EV_SYN, 0, 0);
    }

    int code() {
        return this.mEvCode;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(this.mEvType);
        out.writeInt(this.mEvCode);
        out.writeInt(this.mEvValue);
    }

    private InputDriverEvent(Parcel in) {
        this.mEvType = in.readInt();
        this.mEvCode = in.readInt();
        this.mEvValue = in.readInt();
    }
}
