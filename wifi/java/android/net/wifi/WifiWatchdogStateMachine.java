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

package android.net.wifi;

<<<<<<< HEAD
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
=======
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
<<<<<<< HEAD
import android.content.res.Resources;
import android.database.ContentObserver;
import android.net.arp.ArpPeer;
import android.net.ConnectivityManager;
import android.net.LinkAddress;
import android.net.LinkProperties;
import android.net.NetworkInfo;
import android.net.RouteInfo;
import android.net.Uri;
import android.os.Message;
import android.os.SystemClock;
import android.os.SystemProperties;
import android.provider.Settings;
import android.provider.Settings.Secure;
import android.util.Log;
=======
import android.database.ContentObserver;
import android.net.ConnectivityManager;
import android.net.LinkProperties;
import android.net.NetworkInfo;
import android.net.wifi.RssiPacketCountInfo;
import android.os.Message;
import android.os.SystemClock;
import android.provider.Settings;
import android.provider.Settings.Secure;
import android.util.Log;
import android.util.LruCache;
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a

import com.android.internal.R;
import com.android.internal.util.AsyncChannel;
import com.android.internal.util.Protocol;
import com.android.internal.util.State;
import com.android.internal.util.StateMachine;

<<<<<<< HEAD
import java.io.IOException;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.URL;

/**
 * WifiWatchdogStateMachine monitors the connection to a Wi-Fi
 * network. After the framework notifies that it has connected to an
 * acccess point and is waiting for link to be verified, the watchdog
 * takes over and verifies if the link is good by doing ARP pings to
 * the gateway using {@link ArpPeer}.
 *
 * Upon successful verification, the watchdog notifies and continues
 * to monitor the link afterwards when the RSSI level falls below
 * a certain threshold.

 * When Wi-fi connects at L2 layer, the beacons from access point reach
 * the device and it can maintain a connection, but the application
 * connectivity can be flaky (due to bigger packet size exchange).
 *
 * We now monitor the quality of the last hop on
 * Wi-Fi using signal strength and ARP connectivity as indicators
 * to decide if the link is good enough to switch to Wi-Fi as the uplink.
 *
 * ARP pings are useful for link validation but can still get through
 * when the application traffic fails to go through and are thus not
 * the best indicator of real packet loss since they are tiny packets
 * (28 bytes) and have a much low chance of packet corruption than the
 * regular data packets.
 *
 * When signal strength and ARP are used together, it ends up working well in tests.
 * The goal is to switch to Wi-Fi after validating ARP transfer
 * and RSSI and then switching out of Wi-Fi when we hit a low
 * signal strength threshold and then waiting until the signal strength
 * improves and validating ARP transfer.
=======
import java.io.PrintWriter;
import java.text.DecimalFormat;

/**
 * WifiWatchdogStateMachine monitors the connection to a WiFi network. When WiFi
 * connects at L2 layer, the beacons from access point reach the device and it
 * can maintain a connection, but the application connectivity can be flaky (due
 * to bigger packet size exchange).
 * <p>
 * We now monitor the quality of the last hop on WiFi using packet loss ratio as
 * an indicator to decide if the link is good enough to switch to Wi-Fi as the
 * uplink.
 * <p>
 * When WiFi is connected, the WiFi watchdog keeps sampling the RSSI and the
 * instant packet loss, and record it as per-AP loss-to-rssi statistics. When
 * the instant packet loss is higher than a threshold, the WiFi watchdog sends a
 * poor link notification to avoid WiFi connection temporarily.
 * <p>
 * While WiFi is being avoided, the WiFi watchdog keep watching the RSSI to
 * bring the WiFi connection back. Once the RSSI is high enough to achieve a
 * lower packet loss, a good link detection is sent such that the WiFi
 * connection become available again.
 * <p>
 * BSSID roaming has been taken into account. When user is moving across
 * multiple APs, the WiFi watchdog will detect that and keep watching the
 * currently connected AP.
 * <p>
 * Power impact should be minimal since much of the measurement relies on
 * passive statistics already being tracked at the driver and the polling is
 * done when screen is turned on and the RSSI is in a certain range.
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
 *
 * @hide
 */
public class WifiWatchdogStateMachine extends StateMachine {

    /* STOPSHIP: Keep this configurable for debugging until ship */
    private static boolean DBG = false;
    private static final String TAG = "WifiWatchdogStateMachine";
<<<<<<< HEAD
    private static final String WALLED_GARDEN_NOTIFICATION_ID = "WifiWatchdog.walledgarden";

    /* RSSI Levels as used by notification icon
       Level 4  -55 <= RSSI
       Level 3  -66 <= RSSI < -55
       Level 2  -77 <= RSSI < -67
       Level 1  -88 <= RSSI < -78
       Level 0         RSSI < -88 */

    /* Wi-fi connection is monitored actively below this
       threshold */
    private static final int RSSI_LEVEL_MONITOR = 0;
    /* Rssi threshold is at level 0 (-88dBm) */
    private static final int RSSI_MONITOR_THRESHOLD = -88;
    /* Number of times RSSI is measured to be low before being avoided */
    private static final int RSSI_MONITOR_COUNT = 5;
    private int mRssiMonitorCount = 0;

    /* Avoid flapping. The interval is changed over time as long as we continue to avoid
     * under the max interval after which we reset the interval again */
    private static final int MIN_INTERVAL_AVOID_BSSID_MS[] = {0, 30 * 1000, 60 * 1000,
            5 * 60 * 1000, 30 * 60 * 1000};
    /* Index into the interval array MIN_INTERVAL_AVOID_BSSID_MS */
    private int mMinIntervalArrayIndex = 0;

    private long mLastBssidAvoidedTime;

    private int mCurrentSignalLevel;

    private static final long DEFAULT_ARP_CHECK_INTERVAL_MS = 2 * 60 * 1000;
    private static final long DEFAULT_RSSI_FETCH_INTERVAL_MS = 1000;
    private static final long DEFAULT_WALLED_GARDEN_INTERVAL_MS = 30 * 60 * 1000;

    private static final int DEFAULT_NUM_ARP_PINGS = 5;
    private static final int DEFAULT_MIN_ARP_RESPONSES = 1;

    private static final int DEFAULT_ARP_PING_TIMEOUT_MS = 100;

    // See http://go/clientsdns for usage approval
    private static final String DEFAULT_WALLED_GARDEN_URL =
            "http://clients3.google.com/generate_204";
    private static final int WALLED_GARDEN_SOCKET_TIMEOUT_MS = 10000;

    /* Some carrier apps might have support captive portal handling. Add some delay to allow
        app authentication to be done before our test.
       TODO: This should go away once we provide an API to apps to disable walled garden test
       for certain SSIDs
     */
    private static final int WALLED_GARDEN_START_DELAY_MS = 3000;

    private static final int BASE = Protocol.BASE_WIFI_WATCHDOG;

    /**
     * Indicates the enable setting of WWS may have changed
     */
    private static final int EVENT_WATCHDOG_TOGGLED                 = BASE + 1;

    /**
     * Indicates the wifi network state has changed. Passed w/ original intent
     * which has a non-null networkInfo object
     */
    private static final int EVENT_NETWORK_STATE_CHANGE             = BASE + 2;
    /* Passed with RSSI information */
    private static final int EVENT_RSSI_CHANGE                      = BASE + 3;
    private static final int EVENT_WIFI_RADIO_STATE_CHANGE          = BASE + 5;
    private static final int EVENT_WATCHDOG_SETTINGS_CHANGE         = BASE + 6;

    /* Internal messages */
    private static final int CMD_ARP_CHECK                          = BASE + 11;
    private static final int CMD_DELAYED_WALLED_GARDEN_CHECK        = BASE + 12;
    private static final int CMD_RSSI_FETCH                         = BASE + 13;

    /* Notifications to WifiStateMachine */
    static final int POOR_LINK_DETECTED                             = BASE + 21;
    static final int GOOD_LINK_DETECTED                             = BASE + 22;
    static final int RSSI_FETCH                                     = BASE + 23;
    static final int RSSI_FETCH_SUCCEEDED                           = BASE + 24;
    static final int RSSI_FETCH_FAILED                              = BASE + 25;

    private static final int SINGLE_ARP_CHECK = 0;
    private static final int FULL_ARP_CHECK   = 1;

=======

    private static final int BASE = Protocol.BASE_WIFI_WATCHDOG;

    /* Internal events */
    private static final int EVENT_WATCHDOG_TOGGLED                 = BASE + 1;
    private static final int EVENT_NETWORK_STATE_CHANGE             = BASE + 2;
    private static final int EVENT_RSSI_CHANGE                      = BASE + 3;
    private static final int EVENT_SUPPLICANT_STATE_CHANGE          = BASE + 4;
    private static final int EVENT_WIFI_RADIO_STATE_CHANGE          = BASE + 5;
    private static final int EVENT_WATCHDOG_SETTINGS_CHANGE         = BASE + 6;
    private static final int EVENT_BSSID_CHANGE                     = BASE + 7;
    private static final int EVENT_SCREEN_ON                        = BASE + 8;
    private static final int EVENT_SCREEN_OFF                       = BASE + 9;

    /* Internal messages */
    private static final int CMD_RSSI_FETCH                         = BASE + 11;

    /* Notifications from/to WifiStateMachine */
    static final int POOR_LINK_DETECTED                             = BASE + 21;
    static final int GOOD_LINK_DETECTED                             = BASE + 22;

    public static final boolean DEFAULT_POOR_NETWORK_AVOIDANCE_ENABLED = false;

    /*
     * RSSI levels as used by notification icon
     * Level 4  -55 <= RSSI
     * Level 3  -66 <= RSSI < -55
     * Level 2  -77 <= RSSI < -67
     * Level 1  -88 <= RSSI < -78
     * Level 0         RSSI < -88
     */

    /**
     * WiFi link statistics is monitored and recorded actively below this threshold.
     * <p>
     * Larger threshold is more adaptive but increases sampling cost.
     */
    private static final int LINK_MONITOR_LEVEL_THRESHOLD = WifiManager.RSSI_LEVELS - 1;

    /**
     * Remember packet loss statistics of how many BSSIDs.
     * <p>
     * Larger size is usually better but requires more space.
     */
    private static final int BSSID_STAT_CACHE_SIZE = 20;

    /**
     * RSSI range of a BSSID statistics.
     * Within the range, (RSSI -> packet loss %) mappings are stored.
     * <p>
     * Larger range is usually better but requires more space.
     */
    private static final int BSSID_STAT_RANGE_LOW_DBM  = -105;

    /**
     * See {@link #BSSID_STAT_RANGE_LOW_DBM}.
     */
    private static final int BSSID_STAT_RANGE_HIGH_DBM = -45;

    /**
     * How many consecutive empty data point to trigger a empty-cache detection.
     * In this case, a preset/default loss value (function on RSSI) is used.
     * <p>
     * In normal uses, some RSSI values may never be seen due to channel randomness.
     * However, the size of such empty RSSI chunk in normal use is generally 1~2.
     */
    private static final int BSSID_STAT_EMPTY_COUNT = 3;

    /**
     * Sample interval for packet loss statistics, in msec.
     * <p>
     * Smaller interval is more accurate but increases sampling cost (battery consumption).
     */
    private static final long LINK_SAMPLING_INTERVAL_MS = 1 * 1000;

    /**
     * Coefficients (alpha) for moving average for packet loss tracking.
     * Must be within (0.0, 1.0).
     * <p>
     * Equivalent number of samples: N = 2 / alpha - 1 .
     * We want the historic loss to base on more data points to be statistically reliable.
     * We want the current instant loss to base on less data points to be responsive.
     */
    private static final double EXP_COEFFICIENT_RECORD  = 0.1;

    /**
     * See {@link #EXP_COEFFICIENT_RECORD}.
     */
    private static final double EXP_COEFFICIENT_MONITOR = 0.5;

    /**
     * Thresholds for sending good/poor link notifications, in packet loss %.
     * Good threshold must be smaller than poor threshold.
     * Use smaller poor threshold to avoid WiFi more aggressively.
     * Use smaller good threshold to bring back WiFi more conservatively.
     * <p>
     * When approaching the boundary, loss ratio jumps significantly within a few dBs.
     * 50% loss threshold is a good balance between accuracy and reponsiveness.
     * <=10% good threshold is a safe value to avoid jumping back to WiFi too easily.
     */
    private static final double POOR_LINK_LOSS_THRESHOLD = 0.5;

    /**
     * See {@link #POOR_LINK_LOSS_THRESHOLD}.
     */
    private static final double GOOD_LINK_LOSS_THRESHOLD = 0.1;

    /**
     * Number of samples to confirm before sending a poor link notification.
     * Response time = confirm_count * sample_interval .
     * <p>
     * A smaller threshold improves response speed but may suffer from randomness.
     * According to experiments, 3~5 are good values to achieve a balance.
     * These parameters should be tuned along with {@link #LINK_SAMPLING_INTERVAL_MS}.
     */
    private static final int POOR_LINK_SAMPLE_COUNT = 3;

    /**
     * Minimum volume (converted from pkt/sec) to detect a poor link, to avoid randomness.
     * <p>
     * According to experiments, 1pkt/sec is too sensitive but 3pkt/sec is slightly unresponsive.
     */
    private static final double POOR_LINK_MIN_VOLUME = 2.0 * LINK_SAMPLING_INTERVAL_MS / 1000.0;

    /**
     * When a poor link is detected, we scan over this range (based on current
     * poor link RSSI) for a target RSSI that satisfies a target packet loss.
     * Refer to {@link #GOOD_LINK_TARGET}.
     * <p>
     * We want range_min not too small to avoid jumping back to WiFi too easily.
     */
    private static final int GOOD_LINK_RSSI_RANGE_MIN = 3;

    /**
     * See {@link #GOOD_LINK_RSSI_RANGE_MIN}.
     */
    private static final int GOOD_LINK_RSSI_RANGE_MAX = 20;

    /**
     * Adaptive good link target to avoid flapping.
     * When a poor link is detected, a good link target is calculated as follows:
     * <p>
     *      targetRSSI = min { rssi | loss(rssi) < GOOD_LINK_LOSS_THRESHOLD } + rssi_adj[i],
     *                   where rssi is within the above GOOD_LINK_RSSI_RANGE.
     *      targetCount = sample_count[i] .
     * <p>
     * While WiFi is being avoided, we keep monitoring its signal strength.
     * Good link notification is sent when we see current RSSI >= targetRSSI
     * for targetCount consecutive times.
     * <p>
     * Index i is incremented each time after a poor link detection.
     * Index i is decreased to at most k if the last poor link was at lease reduce_time[k] ago.
     * <p>
     * Intuitively, larger index i makes it more difficult to get back to WiFi, avoiding flapping.
     * In experiments, (+9 dB / 30 counts) makes it quite difficult to achieve.
     * Avoid using it unless flapping is really bad (say, last poor link is < 1 min ago).
     */
    private static final GoodLinkTarget[] GOOD_LINK_TARGET = {
        /*                  rssi_adj,       sample_count,   reduce_time */
        new GoodLinkTarget( 0,              3,              30 * 60000   ),
        new GoodLinkTarget( 3,              5,              5  * 60000   ),
        new GoodLinkTarget( 6,              10,             1  * 60000   ),
        new GoodLinkTarget( 9,              30,             0  * 60000   ),
    };

    /**
     * The max time to avoid a BSSID, to prevent avoiding forever.
     * If current RSSI is at least min_rssi[i], the max avoidance time is at most max_time[i]
     * <p>
     * It is unusual to experience high packet loss at high RSSI. Something unusual must be
     * happening (e.g. strong interference). For higher signal strengths, we set the avoidance
     * time to be low to allow for quick turn around from temporary interference.
     * <p>
     * See {@link BssidStatistics#poorLinkDetected}.
     */
    private static final MaxAvoidTime[] MAX_AVOID_TIME = {
        /*                  max_time,           min_rssi */
        new MaxAvoidTime(   30 * 60000,         -200      ),
        new MaxAvoidTime(   5  * 60000,         -70       ),
        new MaxAvoidTime(   0  * 60000,         -55       ),
    };

    /* Framework related */
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
    private Context mContext;
    private ContentResolver mContentResolver;
    private WifiManager mWifiManager;
    private IntentFilter mIntentFilter;
    private BroadcastReceiver mBroadcastReceiver;
<<<<<<< HEAD
    private AsyncChannel mWsmChannel = new AsyncChannel();;

=======
    private AsyncChannel mWsmChannel = new AsyncChannel();
    private WifiInfo mWifiInfo;
    private LinkProperties mLinkProperties;

    /* System settingss related */
    private static boolean sWifiOnly = false;
    private boolean mPoorNetworkDetectionEnabled;

    /* Poor link detection related */
    private LruCache<String, BssidStatistics> mBssidCache =
            new LruCache<String, BssidStatistics>(BSSID_STAT_CACHE_SIZE);
    private int mRssiFetchToken = 0;
    private int mCurrentSignalLevel;
    private BssidStatistics mCurrentBssid;
    private VolumeWeightedEMA mCurrentLoss;
    private boolean mIsScreenOn = true;
    private static double sPresetLoss[];

    /* WiFi watchdog state machine related */
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
    private DefaultState mDefaultState = new DefaultState();
    private WatchdogDisabledState mWatchdogDisabledState = new WatchdogDisabledState();
    private WatchdogEnabledState mWatchdogEnabledState = new WatchdogEnabledState();
    private NotConnectedState mNotConnectedState = new NotConnectedState();
    private VerifyingLinkState mVerifyingLinkState = new VerifyingLinkState();
    private ConnectedState mConnectedState = new ConnectedState();
<<<<<<< HEAD
    private WalledGardenCheckState mWalledGardenCheckState = new WalledGardenCheckState();
    /* Online and watching link connectivity */
    private OnlineWatchState mOnlineWatchState = new OnlineWatchState();
    /* RSSI level is below RSSI_LEVEL_MONITOR and needs close monitoring */
    private RssiMonitoringState mRssiMonitoringState = new RssiMonitoringState();
    /* Online and doing nothing */
    private OnlineState mOnlineState = new OnlineState();

    private int mArpToken = 0;
    private long mArpCheckIntervalMs;
    private int mRssiFetchToken = 0;
    private long mRssiFetchIntervalMs;
    private long mWalledGardenIntervalMs;
    private int mNumArpPings;
    private int mMinArpResponses;
    private int mArpPingTimeoutMs;
    private boolean mPoorNetworkDetectionEnabled;
    private boolean mWalledGardenTestEnabled;
    private String mWalledGardenUrl;

    private WifiInfo mWifiInfo;
    private LinkProperties mLinkProperties;

    private long mLastWalledGardenCheckTime = 0;

    private static boolean sWifiOnly = false;
    private boolean mWalledGardenNotificationShown;

=======
    private OnlineWatchState mOnlineWatchState = new OnlineWatchState();
    private LinkMonitoringState mLinkMonitoringState = new LinkMonitoringState();
    private OnlineState mOnlineState = new OnlineState();

>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
    /**
     * STATE MAP
     *          Default
     *         /       \
     * Disabled      Enabled
     *             /     \     \
     * NotConnected  Verifying  Connected
     *                         /---------\
     *                       (all other states)
     */
    private WifiWatchdogStateMachine(Context context) {
        super(TAG);
        mContext = context;
        mContentResolver = context.getContentResolver();
        mWifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        mWsmChannel.connectSync(mContext, getHandler(),
                mWifiManager.getWifiStateMachineMessenger());

        setupNetworkReceiver();

<<<<<<< HEAD
        // The content observer to listen needs a handler
=======
        // the content observer to listen needs a handler
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
        registerForSettingsChanges();
        registerForWatchdogToggle();
        addState(mDefaultState);
            addState(mWatchdogDisabledState, mDefaultState);
            addState(mWatchdogEnabledState, mDefaultState);
                addState(mNotConnectedState, mWatchdogEnabledState);
                addState(mVerifyingLinkState, mWatchdogEnabledState);
                addState(mConnectedState, mWatchdogEnabledState);
<<<<<<< HEAD
                    addState(mWalledGardenCheckState, mConnectedState);
                    addState(mOnlineWatchState, mConnectedState);
                    addState(mRssiMonitoringState, mOnlineWatchState);
=======
                    addState(mOnlineWatchState, mConnectedState);
                    addState(mLinkMonitoringState, mConnectedState);
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
                    addState(mOnlineState, mConnectedState);

        if (isWatchdogEnabled()) {
            setInitialState(mNotConnectedState);
        } else {
            setInitialState(mWatchdogDisabledState);
        }
        updateSettings();
    }

    public static WifiWatchdogStateMachine makeWifiWatchdogStateMachine(Context context) {
        ContentResolver contentResolver = context.getContentResolver();

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(
                Context.CONNECTIVITY_SERVICE);
        sWifiOnly = (cm.isNetworkSupported(ConnectivityManager.TYPE_MOBILE) == false);

<<<<<<< HEAD
        // Watchdog is always enabled. Poor network detection & walled garden detection
        // can individually be turned on/off
        // TODO: Remove this setting & clean up state machine since we always have
        // watchdog in an enabled state
        putSettingsBoolean(contentResolver, Settings.Secure.WIFI_WATCHDOG_ON, true);

        // Disable poor network avoidance, but keep watchdog active for walled garden detection
        if (sWifiOnly) {
            log("Disabling poor network avoidance for wi-fi only device");
            putSettingsBoolean(contentResolver,
                    Settings.Secure.WIFI_WATCHDOG_POOR_NETWORK_TEST_ENABLED, false);
        }
=======
        // Watchdog is always enabled. Poor network detection can be seperately turned on/off
        // TODO: Remove this setting & clean up state machine since we always have
        // watchdog in an enabled state
        putSettingsGlobalBoolean(contentResolver, Settings.Global.WIFI_WATCHDOG_ON, true);
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a

        WifiWatchdogStateMachine wwsm = new WifiWatchdogStateMachine(context);
        wwsm.start();
        return wwsm;
    }

    private void setupNetworkReceiver() {
        mBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
<<<<<<< HEAD
                if (action.equals(WifiManager.NETWORK_STATE_CHANGED_ACTION)) {
                    sendMessage(EVENT_NETWORK_STATE_CHANGE, intent);
                } else if (action.equals(WifiManager.RSSI_CHANGED_ACTION)) {
                    obtainMessage(EVENT_RSSI_CHANGE,
                            intent.getIntExtra(WifiManager.EXTRA_NEW_RSSI, -200), 0).sendToTarget();
                } else if (action.equals(WifiManager.WIFI_STATE_CHANGED_ACTION)) {
                    sendMessage(EVENT_WIFI_RADIO_STATE_CHANGE,
                            intent.getIntExtra(WifiManager.EXTRA_WIFI_STATE,
                                    WifiManager.WIFI_STATE_UNKNOWN));
=======
                if (action.equals(WifiManager.RSSI_CHANGED_ACTION)) {
                    obtainMessage(EVENT_RSSI_CHANGE,
                            intent.getIntExtra(WifiManager.EXTRA_NEW_RSSI, -200), 0).sendToTarget();
                } else if (action.equals(WifiManager.SUPPLICANT_STATE_CHANGED_ACTION)) {
                    sendMessage(EVENT_SUPPLICANT_STATE_CHANGE, intent);
                } else if (action.equals(WifiManager.NETWORK_STATE_CHANGED_ACTION)) {
                    sendMessage(EVENT_NETWORK_STATE_CHANGE, intent);
                } else if (action.equals(Intent.ACTION_SCREEN_ON)) {
                    sendMessage(EVENT_SCREEN_ON);
                } else if (action.equals(Intent.ACTION_SCREEN_OFF)) {
                    sendMessage(EVENT_SCREEN_OFF);
                } else if (action.equals(WifiManager.WIFI_STATE_CHANGED_ACTION)) {
                    sendMessage(EVENT_WIFI_RADIO_STATE_CHANGE,intent.getIntExtra(
                            WifiManager.EXTRA_WIFI_STATE, WifiManager.WIFI_STATE_UNKNOWN));
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
                }
            }
        };

        mIntentFilter = new IntentFilter();
        mIntentFilter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);
        mIntentFilter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
        mIntentFilter.addAction(WifiManager.RSSI_CHANGED_ACTION);
<<<<<<< HEAD
=======
        mIntentFilter.addAction(WifiManager.SUPPLICANT_STATE_CHANGED_ACTION);
        mIntentFilter.addAction(Intent.ACTION_SCREEN_ON);
        mIntentFilter.addAction(Intent.ACTION_SCREEN_OFF);
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
        mContext.registerReceiver(mBroadcastReceiver, mIntentFilter);
    }

    /**
     * Observes the watchdog on/off setting, and takes action when changed.
     */
    private void registerForWatchdogToggle() {
        ContentObserver contentObserver = new ContentObserver(this.getHandler()) {
            @Override
            public void onChange(boolean selfChange) {
                sendMessage(EVENT_WATCHDOG_TOGGLED);
            }
        };

        mContext.getContentResolver().registerContentObserver(
<<<<<<< HEAD
                Settings.Secure.getUriFor(Settings.Secure.WIFI_WATCHDOG_ON),
=======
                Settings.Global.getUriFor(Settings.Global.WIFI_WATCHDOG_ON),
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
                false, contentObserver);
    }

    /**
     * Observes watchdogs secure setting changes.
     */
    private void registerForSettingsChanges() {
        ContentObserver contentObserver = new ContentObserver(this.getHandler()) {
            @Override
            public void onChange(boolean selfChange) {
                sendMessage(EVENT_WATCHDOG_SETTINGS_CHANGE);
            }
        };

        mContext.getContentResolver().registerContentObserver(
<<<<<<< HEAD
                Settings.Secure.getUriFor(
                        Settings.Secure.WIFI_WATCHDOG_ARP_CHECK_INTERVAL_MS),
                        false, contentObserver);
        mContext.getContentResolver().registerContentObserver(
                Settings.Secure.getUriFor(Settings.Secure.WIFI_WATCHDOG_WALLED_GARDEN_INTERVAL_MS),
                false, contentObserver);
        mContext.getContentResolver().registerContentObserver(
                Settings.Secure.getUriFor(Settings.Secure.WIFI_WATCHDOG_NUM_ARP_PINGS),
                false, contentObserver);
        mContext.getContentResolver().registerContentObserver(
                Settings.Secure.getUriFor(Settings.Secure.WIFI_WATCHDOG_MIN_ARP_RESPONSES),
                false, contentObserver);
        mContext.getContentResolver().registerContentObserver(
                Settings.Secure.getUriFor(Settings.Secure.WIFI_WATCHDOG_ARP_PING_TIMEOUT_MS),
                false, contentObserver);
        mContext.getContentResolver().registerContentObserver(
                Settings.Secure.getUriFor(Settings.Secure.WIFI_WATCHDOG_POOR_NETWORK_TEST_ENABLED),
                false, contentObserver);
        mContext.getContentResolver().registerContentObserver(
                Settings.Secure.getUriFor(Settings.Secure.WIFI_WATCHDOG_WALLED_GARDEN_TEST_ENABLED),
                false, contentObserver);
        mContext.getContentResolver().registerContentObserver(
                Settings.Secure.getUriFor(Settings.Secure.WIFI_WATCHDOG_WALLED_GARDEN_URL),
                false, contentObserver);
    }

    /**
     * DNS based detection techniques do not work at all hotspots. The one sure
     * way to check a walled garden is to see if a URL fetch on a known address
     * fetches the data we expect
     */
    private boolean isWalledGardenConnection() {
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(mWalledGardenUrl);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setInstanceFollowRedirects(false);
            urlConnection.setConnectTimeout(WALLED_GARDEN_SOCKET_TIMEOUT_MS);
            urlConnection.setReadTimeout(WALLED_GARDEN_SOCKET_TIMEOUT_MS);
            urlConnection.setUseCaches(false);
            urlConnection.getInputStream();
            // We got a valid response, but not from the real google
            return urlConnection.getResponseCode() != 204;
        } catch (IOException e) {
            if (DBG) {
                log("Walled garden check - probably not a portal: exception " + e);
            }
            return false;
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
=======
                Settings.Global.getUriFor(Settings.Global.WIFI_WATCHDOG_POOR_NETWORK_TEST_ENABLED),
                false, contentObserver);
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
    }

    public void dump(PrintWriter pw) {
        pw.print("WatchdogStatus: ");
        pw.print("State: " + getCurrentState());
        pw.println("mWifiInfo: [" + mWifiInfo + "]");
        pw.println("mLinkProperties: [" + mLinkProperties + "]");
        pw.println("mCurrentSignalLevel: [" + mCurrentSignalLevel + "]");
<<<<<<< HEAD
        pw.println("mArpCheckIntervalMs: [" + mArpCheckIntervalMs+ "]");
        pw.println("mRssiFetchIntervalMs: [" + mRssiFetchIntervalMs + "]");
        pw.println("mWalledGardenIntervalMs: [" + mWalledGardenIntervalMs + "]");
        pw.println("mNumArpPings: [" + mNumArpPings + "]");
        pw.println("mMinArpResponses: [" + mMinArpResponses + "]");
        pw.println("mArpPingTimeoutMs: [" + mArpPingTimeoutMs + "]");
        pw.println("mPoorNetworkDetectionEnabled: [" + mPoorNetworkDetectionEnabled + "]");
        pw.println("mWalledGardenTestEnabled: [" + mWalledGardenTestEnabled + "]");
        pw.println("mWalledGardenUrl: [" + mWalledGardenUrl + "]");
    }

    private boolean isWatchdogEnabled() {
        boolean ret = getSettingsBoolean(mContentResolver, Settings.Secure.WIFI_WATCHDOG_ON, true);
        if (DBG) log("watchdog enabled " + ret);
=======
        pw.println("mPoorNetworkDetectionEnabled: [" + mPoorNetworkDetectionEnabled + "]");
    }

    private boolean isWatchdogEnabled() {
        boolean ret = getSettingsGlobalBoolean(
                mContentResolver, Settings.Global.WIFI_WATCHDOG_ON, true);
        if (DBG) logd("Watchdog enabled " + ret);
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
        return ret;
    }

    private void updateSettings() {
<<<<<<< HEAD
        if (DBG) log("Updating secure settings");

        mArpCheckIntervalMs = Secure.getLong(mContentResolver,
                Secure.WIFI_WATCHDOG_ARP_CHECK_INTERVAL_MS,
                DEFAULT_ARP_CHECK_INTERVAL_MS);
        mRssiFetchIntervalMs = Secure.getLong(mContentResolver,
                Secure.WIFI_WATCHDOG_RSSI_FETCH_INTERVAL_MS,
                DEFAULT_RSSI_FETCH_INTERVAL_MS);
        mNumArpPings = Secure.getInt(mContentResolver,
                Secure.WIFI_WATCHDOG_NUM_ARP_PINGS,
                DEFAULT_NUM_ARP_PINGS);
        mMinArpResponses = Secure.getInt(mContentResolver,
                Secure.WIFI_WATCHDOG_MIN_ARP_RESPONSES,
                DEFAULT_MIN_ARP_RESPONSES);
        mArpPingTimeoutMs = Secure.getInt(mContentResolver,
                Secure.WIFI_WATCHDOG_ARP_PING_TIMEOUT_MS,
                DEFAULT_ARP_PING_TIMEOUT_MS);
        mPoorNetworkDetectionEnabled = getSettingsBoolean(mContentResolver,
                Settings.Secure.WIFI_WATCHDOG_POOR_NETWORK_TEST_ENABLED, true);
        mWalledGardenTestEnabled = getSettingsBoolean(mContentResolver,
                Settings.Secure.WIFI_WATCHDOG_WALLED_GARDEN_TEST_ENABLED, true);
        mWalledGardenUrl = getSettingsStr(mContentResolver,
                Settings.Secure.WIFI_WATCHDOG_WALLED_GARDEN_URL,
                DEFAULT_WALLED_GARDEN_URL);
        mWalledGardenIntervalMs = Secure.getLong(mContentResolver,
                Secure.WIFI_WATCHDOG_WALLED_GARDEN_INTERVAL_MS,
                DEFAULT_WALLED_GARDEN_INTERVAL_MS);
    }

    private void setWalledGardenNotificationVisible(boolean visible) {
        // If it should be hidden and it is already hidden, then noop
        if (!visible && !mWalledGardenNotificationShown) {
            return;
        }

        Resources r = Resources.getSystem();
        NotificationManager notificationManager = (NotificationManager) mContext
            .getSystemService(Context.NOTIFICATION_SERVICE);

        if (visible) {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(mWalledGardenUrl));
            intent.setFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT | Intent.FLAG_ACTIVITY_NEW_TASK);

            CharSequence title = r.getString(R.string.wifi_available_sign_in, 0);
            CharSequence details = r.getString(R.string.wifi_available_sign_in_detailed,
                    mWifiInfo.getSSID());

            Notification notification = new Notification();
            notification.when = 0;
            notification.icon = com.android.internal.R.drawable.stat_notify_wifi_in_range;
            notification.flags = Notification.FLAG_AUTO_CANCEL;
            notification.contentIntent = PendingIntent.getActivity(mContext, 0, intent, 0);
            notification.tickerText = title;
            notification.setLatestEventInfo(mContext, title, details, notification.contentIntent);

            notificationManager.notify(WALLED_GARDEN_NOTIFICATION_ID, 1, notification);
        } else {
            notificationManager.cancel(WALLED_GARDEN_NOTIFICATION_ID, 1);
        }
        mWalledGardenNotificationShown = visible;
    }

    class DefaultState extends State {
        @Override
        public void enter() {
            if (DBG) log(getName() + "\n");
=======
        if (DBG) logd("Updating secure settings");

        // disable poor network avoidance
        if (sWifiOnly) {
            logd("Disabling poor network avoidance for wi-fi only device");
            mPoorNetworkDetectionEnabled = false;
        } else {
            mPoorNetworkDetectionEnabled = getSettingsGlobalBoolean(mContentResolver,
                    Settings.Global.WIFI_WATCHDOG_POOR_NETWORK_TEST_ENABLED,
                    DEFAULT_POOR_NETWORK_AVOIDANCE_ENABLED);
        }
    }

    /**
     * Default state, guard for unhandled messages.
     */
    class DefaultState extends State {
        @Override
        public void enter() {
            if (DBG) logd(getName());
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
        }

        @Override
        public boolean processMessage(Message msg) {
            switch (msg.what) {
                case EVENT_WATCHDOG_SETTINGS_CHANGE:
                    updateSettings();
<<<<<<< HEAD
                    if (DBG) {
                        log("Updating wifi-watchdog secure settings");
                    }
=======
                    if (DBG) logd("Updating wifi-watchdog secure settings");
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
                    break;
                case EVENT_RSSI_CHANGE:
                    mCurrentSignalLevel = calculateSignalLevel(msg.arg1);
                    break;
                case EVENT_WIFI_RADIO_STATE_CHANGE:
                case EVENT_NETWORK_STATE_CHANGE:
<<<<<<< HEAD
                case CMD_ARP_CHECK:
                case CMD_DELAYED_WALLED_GARDEN_CHECK:
                case CMD_RSSI_FETCH:
                case RSSI_FETCH_SUCCEEDED:
                case RSSI_FETCH_FAILED:
                    //ignore
                    break;
                default:
                    log("Unhandled message " + msg + " in state " + getCurrentState().getName());
=======
                case EVENT_SUPPLICANT_STATE_CHANGE:
                case EVENT_BSSID_CHANGE:
                case CMD_RSSI_FETCH:
                case WifiManager.RSSI_PKTCNT_FETCH_SUCCEEDED:
                case WifiManager.RSSI_PKTCNT_FETCH_FAILED:
                    // ignore
                    break;
                case EVENT_SCREEN_ON:
                    mIsScreenOn = true;
                    break;
                case EVENT_SCREEN_OFF:
                    mIsScreenOn = false;
                    break;
                default:
                    loge("Unhandled message " + msg + " in state " + getCurrentState().getName());
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
                    break;
            }
            return HANDLED;
        }
    }

<<<<<<< HEAD
    class WatchdogDisabledState extends State {
        @Override
        public void enter() {
            if (DBG) log(getName() + "\n");
=======
    /**
     * WiFi watchdog is disabled by the setting.
     */
    class WatchdogDisabledState extends State {
        @Override
        public void enter() {
            if (DBG) logd(getName());
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
        }

        @Override
        public boolean processMessage(Message msg) {
            switch (msg.what) {
                case EVENT_WATCHDOG_TOGGLED:
                    if (isWatchdogEnabled())
                        transitionTo(mNotConnectedState);
                    return HANDLED;
                case EVENT_NETWORK_STATE_CHANGE:
                    Intent intent = (Intent) msg.obj;
                    NetworkInfo networkInfo = (NetworkInfo)
                            intent.getParcelableExtra(WifiManager.EXTRA_NETWORK_INFO);

                    switch (networkInfo.getDetailedState()) {
                        case VERIFYING_POOR_LINK:
<<<<<<< HEAD
                            if (DBG) log("Watchdog disabled, verify link");
                            mWsmChannel.sendMessage(GOOD_LINK_DETECTED);
=======
                            if (DBG) logd("Watchdog disabled, verify link");
                            sendLinkStatusNotification(true);
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
                            break;
                        default:
                            break;
                    }
                    break;
            }
            return NOT_HANDLED;
        }
    }

<<<<<<< HEAD
    class WatchdogEnabledState extends State {
        @Override
        public void enter() {
            if (DBG) log("WifiWatchdogService enabled");
=======
    /**
     * WiFi watchdog is enabled by the setting.
     */
    class WatchdogEnabledState extends State {
        @Override
        public void enter() {
            if (DBG) logd(getName());
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
        }

        @Override
        public boolean processMessage(Message msg) {
<<<<<<< HEAD
=======
            Intent intent;
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
            switch (msg.what) {
                case EVENT_WATCHDOG_TOGGLED:
                    if (!isWatchdogEnabled())
                        transitionTo(mWatchdogDisabledState);
                    break;
<<<<<<< HEAD
                case EVENT_NETWORK_STATE_CHANGE:
                    Intent intent = (Intent) msg.obj;
                    NetworkInfo networkInfo = (NetworkInfo)
                            intent.getParcelableExtra(WifiManager.EXTRA_NETWORK_INFO);

                    if (DBG) log("network state change " + networkInfo.getDetailedState());
=======

                case EVENT_NETWORK_STATE_CHANGE:
                    intent = (Intent) msg.obj;
                    NetworkInfo networkInfo =
                            (NetworkInfo) intent.getParcelableExtra(WifiManager.EXTRA_NETWORK_INFO);
                    if (DBG) logd("Network state change " + networkInfo.getDetailedState());

                    mWifiInfo = (WifiInfo) intent.getParcelableExtra(WifiManager.EXTRA_WIFI_INFO);
                    updateCurrentBssid(mWifiInfo != null ? mWifiInfo.getBSSID() : null);
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a

                    switch (networkInfo.getDetailedState()) {
                        case VERIFYING_POOR_LINK:
                            mLinkProperties = (LinkProperties) intent.getParcelableExtra(
                                    WifiManager.EXTRA_LINK_PROPERTIES);
<<<<<<< HEAD
                            mWifiInfo = (WifiInfo) intent.getParcelableExtra(
                                    WifiManager.EXTRA_WIFI_INFO);
                            if (mPoorNetworkDetectionEnabled) {
                                if (mWifiInfo == null) {
                                    log("Ignoring link verification, mWifiInfo is NULL");
                                    mWsmChannel.sendMessage(GOOD_LINK_DETECTED);
=======
                            if (mPoorNetworkDetectionEnabled) {
                                if (mWifiInfo == null || mCurrentBssid == null) {
                                    loge("Ignore, wifiinfo " + mWifiInfo +" bssid " + mCurrentBssid);
                                    sendLinkStatusNotification(true);
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
                                } else {
                                    transitionTo(mVerifyingLinkState);
                                }
                            } else {
<<<<<<< HEAD
                                mWsmChannel.sendMessage(GOOD_LINK_DETECTED);
                            }
                            break;
                        case CONNECTED:
                            if (shouldCheckWalledGarden()) {
                                transitionTo(mWalledGardenCheckState);
                            } else {
                                transitionTo(mOnlineWatchState);
                            }
=======
                                sendLinkStatusNotification(true);
                            }
                            break;
                        case CONNECTED:
                            transitionTo(mOnlineWatchState);
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
                            break;
                        default:
                            transitionTo(mNotConnectedState);
                            break;
                    }
                    break;
<<<<<<< HEAD
                case EVENT_WIFI_RADIO_STATE_CHANGE:
                    if ((Integer) msg.obj == WifiManager.WIFI_STATE_DISABLING) {
                        if (DBG) log("WifiStateDisabling -- Resetting WatchdogState");
                        transitionTo(mNotConnectedState);
                    }
                    break;
=======

                case EVENT_SUPPLICANT_STATE_CHANGE:
                    intent = (Intent) msg.obj;
                    SupplicantState supplicantState = (SupplicantState) intent.getParcelableExtra(
                            WifiManager.EXTRA_NEW_STATE);
                    if (supplicantState == SupplicantState.COMPLETED) {
                        mWifiInfo = mWifiManager.getConnectionInfo();
                        updateCurrentBssid(mWifiInfo.getBSSID());
                    }
                    break;

                case EVENT_WIFI_RADIO_STATE_CHANGE:
                    if ((Integer) msg.obj == WifiManager.WIFI_STATE_DISABLING)
                        transitionTo(mNotConnectedState);
                    break;

>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
                default:
                    return NOT_HANDLED;
            }

<<<<<<< HEAD
            setWalledGardenNotificationVisible(false);
            return HANDLED;
        }

        @Override
        public void exit() {
            if (DBG) log("WifiWatchdogService disabled");
        }
    }

    class NotConnectedState extends State {
        @Override
        public void enter() {
            if (DBG) log(getName() + "\n");
        }
    }

    class VerifyingLinkState extends State {
        @Override
        public void enter() {
            if (DBG) log(getName() + "\n");
            //Treat entry as an rssi change
            handleRssiChange();
        }

        private void handleRssiChange() {
            if (mCurrentSignalLevel <= RSSI_LEVEL_MONITOR) {
                //stay here
                if (DBG) log("enter VerifyingLinkState, stay level: " + mCurrentSignalLevel);
            } else {
                if (DBG) log("enter VerifyingLinkState, arp check level: " + mCurrentSignalLevel);
                sendMessage(obtainMessage(CMD_ARP_CHECK, ++mArpToken, 0));
            }
=======
            return HANDLED;
        }
    }

    /**
     * WiFi is disconnected.
     */
    class NotConnectedState extends State {
        @Override
        public void enter() {
            if (DBG) logd(getName());
        }
    }

    /**
     * WiFi is connected, but waiting for good link detection message.
     */
    class VerifyingLinkState extends State {

        private int mSampleCount;

        @Override
        public void enter() {
            if (DBG) logd(getName());
            mSampleCount = 0;
            mCurrentBssid.newLinkDetected();
            sendMessage(obtainMessage(CMD_RSSI_FETCH, ++mRssiFetchToken, 0));
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
        }

        @Override
        public boolean processMessage(Message msg) {
            switch (msg.what) {
                case EVENT_WATCHDOG_SETTINGS_CHANGE:
                    updateSettings();
                    if (!mPoorNetworkDetectionEnabled) {
<<<<<<< HEAD
                        mWsmChannel.sendMessage(GOOD_LINK_DETECTED);
                    }
                    break;
                case EVENT_RSSI_CHANGE:
                    mCurrentSignalLevel = calculateSignalLevel(msg.arg1);
                    handleRssiChange();
                    break;
                case CMD_ARP_CHECK:
                    if (msg.arg1 == mArpToken) {
                        if (doArpTest(FULL_ARP_CHECK) == true) {
                            if (DBG) log("Notify link is good " + mCurrentSignalLevel);
                            mWsmChannel.sendMessage(GOOD_LINK_DETECTED);
                        } else {
                            if (DBG) log("Continue ARP check, rssi level: " + mCurrentSignalLevel);
                            sendMessageDelayed(obtainMessage(CMD_ARP_CHECK, ++mArpToken, 0),
                                    mArpCheckIntervalMs);
                        }
                    }
                    break;
=======
                        sendLinkStatusNotification(true);
                    }
                    break;

                case EVENT_BSSID_CHANGE:
                    transitionTo(mVerifyingLinkState);
                    break;

                case CMD_RSSI_FETCH:
                    if (msg.arg1 == mRssiFetchToken) {
                        mWsmChannel.sendMessage(WifiManager.RSSI_PKTCNT_FETCH);
                        sendMessageDelayed(obtainMessage(CMD_RSSI_FETCH, ++mRssiFetchToken, 0),
                                LINK_SAMPLING_INTERVAL_MS);
                    }
                    break;

                case WifiManager.RSSI_PKTCNT_FETCH_SUCCEEDED:
                    RssiPacketCountInfo info = (RssiPacketCountInfo) msg.obj;
                    int rssi = info.rssi;
                    if (DBG) logd("Fetch RSSI succeed, rssi=" + rssi);

                    long time = mCurrentBssid.mBssidAvoidTimeMax - SystemClock.elapsedRealtime();
                    if (time <= 0) {
                        // max avoidance time is met
                        if (DBG) logd("Max avoid time elapsed");
                        sendLinkStatusNotification(true);
                    } else {
                        if (rssi >= mCurrentBssid.mGoodLinkTargetRssi) {
                            if (++mSampleCount >= mCurrentBssid.mGoodLinkTargetCount) {
                                // link is good again
                                if (DBG) logd("Good link detected, rssi=" + rssi);
                                mCurrentBssid.mBssidAvoidTimeMax = 0;
                                sendLinkStatusNotification(true);
                            }
                        } else {
                            mSampleCount = 0;
                            if (DBG) logd("Link is still poor, time left=" + time);
                        }
                    }
                    break;

                case WifiManager.RSSI_PKTCNT_FETCH_FAILED:
                    if (DBG) logd("RSSI_FETCH_FAILED");
                    break;

>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
                default:
                    return NOT_HANDLED;
            }
            return HANDLED;
        }
    }

<<<<<<< HEAD
    class ConnectedState extends State {
        @Override
        public void enter() {
            if (DBG) log(getName() + "\n");
        }
=======
    /**
     * WiFi is connected and link is verified.
     */
    class ConnectedState extends State {
        @Override
        public void enter() {
            if (DBG) logd(getName());
        }

>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
        @Override
        public boolean processMessage(Message msg) {
            switch (msg.what) {
                case EVENT_WATCHDOG_SETTINGS_CHANGE:
                    updateSettings();
<<<<<<< HEAD
                    //STOPSHIP: Remove this at ship
                    DBG = true;
                    if (DBG) log("Updated secure settings and turned debug on");
=======
                    // STOPSHIP: Remove this at ship
                    logd("Updated secure settings and turned debug on");
                    DBG = true;
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a

                    if (mPoorNetworkDetectionEnabled) {
                        transitionTo(mOnlineWatchState);
                    } else {
                        transitionTo(mOnlineState);
                    }
                    return HANDLED;
            }
            return NOT_HANDLED;
        }
    }

<<<<<<< HEAD
    class WalledGardenCheckState extends State {
        private int mWalledGardenToken = 0;
        @Override
        public void enter() {
            if (DBG) log(getName() + "\n");
            sendMessageDelayed(obtainMessage(CMD_DELAYED_WALLED_GARDEN_CHECK,
                    ++mWalledGardenToken, 0), WALLED_GARDEN_START_DELAY_MS);
        }

        @Override
        public boolean processMessage(Message msg) {
            switch (msg.what) {
                case CMD_DELAYED_WALLED_GARDEN_CHECK:
                    if (msg.arg1 == mWalledGardenToken) {
                        mLastWalledGardenCheckTime = SystemClock.elapsedRealtime();
                        if (isWalledGardenConnection()) {
                            if (DBG) log("Walled garden detected");
                            setWalledGardenNotificationVisible(true);
                        }
                        transitionTo(mOnlineWatchState);
                    }
                    break;
                default:
                    return NOT_HANDLED;
            }
            return HANDLED;
        }
    }

    class OnlineWatchState extends State {
        public void enter() {
            if (DBG) log(getName() + "\n");
            if (mPoorNetworkDetectionEnabled) {
                //Treat entry as an rssi change
=======
    /**
     * RSSI is high enough and don't need link monitoring.
     */
    class OnlineWatchState extends State {
        @Override
        public void enter() {
            if (DBG) logd(getName());
            if (mPoorNetworkDetectionEnabled) {
                // treat entry as an rssi change
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
                handleRssiChange();
            } else {
                transitionTo(mOnlineState);
            }
        }

        private void handleRssiChange() {
<<<<<<< HEAD
            if (mCurrentSignalLevel <= RSSI_LEVEL_MONITOR) {
                transitionTo(mRssiMonitoringState);
            } else {
                //stay here
=======
            if (mCurrentSignalLevel <= LINK_MONITOR_LEVEL_THRESHOLD && mCurrentBssid != null) {
                transitionTo(mLinkMonitoringState);
            } else {
                // stay here
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
            }
        }

        @Override
        public boolean processMessage(Message msg) {
            switch (msg.what) {
                case EVENT_RSSI_CHANGE:
                    mCurrentSignalLevel = calculateSignalLevel(msg.arg1);
<<<<<<< HEAD
                    //Ready to avoid bssid again ?
                    long time = android.os.SystemClock.elapsedRealtime();
                    if (time - mLastBssidAvoidedTime  > MIN_INTERVAL_AVOID_BSSID_MS[
                            mMinIntervalArrayIndex]) {
                        handleRssiChange();
                    } else {
                        if (DBG) log("Early to avoid " + mWifiInfo + " time: " + time +
                                " last avoided: " + mLastBssidAvoidedTime +
                                " mMinIntervalArrayIndex: " + mMinIntervalArrayIndex);
                    }
=======
                    handleRssiChange();
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
                    break;
                default:
                    return NOT_HANDLED;
            }
            return HANDLED;
        }
    }

<<<<<<< HEAD
    class RssiMonitoringState extends State {
        public void enter() {
            if (DBG) log(getName() + "\n");
            sendMessage(obtainMessage(CMD_RSSI_FETCH, ++mRssiFetchToken, 0));
        }

=======
    /**
     * Keep sampling the link and monitor any poor link situation.
     */
    class LinkMonitoringState extends State {

        private int mSampleCount;

        private int mLastRssi;
        private int mLastTxGood;
        private int mLastTxBad;

        @Override
        public void enter() {
            if (DBG) logd(getName());
            mSampleCount = 0;
            mCurrentLoss = new VolumeWeightedEMA(EXP_COEFFICIENT_MONITOR);
            sendMessage(obtainMessage(CMD_RSSI_FETCH, ++mRssiFetchToken, 0));
        }

        @Override
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
        public boolean processMessage(Message msg) {
            switch (msg.what) {
                case EVENT_RSSI_CHANGE:
                    mCurrentSignalLevel = calculateSignalLevel(msg.arg1);
<<<<<<< HEAD
                    if (mCurrentSignalLevel <= RSSI_LEVEL_MONITOR) {
                        //stay here;
                    } else {
                        //We dont need frequent RSSI monitoring any more
                        transitionTo(mOnlineWatchState);
                    }
                    break;
                case CMD_RSSI_FETCH:
                    if (msg.arg1 == mRssiFetchToken) {
                        mWsmChannel.sendMessage(RSSI_FETCH);
                        sendMessageDelayed(obtainMessage(CMD_RSSI_FETCH, ++mRssiFetchToken, 0),
                                mRssiFetchIntervalMs);
                    }
                    break;
                case RSSI_FETCH_SUCCEEDED:
                    int rssi = msg.arg1;
                    if (DBG) log("RSSI_FETCH_SUCCEEDED: " + rssi);
                    if (msg.arg1 < RSSI_MONITOR_THRESHOLD) {
                        mRssiMonitorCount++;
                    } else {
                        mRssiMonitorCount = 0;
                    }

                    if (mRssiMonitorCount > RSSI_MONITOR_COUNT) {
                        sendPoorLinkDetected();
                        ++mRssiFetchToken;
                    }
                    break;
                case RSSI_FETCH_FAILED:
                    //can happen if we are waiting to get a disconnect notification
                    if (DBG) log("RSSI_FETCH_FAILED");
                    break;
=======
                    if (mCurrentSignalLevel <= LINK_MONITOR_LEVEL_THRESHOLD) {
                        // stay here;
                    } else {
                        // we don't need frequent RSSI monitoring any more
                        transitionTo(mOnlineWatchState);
                    }
                    break;

                case EVENT_BSSID_CHANGE:
                    transitionTo(mLinkMonitoringState);
                    break;

                case CMD_RSSI_FETCH:
                    if (!mIsScreenOn) {
                        transitionTo(mOnlineState);
                    } else if (msg.arg1 == mRssiFetchToken) {
                        mWsmChannel.sendMessage(WifiManager.RSSI_PKTCNT_FETCH);
                        sendMessageDelayed(obtainMessage(CMD_RSSI_FETCH, ++mRssiFetchToken, 0),
                                LINK_SAMPLING_INTERVAL_MS);
                    }
                    break;

                case WifiManager.RSSI_PKTCNT_FETCH_SUCCEEDED:
                    RssiPacketCountInfo info = (RssiPacketCountInfo) msg.obj;
                    int rssi = info.rssi;
                    int mrssi = (mLastRssi + rssi) / 2;
                    int txbad = info.txbad;
                    int txgood = info.txgood;
                    if (DBG) logd("Fetch RSSI succeed, rssi=" + rssi + " mrssi=" + mrssi + " txbad="
                            + txbad + " txgood=" + txgood);

                    // skip the first data point as we want incremental values
                    long now = SystemClock.elapsedRealtime();
                    if (now - mCurrentBssid.mLastTimeSample < LINK_SAMPLING_INTERVAL_MS * 2) {

                        // update packet loss statistics
                        int dbad = txbad - mLastTxBad;
                        int dgood = txgood - mLastTxGood;
                        int dtotal = dbad + dgood;

                        if (dtotal > 0) {
                            // calculate packet loss in the last sampling interval
                            double loss = ((double) dbad) / ((double) dtotal);

                            mCurrentLoss.update(loss, dtotal);

                            if (DBG) {
                                DecimalFormat df = new DecimalFormat("#.##");
                                logd("Incremental loss=" + dbad + "/" + dtotal + " Current loss="
                                        + df.format(mCurrentLoss.mValue * 100) + "% volume="
                                        + df.format(mCurrentLoss.mVolume));
                            }

                            mCurrentBssid.updateLoss(mrssi, loss, dtotal);

                            // check for high packet loss and send poor link notification
                            if (mCurrentLoss.mValue > POOR_LINK_LOSS_THRESHOLD
                                    && mCurrentLoss.mVolume > POOR_LINK_MIN_VOLUME) {
                                if (++mSampleCount >= POOR_LINK_SAMPLE_COUNT)
                                    if (mCurrentBssid.poorLinkDetected(rssi)) {
                                        sendLinkStatusNotification(false);
                                        ++mRssiFetchToken;
                                    }
                            } else {
                                mSampleCount = 0;
                            }
                        }
                    }

                    mCurrentBssid.mLastTimeSample = now;
                    mLastTxBad = txbad;
                    mLastTxGood = txgood;
                    mLastRssi = rssi;
                    break;

                case WifiManager.RSSI_PKTCNT_FETCH_FAILED:
                    // can happen if we are waiting to get a disconnect notification
                    if (DBG) logd("RSSI_FETCH_FAILED");
                    break;

>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
                default:
                    return NOT_HANDLED;
            }
            return HANDLED;
        }
   }

<<<<<<< HEAD
    /* Child state of ConnectedState indicating that we are online
     * and there is nothing to do
=======
    /**
     * Child state of ConnectedState indicating that we are online and there is nothing to do.
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
     */
    class OnlineState extends State {
        @Override
        public void enter() {
<<<<<<< HEAD
            if (DBG) log(getName() + "\n");
        }
    }

    private boolean shouldCheckWalledGarden() {
        if (!mWalledGardenTestEnabled) {
            if (DBG) log("Skipping walled garden check - disabled");
            return false;
        }

        long waitTime = (mWalledGardenIntervalMs + mLastWalledGardenCheckTime)
            - SystemClock.elapsedRealtime();

        if (mLastWalledGardenCheckTime != 0 && waitTime > 0) {
            if (DBG) {
                log("Skipping walled garden check - wait " +
                        waitTime + " ms.");
            }
            return false;
        }
        return true;
    }

    private boolean doArpTest(int type) {
        boolean success;

        String iface = mLinkProperties.getInterfaceName();
        String mac = mWifiInfo.getMacAddress();
        InetAddress inetAddress = null;
        InetAddress gateway = null;

        for (LinkAddress la : mLinkProperties.getLinkAddresses()) {
            inetAddress = la.getAddress();
            break;
        }

        for (RouteInfo route : mLinkProperties.getRoutes()) {
            gateway = route.getGateway();
            break;
        }

        if (DBG) log("ARP " + iface + "addr: " + inetAddress + "mac: " + mac + "gw: " + gateway);

        try {
            ArpPeer peer = new ArpPeer(iface, inetAddress, mac, gateway);
            if (type == SINGLE_ARP_CHECK) {
                success = (peer.doArp(mArpPingTimeoutMs) != null);
                if (DBG) log("single ARP test result: " + success);
            } else {
                int responses = 0;
                for (int i=0; i < mNumArpPings; i++) {
                    if(peer.doArp(mArpPingTimeoutMs) != null) responses++;
                }
                if (DBG) log("full ARP test result: " + responses + "/" + mNumArpPings);
                success = (responses >= mMinArpResponses);
            }
            peer.close();
        } catch (SocketException se) {
            //Consider an Arp socket creation issue as a successful Arp
            //test to avoid any wifi connectivity issues
            loge("ARP test initiation failure: " + se);
            success = true;
        } catch (IllegalArgumentException e) {
            // ArpPeer throws exception for IPv6 address
            success = true;
        }

        return success;
    }

    private int calculateSignalLevel(int rssi) {
        int signalLevel = WifiManager.calculateSignalLevel(rssi,
                WifiManager.RSSI_LEVELS);
        if (DBG) log("RSSI current: " + mCurrentSignalLevel + "new: " + rssi + ", " + signalLevel);
        return signalLevel;
    }

    private void sendPoorLinkDetected() {
        if (DBG) log("send POOR_LINK_DETECTED " + mWifiInfo);
        mWsmChannel.sendMessage(POOR_LINK_DETECTED);

        long time = android.os.SystemClock.elapsedRealtime();
        if (time - mLastBssidAvoidedTime  > MIN_INTERVAL_AVOID_BSSID_MS[
                MIN_INTERVAL_AVOID_BSSID_MS.length - 1]) {
            mMinIntervalArrayIndex = 1;
            if (DBG) log("set mMinIntervalArrayIndex to 1");
        } else {

            if (mMinIntervalArrayIndex < MIN_INTERVAL_AVOID_BSSID_MS.length - 1) {
                mMinIntervalArrayIndex++;
            }
            if (DBG) log("mMinIntervalArrayIndex: " + mMinIntervalArrayIndex);
        }

        mLastBssidAvoidedTime = android.os.SystemClock.elapsedRealtime();
    }

    /**
     * Convenience function for retrieving a single secure settings value
     * as a string with a default value.
     *
     * @param cr The ContentResolver to access.
     * @param name The name of the setting to retrieve.
     * @param def Value to return if the setting is not defined.
     *
     * @return The setting's current value, or 'def' if it is not defined
     */
    private static String getSettingsStr(ContentResolver cr, String name, String def) {
        String v = Settings.Secure.getString(cr, name);
        return v != null ? v : def;
    }

    /**
     * Convenience function for retrieving a single secure settings value
     * as a boolean.  Note that internally setting values are always
     * stored as strings; this function converts the string to a boolean
     * for you.  The default value will be returned if the setting is
     * not defined or not a valid boolean.
=======
            if (DBG) logd(getName());
        }

        @Override
        public boolean processMessage(Message msg) {
            switch (msg.what) {
                case EVENT_SCREEN_ON:
                    mIsScreenOn = true;
                    if (mPoorNetworkDetectionEnabled)
                        transitionTo(mOnlineWatchState);
                    break;
                default:
                    return NOT_HANDLED;
            }
            return HANDLED;
        }
    }

    private void updateCurrentBssid(String bssid) {
        if (DBG) logd("Update current BSSID to " + (bssid != null ? bssid : "null"));

        // if currently not connected, then set current BSSID to null
        if (bssid == null) {
            if (mCurrentBssid == null) return;
            mCurrentBssid = null;
            if (DBG) logd("BSSID changed");
            sendMessage(EVENT_BSSID_CHANGE);
            return;
        }

        // if it is already the current BSSID, then done
        if (mCurrentBssid != null && bssid.equals(mCurrentBssid.mBssid)) return;

        // search for the new BSSID in the cache, add to cache if not found
        mCurrentBssid = mBssidCache.get(bssid);
        if (mCurrentBssid == null) {
            mCurrentBssid = new BssidStatistics(bssid);
            mBssidCache.put(bssid, mCurrentBssid);
        }

        // send BSSID change notification
        if (DBG) logd("BSSID changed");
        sendMessage(EVENT_BSSID_CHANGE);
    }

    private int calculateSignalLevel(int rssi) {
        int signalLevel = WifiManager.calculateSignalLevel(rssi, WifiManager.RSSI_LEVELS);
        if (DBG)
            logd("RSSI current: " + mCurrentSignalLevel + " new: " + rssi + ", " + signalLevel);
        return signalLevel;
    }

    private void sendLinkStatusNotification(boolean isGood) {
        if (DBG) logd("########################################");
        if (isGood) {
            mWsmChannel.sendMessage(GOOD_LINK_DETECTED);
            if (mCurrentBssid != null) {
                mCurrentBssid.mLastTimeGood = SystemClock.elapsedRealtime();
            }
            if (DBG) logd("Good link notification is sent");
        } else {
            mWsmChannel.sendMessage(POOR_LINK_DETECTED);
            if (mCurrentBssid != null) {
                mCurrentBssid.mLastTimePoor = SystemClock.elapsedRealtime();
            }
            logd("Poor link notification is sent");
        }
    }

    /**
     * Convenience function for retrieving a single secure settings value as a
     * boolean. Note that internally setting values are always stored as
     * strings; this function converts the string to a boolean for you. The
     * default value will be returned if the setting is not defined or not a
     * valid boolean.
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
     *
     * @param cr The ContentResolver to access.
     * @param name The name of the setting to retrieve.
     * @param def Value to return if the setting is not defined.
<<<<<<< HEAD
     *
     * @return The setting's current value, or 'def' if it is not defined
     * or not a valid boolean.
     */
    private static boolean getSettingsBoolean(ContentResolver cr, String name, boolean def) {
        return Settings.Secure.getInt(cr, name, def ? 1 : 0) == 1;
    }

    /**
     * Convenience function for updating a single settings value as an
     * integer. This will either create a new entry in the table if the
     * given name does not exist, or modify the value of the existing row
     * with that name.  Note that internally setting values are always
     * stored as strings, so this function converts the given value to a
     * string before storing it.
=======
     * @return The setting's current value, or 'def' if it is not defined or not
     *         a valid boolean.
     */
    private static boolean getSettingsGlobalBoolean(ContentResolver cr, String name, boolean def) {
        return Settings.Global.getInt(cr, name, def ? 1 : 0) == 1;
    }

    /**
     * Convenience function for updating a single settings value as an integer.
     * This will either create a new entry in the table if the given name does
     * not exist, or modify the value of the existing row with that name. Note
     * that internally setting values are always stored as strings, so this
     * function converts the given value to a string before storing it.
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
     *
     * @param cr The ContentResolver to access.
     * @param name The name of the setting to modify.
     * @param value The new value for the setting.
     * @return true if the value was set, false on database errors
     */
<<<<<<< HEAD
    private static boolean putSettingsBoolean(ContentResolver cr, String name, boolean value) {
        return Settings.Secure.putInt(cr, name, value ? 1 : 0);
    }

    private static void log(String s) {
=======
    private static boolean putSettingsGlobalBoolean(ContentResolver cr, String name, boolean value) {
        return Settings.Global.putInt(cr, name, value ? 1 : 0);
    }

    private static void logd(String s) {
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
        Log.d(TAG, s);
    }

    private static void loge(String s) {
        Log.e(TAG, s);
    }
<<<<<<< HEAD
=======

    /**
     * Bundle of good link count parameters
     */
    private static class GoodLinkTarget {
        public final int RSSI_ADJ_DBM;
        public final int SAMPLE_COUNT;
        public final int REDUCE_TIME_MS;
        public GoodLinkTarget(int adj, int count, int time) {
            RSSI_ADJ_DBM = adj;
            SAMPLE_COUNT = count;
            REDUCE_TIME_MS = time;
        }
    }

    /**
     * Bundle of max avoidance time parameters
     */
    private static class MaxAvoidTime {
        public final int TIME_MS;
        public final int MIN_RSSI_DBM;
        public MaxAvoidTime(int time, int rssi) {
            TIME_MS = time;
            MIN_RSSI_DBM = rssi;
        }
    }

    /**
     * Volume-weighted Exponential Moving Average (V-EMA)
     *    - volume-weighted:  each update has its own weight (number of packets)
     *    - exponential:      O(1) time and O(1) space for both update and query
     *    - moving average:   reflect most recent results and expire old ones
     */
    private class VolumeWeightedEMA {
        private double mValue;
        private double mVolume;
        private double mProduct;
        private final double mAlpha;

        public VolumeWeightedEMA(double coefficient) {
            mValue   = 0.0;
            mVolume  = 0.0;
            mProduct = 0.0;
            mAlpha   = coefficient;
        }

        public void update(double newValue, int newVolume) {
            if (newVolume <= 0) return;
            // core update formulas
            double newProduct = newValue * newVolume;
            mProduct = mAlpha * newProduct + (1 - mAlpha) * mProduct;
            mVolume  = mAlpha * newVolume  + (1 - mAlpha) * mVolume;
            mValue   = mProduct / mVolume;
        }
    }

    /**
     * Record (RSSI -> pakce loss %) mappings of one BSSID
     */
    private class BssidStatistics {

        /* MAC address of this BSSID */
        private final String mBssid;

        /* RSSI -> packet loss % mappings */
        private VolumeWeightedEMA[] mEntries;
        private int mRssiBase;
        private int mEntriesSize;

        /* Target to send good link notification, set when poor link is detected */
        private int mGoodLinkTargetRssi;
        private int mGoodLinkTargetCount;

        /* Index of GOOD_LINK_TARGET array */
        private int mGoodLinkTargetIndex;

        /* Timestamps of some last events */
        private long mLastTimeSample;
        private long mLastTimeGood;
        private long mLastTimePoor;

        /* Max time to avoid this BSSID */
        private long mBssidAvoidTimeMax;

        /**
         * Constructor
         *
         * @param bssid is the address of this BSSID
         */
        public BssidStatistics(String bssid) {
            this.mBssid = bssid;
            mRssiBase = BSSID_STAT_RANGE_LOW_DBM;
            mEntriesSize = BSSID_STAT_RANGE_HIGH_DBM - BSSID_STAT_RANGE_LOW_DBM + 1;
            mEntries = new VolumeWeightedEMA[mEntriesSize];
            for (int i = 0; i < mEntriesSize; i++)
                mEntries[i] = new VolumeWeightedEMA(EXP_COEFFICIENT_RECORD);
        }

        /**
         * Update this BSSID cache
         *
         * @param rssi is the RSSI
         * @param value is the new instant loss value at this RSSI
         * @param volume is the volume for this single update
         */
        public void updateLoss(int rssi, double value, int volume) {
            if (volume <= 0) return;
            int index = rssi - mRssiBase;
            if (index < 0 || index >= mEntriesSize) return;
            mEntries[index].update(value, volume);
            if (DBG) {
                DecimalFormat df = new DecimalFormat("#.##");
                logd("Cache updated: loss[" + rssi + "]=" + df.format(mEntries[index].mValue * 100)
                        + "% volume=" + df.format(mEntries[index].mVolume));
            }
        }

        /**
         * Get preset loss if the cache has insufficient data, observed from experiments.
         *
         * @param rssi is the input RSSI
         * @return preset loss of the given RSSI
         */
        public double presetLoss(int rssi) {
            if (rssi <= -90) return 1.0;
            if (rssi > 0) return 0.0;

            if (sPresetLoss == null) {
                // pre-calculate all preset losses only once, then reuse them
                final int size = 90;
                sPresetLoss = new double[size];
                for (int i = 0; i < size; i++) sPresetLoss[i] = 1.0 / Math.pow(90 - i, 1.5);
            }
            return sPresetLoss[-rssi];
        }

        /**
         * A poor link is detected, calculate a target RSSI to bring WiFi back.
         *
         * @param rssi is the current RSSI
         * @return true iff the current BSSID should be avoided
         */
        public boolean poorLinkDetected(int rssi) {
            if (DBG) logd("Poor link detected, rssi=" + rssi);

            long now = SystemClock.elapsedRealtime();
            long lastGood = now - mLastTimeGood;
            long lastPoor = now - mLastTimePoor;

            // reduce the difficulty of good link target if last avoidance was long time ago
            while (mGoodLinkTargetIndex > 0
                    && lastPoor >= GOOD_LINK_TARGET[mGoodLinkTargetIndex - 1].REDUCE_TIME_MS)
                mGoodLinkTargetIndex--;
            mGoodLinkTargetCount = GOOD_LINK_TARGET[mGoodLinkTargetIndex].SAMPLE_COUNT;

            // scan for a target RSSI at which the link is good
            int from = rssi + GOOD_LINK_RSSI_RANGE_MIN;
            int to = rssi + GOOD_LINK_RSSI_RANGE_MAX;
            mGoodLinkTargetRssi = findRssiTarget(from, to, GOOD_LINK_LOSS_THRESHOLD);
            mGoodLinkTargetRssi += GOOD_LINK_TARGET[mGoodLinkTargetIndex].RSSI_ADJ_DBM;
            if (mGoodLinkTargetIndex < GOOD_LINK_TARGET.length - 1) mGoodLinkTargetIndex++;

            // calculate max avoidance time to prevent avoiding forever
            int p = 0, pmax = MAX_AVOID_TIME.length - 1;
            while (p < pmax && rssi >= MAX_AVOID_TIME[p + 1].MIN_RSSI_DBM) p++;
            long avoidMax = MAX_AVOID_TIME[p].TIME_MS;

            // don't avoid if max avoidance time is 0 (RSSI is super high)
            if (avoidMax <= 0) return false;

            // set max avoidance time, send poor link notification
            mBssidAvoidTimeMax = now + avoidMax;

            if (DBG) logd("goodRssi=" + mGoodLinkTargetRssi + " goodCount=" + mGoodLinkTargetCount
                    + " lastGood=" + lastGood + " lastPoor=" + lastPoor + " avoidMax=" + avoidMax);

            return true;
        }

        /**
         * A new BSSID is connected, recalculate target RSSI threshold
         */
        public void newLinkDetected() {
            // if this BSSID is currently being avoided, the reuse those values
            if (mBssidAvoidTimeMax > 0) {
                if (DBG) logd("Previous avoidance still in effect, rssi=" + mGoodLinkTargetRssi
                        + " count=" + mGoodLinkTargetCount);
                return;
            }

            // calculate a new RSSI threshold for new link verifying
            int from = BSSID_STAT_RANGE_LOW_DBM;
            int to = BSSID_STAT_RANGE_HIGH_DBM;
            mGoodLinkTargetRssi = findRssiTarget(from, to, GOOD_LINK_LOSS_THRESHOLD);
            mGoodLinkTargetCount = 1;
            mBssidAvoidTimeMax = SystemClock.elapsedRealtime() + MAX_AVOID_TIME[0].TIME_MS;
            if (DBG) logd("New link verifying target set, rssi=" + mGoodLinkTargetRssi + " count="
                    + mGoodLinkTargetCount);
        }

        /**
         * Return the first RSSI within the range where loss[rssi] < threshold
         *
         * @param from start scanning from this RSSI
         * @param to stop scanning at this RSSI
         * @param threshold target threshold for scanning
         * @return target RSSI
         */
        public int findRssiTarget(int from, int to, double threshold) {
            from -= mRssiBase;
            to -= mRssiBase;
            int emptyCount = 0;
            int d = from < to ? 1 : -1;
            for (int i = from; i != to; i += d)
                // don't use a data point if it volume is too small (statistically unreliable)
                if (i >= 0 && i < mEntriesSize && mEntries[i].mVolume > 1.0) {
                    emptyCount = 0;
                    if (mEntries[i].mValue < threshold) {
                        // scan target found
                        int rssi = mRssiBase + i;
                        if (DBG) {
                            DecimalFormat df = new DecimalFormat("#.##");
                            logd("Scan target found: rssi=" + rssi + " threshold="
                                    + df.format(threshold * 100) + "% value="
                                    + df.format(mEntries[i].mValue * 100) + "% volume="
                                    + df.format(mEntries[i].mVolume));
                        }
                        return rssi;
                    }
                } else if (++emptyCount >= BSSID_STAT_EMPTY_COUNT) {
                    // cache has insufficient data around this RSSI, use preset loss instead
                    int rssi = mRssiBase + i;
                    double lossPreset = presetLoss(rssi);
                    if (lossPreset < threshold) {
                        if (DBG) {
                            DecimalFormat df = new DecimalFormat("#.##");
                            logd("Scan target found: rssi=" + rssi + " threshold="
                                    + df.format(threshold * 100) + "% value="
                                    + df.format(lossPreset * 100) + "% volume=preset");
                        }
                        return rssi;
                    }
                }

            return mRssiBase + to;
        }
    }
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
}
