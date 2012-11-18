/*
 * Copyright (C) 2006 The Android Open Source Project
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

package com.android.server;

import com.android.internal.app.IBatteryStats;
import com.android.server.am.BatteryStatsService;

import android.app.ActivityManagerNative;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.BatteryManager;
import android.os.Binder;
import android.os.FileUtils;
<<<<<<< HEAD
=======
import android.os.Handler;
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
import android.os.IBinder;
import android.os.DropBoxManager;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.SystemClock;
import android.os.UEventObserver;
<<<<<<< HEAD
=======
import android.os.UserHandle;
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
import android.provider.Settings;
import android.util.EventLog;
import android.util.Slog;

import java.io.File;
import java.io.FileDescriptor;
<<<<<<< HEAD
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
=======
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a


/**
 * <p>BatteryService monitors the charging status, and charge level of the device
 * battery.  When these values change this service broadcasts the new values
 * to all {@link android.content.BroadcastReceiver IntentReceivers} that are
 * watching the {@link android.content.Intent#ACTION_BATTERY_CHANGED
 * BATTERY_CHANGED} action.</p>
 * <p>The new values are stored in the Intent data and can be retrieved by
 * calling {@link android.content.Intent#getExtra Intent.getExtra} with the
 * following keys:</p>
 * <p>&quot;scale&quot; - int, the maximum value for the charge level</p>
 * <p>&quot;level&quot; - int, charge level, from 0 through &quot;scale&quot; inclusive</p>
 * <p>&quot;status&quot; - String, the current charging status.<br />
 * <p>&quot;health&quot; - String, the current battery health.<br />
 * <p>&quot;present&quot; - boolean, true if the battery is present<br />
 * <p>&quot;icon-small&quot; - int, suggested small icon to use for this state</p>
 * <p>&quot;plugged&quot; - int, 0 if the device is not plugged in; 1 if plugged
 * into an AC power adapter; 2 if plugged in via USB.</p>
 * <p>&quot;voltage&quot; - int, current battery voltage in millivolts</p>
 * <p>&quot;temperature&quot; - int, current battery temperature in tenths of
 * a degree Centigrade</p>
 * <p>&quot;technology&quot; - String, the type of battery installed, e.g. "Li-ion"</p>
<<<<<<< HEAD
 */
class BatteryService extends Binder {
    private static final String TAG = BatteryService.class.getSimpleName();

    private static final boolean LOCAL_LOGV = false;

    static final int BATTERY_SCALE = 100;    // battery capacity is a percentage
=======
 *
 * <p>
 * The battery service may be called by the power manager while holding its locks so
 * we take care to post all outcalls into the activity manager to a handler.
 *
 * FIXME: Ideally the power manager would perform all of its calls into the battery
 * service asynchronously itself.
 * </p>
 */
public final class BatteryService extends Binder {
    private static final String TAG = BatteryService.class.getSimpleName();

    private static final boolean DEBUG = false;

    private static final int BATTERY_SCALE = 100;    // battery capacity is a percentage
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a

    // Used locally for determining when to make a last ditch effort to log
    // discharge stats before the device dies.
    private int mCriticalBatteryLevel;

    private static final int DUMP_MAX_LENGTH = 24 * 1024;
    private static final String[] DUMPSYS_ARGS = new String[] { "--checkin", "-u" };
    private static final String BATTERY_STATS_SERVICE_NAME = "batteryinfo";

    private static final String DUMPSYS_DATA_PATH = "/data/system/";

    // This should probably be exposed in the API, though it's not critical
    private static final int BATTERY_PLUGGED_NONE = 0;

    private final Context mContext;
    private final IBatteryStats mBatteryStats;
<<<<<<< HEAD

    private boolean mAcOnline;
    private boolean mUsbOnline;
=======
    private final Handler mHandler;

    private final Object mLock = new Object();

    /* Begin native fields: All of these fields are set by native code. */
    private boolean mAcOnline;
    private boolean mUsbOnline;
    private boolean mWirelessOnline;
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
    private int mBatteryStatus;
    private int mBatteryHealth;
    private boolean mBatteryPresent;
    private int mBatteryLevel;
    private int mBatteryVoltage;
    private int mBatteryTemperature;
    private String mBatteryTechnology;
    private boolean mBatteryLevelCritical;
<<<<<<< HEAD
    private int mInvalidCharger;
=======
    /* End native fields. */
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a

    private int mLastBatteryStatus;
    private int mLastBatteryHealth;
    private boolean mLastBatteryPresent;
    private int mLastBatteryLevel;
    private int mLastBatteryVoltage;
    private int mLastBatteryTemperature;
    private boolean mLastBatteryLevelCritical;
<<<<<<< HEAD
=======

    private int mInvalidCharger;
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
    private int mLastInvalidCharger;

    private int mLowBatteryWarningLevel;
    private int mLowBatteryCloseWarningLevel;
<<<<<<< HEAD
=======
    private int mShutdownBatteryTemperature;
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a

    private int mPlugType;
    private int mLastPlugType = -1; // Extra state so we can detect first run

    private long mDischargeStartTime;
    private int mDischargeStartLevel;

<<<<<<< HEAD
=======
    private boolean mUpdatesStopped;

>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
    private Led mLed;

    private boolean mSentLowBatteryBroadcast = false;

<<<<<<< HEAD
    public BatteryService(Context context, LightsService lights) {
        mContext = context;
=======
    private native void native_update();

    public BatteryService(Context context, LightsService lights) {
        mContext = context;
        mHandler = new Handler(true /*async*/);
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
        mLed = new Led(context, lights);
        mBatteryStats = BatteryStatsService.getService();

        mCriticalBatteryLevel = mContext.getResources().getInteger(
                com.android.internal.R.integer.config_criticalBatteryWarningLevel);
        mLowBatteryWarningLevel = mContext.getResources().getInteger(
                com.android.internal.R.integer.config_lowBatteryWarningLevel);
        mLowBatteryCloseWarningLevel = mContext.getResources().getInteger(
                com.android.internal.R.integer.config_lowBatteryCloseWarningLevel);
<<<<<<< HEAD
=======
        mShutdownBatteryTemperature = mContext.getResources().getInteger(
                com.android.internal.R.integer.config_shutdownBatteryTemperature);
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a

        mPowerSupplyObserver.startObserving("SUBSYSTEM=power_supply");

        // watch for invalid charger messages if the invalid_charger switch exists
        if (new File("/sys/devices/virtual/switch/invalid_charger/state").exists()) {
<<<<<<< HEAD
            mInvalidChargerObserver.startObserving("DEVPATH=/devices/virtual/switch/invalid_charger");
        }

        // set initial status
        update();
    }

    final boolean isPowered() {
        // assume we are powered if battery state is unknown so the "stay on while plugged in" option will work.
        return (mAcOnline || mUsbOnline || mBatteryStatus == BatteryManager.BATTERY_STATUS_UNKNOWN);
    }

    final boolean isPowered(int plugTypeSet) {
=======
            mInvalidChargerObserver.startObserving(
                    "DEVPATH=/devices/virtual/switch/invalid_charger");
        }

        // set initial status
        synchronized (mLock) {
            updateLocked();
        }
    }

    void systemReady() {
        // check our power situation now that it is safe to display the shutdown dialog.
        synchronized (mLock) {
            shutdownIfNoPowerLocked();
            shutdownIfOverTempLocked();
        }
    }

    /**
     * Returns true if the device is plugged into any of the specified plug types.
     */
    public boolean isPowered(int plugTypeSet) {
        synchronized (mLock) {
            return isPoweredLocked(plugTypeSet);
        }
    }

    private boolean isPoweredLocked(int plugTypeSet) {
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
        // assume we are powered if battery state is unknown so
        // the "stay on while plugged in" option will work.
        if (mBatteryStatus == BatteryManager.BATTERY_STATUS_UNKNOWN) {
            return true;
        }
<<<<<<< HEAD
        if (plugTypeSet == 0) {
            return false;
        }
        int plugTypeBit = 0;
        if (mAcOnline) {
            plugTypeBit |= BatteryManager.BATTERY_PLUGGED_AC;
        }
        if (mUsbOnline) {
            plugTypeBit |= BatteryManager.BATTERY_PLUGGED_USB;
        }
        return (plugTypeSet & plugTypeBit) != 0;
    }

    final int getPlugType() {
        return mPlugType;
    }

    private UEventObserver mPowerSupplyObserver = new UEventObserver() {
        @Override
        public void onUEvent(UEventObserver.UEvent event) {
            update();
        }
    };

    private UEventObserver mInvalidChargerObserver = new UEventObserver() {
        @Override
        public void onUEvent(UEventObserver.UEvent event) {
            int invalidCharger = "1".equals(event.get("SWITCH_STATE")) ? 1 : 0;
            if (mInvalidCharger != invalidCharger) {
                mInvalidCharger = invalidCharger;
                update();
            }
        }
    };

    // returns battery level as a percentage
    final int getBatteryLevel() {
        return mBatteryLevel;
    }

    void systemReady() {
        // check our power situation now that it is safe to display the shutdown dialog.
        shutdownIfNoPower();
        shutdownIfOverTemp();
    }

    private final void shutdownIfNoPower() {
        // shut down gracefully if our battery is critically low and we are not powered.
        // wait until the system has booted before attempting to display the shutdown dialog.
        if (mBatteryLevel == 0 && !isPowered() && ActivityManagerNative.isSystemReady()) {
            Intent intent = new Intent(Intent.ACTION_REQUEST_SHUTDOWN);
            intent.putExtra(Intent.EXTRA_KEY_CONFIRM, false);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            mContext.startActivity(intent);
        }
    }

    private final void shutdownIfOverTemp() {
        // shut down gracefully if temperature is too high (> 68.0C)
        // wait until the system has booted before attempting to display the shutdown dialog.
        if (mBatteryTemperature > 680 && ActivityManagerNative.isSystemReady()) {
            Intent intent = new Intent(Intent.ACTION_REQUEST_SHUTDOWN);
            intent.putExtra(Intent.EXTRA_KEY_CONFIRM, false);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            mContext.startActivity(intent);
        }
    }

    private native void native_update();

    private synchronized final void update() {
        native_update();
        processValues();
    }

    private void processValues() {
        boolean logOutlier = false;
        long dischargeDuration = 0;

        mBatteryLevelCritical = mBatteryLevel <= mCriticalBatteryLevel;
=======
        if ((plugTypeSet & BatteryManager.BATTERY_PLUGGED_AC) != 0 && mAcOnline) {
            return true;
        }
        if ((plugTypeSet & BatteryManager.BATTERY_PLUGGED_USB) != 0 && mUsbOnline) {
            return true;
        }
        if ((plugTypeSet & BatteryManager.BATTERY_PLUGGED_WIRELESS) != 0 && mWirelessOnline) {
            return true;
        }
        return false;
    }

    /**
     * Returns the current plug type.
     */
    public int getPlugType() {
        synchronized (mLock) {
            return mPlugType;
        }
    }

    /**
     * Returns battery level as a percentage.
     */
    public int getBatteryLevel() {
        synchronized (mLock) {
            return mBatteryLevel;
        }
    }

    /**
     * Returns true if battery level is below the first warning threshold.
     */
    public boolean isBatteryLow() {
        synchronized (mLock) {
            return mBatteryPresent && mBatteryLevel <= mLowBatteryWarningLevel;
        }
    }

    private void shutdownIfNoPowerLocked() {
        // shut down gracefully if our battery is critically low and we are not powered.
        // wait until the system has booted before attempting to display the shutdown dialog.
        if (mBatteryLevel == 0 && !isPoweredLocked(BatteryManager.BATTERY_PLUGGED_ANY)) {
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    if (ActivityManagerNative.isSystemReady()) {
                        Intent intent = new Intent(Intent.ACTION_REQUEST_SHUTDOWN);
                        intent.putExtra(Intent.EXTRA_KEY_CONFIRM, false);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        mContext.startActivityAsUser(intent, UserHandle.CURRENT);
                    }
                }
            });
        }
    }

    private void shutdownIfOverTempLocked() {
        // shut down gracefully if temperature is too high (> 68.0C by default)
        // wait until the system has booted before attempting to display the
        // shutdown dialog.
        if (mBatteryTemperature > mShutdownBatteryTemperature) {
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    if (ActivityManagerNative.isSystemReady()) {
                        Intent intent = new Intent(Intent.ACTION_REQUEST_SHUTDOWN);
                        intent.putExtra(Intent.EXTRA_KEY_CONFIRM, false);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        mContext.startActivityAsUser(intent, UserHandle.CURRENT);
                    }
                }
            });
        }
    }

    private void updateLocked() {
        if (!mUpdatesStopped) {
            // Update the values of mAcOnline, et. all.
            native_update();

            // Process the new values.
            processValuesLocked();
        }
    }

    private void processValuesLocked() {
        boolean logOutlier = false;
        long dischargeDuration = 0;

        mBatteryLevelCritical = (mBatteryLevel <= mCriticalBatteryLevel);
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
        if (mAcOnline) {
            mPlugType = BatteryManager.BATTERY_PLUGGED_AC;
        } else if (mUsbOnline) {
            mPlugType = BatteryManager.BATTERY_PLUGGED_USB;
<<<<<<< HEAD
        } else {
            mPlugType = BATTERY_PLUGGED_NONE;
        }
        
=======
        } else if (mWirelessOnline) {
            mPlugType = BatteryManager.BATTERY_PLUGGED_WIRELESS;
        } else {
            mPlugType = BATTERY_PLUGGED_NONE;
        }

        if (DEBUG) {
            Slog.d(TAG, "Processing new values: "
                    + "mAcOnline=" + mAcOnline
                    + ", mUsbOnline=" + mUsbOnline
                    + ", mWirelessOnline=" + mWirelessOnline
                    + ", mBatteryStatus=" + mBatteryStatus
                    + ", mBatteryHealth=" + mBatteryHealth
                    + ", mBatteryPresent=" + mBatteryPresent
                    + ", mBatteryLevel=" + mBatteryLevel
                    + ", mBatteryTechnology=" + mBatteryTechnology
                    + ", mBatteryVoltage=" + mBatteryVoltage
                    + ", mBatteryTemperature=" + mBatteryTemperature
                    + ", mBatteryLevelCritical=" + mBatteryLevelCritical
                    + ", mPlugType=" + mPlugType);
        }

>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
        // Let the battery stats keep track of the current level.
        try {
            mBatteryStats.setBatteryState(mBatteryStatus, mBatteryHealth,
                    mPlugType, mBatteryLevel, mBatteryTemperature,
                    mBatteryVoltage);
        } catch (RemoteException e) {
            // Should never happen.
        }
<<<<<<< HEAD
        
        shutdownIfNoPower();
        shutdownIfOverTemp();
=======

        shutdownIfNoPowerLocked();
        shutdownIfOverTempLocked();
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a

        if (mBatteryStatus != mLastBatteryStatus ||
                mBatteryHealth != mLastBatteryHealth ||
                mBatteryPresent != mLastBatteryPresent ||
                mBatteryLevel != mLastBatteryLevel ||
                mPlugType != mLastPlugType ||
                mBatteryVoltage != mLastBatteryVoltage ||
                mBatteryTemperature != mLastBatteryTemperature ||
                mInvalidCharger != mLastInvalidCharger) {

            if (mPlugType != mLastPlugType) {
                if (mLastPlugType == BATTERY_PLUGGED_NONE) {
                    // discharging -> charging

                    // There's no value in this data unless we've discharged at least once and the
                    // battery level has changed; so don't log until it does.
                    if (mDischargeStartTime != 0 && mDischargeStartLevel != mBatteryLevel) {
                        dischargeDuration = SystemClock.elapsedRealtime() - mDischargeStartTime;
                        logOutlier = true;
                        EventLog.writeEvent(EventLogTags.BATTERY_DISCHARGE, dischargeDuration,
                                mDischargeStartLevel, mBatteryLevel);
                        // make sure we see a discharge event before logging again
                        mDischargeStartTime = 0;
                    }
                } else if (mPlugType == BATTERY_PLUGGED_NONE) {
                    // charging -> discharging or we just powered up
                    mDischargeStartTime = SystemClock.elapsedRealtime();
                    mDischargeStartLevel = mBatteryLevel;
                }
            }
            if (mBatteryStatus != mLastBatteryStatus ||
                    mBatteryHealth != mLastBatteryHealth ||
                    mBatteryPresent != mLastBatteryPresent ||
                    mPlugType != mLastPlugType) {
                EventLog.writeEvent(EventLogTags.BATTERY_STATUS,
                        mBatteryStatus, mBatteryHealth, mBatteryPresent ? 1 : 0,
                        mPlugType, mBatteryTechnology);
            }
            if (mBatteryLevel != mLastBatteryLevel ||
                    mBatteryVoltage != mLastBatteryVoltage ||
                    mBatteryTemperature != mLastBatteryTemperature) {
                EventLog.writeEvent(EventLogTags.BATTERY_LEVEL,
                        mBatteryLevel, mBatteryVoltage, mBatteryTemperature);
            }
            if (mBatteryLevelCritical && !mLastBatteryLevelCritical &&
                    mPlugType == BATTERY_PLUGGED_NONE) {
                // We want to make sure we log discharge cycle outliers
                // if the battery is about to die.
                dischargeDuration = SystemClock.elapsedRealtime() - mDischargeStartTime;
                logOutlier = true;
            }

            final boolean plugged = mPlugType != BATTERY_PLUGGED_NONE;
            final boolean oldPlugged = mLastPlugType != BATTERY_PLUGGED_NONE;

            /* The ACTION_BATTERY_LOW broadcast is sent in these situations:
             * - is just un-plugged (previously was plugged) and battery level is
             *   less than or equal to WARNING, or
             * - is not plugged and battery level falls to WARNING boundary
             *   (becomes <= mLowBatteryWarningLevel).
             */
            final boolean sendBatteryLow = !plugged
                    && mBatteryStatus != BatteryManager.BATTERY_STATUS_UNKNOWN
                    && mBatteryLevel <= mLowBatteryWarningLevel
                    && (oldPlugged || mLastBatteryLevel > mLowBatteryWarningLevel);

<<<<<<< HEAD
            sendIntent();
=======
            sendIntentLocked();
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a

            // Separate broadcast is sent for power connected / not connected
            // since the standard intent will not wake any applications and some
            // applications may want to have smart behavior based on this.
<<<<<<< HEAD
            Intent statusIntent = new Intent();
            statusIntent.setFlags(Intent.FLAG_RECEIVER_REGISTERED_ONLY_BEFORE_BOOT);
            if (mPlugType != 0 && mLastPlugType == 0) {
                statusIntent.setAction(Intent.ACTION_POWER_CONNECTED);
                mContext.sendBroadcast(statusIntent);
            }
            else if (mPlugType == 0 && mLastPlugType != 0) {
                statusIntent.setAction(Intent.ACTION_POWER_DISCONNECTED);
                mContext.sendBroadcast(statusIntent);
=======
            if (mPlugType != 0 && mLastPlugType == 0) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        Intent statusIntent = new Intent(Intent.ACTION_POWER_CONNECTED);
                        statusIntent.setFlags(Intent.FLAG_RECEIVER_REGISTERED_ONLY_BEFORE_BOOT);
                        mContext.sendBroadcastAsUser(statusIntent, UserHandle.ALL);
                    }
                });
            }
            else if (mPlugType == 0 && mLastPlugType != 0) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        Intent statusIntent = new Intent(Intent.ACTION_POWER_DISCONNECTED);
                        statusIntent.setFlags(Intent.FLAG_RECEIVER_REGISTERED_ONLY_BEFORE_BOOT);
                        mContext.sendBroadcastAsUser(statusIntent, UserHandle.ALL);
                    }
                });
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
            }

            if (sendBatteryLow) {
                mSentLowBatteryBroadcast = true;
<<<<<<< HEAD
                statusIntent.setAction(Intent.ACTION_BATTERY_LOW);
                mContext.sendBroadcast(statusIntent);
            } else if (mSentLowBatteryBroadcast && mLastBatteryLevel >= mLowBatteryCloseWarningLevel) {
                mSentLowBatteryBroadcast = false;
                statusIntent.setAction(Intent.ACTION_BATTERY_OKAY);
                mContext.sendBroadcast(statusIntent);
=======
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        Intent statusIntent = new Intent(Intent.ACTION_BATTERY_LOW);
                        statusIntent.setFlags(Intent.FLAG_RECEIVER_REGISTERED_ONLY_BEFORE_BOOT);
                        mContext.sendBroadcastAsUser(statusIntent, UserHandle.ALL);
                    }
                });
            } else if (mSentLowBatteryBroadcast && mLastBatteryLevel >= mLowBatteryCloseWarningLevel) {
                mSentLowBatteryBroadcast = false;
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        Intent statusIntent = new Intent(Intent.ACTION_BATTERY_OKAY);
                        statusIntent.setFlags(Intent.FLAG_RECEIVER_REGISTERED_ONLY_BEFORE_BOOT);
                        mContext.sendBroadcastAsUser(statusIntent, UserHandle.ALL);
                    }
                });
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
            }

            // Update the battery LED
            mLed.updateLightsLocked();

            // This needs to be done after sendIntent() so that we get the lastest battery stats.
            if (logOutlier && dischargeDuration != 0) {
<<<<<<< HEAD
                logOutlier(dischargeDuration);
=======
                logOutlierLocked(dischargeDuration);
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
            }

            mLastBatteryStatus = mBatteryStatus;
            mLastBatteryHealth = mBatteryHealth;
            mLastBatteryPresent = mBatteryPresent;
            mLastBatteryLevel = mBatteryLevel;
            mLastPlugType = mPlugType;
            mLastBatteryVoltage = mBatteryVoltage;
            mLastBatteryTemperature = mBatteryTemperature;
            mLastBatteryLevelCritical = mBatteryLevelCritical;
            mLastInvalidCharger = mInvalidCharger;
        }
    }

<<<<<<< HEAD
    private final void sendIntent() {
        //  Pack up the values and broadcast them to everyone
        Intent intent = new Intent(Intent.ACTION_BATTERY_CHANGED);
        intent.addFlags(Intent.FLAG_RECEIVER_REGISTERED_ONLY
                | Intent.FLAG_RECEIVER_REPLACE_PENDING);

        int icon = getIcon(mBatteryLevel);
=======
    private void sendIntentLocked() {
        //  Pack up the values and broadcast them to everyone
        final Intent intent = new Intent(Intent.ACTION_BATTERY_CHANGED);
        intent.addFlags(Intent.FLAG_RECEIVER_REGISTERED_ONLY
                | Intent.FLAG_RECEIVER_REPLACE_PENDING);

        int icon = getIconLocked(mBatteryLevel);
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a

        intent.putExtra(BatteryManager.EXTRA_STATUS, mBatteryStatus);
        intent.putExtra(BatteryManager.EXTRA_HEALTH, mBatteryHealth);
        intent.putExtra(BatteryManager.EXTRA_PRESENT, mBatteryPresent);
        intent.putExtra(BatteryManager.EXTRA_LEVEL, mBatteryLevel);
        intent.putExtra(BatteryManager.EXTRA_SCALE, BATTERY_SCALE);
        intent.putExtra(BatteryManager.EXTRA_ICON_SMALL, icon);
        intent.putExtra(BatteryManager.EXTRA_PLUGGED, mPlugType);
        intent.putExtra(BatteryManager.EXTRA_VOLTAGE, mBatteryVoltage);
        intent.putExtra(BatteryManager.EXTRA_TEMPERATURE, mBatteryTemperature);
        intent.putExtra(BatteryManager.EXTRA_TECHNOLOGY, mBatteryTechnology);
        intent.putExtra(BatteryManager.EXTRA_INVALID_CHARGER, mInvalidCharger);

<<<<<<< HEAD
        if (false) {
            Slog.d(TAG, "level:" + mBatteryLevel +
                    " scale:" + BATTERY_SCALE + " status:" + mBatteryStatus +
                    " health:" + mBatteryHealth +  " present:" + mBatteryPresent +
                    " voltage: " + mBatteryVoltage +
                    " temperature: " + mBatteryTemperature +
                    " technology: " + mBatteryTechnology +
                    " AC powered:" + mAcOnline + " USB powered:" + mUsbOnline +
                    " icon:" + icon  + " invalid charger:" + mInvalidCharger);
        }

        ActivityManagerNative.broadcastStickyIntent(intent, null);
    }

    private final void logBatteryStats() {
=======
        if (DEBUG) {
            Slog.d(TAG, "Sending ACTION_BATTERY_CHANGED.  level:" + mBatteryLevel +
                    ", scale:" + BATTERY_SCALE + ", status:" + mBatteryStatus +
                    ", health:" + mBatteryHealth +  ", present:" + mBatteryPresent +
                    ", voltage: " + mBatteryVoltage +
                    ", temperature: " + mBatteryTemperature +
                    ", technology: " + mBatteryTechnology +
                    ", AC powered:" + mAcOnline + ", USB powered:" + mUsbOnline +
                    ", Wireless powered:" + mWirelessOnline +
                    ", icon:" + icon  + ", invalid charger:" + mInvalidCharger);
        }

        mHandler.post(new Runnable() {
            @Override
            public void run() {
                ActivityManagerNative.broadcastStickyIntent(intent, null, UserHandle.USER_ALL);
            }
        });
    }

    private void logBatteryStatsLocked() {
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
        IBinder batteryInfoService = ServiceManager.getService(BATTERY_STATS_SERVICE_NAME);
        if (batteryInfoService == null) return;

        DropBoxManager db = (DropBoxManager) mContext.getSystemService(Context.DROPBOX_SERVICE);
        if (db == null || !db.isTagEnabled("BATTERY_DISCHARGE_INFO")) return;

        File dumpFile = null;
        FileOutputStream dumpStream = null;
        try {
            // dump the service to a file
            dumpFile = new File(DUMPSYS_DATA_PATH + BATTERY_STATS_SERVICE_NAME + ".dump");
            dumpStream = new FileOutputStream(dumpFile);
            batteryInfoService.dump(dumpStream.getFD(), DUMPSYS_ARGS);
            FileUtils.sync(dumpStream);

            // add dump file to drop box
            db.addFile("BATTERY_DISCHARGE_INFO", dumpFile, DropBoxManager.IS_TEXT);
        } catch (RemoteException e) {
            Slog.e(TAG, "failed to dump battery service", e);
        } catch (IOException e) {
            Slog.e(TAG, "failed to write dumpsys file", e);
        } finally {
            // make sure we clean up
            if (dumpStream != null) {
                try {
                    dumpStream.close();
                } catch (IOException e) {
                    Slog.e(TAG, "failed to close dumpsys output stream");
                }
            }
            if (dumpFile != null && !dumpFile.delete()) {
                Slog.e(TAG, "failed to delete temporary dumpsys file: "
                        + dumpFile.getAbsolutePath());
            }
        }
    }

<<<<<<< HEAD
    private final void logOutlier(long duration) {
        ContentResolver cr = mContext.getContentResolver();
        String dischargeThresholdString = Settings.Secure.getString(cr,
                Settings.Secure.BATTERY_DISCHARGE_THRESHOLD);
        String durationThresholdString = Settings.Secure.getString(cr,
                Settings.Secure.BATTERY_DISCHARGE_DURATION_THRESHOLD);
=======
    private void logOutlierLocked(long duration) {
        ContentResolver cr = mContext.getContentResolver();
        String dischargeThresholdString = Settings.Global.getString(cr,
                Settings.Global.BATTERY_DISCHARGE_THRESHOLD);
        String durationThresholdString = Settings.Global.getString(cr,
                Settings.Global.BATTERY_DISCHARGE_DURATION_THRESHOLD);
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a

        if (dischargeThresholdString != null && durationThresholdString != null) {
            try {
                long durationThreshold = Long.parseLong(durationThresholdString);
                int dischargeThreshold = Integer.parseInt(dischargeThresholdString);
                if (duration <= durationThreshold &&
                        mDischargeStartLevel - mBatteryLevel >= dischargeThreshold) {
                    // If the discharge cycle is bad enough we want to know about it.
<<<<<<< HEAD
                    logBatteryStats();
                }
                if (LOCAL_LOGV) Slog.v(TAG, "duration threshold: " + durationThreshold +
                        " discharge threshold: " + dischargeThreshold);
                if (LOCAL_LOGV) Slog.v(TAG, "duration: " + duration + " discharge: " +
=======
                    logBatteryStatsLocked();
                }
                if (DEBUG) Slog.v(TAG, "duration threshold: " + durationThreshold +
                        " discharge threshold: " + dischargeThreshold);
                if (DEBUG) Slog.v(TAG, "duration: " + duration + " discharge: " +
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
                        (mDischargeStartLevel - mBatteryLevel));
            } catch (NumberFormatException e) {
                Slog.e(TAG, "Invalid DischargeThresholds GService string: " +
                        durationThresholdString + " or " + dischargeThresholdString);
                return;
            }
        }
    }

<<<<<<< HEAD
    private final int getIcon(int level) {
=======
    private int getIconLocked(int level) {
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
        if (mBatteryStatus == BatteryManager.BATTERY_STATUS_CHARGING) {
            return com.android.internal.R.drawable.stat_sys_battery_charge;
        } else if (mBatteryStatus == BatteryManager.BATTERY_STATUS_DISCHARGING) {
            return com.android.internal.R.drawable.stat_sys_battery;
        } else if (mBatteryStatus == BatteryManager.BATTERY_STATUS_NOT_CHARGING
                || mBatteryStatus == BatteryManager.BATTERY_STATUS_FULL) {
<<<<<<< HEAD
            if (isPowered() && mBatteryLevel >= 100) {
=======
            if (isPoweredLocked(BatteryManager.BATTERY_PLUGGED_ANY)
                    && mBatteryLevel >= 100) {
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
                return com.android.internal.R.drawable.stat_sys_battery_charge;
            } else {
                return com.android.internal.R.drawable.stat_sys_battery;
            }
        } else {
            return com.android.internal.R.drawable.stat_sys_battery_unknown;
        }
    }

    @Override
    protected void dump(FileDescriptor fd, PrintWriter pw, String[] args) {
        if (mContext.checkCallingOrSelfPermission(android.Manifest.permission.DUMP)
                != PackageManager.PERMISSION_GRANTED) {

            pw.println("Permission Denial: can't dump Battery service from from pid="
                    + Binder.getCallingPid()
                    + ", uid=" + Binder.getCallingUid());
            return;
        }

<<<<<<< HEAD
        if (args == null || args.length == 0 || "-a".equals(args[0])) {
            synchronized (this) {
                pw.println("Current Battery Service state:");
                pw.println("  AC powered: " + mAcOnline);
                pw.println("  USB powered: " + mUsbOnline);
=======
        synchronized (mLock) {
            if (args == null || args.length == 0 || "-a".equals(args[0])) {
                pw.println("Current Battery Service state:");
                if (mUpdatesStopped) {
                    pw.println("  (UPDATES STOPPED -- use 'reset' to restart)");
                }
                pw.println("  AC powered: " + mAcOnline);
                pw.println("  USB powered: " + mUsbOnline);
                pw.println("  Wireless powered: " + mWirelessOnline);
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
                pw.println("  status: " + mBatteryStatus);
                pw.println("  health: " + mBatteryHealth);
                pw.println("  present: " + mBatteryPresent);
                pw.println("  level: " + mBatteryLevel);
                pw.println("  scale: " + BATTERY_SCALE);
                pw.println("  voltage:" + mBatteryVoltage);
                pw.println("  temperature: " + mBatteryTemperature);
                pw.println("  technology: " + mBatteryTechnology);
<<<<<<< HEAD
            }
        } else if (false) {
            // DO NOT SUBMIT WITH THIS TURNED ON
            if (args.length == 3 && "set".equals(args[0])) {
=======
            } else if (args.length == 3 && "set".equals(args[0])) {
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
                String key = args[1];
                String value = args[2];
                try {
                    boolean update = true;
                    if ("ac".equals(key)) {
                        mAcOnline = Integer.parseInt(value) != 0;
                    } else if ("usb".equals(key)) {
                        mUsbOnline = Integer.parseInt(value) != 0;
<<<<<<< HEAD
=======
                    } else if ("wireless".equals(key)) {
                        mWirelessOnline = Integer.parseInt(value) != 0;
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
                    } else if ("status".equals(key)) {
                        mBatteryStatus = Integer.parseInt(value);
                    } else if ("level".equals(key)) {
                        mBatteryLevel = Integer.parseInt(value);
                    } else if ("invalid".equals(key)) {
                        mInvalidCharger = Integer.parseInt(value);
                    } else {
<<<<<<< HEAD
                        update = false;
                    }
                    if (update) {
                        processValues();
=======
                        pw.println("Unknown set option: " + key);
                        update = false;
                    }
                    if (update) {
                        mUpdatesStopped = true;
                        processValuesLocked();
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
                    }
                } catch (NumberFormatException ex) {
                    pw.println("Bad value: " + value);
                }
<<<<<<< HEAD
=======
            } else if (args.length == 1 && "reset".equals(args[0])) {
                mUpdatesStopped = false;
                updateLocked();
            } else {
                pw.println("Dump current battery state, or:");
                pw.println("  set ac|usb|wireless|status|level|invalid <value>");
                pw.println("  reset");
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
            }
        }
    }

<<<<<<< HEAD
    class Led {
        private LightsService mLightsService;
        private LightsService.Light mBatteryLight;

        private int mBatteryLowARGB;
        private int mBatteryMediumARGB;
        private int mBatteryFullARGB;
        private int mBatteryLedOn;
        private int mBatteryLedOff;

        private boolean mBatteryCharging;
        private boolean mBatteryLow;
        private boolean mBatteryFull;

        Led(Context context, LightsService lights) {
            mLightsService = lights;
            mBatteryLight = lights.getLight(LightsService.LIGHT_ID_BATTERY);

            mBatteryLowARGB = mContext.getResources().getInteger(
                    com.android.internal.R.integer.config_notificationsBatteryLowARGB);
            mBatteryMediumARGB = mContext.getResources().getInteger(
                    com.android.internal.R.integer.config_notificationsBatteryMediumARGB);
            mBatteryFullARGB = mContext.getResources().getInteger(
                    com.android.internal.R.integer.config_notificationsBatteryFullARGB);
            mBatteryLedOn = mContext.getResources().getInteger(
                    com.android.internal.R.integer.config_notificationsBatteryLedOn);
            mBatteryLedOff = mContext.getResources().getInteger(
=======
    private final UEventObserver mPowerSupplyObserver = new UEventObserver() {
        @Override
        public void onUEvent(UEventObserver.UEvent event) {
            synchronized (mLock) {
                updateLocked();
            }
        }
    };

    private final UEventObserver mInvalidChargerObserver = new UEventObserver() {
        @Override
        public void onUEvent(UEventObserver.UEvent event) {
            final int invalidCharger = "1".equals(event.get("SWITCH_STATE")) ? 1 : 0;
            synchronized (mLock) {
                if (mInvalidCharger != invalidCharger) {
                    mInvalidCharger = invalidCharger;
                    updateLocked();
                }
            }
        }
    };

    private final class Led {
        private final LightsService.Light mBatteryLight;

        private final int mBatteryLowARGB;
        private final int mBatteryMediumARGB;
        private final int mBatteryFullARGB;
        private final int mBatteryLedOn;
        private final int mBatteryLedOff;

        public Led(Context context, LightsService lights) {
            mBatteryLight = lights.getLight(LightsService.LIGHT_ID_BATTERY);

            mBatteryLowARGB = context.getResources().getInteger(
                    com.android.internal.R.integer.config_notificationsBatteryLowARGB);
            mBatteryMediumARGB = context.getResources().getInteger(
                    com.android.internal.R.integer.config_notificationsBatteryMediumARGB);
            mBatteryFullARGB = context.getResources().getInteger(
                    com.android.internal.R.integer.config_notificationsBatteryFullARGB);
            mBatteryLedOn = context.getResources().getInteger(
                    com.android.internal.R.integer.config_notificationsBatteryLedOn);
            mBatteryLedOff = context.getResources().getInteger(
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
                    com.android.internal.R.integer.config_notificationsBatteryLedOff);
        }

        /**
         * Synchronize on BatteryService.
         */
<<<<<<< HEAD
        void updateLightsLocked() {
=======
        public void updateLightsLocked() {
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
            final int level = mBatteryLevel;
            final int status = mBatteryStatus;
            if (level < mLowBatteryWarningLevel) {
                if (status == BatteryManager.BATTERY_STATUS_CHARGING) {
                    // Solid red when battery is charging
                    mBatteryLight.setColor(mBatteryLowARGB);
                } else {
                    // Flash red when battery is low and not charging
                    mBatteryLight.setFlashing(mBatteryLowARGB, LightsService.LIGHT_FLASH_TIMED,
                            mBatteryLedOn, mBatteryLedOff);
                }
            } else if (status == BatteryManager.BATTERY_STATUS_CHARGING
                    || status == BatteryManager.BATTERY_STATUS_FULL) {
                if (status == BatteryManager.BATTERY_STATUS_FULL || level >= 90) {
                    // Solid green when full or charging and nearly full
                    mBatteryLight.setColor(mBatteryFullARGB);
                } else {
                    // Solid orange when charging and halfway full
                    mBatteryLight.setColor(mBatteryMediumARGB);
                }
            } else {
                // No lights if not charging and not low
                mBatteryLight.turnOff();
            }
        }
    }
}
<<<<<<< HEAD

=======
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
