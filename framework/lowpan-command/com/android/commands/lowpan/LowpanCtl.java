package com.android.commands.lowpan;

import android.net.LinkAddress;
import android.net.lowpan.ILowpanInterface;
import android.net.lowpan.LowpanBeaconInfo;
import android.net.lowpan.LowpanCredential;
import android.net.lowpan.LowpanEnergyScanResult;
import android.net.lowpan.LowpanException;
import android.net.lowpan.LowpanIdentity;
import android.net.lowpan.LowpanInterface;
import android.net.lowpan.LowpanManager;
import android.net.lowpan.LowpanProvision;
import android.net.lowpan.LowpanProvision.Builder;
import android.net.lowpan.LowpanScanner;
import android.net.lowpan.LowpanScanner.Callback;
import android.os.RemoteException;
import android.util.AndroidRuntimeException;
import com.android.internal.os.BaseCommand;
import com.android.internal.util.HexDump;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class LowpanCtl extends BaseCommand {
    private ILowpanInterface mILowpanInterface;
    private LowpanInterface mLowpanInterface;
    private String mLowpanInterfaceName;
    private LowpanManager mLowpanManager;

    private class ArgumentErrorException extends IllegalArgumentException {
        public ArgumentErrorException(String desc) {
            super(desc);
        }
    }

    private class CommandErrorException extends AndroidRuntimeException {
        public CommandErrorException(String desc) {
            super(desc);
        }
    }

    public static void main(String[] args) {
        new LowpanCtl().run(args);
    }

    public void onShowUsage(PrintStream out) {
        out.println("usage: lowpanctl [options] [subcommand] [subcommand-options]\noptions:\n       -I / --interface <iface-name> ..... Interface Name\nsubcommands:\n       lowpanctl status\n       lowpanctl form\n       lowpanctl join\n       lowpanctl attach\n       lowpanctl leave\n       lowpanctl enable\n       lowpanctl disable\n       lowpanctl show-credential\n       lowpanctl scan\n       lowpanctl reset\n       lowpanctl list\n\nusage: lowpanctl [options] join/form/attach [network-name]\nsubcommand-options:\n       --name <network-name> ............. Network Name\n       -p / --panid <panid> .............. PANID\n       -c / --channel <channel> .......... Channel Index\n       -x / --xpanid <xpanid> ............ XPANID\n       -k / --master-key <master-key> .... Master Key\n       --master-key-index <key-index> .... Key Index\n\nusage: lowpanctl [options] show-credential\nsubcommand-options:\n       -r / --raw ........................ Print only key contents\n\n");
    }

    private void throwCommandError(String desc) {
        throw new CommandErrorException(desc);
    }

    private void throwArgumentError(String desc) {
        throw new ArgumentErrorException(desc);
    }

    private LowpanManager getLowpanManager() {
        if (this.mLowpanManager == null) {
            this.mLowpanManager = LowpanManager.getManager();
            if (this.mLowpanManager == null) {
                System.err.println("Error type 2");
                throwCommandError("Can't connect to LoWPAN service; is the service running?");
            }
        }
        return this.mLowpanManager;
    }

    private LowpanInterface getLowpanInterface() {
        if (this.mLowpanInterface == null) {
            if (this.mLowpanInterfaceName == null) {
                String[] interfaceArray = getLowpanManager().getInterfaceList();
                if (interfaceArray.length != 0) {
                    this.mLowpanInterfaceName = interfaceArray[0];
                } else {
                    throwCommandError("No LoWPAN interfaces are present");
                }
            }
            this.mLowpanInterface = getLowpanManager().getInterface(this.mLowpanInterfaceName);
            if (this.mLowpanInterface == null) {
                throwCommandError("Unknown LoWPAN interface \"" + this.mLowpanInterfaceName + "\"");
            }
        }
        return this.mLowpanInterface;
    }

    private ILowpanInterface getILowpanInterface() {
        if (this.mILowpanInterface == null) {
            this.mILowpanInterface = getLowpanInterface().getService();
        }
        return this.mILowpanInterface;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void onRun() throws java.lang.Exception {
        /*
        r9 = this;
        r8 = 1;
    L_0x0001:
        r0 = r9.nextArgRequired();	 Catch:{ ServiceSpecificException -> 0x0020, ArgumentErrorException -> 0x0096, IllegalArgumentException -> 0x00ae, CommandErrorException -> 0x00d7 }
        if (r0 == 0) goto L_0x006c;
    L_0x0007:
        r5 = "-I";
        r5 = r0.equals(r5);	 Catch:{ ServiceSpecificException -> 0x0020, ArgumentErrorException -> 0x0096, IllegalArgumentException -> 0x00ae, CommandErrorException -> 0x00d7 }
        if (r5 != 0) goto L_0x0019;
    L_0x0010:
        r5 = "--interface";
        r5 = r0.equals(r5);	 Catch:{ ServiceSpecificException -> 0x0020, ArgumentErrorException -> 0x0096, IllegalArgumentException -> 0x00ae, CommandErrorException -> 0x00d7 }
        if (r5 == 0) goto L_0x004c;
    L_0x0019:
        r5 = r9.nextArgRequired();	 Catch:{ ServiceSpecificException -> 0x0020, ArgumentErrorException -> 0x0096, IllegalArgumentException -> 0x00ae, CommandErrorException -> 0x00d7 }
        r9.mLowpanInterfaceName = r5;	 Catch:{ ServiceSpecificException -> 0x0020, ArgumentErrorException -> 0x0096, IllegalArgumentException -> 0x00ae, CommandErrorException -> 0x00d7 }
        goto L_0x0001;
    L_0x0020:
        r1 = move-exception;
        r5 = java.lang.System.out;
        r6 = new java.lang.StringBuilder;
        r6.<init>();
        r7 = "ServiceSpecificException: ";
        r6 = r6.append(r7);
        r7 = r1.errorCode;
        r6 = r6.append(r7);
        r7 = ": ";
        r6 = r6.append(r7);
        r7 = r1.getLocalizedMessage();
        r6 = r6.append(r7);
        r6 = r6.toString();
        r5.println(r6);
        throw r1;
    L_0x004c:
        r5 = "-h";
        r5 = r0.equals(r5);	 Catch:{ ServiceSpecificException -> 0x0020, ArgumentErrorException -> 0x0096, IllegalArgumentException -> 0x00ae, CommandErrorException -> 0x00d7 }
        if (r5 != 0) goto L_0x0067;
    L_0x0055:
        r5 = "--help";
        r5 = r0.equals(r5);	 Catch:{ ServiceSpecificException -> 0x0020, ArgumentErrorException -> 0x0096, IllegalArgumentException -> 0x00ae, CommandErrorException -> 0x00d7 }
        if (r5 != 0) goto L_0x0067;
    L_0x005e:
        r5 = "help";
        r5 = r0.equals(r5);	 Catch:{ ServiceSpecificException -> 0x0020, ArgumentErrorException -> 0x0096, IllegalArgumentException -> 0x00ae, CommandErrorException -> 0x00d7 }
        if (r5 == 0) goto L_0x006d;
    L_0x0067:
        r5 = java.lang.System.out;	 Catch:{ ServiceSpecificException -> 0x0020, ArgumentErrorException -> 0x0096, IllegalArgumentException -> 0x00ae, CommandErrorException -> 0x00d7 }
        r9.onShowUsage(r5);	 Catch:{ ServiceSpecificException -> 0x0020, ArgumentErrorException -> 0x0096, IllegalArgumentException -> 0x00ae, CommandErrorException -> 0x00d7 }
    L_0x006c:
        return;
    L_0x006d:
        r5 = "-";
        r5 = r0.startsWith(r5);	 Catch:{ ServiceSpecificException -> 0x0020, ArgumentErrorException -> 0x0096, IllegalArgumentException -> 0x00ae, CommandErrorException -> 0x00d7 }
        if (r5 == 0) goto L_0x0098;
    L_0x0076:
        r5 = new java.lang.StringBuilder;	 Catch:{ ServiceSpecificException -> 0x0020, ArgumentErrorException -> 0x0096, IllegalArgumentException -> 0x00ae, CommandErrorException -> 0x00d7 }
        r5.<init>();	 Catch:{ ServiceSpecificException -> 0x0020, ArgumentErrorException -> 0x0096, IllegalArgumentException -> 0x00ae, CommandErrorException -> 0x00d7 }
        r6 = "Unrecognized argument \"";
        r5 = r5.append(r6);	 Catch:{ ServiceSpecificException -> 0x0020, ArgumentErrorException -> 0x0096, IllegalArgumentException -> 0x00ae, CommandErrorException -> 0x00d7 }
        r5 = r5.append(r0);	 Catch:{ ServiceSpecificException -> 0x0020, ArgumentErrorException -> 0x0096, IllegalArgumentException -> 0x00ae, CommandErrorException -> 0x00d7 }
        r6 = "\"";
        r5 = r5.append(r6);	 Catch:{ ServiceSpecificException -> 0x0020, ArgumentErrorException -> 0x0096, IllegalArgumentException -> 0x00ae, CommandErrorException -> 0x00d7 }
        r5 = r5.toString();	 Catch:{ ServiceSpecificException -> 0x0020, ArgumentErrorException -> 0x0096, IllegalArgumentException -> 0x00ae, CommandErrorException -> 0x00d7 }
        r9.throwArgumentError(r5);	 Catch:{ ServiceSpecificException -> 0x0020, ArgumentErrorException -> 0x0096, IllegalArgumentException -> 0x00ae, CommandErrorException -> 0x00d7 }
        goto L_0x0001;
    L_0x0096:
        r2 = move-exception;
        throw r2;
    L_0x0098:
        r5 = "status";
        r5 = r0.equals(r5);	 Catch:{ ServiceSpecificException -> 0x0020, ArgumentErrorException -> 0x0096, IllegalArgumentException -> 0x00ae, CommandErrorException -> 0x00d7 }
        if (r5 != 0) goto L_0x00aa;
    L_0x00a1:
        r5 = "stat";
        r5 = r0.equals(r5);	 Catch:{ ServiceSpecificException -> 0x0020, ArgumentErrorException -> 0x0096, IllegalArgumentException -> 0x00ae, CommandErrorException -> 0x00d7 }
        if (r5 == 0) goto L_0x00b8;
    L_0x00aa:
        r9.runStatus();	 Catch:{ ServiceSpecificException -> 0x0020, ArgumentErrorException -> 0x0096, IllegalArgumentException -> 0x00ae, CommandErrorException -> 0x00d7 }
        goto L_0x006c;
    L_0x00ae:
        r4 = move-exception;
        r5 = java.lang.System.err;
        r4.printStackTrace(r5);
        java.lang.System.exit(r8);
        goto L_0x006c;
    L_0x00b8:
        r5 = "scan";
        r5 = r0.equals(r5);	 Catch:{ ServiceSpecificException -> 0x0020, ArgumentErrorException -> 0x0096, IllegalArgumentException -> 0x00ae, CommandErrorException -> 0x00d7 }
        if (r5 != 0) goto L_0x00d3;
    L_0x00c1:
        r5 = "netscan";
        r5 = r0.equals(r5);	 Catch:{ ServiceSpecificException -> 0x0020, ArgumentErrorException -> 0x0096, IllegalArgumentException -> 0x00ae, CommandErrorException -> 0x00d7 }
        if (r5 != 0) goto L_0x00d3;
    L_0x00ca:
        r5 = "ns";
        r5 = r0.equals(r5);	 Catch:{ ServiceSpecificException -> 0x0020, ArgumentErrorException -> 0x0096, IllegalArgumentException -> 0x00ae, CommandErrorException -> 0x00d7 }
        if (r5 == 0) goto L_0x00fa;
    L_0x00d3:
        r9.runNetScan();	 Catch:{ ServiceSpecificException -> 0x0020, ArgumentErrorException -> 0x0096, IllegalArgumentException -> 0x00ae, CommandErrorException -> 0x00d7 }
        goto L_0x006c;
    L_0x00d7:
        r3 = move-exception;
        r5 = java.lang.System.out;
        r6 = new java.lang.StringBuilder;
        r6.<init>();
        r7 = "error: ";
        r6 = r6.append(r7);
        r7 = r3.getLocalizedMessage();
        r6 = r6.append(r7);
        r6 = r6.toString();
        r5.println(r6);
        java.lang.System.exit(r8);
        goto L_0x006c;
    L_0x00fa:
        r5 = "attach";
        r5 = r0.equals(r5);	 Catch:{ ServiceSpecificException -> 0x0020, ArgumentErrorException -> 0x0096, IllegalArgumentException -> 0x00ae, CommandErrorException -> 0x00d7 }
        if (r5 == 0) goto L_0x0108;
    L_0x0103:
        r9.runAttach();	 Catch:{ ServiceSpecificException -> 0x0020, ArgumentErrorException -> 0x0096, IllegalArgumentException -> 0x00ae, CommandErrorException -> 0x00d7 }
        goto L_0x006c;
    L_0x0108:
        r5 = "enable";
        r5 = r0.equals(r5);	 Catch:{ ServiceSpecificException -> 0x0020, ArgumentErrorException -> 0x0096, IllegalArgumentException -> 0x00ae, CommandErrorException -> 0x00d7 }
        if (r5 == 0) goto L_0x0116;
    L_0x0111:
        r9.runEnable();	 Catch:{ ServiceSpecificException -> 0x0020, ArgumentErrorException -> 0x0096, IllegalArgumentException -> 0x00ae, CommandErrorException -> 0x00d7 }
        goto L_0x006c;
    L_0x0116:
        r5 = "disable";
        r5 = r0.equals(r5);	 Catch:{ ServiceSpecificException -> 0x0020, ArgumentErrorException -> 0x0096, IllegalArgumentException -> 0x00ae, CommandErrorException -> 0x00d7 }
        if (r5 == 0) goto L_0x0124;
    L_0x011f:
        r9.runDisable();	 Catch:{ ServiceSpecificException -> 0x0020, ArgumentErrorException -> 0x0096, IllegalArgumentException -> 0x00ae, CommandErrorException -> 0x00d7 }
        goto L_0x006c;
    L_0x0124:
        r5 = "show-credential";
        r5 = r0.equals(r5);	 Catch:{ ServiceSpecificException -> 0x0020, ArgumentErrorException -> 0x0096, IllegalArgumentException -> 0x00ae, CommandErrorException -> 0x00d7 }
        if (r5 == 0) goto L_0x0132;
    L_0x012d:
        r9.runShowCredential();	 Catch:{ ServiceSpecificException -> 0x0020, ArgumentErrorException -> 0x0096, IllegalArgumentException -> 0x00ae, CommandErrorException -> 0x00d7 }
        goto L_0x006c;
    L_0x0132:
        r5 = "join";
        r5 = r0.equals(r5);	 Catch:{ ServiceSpecificException -> 0x0020, ArgumentErrorException -> 0x0096, IllegalArgumentException -> 0x00ae, CommandErrorException -> 0x00d7 }
        if (r5 == 0) goto L_0x0140;
    L_0x013b:
        r9.runJoin();	 Catch:{ ServiceSpecificException -> 0x0020, ArgumentErrorException -> 0x0096, IllegalArgumentException -> 0x00ae, CommandErrorException -> 0x00d7 }
        goto L_0x006c;
    L_0x0140:
        r5 = "form";
        r5 = r0.equals(r5);	 Catch:{ ServiceSpecificException -> 0x0020, ArgumentErrorException -> 0x0096, IllegalArgumentException -> 0x00ae, CommandErrorException -> 0x00d7 }
        if (r5 == 0) goto L_0x014e;
    L_0x0149:
        r9.runForm();	 Catch:{ ServiceSpecificException -> 0x0020, ArgumentErrorException -> 0x0096, IllegalArgumentException -> 0x00ae, CommandErrorException -> 0x00d7 }
        goto L_0x006c;
    L_0x014e:
        r5 = "leave";
        r5 = r0.equals(r5);	 Catch:{ ServiceSpecificException -> 0x0020, ArgumentErrorException -> 0x0096, IllegalArgumentException -> 0x00ae, CommandErrorException -> 0x00d7 }
        if (r5 == 0) goto L_0x015c;
    L_0x0157:
        r9.runLeave();	 Catch:{ ServiceSpecificException -> 0x0020, ArgumentErrorException -> 0x0096, IllegalArgumentException -> 0x00ae, CommandErrorException -> 0x00d7 }
        goto L_0x006c;
    L_0x015c:
        r5 = "energyscan";
        r5 = r0.equals(r5);	 Catch:{ ServiceSpecificException -> 0x0020, ArgumentErrorException -> 0x0096, IllegalArgumentException -> 0x00ae, CommandErrorException -> 0x00d7 }
        if (r5 != 0) goto L_0x0177;
    L_0x0165:
        r5 = "energy";
        r5 = r0.equals(r5);	 Catch:{ ServiceSpecificException -> 0x0020, ArgumentErrorException -> 0x0096, IllegalArgumentException -> 0x00ae, CommandErrorException -> 0x00d7 }
        if (r5 != 0) goto L_0x0177;
    L_0x016e:
        r5 = "es";
        r5 = r0.equals(r5);	 Catch:{ ServiceSpecificException -> 0x0020, ArgumentErrorException -> 0x0096, IllegalArgumentException -> 0x00ae, CommandErrorException -> 0x00d7 }
        if (r5 == 0) goto L_0x017c;
    L_0x0177:
        r9.runEnergyScan();	 Catch:{ ServiceSpecificException -> 0x0020, ArgumentErrorException -> 0x0096, IllegalArgumentException -> 0x00ae, CommandErrorException -> 0x00d7 }
        goto L_0x006c;
    L_0x017c:
        r5 = "list";
        r5 = r0.equals(r5);	 Catch:{ ServiceSpecificException -> 0x0020, ArgumentErrorException -> 0x0096, IllegalArgumentException -> 0x00ae, CommandErrorException -> 0x00d7 }
        if (r5 != 0) goto L_0x018e;
    L_0x0185:
        r5 = "ls";
        r5 = r0.equals(r5);	 Catch:{ ServiceSpecificException -> 0x0020, ArgumentErrorException -> 0x0096, IllegalArgumentException -> 0x00ae, CommandErrorException -> 0x00d7 }
        if (r5 == 0) goto L_0x0193;
    L_0x018e:
        r9.runListInterfaces();	 Catch:{ ServiceSpecificException -> 0x0020, ArgumentErrorException -> 0x0096, IllegalArgumentException -> 0x00ae, CommandErrorException -> 0x00d7 }
        goto L_0x006c;
    L_0x0193:
        r5 = "reset";
        r5 = r0.equals(r5);	 Catch:{ ServiceSpecificException -> 0x0020, ArgumentErrorException -> 0x0096, IllegalArgumentException -> 0x00ae, CommandErrorException -> 0x00d7 }
        if (r5 == 0) goto L_0x01a1;
    L_0x019c:
        r9.runReset();	 Catch:{ ServiceSpecificException -> 0x0020, ArgumentErrorException -> 0x0096, IllegalArgumentException -> 0x00ae, CommandErrorException -> 0x00d7 }
        goto L_0x006c;
    L_0x01a1:
        r5 = new java.lang.StringBuilder;	 Catch:{ ServiceSpecificException -> 0x0020, ArgumentErrorException -> 0x0096, IllegalArgumentException -> 0x00ae, CommandErrorException -> 0x00d7 }
        r5.<init>();	 Catch:{ ServiceSpecificException -> 0x0020, ArgumentErrorException -> 0x0096, IllegalArgumentException -> 0x00ae, CommandErrorException -> 0x00d7 }
        r6 = "Unrecognized argument \"";
        r5 = r5.append(r6);	 Catch:{ ServiceSpecificException -> 0x0020, ArgumentErrorException -> 0x0096, IllegalArgumentException -> 0x00ae, CommandErrorException -> 0x00d7 }
        r5 = r5.append(r0);	 Catch:{ ServiceSpecificException -> 0x0020, ArgumentErrorException -> 0x0096, IllegalArgumentException -> 0x00ae, CommandErrorException -> 0x00d7 }
        r6 = "\"";
        r5 = r5.append(r6);	 Catch:{ ServiceSpecificException -> 0x0020, ArgumentErrorException -> 0x0096, IllegalArgumentException -> 0x00ae, CommandErrorException -> 0x00d7 }
        r5 = r5.toString();	 Catch:{ ServiceSpecificException -> 0x0020, ArgumentErrorException -> 0x0096, IllegalArgumentException -> 0x00ae, CommandErrorException -> 0x00d7 }
        r9.throwArgumentError(r5);	 Catch:{ ServiceSpecificException -> 0x0020, ArgumentErrorException -> 0x0096, IllegalArgumentException -> 0x00ae, CommandErrorException -> 0x00d7 }
        goto L_0x006c;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.commands.lowpan.LowpanCtl.onRun():void");
    }

    private void runReset() throws LowpanException {
        getLowpanInterface().reset();
    }

    private void runEnable() throws LowpanException {
        getLowpanInterface().setEnabled(true);
    }

    private void runDisable() throws LowpanException {
        getLowpanInterface().setEnabled(false);
    }

    private LowpanProvision getProvisionFromArgs(boolean credentialRequired) {
        Builder builder = new Builder();
        Map<String, Object> properties = new HashMap();
        LowpanIdentity.Builder identityBuilder = new LowpanIdentity.Builder();
        LowpanCredential credential = null;
        byte[] masterKey = null;
        int masterKeyIndex = 0;
        boolean hasName = false;
        while (true) {
            String arg = nextArg();
            if (arg == null) {
                break;
            } else if (arg.equals("--name")) {
                identityBuilder.setName(nextArgRequired());
                hasName = true;
            } else if (arg.equals("-p") || arg.equals("--panid")) {
                identityBuilder.setPanid(Integer.decode(nextArgRequired()).intValue());
            } else if (arg.equals("-c") || arg.equals("--channel")) {
                identityBuilder.setChannel(Integer.decode(nextArgRequired()).intValue());
            } else if (arg.equals("-x") || arg.equals("--xpanid")) {
                identityBuilder.setXpanid(HexDump.hexStringToByteArray(nextArgRequired()));
            } else if (arg.equals("-k") || arg.equals("--master-key")) {
                masterKey = HexDump.hexStringToByteArray(nextArgRequired());
            } else if (arg.equals("--master-key-index")) {
                masterKeyIndex = Integer.decode(nextArgRequired()).intValue();
            } else if (arg.startsWith("-") || hasName) {
                throwArgumentError("Unrecognized argument \"" + arg + "\"");
            } else {
                identityBuilder.setName(arg);
                hasName = true;
            }
        }
        if (masterKey != null) {
            if (masterKeyIndex == 0) {
                credential = LowpanCredential.createMasterKey(masterKey);
            } else {
                credential = LowpanCredential.createMasterKey(masterKey, masterKeyIndex);
            }
        }
        if (credential != null) {
            builder.setLowpanCredential(credential);
        } else if (credentialRequired) {
            throwArgumentError("No credential (like a master key) was specified!");
        }
        return builder.setLowpanIdentity(identityBuilder.build()).build();
    }

    private void runAttach() throws LowpanException {
        LowpanProvision provision = getProvisionFromArgs(true);
        System.out.println("Attaching to " + provision.getLowpanIdentity() + " with provided credential");
        getLowpanInterface().attach(provision);
        System.out.println("Attached.");
    }

    private void runLeave() throws LowpanException {
        getLowpanInterface().leave();
    }

    private void runJoin() throws LowpanException {
        LowpanProvision provision = getProvisionFromArgs(true);
        System.out.println("Joining " + provision.getLowpanIdentity() + " with provided credential");
        getLowpanInterface().join(provision);
        System.out.println("Joined.");
    }

    private void runForm() throws LowpanException {
        LowpanProvision provision = getProvisionFromArgs(false);
        if (provision.getLowpanCredential() != null) {
            System.out.println("Forming " + provision.getLowpanIdentity() + " with provided credential");
        } else {
            System.out.println("Forming " + provision.getLowpanIdentity());
        }
        getLowpanInterface().form(provision);
        System.out.println("Formed.");
    }

    private void runStatus() throws LowpanException, RemoteException {
        LowpanInterface iface = getLowpanInterface();
        StringBuffer sb = new StringBuffer();
        sb.append(iface.getName()).append("\t").append(iface.getState());
        if (!iface.isEnabled()) {
            sb.append(" DISABLED");
        } else if (iface.getState() != "fault") {
            sb.append(" (" + iface.getRole() + ")");
            if (iface.isUp()) {
                sb.append(" UP");
            }
            if (iface.isConnected()) {
                sb.append(" CONNECTED");
            }
            if (iface.isCommissioned()) {
                sb.append(" COMMISSIONED");
                LowpanIdentity identity = getLowpanInterface().getLowpanIdentity();
                if (identity != null) {
                    sb.append("\n\t").append(identity);
                }
            }
            if (iface.isUp()) {
                for (LinkAddress addr : iface.getLinkAddresses()) {
                    sb.append("\n\t").append(addr);
                }
            }
        }
        sb.append("\n");
        System.out.println(sb.toString());
    }

    private void runShowCredential() throws LowpanException, RemoteException {
        LowpanInterface iface = getLowpanInterface();
        boolean raw = false;
        while (true) {
            String arg = nextArg();
            if (arg == null) {
                break;
            } else if (arg.equals("--raw") || arg.equals("-r")) {
                raw = true;
            } else {
                throwArgumentError("Unrecognized argument \"" + arg + "\"");
            }
        }
        LowpanCredential credential = iface.getLowpanCredential();
        if (raw) {
            System.out.println(HexDump.toHexString(credential.getMasterKey()));
        } else {
            System.out.println(iface.getName() + "\t" + credential.toSensitiveString());
        }
    }

    private void runListInterfaces() {
        for (String name : getLowpanManager().getInterfaceList()) {
            System.out.println(name);
        }
    }

    private void runNetScan() throws LowpanException, InterruptedException {
        LowpanScanner scanner = getLowpanInterface().createScanner();
        while (true) {
            String arg = nextArg();
            if (arg == null) {
                break;
            } else if (arg.equals("-c") || arg.equals("--channel")) {
                scanner.addChannel(Integer.decode(nextArgRequired()).intValue());
            } else {
                throwArgumentError("Unrecognized argument \"" + arg + "\"");
            }
        }
        final Semaphore semaphore = new Semaphore(1);
        scanner.setCallback(new Callback() {
            public void onNetScanBeacon(LowpanBeaconInfo beacon) {
                System.out.println(beacon.toString());
            }

            public void onScanFinished() {
                semaphore.release();
            }
        });
        semaphore.acquire();
        scanner.startNetScan();
        if (semaphore.tryAcquire(1, 60, TimeUnit.SECONDS)) {
            semaphore.release();
        } else {
            throwCommandError("Timeout while waiting for scan to complete.");
        }
    }

    private void runEnergyScan() throws LowpanException, InterruptedException {
        LowpanScanner scanner = getLowpanInterface().createScanner();
        while (true) {
            String arg = nextArg();
            if (arg == null) {
                break;
            } else if (arg.equals("-c") || arg.equals("--channel")) {
                scanner.addChannel(Integer.decode(nextArgRequired()).intValue());
            } else {
                throwArgumentError("Unrecognized argument \"" + arg + "\"");
            }
        }
        final Semaphore semaphore = new Semaphore(1);
        scanner.setCallback(new Callback() {
            public void onEnergyScanResult(LowpanEnergyScanResult result) {
                System.out.println(result.toString());
            }

            public void onScanFinished() {
                semaphore.release();
            }
        });
        semaphore.acquire();
        scanner.startEnergyScan();
        if (semaphore.tryAcquire(1, 60, TimeUnit.SECONDS)) {
            semaphore.release();
        } else {
            throwCommandError("Timeout while waiting for scan to complete.");
        }
    }
}
