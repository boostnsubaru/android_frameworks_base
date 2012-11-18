/*
 * Copyright (C) 2008 The Android Open Source Project
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

<<<<<<< HEAD
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
=======
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
<<<<<<< HEAD
import android.os.SystemClock;
import android.os.UEventObserver;
import android.provider.Settings;
import android.server.BluetoothService;
=======
import android.os.PowerManager;
import android.os.SystemClock;
import android.os.UEventObserver;
import android.os.UserHandle;
import android.provider.Settings;
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
import android.util.Log;
import android.util.Slog;

import java.io.FileNotFoundException;
import java.io.FileReader;

/**
 * <p>DockObserver monitors for a docking station.
 */
<<<<<<< HEAD
class DockObserver extends UEventObserver {
    private static final String TAG = DockObserver.class.getSimpleName();
    private static final boolean LOG = false;
=======
final class DockObserver extends UEventObserver {
    private static final String TAG = DockObserver.class.getSimpleName();
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a

    private static final String DOCK_UEVENT_MATCH = "DEVPATH=/devices/virtual/switch/dock";
    private static final String DOCK_STATE_PATH = "/sys/class/switch/dock/state";

<<<<<<< HEAD
    private static final int MSG_DOCK_STATE = 0;
=======
    private static final int MSG_DOCK_STATE_CHANGED = 0;

    private final Object mLock = new Object();
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a

    private int mDockState = Intent.EXTRA_DOCK_STATE_UNDOCKED;
    private int mPreviousDockState = Intent.EXTRA_DOCK_STATE_UNDOCKED;

    private boolean mSystemReady;

    private final Context mContext;
<<<<<<< HEAD

    private PowerManagerService mPowerManager;

    public DockObserver(Context context, PowerManagerService pm) {
        mContext = context;
        mPowerManager = pm;
        init();  // set initial status

=======
    private final PowerManager mPowerManager;
    private final PowerManager.WakeLock mWakeLock;

    public DockObserver(Context context) {
        mContext = context;

        mPowerManager = (PowerManager)mContext.getSystemService(Context.POWER_SERVICE);
        mWakeLock = mPowerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, TAG);

        init();  // set initial status
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
        startObserving(DOCK_UEVENT_MATCH);
    }

    @Override
    public void onUEvent(UEventObserver.UEvent event) {
        if (Log.isLoggable(TAG, Log.VERBOSE)) {
            Slog.v(TAG, "Dock UEVENT: " + event.toString());
        }

<<<<<<< HEAD
        synchronized (this) {
=======
        synchronized (mLock) {
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
            try {
                int newState = Integer.parseInt(event.get("SWITCH_STATE"));
                if (newState != mDockState) {
                    mPreviousDockState = mDockState;
                    mDockState = newState;
                    if (mSystemReady) {
<<<<<<< HEAD
                        // Don't force screen on when undocking from the desk dock.
                        // The change in power state will do this anyway.
                        // FIXME - we should be configurable.
                        if ((mPreviousDockState != Intent.EXTRA_DOCK_STATE_DESK
                                && mPreviousDockState != Intent.EXTRA_DOCK_STATE_LE_DESK
                                && mPreviousDockState != Intent.EXTRA_DOCK_STATE_HE_DESK) ||
                                mDockState != Intent.EXTRA_DOCK_STATE_UNDOCKED) {
                            mPowerManager.userActivityWithForce(SystemClock.uptimeMillis(),
                                    false, true);
                        }
                        update();
=======
                        // Wake up immediately when docked or undocked.
                        mPowerManager.wakeUp(SystemClock.uptimeMillis());

                        updateLocked();
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
                    }
                }
            } catch (NumberFormatException e) {
                Slog.e(TAG, "Could not parse switch state from event " + event);
            }
        }
    }

<<<<<<< HEAD
    private final void init() {
        char[] buffer = new char[1024];

        try {
            FileReader file = new FileReader(DOCK_STATE_PATH);
            int len = file.read(buffer, 0, 1024);
            file.close();
            mPreviousDockState = mDockState = Integer.valueOf((new String(buffer, 0, len)).trim());
        } catch (FileNotFoundException e) {
            Slog.w(TAG, "This kernel does not have dock station support");
        } catch (Exception e) {
            Slog.e(TAG, "" , e);
=======
    private void init() {
        synchronized (mLock) {
            try {
                char[] buffer = new char[1024];
                FileReader file = new FileReader(DOCK_STATE_PATH);
                try {
                    int len = file.read(buffer, 0, 1024);
                    mDockState = Integer.valueOf((new String(buffer, 0, len)).trim());
                    mPreviousDockState = mDockState;
                } finally {
                    file.close();
                }
            } catch (FileNotFoundException e) {
                Slog.w(TAG, "This kernel does not have dock station support");
            } catch (Exception e) {
                Slog.e(TAG, "" , e);
            }
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
        }
    }

    void systemReady() {
<<<<<<< HEAD
        synchronized (this) {
            // don't bother broadcasting undocked here
            if (mDockState != Intent.EXTRA_DOCK_STATE_UNDOCKED) {
                update();
=======
        synchronized (mLock) {
            // don't bother broadcasting undocked here
            if (mDockState != Intent.EXTRA_DOCK_STATE_UNDOCKED) {
                updateLocked();
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
            }
            mSystemReady = true;
        }
    }

<<<<<<< HEAD
    private final void update() {
        mHandler.sendEmptyMessage(MSG_DOCK_STATE);
    }

    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_DOCK_STATE:
                    synchronized (this) {
                        Slog.i(TAG, "Dock state changed: " + mDockState);

                        final ContentResolver cr = mContext.getContentResolver();

                        if (Settings.Secure.getInt(cr,
                                Settings.Secure.DEVICE_PROVISIONED, 0) == 0) {
                            Slog.i(TAG, "Device not provisioned, skipping dock broadcast");
                            return;
                        }
                        // Pack up the values and broadcast them to everyone
                        Intent intent = new Intent(Intent.ACTION_DOCK_EVENT);
                        intent.addFlags(Intent.FLAG_RECEIVER_REPLACE_PENDING);
                        intent.putExtra(Intent.EXTRA_DOCK_STATE, mDockState);

                        // Check if this is Bluetooth Dock
                        String address = BluetoothService.readDockBluetoothAddress();
                        if (address != null)
                            intent.putExtra(BluetoothDevice.EXTRA_DEVICE,
                                    BluetoothAdapter.getDefaultAdapter().getRemoteDevice(address));

                        // User feedback to confirm dock connection. Particularly
                        // useful for flaky contact pins...
                        if (Settings.System.getInt(cr,
                                Settings.System.DOCK_SOUNDS_ENABLED, 1) == 1)
                        {
                            String whichSound = null;
                            if (mDockState == Intent.EXTRA_DOCK_STATE_UNDOCKED) {
                                if ((mPreviousDockState == Intent.EXTRA_DOCK_STATE_DESK) ||
                                    (mPreviousDockState == Intent.EXTRA_DOCK_STATE_LE_DESK) ||
                                    (mPreviousDockState == Intent.EXTRA_DOCK_STATE_HE_DESK)) {
                                    whichSound = Settings.System.DESK_UNDOCK_SOUND;
                                } else if (mPreviousDockState == Intent.EXTRA_DOCK_STATE_CAR) {
                                    whichSound = Settings.System.CAR_UNDOCK_SOUND;
                                }
                            } else {
                                if ((mDockState == Intent.EXTRA_DOCK_STATE_DESK) ||
                                    (mDockState == Intent.EXTRA_DOCK_STATE_LE_DESK) ||
                                    (mDockState == Intent.EXTRA_DOCK_STATE_HE_DESK)) {
                                    whichSound = Settings.System.DESK_DOCK_SOUND;
                                } else if (mDockState == Intent.EXTRA_DOCK_STATE_CAR) {
                                    whichSound = Settings.System.CAR_DOCK_SOUND;
                                }
                            }

                            if (whichSound != null) {
                                final String soundPath = Settings.System.getString(cr, whichSound);
                                if (soundPath != null) {
                                    final Uri soundUri = Uri.parse("file://" + soundPath);
                                    if (soundUri != null) {
                                        final Ringtone sfx = RingtoneManager.getRingtone(mContext, soundUri);
                                        if (sfx != null) {
                                            sfx.setStreamType(AudioManager.STREAM_SYSTEM);
                                            sfx.play();
                                        }
                                    }
                                }
                            }
                        }

                        mContext.sendStickyBroadcast(intent);
                    }
=======
    private void updateLocked() {
        mWakeLock.acquire();
        mHandler.sendEmptyMessage(MSG_DOCK_STATE_CHANGED);
    }

    private void handleDockStateChange() {
        synchronized (mLock) {
            Slog.i(TAG, "Dock state changed: " + mDockState);

            // Skip the dock intent if not yet provisioned.
            final ContentResolver cr = mContext.getContentResolver();
            if (Settings.Global.getInt(cr,
                    Settings.Global.DEVICE_PROVISIONED, 0) == 0) {
                Slog.i(TAG, "Device not provisioned, skipping dock broadcast");
                return;
            }

            // Pack up the values and broadcast them to everyone
            Intent intent = new Intent(Intent.ACTION_DOCK_EVENT);
            intent.addFlags(Intent.FLAG_RECEIVER_REPLACE_PENDING);
            intent.putExtra(Intent.EXTRA_DOCK_STATE, mDockState);

            // Play a sound to provide feedback to confirm dock connection.
            // Particularly useful for flaky contact pins...
            if (Settings.Global.getInt(cr,
                    Settings.Global.DOCK_SOUNDS_ENABLED, 1) == 1) {
                String whichSound = null;
                if (mDockState == Intent.EXTRA_DOCK_STATE_UNDOCKED) {
                    if ((mPreviousDockState == Intent.EXTRA_DOCK_STATE_DESK) ||
                        (mPreviousDockState == Intent.EXTRA_DOCK_STATE_LE_DESK) ||
                        (mPreviousDockState == Intent.EXTRA_DOCK_STATE_HE_DESK)) {
                        whichSound = Settings.Global.DESK_UNDOCK_SOUND;
                    } else if (mPreviousDockState == Intent.EXTRA_DOCK_STATE_CAR) {
                        whichSound = Settings.Global.CAR_UNDOCK_SOUND;
                    }
                } else {
                    if ((mDockState == Intent.EXTRA_DOCK_STATE_DESK) ||
                        (mDockState == Intent.EXTRA_DOCK_STATE_LE_DESK) ||
                        (mDockState == Intent.EXTRA_DOCK_STATE_HE_DESK)) {
                        whichSound = Settings.Global.DESK_DOCK_SOUND;
                    } else if (mDockState == Intent.EXTRA_DOCK_STATE_CAR) {
                        whichSound = Settings.Global.CAR_DOCK_SOUND;
                    }
                }

                if (whichSound != null) {
                    final String soundPath = Settings.Global.getString(cr, whichSound);
                    if (soundPath != null) {
                        final Uri soundUri = Uri.parse("file://" + soundPath);
                        if (soundUri != null) {
                            final Ringtone sfx = RingtoneManager.getRingtone(mContext, soundUri);
                            if (sfx != null) {
                                sfx.setStreamType(AudioManager.STREAM_SYSTEM);
                                sfx.play();
                            }
                        }
                    }
                }
            }

            // Send the dock event intent.
            // There are many components in the system watching for this so as to
            // adjust audio routing, screen orientation, etc.
            mContext.sendStickyBroadcastAsUser(intent, UserHandle.ALL);

            // Release the wake lock that was acquired when the message was posted.
            mWakeLock.release();
        }
    }

    private final Handler mHandler = new Handler(true /*async*/) {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_DOCK_STATE_CHANGED:
                    handleDockStateChange();
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
                    break;
            }
        }
    };
}
