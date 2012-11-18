/*
 * Copyright (C) 2011 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.android.server.connectivity;

<<<<<<< HEAD
import android.app.Notification;
import android.app.NotificationManager;
=======
import static android.Manifest.permission.BIND_VPN_SERVICE;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
<<<<<<< HEAD
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.net.INetworkManagementEventObserver;
import android.net.LocalSocket;
import android.net.LocalSocketAddress;
import android.os.Binder;
import android.os.FileUtils;
import android.os.IBinder;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.Process;
import android.os.SystemClock;
import android.os.SystemProperties;
import android.util.Log;
=======
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.net.BaseNetworkStateTracker;
import android.net.ConnectivityManager;
import android.net.INetworkManagementEventObserver;
import android.net.LinkProperties;
import android.net.LocalSocket;
import android.net.LocalSocketAddress;
import android.net.NetworkInfo;
import android.net.RouteInfo;
import android.net.NetworkInfo.DetailedState;
import android.os.Binder;
import android.os.FileUtils;
import android.os.IBinder;
import android.os.INetworkManagementService;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.Process;
import android.os.RemoteException;
import android.os.SystemClock;
import android.os.SystemService;
import android.os.UserHandle;
import android.security.Credentials;
import android.security.KeyStore;
import android.util.Log;
import android.widget.Toast;
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a

import com.android.internal.R;
import com.android.internal.net.LegacyVpnInfo;
import com.android.internal.net.VpnConfig;
<<<<<<< HEAD
import com.android.server.ConnectivityService.VpnCallback;
=======
import com.android.internal.net.VpnProfile;
import com.android.internal.util.Preconditions;
import com.android.server.ConnectivityService.VpnCallback;
import com.android.server.net.BaseNetworkObserver;
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
<<<<<<< HEAD
import java.nio.charset.Charsets;
import java.util.Arrays;

/**
 * @hide
 */
public class Vpn extends INetworkManagementEventObserver.Stub {

    private final static String TAG = "Vpn";

    private final static String BIND_VPN_SERVICE =
            android.Manifest.permission.BIND_VPN_SERVICE;

    private final Context mContext;
=======
import java.net.Inet4Address;
import java.net.InetAddress;
import java.nio.charset.Charsets;
import java.util.Arrays;

import libcore.io.IoUtils;

/**
 * @hide
 */
public class Vpn extends BaseNetworkStateTracker {
    private static final String TAG = "Vpn";
    private static final boolean LOGD = true;
    
    // TODO: create separate trackers for each unique VPN to support
    // automated reconnection

>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
    private final VpnCallback mCallback;

    private String mPackage = VpnConfig.LEGACY_VPN;
    private String mInterface;
    private Connection mConnection;
    private LegacyVpnRunner mLegacyVpnRunner;
<<<<<<< HEAD

    public Vpn(Context context, VpnCallback callback) {
        mContext = context;
        mCallback = callback;
=======
    private PendingIntent mStatusIntent;
    private boolean mEnableNotif = true;

    public Vpn(Context context, VpnCallback callback, INetworkManagementService netService) {
        // TODO: create dedicated TYPE_VPN network type
        super(ConnectivityManager.TYPE_DUMMY);
        mContext = context;
        mCallback = callback;

        try {
            netService.registerObserver(mObserver);
        } catch (RemoteException e) {
            Log.wtf(TAG, "Problem registering observer", e);
        }
    }

    public void setEnableNotifications(boolean enableNotif) {
        mEnableNotif = enableNotif;
    }

    @Override
    protected void startMonitoringInternal() {
        // Ignored; events are sent through callbacks for now
    }

    @Override
    public boolean teardown() {
        // TODO: finish migration to unique tracker for each VPN
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean reconnect() {
        // TODO: finish migration to unique tracker for each VPN
        throw new UnsupportedOperationException();
    }

    @Override
    public String getTcpBufferSizesPropName() {
        return PROP_TCP_BUFFER_UNKNOWN;
    }

    /**
     * Update current state, dispaching event to listeners.
     */
    private void updateState(DetailedState detailedState, String reason) {
        if (LOGD) Log.d(TAG, "setting state=" + detailedState + ", reason=" + reason);
        mNetworkInfo.setDetailedState(detailedState, reason, null);
        mCallback.onStateChanged(new NetworkInfo(mNetworkInfo));
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
    }

    /**
     * Prepare for a VPN application. This method is designed to solve
     * race conditions. It first compares the current prepared package
     * with {@code oldPackage}. If they are the same, the prepared
     * package is revoked and replaced with {@code newPackage}. If
     * {@code oldPackage} is {@code null}, the comparison is omitted.
     * If {@code newPackage} is the same package or {@code null}, the
     * revocation is omitted. This method returns {@code true} if the
     * operation is succeeded.
     *
     * Legacy VPN is handled specially since it is not a real package.
     * It uses {@link VpnConfig#LEGACY_VPN} as its package name, and
     * it can be revoked by itself.
     *
     * @param oldPackage The package name of the old VPN application.
     * @param newPackage The package name of the new VPN application.
     * @return true if the operation is succeeded.
     */
    public synchronized boolean prepare(String oldPackage, String newPackage) {
        // Return false if the package does not match.
        if (oldPackage != null && !oldPackage.equals(mPackage)) {
            return false;
        }

        // Return true if we do not need to revoke.
        if (newPackage == null ||
                (newPackage.equals(mPackage) && !newPackage.equals(VpnConfig.LEGACY_VPN))) {
            return true;
        }

        // Check if the caller is authorized.
        enforceControlPermission();

        // Reset the interface and hide the notification.
        if (mInterface != null) {
            jniReset(mInterface);
<<<<<<< HEAD
            long identity = Binder.clearCallingIdentity();
            mCallback.restore();
            hideNotification();
            Binder.restoreCallingIdentity(identity);
=======
            final long token = Binder.clearCallingIdentity();
            try {
                mCallback.restore();
                hideNotification();
            } finally {
                Binder.restoreCallingIdentity(token);
            }
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
            mInterface = null;
        }

        // Revoke the connection or stop LegacyVpnRunner.
        if (mConnection != null) {
            try {
                mConnection.mService.transact(IBinder.LAST_CALL_TRANSACTION,
                        Parcel.obtain(), null, IBinder.FLAG_ONEWAY);
            } catch (Exception e) {
                // ignore
            }
            mContext.unbindService(mConnection);
            mConnection = null;
        } else if (mLegacyVpnRunner != null) {
            mLegacyVpnRunner.exit();
            mLegacyVpnRunner = null;
        }

        Log.i(TAG, "Switched from " + mPackage + " to " + newPackage);
        mPackage = newPackage;
<<<<<<< HEAD
=======
        updateState(DetailedState.IDLE, "prepare");
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
        return true;
    }

    /**
     * Protect a socket from routing changes by binding it to the given
     * interface. The socket is NOT closed by this method.
     *
     * @param socket The socket to be bound.
<<<<<<< HEAD
     * @param name The name of the interface.
=======
     * @param interfaze The name of the interface.
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
     */
    public void protect(ParcelFileDescriptor socket, String interfaze) throws Exception {
        PackageManager pm = mContext.getPackageManager();
        ApplicationInfo app = pm.getApplicationInfo(mPackage, 0);
        if (Binder.getCallingUid() != app.uid) {
            throw new SecurityException("Unauthorized Caller");
        }
        jniProtect(socket.getFd(), interfaze);
    }

    /**
     * Establish a VPN network and return the file descriptor of the VPN
     * interface. This methods returns {@code null} if the application is
     * revoked or not prepared.
     *
     * @param config The parameters to configure the network.
     * @return The file descriptor of the VPN interface.
     */
    public synchronized ParcelFileDescriptor establish(VpnConfig config) {
        // Check if the caller is already prepared.
        PackageManager pm = mContext.getPackageManager();
        ApplicationInfo app = null;
        try {
            app = pm.getApplicationInfo(mPackage, 0);
        } catch (Exception e) {
            return null;
        }
        if (Binder.getCallingUid() != app.uid) {
            return null;
        }

        // Check if the service is properly declared.
        Intent intent = new Intent(VpnConfig.SERVICE_INTERFACE);
        intent.setClassName(mPackage, config.user);
        ResolveInfo info = pm.resolveService(intent, 0);
        if (info == null) {
            throw new SecurityException("Cannot find " + config.user);
        }
        if (!BIND_VPN_SERVICE.equals(info.serviceInfo.permission)) {
            throw new SecurityException(config.user + " does not require " + BIND_VPN_SERVICE);
        }

        // Load the label.
        String label = app.loadLabel(pm).toString();

        // Load the icon and convert it into a bitmap.
        Drawable icon = app.loadIcon(pm);
        Bitmap bitmap = null;
        if (icon.getIntrinsicWidth() > 0 && icon.getIntrinsicHeight() > 0) {
            int width = mContext.getResources().getDimensionPixelSize(
                    android.R.dimen.notification_large_icon_width);
            int height = mContext.getResources().getDimensionPixelSize(
                    android.R.dimen.notification_large_icon_height);
            icon.setBounds(0, 0, width, height);
            bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            Canvas c = new Canvas(bitmap);
            icon.draw(c);
            c.setBitmap(null);
        }

        // Configure the interface. Abort if any of these steps fails.
        ParcelFileDescriptor tun = ParcelFileDescriptor.adoptFd(jniCreate(config.mtu));
        try {
<<<<<<< HEAD
=======
            updateState(DetailedState.CONNECTING, "establish");
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
            String interfaze = jniGetName(tun.getFd());
            if (jniSetAddresses(interfaze, config.addresses) < 1) {
                throw new IllegalArgumentException("At least one address must be specified");
            }
            if (config.routes != null) {
                jniSetRoutes(interfaze, config.routes);
            }
            Connection connection = new Connection();
            if (!mContext.bindService(intent, connection, Context.BIND_AUTO_CREATE)) {
                throw new IllegalStateException("Cannot bind " + config.user);
            }
            if (mConnection != null) {
                mContext.unbindService(mConnection);
            }
            if (mInterface != null && !mInterface.equals(interfaze)) {
                jniReset(mInterface);
            }
            mConnection = connection;
            mInterface = interfaze;
        } catch (RuntimeException e) {
<<<<<<< HEAD
            try {
                tun.close();
            } catch (Exception ex) {
                // ignore
            }
=======
            updateState(DetailedState.FAILED, "establish");
            IoUtils.closeQuietly(tun);
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
            throw e;
        }
        Log.i(TAG, "Established by " + config.user + " on " + mInterface);

        // Fill more values.
        config.user = mPackage;
        config.interfaze = mInterface;

        // Override DNS servers and show the notification.
<<<<<<< HEAD
        long identity = Binder.clearCallingIdentity();
        mCallback.override(config.dnsServers, config.searchDomains);
        showNotification(config, label, bitmap);
        Binder.restoreCallingIdentity(identity);
        return tun;
    }

    // INetworkManagementEventObserver.Stub
    @Override
    public void interfaceAdded(String interfaze) {
    }

    // INetworkManagementEventObserver.Stub
    @Override
    public synchronized void interfaceStatusChanged(String interfaze, boolean up) {
        if (!up && mLegacyVpnRunner != null) {
            mLegacyVpnRunner.check(interfaze);
        }
    }

    // INetworkManagementEventObserver.Stub
    @Override
    public void interfaceLinkStateChanged(String interfaze, boolean up) {
    }

    // INetworkManagementEventObserver.Stub
    @Override
    public synchronized void interfaceRemoved(String interfaze) {
        if (interfaze.equals(mInterface) && jniCheck(interfaze) == 0) {
            long identity = Binder.clearCallingIdentity();
            mCallback.restore();
            hideNotification();
            Binder.restoreCallingIdentity(identity);
            mInterface = null;
            if (mConnection != null) {
                mContext.unbindService(mConnection);
                mConnection = null;
            } else if (mLegacyVpnRunner != null) {
                mLegacyVpnRunner.exit();
                mLegacyVpnRunner = null;
            }
        }
    }

    // INetworkManagementEventObserver.Stub
    @Override
    public void limitReached(String limit, String interfaze) {
    }
=======
        final long token = Binder.clearCallingIdentity();
        try {
            mCallback.override(config.dnsServers, config.searchDomains);
            showNotification(config, label, bitmap);
        } finally {
            Binder.restoreCallingIdentity(token);
        }
        // TODO: ensure that contract class eventually marks as connected
        updateState(DetailedState.AUTHENTICATING, "establish");
        return tun;
    }

    @Deprecated
    public synchronized void interfaceStatusChanged(String iface, boolean up) {
        try {
            mObserver.interfaceStatusChanged(iface, up);
        } catch (RemoteException e) {
            // ignored; target is local
        }
    }

    private INetworkManagementEventObserver mObserver = new BaseNetworkObserver() {
        @Override
        public void interfaceStatusChanged(String interfaze, boolean up) {
            synchronized (Vpn.this) {
                if (!up && mLegacyVpnRunner != null) {
                    mLegacyVpnRunner.check(interfaze);
                }
            }
        }

        @Override
        public void interfaceRemoved(String interfaze) {
            synchronized (Vpn.this) {
                if (interfaze.equals(mInterface) && jniCheck(interfaze) == 0) {
                    final long token = Binder.clearCallingIdentity();
                    try {
                        mCallback.restore();
                        hideNotification();
                    } finally {
                        Binder.restoreCallingIdentity(token);
                    }
                    mInterface = null;
                    if (mConnection != null) {
                        mContext.unbindService(mConnection);
                        mConnection = null;
                        updateState(DetailedState.DISCONNECTED, "interfaceRemoved");
                    } else if (mLegacyVpnRunner != null) {
                        mLegacyVpnRunner.exit();
                        mLegacyVpnRunner = null;
                    }
                }
            }
        }
    };
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a

    private void enforceControlPermission() {
        // System user is allowed to control VPN.
        if (Binder.getCallingUid() == Process.SYSTEM_UID) {
            return;
        }

        try {
            // System dialogs are also allowed to control VPN.
            PackageManager pm = mContext.getPackageManager();
            ApplicationInfo app = pm.getApplicationInfo(VpnConfig.DIALOGS_PACKAGE, 0);
            if (Binder.getCallingUid() == app.uid) {
                return;
            }
        } catch (Exception e) {
            // ignore
        }

        throw new SecurityException("Unauthorized Caller");
    }

    private class Connection implements ServiceConnection {
        private IBinder mService;

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mService = service;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mService = null;
        }
    }

    private void showNotification(VpnConfig config, String label, Bitmap icon) {
<<<<<<< HEAD
=======
        if (!mEnableNotif) return;
        mStatusIntent = VpnConfig.getIntentForStatusPanel(mContext, config);

>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
        NotificationManager nm = (NotificationManager)
                mContext.getSystemService(Context.NOTIFICATION_SERVICE);

        if (nm != null) {
            String title = (label == null) ? mContext.getString(R.string.vpn_title) :
                    mContext.getString(R.string.vpn_title_long, label);
            String text = (config.session == null) ? mContext.getString(R.string.vpn_text) :
                    mContext.getString(R.string.vpn_text_long, config.session);
            config.startTime = SystemClock.elapsedRealtime();

            Notification notification = new Notification.Builder(mContext)
                    .setSmallIcon(R.drawable.vpn_connected)
                    .setLargeIcon(icon)
                    .setContentTitle(title)
                    .setContentText(text)
<<<<<<< HEAD
                    .setContentIntent(VpnConfig.getIntentForStatusPanel(mContext, config))
                    .setDefaults(0)
                    .setOngoing(true)
                    .getNotification();
            nm.notify(R.drawable.vpn_connected, notification);
=======
                    .setContentIntent(mStatusIntent)
                    .setDefaults(0)
                    .setOngoing(true)
                    .build();
            nm.notifyAsUser(null, R.drawable.vpn_connected, notification, UserHandle.ALL);
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
        }
    }

    private void hideNotification() {
<<<<<<< HEAD
=======
        if (!mEnableNotif) return;
        mStatusIntent = null;

>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
        NotificationManager nm = (NotificationManager)
                mContext.getSystemService(Context.NOTIFICATION_SERVICE);

        if (nm != null) {
<<<<<<< HEAD
            nm.cancel(R.drawable.vpn_connected);
=======
            nm.cancelAsUser(null, R.drawable.vpn_connected, UserHandle.ALL);
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
        }
    }

    private native int jniCreate(int mtu);
    private native String jniGetName(int tun);
    private native int jniSetAddresses(String interfaze, String addresses);
    private native int jniSetRoutes(String interfaze, String routes);
    private native void jniReset(String interfaze);
    private native int jniCheck(String interfaze);
    private native void jniProtect(int socket, String interfaze);

<<<<<<< HEAD
    /**
     * Start legacy VPN. This method stops the daemons and restart them
     * if arguments are not null. Heavy things are offloaded to another
     * thread, so callers will not be blocked for a long time.
     *
     * @param config The parameters to configure the network.
     * @param raoocn The arguments to be passed to racoon.
     * @param mtpd The arguments to be passed to mtpd.
     */
    public synchronized void startLegacyVpn(VpnConfig config, String[] racoon, String[] mtpd) {
        // Prepare for the new request. This also checks the caller.
        prepare(null, VpnConfig.LEGACY_VPN);
=======
    private static String findLegacyVpnGateway(LinkProperties prop) {
        for (RouteInfo route : prop.getRoutes()) {
            // Currently legacy VPN only works on IPv4.
            if (route.isDefaultRoute() && route.getGateway() instanceof Inet4Address) {
                return route.getGateway().getHostAddress();
            }
        }

        throw new IllegalStateException("Unable to find suitable gateway");
    }

    /**
     * Start legacy VPN, controlling native daemons as needed. Creates a
     * secondary thread to perform connection work, returning quickly.
     */
    public void startLegacyVpn(VpnProfile profile, KeyStore keyStore, LinkProperties egress) {
        if (keyStore.state() != KeyStore.State.UNLOCKED) {
            throw new IllegalStateException("KeyStore isn't unlocked");
        }

        final String iface = egress.getInterfaceName();
        final String gateway = findLegacyVpnGateway(egress);

        // Load certificates.
        String privateKey = "";
        String userCert = "";
        String caCert = "";
        String serverCert = "";
        if (!profile.ipsecUserCert.isEmpty()) {
            privateKey = Credentials.USER_PRIVATE_KEY + profile.ipsecUserCert;
            byte[] value = keyStore.get(Credentials.USER_CERTIFICATE + profile.ipsecUserCert);
            userCert = (value == null) ? null : new String(value, Charsets.UTF_8);
        }
        if (!profile.ipsecCaCert.isEmpty()) {
            byte[] value = keyStore.get(Credentials.CA_CERTIFICATE + profile.ipsecCaCert);
            caCert = (value == null) ? null : new String(value, Charsets.UTF_8);
        }
        if (!profile.ipsecServerCert.isEmpty()) {
            byte[] value = keyStore.get(Credentials.USER_CERTIFICATE + profile.ipsecServerCert);
            serverCert = (value == null) ? null : new String(value, Charsets.UTF_8);
        }
        if (privateKey == null || userCert == null || caCert == null || serverCert == null) {
            throw new IllegalStateException("Cannot load credentials");
        }

        // Prepare arguments for racoon.
        String[] racoon = null;
        switch (profile.type) {
            case VpnProfile.TYPE_L2TP_IPSEC_PSK:
                racoon = new String[] {
                    iface, profile.server, "udppsk", profile.ipsecIdentifier,
                    profile.ipsecSecret, "1701",
                };
                break;
            case VpnProfile.TYPE_L2TP_IPSEC_RSA:
                racoon = new String[] {
                    iface, profile.server, "udprsa", privateKey, userCert,
                    caCert, serverCert, "1701",
                };
                break;
            case VpnProfile.TYPE_IPSEC_XAUTH_PSK:
                racoon = new String[] {
                    iface, profile.server, "xauthpsk", profile.ipsecIdentifier,
                    profile.ipsecSecret, profile.username, profile.password, "", gateway,
                };
                break;
            case VpnProfile.TYPE_IPSEC_XAUTH_RSA:
                racoon = new String[] {
                    iface, profile.server, "xauthrsa", privateKey, userCert,
                    caCert, serverCert, profile.username, profile.password, "", gateway,
                };
                break;
            case VpnProfile.TYPE_IPSEC_HYBRID_RSA:
                racoon = new String[] {
                    iface, profile.server, "hybridrsa",
                    caCert, serverCert, profile.username, profile.password, "", gateway,
                };
                break;
        }

        // Prepare arguments for mtpd.
        String[] mtpd = null;
        switch (profile.type) {
            case VpnProfile.TYPE_PPTP:
                mtpd = new String[] {
                    iface, "pptp", profile.server, "1723",
                    "name", profile.username, "password", profile.password,
                    "linkname", "vpn", "refuse-eap", "nodefaultroute",
                    "usepeerdns", "idle", "1800", "mtu", "1400", "mru", "1400",
                    (profile.mppe ? "+mppe" : "nomppe"),
                };
                break;
            case VpnProfile.TYPE_L2TP_IPSEC_PSK:
            case VpnProfile.TYPE_L2TP_IPSEC_RSA:
                mtpd = new String[] {
                    iface, "l2tp", profile.server, "1701", profile.l2tpSecret,
                    "name", profile.username, "password", profile.password,
                    "linkname", "vpn", "refuse-eap", "nodefaultroute",
                    "usepeerdns", "idle", "1800", "mtu", "1400", "mru", "1400",
                };
                break;
        }

        VpnConfig config = new VpnConfig();
        config.legacy = true;
        config.user = profile.key;
        config.interfaze = iface;
        config.session = profile.name;
        config.routes = profile.routes;
        if (!profile.dnsServers.isEmpty()) {
            config.dnsServers = Arrays.asList(profile.dnsServers.split(" +"));
        }
        if (!profile.searchDomains.isEmpty()) {
            config.searchDomains = Arrays.asList(profile.searchDomains.split(" +"));
        }

        startLegacyVpn(config, racoon, mtpd);
    }

    private synchronized void startLegacyVpn(VpnConfig config, String[] racoon, String[] mtpd) {
        stopLegacyVpn();

        // Prepare for the new request. This also checks the caller.
        prepare(null, VpnConfig.LEGACY_VPN);
        updateState(DetailedState.CONNECTING, "startLegacyVpn");
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a

        // Start a new LegacyVpnRunner and we are done!
        mLegacyVpnRunner = new LegacyVpnRunner(config, racoon, mtpd);
        mLegacyVpnRunner.start();
    }

<<<<<<< HEAD
=======
    public synchronized void stopLegacyVpn() {
        if (mLegacyVpnRunner != null) {
            mLegacyVpnRunner.exit();
            mLegacyVpnRunner = null;

            synchronized (LegacyVpnRunner.TAG) {
                // wait for old thread to completely finish before spinning up
                // new instance, otherwise state updates can be out of order.
            }
        }
    }

>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
    /**
     * Return the information of the current ongoing legacy VPN.
     */
    public synchronized LegacyVpnInfo getLegacyVpnInfo() {
        // Check if the caller is authorized.
        enforceControlPermission();
<<<<<<< HEAD
        return (mLegacyVpnRunner == null) ? null : mLegacyVpnRunner.getInfo();
=======
        if (mLegacyVpnRunner == null) return null;

        final LegacyVpnInfo info = new LegacyVpnInfo();
        info.key = mLegacyVpnRunner.mConfig.user;
        info.state = LegacyVpnInfo.stateFromNetworkInfo(mNetworkInfo);
        if (mNetworkInfo.isConnected()) {
            info.intent = mStatusIntent;
        }
        return info;
    }

    public VpnConfig getLegacyVpnConfig() {
        if (mLegacyVpnRunner != null) {
            return mLegacyVpnRunner.mConfig;
        } else {
            return null;
        }
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
    }

    /**
     * Bringing up a VPN connection takes time, and that is all this thread
     * does. Here we have plenty of time. The only thing we need to take
     * care of is responding to interruptions as soon as possible. Otherwise
     * requests will be piled up. This can be done in a Handler as a state
     * machine, but it is much easier to read in the current form.
     */
    private class LegacyVpnRunner extends Thread {
        private static final String TAG = "LegacyVpnRunner";

        private final VpnConfig mConfig;
        private final String[] mDaemons;
        private final String[][] mArguments;
        private final LocalSocket[] mSockets;
        private final String mOuterInterface;
<<<<<<< HEAD
        private final LegacyVpnInfo mInfo;
=======
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a

        private long mTimer = -1;

        public LegacyVpnRunner(VpnConfig config, String[] racoon, String[] mtpd) {
            super(TAG);
            mConfig = config;
            mDaemons = new String[] {"racoon", "mtpd"};
<<<<<<< HEAD
            mArguments = new String[][] {racoon, mtpd};
            mSockets = new LocalSocket[mDaemons.length];
            mInfo = new LegacyVpnInfo();

            // This is the interface which VPN is running on.
            mOuterInterface = mConfig.interfaze;

            // Legacy VPN is not a real package, so we use it to carry the key.
            mInfo.key = mConfig.user;
            mConfig.user = VpnConfig.LEGACY_VPN;
=======
            // TODO: clear arguments from memory once launched
            mArguments = new String[][] {racoon, mtpd};
            mSockets = new LocalSocket[mDaemons.length];

            // This is the interface which VPN is running on,
            // mConfig.interfaze will change to point to OUR
            // internal interface soon. TODO - add inner/outer to mconfig
            mOuterInterface = mConfig.interfaze;
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
        }

        public void check(String interfaze) {
            if (interfaze.equals(mOuterInterface)) {
                Log.i(TAG, "Legacy VPN is going down with " + interfaze);
                exit();
            }
        }

        public void exit() {
            // We assume that everything is reset after stopping the daemons.
            interrupt();
            for (LocalSocket socket : mSockets) {
<<<<<<< HEAD
                try {
                    socket.close();
                } catch (Exception e) {
                    // ignore
                }
            }
        }

        public LegacyVpnInfo getInfo() {
            // Update the info when VPN is disconnected.
            if (mInfo.state == LegacyVpnInfo.STATE_CONNECTED && mInterface == null) {
                mInfo.state = LegacyVpnInfo.STATE_DISCONNECTED;
                mInfo.intent = null;
            }
            return mInfo;
=======
                IoUtils.closeQuietly(socket);
            }
            updateState(DetailedState.DISCONNECTED, "exit");
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
        }

        @Override
        public void run() {
            // Wait for the previous thread since it has been interrupted.
            Log.v(TAG, "Waiting");
            synchronized (TAG) {
                Log.v(TAG, "Executing");
                execute();
<<<<<<< HEAD
=======
                monitorDaemons();
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
            }
        }

        private void checkpoint(boolean yield) throws InterruptedException {
            long now = SystemClock.elapsedRealtime();
            if (mTimer == -1) {
                mTimer = now;
                Thread.sleep(1);
            } else if (now - mTimer <= 60000) {
                Thread.sleep(yield ? 200 : 1);
            } else {
<<<<<<< HEAD
                mInfo.state = LegacyVpnInfo.STATE_TIMEOUT;
=======
                updateState(DetailedState.FAILED, "checkpoint");
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
                throw new IllegalStateException("Time is up");
            }
        }

        private void execute() {
            // Catch all exceptions so we can clean up few things.
<<<<<<< HEAD
            try {
                // Initialize the timer.
                checkpoint(false);
                mInfo.state = LegacyVpnInfo.STATE_INITIALIZING;

                // Wait for the daemons to stop.
                for (String daemon : mDaemons) {
                    String key = "init.svc." + daemon;
                    while (!"stopped".equals(SystemProperties.get(key, "stopped"))) {
=======
            boolean initFinished = false;
            try {
                // Initialize the timer.
                checkpoint(false);

                // Wait for the daemons to stop.
                for (String daemon : mDaemons) {
                    while (!SystemService.isStopped(daemon)) {
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
                        checkpoint(true);
                    }
                }

                // Clear the previous state.
                File state = new File("/data/misc/vpn/state");
                state.delete();
                if (state.exists()) {
                    throw new IllegalStateException("Cannot delete the state");
                }
                new File("/data/misc/vpn/abort").delete();
<<<<<<< HEAD
=======
                initFinished = true;
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a

                // Check if we need to restart any of the daemons.
                boolean restart = false;
                for (String[] arguments : mArguments) {
                    restart = restart || (arguments != null);
                }
                if (!restart) {
<<<<<<< HEAD
                    mInfo.state = LegacyVpnInfo.STATE_DISCONNECTED;
                    return;
                }
                mInfo.state = LegacyVpnInfo.STATE_CONNECTING;
=======
                    updateState(DetailedState.DISCONNECTED, "execute");
                    return;
                }
                updateState(DetailedState.CONNECTING, "execute");
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a

                // Start the daemon with arguments.
                for (int i = 0; i < mDaemons.length; ++i) {
                    String[] arguments = mArguments[i];
                    if (arguments == null) {
                        continue;
                    }

                    // Start the daemon.
                    String daemon = mDaemons[i];
<<<<<<< HEAD
                    SystemProperties.set("ctl.start", daemon);

                    // Wait for the daemon to start.
                    String key = "init.svc." + daemon;
                    while (!"running".equals(SystemProperties.get(key))) {
=======
                    SystemService.start(daemon);

                    // Wait for the daemon to start.
                    while (!SystemService.isRunning(daemon)) {
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
                        checkpoint(true);
                    }

                    // Create the control socket.
                    mSockets[i] = new LocalSocket();
                    LocalSocketAddress address = new LocalSocketAddress(
                            daemon, LocalSocketAddress.Namespace.RESERVED);

                    // Wait for the socket to connect.
                    while (true) {
                        try {
                            mSockets[i].connect(address);
                            break;
                        } catch (Exception e) {
                            // ignore
                        }
                        checkpoint(true);
                    }
                    mSockets[i].setSoTimeout(500);

                    // Send over the arguments.
                    OutputStream out = mSockets[i].getOutputStream();
                    for (String argument : arguments) {
                        byte[] bytes = argument.getBytes(Charsets.UTF_8);
                        if (bytes.length >= 0xFFFF) {
                            throw new IllegalArgumentException("Argument is too large");
                        }
                        out.write(bytes.length >> 8);
                        out.write(bytes.length);
                        out.write(bytes);
                        checkpoint(false);
                    }
                    out.write(0xFF);
                    out.write(0xFF);
                    out.flush();

                    // Wait for End-of-File.
                    InputStream in = mSockets[i].getInputStream();
                    while (true) {
                        try {
                            if (in.read() == -1) {
                                break;
                            }
                        } catch (Exception e) {
                            // ignore
                        }
                        checkpoint(true);
                    }
                }

                // Wait for the daemons to create the new state.
                while (!state.exists()) {
                    // Check if a running daemon is dead.
                    for (int i = 0; i < mDaemons.length; ++i) {
                        String daemon = mDaemons[i];
<<<<<<< HEAD
                        if (mArguments[i] != null && !"running".equals(
                                SystemProperties.get("init.svc." + daemon))) {
=======
                        if (mArguments[i] != null && !SystemService.isRunning(daemon)) {
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
                            throw new IllegalStateException(daemon + " is dead");
                        }
                    }
                    checkpoint(true);
                }

                // Now we are connected. Read and parse the new state.
                String[] parameters = FileUtils.readTextFile(state, 0, null).split("\n", -1);
                if (parameters.length != 6) {
                    throw new IllegalStateException("Cannot parse the state");
                }

                // Set the interface and the addresses in the config.
                mConfig.interfaze = parameters[0].trim();
                mConfig.addresses = parameters[1].trim();

                // Set the routes if they are not set in the config.
                if (mConfig.routes == null || mConfig.routes.isEmpty()) {
                    mConfig.routes = parameters[2].trim();
                }

                // Set the DNS servers if they are not set in the config.
                if (mConfig.dnsServers == null || mConfig.dnsServers.size() == 0) {
                    String dnsServers = parameters[3].trim();
                    if (!dnsServers.isEmpty()) {
                        mConfig.dnsServers = Arrays.asList(dnsServers.split(" "));
                    }
                }

                // Set the search domains if they are not set in the config.
                if (mConfig.searchDomains == null || mConfig.searchDomains.size() == 0) {
                    String searchDomains = parameters[4].trim();
                    if (!searchDomains.isEmpty()) {
                        mConfig.searchDomains = Arrays.asList(searchDomains.split(" "));
                    }
                }

                // Set the routes.
                jniSetRoutes(mConfig.interfaze, mConfig.routes);

                // Here is the last step and it must be done synchronously.
                synchronized (Vpn.this) {
                    // Check if the thread is interrupted while we are waiting.
                    checkpoint(false);

                    // Check if the interface is gone while we are waiting.
                    if (jniCheck(mConfig.interfaze) == 0) {
                        throw new IllegalStateException(mConfig.interfaze + " is gone");
                    }

                    // Now INetworkManagementEventObserver is watching our back.
                    mInterface = mConfig.interfaze;
                    mCallback.override(mConfig.dnsServers, mConfig.searchDomains);
                    showNotification(mConfig, null, null);

                    Log.i(TAG, "Connected!");
<<<<<<< HEAD
                    mInfo.state = LegacyVpnInfo.STATE_CONNECTED;
                    mInfo.intent = VpnConfig.getIntentForStatusPanel(mContext, null);
=======
                    updateState(DetailedState.CONNECTED, "execute");
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
                }
            } catch (Exception e) {
                Log.i(TAG, "Aborting", e);
                exit();
            } finally {
                // Kill the daemons if they fail to stop.
<<<<<<< HEAD
                if (mInfo.state == LegacyVpnInfo.STATE_INITIALIZING) {
                    for (String daemon : mDaemons) {
                        SystemProperties.set("ctl.stop", daemon);
=======
                if (!initFinished) {
                    for (String daemon : mDaemons) {
                        SystemService.stop(daemon);
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
                    }
                }

                // Do not leave an unstable state.
<<<<<<< HEAD
                if (mInfo.state == LegacyVpnInfo.STATE_INITIALIZING ||
                        mInfo.state == LegacyVpnInfo.STATE_CONNECTING) {
                    mInfo.state = LegacyVpnInfo.STATE_FAILED;
                }
            }
        }
=======
                if (!initFinished || mNetworkInfo.getDetailedState() == DetailedState.CONNECTING) {
                    updateState(DetailedState.FAILED, "execute");
                }
            }
        }

        /**
         * Monitor the daemons we started, moving to disconnected state if the
         * underlying services fail.
         */
        private void monitorDaemons() {
            if (!mNetworkInfo.isConnected()) {
                return;
            }

            try {
                while (true) {
                    Thread.sleep(2000);
                    for (int i = 0; i < mDaemons.length; i++) {
                        if (mArguments[i] != null && SystemService.isStopped(mDaemons[i])) {
                            return;
                        }
                    }
                }
            } catch (InterruptedException e) {
                Log.d(TAG, "interrupted during monitorDaemons(); stopping services");
            } finally {
                for (String daemon : mDaemons) {
                    SystemService.stop(daemon);
                }

                updateState(DetailedState.DISCONNECTED, "babysit");
            }
        }
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
    }
}
