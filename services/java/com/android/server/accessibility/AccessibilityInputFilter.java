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

package com.android.server.accessibility;

<<<<<<< HEAD
import com.android.server.input.InputFilter;

=======
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
import android.content.Context;
import android.os.PowerManager;
import android.util.Slog;
import android.view.InputDevice;
import android.view.InputEvent;
<<<<<<< HEAD
=======
import android.view.InputFilter;
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
import android.view.MotionEvent;
import android.view.WindowManagerPolicy;
import android.view.accessibility.AccessibilityEvent;

<<<<<<< HEAD
/**
 * Input filter for accessibility.
 *
 * Currently just a stub but will eventually implement touch exploration, etc.
 */
public class AccessibilityInputFilter extends InputFilter {
    private static final String TAG = "AccessibilityInputFilter";
    private static final boolean DEBUG = false;

=======
class AccessibilityInputFilter extends InputFilter implements EventStreamTransformation {

    private static final String TAG = AccessibilityInputFilter.class.getSimpleName();

    private static final boolean DEBUG = false;

    private static final int UNDEFINED_DEVICE_ID = -1;

    /**
     * Flag for enabling the screen magnification feature.
     *
     * @see #setEnabledFeatures(int)
     */
    static final int FLAG_FEATURE_SCREEN_MAGNIFIER = 0x00000001;

    /**
     * Flag for enabling the touch exploration feature.
     *
     * @see #setEnabledFeatures(int)
     */
    static final int FLAG_FEATURE_TOUCH_EXPLORATION = 0x00000002;

>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
    private final Context mContext;

    private final PowerManager mPm;

    private final AccessibilityManagerService mAms;

<<<<<<< HEAD
    /**
     * This is an interface for explorers that take a {@link MotionEvent}
     * stream and perform touch exploration of the screen content.
     */
    public interface Explorer {
        /**
         * Handles a {@link MotionEvent}.
         *
         * @param event The event to handle.
         * @param policyFlags The policy flags associated with the event.
         */
        public void onMotionEvent(MotionEvent event, int policyFlags);

        /**
         * Requests that the explorer clears its internal state.
         *
         * @param event The last received event.
         * @param policyFlags The policy flags associated with the event.
         */
        public void clear(MotionEvent event, int policyFlags);

        /**
         * Requests that the explorer clears its internal state.
         */
        public void clear();
    }

    private TouchExplorer mTouchExplorer;

    private int mTouchscreenSourceDeviceId;

    public AccessibilityInputFilter(Context context, AccessibilityManagerService service) {
        super(context.getMainLooper());
        mContext = context;
        mAms = service;
        mPm = (PowerManager)context.getSystemService(Context.POWER_SERVICE);
=======
    private int mCurrentDeviceId;

    private boolean mInstalled;

    private int mEnabledFeatures;

    private TouchExplorer mTouchExplorer;
    private ScreenMagnifier mScreenMagnifier;
    private EventStreamTransformation mEventHandler;

    AccessibilityInputFilter(Context context, AccessibilityManagerService service) {
        super(context.getMainLooper());
        mContext = context;
        mAms = service;
        mPm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
    }

    @Override
    public void onInstalled() {
        if (DEBUG) {
            Slog.d(TAG, "Accessibility input filter installed.");
        }
<<<<<<< HEAD
        mTouchExplorer = new TouchExplorer(this, mContext, mAms);
=======
        mInstalled = true;
        disableFeatures();
        enableFeatures();
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
        super.onInstalled();
    }

    @Override
    public void onUninstalled() {
        if (DEBUG) {
            Slog.d(TAG, "Accessibility input filter uninstalled.");
        }
<<<<<<< HEAD
        mTouchExplorer.clear();
=======
        mInstalled = false;
        disableFeatures();
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
        super.onUninstalled();
    }

    @Override
    public void onInputEvent(InputEvent event, int policyFlags) {
        if (DEBUG) {
            Slog.d(TAG, "Received event: " + event + ", policyFlags=0x" 
                    + Integer.toHexString(policyFlags));
        }
<<<<<<< HEAD
        if (event.getSource() == InputDevice.SOURCE_TOUCHSCREEN) {
            MotionEvent motionEvent = (MotionEvent) event;
            int deviceId = event.getDeviceId();
            if (mTouchscreenSourceDeviceId != deviceId) {
                mTouchscreenSourceDeviceId = deviceId;
                mTouchExplorer.clear(motionEvent, policyFlags);
            }
            if ((policyFlags & WindowManagerPolicy.FLAG_PASS_TO_USER) != 0) {
                mPm.userActivity(event.getEventTime(), false);
                mTouchExplorer.onMotionEvent(motionEvent, policyFlags);
            } else {
                mTouchExplorer.clear(motionEvent, policyFlags);
            }
        } else {
            super.onInputEvent(event, policyFlags);
        }
    }

    public void onAccessibilityEvent(AccessibilityEvent event) {
        if (mTouchExplorer != null) {
            mTouchExplorer.onAccessibilityEvent(event);
        }
=======
        if (mEventHandler == null) {
            super.onInputEvent(event, policyFlags);
            return;
        }
        if (event.getSource() != InputDevice.SOURCE_TOUCHSCREEN) {
            super.onInputEvent(event, policyFlags);
            return;
        }
        if ((policyFlags & WindowManagerPolicy.FLAG_PASS_TO_USER) == 0) {
            mEventHandler.clear();
            super.onInputEvent(event, policyFlags);
            return;
        }
        final int deviceId = event.getDeviceId();
        if (mCurrentDeviceId != deviceId) {
            if (mCurrentDeviceId != UNDEFINED_DEVICE_ID) {
                mEventHandler.clear();
            }
            mCurrentDeviceId = deviceId;
        }
        mPm.userActivity(event.getEventTime(), false);
        MotionEvent rawEvent = (MotionEvent) event;
        MotionEvent transformedEvent = MotionEvent.obtain(rawEvent);
        mEventHandler.onMotionEvent(transformedEvent, rawEvent, policyFlags);
        transformedEvent.recycle();
    }

    @Override
    public void onMotionEvent(MotionEvent transformedEvent, MotionEvent rawEvent,
            int policyFlags) {
        sendInputEvent(transformedEvent, policyFlags);
    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        // TODO Implement this to inject the accessibility event
        //      into the accessibility manager service similarly
        //      to how this is done for input events.
    }

    @Override
    public void setNext(EventStreamTransformation sink) {
        /* do nothing */
    }

    @Override
    public void clear() {
        /* do nothing */
    }

    void setEnabledFeatures(int enabledFeatures) {
        if (mEnabledFeatures == enabledFeatures) {
            return;
        }
        if (mInstalled) {
            disableFeatures();
        }
        mEnabledFeatures = enabledFeatures;
        if (mInstalled) {
            enableFeatures();
        }
    }

    void notifyAccessibilityEvent(AccessibilityEvent event) {
        if (mEventHandler != null) {
            mEventHandler.onAccessibilityEvent(event);
        }
    }

    private void enableFeatures() {
        if ((mEnabledFeatures & FLAG_FEATURE_SCREEN_MAGNIFIER) != 0) {
            mEventHandler = mScreenMagnifier = new ScreenMagnifier(mContext);
            mEventHandler.setNext(this);
        }
        if ((mEnabledFeatures & FLAG_FEATURE_TOUCH_EXPLORATION) != 0) {
            mTouchExplorer = new TouchExplorer(mContext, mAms);
            mTouchExplorer.setNext(this);
            if (mEventHandler != null) {
                mEventHandler.setNext(mTouchExplorer);
            } else {
                mEventHandler = mTouchExplorer;
            }
        }
    }

    private void disableFeatures() {
        if (mTouchExplorer != null) {
            mTouchExplorer.clear();
            mTouchExplorer.onDestroy();
            mTouchExplorer = null;
        }
        if (mScreenMagnifier != null) {
            mScreenMagnifier.clear();
            mScreenMagnifier.onDestroy();
            mScreenMagnifier = null;
        }
        mEventHandler = null;
    }

    @Override
    public void onDestroy() {
        /* ignore */
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
    }
}
