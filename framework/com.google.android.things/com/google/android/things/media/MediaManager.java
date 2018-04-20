package com.google.android.things.media;

import android.content.ComponentName;
import android.content.Context;
import android.media.session.MediaController;
import android.media.session.MediaSession.Token;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.util.Log;
import com.google.android.things.media.IMediaSessionManager.Stub;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class MediaManager {
    private static final String MEDIA_PERMISSION = "com.google.android.things.permission.MEDIA_CONTENT_CONTROL";
    private static final String MEDIA_SERVICE = "mediacontrolservice";
    private static final String TAG = MediaManager.class.getSimpleName();
    private final Context mContext;
    private final IMediaSessionManager mMediaManager = Stub.asInterface(ServiceManager.getService(MEDIA_SERVICE));

    private class CallbackInternal extends IMediaSessionChangeListener.Stub {
        private final SessionCallback mCallback;

        private CallbackInternal(SessionCallback callback) {
            Objects.requireNonNull(callback);
            this.mCallback = callback;
        }

        public void onActiveSessionsChanged(List<Token> sessions) {
            List<MediaController> controllers = new ArrayList();
            for (Token tok : sessions) {
                controllers.add(new MediaController(MediaManager.this.mContext, tok));
            }
            this.mCallback.onActiveSessionsChanged(controllers);
        }
    }

    public static abstract class SessionCallback {
        public void onActiveSessionsChanged(List<MediaController> list) {
        }
    }

    public MediaManager(Context context) {
        this.mContext = context;
        if (this.mMediaManager == null) {
            Log.e(TAG, "Unable to fetch service interface.");
            throw new IllegalStateException("Unable to fetch mediacontrolservice");
        }
    }

    public void addOnActiveSessionsChangedListener(SessionCallback listener) {
        if (listener == null) {
            throw new IllegalArgumentException("Callback must be non-null.");
        }
        try {
            this.mMediaManager.addOnActiveSessionsChangedListener(new CallbackInternal(listener));
        } catch (RemoteException e) {
            e.rethrowAsRuntimeException();
        }
    }

    public void removeOnActiveSessionsChangedListener(SessionCallback listener) {
        if (listener == null) {
            throw new IllegalArgumentException("Callback must be non-null.");
        }
        try {
            this.mMediaManager.removeOnActiveSessionsChangedListener(new CallbackInternal(listener));
        } catch (RemoteException e) {
            e.rethrowAsRuntimeException();
        }
    }

    public List<MediaController> getActiveSessions(ComponentName notificationListener) {
        List<MediaController> ret = new ArrayList();
        try {
            for (Token tok : this.mMediaManager.getActiveSessions(notificationListener)) {
                ret.add(new MediaController(this.mContext, tok));
            }
        } catch (RemoteException e) {
            e.rethrowAsRuntimeException();
        }
        return ret;
    }
}
