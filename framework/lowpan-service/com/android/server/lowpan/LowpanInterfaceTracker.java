package com.android.server.lowpan;

import android.content.Context;
import android.net.IpPrefix;
import android.net.LinkAddress;
import android.net.LinkProperties;
import android.net.NetworkAgent;
import android.net.NetworkCapabilities;
import android.net.NetworkFactory;
import android.net.NetworkInfo;
import android.net.NetworkInfo.DetailedState;
import android.net.ip.IpManager;
import android.net.ip.IpManager.Callback;
import android.net.ip.IpManager.InitialConfiguration;
import android.net.ip.IpManager.ProvisioningConfiguration.Builder;
import android.net.lowpan.ILowpanInterface;
import android.net.lowpan.LowpanInterface;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.util.Log;
import com.android.internal.util.HexDump;
import com.android.internal.util.State;
import com.android.internal.util.StateMachine;

class LowpanInterfaceTracker extends StateMachine {
    static final int BASE = 544768;
    static final int CMD_LINK_PROPERTIES_CHANGE = 544774;
    static final int CMD_PROVISIONING_FAILURE = 544777;
    static final int CMD_PROVISIONING_SUCCESS = 544776;
    static final int CMD_START_NETWORK = 544771;
    static final int CMD_STATE_CHANGE = 544773;
    static final int CMD_STOP_NETWORK = 544772;
    static final int CMD_UNWANTED = 544775;
    private static final boolean DBG = true;
    private static final int NETWORK_SCORE = 30;
    private static final String NETWORK_TYPE = "LoWPAN";
    public static final short NUM_LOG_RECS_NORMAL = (short) 100;
    private static final String TAG = "LowpanInterfaceTracker";
    final AttachedState mAttachedState = new AttachedState();
    final AttachingState mAttachingState = new AttachingState();
    final CommissioningState mCommissioningState = new CommissioningState();
    final ConnectedState mConnectedState = new ConnectedState();
    private Context mContext;
    final DefaultState mDefaultState = new DefaultState();
    final FaultState mFaultState = new FaultState();
    private String mHwAddr;
    ILowpanInterface mILowpanInterface;
    private String mInterfaceName;
    private IpManager mIpManager;
    private final Callback mIpManagerCallback = new IpManagerCallback();
    private LinkProperties mLinkProperties;
    private LocalLowpanCallback mLocalLowpanCallback = new LocalLowpanCallback();
    private LowpanInterface mLowpanInterface;
    private NetworkAgent mNetworkAgent;
    private final NetworkCapabilities mNetworkCapabilities = new NetworkCapabilities();
    private NetworkFactory mNetworkFactory;
    private NetworkInfo mNetworkInfo;
    final NormalState mNormalState = new NormalState();
    final ObtainingIpState mObtainingIpState = new ObtainingIpState();
    final OfflineState mOfflineState = new OfflineState();
    private String mState = "";

    class AttachedState extends State {
        AttachedState() {
        }

        public void enter() {
            LowpanInterfaceTracker.this.bringUpNetworkAgent();
            LowpanInterfaceTracker.this.mNetworkInfo.setIsAvailable(LowpanInterfaceTracker.DBG);
        }

        public boolean processMessage(Message message) {
            switch (message.what) {
                case LowpanInterfaceTracker.CMD_STATE_CHANGE /*544773*/:
                    if (LowpanInterfaceTracker.this.mState.equals(message.obj) || ("attached".equals(message.obj) ^ 1) == 0) {
                        return LowpanInterfaceTracker.DBG;
                    }
                    return false;
                default:
                    return false;
            }
        }

        public void exit() {
            LowpanInterfaceTracker.this.mNetworkInfo.setIsAvailable(false);
        }
    }

    class AttachingState extends State {
        AttachingState() {
        }

        public void enter() {
            LowpanInterfaceTracker.this.mNetworkInfo.setDetailedState(DetailedState.CONNECTING, null, LowpanInterfaceTracker.this.mHwAddr);
            LowpanInterfaceTracker.this.mNetworkInfo.setIsAvailable(LowpanInterfaceTracker.DBG);
            LowpanInterfaceTracker.this.bringUpNetworkAgent();
            LowpanInterfaceTracker.this.mNetworkAgent.sendNetworkInfo(LowpanInterfaceTracker.this.mNetworkInfo);
        }

        public boolean processMessage(Message message) {
            return false;
        }

        public void exit() {
        }
    }

    class CommissioningState extends State {
        CommissioningState() {
        }

        public void enter() {
        }

        public boolean processMessage(Message message) {
            return false;
        }

        public void exit() {
        }
    }

    class ConnectedState extends State {
        ConnectedState() {
        }

        public void enter() {
            LowpanInterfaceTracker.this.mNetworkInfo.setDetailedState(DetailedState.CONNECTED, null, LowpanInterfaceTracker.this.mHwAddr);
            LowpanInterfaceTracker.this.mNetworkAgent.sendNetworkInfo(LowpanInterfaceTracker.this.mNetworkInfo);
            LowpanInterfaceTracker.this.mNetworkAgent.sendNetworkScore(LowpanInterfaceTracker.NETWORK_SCORE);
        }

        public boolean processMessage(Message message) {
            return false;
        }

        public void exit() {
            if (LowpanInterfaceTracker.this.mNetworkAgent != null) {
                LowpanInterfaceTracker.this.mNetworkAgent.sendNetworkScore(0);
            }
        }
    }

    class DefaultState extends State {
        DefaultState() {
        }

        public void enter() {
            Log.i(LowpanInterfaceTracker.TAG, "DefaultState.enter()");
            LowpanInterfaceTracker.this.mNetworkInfo = new NetworkInfo(-1, 0, LowpanInterfaceTracker.NETWORK_TYPE, "");
            LowpanInterfaceTracker.this.mNetworkInfo.setIsAvailable(LowpanInterfaceTracker.DBG);
            LowpanInterfaceTracker.this.mLowpanInterface.registerCallback(LowpanInterfaceTracker.this.mLocalLowpanCallback);
            LowpanInterfaceTracker.this.mState = "";
            LowpanInterfaceTracker.this.sendMessage(LowpanInterfaceTracker.CMD_STATE_CHANGE, LowpanInterfaceTracker.this.mLowpanInterface.getState());
        }

        public boolean processMessage(Message message) {
            boolean retValue = false;
            switch (message.what) {
                case LowpanInterfaceTracker.CMD_START_NETWORK /*544771*/:
                    Log.i(LowpanInterfaceTracker.TAG, "CMD_START_NETWORK");
                    try {
                        LowpanInterfaceTracker.this.mLowpanInterface.setEnabled(LowpanInterfaceTracker.DBG);
                        break;
                    } catch (Exception x) {
                        Log.e(LowpanInterfaceTracker.TAG, "Exception while enabling: " + x);
                        LowpanInterfaceTracker.this.transitionTo(LowpanInterfaceTracker.this.mFaultState);
                        return LowpanInterfaceTracker.DBG;
                    }
                case LowpanInterfaceTracker.CMD_STOP_NETWORK /*544772*/:
                    Log.i(LowpanInterfaceTracker.TAG, "CMD_STOP_NETWORK");
                    try {
                        LowpanInterfaceTracker.this.mLowpanInterface.setEnabled(false);
                        break;
                    } catch (Exception x2) {
                        Log.e(LowpanInterfaceTracker.TAG, "Exception while disabling: " + x2);
                        LowpanInterfaceTracker.this.transitionTo(LowpanInterfaceTracker.this.mFaultState);
                        return LowpanInterfaceTracker.DBG;
                    }
                case LowpanInterfaceTracker.CMD_STATE_CHANGE /*544773*/:
                    if (!LowpanInterfaceTracker.this.mState.equals(message.obj)) {
                        Log.i(LowpanInterfaceTracker.TAG, "LowpanInterface changed state from \"" + LowpanInterfaceTracker.this.mState + "\" to \"" + message.obj + "\".");
                        LowpanInterfaceTracker.this.mState = (String) message.obj;
                        String -get11 = LowpanInterfaceTracker.this.mState;
                        if (-get11.equals("offline")) {
                            LowpanInterfaceTracker.this.transitionTo(LowpanInterfaceTracker.this.mOfflineState);
                        } else if (-get11.equals("commissioning")) {
                            LowpanInterfaceTracker.this.transitionTo(LowpanInterfaceTracker.this.mCommissioningState);
                        } else if (-get11.equals("attaching")) {
                            LowpanInterfaceTracker.this.transitionTo(LowpanInterfaceTracker.this.mAttachingState);
                        } else if (-get11.equals("attached")) {
                            LowpanInterfaceTracker.this.transitionTo(LowpanInterfaceTracker.this.mObtainingIpState);
                        } else if (-get11.equals("fault")) {
                            LowpanInterfaceTracker.this.transitionTo(LowpanInterfaceTracker.this.mFaultState);
                        }
                    }
                    retValue = LowpanInterfaceTracker.DBG;
                    break;
            }
            return retValue;
        }

        public void exit() {
            LowpanInterfaceTracker.this.mLowpanInterface.unregisterCallback(LowpanInterfaceTracker.this.mLocalLowpanCallback);
        }
    }

    class FaultState extends State {
        FaultState() {
        }

        public void enter() {
        }

        public boolean processMessage(Message message) {
            return false;
        }

        public void exit() {
        }
    }

    class IpManagerCallback extends Callback {
        IpManagerCallback() {
        }

        public void onProvisioningSuccess(LinkProperties newLp) {
            LowpanInterfaceTracker.this.sendMessage(LowpanInterfaceTracker.CMD_PROVISIONING_SUCCESS, newLp);
        }

        public void onProvisioningFailure(LinkProperties newLp) {
            LowpanInterfaceTracker.this.sendMessage(LowpanInterfaceTracker.CMD_PROVISIONING_FAILURE, newLp);
        }

        public void onLinkPropertiesChange(LinkProperties newLp) {
            LowpanInterfaceTracker.this.sendMessage(LowpanInterfaceTracker.CMD_LINK_PROPERTIES_CHANGE, newLp);
        }
    }

    private class LocalLowpanCallback extends LowpanInterface.Callback {
        private LocalLowpanCallback() {
        }

        public void onEnabledChanged(boolean value) {
        }

        public void onUpChanged(boolean value) {
        }

        public void onConnectedChanged(boolean value) {
        }

        public void onStateChanged(String state) {
            LowpanInterfaceTracker.this.sendMessage(LowpanInterfaceTracker.CMD_STATE_CHANGE, state);
        }
    }

    class NormalState extends State {
        NormalState() {
        }

        public void enter() {
            Log.i(LowpanInterfaceTracker.TAG, "NormalState.enter()");
            LowpanInterfaceTracker.this.mIpManager = new IpManager(LowpanInterfaceTracker.this.mContext, LowpanInterfaceTracker.this.mInterfaceName, LowpanInterfaceTracker.this.mIpManagerCallback);
            if (LowpanInterfaceTracker.this.mHwAddr == null) {
                byte[] hwAddr = null;
                try {
                    hwAddr = LowpanInterfaceTracker.this.mLowpanInterface.getService().getMacAddress();
                } catch (Exception x) {
                    Log.e(LowpanInterfaceTracker.TAG, "Call to getMacAddress() failed: " + x);
                    LowpanInterfaceTracker.this.transitionTo(LowpanInterfaceTracker.this.mFaultState);
                }
                if (hwAddr != null) {
                    LowpanInterfaceTracker.this.mHwAddr = HexDump.toHexString(hwAddr);
                }
            }
            LowpanInterfaceTracker.this.mNetworkFactory.register();
        }

        public boolean processMessage(Message message) {
            switch (message.what) {
                case LowpanInterfaceTracker.CMD_LINK_PROPERTIES_CHANGE /*544774*/:
                    LowpanInterfaceTracker.this.mLinkProperties = (LinkProperties) message.obj;
                    Log.i(LowpanInterfaceTracker.TAG, "Got LinkProperties: " + LowpanInterfaceTracker.this.mLinkProperties);
                    if (LowpanInterfaceTracker.this.mNetworkAgent != null) {
                        LowpanInterfaceTracker.this.mNetworkAgent.sendLinkProperties(LowpanInterfaceTracker.this.mLinkProperties);
                        break;
                    }
                    break;
                case LowpanInterfaceTracker.CMD_UNWANTED /*544775*/:
                    if (LowpanInterfaceTracker.this.mNetworkAgent == message.obj) {
                        Log.i(LowpanInterfaceTracker.TAG, "UNWANTED.");
                        try {
                            LowpanInterfaceTracker.this.mLowpanInterface.setEnabled(false);
                            LowpanInterfaceTracker.this.shutdownNetworkAgent();
                            break;
                        } catch (Exception x) {
                            Log.e(LowpanInterfaceTracker.TAG, "Exception while disabling: " + x);
                            LowpanInterfaceTracker.this.transitionTo(LowpanInterfaceTracker.this.mFaultState);
                            return LowpanInterfaceTracker.DBG;
                        }
                    }
                    break;
                case LowpanInterfaceTracker.CMD_PROVISIONING_FAILURE /*544777*/:
                    Log.i(LowpanInterfaceTracker.TAG, "Provisioning Failure: " + message.obj);
                    break;
            }
            return false;
        }

        public void exit() {
            LowpanInterfaceTracker.this.shutdownNetworkAgent();
            LowpanInterfaceTracker.this.mNetworkFactory.unregister();
            if (LowpanInterfaceTracker.this.mIpManager != null) {
                LowpanInterfaceTracker.this.mIpManager.shutdown();
            }
            LowpanInterfaceTracker.this.mIpManager = null;
        }
    }

    class ObtainingIpState extends State {
        ObtainingIpState() {
        }

        public void enter() {
            InitialConfiguration initialConfiguration = new InitialConfiguration();
            try {
                for (LinkAddress address : LowpanInterfaceTracker.this.mLowpanInterface.getLinkAddresses()) {
                    Log.i(LowpanInterfaceTracker.TAG, "Adding link address: " + address);
                    initialConfiguration.ipAddresses.add(address);
                    initialConfiguration.directlyConnectedRoutes.add(new IpPrefix(address.getAddress(), address.getPrefixLength()));
                }
                for (IpPrefix prefix : LowpanInterfaceTracker.this.mLowpanInterface.getLinkNetworks()) {
                    Log.i(LowpanInterfaceTracker.TAG, "Adding directly connected route: " + prefix);
                    initialConfiguration.directlyConnectedRoutes.add(prefix);
                }
            } catch (Exception x) {
                Log.e(LowpanInterfaceTracker.TAG, "Exception while populating InitialConfiguration: " + x);
                LowpanInterfaceTracker.this.transitionTo(LowpanInterfaceTracker.this.mFaultState);
                return;
            } catch (RuntimeException x2) {
                if (x2.getCause() instanceof RemoteException) {
                    Log.e(LowpanInterfaceTracker.TAG, "RuntimeException while populating InitialConfiguration: " + x2);
                    LowpanInterfaceTracker.this.transitionTo(LowpanInterfaceTracker.this.mFaultState);
                } else {
                    throw x2;
                }
            }
            if (initialConfiguration.isValid()) {
                Log.d(LowpanInterfaceTracker.TAG, "Using Initial configuration: " + initialConfiguration);
                LowpanInterfaceTracker.this.mIpManager;
                Builder builder = IpManager.buildProvisioningConfiguration();
                builder.withInitialConfiguration(initialConfiguration).withProvisioningTimeoutMs(0);
                builder.withoutIpReachabilityMonitor();
                builder.withoutIPv4();
                LowpanInterfaceTracker.this.mIpManager.startProvisioning(builder.build());
                LowpanInterfaceTracker.this.mNetworkInfo.setDetailedState(DetailedState.OBTAINING_IPADDR, null, LowpanInterfaceTracker.this.mHwAddr);
                LowpanInterfaceTracker.this.mNetworkAgent.sendNetworkInfo(LowpanInterfaceTracker.this.mNetworkInfo);
                return;
            }
            Log.e(LowpanInterfaceTracker.TAG, "Invalid initial configuration: " + initialConfiguration);
            LowpanInterfaceTracker.this.transitionTo(LowpanInterfaceTracker.this.mFaultState);
        }

        public boolean processMessage(Message message) {
            switch (message.what) {
                case LowpanInterfaceTracker.CMD_PROVISIONING_SUCCESS /*544776*/:
                    Log.i(LowpanInterfaceTracker.TAG, "Provisioning Success: " + message.obj);
                    LowpanInterfaceTracker.this.transitionTo(LowpanInterfaceTracker.this.mConnectedState);
                    return LowpanInterfaceTracker.DBG;
                default:
                    return false;
            }
        }

        public void exit() {
        }
    }

    class OfflineState extends State {
        OfflineState() {
        }

        public void enter() {
            LowpanInterfaceTracker.this.shutdownNetworkAgent();
            LowpanInterfaceTracker.this.mNetworkInfo.setIsAvailable(LowpanInterfaceTracker.DBG);
            LowpanInterfaceTracker.this.mIpManager.stop();
        }

        public boolean processMessage(Message message) {
            return false;
        }

        public void exit() {
        }
    }

    public LowpanInterfaceTracker(Context context, ILowpanInterface ifaceService, Looper looper) {
        super(TAG, looper);
        Log.i(TAG, "LowpanInterfaceTracker() begin");
        setDbg(DBG);
        setLogRecSize(100);
        setLogOnlyTransitions(false);
        this.mILowpanInterface = ifaceService;
        this.mLowpanInterface = new LowpanInterface(context, ifaceService, looper);
        this.mContext = context;
        this.mInterfaceName = this.mLowpanInterface.getName();
        this.mLinkProperties = new LinkProperties();
        this.mLinkProperties.setInterfaceName(this.mInterfaceName);
        this.mNetworkCapabilities.addTransportType(6);
        this.mNetworkCapabilities.addCapability(13);
        this.mNetworkCapabilities.setLinkUpstreamBandwidthKbps(100);
        this.mNetworkCapabilities.setLinkDownstreamBandwidthKbps(100);
        addState(this.mDefaultState);
        addState(this.mFaultState, this.mDefaultState);
        addState(this.mNormalState, this.mDefaultState);
        addState(this.mOfflineState, this.mNormalState);
        addState(this.mCommissioningState, this.mNormalState);
        addState(this.mAttachingState, this.mNormalState);
        addState(this.mAttachedState, this.mNormalState);
        addState(this.mObtainingIpState, this.mAttachedState);
        addState(this.mConnectedState, this.mAttachedState);
        setInitialState(this.mDefaultState);
        this.mNetworkFactory = new NetworkFactory(looper, context, NETWORK_TYPE, this.mNetworkCapabilities) {
            protected void startNetwork() {
                LowpanInterfaceTracker.this.sendMessage(LowpanInterfaceTracker.CMD_START_NETWORK);
            }

            protected void stopNetwork() {
                LowpanInterfaceTracker.this.sendMessage(LowpanInterfaceTracker.CMD_STOP_NETWORK);
            }
        };
        Log.i(TAG, "LowpanInterfaceTracker() end");
    }

    private void shutdownNetworkAgent() {
        this.mNetworkInfo.setDetailedState(DetailedState.DISCONNECTED, null, this.mHwAddr);
        this.mNetworkInfo.setIsAvailable(false);
        if (this.mNetworkAgent != null) {
            this.mNetworkAgent.sendNetworkScore(0);
            this.mNetworkAgent.sendNetworkInfo(this.mNetworkInfo);
        }
        this.mNetworkAgent = null;
    }

    private void bringUpNetworkAgent() {
        if (this.mNetworkAgent == null) {
            this.mNetworkAgent = new NetworkAgent(this.mNetworkFactory.getLooper(), this.mContext, NETWORK_TYPE, this.mNetworkInfo, this.mNetworkCapabilities, this.mLinkProperties, NETWORK_SCORE) {
                public void unwanted() {
                    LowpanInterfaceTracker.this.sendMessage(LowpanInterfaceTracker.CMD_UNWANTED, this);
                }
            };
        }
    }

    public void register() {
        Log.i(TAG, "register()");
        start();
    }

    public void unregister() {
        Log.i(TAG, "unregister()");
        quit();
    }
}
