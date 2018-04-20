package com.google.android.things.lowpan;

import android.net.IpPrefix;
import android.net.LinkAddress;
import android.net.lowpan.LowpanChannelInfo;
import android.net.lowpan.LowpanCredential;
import android.net.lowpan.LowpanException;
import android.net.lowpan.LowpanIdentity;
import android.net.lowpan.LowpanProvision.Builder;
import android.net.lowpan.LowpanRuntimeException;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.RemoteException;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.HashMap;
import java.util.Objects;

public class LowpanInterface {
    public static final String NETWORK_TYPE_THREAD_V1 = "org.threadgroup.thread.v1";
    public static final int ROLE_COORDINATOR = 4;
    public static final int ROLE_DETACHED = 0;
    public static final int ROLE_END_DEVICE = 1;
    public static final int ROLE_LEADER = 3;
    public static final int ROLE_ROUTER = 2;
    public static final int ROLE_SLEEPY_END_DEVICE = 5;
    public static final int ROLE_SLEEPY_ROUTER = 6;
    public static final int STATE_ATTACHED = 3;
    public static final int STATE_ATTACHING = 2;
    public static final int STATE_COMMISSIONING = 1;
    public static final int STATE_DISABLED = 6;
    public static final int STATE_FAULT = 4;
    public static final int STATE_ISOLATED = 5;
    public static final int STATE_OFFLINE = 0;
    private static final String TAG = LowpanInterface.class.getSimpleName();
    private final android.net.lowpan.LowpanInterface mActualLowpanInterface;
    private final HashMap<Callback, Handler> mListenerHandlerMap = new HashMap();
    private final HashMap<Callback, CallbackInternal> mListenerMap = new HashMap();

    private class AsyncCallGlue extends AsyncTask<Void, Void, Exception> {
        private AsyncCallGlue() {
        }

        protected void run() throws LowpanException {
        }

        protected Exception doInBackground(Void... ignore) {
            try {
                run();
                return null;
            } catch (Exception x) {
                return new LowpanException(x);
            } catch (Exception x2) {
                return new LowpanRuntimeException(x2);
            } catch (Exception x3) {
                if (x3.getCause() instanceof RemoteException) {
                    return x3;
                }
                throw x3;
            }
        }

        protected void onPostExecute(Exception result) {
            if (result != null) {
                LowpanInterface.this.onProvisionException(result);
            }
        }
    }

    public static abstract class Callback {
        public void onRoleChanged(int value) {
        }

        public void onStateChanged(int state) {
        }

        public void onLowpanIdentityChanged(LowpanIdentity identity) {
        }

        public void onProvisionException(Exception exception) {
        }

        public void onLinkNetworkAdded(IpPrefix prefix) {
        }

        public void onLinkNetworkRemoved(IpPrefix prefix) {
        }

        public void onLinkAddressAdded(LinkAddress address) {
        }

        public void onLinkAddressRemoved(LinkAddress address) {
        }
    }

    private static class CallbackInternal extends android.net.lowpan.LowpanInterface.Callback {
        LowpanIdentity mLastIdentity = null;
        int mLastState = -1;
        final LowpanInterface mLowpanInterface;
        final Callback mUserCallbackInstance;

        CallbackInternal(LowpanInterface lowpanInterface, Callback userCallbackInstance) {
            this.mLowpanInterface = lowpanInterface;
            this.mUserCallbackInstance = userCallbackInstance;
        }

        private synchronized boolean refreshStateAndRole() {
            try {
                LowpanIdentity lowpanIdentity;
                int state = this.mLowpanInterface.getState();
                LowpanProvisioningParams params = this.mLowpanInterface.getLowpanProvisioningParams(false);
                if (params != null) {
                    lowpanIdentity = params.getLowpanIdentity();
                } else {
                    lowpanIdentity = null;
                }
                if (state != this.mLastState) {
                    this.mUserCallbackInstance.onStateChanged(state);
                    this.mLastState = state;
                }
                if (!Objects.equals(lowpanIdentity, this.mLastIdentity)) {
                    this.mUserCallbackInstance.onLowpanIdentityChanged(lowpanIdentity);
                    this.mLastIdentity = lowpanIdentity;
                }
            } catch (LowpanRuntimeException x) {
                x.printStackTrace();
                return false;
            }
            return true;
        }

        public void onStateChanged(String value) {
            if (!refreshStateAndRole()) {
                int state = LowpanInterface.convertStringToState(value);
                if (state != this.mLastState) {
                    this.mUserCallbackInstance.onStateChanged(state);
                    this.mLastState = state;
                }
            }
        }

        public void onLowpanIdentityChanged(LowpanIdentity value) {
            if (!refreshStateAndRole()) {
                LowpanIdentity identity = new LowpanIdentity(value);
                if (!Objects.equals(identity, this.mLastIdentity)) {
                    this.mUserCallbackInstance.onLowpanIdentityChanged(identity);
                    this.mLastIdentity = identity;
                }
            }
        }

        public void onLinkNetworkAdded(IpPrefix prefix) {
            this.mUserCallbackInstance.onLinkNetworkAdded(prefix);
        }

        public void onLinkNetworkRemoved(IpPrefix prefix) {
            this.mUserCallbackInstance.onLinkNetworkRemoved(prefix);
        }

        public void onLinkAddressAdded(LinkAddress address) {
            this.mUserCallbackInstance.onLinkAddressAdded(address);
        }

        public void onLinkAddressRemoved(LinkAddress address) {
            this.mUserCallbackInstance.onLinkAddressRemoved(address);
        }
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface Role {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface State {
    }

    static String convertRoleToString(int role) {
        switch (role) {
            case 0:
                return "detached";
            case 1:
                return "end-device";
            case 2:
                return "router";
            case 3:
                return "leader";
            case 4:
                return "coordinator";
            case 5:
                return "sleepy-end-device";
            case 6:
                return "sleepy-router";
            default:
                return null;
        }
    }

    static int convertStringToRole(String str) {
        if (str.equals("detached")) {
            return 0;
        }
        if (str.equals("end-device")) {
            return 1;
        }
        if (str.equals("router")) {
            return 2;
        }
        if (str.equals("leader")) {
            return 3;
        }
        if (str.equals("sleepy-end-device")) {
            return 5;
        }
        if (str.equals("sleepy-router")) {
            return 6;
        }
        if (str.equals("coordinator")) {
            return 4;
        }
        return 0;
    }

    static String convertStateToString(int state) {
        switch (state) {
            case 0:
                return "offline";
            case 1:
                return "commissioning";
            case 2:
                return "attaching";
            case 3:
                return "attached";
            case 4:
                return "fault";
            default:
                return null;
        }
    }

    static int convertStringToState(String str) {
        if (str.equals("offline")) {
            return 0;
        }
        if (str.equals("commissioning")) {
            return 1;
        }
        if (str.equals("attaching")) {
            return 2;
        }
        if (str.equals("attached")) {
            return 3;
        }
        if (str.equals("fault")) {
            return 4;
        }
        return 0;
    }

    LowpanInterface(android.net.lowpan.LowpanInterface ifaceActual) {
        this.mActualLowpanInterface = ifaceActual;
    }

    private void onProvisionException(Exception exception) {
        synchronized (this.mListenerMap) {
            HashMap<Callback, Handler> listenerHandlerMap = (HashMap) this.mListenerHandlerMap.clone();
        }
        for (Callback cb : listenerHandlerMap.keySet()) {
            ((Handler) listenerHandlerMap.get(cb)).post(new -$Lambda$BbY8jmNkP4LBgtyiBmEwmS1DCOw(cb, exception));
        }
    }

    public void form(final LowpanProvisioningParams params) throws LowpanException {
        if (params == null) {
            throw new NullPointerException("Provisioning params cannot be null");
        } else if (isEnabled()) {
            new AsyncCallGlue(this) {
                protected void run() throws LowpanException {
                    if (this.isProvisioned()) {
                        this.mActualLowpanInterface.leave();
                    }
                    this.mActualLowpanInterface.form(params.asActual());
                }
            }.execute(new Void[0]);
        } else {
            throw new InterfaceDisabledException();
        }
    }

    public void join(final LowpanProvisioningParams params) throws LowpanException {
        if (params == null) {
            throw new NullPointerException("Provisioning params cannot be null");
        } else if (params.getLowpanCredential() == null) {
            throw new IllegalArgumentException("Missing LowpanCredential");
        } else if (isEnabled()) {
            new AsyncCallGlue(this) {
                protected void run() throws LowpanException {
                    if (this.isProvisioned()) {
                        this.mActualLowpanInterface.leave();
                    }
                    this.mActualLowpanInterface.join(params.asActual());
                }
            }.execute(new Void[0]);
        } else {
            throw new InterfaceDisabledException();
        }
    }

    public void provision(final LowpanProvisioningParams params) throws LowpanException {
        if (params == null) {
            throw new NullPointerException("Provisioning params cannot be null");
        } else if (params.getLowpanCredential() == null) {
            throw new IllegalArgumentException("Missing LowpanCredential");
        } else if (isEnabled()) {
            new AsyncCallGlue(this) {
                protected void run() throws LowpanException {
                    if (this.isProvisioned()) {
                        this.mActualLowpanInterface.leave();
                    }
                    this.mActualLowpanInterface.attach(params.asActual());
                }
            }.execute(new Void[0]);
        } else {
            throw new InterfaceDisabledException();
        }
    }

    public void leave() throws LowpanException {
        if (isEnabled()) {
            new AsyncCallGlue(this) {
                protected void run() throws LowpanException {
                    this.mActualLowpanInterface.leave();
                }
            }.execute(new Void[0]);
            return;
        }
        throw new InterfaceDisabledException();
    }

    public LowpanCommissioningSession startCommissioningSession(LowpanBeaconInfo beaconInfo) throws LowpanException {
        try {
            return new LowpanCommissioningSession(this.mActualLowpanInterface.startCommissioningSession(beaconInfo.asActual()));
        } catch (LowpanException x) {
            throw LowpanException.createFromActual(x);
        } catch (LowpanRuntimeException x2) {
            throw LowpanRuntimeException.createFromActual(x2);
        }
    }

    public void reset() throws LowpanException {
        if (isEnabled()) {
            new AsyncTask<Void, Void, Void>() {
                protected Void doInBackground(Void... empty) {
                    try {
                        LowpanInterface.this.mActualLowpanInterface.reset();
                    } catch (LowpanException x) {
                        x.printStackTrace();
                    } catch (LowpanRuntimeException x2) {
                        x2.printStackTrace();
                    }
                    return null;
                }
            }.execute(new Void[0]);
            return;
        }
        throw new InterfaceDisabledException();
    }

    public String getName() {
        return this.mActualLowpanInterface.getName();
    }

    public boolean isEnabled() {
        return this.mActualLowpanInterface.isEnabled();
    }

    public void setEnabled(boolean enabled) throws LowpanException {
        try {
            this.mActualLowpanInterface.setEnabled(enabled);
        } catch (LowpanException x) {
            throw LowpanException.createFromActual(x);
        } catch (LowpanRuntimeException x2) {
            throw LowpanRuntimeException.createFromActual(x2);
        }
    }

    public boolean isUp() {
        return this.mActualLowpanInterface.isUp();
    }

    public boolean isConnected() {
        return this.mActualLowpanInterface.isConnected();
    }

    public boolean isProvisioned() {
        return this.mActualLowpanInterface.isCommissioned();
    }

    public int getState() {
        if (this.mActualLowpanInterface.isEnabled()) {
            return convertStringToState(this.mActualLowpanInterface.getState());
        }
        return 6;
    }

    public LowpanProvisioningParams getLowpanProvisioningParams(boolean includeCredential) {
        if (!isProvisioned()) {
            return null;
        }
        LowpanIdentity identity = this.mActualLowpanInterface.getLowpanIdentity();
        if (identity == null) {
            return null;
        }
        Builder builder = new Builder();
        builder.setLowpanIdentity(identity);
        if (includeCredential) {
            builder.setLowpanCredential(this.mActualLowpanInterface.getLowpanCredential());
        }
        return new LowpanProvisioningParams(builder.build());
    }

    public int getRole() {
        return convertStringToRole(this.mActualLowpanInterface.getRole());
    }

    public LowpanCredential getLowpanCredential() {
        LowpanCredential actualCredential = this.mActualLowpanInterface.getLowpanCredential();
        if (actualCredential != null) {
            return new LowpanCredential(actualCredential);
        }
        return null;
    }

    public String[] getSupportedNetworkTypes() throws LowpanException {
        try {
            return this.mActualLowpanInterface.getSupportedNetworkTypes();
        } catch (LowpanException x) {
            throw LowpanException.createFromActual(x);
        } catch (LowpanRuntimeException x2) {
            throw LowpanRuntimeException.createFromActual(x2);
        }
    }

    public LowpanChannelInfo[] getSupportedChannels() throws LowpanException {
        try {
            LowpanChannelInfo[] actualChannels = this.mActualLowpanInterface.getSupportedChannels();
            LowpanChannelInfo[] ret = new LowpanChannelInfo[actualChannels.length];
            for (int i = 0; i < actualChannels.length; i++) {
                ret[i] = new LowpanChannelInfo(actualChannels[i]);
            }
            return ret;
        } catch (LowpanException x) {
            throw LowpanException.createFromActual(x);
        } catch (LowpanRuntimeException x2) {
            throw LowpanRuntimeException.createFromActual(x2);
        }
    }

    public void registerCallback(Callback cb, Handler handler) {
        CallbackInternal callbackInternal = new CallbackInternal(this, cb);
        this.mActualLowpanInterface.registerCallback(callbackInternal, handler);
        synchronized (this.mListenerMap) {
            this.mListenerMap.put(cb, callbackInternal);
            if (handler == null) {
                handler = new Handler();
            }
            this.mListenerHandlerMap.put(cb, handler);
        }
    }

    public void registerCallback(Callback cb) {
        registerCallback(cb, null);
    }

    public void unregisterCallback(Callback cb) {
        synchronized (this.mListenerMap) {
            CallbackInternal callbackInternal = (CallbackInternal) this.mListenerMap.remove(cb);
            this.mListenerHandlerMap.remove(cb);
        }
        if (callbackInternal != null) {
            this.mActualLowpanInterface.unregisterCallback(callbackInternal);
        }
    }

    public LowpanScanner createScanner() {
        return new LowpanScanner(this.mActualLowpanInterface.createScanner());
    }

    public void addOnMeshPrefix(IpPrefix prefix, int flags) throws LowpanException {
        try {
            this.mActualLowpanInterface.addOnMeshPrefix(prefix, flags);
        } catch (LowpanException x) {
            throw LowpanException.createFromActual(x);
        } catch (LowpanRuntimeException x2) {
            throw LowpanRuntimeException.createFromActual(x2);
        }
    }

    public void removeOnMeshPrefix(IpPrefix prefix) {
        this.mActualLowpanInterface.removeOnMeshPrefix(prefix);
    }

    public void addExternalRoute(IpPrefix prefix, int flags) throws LowpanException {
        try {
            this.mActualLowpanInterface.addExternalRoute(prefix, flags);
        } catch (LowpanException x) {
            throw LowpanException.createFromActual(x);
        } catch (LowpanRuntimeException x2) {
            throw LowpanRuntimeException.createFromActual(x2);
        }
    }

    public void removeExternalRoute(IpPrefix prefix) {
        this.mActualLowpanInterface.removeExternalRoute(prefix);
    }
}
