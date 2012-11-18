/*
 * Copyright (C) 2010 The Android Open Source Project
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

package com.android.systemui.statusbar.phone;

import android.animation.Animator;
<<<<<<< HEAD
import android.animation.Animator.AnimatorListener;
import android.view.animation.Animation.AnimationListener;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.ActivityManager;
import android.app.ActivityManagerNative;
import android.app.Dialog;
import android.app.KeyguardManager;
=======
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.app.ActivityManager;
import android.app.ActivityManagerNative;
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
import android.app.Notification;
import android.app.PendingIntent;
import android.app.StatusBarManager;
import android.content.BroadcastReceiver;
<<<<<<< HEAD
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.provider.AlarmClock;
import android.provider.CalendarContract;
import android.provider.CalendarContract.Events;
import android.content.ActivityNotFoundException;
import android.content.res.Configuration;
import android.content.res.CustomTheme;
=======
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
import android.content.res.Resources;
import android.database.ContentObserver;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.PixelFormat;
<<<<<<< HEAD
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.NinePatchDrawable;
import android.inputmethodservice.InputMethodService;
import android.os.Handler;
import android.os.IBinder;
import android.os.IPowerManager;
=======
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.inputmethodservice.InputMethodService;
import android.os.Handler;
import android.os.IBinder;
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
import android.os.Message;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.SystemClock;
<<<<<<< HEAD
import android.provider.Settings;
import android.speech.RecognizerIntent;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.Pair;
import android.util.Slog;
import android.view.Choreographer;
import android.view.Display;
import android.view.Gravity;
import android.view.IWindowManager;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.view.WindowManagerImpl;
=======
import android.os.UserHandle;
import android.provider.Settings;
import android.service.dreams.DreamService;
import android.service.dreams.IDreamManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.Slog;
import android.view.Display;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewPropertyAnimator;
import android.view.ViewStub;
import android.view.WindowManager;
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
<<<<<<< HEAD
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import com.android.internal.statusbar.StatusBarIcon;
import com.android.internal.statusbar.StatusBarNotification;
import com.android.systemui.R;
import com.android.systemui.recent.RecentTasksLoader;
import com.android.systemui.statusbar.BaseStatusBar;
import com.android.systemui.statusbar.NotificationData;
import com.android.systemui.statusbar.NotificationData.Entry;
import com.android.systemui.statusbar.CommandQueue;
import com.android.systemui.statusbar.RotationToggle;
import com.android.systemui.statusbar.SignalClusterView;
import com.android.systemui.statusbar.StatusBarIconView;
import com.android.systemui.statusbar.policy.BatteryController;
import com.android.systemui.statusbar.policy.BatteryControllerNotification;
import com.android.systemui.statusbar.policy.DateView;
import com.android.systemui.statusbar.policy.IntruderAlertView;
import com.android.systemui.statusbar.policy.LocationController;
import com.android.systemui.statusbar.policy.OnSizeChangedListener;
import com.android.systemui.statusbar.policy.NetworkController;
import com.android.systemui.statusbar.policy.NotificationRowLayout;
import com.android.systemui.statusbar.policy.WeatherPanel;
import com.android.systemui.statusbar.toggles.TogglesView;

import java.io.FileDescriptor;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.net.URISyntaxException;

public class PhoneStatusBar extends BaseStatusBar {
    static final String TAG = "PhoneStatusBar";
    public static final boolean DEBUG = false;
    public static final boolean SPEW = DEBUG;
=======
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.android.internal.statusbar.StatusBarIcon;
import com.android.internal.statusbar.StatusBarNotification;
import com.android.systemui.R;
import com.android.systemui.statusbar.BaseStatusBar;
import com.android.systemui.statusbar.CommandQueue;
import com.android.systemui.statusbar.GestureRecorder;
import com.android.systemui.statusbar.NotificationData;
import com.android.systemui.statusbar.NotificationData.Entry;
import com.android.systemui.statusbar.SignalClusterView;
import com.android.systemui.statusbar.StatusBarIconView;
import com.android.systemui.statusbar.policy.BatteryController;
import com.android.systemui.statusbar.policy.BluetoothController;
import com.android.systemui.statusbar.policy.DateView;
import com.android.systemui.statusbar.policy.IntruderAlertView;
import com.android.systemui.statusbar.policy.LocationController;
import com.android.systemui.statusbar.policy.NetworkController;
import com.android.systemui.statusbar.policy.NotificationRowLayout;
import com.android.systemui.statusbar.policy.OnSizeChangedListener;
import com.android.systemui.statusbar.policy.Prefs;

import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;

public class PhoneStatusBar extends BaseStatusBar {
    static final String TAG = "PhoneStatusBar";
    public static final boolean DEBUG = BaseStatusBar.DEBUG;
    public static final boolean SPEW = DEBUG;
    public static final boolean DUMPTRUCK = true; // extra dumpsys info
    public static final boolean DEBUG_GESTURES = false;

    public static final boolean DEBUG_CLINGS = false;

    public static final boolean ENABLE_NOTIFICATION_PANEL_CLING = false;

    public static final boolean SETTINGS_DRAG_SHORTCUT = true;
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a

    // additional instrumentation for testing purposes; intended to be left on during development
    public static final boolean CHATTY = DEBUG;

    public static final String ACTION_STATUSBAR_START
            = "com.android.internal.policy.statusbar.START";

<<<<<<< HEAD
    private static final boolean DIM_BEHIND_EXPANDED_PANEL = true;
    private static final boolean SHOW_CARRIER_LABEL = true;

    private static final int MSG_OPEN_NOTIFICATION_PANEL = 1000;
    private static final int MSG_CLOSE_NOTIFICATION_PANEL = 1001;
    private static final int MSG_STATUSBAR_BRIGHTNESS = 1002;

=======
    private static final int MSG_OPEN_NOTIFICATION_PANEL = 1000;
    private static final int MSG_CLOSE_PANELS = 1001;
    private static final int MSG_OPEN_SETTINGS_PANEL = 1002;
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
    // 1020-1030 reserved for BaseStatusBar

    // will likely move to a resource or other tunable param at some point
    private static final int INTRUDER_ALERT_DECAY_MS = 0; // disabled, was 10000;

    private static final boolean CLOSE_PANEL_WHEN_EMPTIED = true;

    private static final int NOTIFICATION_PRIORITY_MULTIPLIER = 10; // see NotificationManagerService
    private static final int HIDE_ICONS_BELOW_SCORE = Notification.PRIORITY_LOW * NOTIFICATION_PRIORITY_MULTIPLIER;

    // fling gesture tuning parameters, scaled to display density
    private float mSelfExpandVelocityPx; // classic value: 2000px/s
    private float mSelfCollapseVelocityPx; // classic value: 2000px/s (will be negated to collapse "up")
    private float mFlingExpandMinVelocityPx; // classic value: 200px/s
    private float mFlingCollapseMinVelocityPx; // classic value: 200px/s
    private float mCollapseMinDisplayFraction; // classic value: 0.08 (25px/min(320px,480px) on G1)
    private float mExpandMinDisplayFraction; // classic value: 0.5 (drag open halfway to expand)
    private float mFlingGestureMaxXVelocityPx; // classic value: 150px/s

    private float mExpandAccelPx; // classic value: 2000px/s/s
    private float mCollapseAccelPx; // classic value: 2000px/s/s (will be negated to collapse "up")

    private float mFlingGestureMaxOutputVelocityPx; // how fast can it really go? (should be a little 
                                                    // faster than mSelfCollapseVelocityPx)

    PhoneStatusBarPolicy mIconPolicy;

    // These are no longer handled by the policy, because we need custom strategies for them
<<<<<<< HEAD
    //BatteryController mBatteryController;
=======
    BluetoothController mBluetoothController;
    BatteryController mBatteryController;
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
    LocationController mLocationController;
    NetworkController mNetworkController;

    int mNaturalBarHeight = -1;
    int mIconSize = -1;
    int mIconHPadding = -1;
    Display mDisplay;
<<<<<<< HEAD

    IWindowManager mWindowManager;
=======
    Point mCurrentDisplaySize = new Point();

    IDreamManager mDreamManager;
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a

    StatusBarWindowView mStatusBarWindow;
    PhoneStatusBarView mStatusBarView;

    int mPixelFormat;
    Object mQueueLock = new Object();

<<<<<<< HEAD
    // icons
    LinearLayout mIcons;
    IconMerger mNotificationIcons;
    View mMoreIcon;
    LinearLayout mStatusIcons;
    LinearLayout mCenterClockLayout;

    final static String ACTION_EVENT = "**event**";
    final static String ACTION_ALARM = "**alarm**";
    final static String ACTION_TODAY = "**today**";
    final static String ACTION_VOICEASSIST = "**assist**";
    final static String ACTION_NOTHING = "**nothing**";
    final static String ACTION_UPDATE = "**update**";

    private Intent intent;
    private String mShortClick;
    private String mLongClick;
    private String mShortClickWeather;
    private String mLongClickWeather;

    // expanded notifications
    View mNotificationPanel; // the sliding/resizing panel within the notification window
    ScrollView mScrollView;
    View mExpandedContents;
    int mNotificationPanelMarginBottomPx, mNotificationPanelMarginLeftPx;
    final Rect mNotificationPanelBackgroundPadding = new Rect();
    int mNotificationPanelGravity;
    int mNotificationPanelMinHeight;
    boolean mNotificationPanelIsFullScreenWidth;

    // top bar
    View mClearButton;
    View mSettingsButton;
    TextView clock;
    TextView cclock;
    RotationToggle mRotationButton;
    BatteryControllerNotification mBatteryNotification;

    // AoCP - weatherpanel
    boolean mWeatherPanelEnabled;
    boolean mWeatherGoneOnBoot;
    int mWeatherHideStatus;
    WeatherPanel mWeatherPanel;

    TogglesView mQuickToggles;
=======
    // viewgroup containing the normal contents of the statusbar
    LinearLayout mStatusBarContents;
    
    // right-hand icons
    LinearLayout mSystemIconArea;
    
    // left-hand icons 
    LinearLayout mStatusIcons;
    // the icons themselves
    IconMerger mNotificationIcons;
    // [+>
    View mMoreIcon;

    // expanded notifications
    NotificationPanelView mNotificationPanel; // the sliding/resizing panel within the notification window
    ScrollView mScrollView;
    View mExpandedContents;
    int mNotificationPanelGravity;
    int mNotificationPanelMarginBottomPx, mNotificationPanelMarginPx;
    float mNotificationPanelMinHeightFrac;
    boolean mNotificationPanelIsFullScreenWidth;
    TextView mNotificationPanelDebugText;

    // settings
    QuickSettings mQS;
    boolean mHasSettingsPanel, mHasFlipSettings;
    SettingsPanelView mSettingsPanel;
    View mFlipSettingsView;
    QuickSettingsContainerView mSettingsContainer;
    int mSettingsPanelGravity;

    // top bar
    View mNotificationPanelHeader;
    View mDateTimeView; 
    View mClearButton;
    ImageView mSettingsButton, mNotificationButton;
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a

    // carrier/wifi label
    private TextView mCarrierLabel;
    private boolean mCarrierLabelVisible = false;
    private int mCarrierLabelHeight;
<<<<<<< HEAD

    // drag bar
    CloseDragHandle mCloseView;
    private int mCloseViewHeight;

    // position
    int[] mPositionTmp = new int[2];
    boolean mExpanded;
=======
    private TextView mEmergencyCallLabel;
    private int mNotificationHeaderHeight;

    private boolean mShowCarrierInPanel = false;

    // position
    int[] mPositionTmp = new int[2];
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
    boolean mExpandedVisible;

    // the date view
    DateView mDateView;

    // for immersive activities
    private IntruderAlertView mIntruderAlertView;

    // on-screen navigation buttons
    private NavigationBarView mNavigationBarView = null;

    // the tracker view
    int mTrackingPosition; // the position of the top of the tracking view.

    // ticker
    private Ticker mTicker;
    private View mTickerView;
    private boolean mTicking;

    // Tracking finger for opening/closing.
    int mEdgeBorder; // corresponds to R.dimen.status_bar_edge_ignore
    boolean mTracking;
    VelocityTracker mVelocityTracker;

<<<<<<< HEAD
    Choreographer mChoreographer;
=======
    // help screen
    private boolean mClingShown;
    private ViewGroup mCling;
    private boolean mSuppressStatusBarDrags; // while a cling is up, briefly deaden the bar to give things time to settle

>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
    boolean mAnimating;
    boolean mClosing; // only valid when mAnimating; indicates the initial acceleration
    float mAnimY;
    float mAnimVel;
    float mAnimAccel;
    long mAnimLastTimeNanos;
    boolean mAnimatingReveal = false;
    int mViewDelta;
    float mFlingVelocity;
    int mFlingY;
    int[] mAbsPos = new int[2];
    Runnable mPostCollapseCleanup = null;

<<<<<<< HEAD
    // last theme that was applied in order to detect theme change (as opposed
    // to some other configuration change).
    CustomTheme mCurrentTheme;
    private boolean mRecreating = false;

    private AnimatorSet mLightsOutAnimation;
    private AnimatorSet mLightsOnAnimation;
=======
    private Animator mLightsOutAnimation;
    private Animator mLightsOnAnimation;
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a

    // for disabling the status bar
    int mDisabled = 0;

    // tracking calls to View.setSystemUiVisibility()
    int mSystemUiVisibility = View.SYSTEM_UI_FLAG_VISIBLE;

    DisplayMetrics mDisplayMetrics = new DisplayMetrics();

<<<<<<< HEAD
    // brightness slider
    private TextView mBrightnessPercent;
    private int mIsBrightNessMode = 0;
    private boolean mIsStatusBarBrightNess;
    private boolean mIsAutoBrightNess;
    private BrightNessContentObserver mBrightNessContentObs = new BrightNessContentObserver();
    private Float mPropFactor;

    boolean mQuickTogglesHideAfterCollapse = true;
=======
    // XXX: gesture research
    private final GestureRecorder mGestureRec = DEBUG_GESTURES
        ? new GestureRecorder("/sdcard/statusbar_gestures.dat") 
        : null;
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a

    private int mNavigationIconHints = 0;
    private final Animator.AnimatorListener mMakeIconsInvisible = new AnimatorListenerAdapter() {
        @Override
        public void onAnimationEnd(Animator animation) {
            // double-check to avoid races
<<<<<<< HEAD
            if (mIcons.getAlpha() == 0) {
                Slog.d(TAG, "makeIconsInvisible");
                mIcons.setVisibility(View.INVISIBLE);
=======
            if (mStatusBarContents.getAlpha() == 0) {
                if (DEBUG) Slog.d(TAG, "makeIconsInvisible");
                mStatusBarContents.setVisibility(View.INVISIBLE);
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
            }
        }
    };

<<<<<<< HEAD
    private final Runnable mStartRevealAnimation = new Runnable() {
        @Override
        public void run() {
            mAnimAccel = mExpandAccelPx;
            mAnimVel = mFlingExpandMinVelocityPx;
            mAnimY = getStatusBarHeight();
            updateExpandedViewPos((int)mAnimY);

            mAnimating = true;
            mAnimatingReveal = true;
            resetLastAnimTime();
            mChoreographer.removeCallbacks(Choreographer.CALLBACK_ANIMATION,
                mAnimationCallback, null);
            mChoreographer.removeCallbacks(Choreographer.CALLBACK_ANIMATION,
                mRevealAnimationCallback, null);
            mChoreographer.postCallback(Choreographer.CALLBACK_ANIMATION,
                mRevealAnimationCallback, null);
        }
    };

    private final Runnable mPerformSelfExpandFling = new Runnable() {
        @Override
        public void run() {
            performFling(0, mSelfExpandVelocityPx, true);
        }
    };

    private final Runnable mPerformFling = new Runnable() {
        @Override
        public void run() {
            performFling(mFlingY + mViewDelta, mFlingVelocity, false);
        }
    };

    private class ExpandedDialog extends Dialog {
        ExpandedDialog(Context context) {
            super(context, com.android.internal.R.style.Theme_Translucent_NoTitleBar);
        }

        @Override
        public boolean dispatchKeyEvent(KeyEvent event) {
            boolean down = event.getAction() == KeyEvent.ACTION_DOWN;
            switch (event.getKeyCode()) {
            case KeyEvent.KEYCODE_BACK:
                if (!down) {
                    animateCollapse();
                }
                return true;
            }
            return super.dispatchKeyEvent(event);
        }
    }
=======
    // ensure quick settings is disabled until the current user makes it through the setup wizard
    private boolean mUserSetup = false;
    private ContentObserver mUserSetupObserver = new ContentObserver(new Handler()) {
        @Override
        public void onChange(boolean selfChange) {
            final boolean userSetup = 0 != Settings.Secure.getIntForUser(
                    mContext.getContentResolver(),
                    Settings.Secure.USER_SETUP_COMPLETE,
                    0 /*default */,
                    mCurrentUserId);
            if (MULTIUSER_DEBUG) Slog.d(TAG, String.format("User setup changed: " +
                    "selfChange=%s userSetup=%s mUserSetup=%s",
                    selfChange, userSetup, mUserSetup));
            if (mSettingsButton != null && !mHasSettingsPanel) {
                mSettingsButton.setVisibility(userSetup ? View.VISIBLE : View.INVISIBLE);
            }
            if (mSettingsPanel != null) {
                mSettingsPanel.setEnabled(userSetup);
            }
            if (userSetup != mUserSetup) {
                mUserSetup = userSetup;
                if (!mUserSetup && mStatusBarView != null)
                    animateCollapseQuickSettings();
            }
        }
    };
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a

    @Override
    public void start() {
        mDisplay = ((WindowManager)mContext.getSystemService(Context.WINDOW_SERVICE))
                .getDefaultDisplay();

<<<<<<< HEAD
        mWindowManager = IWindowManager.Stub.asInterface(
                ServiceManager.getService(Context.WINDOW_SERVICE));

        CustomTheme currentTheme = mContext.getResources().getConfiguration().customTheme;
        if (currentTheme != null) {
            mCurrentTheme = (CustomTheme)currentTheme.clone();
        }
=======
        mDreamManager = IDreamManager.Stub.asInterface(
                ServiceManager.checkService(DreamService.DREAM_SERVICE));
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a

        super.start(); // calls createAndAddWindows()

        addNavigationBar();

        if (ENABLE_INTRUDERS) addIntruderView();

        // Lastly, call to the icon policy to install/update all the icons.
        mIconPolicy = new PhoneStatusBarPolicy(mContext);
    }

    // ================================================================================
    // Constructing the view
    // ================================================================================
    protected PhoneStatusBarView makeStatusBarView() {
        final Context context = mContext;

        Resources res = context.getResources();

        updateDisplaySize(); // populates mDisplayMetrics
        loadDimens();

        mIconSize = res.getDimensionPixelSize(com.android.internal.R.dimen.status_bar_icon_size);

        mStatusBarWindow = (StatusBarWindowView) View.inflate(context,
                R.layout.super_status_bar, null);
<<<<<<< HEAD
        if (DEBUG) {
            mStatusBarWindow.setBackgroundColor(0x6000FF80);
        }
=======
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
        mStatusBarWindow.mService = this;
        mStatusBarWindow.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
<<<<<<< HEAD
                    if (mExpanded && !mAnimating) {
                        animateCollapse();
=======
                    if (mExpandedVisible && !mAnimating) {
                        animateCollapsePanels();
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
                    }
                }
                return mStatusBarWindow.onTouchEvent(event);
            }});

        mStatusBarView = (PhoneStatusBarView) mStatusBarWindow.findViewById(R.id.status_bar);
<<<<<<< HEAD
        mNotificationPanel = mStatusBarWindow.findViewById(R.id.notification_panel);
        // don't allow clicks on the panel to pass through to the background where they will cause the panel to close
        mNotificationPanel.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
        mNotificationPanelIsFullScreenWidth =
            (mNotificationPanel.getLayoutParams().width == ViewGroup.LayoutParams.MATCH_PARENT);
        mNotificationPanel.setSystemUiVisibility(
                  View.STATUS_BAR_DISABLE_NOTIFICATION_TICKER
                | (mNotificationPanelIsFullScreenWidth ? 0 : View.STATUS_BAR_DISABLE_SYSTEM_INFO));

        if (!ActivityManager.isHighEndGfx(mDisplay)) {
=======
        mStatusBarView.setBar(this);
        

        PanelHolder holder = (PanelHolder) mStatusBarWindow.findViewById(R.id.panel_holder);
        mStatusBarView.setPanelHolder(holder);

        mNotificationPanel = (NotificationPanelView) mStatusBarWindow.findViewById(R.id.notification_panel);
        mNotificationPanel.setStatusBar(this);
        mNotificationPanelIsFullScreenWidth =
            (mNotificationPanel.getLayoutParams().width == ViewGroup.LayoutParams.MATCH_PARENT);

        // make the header non-responsive to clicks
        mNotificationPanel.findViewById(R.id.header).setOnTouchListener(
                new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        return true; // e eats everything
                    }
                });

        if (!ActivityManager.isHighEndGfx()) {
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
            mStatusBarWindow.setBackground(null);
            mNotificationPanel.setBackground(new FastColorDrawable(context.getResources().getColor(
                    R.color.notification_panel_solid_background)));
        }
        if (ENABLE_INTRUDERS) {
            mIntruderAlertView = (IntruderAlertView) View.inflate(context, R.layout.intruder_alert, null);
            mIntruderAlertView.setVisibility(View.GONE);
            mIntruderAlertView.setBar(this);
        }
<<<<<<< HEAD

        updateShowSearchHoldoff();

        mStatusBarView.mService = this;

        mChoreographer = Choreographer.getInstance();

        try {
            boolean showNav = mWindowManager.hasNavigationBar();
=======
        if (MULTIUSER_DEBUG) {
            mNotificationPanelDebugText = (TextView) mNotificationPanel.findViewById(R.id.header_debug_info);
            mNotificationPanelDebugText.setVisibility(View.VISIBLE);
        }

        updateShowSearchHoldoff();

        try {
            boolean showNav = mWindowManagerService.hasNavigationBar();
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
            if (DEBUG) Slog.v(TAG, "hasNavigationBar=" + showNav);
            if (showNav) {
                mNavigationBarView =
                    (NavigationBarView) View.inflate(context, R.layout.navigation_bar, null);

                mNavigationBarView.setDisabledFlags(mDisabled);
                mNavigationBarView.setBar(this);
            }
        } catch (RemoteException ex) {
            // no window manager? good luck with that
        }

        // figure out which pixel-format to use for the status bar.
        mPixelFormat = PixelFormat.OPAQUE;
<<<<<<< HEAD
        mStatusIcons = (LinearLayout)mStatusBarView.findViewById(R.id.statusIcons);
        mNotificationIcons = (IconMerger)mStatusBarView.findViewById(R.id.notificationIcons);
        mNotificationIcons.setOverflowIndicator(mMoreIcon);
        mIcons = (LinearLayout)mStatusBarView.findViewById(R.id.icons);
        mCenterClockLayout = (LinearLayout)mStatusBarView.findViewById(R.id.center_clock_layout);
=======

        mSystemIconArea = (LinearLayout) mStatusBarView.findViewById(R.id.system_icon_area);
        mStatusIcons = (LinearLayout)mStatusBarView.findViewById(R.id.statusIcons);
        mNotificationIcons = (IconMerger)mStatusBarView.findViewById(R.id.notificationIcons);
        mNotificationIcons.setOverflowIndicator(mMoreIcon);
        mStatusBarContents = (LinearLayout)mStatusBarView.findViewById(R.id.status_bar_contents);
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
        mTickerView = mStatusBarView.findViewById(R.id.ticker);

        mPile = (NotificationRowLayout)mStatusBarWindow.findViewById(R.id.latestItems);
        mPile.setLayoutTransitionsEnabled(false);
        mPile.setLongPressListener(getNotificationLongClicker());
        mExpandedContents = mPile; // was: expanded.findViewById(R.id.notificationLinearLayout);

<<<<<<< HEAD
=======
        mNotificationPanelHeader = mStatusBarWindow.findViewById(R.id.header);

>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
        mClearButton = mStatusBarWindow.findViewById(R.id.clear_all_button);
        mClearButton.setOnClickListener(mClearButtonListener);
        mClearButton.setAlpha(0f);
        mClearButton.setVisibility(View.INVISIBLE);
        mClearButton.setEnabled(false);
<<<<<<< HEAD
        mBatteryNotification = (BatteryControllerNotification)mStatusBarWindow.findViewById(R.id.battery_notification);
        mBrightnessPercent = (TextView)mStatusBarWindow.findViewById(R.id.brightness_percent);
        mDateView = (DateView)mStatusBarWindow.findViewById(R.id.date);
        clock = (TextView)mStatusBarView.findViewById(R.id.clock);
        cclock = (TextView)mStatusBarView.findViewById(R.id.center_clock);
        mDateView.setOnClickListener(mDateViewListener);
        mDateView.setOnLongClickListener(mDateViewLongClickListener);
        mSettingsButton = mStatusBarWindow.findViewById(R.id.settings_button);
        mSettingsButton.setOnClickListener(mSettingsButtonListener);
        mSettingsButton.setOnLongClickListener(mSettingsLongClickListener);
        mRotationButton = (RotationToggle) mStatusBarWindow.findViewById(R.id.rotation_lock_button);
        mWeatherPanel = (WeatherPanel) mStatusBarWindow.findViewById(R.id.weatherpanel);
        mWeatherPanel.setOnClickListener(mWeatherPanelListener);
        mWeatherPanel.setOnLongClickListener(mWeatherPanelLongClickListener);
        mQuickToggles = (TogglesView) mStatusBarWindow.findViewById(R.id.quick_toggles);
        
        mScrollView = (ScrollView)mStatusBarWindow.findViewById(R.id.scroll);
        mScrollView.setVerticalScrollBarEnabled(false); // less drawing during pulldowns
=======
        mDateView = (DateView)mStatusBarWindow.findViewById(R.id.date);

        mHasSettingsPanel = res.getBoolean(R.bool.config_hasSettingsPanel);
        mHasFlipSettings = res.getBoolean(R.bool.config_hasFlipSettingsPanel);

        mDateTimeView = mNotificationPanelHeader.findViewById(R.id.datetime);
        if (mHasFlipSettings) {
            mDateTimeView.setOnClickListener(mClockClickListener);
            mDateTimeView.setEnabled(true);
        }

        mSettingsButton = (ImageView) mStatusBarWindow.findViewById(R.id.settings_button);
        if (mSettingsButton != null) {
            mSettingsButton.setOnClickListener(mSettingsButtonListener);
            if (mHasSettingsPanel) {
                if (mStatusBarView.hasFullWidthNotifications()) {
                    // the settings panel is hiding behind this button
                    mSettingsButton.setImageResource(R.drawable.ic_notify_quicksettings);
                    mSettingsButton.setVisibility(View.VISIBLE);
                } else {
                    // there is a settings panel, but it's on the other side of the (large) screen
                    final View buttonHolder = mStatusBarWindow.findViewById(
                            R.id.settings_button_holder);
                    if (buttonHolder != null) {
                        buttonHolder.setVisibility(View.GONE);
                    }
                }
            } else {
                // no settings panel, go straight to settings
                mSettingsButton.setVisibility(View.VISIBLE);
                mSettingsButton.setImageResource(R.drawable.ic_notify_settings);
            }
        }
        if (mHasFlipSettings) {
            mNotificationButton = (ImageView) mStatusBarWindow.findViewById(R.id.notification_button);
            if (mNotificationButton != null) {
                mNotificationButton.setOnClickListener(mNotificationButtonListener);
            }
        }

        mScrollView = (ScrollView)mStatusBarWindow.findViewById(R.id.scroll);
        mScrollView.setVerticalScrollBarEnabled(false); // less drawing during pulldowns
        if (!mNotificationPanelIsFullScreenWidth) {
            mScrollView.setSystemUiVisibility(
                    View.STATUS_BAR_DISABLE_NOTIFICATION_TICKER |
                    View.STATUS_BAR_DISABLE_NOTIFICATION_ICONS |
                    View.STATUS_BAR_DISABLE_CLOCK);
        }
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a

        mTicker = new MyTicker(context, mStatusBarView);

        TickerView tickerView = (TickerView)mStatusBarView.findViewById(R.id.tickerText);
        tickerView.mTicker = mTicker;

<<<<<<< HEAD
        mCloseView = (CloseDragHandle)mStatusBarWindow.findViewById(R.id.close);
        mCloseView.mService = this;
        mCloseViewHeight = res.getDimensionPixelSize(R.dimen.close_handle_height);

=======
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
        mEdgeBorder = res.getDimensionPixelSize(R.dimen.status_bar_edge_ignore);

        // set the inital view visibility
        setAreThereNotifications();

        // Other icons
        mLocationController = new LocationController(mContext); // will post a notification
<<<<<<< HEAD
        //mBatteryController = new BatteryController(mContext);
        //mBatteryController.addIconView((ImageView)mStatusBarView.findViewById(R.id.battery));
        mNetworkController = new NetworkController(mContext);
        final SignalClusterView signalCluster =
                (SignalClusterView)mStatusBarView.findViewById(R.id.signal_cluster);

        mNetworkController.addSignalCluster(signalCluster);
        signalCluster.setNetworkController(mNetworkController);

        if (SHOW_CARRIER_LABEL) {
            mCarrierLabel = (TextView)mStatusBarWindow.findViewById(R.id.carrier_label);
=======
        mBatteryController = new BatteryController(mContext);
        mBatteryController.addIconView((ImageView)mStatusBarView.findViewById(R.id.battery));
        mNetworkController = new NetworkController(mContext);
        mBluetoothController = new BluetoothController(mContext);
        final SignalClusterView signalCluster =
                (SignalClusterView)mStatusBarView.findViewById(R.id.signal_cluster);


        mNetworkController.addSignalCluster(signalCluster);
        signalCluster.setNetworkController(mNetworkController);

        mEmergencyCallLabel = (TextView)mStatusBarWindow.findViewById(R.id.emergency_calls_only);
        if (mEmergencyCallLabel != null) {
            mNetworkController.addEmergencyLabelView(mEmergencyCallLabel);
            mEmergencyCallLabel.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) { }});
            mEmergencyCallLabel.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
                @Override
                public void onLayoutChange(View v, int left, int top, int right, int bottom,
                        int oldLeft, int oldTop, int oldRight, int oldBottom) {
                    updateCarrierLabelVisibility(false);
                }});
        }

        mCarrierLabel = (TextView)mStatusBarWindow.findViewById(R.id.carrier_label);
        mShowCarrierInPanel = (mCarrierLabel != null);
        if (DEBUG) Slog.v(TAG, "carrierlabel=" + mCarrierLabel + " show=" + mShowCarrierInPanel);
        if (mShowCarrierInPanel) {
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
            mCarrierLabel.setVisibility(mCarrierLabelVisible ? View.VISIBLE : View.INVISIBLE);

            // for mobile devices, we always show mobile connection info here (SPN/PLMN)
            // for other devices, we show whatever network is connected
            if (mNetworkController.hasMobileDataFeature()) {
                mNetworkController.addMobileLabelView(mCarrierLabel);
            } else {
                mNetworkController.addCombinedLabelView(mCarrierLabel);
            }

            // set up the dynamic hide/show of the label
            mPile.setOnSizeChangedListener(new OnSizeChangedListener() {
                @Override
                public void onSizeChanged(View view, int w, int h, int oldw, int oldh) {
                    updateCarrierLabelVisibility(false);
                }
            });
        }

<<<<<<< HEAD
=======
        // Quick Settings (where available, some restrictions apply)
        if (mHasSettingsPanel) {
            // first, figure out where quick settings should be inflated
            final View settings_stub;
            if (mHasFlipSettings) {
                // a version of quick settings that flips around behind the notifications
                settings_stub = mStatusBarWindow.findViewById(R.id.flip_settings_stub);
                if (settings_stub != null) {
                    mFlipSettingsView = ((ViewStub)settings_stub).inflate();
                    mFlipSettingsView.setVisibility(View.GONE);
                    mFlipSettingsView.setVerticalScrollBarEnabled(false);
                }
            } else {
                // full quick settings panel
                settings_stub = mStatusBarWindow.findViewById(R.id.quick_settings_stub);
                if (settings_stub != null) {
                    mSettingsPanel = (SettingsPanelView) ((ViewStub)settings_stub).inflate();
                } else {
                    mSettingsPanel = (SettingsPanelView) mStatusBarWindow.findViewById(R.id.settings_panel);
                }

                if (mSettingsPanel != null) {
                    if (!ActivityManager.isHighEndGfx()) {
                        mSettingsPanel.setBackground(new FastColorDrawable(context.getResources().getColor(
                                R.color.notification_panel_solid_background)));
                    }
                }
            }

            // wherever you find it, Quick Settings needs a container to survive
            mSettingsContainer = (QuickSettingsContainerView)
                    mStatusBarWindow.findViewById(R.id.quick_settings_container);
            if (mSettingsContainer != null) {
                mQS = new QuickSettings(mContext, mSettingsContainer);
                if (!mNotificationPanelIsFullScreenWidth) {
                    mSettingsContainer.setSystemUiVisibility(
                            View.STATUS_BAR_DISABLE_NOTIFICATION_TICKER
                            | View.STATUS_BAR_DISABLE_SYSTEM_INFO);
                }
                if (mSettingsPanel != null) {
                    mSettingsPanel.setQuickSettings(mQS);
                }
                mQS.setService(this);
                mQS.setBar(mStatusBarView);
                mQS.setup(mNetworkController, mBluetoothController, mBatteryController,
                        mLocationController);
            } else {
                mQS = null; // fly away, be free
            }
        }

        mClingShown = ! (DEBUG_CLINGS 
            || !Prefs.read(mContext).getBoolean(Prefs.SHOWN_QUICK_SETTINGS_HELP, false));

        if (!ENABLE_NOTIFICATION_PANEL_CLING || ActivityManager.isRunningInTestHarness()) {
            mClingShown = true;
        }

>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
//        final ImageView wimaxRSSI =
//                (ImageView)sb.findViewById(R.id.wimax_signal);
//        if (wimaxRSSI != null) {
//            mNetworkController.addWimaxIconView(wimaxRSSI);
//        }
<<<<<<< HEAD
        // Recents Panel
        mRecentTasksLoader = new RecentTasksLoader(context);
        updateRecentsPanel();
=======
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a

        // receive broadcasts
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_CONFIGURATION_CHANGED);
        filter.addAction(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
        filter.addAction(Intent.ACTION_SCREEN_OFF);
<<<<<<< HEAD
        context.registerReceiver(mBroadcastReceiver, filter);

        SettingsObserver observer = new SettingsObserver(new Handler());
        observer.observe();
        updateSettings();
        if (mShortClick == null || mShortClick == "") {
            mShortClick = "**nothing**";
        }
        if (mLongClick == null || mLongClick == "") {
            mLongClick = "**nothing**";
        }

        if (mShortClickWeather == null || mShortClickWeather == "") {
            mShortClickWeather = "**nothing**";
        }
        if (mLongClickWeather == null || mLongClickWeather == "") {
            mLongClickWeather = "**nothing**";
        }

        boolean WeatherGoneOnBoot = (mWeatherHideStatus == 2);

        if (WeatherGoneOnBoot) {
               mWeatherPanel.setVisibility(View.GONE);
        } else {
               mWeatherPanel.setVisibility(mWeatherPanelEnabled ? View.VISIBLE : View.GONE);
        }

        mIsAutoBrightNess = checkAutoBrightNess();
        mContext.getContentResolver().registerContentObserver(
                Settings.System.getUriFor(Settings.System.SCREEN_BRIGHTNESS_MODE), false,
                mBrightNessContentObs);
        updatePropFactorValue();

=======
        filter.addAction(Intent.ACTION_SCREEN_ON);
        context.registerReceiver(mBroadcastReceiver, filter);

        // listen for USER_SETUP_COMPLETE setting (per-user)
        resetUserSetupObserver();

        return mStatusBarView;
    }

    @Override
    protected View getStatusBarView() {
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
        return mStatusBarView;
    }

    @Override
    protected WindowManager.LayoutParams getRecentsLayoutParams(LayoutParams layoutParams) {
        boolean opaque = false;
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams(
                layoutParams.width,
                layoutParams.height,
                WindowManager.LayoutParams.TYPE_STATUS_BAR_PANEL,
                WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN
                | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM
                | WindowManager.LayoutParams.FLAG_SPLIT_TOUCH,
                (opaque ? PixelFormat.OPAQUE : PixelFormat.TRANSLUCENT));
<<<<<<< HEAD
        if (ActivityManager.isHighEndGfx(mDisplay)) {
=======
        if (ActivityManager.isHighEndGfx()) {
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
            lp.flags |= WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED;
        } else {
            lp.flags |= WindowManager.LayoutParams.FLAG_DIM_BEHIND;
            lp.dimAmount = 0.75f;
        }
        lp.gravity = Gravity.BOTTOM | Gravity.LEFT;
        lp.setTitle("RecentsPanel");
        lp.windowAnimations = com.android.internal.R.style.Animation_RecentApplications;
        lp.softInputMode = WindowManager.LayoutParams.SOFT_INPUT_STATE_UNCHANGED
        | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING;
        return lp;
    }

    @Override
    protected WindowManager.LayoutParams getSearchLayoutParams(LayoutParams layoutParams) {
        boolean opaque = false;
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.TYPE_NAVIGATION_BAR_PANEL,
                WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN
                | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM
                | WindowManager.LayoutParams.FLAG_SPLIT_TOUCH,
                (opaque ? PixelFormat.OPAQUE : PixelFormat.TRANSLUCENT));
<<<<<<< HEAD
        if (ActivityManager.isHighEndGfx(mDisplay)) {
=======
        if (ActivityManager.isHighEndGfx()) {
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
            lp.flags |= WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED;
        }
        lp.gravity = Gravity.BOTTOM | Gravity.LEFT;
        lp.setTitle("SearchPanel");
        // TODO: Define custom animation for Search panel
        lp.windowAnimations = com.android.internal.R.style.Animation_RecentApplications;
        lp.softInputMode = WindowManager.LayoutParams.SOFT_INPUT_STATE_UNCHANGED
        | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING;
        return lp;
    }

<<<<<<< HEAD
    protected void updateRecentsPanel() {
        super.updateRecentsPanel(R.layout.status_bar_recent_panel);
        // Make .03 alpha the minimum so you always see the item a bit-- slightly below
        // .03, the item disappears entirely (as if alpha = 0) and that discontinuity looks
        // a bit jarring
        mRecentsPanel.setMinSwipeAlpha(0.03f);
        if (mNavigationBarView != null) {
        	if (mNavigationBarView.getRecentsButton() !=null) {
            mNavigationBarView.getRecentsButton().setOnTouchListener(mRecentsPanel);
        	}
        }
    }

    void onBarViewDetached() {
     //   WindowManagerImpl.getDefault().removeView(mStatusBarWindow);
    }

    @Override
    public void toggleNotificationShade() {
        int msg = (mExpandedVisible)
            ? MSG_CLOSE_NOTIFICATION_PANEL : MSG_OPEN_NOTIFICATION_PANEL;
        mHandler.removeMessages(msg);
        mHandler.sendEmptyMessage(msg);
    }

=======
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
    @Override
    protected void updateSearchPanel() {
        super.updateSearchPanel();
        mSearchPanelView.setStatusBarView(mNavigationBarView);
        mNavigationBarView.setDelegateView(mSearchPanelView);
    }

    @Override
    public void showSearchPanel() {
        super.showSearchPanel();
<<<<<<< HEAD
        WindowManager.LayoutParams lp =
            (android.view.WindowManager.LayoutParams) mNavigationBarView.getLayoutParams();
        lp.flags &= ~WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL;
        WindowManagerImpl.getDefault().updateViewLayout(mNavigationBarView, lp);
=======
        mHandler.removeCallbacks(mShowSearchPanel);

        // we want to freeze the sysui state wherever it is
        mSearchPanelView.setSystemUiVisibility(mSystemUiVisibility);

        WindowManager.LayoutParams lp =
            (android.view.WindowManager.LayoutParams) mNavigationBarView.getLayoutParams();
        lp.flags &= ~WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL;
        mWindowManager.updateViewLayout(mNavigationBarView, lp);
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
    }

    @Override
    public void hideSearchPanel() {
        super.hideSearchPanel();
        WindowManager.LayoutParams lp =
            (android.view.WindowManager.LayoutParams) mNavigationBarView.getLayoutParams();
        lp.flags |= WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL;
<<<<<<< HEAD
        WindowManagerImpl.getDefault().updateViewLayout(mNavigationBarView, lp);
=======
        mWindowManager.updateViewLayout(mNavigationBarView, lp);
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
    }

    protected int getStatusBarGravity() {
        return Gravity.TOP | Gravity.FILL_HORIZONTAL;
    }

    public int getStatusBarHeight() {
        if (mNaturalBarHeight < 0) {
            final Resources res = mContext.getResources();
            mNaturalBarHeight =
                    res.getDimensionPixelSize(com.android.internal.R.dimen.status_bar_height);
        }
        return mNaturalBarHeight;
    }

<<<<<<< HEAD
    private int getCloseViewHeight() {
        return mCloseViewHeight;
    }

=======
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
    private View.OnClickListener mRecentsClickListener = new View.OnClickListener() {
        public void onClick(View v) {
            toggleRecentApps();
        }
    };

    private int mShowSearchHoldoff = 0;
    private Runnable mShowSearchPanel = new Runnable() {
        public void run() {
            showSearchPanel();
<<<<<<< HEAD
=======
            awakenDreams();
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
        }
    };

    View.OnTouchListener mHomeSearchActionListener = new View.OnTouchListener() {
        public boolean onTouch(View v, MotionEvent event) {
            switch(event.getAction()) {
            case MotionEvent.ACTION_DOWN:
<<<<<<< HEAD
                if (!shouldDisableNavbarGestures() && !inKeyguardRestrictedInputMode()) {
=======
                if (!shouldDisableNavbarGestures()) {
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
                    mHandler.removeCallbacks(mShowSearchPanel);
                    mHandler.postDelayed(mShowSearchPanel, mShowSearchHoldoff);
                }
            break;

            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                mHandler.removeCallbacks(mShowSearchPanel);
<<<<<<< HEAD
=======
                awakenDreams();
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
            break;
        }
        return false;
        }
    };

<<<<<<< HEAD
    private void prepareNavigationBarView() {
        mNavigationBarView.reorient();
        
        if (mNavigationBarView.getRecentsButton() != null){ 
        	mNavigationBarView.getRecentsButton().setOnClickListener(mRecentsClickListener);
        	mNavigationBarView.getRecentsButton().setOnTouchListener(mRecentsPanel);
        }
        if (mNavigationBarView.getHomeButton() != null) {
        		mNavigationBarView.getHomeButton().setOnTouchListener(mHomeSearchActionListener);
        }
=======
    private void awakenDreams() {
        if (mDreamManager != null) {
            try {
                mDreamManager.awaken();
            } catch (RemoteException e) {
                // fine, stay asleep then
            }
        }
    }

    private void prepareNavigationBarView() {
        mNavigationBarView.reorient();

        mNavigationBarView.getRecentsButton().setOnClickListener(mRecentsClickListener);
        mNavigationBarView.getRecentsButton().setOnTouchListener(mRecentsPreloadOnTouchListener);
        mNavigationBarView.getHomeButton().setOnTouchListener(mHomeSearchActionListener);
        mNavigationBarView.getSearchLight().setOnTouchListener(mHomeSearchActionListener);
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
        updateSearchPanel();
    }

    // For small-screen devices (read: phones) that lack hardware navigation buttons
    private void addNavigationBar() {
        if (DEBUG) Slog.v(TAG, "addNavigationBar: about to add " + mNavigationBarView);
        if (mNavigationBarView == null) return;

        prepareNavigationBarView();

<<<<<<< HEAD
        WindowManagerImpl.getDefault().addView(
                mNavigationBarView, getNavigationBarLayoutParams());
=======
        mWindowManager.addView(mNavigationBarView, getNavigationBarLayoutParams());
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
    }

    private void repositionNavigationBar() {
        if (mNavigationBarView == null) return;

<<<<<<< HEAD
        CustomTheme newTheme = mContext.getResources().getConfiguration().customTheme;
        if (newTheme != null &&
                (mCurrentTheme == null || !mCurrentTheme.equals(newTheme))) {
            // Nevermind, this will be re-created
            return;
        }
        prepareNavigationBarView();

        WindowManagerImpl.getDefault().updateViewLayout(
                mNavigationBarView, getNavigationBarLayoutParams());
=======
        prepareNavigationBarView();

        mWindowManager.updateViewLayout(mNavigationBarView, getNavigationBarLayoutParams());
    }

    private void notifyNavigationBarScreenOn(boolean screenOn) {
        if (mNavigationBarView == null) return;
        mNavigationBarView.notifyScreenOn(screenOn);
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
    }

    private WindowManager.LayoutParams getNavigationBarLayoutParams() {
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams(
                LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.TYPE_NAVIGATION_BAR,
                    0
                    | WindowManager.LayoutParams.FLAG_TOUCHABLE_WHEN_WAKING
                    | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                    | WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
<<<<<<< HEAD
                    | WindowManager.LayoutParams.FLAG_SPLIT_TOUCH,
                PixelFormat.OPAQUE);
        // this will allow the navbar to run in an overlay on devices that support this
        if (ActivityManager.isHighEndGfx(mDisplay)) {
=======
                    | WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH
                    | WindowManager.LayoutParams.FLAG_SPLIT_TOUCH,
                PixelFormat.OPAQUE);
        // this will allow the navbar to run in an overlay on devices that support this
        if (ActivityManager.isHighEndGfx()) {
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
            lp.flags |= WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED;
        }

        lp.setTitle("NavigationBar");
        lp.windowAnimations = 0;
        return lp;
    }

    private void addIntruderView() {
<<<<<<< HEAD
        final int height = getStatusBarHeight();

=======
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_STATUS_BAR_PANEL, // above the status bar!
                WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN
                    | WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
                    | WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                    | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                    | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM
                    | WindowManager.LayoutParams.FLAG_SPLIT_TOUCH,
                PixelFormat.TRANSLUCENT);
        lp.gravity = Gravity.TOP | Gravity.FILL_HORIZONTAL;
        //lp.y += height * 1.5; // FIXME
        lp.setTitle("IntruderAlert");
        lp.packageName = mContext.getPackageName();
        lp.windowAnimations = R.style.Animation_StatusBar_IntruderAlert;

<<<<<<< HEAD
        WindowManagerImpl.getDefault().addView(mIntruderAlertView, lp);
=======
        mWindowManager.addView(mIntruderAlertView, lp);
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
    }

    public void addIcon(String slot, int index, int viewIndex, StatusBarIcon icon) {
        if (SPEW) Slog.d(TAG, "addIcon slot=" + slot + " index=" + index + " viewIndex=" + viewIndex
                + " icon=" + icon);
        StatusBarIconView view = new StatusBarIconView(mContext, slot, null);
        view.set(icon);
        mStatusIcons.addView(view, viewIndex, new LinearLayout.LayoutParams(mIconSize, mIconSize));
    }

    public void updateIcon(String slot, int index, int viewIndex,
            StatusBarIcon old, StatusBarIcon icon) {
        if (SPEW) Slog.d(TAG, "updateIcon slot=" + slot + " index=" + index + " viewIndex=" + viewIndex
                + " old=" + old + " icon=" + icon);
        StatusBarIconView view = (StatusBarIconView)mStatusIcons.getChildAt(viewIndex);
        view.set(icon);
    }

    public void removeIcon(String slot, int index, int viewIndex) {
        if (SPEW) Slog.d(TAG, "removeIcon slot=" + slot + " index=" + index + " viewIndex=" + viewIndex);
        mStatusIcons.removeViewAt(viewIndex);
    }

    public void addNotification(IBinder key, StatusBarNotification notification) {
<<<<<<< HEAD
        /* if (DEBUG) */ Slog.d(TAG, "addNotification score=" + notification.score);
=======
        if (DEBUG) Slog.d(TAG, "addNotification score=" + notification.score);
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
        StatusBarIconView iconView = addNotificationViews(key, notification);
        if (iconView == null) return;

        boolean immersive = false;
        try {
            immersive = ActivityManagerNative.getDefault().isTopActivityImmersive();
            if (DEBUG) {
                Slog.d(TAG, "Top activity is " + (immersive?"immersive":"not immersive"));
            }
        } catch (RemoteException ex) {
        }

        /*
         * DISABLED due to missing API
        if (ENABLE_INTRUDERS && (
                   // TODO(dsandler): Only if the screen is on
                notification.notification.intruderView != null)) {
            Slog.d(TAG, "Presenting high-priority notification");
            // special new transient ticker mode
            // 1. Populate mIntruderAlertView

            if (notification.notification.intruderView == null) {
                Slog.e(TAG, notification.notification.toString() + " wanted to intrude but intruderView was null");
                return;
            }

            // bind the click event to the content area
            PendingIntent contentIntent = notification.notification.contentIntent;
            final View.OnClickListener listener = (contentIntent != null)
                    ? new NotificationClicker(contentIntent,
                            notification.pkg, notification.tag, notification.id)
                    : null;

            mIntruderAlertView.applyIntruderContent(notification.notification.intruderView, listener);

            mCurrentlyIntrudingNotification = notification;

            // 2. Animate mIntruderAlertView in
            mHandler.sendEmptyMessage(MSG_SHOW_INTRUDER);

            // 3. Set alarm to age the notification off (TODO)
            mHandler.removeMessages(MSG_HIDE_INTRUDER);
            if (INTRUDER_ALERT_DECAY_MS > 0) {
                mHandler.sendEmptyMessageDelayed(MSG_HIDE_INTRUDER, INTRUDER_ALERT_DECAY_MS);
            }
        } else
         */

        if (notification.notification.fullScreenIntent != null) {
<<<<<<< HEAD
            // not immersive & a full-screen alert should be shown
            Slog.d(TAG, "Notification has fullScreenIntent; sending fullScreenIntent");
=======
            // Stop screensaver if the notification has a full-screen intent.
            // (like an incoming phone call)
            awakenDreams();

            // not immersive & a full-screen alert should be shown
            if (DEBUG) Slog.d(TAG, "Notification has fullScreenIntent; sending fullScreenIntent");
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
            try {
                notification.notification.fullScreenIntent.send();
            } catch (PendingIntent.CanceledException e) {
            }
<<<<<<< HEAD
        } else if (!mRecreating) {
=======
        } else {
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
            // usual case: status bar visible & not immersive

            // show the ticker if there isn't an intruder too
            if (mCurrentlyIntrudingNotification == null) {
                tick(null, notification, true);
            }
        }

        // Recalculate the position of the sliding windows and the titles.
        setAreThereNotifications();
        updateExpandedViewPos(EXPANDED_LEAVE_ALONE);
    }

    public void removeNotification(IBinder key) {
        StatusBarNotification old = removeNotificationViews(key);
        if (SPEW) Slog.d(TAG, "removeNotification key=" + key + " old=" + old);

        if (old != null) {
            // Cancel the ticker if it's still running
            mTicker.removeEntry(old);

            // Recalculate the position of the sliding windows and the titles.
            updateExpandedViewPos(EXPANDED_LEAVE_ALONE);

            if (ENABLE_INTRUDERS && old == mCurrentlyIntrudingNotification) {
                mHandler.sendEmptyMessage(MSG_HIDE_INTRUDER);
            }

            if (CLOSE_PANEL_WHEN_EMPTIED && mNotificationData.size() == 0 && !mAnimating) {
<<<<<<< HEAD
                animateCollapse();
=======
                animateCollapsePanels();
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
            }
        }

        setAreThereNotifications();
    }

<<<<<<< HEAD
    @Override
    protected void onConfigurationChanged(Configuration newConfig) {
        try {
            updateRecentsPanel();
        } catch(android.view.InflateException avIE) {
            Log.d(TAG, "Reinflation failure for the recents panel, new theme is '" + newConfig.customTheme + "' vs current '" + mContext.getResources().getConfiguration().customTheme + "'");
        }
        updateShowSearchHoldoff();
    }

=======
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
    private void updateShowSearchHoldoff() {
        mShowSearchHoldoff = mContext.getResources().getInteger(
            R.integer.config_show_search_delay);
    }

    private void loadNotificationShade() {
        if (mPile == null) return;

        int N = mNotificationData.size();

        ArrayList<View> toShow = new ArrayList<View>();

        final boolean provisioned = isDeviceProvisioned();
        // If the device hasn't been through Setup, we only show system notifications
        for (int i=0; i<N; i++) {
            Entry ent = mNotificationData.get(N-i-1);
<<<<<<< HEAD
            if (provisioned || showNotificationEvenIfUnprovisioned(ent.notification)) {
                toShow.add(ent.row);
            }
=======
            if (!(provisioned || showNotificationEvenIfUnprovisioned(ent.notification))) continue;
            if (!notificationIsForCurrentUser(ent.notification)) continue;
            toShow.add(ent.row);
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
        }

        ArrayList<View> toRemove = new ArrayList<View>();
        for (int i=0; i<mPile.getChildCount(); i++) {
            View child = mPile.getChildAt(i);
            if (!toShow.contains(child)) {
                toRemove.add(child);
            }
        }

        for (View remove : toRemove) {
            mPile.removeView(remove);
        }

        for (int i=0; i<toShow.size(); i++) {
            View v = toShow.get(i);
            if (v.getParent() == null) {
                mPile.addView(v, i);
            }
        }

<<<<<<< HEAD
        mSettingsButton.setEnabled(isDeviceProvisioned());
    }

    private void reloadAllNotificationIcons() {
        if (mNotificationIcons == null) return;
        mNotificationIcons.removeAllViews();
        updateNotificationIcons();
=======
        if (mSettingsButton != null) {
            mSettingsButton.setEnabled(isDeviceProvisioned());
        }
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
    }

    @Override
    protected void updateNotificationIcons() {
        if (mNotificationIcons == null) return;

        loadNotificationShade();

        final LinearLayout.LayoutParams params
            = new LinearLayout.LayoutParams(mIconSize + 2*mIconHPadding, mNaturalBarHeight);

        int N = mNotificationData.size();

        if (DEBUG) {
            Slog.d(TAG, "refreshing icons: " + N + " notifications, mNotificationIcons=" + mNotificationIcons);
        }

        ArrayList<View> toShow = new ArrayList<View>();

        final boolean provisioned = isDeviceProvisioned();
        // If the device hasn't been through Setup, we only show system notifications
        for (int i=0; i<N; i++) {
            Entry ent = mNotificationData.get(N-i-1);
<<<<<<< HEAD
            if ((provisioned && ent.notification.score >= HIDE_ICONS_BELOW_SCORE)
                    || showNotificationEvenIfUnprovisioned(ent.notification)) {
                toShow.add(ent.icon);
            }
=======
            if (!((provisioned && ent.notification.score >= HIDE_ICONS_BELOW_SCORE)
                    || showNotificationEvenIfUnprovisioned(ent.notification))) continue;
            if (!notificationIsForCurrentUser(ent.notification)) continue;
            toShow.add(ent.icon);
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
        }

        ArrayList<View> toRemove = new ArrayList<View>();
        for (int i=0; i<mNotificationIcons.getChildCount(); i++) {
            View child = mNotificationIcons.getChildAt(i);
            if (!toShow.contains(child)) {
                toRemove.add(child);
            }
        }

        for (View remove : toRemove) {
            mNotificationIcons.removeView(remove);
        }

        for (int i=0; i<toShow.size(); i++) {
            View v = toShow.get(i);
            if (v.getParent() == null) {
                mNotificationIcons.addView(v, i, params);
            }
        }
    }

    protected void updateCarrierLabelVisibility(boolean force) {
<<<<<<< HEAD
        if (!SHOW_CARRIER_LABEL) return;
=======
        if (!mShowCarrierInPanel) return;
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
        // The idea here is to only show the carrier label when there is enough room to see it, 
        // i.e. when there aren't enough notifications to fill the panel.
        if (DEBUG) {
            Slog.d(TAG, String.format("pileh=%d scrollh=%d carrierh=%d",
                    mPile.getHeight(), mScrollView.getHeight(), mCarrierLabelHeight));
        }
<<<<<<< HEAD
        
        final boolean makeVisible = 
            mPile.getHeight() < (mScrollView.getHeight() - mCarrierLabelHeight);
=======

        final boolean emergencyCallsShownElsewhere = mEmergencyCallLabel != null;
        final boolean makeVisible =
            !(emergencyCallsShownElsewhere && mNetworkController.isEmergencyOnly())
            && mPile.getHeight() < (mNotificationPanel.getHeight() - mCarrierLabelHeight - mNotificationHeaderHeight)
            && mScrollView.getVisibility() == View.VISIBLE;
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
        
        if (force || mCarrierLabelVisible != makeVisible) {
            mCarrierLabelVisible = makeVisible;
            if (DEBUG) {
                Slog.d(TAG, "making carrier label " + (makeVisible?"visible":"invisible"));
            }
            mCarrierLabel.animate().cancel();
            if (makeVisible) {
                mCarrierLabel.setVisibility(View.VISIBLE);
            }
            mCarrierLabel.animate()
                .alpha(makeVisible ? 1f : 0f)
                //.setStartDelay(makeVisible ? 500 : 0)
                //.setDuration(makeVisible ? 750 : 100)
                .setDuration(150)
                .setListener(makeVisible ? null : new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        if (!mCarrierLabelVisible) { // race
                            mCarrierLabel.setVisibility(View.INVISIBLE);
                            mCarrierLabel.setAlpha(0f);
                        }
                    }
                })
                .start();
        }
    }

    @Override
    protected void setAreThereNotifications() {
        final boolean any = mNotificationData.size() > 0;

        final boolean clearable = any && mNotificationData.hasClearableItems();
<<<<<<< HEAD
        
        mBatteryNotification.isVisible(!clearable);
=======
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a

        if (DEBUG) {
            Slog.d(TAG, "setAreThereNotifications: N=" + mNotificationData.size()
                    + " any=" + any + " clearable=" + clearable);
        }

<<<<<<< HEAD
        if (mClearButton.isShown()) {
=======
        if (mHasFlipSettings 
                && mFlipSettingsView != null 
                && mFlipSettingsView.getVisibility() == View.VISIBLE
                && mScrollView.getVisibility() != View.VISIBLE) {
            // the flip settings panel is unequivocally showing; we should not be shown
            mClearButton.setVisibility(View.INVISIBLE);
        } else if (mClearButton.isShown()) {
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
            if (clearable != (mClearButton.getAlpha() == 1.0f)) {
                ObjectAnimator clearAnimation = ObjectAnimator.ofFloat(
                        mClearButton, "alpha", clearable ? 1.0f : 0.0f).setDuration(250);
                clearAnimation.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        if (mClearButton.getAlpha() <= 0.0f) {
                            mClearButton.setVisibility(View.INVISIBLE);
                        }
                    }

                    @Override
                    public void onAnimationStart(Animator animation) {
                        if (mClearButton.getAlpha() <= 0.0f) {
                            mClearButton.setVisibility(View.VISIBLE);
                        }
                    }
                });
                clearAnimation.start();
            }
        } else {
            mClearButton.setAlpha(clearable ? 1.0f : 0.0f);
            mClearButton.setVisibility(clearable ? View.VISIBLE : View.INVISIBLE);
        }
        mClearButton.setEnabled(clearable);

        final View nlo = mStatusBarView.findViewById(R.id.notification_lights_out);
        final boolean showDot = (any&&!areLightsOn());
        if (showDot != (nlo.getAlpha() == 1.0f)) {
            if (showDot) {
                nlo.setAlpha(0f);
                nlo.setVisibility(View.VISIBLE);
            }
            nlo.animate()
                .alpha(showDot?1:0)
                .setDuration(showDot?750:250)
                .setInterpolator(new AccelerateInterpolator(2.0f))
                .setListener(showDot ? null : new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator _a) {
                        nlo.setVisibility(View.GONE);
                    }
                })
                .start();
        }

        updateCarrierLabelVisibility(false);
    }

    public void showClock(boolean show) {
        if (mStatusBarView == null) return;
<<<<<<< HEAD
        boolean rightClock = (Settings.System.getInt(mContext.getContentResolver(), Settings.System.STATUSBAR_CLOCK_STYLE, 1) == 1);
        boolean centerClock = (Settings.System.getInt(mContext.getContentResolver(), Settings.System.STATUSBAR_CLOCK_STYLE, 1) == 2);
        if (rightClock && clock != null) {
            clock.setVisibility(show ? View.VISIBLE : View.GONE);
        }
        if (centerClock && cclock != null) {
        	cclock.setVisibility(show ? View.VISIBLE : View.GONE);
        }
=======
        View clock = mStatusBarView.findViewById(R.id.clock);
        if (clock != null) {
            clock.setVisibility(show ? View.VISIBLE : View.GONE);
        }
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
    }

    /**
     * State is one or more of the DISABLE constants from StatusBarManager.
     */
    public void disable(int state) {
        final int old = mDisabled;
        final int diff = state ^ old;
        mDisabled = state;

        if (DEBUG) {
            Slog.d(TAG, String.format("disable: 0x%08x -> 0x%08x (diff: 0x%08x)",
                old, state, diff));
        }

        StringBuilder flagdbg = new StringBuilder();
        flagdbg.append("disable: < ");
        flagdbg.append(((state & StatusBarManager.DISABLE_EXPAND) != 0) ? "EXPAND" : "expand");
        flagdbg.append(((diff  & StatusBarManager.DISABLE_EXPAND) != 0) ? "* " : " ");
        flagdbg.append(((state & StatusBarManager.DISABLE_NOTIFICATION_ICONS) != 0) ? "ICONS" : "icons");
        flagdbg.append(((diff  & StatusBarManager.DISABLE_NOTIFICATION_ICONS) != 0) ? "* " : " ");
        flagdbg.append(((state & StatusBarManager.DISABLE_NOTIFICATION_ALERTS) != 0) ? "ALERTS" : "alerts");
        flagdbg.append(((diff  & StatusBarManager.DISABLE_NOTIFICATION_ALERTS) != 0) ? "* " : " ");
        flagdbg.append(((state & StatusBarManager.DISABLE_NOTIFICATION_TICKER) != 0) ? "TICKER" : "ticker");
        flagdbg.append(((diff  & StatusBarManager.DISABLE_NOTIFICATION_TICKER) != 0) ? "* " : " ");
        flagdbg.append(((state & StatusBarManager.DISABLE_SYSTEM_INFO) != 0) ? "SYSTEM_INFO" : "system_info");
        flagdbg.append(((diff  & StatusBarManager.DISABLE_SYSTEM_INFO) != 0) ? "* " : " ");
        flagdbg.append(((state & StatusBarManager.DISABLE_BACK) != 0) ? "BACK" : "back");
        flagdbg.append(((diff  & StatusBarManager.DISABLE_BACK) != 0) ? "* " : " ");
        flagdbg.append(((state & StatusBarManager.DISABLE_HOME) != 0) ? "HOME" : "home");
        flagdbg.append(((diff  & StatusBarManager.DISABLE_HOME) != 0) ? "* " : " ");
        flagdbg.append(((state & StatusBarManager.DISABLE_RECENT) != 0) ? "RECENT" : "recent");
        flagdbg.append(((diff  & StatusBarManager.DISABLE_RECENT) != 0) ? "* " : " ");
        flagdbg.append(((state & StatusBarManager.DISABLE_CLOCK) != 0) ? "CLOCK" : "clock");
        flagdbg.append(((diff  & StatusBarManager.DISABLE_CLOCK) != 0) ? "* " : " ");
<<<<<<< HEAD
=======
        flagdbg.append(((state & StatusBarManager.DISABLE_SEARCH) != 0) ? "SEARCH" : "search");
        flagdbg.append(((diff  & StatusBarManager.DISABLE_SEARCH) != 0) ? "* " : " ");
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
        flagdbg.append(">");
        Slog.d(TAG, flagdbg.toString());

        if ((diff & StatusBarManager.DISABLE_SYSTEM_INFO) != 0) {
<<<<<<< HEAD
            mIcons.animate().cancel();
            if ((state & StatusBarManager.DISABLE_SYSTEM_INFO) != 0) {
                if (mTicking) {
                    mTicker.halt();
                }
                mIcons.animate()
                    .alpha(0f)
                    .translationY(mNaturalBarHeight*0.5f)
                    //.setStartDelay(100)
=======
            mSystemIconArea.animate().cancel();
            if ((state & StatusBarManager.DISABLE_SYSTEM_INFO) != 0) {
                mSystemIconArea.animate()
                    .alpha(0f)
                    .translationY(mNaturalBarHeight*0.5f)
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
                    .setDuration(175)
                    .setInterpolator(new DecelerateInterpolator(1.5f))
                    .setListener(mMakeIconsInvisible)
                    .start();
            } else {
<<<<<<< HEAD
                mIcons.setVisibility(View.VISIBLE);
                mIcons.animate()
=======
                mSystemIconArea.setVisibility(View.VISIBLE);
                mSystemIconArea.animate()
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
                    .alpha(1f)
                    .translationY(0)
                    .setStartDelay(0)
                    .setInterpolator(new DecelerateInterpolator(1.5f))
                    .setDuration(175)
                    .start();
            }
        }

        if ((diff & StatusBarManager.DISABLE_CLOCK) != 0) {
            boolean show = (state & StatusBarManager.DISABLE_CLOCK) == 0;
            showClock(show);
        }
        if ((diff & StatusBarManager.DISABLE_EXPAND) != 0) {
            if ((state & StatusBarManager.DISABLE_EXPAND) != 0) {
<<<<<<< HEAD
                animateCollapse();
=======
                animateCollapsePanels();
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
            }
        }

        if ((diff & (StatusBarManager.DISABLE_HOME
                        | StatusBarManager.DISABLE_RECENT
<<<<<<< HEAD
                        | StatusBarManager.DISABLE_BACK)) != 0) {
=======
                        | StatusBarManager.DISABLE_BACK
                        | StatusBarManager.DISABLE_SEARCH)) != 0) {
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
            // the nav bar will take care of these
            if (mNavigationBarView != null) mNavigationBarView.setDisabledFlags(state);

            if ((state & StatusBarManager.DISABLE_RECENT) != 0) {
                // close recents if it's visible
                mHandler.removeMessages(MSG_CLOSE_RECENTS_PANEL);
                mHandler.sendEmptyMessage(MSG_CLOSE_RECENTS_PANEL);
            }
        }

        if ((diff & StatusBarManager.DISABLE_NOTIFICATION_ICONS) != 0) {
            if ((state & StatusBarManager.DISABLE_NOTIFICATION_ICONS) != 0) {
                if (mTicking) {
<<<<<<< HEAD
                    mTicker.halt();
                } else {
                    setNotificationIconVisibility(false, com.android.internal.R.anim.fade_out);
                }
            } else {
                if (!mExpandedVisible) {
                    setNotificationIconVisibility(true, com.android.internal.R.anim.fade_in);
                }
            }
        } else if ((diff & StatusBarManager.DISABLE_NOTIFICATION_TICKER) != 0) {
            if (mTicking && (state & StatusBarManager.DISABLE_NOTIFICATION_TICKER) != 0) {
                mTicker.halt();
=======
                    haltTicker();
                }

                mNotificationIcons.animate()
                    .alpha(0f)
                    .translationY(mNaturalBarHeight*0.5f)
                    .setDuration(175)
                    .setInterpolator(new DecelerateInterpolator(1.5f))
                    .setListener(mMakeIconsInvisible)
                    .start();
            } else {
                mNotificationIcons.setVisibility(View.VISIBLE);
                mNotificationIcons.animate()
                    .alpha(1f)
                    .translationY(0)
                    .setStartDelay(0)
                    .setInterpolator(new DecelerateInterpolator(1.5f))
                    .setDuration(175)
                    .start();
            }
        } else if ((diff & StatusBarManager.DISABLE_NOTIFICATION_TICKER) != 0) {
            if (mTicking && (state & StatusBarManager.DISABLE_NOTIFICATION_TICKER) != 0) {
                haltTicker();
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
            }
        }
    }

    @Override
    protected BaseStatusBar.H createHandler() {
        return new PhoneStatusBar.H();
    }

    /**
     * All changes to the status bar and notifications funnel through here and are batched.
     */
    private class H extends BaseStatusBar.H {
        public void handleMessage(Message m) {
            super.handleMessage(m);
            switch (m.what) {
                case MSG_OPEN_NOTIFICATION_PANEL:
<<<<<<< HEAD
                    animateExpand();
                    break;
                case MSG_CLOSE_NOTIFICATION_PANEL:
                    animateCollapse();
=======
                    animateExpandNotificationsPanel();
                    break;
                case MSG_OPEN_SETTINGS_PANEL:
                    animateExpandSettingsPanel();
                    break;
                case MSG_CLOSE_PANELS:
                    animateCollapsePanels();
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
                    break;
                case MSG_SHOW_INTRUDER:
                    setIntruderAlertVisibility(true);
                    break;
                case MSG_HIDE_INTRUDER:
                    setIntruderAlertVisibility(false);
                    mCurrentlyIntrudingNotification = null;
                    break;
<<<<<<< HEAD
                case MSG_STATUSBAR_BRIGHTNESS:
                    if (mIsStatusBarBrightNess) {
                        mIsBrightNessMode = 1;
                        // don't collapse the statusbar to see %
                        // updateExpandedViewPos(0);
                        // performCollapse();
                    }
                    break;
=======
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
            }
        }
    }

<<<<<<< HEAD
    final Runnable mAnimationCallback = new Runnable() {
        @Override
        public void run() {
            doAnimation(mChoreographer.getFrameTimeNanos());
        }
    };

    final Runnable mRevealAnimationCallback = new Runnable() {
        @Override
        public void run() {
            doRevealAnimation(mChoreographer.getFrameTimeNanos());
        }
    };
=======
    public Handler getHandler() {
        return mHandler;
    }
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a

    View.OnFocusChangeListener mFocusChangeListener = new View.OnFocusChangeListener() {
        public void onFocusChange(View v, boolean hasFocus) {
            // Because 'v' is a ViewGroup, all its children will be (un)selected
            // too, which allows marqueeing to work.
            v.setSelected(hasFocus);
        }
    };

<<<<<<< HEAD
    private void makeExpandedVisible(boolean revealAfterDraw) {
=======
    void makeExpandedVisible(boolean revealAfterDraw) {
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
        if (SPEW) Slog.d(TAG, "Make expanded visible: expanded visible=" + mExpandedVisible);
        if (mExpandedVisible) {
            return;
        }

        mExpandedVisible = true;
        mPile.setLayoutTransitionsEnabled(true);
        if (mNavigationBarView != null)
            mNavigationBarView.setSlippery(true);

        updateCarrierLabelVisibility(true);

        updateExpandedViewPos(EXPANDED_LEAVE_ALONE);

        // Expand the window to encompass the full screen in anticipation of the drag.
        // This is only possible to do atomically because the status bar is at the top of the screen!
<<<<<<< HEAD
        WindowManager.LayoutParams lp = (WindowManager.LayoutParams) mStatusBarContainer.getLayoutParams();
        lp.flags &= ~WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        lp.flags |= WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM;
        lp.height = ViewGroup.LayoutParams.MATCH_PARENT;
        final WindowManager wm = WindowManagerImpl.getDefault();
        wm.updateViewLayout(mStatusBarContainer, lp);
=======
        WindowManager.LayoutParams lp = (WindowManager.LayoutParams) mStatusBarWindow.getLayoutParams();
        lp.flags &= ~WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        lp.flags |= WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM;
        lp.height = ViewGroup.LayoutParams.MATCH_PARENT;
        mWindowManager.updateViewLayout(mStatusBarWindow, lp);
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a

        // Updating the window layout will force an expensive traversal/redraw.
        // Kick off the reveal animation after this is complete to avoid animation latency.
        if (revealAfterDraw) {
<<<<<<< HEAD
            mHandler.post(mStartRevealAnimation);
=======
//            mHandler.post(mStartRevealAnimation);
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
        }

        visibilityChanged(true);
    }

<<<<<<< HEAD
    public void animateExpand() {
        if (SPEW) Slog.d(TAG, "Animate expand: expanded=" + mExpanded);
        if ((mDisabled & StatusBarManager.DISABLE_EXPAND) != 0) {
            return ;
        }
        if (mExpanded) {
            return;
        }

        prepareTracking(0, true);
        mHandler.post(mPerformSelfExpandFling);
    }

    public void animateCollapse() {
        animateCollapse(CommandQueue.FLAG_EXCLUDE_NONE);
    }

    public void animateCollapse(int flags) {
        animateCollapse(flags, 1.0f);
    }

    public void animateCollapse(int flags, float velocityMultiplier) {
        if (SPEW) {
            Slog.d(TAG, "animateCollapse(): mExpanded=" + mExpanded
                    + " mExpandedVisible=" + mExpandedVisible
                    + " mExpanded=" + mExpanded
=======
    public void animateCollapsePanels() {
        animateCollapsePanels(CommandQueue.FLAG_EXCLUDE_NONE);
    }

    public void animateCollapsePanels(int flags) {
        if (SPEW) {
            Slog.d(TAG, "animateCollapse():"
                    + " mExpandedVisible=" + mExpandedVisible
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
                    + " mAnimating=" + mAnimating
                    + " mAnimatingReveal=" + mAnimatingReveal
                    + " mAnimY=" + mAnimY
                    + " mAnimVel=" + mAnimVel
                    + " flags=" + flags);
        }

        if ((flags & CommandQueue.FLAG_EXCLUDE_RECENTS_PANEL) == 0) {
            mHandler.removeMessages(MSG_CLOSE_RECENTS_PANEL);
            mHandler.sendEmptyMessage(MSG_CLOSE_RECENTS_PANEL);
        }

        if ((flags & CommandQueue.FLAG_EXCLUDE_SEARCH_PANEL) == 0) {
            mHandler.removeMessages(MSG_CLOSE_SEARCH_PANEL);
            mHandler.sendEmptyMessage(MSG_CLOSE_SEARCH_PANEL);
        }

<<<<<<< HEAD
        if (!mExpandedVisible) {
            return;
        }

        int y;
        if (mAnimating || mAnimatingReveal) {
            y = (int)mAnimY;
        } else {
            y = getExpandedViewMaxHeight()-1;
        }
        // Let the fling think that we're open so it goes in the right direction
        // and doesn't try to re-open the windowshade.
        mExpanded = true;
        prepareTracking(y, false);
        performFling(y, -mSelfCollapseVelocityPx*velocityMultiplier, true);
    }

    void performExpand() {
        if (SPEW) Slog.d(TAG, "performExpand: mExpanded=" + mExpanded);
        if ((mDisabled & StatusBarManager.DISABLE_EXPAND) != 0) {
            return ;
        }
        if (mExpanded) {
            return;
        }

        mExpanded = true;
        makeExpandedVisible(false);
        updateExpandedViewPos(EXPANDED_FULL_OPEN);
=======
        mStatusBarWindow.cancelExpandHelper();
        mStatusBarView.collapseAllPanels(true);
    }

    public ViewPropertyAnimator setVisibilityWhenDone(
            final ViewPropertyAnimator a, final View v, final int vis) {
        a.setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                v.setVisibility(vis);
                a.setListener(null); // oneshot
            }
        });
        return a;
    }

    public Animator setVisibilityWhenDone(
            final Animator a, final View v, final int vis) {
        a.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                v.setVisibility(vis);
            }
        });
        return a;
    }

    public Animator interpolator(TimeInterpolator ti, Animator a) {
        a.setInterpolator(ti);
        return a;
    }

    public Animator startDelay(int d, Animator a) {
        a.setStartDelay(d);
        return a;
    }
    
    public Animator start(Animator a) {
        a.start();
        return a;
    }

    final TimeInterpolator mAccelerateInterpolator = new AccelerateInterpolator();
    final TimeInterpolator mDecelerateInterpolator = new DecelerateInterpolator();
    final int FLIP_DURATION_OUT = 125;
    final int FLIP_DURATION_IN = 225;
    final int FLIP_DURATION = (FLIP_DURATION_IN + FLIP_DURATION_OUT);

    Animator mScrollViewAnim, mFlipSettingsViewAnim, mNotificationButtonAnim,
        mSettingsButtonAnim, mClearButtonAnim;

    @Override
    public void animateExpandNotificationsPanel() {
        if (SPEW) Slog.d(TAG, "animateExpand: mExpandedVisible=" + mExpandedVisible);
        if ((mDisabled & StatusBarManager.DISABLE_EXPAND) != 0) {
            return ;
        }

        mNotificationPanel.expand();
        if (mHasFlipSettings && mScrollView.getVisibility() != View.VISIBLE) {
            flipToNotifications();
        }

        if (false) postStartTracing();
    }

    public void flipToNotifications() {
        if (mFlipSettingsViewAnim != null) mFlipSettingsViewAnim.cancel();
        if (mScrollViewAnim != null) mScrollViewAnim.cancel();
        if (mSettingsButtonAnim != null) mSettingsButtonAnim.cancel();
        if (mNotificationButtonAnim != null) mNotificationButtonAnim.cancel();
        if (mClearButtonAnim != null) mClearButtonAnim.cancel();

        mScrollView.setVisibility(View.VISIBLE);
        mScrollViewAnim = start(
            startDelay(FLIP_DURATION_OUT,
                interpolator(mDecelerateInterpolator,
                    ObjectAnimator.ofFloat(mScrollView, View.SCALE_X, 0f, 1f)
                        .setDuration(FLIP_DURATION_IN)
                    )));
        mFlipSettingsViewAnim = start(
            setVisibilityWhenDone(
                interpolator(mAccelerateInterpolator,
                        ObjectAnimator.ofFloat(mFlipSettingsView, View.SCALE_X, 1f, 0f)
                        )
                    .setDuration(FLIP_DURATION_OUT),
                mFlipSettingsView, View.INVISIBLE));
        mNotificationButtonAnim = start(
            setVisibilityWhenDone(
                ObjectAnimator.ofFloat(mNotificationButton, View.ALPHA, 0f)
                    .setDuration(FLIP_DURATION),
                mNotificationButton, View.INVISIBLE));
        mSettingsButton.setVisibility(View.VISIBLE);
        mSettingsButtonAnim = start(
            ObjectAnimator.ofFloat(mSettingsButton, View.ALPHA, 1f)
                .setDuration(FLIP_DURATION));
        mClearButton.setVisibility(View.VISIBLE);
        mClearButton.setAlpha(0f);
        setAreThereNotifications(); // this will show/hide the button as necessary
        mNotificationPanel.postDelayed(new Runnable() {
            public void run() {
                updateCarrierLabelVisibility(false);
            }
        }, FLIP_DURATION - 150);
    }

    @Override
    public void animateExpandSettingsPanel() {
        if (SPEW) Slog.d(TAG, "animateExpand: mExpandedVisible=" + mExpandedVisible);
        if ((mDisabled & StatusBarManager.DISABLE_EXPAND) != 0) {
            return;
        }

        if (mHasFlipSettings) {
            mNotificationPanel.expand();
            if (mFlipSettingsView.getVisibility() != View.VISIBLE) {
                flipToSettings();
            }
        } else if (mSettingsPanel != null) {
            mSettingsPanel.expand();
        }
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a

        if (false) postStartTracing();
    }

<<<<<<< HEAD
    void performCollapse() {
        if (SPEW) Slog.d(TAG, "performCollapse: mExpanded=" + mExpanded
=======
    public void switchToSettings() {
        mFlipSettingsView.setScaleX(1f);
        mFlipSettingsView.setVisibility(View.VISIBLE);
        mSettingsButton.setVisibility(View.GONE);
        mScrollView.setVisibility(View.GONE);
        mScrollView.setScaleX(0f);
        mNotificationButton.setVisibility(View.VISIBLE);
        mNotificationButton.setAlpha(1f);
        mClearButton.setVisibility(View.GONE);
    }

    public void flipToSettings() {
        if (mFlipSettingsViewAnim != null) mFlipSettingsViewAnim.cancel();
        if (mScrollViewAnim != null) mScrollViewAnim.cancel();
        if (mSettingsButtonAnim != null) mSettingsButtonAnim.cancel();
        if (mNotificationButtonAnim != null) mNotificationButtonAnim.cancel();
        if (mClearButtonAnim != null) mClearButtonAnim.cancel();

        mFlipSettingsView.setVisibility(View.VISIBLE);
        mFlipSettingsView.setScaleX(0f);
        mFlipSettingsViewAnim = start(
            startDelay(FLIP_DURATION_OUT,
                interpolator(mDecelerateInterpolator,
                    ObjectAnimator.ofFloat(mFlipSettingsView, View.SCALE_X, 0f, 1f)
                        .setDuration(FLIP_DURATION_IN)
                    )));
        mScrollViewAnim = start(
            setVisibilityWhenDone(
                interpolator(mAccelerateInterpolator,
                        ObjectAnimator.ofFloat(mScrollView, View.SCALE_X, 1f, 0f)
                        )
                    .setDuration(FLIP_DURATION_OUT), 
                mScrollView, View.INVISIBLE));
        mSettingsButtonAnim = start(
            setVisibilityWhenDone(
                ObjectAnimator.ofFloat(mSettingsButton, View.ALPHA, 0f)
                    .setDuration(FLIP_DURATION),
                    mScrollView, View.INVISIBLE));
        mNotificationButton.setVisibility(View.VISIBLE);
        mNotificationButtonAnim = start(
            ObjectAnimator.ofFloat(mNotificationButton, View.ALPHA, 1f)
                .setDuration(FLIP_DURATION));
        mClearButtonAnim = start(
            setVisibilityWhenDone(
                ObjectAnimator.ofFloat(mClearButton, View.ALPHA, 0f)
                .setDuration(FLIP_DURATION),
                mClearButton, View.INVISIBLE));
        mNotificationPanel.postDelayed(new Runnable() {
            public void run() {
                updateCarrierLabelVisibility(false);
            }
        }, FLIP_DURATION - 150);
    }

    public void flipPanels() {
        if (mHasFlipSettings) {
            if (mFlipSettingsView.getVisibility() != View.VISIBLE) {
                flipToSettings();
            } else {
                flipToNotifications();
            }
        }
    }

    public void animateCollapseQuickSettings() {
        mStatusBarView.collapseAllPanels(true);
    }

    void makeExpandedInvisibleSoon() {
        mHandler.postDelayed(new Runnable() { public void run() { makeExpandedInvisible(); }}, 50);
    }

    void makeExpandedInvisible() {
        if (SPEW) Slog.d(TAG, "makeExpandedInvisible: mExpandedVisible=" + mExpandedVisible
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
                + " mExpandedVisible=" + mExpandedVisible);

        if (!mExpandedVisible) {
            return;
        }

<<<<<<< HEAD
        // Ensure the panel is fully collapsed (just in case; bug 6765842)
        updateExpandedViewPos(0);
=======
        // Ensure the panel is fully collapsed (just in case; bug 6765842, 7260868)
        mStatusBarView.collapseAllPanels(/*animate=*/ false);

        if (mHasFlipSettings) {
            // reset things to their proper state
            if (mFlipSettingsViewAnim != null) mFlipSettingsViewAnim.cancel();
            if (mScrollViewAnim != null) mScrollViewAnim.cancel();
            if (mSettingsButtonAnim != null) mSettingsButtonAnim.cancel();
            if (mNotificationButtonAnim != null) mNotificationButtonAnim.cancel();
            if (mClearButtonAnim != null) mClearButtonAnim.cancel();

            mScrollView.setScaleX(1f);
            mScrollView.setVisibility(View.VISIBLE);
            mSettingsButton.setAlpha(1f);
            mSettingsButton.setVisibility(View.VISIBLE);
            mNotificationPanel.setVisibility(View.GONE);
            mFlipSettingsView.setVisibility(View.GONE);
            mNotificationButton.setVisibility(View.GONE);
            setAreThereNotifications(); // show the clear button
        }
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a

        mExpandedVisible = false;
        mPile.setLayoutTransitionsEnabled(false);
        if (mNavigationBarView != null)
            mNavigationBarView.setSlippery(false);
        visibilityChanged(false);

        // Shrink the window to the size of the status bar only
<<<<<<< HEAD
        WindowManager.LayoutParams lp = (WindowManager.LayoutParams) mStatusBarContainer.getLayoutParams();
        lp.height = getStatusBarHeight();
        lp.flags |= WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        lp.flags &= ~WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM;
        final WindowManager wm = WindowManagerImpl.getDefault();
        wm.updateViewLayout(mStatusBarContainer, lp);
=======
        WindowManager.LayoutParams lp = (WindowManager.LayoutParams) mStatusBarWindow.getLayoutParams();
        lp.height = getStatusBarHeight();
        lp.flags |= WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        lp.flags &= ~WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM;
        mWindowManager.updateViewLayout(mStatusBarWindow, lp);
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a

        if ((mDisabled & StatusBarManager.DISABLE_NOTIFICATION_ICONS) == 0) {
            setNotificationIconVisibility(true, com.android.internal.R.anim.fade_in);
        }

<<<<<<< HEAD
        if (mQuickTogglesHideAfterCollapse) {
            mQuickToggles.setVisibility(View.GONE);
            if ((mWeatherHideStatus == 2) && mWeatherPanelEnabled) {
                  mWeatherPanel.setVisibility(View.VISIBLE);
            } else if ((mWeatherHideStatus == 1) && mWeatherPanelEnabled) {
                  mWeatherPanel.setVisibility(View.GONE);
            }
        }

        if (!mExpanded) {
            return;
        }
        mExpanded = false;

        // Close any "App info" popups that might have snuck on-screen
        dismissPopups();
=======
        // Close any "App info" popups that might have snuck on-screen
        dismissPopups();
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a

        if (mPostCollapseCleanup != null) {
            mPostCollapseCleanup.run();
            mPostCollapseCleanup = null;
        }
    }

<<<<<<< HEAD
    void resetLastAnimTime() {
        mAnimLastTimeNanos = System.nanoTime();
        if (SPEW) {
            Throwable t = new Throwable();
            t.fillInStackTrace();
            Slog.d(TAG, "resetting last anim time=" + mAnimLastTimeNanos, t);
        }
    }

    void doAnimation(long frameTimeNanos) {
        if (mAnimating) {
            if (SPEW) Slog.d(TAG, "doAnimation dt=" + (frameTimeNanos - mAnimLastTimeNanos));
            if (SPEW) Slog.d(TAG, "doAnimation before mAnimY=" + mAnimY);
            incrementAnim(frameTimeNanos);
            if (SPEW) {
                Slog.d(TAG, "doAnimation after  mAnimY=" + mAnimY);
                Slog.d(TAG, "doAnimation expandedViewMax=" + getExpandedViewMaxHeight());
            }

            if (mAnimY >= getExpandedViewMaxHeight()-1 && !mClosing) {
                if (SPEW) Slog.d(TAG, "Animation completed to expanded state.");
                mAnimating = false;
                updateExpandedViewPos(EXPANDED_FULL_OPEN);
                performExpand();
                return;
            }

            if (mAnimY == 0 && mAnimAccel == 0 && mClosing) {
                if (SPEW) Slog.d(TAG, "Animation completed to collapsed state.");
                mAnimating = false;
                performCollapse();
                return;
            }

            if (mAnimY < getStatusBarHeight() && mClosing) {
                // Draw one more frame with the bar positioned at the top of the screen
                // before ending the animation so that the user sees the bar in
                // its final position.  The call to performCollapse() causes a window
                // relayout which takes time and might cause the animation to skip
                // on the very last frame before the bar disappears if we did it now.
                mAnimY = 0;
                mAnimAccel = 0;
                mAnimVel = 0;
            }

            updateExpandedViewPos((int)mAnimY);
            mChoreographer.postCallback(Choreographer.CALLBACK_ANIMATION,
                    mAnimationCallback, null);
        }
    }

    void stopTracking() {
        if (!mTracking)
            return;
        mTracking = false;
        setPileLayers(View.LAYER_TYPE_NONE);
        mVelocityTracker.recycle();
        mVelocityTracker = null;
        mCloseView.setPressed(false);
    }

=======
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
    /**
     * Enables or disables layers on the children of the notifications pile.
     * 
     * When layers are enabled, this method attempts to enable layers for the minimal
     * number of children. Only children visible when the notification area is fully
     * expanded will receive a layer. The technique used in this method might cause
     * more children than necessary to get a layer (at most one extra child with the
     * current UI.)
     * 
     * @param layerType {@link View#LAYER_TYPE_NONE} or {@link View#LAYER_TYPE_HARDWARE}
     */
    private void setPileLayers(int layerType) {
        final int count = mPile.getChildCount();

        switch (layerType) {
            case View.LAYER_TYPE_NONE:
                for (int i = 0; i < count; i++) {
                    mPile.getChildAt(i).setLayerType(layerType, null);
                }
                break;
            case View.LAYER_TYPE_HARDWARE:
                final int[] location = new int[2]; 
                mNotificationPanel.getLocationInWindow(location);

                final int left = location[0];
                final int top = location[1];
                final int right = left + mNotificationPanel.getWidth();
                final int bottom = top + getExpandedViewMaxHeight();

                final Rect childBounds = new Rect();

                for (int i = 0; i < count; i++) {
                    final View view = mPile.getChildAt(i);
                    view.getLocationInWindow(location);

                    childBounds.set(location[0], location[1],
                            location[0] + view.getWidth(), location[1] + view.getHeight());

                    if (childBounds.intersects(left, top, right, bottom)) {
                        view.setLayerType(layerType, null);
                    }
                }

                break;
        }
    }

<<<<<<< HEAD
    void incrementAnim(long frameTimeNanos) {
        final long deltaNanos = Math.max(frameTimeNanos - mAnimLastTimeNanos, 0);
        final float t = deltaNanos * 0.000000001f;                  // ns -> s
        final float y = mAnimY;
        final float v = mAnimVel;                                   // px/s
        final float a = mAnimAccel;                                 // px/s/s
        mAnimY = y + (v*t) + (0.5f*a*t*t);                          // px
        mAnimVel = v + (a*t);                                       // px/s
        mAnimLastTimeNanos = frameTimeNanos;                        // ns
        //Slog.d(TAG, "y=" + y + " v=" + v + " a=" + a + " t=" + t + " mAnimY=" + mAnimY
        //        + " mAnimAccel=" + mAnimAccel);
    }

    void doRevealAnimation(long frameTimeNanos) {
        if (SPEW) {
            Slog.d(TAG, "doRevealAnimation: dt=" + (frameTimeNanos - mAnimLastTimeNanos));
        }
        final int h = mNotificationPanelMinHeight;
        if (mAnimatingReveal && mAnimating && mAnimY < h) {
            incrementAnim(frameTimeNanos);
            if (mAnimY >= h) {
                mAnimY = h;
                updateExpandedViewPos((int)mAnimY);
            } else {
                updateExpandedViewPos((int)mAnimY);
                mChoreographer.postCallback(Choreographer.CALLBACK_ANIMATION,
                        mRevealAnimationCallback, null);
            }
        }
    }

    void prepareTracking(int y, boolean opening) {
        if (CHATTY) {
            Slog.d(TAG, "panel: beginning to track the user's touch, y=" + y + " opening=" + opening);
        }

        mCloseView.setPressed(true);

        mTracking = true;
        setPileLayers(View.LAYER_TYPE_HARDWARE);
        mVelocityTracker = VelocityTracker.obtain();
        if (opening) {
            makeExpandedVisible(true);
        } else {
            // it's open, close it?
            if (mAnimating) {
                mAnimating = false;
                mChoreographer.removeCallbacks(Choreographer.CALLBACK_ANIMATION,
                        mAnimationCallback, null);
            }
            updateExpandedViewPos(y + mViewDelta);
        }
    }

    void performFling(int y, float vel, boolean always) {
        if (CHATTY) {
            Slog.d(TAG, "panel: will fling, y=" + y + " vel=" + vel + " mExpanded=" + mExpanded);
        }

        mAnimatingReveal = false;

        mAnimY = y;
        mAnimVel = vel;

        //Slog.d(TAG, "starting with mAnimY=" + mAnimY + " mAnimVel=" + mAnimVel);

        if (mExpanded) {
            if (!always && (
                    vel > mFlingCollapseMinVelocityPx
                    || (y > (getExpandedViewMaxHeight()*(1f-mCollapseMinDisplayFraction)) &&
                        vel > -mFlingExpandMinVelocityPx))) {
                // We are expanded, but they didn't move sufficiently to cause
                // us to retract.  Animate back to the expanded position.
                mAnimAccel = mExpandAccelPx;
                if (vel < 0) {
                    mAnimVel = 0;
                }
            }
            else {
                // We are expanded and are now going to animate away.
                mAnimAccel = -mCollapseAccelPx;
                if (vel > 0) {
                    mAnimVel = 0;
                }
            }
        } else {
            if (always || (
                    vel > mFlingExpandMinVelocityPx
                    || (y > (getExpandedViewMaxHeight()*(1f-mExpandMinDisplayFraction)) &&
                        vel > -mFlingCollapseMinVelocityPx))) {
                // We are collapsed, and they moved enough to allow us to
                // expand.  Animate in the notifications.
                mAnimAccel = mExpandAccelPx;
                if (vel < 0) {
                    mAnimVel = 0;
                }
            }
            else {
                // We are collapsed, but they didn't move sufficiently to cause
                // us to retract.  Animate back to the collapsed position.
                mAnimAccel = -mCollapseAccelPx;
                if (vel > 0) {
                    mAnimVel = 0;
                }
            }
        }
        //Slog.d(TAG, "mAnimY=" + mAnimY + " mAnimVel=" + mAnimVel
        //        + " mAnimAccel=" + mAnimAccel);

        resetLastAnimTime();
        mAnimating = true;
        mClosing = mAnimAccel < 0;

        mChoreographer.removeCallbacks(Choreographer.CALLBACK_ANIMATION,
                mAnimationCallback, null);
        mChoreographer.removeCallbacks(Choreographer.CALLBACK_ANIMATION,
                mRevealAnimationCallback, null);
        mChoreographer.postCallback(Choreographer.CALLBACK_ANIMATION,
                mAnimationCallback, null);
        stopTracking();
    }

    boolean interceptTouchEvent(MotionEvent event) {
=======
    public boolean isClinging() {
        return mCling != null && mCling.getVisibility() == View.VISIBLE;
    }

    public void hideCling() {
        if (isClinging()) {
            mCling.animate().alpha(0f).setDuration(250).start();
            mCling.setVisibility(View.GONE);
            mSuppressStatusBarDrags = false;
        }
    }

    public void showCling() {
        // lazily inflate this to accommodate orientation change
        final ViewStub stub = (ViewStub) mStatusBarWindow.findViewById(R.id.status_bar_cling_stub);
        if (stub == null) {
            mClingShown = true;
            return; // no clings on this device
        }

        mSuppressStatusBarDrags = true;

        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mCling = (ViewGroup) stub.inflate();

                mCling.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        return true; // e eats everything
                    }});
                mCling.findViewById(R.id.ok).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        hideCling();
                    }});

                mCling.setAlpha(0f);
                mCling.setVisibility(View.VISIBLE);
                mCling.animate().alpha(1f);

                mClingShown = true;
                SharedPreferences.Editor editor = Prefs.edit(mContext);
                editor.putBoolean(Prefs.SHOWN_QUICK_SETTINGS_HELP, true);
                editor.apply();

                makeExpandedVisible(true); // enforce visibility in case the shade is still animating closed
                animateExpandNotificationsPanel();

                mSuppressStatusBarDrags = false;
            }
        }, 500);

        animateExpandNotificationsPanel();
    }

    public boolean interceptTouchEvent(MotionEvent event) {
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
        if (SPEW) {
            Slog.d(TAG, "Touch: rawY=" + event.getRawY() + " event=" + event + " mDisabled="
                + mDisabled + " mTracking=" + mTracking);
        } else if (CHATTY) {
            if (event.getAction() != MotionEvent.ACTION_MOVE) {
                Slog.d(TAG, String.format(
                            "panel: %s at (%f, %f) mDisabled=0x%08x",
                            MotionEvent.actionToString(event.getAction()),
                            event.getRawX(), event.getRawY(), mDisabled));
            }
        }

<<<<<<< HEAD
        if ((mDisabled & StatusBarManager.DISABLE_EXPAND) != 0) {
            return false;
        }

        final int action = event.getAction();
        final int statusBarSize = getStatusBarHeight();
        final int hitSize = statusBarSize*2;
        final int y = (int)event.getRawY();
        if (action == MotionEvent.ACTION_DOWN) {
            if (!areLightsOn()) {
                setLightsOn(true);
            }

            if (!mExpanded) {
                mViewDelta = statusBarSize - y;
            } else {
                mCloseView.getLocationOnScreen(mAbsPos);
                mViewDelta = mAbsPos[1]
                           + getCloseViewHeight() // XXX: not closeViewHeight, but paddingBottom from the 9patch
                           + mNotificationPanelBackgroundPadding.top
                           + mNotificationPanelBackgroundPadding.bottom
                           - y;
            }
            if ((!mExpanded && y < hitSize) ||
                    // @@ add taps outside the panel if it's not full-screen
                    (mExpanded && y > (getExpandedViewMaxHeight()-hitSize))) {
                // We drop events at the edge of the screen to make the windowshade come
                // down by accident less, especially when pushing open a device with a keyboard
                // that rotates (like g1 and droid)
                int x = (int)event.getRawX();
                final int edgeBorder = mEdgeBorder;
                if (x >= edgeBorder && x < mDisplayMetrics.widthPixels - edgeBorder) {
                    prepareTracking(y, !mExpanded);// opening if we're not already fully visible
                    trackMovement(event);

                    if (mIsStatusBarBrightNess) {
                        mIsBrightNessMode = 0;
                        if (!mIsAutoBrightNess) {
                            mHandler.sendMessageDelayed(mHandler.obtainMessage(MSG_STATUSBAR_BRIGHTNESS),
                                    ViewConfiguration.getGlobalActionKeyTimeout());
                            mBrightnessPercent.setVisibility(View.VISIBLE);
                        }
                    }
                }
            }
        } else if (mTracking) {
            trackMovement(event);
            if (action == MotionEvent.ACTION_MOVE) {
                if (mAnimatingReveal && (y + mViewDelta) < mNotificationPanelMinHeight) {
                    // change brightness
                    if (mIsStatusBarBrightNess && mIsBrightNessMode == 1) {
                        doBrightNess(event);
                    }
                } else  {
                    // remove brightness events from being posted, change mode
                    if (mIsStatusBarBrightNess) {
                        stopBrightnessMessages();

                        if (mIsBrightNessMode == 1) {
                            mIsBrightNessMode = 2;
                        }
                    }
                    mAnimatingReveal = false;
                    updateExpandedViewPos(y + mViewDelta);
                }
            } else if (action == MotionEvent.ACTION_UP
                    || action == MotionEvent.ACTION_CANCEL) {
                mVelocityTracker.computeCurrentVelocity(1000);

                float yVel = mVelocityTracker.getYVelocity();
                boolean negative = yVel < 0;

                float xVel = mVelocityTracker.getXVelocity();
                if (xVel < 0) {
                    xVel = -xVel;
                }
                if (xVel > mFlingGestureMaxXVelocityPx) {
                    xVel = mFlingGestureMaxXVelocityPx; // limit how much we care about the x axis
                }

                float vel = (float)Math.hypot(yVel, xVel);
                if (vel > mFlingGestureMaxOutputVelocityPx) {
                    vel = mFlingGestureMaxOutputVelocityPx;
                }
                if (negative) {
                    vel = -vel;
                }

                if (CHATTY) {
                    Slog.d(TAG, String.format("gesture: vraw=(%f,%f) vnorm=(%f,%f) vlinear=%f",
                        mVelocityTracker.getXVelocity(),
                        mVelocityTracker.getYVelocity(),
                        xVel, yVel,
                        vel));
                }

                if (mTrackingPosition == mNotificationPanelMinHeight) {
                    // start the fling from the tracking position, ignore y and view delta
                    mFlingY = mTrackingPosition;
                    mViewDelta = 0;
                } else {
                    mFlingY = y;
                }
                mFlingVelocity = vel;
                mHandler.post(mPerformFling);

                stopBrightnessMessages();
            }

        }
        return false;
    }

    private void trackMovement(MotionEvent event) {
        // Add movement to velocity tracker using raw screen X and Y coordinates instead
        // of window coordinates because the window frame may be moving at the same time.
        float deltaX = event.getRawX() - event.getX();
        float deltaY = event.getRawY() - event.getY();
        event.offsetLocation(deltaX, deltaY);
        mVelocityTracker.addMovement(event);
        event.offsetLocation(-deltaX, -deltaY);
=======
        if (DEBUG_GESTURES) {
            mGestureRec.add(event);
        }

        // Cling (first-run help) handling.
        // The cling is supposed to show the first time you drag, or even tap, the status bar.
        // It should show the notification panel, then fade in after half a second, giving you 
        // an explanation of what just happened, as well as teach you how to access quick
        // settings (another drag). The user can dismiss the cling by clicking OK or by 
        // dragging quick settings into view.
        final int act = event.getActionMasked();
        if (mSuppressStatusBarDrags) {
            return true;
        } else if (act == MotionEvent.ACTION_UP && !mClingShown) {
            showCling();
        } else {
            hideCling();
        }

        return false;
    }

    public GestureRecorder getGestureRecorder() {
        return mGestureRec;
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
    }

    @Override // CommandQueue
    public void setNavigationIconHints(int hints) {
        if (hints == mNavigationIconHints) return;

        mNavigationIconHints = hints;

        if (mNavigationBarView != null) {
            mNavigationBarView.setNavigationIconHints(hints);
        }
    }

    @Override // CommandQueue
    public void setSystemUiVisibility(int vis, int mask) {
        final int oldVal = mSystemUiVisibility;
        final int newVal = (oldVal&~mask) | (vis&mask);
        final int diff = newVal ^ oldVal;

        if (diff != 0) {
            mSystemUiVisibility = newVal;

            if (0 != (diff & View.SYSTEM_UI_FLAG_LOW_PROFILE)) {
                final boolean lightsOut = (0 != (vis & View.SYSTEM_UI_FLAG_LOW_PROFILE));
                if (lightsOut) {
<<<<<<< HEAD
                    animateCollapse();
                    if (mTicking) {
                        mTicker.halt();
=======
                    animateCollapsePanels();
                    if (mTicking) {
                        haltTicker();
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
                    }
                }

                if (mNavigationBarView != null) {
                    mNavigationBarView.setLowProfile(lightsOut);
                }

                setStatusBarLowProfile(lightsOut);
            }

            notifyUiVisibilityChanged();
        }
    }

    private void setStatusBarLowProfile(boolean lightsOut) {
        if (mLightsOutAnimation == null) {
            final View notifications = mStatusBarView.findViewById(R.id.notification_icon_area);
            final View systemIcons = mStatusBarView.findViewById(R.id.statusIcons);
            final View signal = mStatusBarView.findViewById(R.id.signal_cluster);
            final View battery = mStatusBarView.findViewById(R.id.battery);
<<<<<<< HEAD

            mLightsOutAnimation = new AnimatorSet();
            mLightsOutAnimation.playTogether(
=======
            final View clock = mStatusBarView.findViewById(R.id.clock);

            final AnimatorSet lightsOutAnim = new AnimatorSet();
            lightsOutAnim.playTogether(
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
                    ObjectAnimator.ofFloat(notifications, View.ALPHA, 0),
                    ObjectAnimator.ofFloat(systemIcons, View.ALPHA, 0),
                    ObjectAnimator.ofFloat(signal, View.ALPHA, 0),
                    ObjectAnimator.ofFloat(battery, View.ALPHA, 0.5f),
                    ObjectAnimator.ofFloat(clock, View.ALPHA, 0.5f)
                );
<<<<<<< HEAD
            mLightsOutAnimation.setDuration(750);

            mLightsOnAnimation = new AnimatorSet();
            mLightsOnAnimation.playTogether(
=======
            lightsOutAnim.setDuration(750);

            final AnimatorSet lightsOnAnim = new AnimatorSet();
            lightsOnAnim.playTogether(
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
                    ObjectAnimator.ofFloat(notifications, View.ALPHA, 1),
                    ObjectAnimator.ofFloat(systemIcons, View.ALPHA, 1),
                    ObjectAnimator.ofFloat(signal, View.ALPHA, 1),
                    ObjectAnimator.ofFloat(battery, View.ALPHA, 1),
                    ObjectAnimator.ofFloat(clock, View.ALPHA, 1)
                );
<<<<<<< HEAD
            mLightsOnAnimation.setDuration(250);
=======
            lightsOnAnim.setDuration(250);

            mLightsOutAnimation = lightsOutAnim;
            mLightsOnAnimation = lightsOnAnim;
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
        }

        mLightsOutAnimation.cancel();
        mLightsOnAnimation.cancel();

        final Animator a = lightsOut ? mLightsOutAnimation : mLightsOnAnimation;
        a.start();

        setAreThereNotifications();
    }

    private boolean areLightsOn() {
        return 0 == (mSystemUiVisibility & View.SYSTEM_UI_FLAG_LOW_PROFILE);
    }

    public void setLightsOn(boolean on) {
        Log.v(TAG, "setLightsOn(" + on + ")");
        if (on) {
            setSystemUiVisibility(0, View.SYSTEM_UI_FLAG_LOW_PROFILE);
        } else {
            setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE, View.SYSTEM_UI_FLAG_LOW_PROFILE);
        }
    }

    private void notifyUiVisibilityChanged() {
        try {
<<<<<<< HEAD
            mWindowManager.statusBarVisibilityChanged(mSystemUiVisibility);
=======
            mWindowManagerService.statusBarVisibilityChanged(mSystemUiVisibility);
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
        } catch (RemoteException ex) {
        }
    }

    public void topAppWindowChanged(boolean showMenu) {
        if (DEBUG) {
            Slog.d(TAG, (showMenu?"showing":"hiding") + " the MENU button");
        }
        if (mNavigationBarView != null) {
            mNavigationBarView.setMenuVisibility(showMenu);
        }

        // See above re: lights-out policy for legacy apps.
        if (showMenu) setLightsOn(true);
    }

    @Override
    public void setImeWindowStatus(IBinder token, int vis, int backDisposition) {
        boolean altBack = (backDisposition == InputMethodService.BACK_DISPOSITION_WILL_DISMISS)
            || ((vis & InputMethodService.IME_VISIBLE) != 0);

        mCommandQueue.setNavigationIconHints(
                altBack ? (mNavigationIconHints | StatusBarManager.NAVIGATION_HINT_BACK_ALT)
                        : (mNavigationIconHints & ~StatusBarManager.NAVIGATION_HINT_BACK_ALT));
<<<<<<< HEAD
    }

    @Override
    public void setHardKeyboardStatus(boolean available, boolean enabled) { }

    private class NotificationClicker implements View.OnClickListener {
        private PendingIntent mIntent;
        private String mPkg;
        private String mTag;
        private int mId;

        NotificationClicker(PendingIntent intent, String pkg, String tag, int id) {
            mIntent = intent;
            mPkg = pkg;
            mTag = tag;
            mId = id;
        }

        public void onClick(View v) {
            try {
                // The intent we are sending is for the application, which
                // won't have permission to immediately start an activity after
                // the user switches to home.  We know it is safe to do at this
                // point, so make sure new activity switches are now allowed.
                ActivityManagerNative.getDefault().resumeAppSwitches();
                // Also, notifications can be launched from the lock screen,
                // so dismiss the lock screen when the activity starts.
                ActivityManagerNative.getDefault().dismissKeyguardOnNextActivity();
            } catch (RemoteException e) {
            }

            if (mIntent != null) {
                int[] pos = new int[2];
                v.getLocationOnScreen(pos);
                Intent overlay = new Intent();
                overlay.setSourceBounds(
                        new Rect(pos[0], pos[1], pos[0]+v.getWidth(), pos[1]+v.getHeight()));
                try {
                    mIntent.send(mContext, 0, overlay);
                } catch (PendingIntent.CanceledException e) {
                    // the stack trace isn't very helpful here.  Just log the exception message.
                    Slog.w(TAG, "Sending contentIntent failed: " + e);
                }

                KeyguardManager kgm =
                    (KeyguardManager) mContext.getSystemService(Context.KEYGUARD_SERVICE);
                if (kgm != null) kgm.exitKeyguardSecurely(null);
            }

            try {
                mBarService.onNotificationClick(mPkg, mTag, mId);
            } catch (RemoteException ex) {
                // system process is dead if we're here.
            }

            // close the shade if it was open
            animateCollapse();

            // If this click was on the intruder alert, hide that instead
            mHandler.sendEmptyMessage(MSG_HIDE_INTRUDER);
        }
    }
=======
        if (mQS != null) mQS.setImeWindowStatus(vis > 0);
    }

    @Override
    public void setHardKeyboardStatus(boolean available, boolean enabled) {}
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a

    @Override
    protected void tick(IBinder key, StatusBarNotification n, boolean firstTime) {
        // no ticking in lights-out mode
        if (!areLightsOn()) return;

        // no ticking in Setup
        if (!isDeviceProvisioned()) return;

<<<<<<< HEAD
=======
        // not for you
        if (!notificationIsForCurrentUser(n)) return;

>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
        // Show the ticker if one is requested. Also don't do this
        // until status bar window is attached to the window manager,
        // because...  well, what's the point otherwise?  And trying to
        // run a ticker without being attached will crash!
<<<<<<< HEAD
        if (n.notification.tickerText != null && mStatusBarContainer.getWindowToken() != null) {
=======
        if (n.notification.tickerText != null && mStatusBarWindow.getWindowToken() != null) {
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
            if (0 == (mDisabled & (StatusBarManager.DISABLE_NOTIFICATION_ICONS
                            | StatusBarManager.DISABLE_NOTIFICATION_TICKER))) {
                mTicker.addEntry(n);
            }
        }
    }

    private class MyTicker extends Ticker {
        MyTicker(Context context, View sb) {
            super(context, sb);
        }

        @Override
        public void tickerStarting() {
            mTicking = true;
<<<<<<< HEAD
            mIcons.setVisibility(View.GONE);
            mCenterClockLayout.setVisibility(View.GONE);
            mTickerView.setVisibility(View.VISIBLE);
            mTickerView.startAnimation(loadAnim(com.android.internal.R.anim.push_up_in, null));
            mIcons.startAnimation(loadAnim(com.android.internal.R.anim.push_up_out, null));
            mCenterClockLayout.startAnimation(loadAnim(com.android.internal.R.anim.push_up_out,
                    null));
=======
            mStatusBarContents.setVisibility(View.GONE);
            mTickerView.setVisibility(View.VISIBLE);
            mTickerView.startAnimation(loadAnim(com.android.internal.R.anim.push_up_in, null));
            mStatusBarContents.startAnimation(loadAnim(com.android.internal.R.anim.push_up_out, null));
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
        }

        @Override
        public void tickerDone() {
<<<<<<< HEAD
            mIcons.setVisibility(View.VISIBLE);
            mCenterClockLayout.setVisibility(View.VISIBLE);
            mTickerView.setVisibility(View.GONE);
            mIcons.startAnimation(loadAnim(com.android.internal.R.anim.push_down_in, null));
            mTickerView.startAnimation(loadAnim(com.android.internal.R.anim.push_down_out,
                        mTickingDoneListener));
            mCenterClockLayout.startAnimation(loadAnim(com.android.internal.R.anim.push_down_in,
                    null));
        }

        public void tickerHalting() {
            mIcons.setVisibility(View.VISIBLE);
            mCenterClockLayout.setVisibility(View.VISIBLE);
            mTickerView.setVisibility(View.GONE);
            mIcons.startAnimation(loadAnim(com.android.internal.R.anim.fade_in, null));
            mTickerView.startAnimation(loadAnim(com.android.internal.R.anim.fade_out,
                        mTickingDoneListener));
            mCenterClockLayout.startAnimation(loadAnim(com.android.internal.R.anim.fade_in, null));
=======
            mStatusBarContents.setVisibility(View.VISIBLE);
            mTickerView.setVisibility(View.GONE);
            mStatusBarContents.startAnimation(loadAnim(com.android.internal.R.anim.push_down_in, null));
            mTickerView.startAnimation(loadAnim(com.android.internal.R.anim.push_down_out,
                        mTickingDoneListener));
        }

        public void tickerHalting() {
            mStatusBarContents.setVisibility(View.VISIBLE);
            mTickerView.setVisibility(View.GONE);
            mStatusBarContents.startAnimation(loadAnim(com.android.internal.R.anim.fade_in, null));
            // we do not animate the ticker away at this point, just get rid of it (b/6992707)
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
        }
    }

    Animation.AnimationListener mTickingDoneListener = new Animation.AnimationListener() {;
        public void onAnimationEnd(Animation animation) {
            mTicking = false;
        }
        public void onAnimationRepeat(Animation animation) {
        }
        public void onAnimationStart(Animation animation) {
        }
    };

    private Animation loadAnim(int id, Animation.AnimationListener listener) {
        Animation anim = AnimationUtils.loadAnimation(mContext, id);
        if (listener != null) {
            anim.setAnimationListener(listener);
        }
        return anim;
    }

    public static String viewInfo(View v) {
        return "[(" + v.getLeft() + "," + v.getTop() + ")(" + v.getRight() + "," + v.getBottom()
                + ") " + v.getWidth() + "x" + v.getHeight() + "]";
    }

<<<<<<< HEAD
=======
    public void dump(FileDescriptor fd, PrintWriter pw, String[] args) {
        synchronized (mQueueLock) {
            pw.println("Current Status Bar state:");
            pw.println("  mExpandedVisible=" + mExpandedVisible
                    + ", mTrackingPosition=" + mTrackingPosition);
            pw.println("  mTicking=" + mTicking);
            pw.println("  mTracking=" + mTracking);
            pw.println("  mNotificationPanel=" + 
                    ((mNotificationPanel == null) 
                            ? "null" 
                            : (mNotificationPanel + " params=" + mNotificationPanel.getLayoutParams().debug(""))));
            pw.println("  mAnimating=" + mAnimating
                    + ", mAnimY=" + mAnimY + ", mAnimVel=" + mAnimVel
                    + ", mAnimAccel=" + mAnimAccel);
            pw.println("  mAnimLastTimeNanos=" + mAnimLastTimeNanos);
            pw.println("  mAnimatingReveal=" + mAnimatingReveal
                    + " mViewDelta=" + mViewDelta);
            pw.println("  mDisplayMetrics=" + mDisplayMetrics);
            pw.println("  mPile: " + viewInfo(mPile));
            pw.println("  mTickerView: " + viewInfo(mTickerView));
            pw.println("  mScrollView: " + viewInfo(mScrollView)
                    + " scroll " + mScrollView.getScrollX() + "," + mScrollView.getScrollY());
        }

        pw.print("  mNavigationBarView=");
        if (mNavigationBarView == null) {
            pw.println("null");
        } else {
            mNavigationBarView.dump(fd, pw, args);
        }

        if (DUMPTRUCK) {
            synchronized (mNotificationData) {
                int N = mNotificationData.size();
                pw.println("  notification icons: " + N);
                for (int i=0; i<N; i++) {
                    NotificationData.Entry e = mNotificationData.get(i);
                    pw.println("    [" + i + "] key=" + e.key + " icon=" + e.icon);
                    StatusBarNotification n = e.notification;
                    pw.println("         pkg=" + n.pkg + " id=" + n.id + " score=" + n.score);
                    pw.println("         notification=" + n.notification);
                    pw.println("         tickerText=\"" + n.notification.tickerText + "\"");
                }
            }

            int N = mStatusIcons.getChildCount();
            pw.println("  system icons: " + N);
            for (int i=0; i<N; i++) {
                StatusBarIconView ic = (StatusBarIconView) mStatusIcons.getChildAt(i);
                pw.println("    [" + i + "] icon=" + ic);
            }

            if (false) {
                pw.println("see the logcat for a dump of the views we have created.");
                // must happen on ui thread
                mHandler.post(new Runnable() {
                        public void run() {
                            mStatusBarView.getLocationOnScreen(mAbsPos);
                            Slog.d(TAG, "mStatusBarView: ----- (" + mAbsPos[0] + "," + mAbsPos[1]
                                    + ") " + mStatusBarView.getWidth() + "x"
                                    + getStatusBarHeight());
                            mStatusBarView.debug();
                        }
                    });
            }
        }

        if (DEBUG_GESTURES) {
            pw.print("  status bar gestures: ");
            mGestureRec.dump(fd, pw, args);
        }

        mNetworkController.dump(fd, pw, args);
    }

>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
    @Override
    public void createAndAddWindows() {
        addStatusBarWindow();
    }

    private void addStatusBarWindow() {
        // Put up the view
        final int height = getStatusBarHeight();

        // Now that the status bar window encompasses the sliding panel and its
        // translucent backdrop, the entire thing is made TRANSLUCENT and is
        // hardware-accelerated.
        final WindowManager.LayoutParams lp = new WindowManager.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                height,
                WindowManager.LayoutParams.TYPE_STATUS_BAR,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                    | WindowManager.LayoutParams.FLAG_TOUCHABLE_WHEN_WAKING
                    | WindowManager.LayoutParams.FLAG_SPLIT_TOUCH,
                PixelFormat.TRANSLUCENT);

        lp.flags |= WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED;

        lp.gravity = getStatusBarGravity();
        lp.setTitle("StatusBar");
        lp.packageName = mContext.getPackageName();

        makeStatusBarView();
<<<<<<< HEAD

        mStatusBarContainer.addView(mStatusBarWindow);
        WindowManagerImpl.getDefault().addView(mStatusBarContainer, lp);
=======
        mWindowManager.addView(mStatusBarWindow, lp);
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
    }

    void setNotificationIconVisibility(boolean visible, int anim) {
        int old = mNotificationIcons.getVisibility();
        int v = visible ? View.VISIBLE : View.INVISIBLE;
        if (old != v) {
            mNotificationIcons.setVisibility(v);
            mNotificationIcons.startAnimation(loadAnim(anim, null));
        }
    }

    void updateExpandedInvisiblePosition() {
        mTrackingPosition = -mDisplayMetrics.heightPixels;
    }

    static final float saturate(float a) {
        return a < 0f ? 0f : (a > 1f ? 1f : a);
    }

    @Override
    protected int getExpandedViewMaxHeight() {
        return mDisplayMetrics.heightPixels - mNotificationPanelMarginBottomPx;
    }

    @Override
<<<<<<< HEAD
    protected void updateExpandedViewPos(int expandedPosition) {
        if (SPEW) {
            Slog.d(TAG, "updateExpandedViewPos: expandedPosition=" + expandedPosition
                    //+ " mTrackingParams.y=" + ((mTrackingParams == null) ? "?" : mTrackingParams.y)
                    + " mTracking=" + mTracking
                    + " mTrackingPosition=" + mTrackingPosition
                    + " mExpandedVisible=" + mExpandedVisible
                    + " mAnimating=" + mAnimating
                    + " mAnimatingReveal=" + mAnimatingReveal
                    + " mClosing=" + mClosing
                    + " gravity=" + mNotificationPanelGravity);
        }
        int panelh = 0;
        final int disph = getExpandedViewMaxHeight();

        // If the expanded view is not visible, make sure they're still off screen.
        // Maybe the view was resized.
        if (!mExpandedVisible) {
            if (SPEW) Slog.d(TAG, "updateExpandedViewPos: view not visible, bailing");
            updateExpandedInvisiblePosition();
            return;
        }

        // tracking view...
        int pos;
        if (expandedPosition == EXPANDED_FULL_OPEN) {
            panelh = disph;
        }
        else if (expandedPosition == EXPANDED_LEAVE_ALONE) {
            panelh = mTrackingPosition;
        }
        else {
            if (expandedPosition <= disph) {
                panelh = expandedPosition;
            } else {
                panelh = disph;
            }
        }

        // catch orientation changes and other peculiar cases
        if (panelh > 0 &&
                ((panelh > disph) ||
                 (panelh < disph && !mTracking && !mAnimating))) {
            if (SPEW) Slog.d(TAG, "updateExpandedViewPos: orientation change?");
            panelh = disph;
        } else if (panelh < 0) {
            panelh = 0;
        }

        if (SPEW) Slog.d(TAG, "updateExpandedViewPos: adjusting size to panelh=" + panelh);

        if (panelh == mTrackingPosition) {
            if (SPEW) Slog.d(TAG, "updateExpandedViewPos: panelh == mTrackingPosition, bailing");
            return;
        }

        mTrackingPosition = panelh;

        FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) mNotificationPanel.getLayoutParams();
        lp.height = panelh;
        lp.gravity = mNotificationPanelGravity;
        lp.leftMargin = mNotificationPanelMarginLeftPx;
        if (SPEW) {
            Slog.v(TAG, "updated cropView height=" + panelh + " grav=" + lp.gravity);
        }
        mNotificationPanel.setLayoutParams(lp);

        final int barh = getCloseViewHeight() + getStatusBarHeight();
        final float frac = saturate((float)(panelh - barh) / (disph - barh));

        if (DIM_BEHIND_EXPANDED_PANEL && ActivityManager.isHighEndGfx(mDisplay)) {
            // woo, special effects
            final float k = (float)(1f-0.5f*(1f-Math.cos(3.14159f * Math.pow(1f-frac, 2.2f))));
            final int color = ((int)(0xB0 * k)) << 24;
            mStatusBarWindow.setBackgroundColor(color);
        }
        
        updateCarrierLabelVisibility(false);
    }

    void updateDisplaySize() {
        mDisplay.getMetrics(mDisplayMetrics);
    }

    void performDisableActions(int net) {
        int old = mDisabled;
        int diff = net ^ old;
        mDisabled = net;

        // act accordingly
        if ((diff & StatusBarManager.DISABLE_EXPAND) != 0) {
            if ((net & StatusBarManager.DISABLE_EXPAND) != 0) {
                Slog.d(TAG, "DISABLE_EXPAND: yes");
                animateCollapse();
            }
        }
        if ((diff & StatusBarManager.DISABLE_NOTIFICATION_ICONS) != 0) {
            if ((net & StatusBarManager.DISABLE_NOTIFICATION_ICONS) != 0) {
                Slog.d(TAG, "DISABLE_NOTIFICATION_ICONS: yes");
                if (mTicking) {
                    mNotificationIcons.setVisibility(View.INVISIBLE);
                    mTicker.halt();
                } else {
                    setNotificationIconVisibility(false, com.android.internal.R.anim.fade_out);
                }
            } else {
                Slog.d(TAG, "DISABLE_NOTIFICATION_ICONS: no");
                if (!mExpandedVisible) {
                    setNotificationIconVisibility(true, com.android.internal.R.anim.fade_in);
                }
            }
        } else if ((diff & StatusBarManager.DISABLE_NOTIFICATION_TICKER) != 0) {
            if (mTicking && (net & StatusBarManager.DISABLE_NOTIFICATION_TICKER) != 0) {
                mTicker.halt();
            }
=======
    public void updateExpandedViewPos(int thingy) {
        if (DEBUG) Slog.v(TAG, "updateExpandedViewPos");

        // on larger devices, the notification panel is propped open a bit
        mNotificationPanel.setMinimumHeight(
                (int)(mNotificationPanelMinHeightFrac * mCurrentDisplaySize.y));

        FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) mNotificationPanel.getLayoutParams();
        lp.gravity = mNotificationPanelGravity;
        lp.leftMargin = mNotificationPanelMarginPx;
        mNotificationPanel.setLayoutParams(lp);

        if (mSettingsPanel != null) {
            lp = (FrameLayout.LayoutParams) mSettingsPanel.getLayoutParams();
            lp.gravity = mSettingsPanelGravity;
            lp.rightMargin = mNotificationPanelMarginPx;
            mSettingsPanel.setLayoutParams(lp);
        }

        updateCarrierLabelVisibility(false);
    }

    // called by makeStatusbar and also by PhoneStatusBarView
    void updateDisplaySize() {
        mDisplay.getMetrics(mDisplayMetrics);
        if (DEBUG_GESTURES) {
            mGestureRec.tag("display", 
                    String.format("%dx%d", mDisplayMetrics.widthPixels, mDisplayMetrics.heightPixels));
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
        }
    }

    private View.OnClickListener mClearButtonListener = new View.OnClickListener() {
<<<<<<< HEAD
        final int mini(int a, int b) {
            return (b>a?a:b);
        }
=======
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
        public void onClick(View v) {
            synchronized (mNotificationData) {
                // animate-swipe all dismissable notifications, then animate the shade closed
                int numChildren = mPile.getChildCount();

                int scrollTop = mScrollView.getScrollY();
                int scrollBottom = scrollTop + mScrollView.getHeight();
                final ArrayList<View> snapshot = new ArrayList<View>(numChildren);
                for (int i=0; i<numChildren; i++) {
                    final View child = mPile.getChildAt(i);
                    if (mPile.canChildBeDismissed(child) && child.getBottom() > scrollTop &&
                            child.getTop() < scrollBottom) {
                        snapshot.add(child);
                    }
                }
                if (snapshot.isEmpty()) {
<<<<<<< HEAD
                    animateCollapse(CommandQueue.FLAG_EXCLUDE_NONE);
=======
                    animateCollapsePanels(CommandQueue.FLAG_EXCLUDE_NONE);
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
                    return;
                }
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        // Decrease the delay for every row we animate to give the sense of
                        // accelerating the swipes
                        final int ROW_DELAY_DECREMENT = 10;
                        int currentDelay = 140;
                        int totalDelay = 0;

                        // Set the shade-animating state to avoid doing other work during
                        // all of these animations. In particular, avoid layout and
                        // redrawing when collapsing the shade.
                        mPile.setViewRemoval(false);

                        mPostCollapseCleanup = new Runnable() {
                            @Override
                            public void run() {
<<<<<<< HEAD
=======
                                if (DEBUG) {
                                    Slog.v(TAG, "running post-collapse cleanup");
                                }
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
                                try {
                                    mPile.setViewRemoval(true);
                                    mBarService.onClearAllNotifications();
                                } catch (Exception ex) { }
                            }
                        };

                        View sampleView = snapshot.get(0);
                        int width = sampleView.getWidth();
                        final int velocity = width * 8; // 1000/8 = 125 ms duration
                        for (final View _v : snapshot) {
                            mHandler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    mPile.dismissRowAnimated(_v, velocity);
                                }
                            }, totalDelay);
                            currentDelay = Math.max(50, currentDelay - ROW_DELAY_DECREMENT);
                            totalDelay += currentDelay;
                        }
                        // Delay the collapse animation until after all swipe animations have
                        // finished. Provide some buffer because there may be some extra delay
                        // before actually starting each swipe animation. Ideally, we'd
                        // synchronize the end of those animations with the start of the collaps
                        // exactly.
                        mHandler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
<<<<<<< HEAD
                                animateCollapse(CommandQueue.FLAG_EXCLUDE_NONE);
=======
                                animateCollapsePanels(CommandQueue.FLAG_EXCLUDE_NONE);
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
                            }
                        }, totalDelay + 225);
                    }
                }).start();
            }
        }
    };

<<<<<<< HEAD
    private View.OnClickListener mWeatherPanelListener = new View.OnClickListener() {
        public void onClick(View v) {
            if (mShortClickWeather.equals(ACTION_NOTHING)) {
            // do nothing
            } else {
                try {
                    ActivityManagerNative.getDefault().dismissKeyguardOnNextActivity();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                if (mShortClickWeather.equals(ACTION_UPDATE)) {
                    Intent weatherintent = new Intent("com.collective.personalize.INTENT_WEATHER_REQUEST");
                    weatherintent.putExtra("com.collective.personalize.INTENT_EXTRA_TYPE", "updateweather");
                    weatherintent.putExtra("com.collective.personalize.INTENT_EXTRA_ISMANUAL", true);
                    v.getContext().sendBroadcast(weatherintent);
                } else {
                    try {
                        intent = Intent.parseUri(mShortClickWeather, 0);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        mContext.startActivity(intent);
                    } catch (ActivityNotFoundException e){
                        e.printStackTrace();
                    } catch (URISyntaxException e) {
                        e.printStackTrace();
                    }
                   animateCollapse();
                }
            }
        }
    };

    private View.OnLongClickListener mWeatherPanelLongClickListener = new View.OnLongClickListener() {

        @Override
        public boolean onLongClick(View v) {
            if (mLongClickWeather.equals(ACTION_NOTHING)) {
                return true;
            } else {
                try {
                    ActivityManagerNative.getDefault().dismissKeyguardOnNextActivity();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                if (mLongClickWeather.equals(ACTION_UPDATE)) {
                    Intent weatherintent = new Intent("com.collective.personalize.INTENT_WEATHER_REQUEST");
                    weatherintent.putExtra("com.collective.personalize.INTENT_EXTRA_TYPE", "updateweather");
                    weatherintent.putExtra("com.collective.personalize.INTENT_EXTRA_ISMANUAL", true);
                    v.getContext().sendBroadcast(weatherintent);
                } else {
                    try {
                        intent = Intent.parseUri(mLongClickWeather, 0);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        mContext.startActivity(intent);
                    } catch (ActivityNotFoundException e){
                        e.printStackTrace();
                    } catch (URISyntaxException e) {
                        e.printStackTrace();
                    }
                   animateCollapse();
                 }
            }
        return true;
        }
    };

    private View.OnClickListener mDateViewListener = new View.OnClickListener() {
        public void onClick(View v) {
        if (mShortClick.equals(ACTION_NOTHING)) {
            // Do nothing....
        } else {
            try {
                ActivityManagerNative.getDefault().dismissKeyguardOnNextActivity();
            } catch (RemoteException e) {
            }
            if (mShortClick.equals(ACTION_TODAY)) {
                 // A date-time specified in milliseconds since the epoch.
                 long startMillis = System.currentTimeMillis();
                 Uri.Builder builder = CalendarContract.CONTENT_URI.buildUpon();
                 builder.appendPath("time");
                 ContentUris.appendId(builder, startMillis);
                 intent = new Intent(Intent.ACTION_VIEW)
                      .setData(builder.build());
            } else if (mShortClick.equals(ACTION_EVENT)) {
                 intent = new Intent(Intent.ACTION_INSERT)
                      .setData(Events.CONTENT_URI);
            } else if (mShortClick.equals(ACTION_VOICEASSIST)) {
                 intent = new Intent(RecognizerIntent.ACTION_WEB_SEARCH);
            } else if (mShortClick.equals(ACTION_ALARM)) {
                 intent = new Intent(AlarmClock.ACTION_SET_ALARM);
            } else {
                try {
                    intent = Intent.parseUri(mShortClick, 0);
                } catch (URISyntaxException e) {
                }
            }
               try {
                   intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                   mContext.startActivity(intent);
               } catch (ActivityNotFoundException e){
               }
            animateCollapse();
            }
        }
    };

    private View.OnLongClickListener mDateViewLongClickListener = new View.OnLongClickListener() {

        @Override
        public boolean onLongClick(View v) {
        if (mLongClick.equals(ACTION_NOTHING)) {
            // Do nothing....
        } else {
            try {
                ActivityManagerNative.getDefault().dismissKeyguardOnNextActivity();
            } catch (RemoteException e) {
            }
            if (mLongClick.equals(ACTION_TODAY)) {
                 // A date-time specified in milliseconds since the epoch.
                 long startMillis = System.currentTimeMillis();
                 Uri.Builder builder = CalendarContract.CONTENT_URI.buildUpon();
                 builder.appendPath("time");
                 ContentUris.appendId(builder, startMillis);
                 intent = new Intent(Intent.ACTION_VIEW)
                      .setData(builder.build());
            } else if (mLongClick.equals(ACTION_EVENT)) {
                 intent = new Intent(Intent.ACTION_INSERT)
                      .setData(Events.CONTENT_URI);
            } else if (mLongClick.equals(ACTION_VOICEASSIST)) {
                 intent = new Intent(RecognizerIntent.ACTION_WEB_SEARCH);
            } else if (mLongClick.equals(ACTION_ALARM)) {
                 intent = new Intent(AlarmClock.ACTION_SET_ALARM);
            } else {
                try {
                    intent = Intent.parseUri(mLongClick, 0);
                } catch (URISyntaxException e) {
                }
            }
               try {
                   intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                   mContext.startActivity(intent);
               } catch (ActivityNotFoundException e){
               }
            animateCollapse();
			}
            return true;
        }
    };

    private View.OnClickListener mSettingsButtonListener = new View.OnClickListener() {
        public void onClick(View v) {
            if (mDropdownSettingsDefualtBehavior)
                mSettingsBehaviorOpenSettings();
            else
                mSettingsBehaviorOpenToggles();
        }
    };

    private View.OnLongClickListener mSettingsLongClickListener = new View.OnLongClickListener() {

        @Override
        public boolean onLongClick(View v) {
            if (mDropdownSettingsDefualtBehavior)
                mSettingsBehaviorOpenToggles();
            else
                mSettingsBehaviorOpenSettings();
            return true;
        }
    };

    private void mSettingsBehaviorOpenSettings() {
            // We take this as a good indicator that Setup is running and we shouldn't
            // allow you to go somewhere else
            if (!isDeviceProvisioned()) return;
            try {
                // Dismiss the lock screen when Settings starts.
                ActivityManagerNative.getDefault().dismissKeyguardOnNextActivity();
            } catch (RemoteException e) {
            }
            mContext.startActivity(new Intent(Settings.ACTION_SETTINGS)
                    .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
            animateCollapse();
    }

    private void mSettingsBehaviorOpenToggles() {
        if (mQuickToggles.getVisibility() == View.VISIBLE) {
            int height = mQuickToggles.getHeight();

            final Animation a =
                    AnimationUtils.makeOutAnimation(mContext, true);
            a.setDuration(400);
            final Animation b =
                    AnimationUtils.makeInAnimation(mContext, true);
            b.setDuration(400);
            if ((mWeatherHideStatus == 2) && mWeatherPanelEnabled) {
                a.setAnimationListener(new AnimationListener() {

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        mQuickToggles.setVisibility(View.GONE);
                        b.setAnimationListener(new AnimationListener() {

                            @Override
                            public void onAnimationEnd(Animation animation) {
                            }

                            @Override
                            public void onAnimationStart(Animation animation) {
                            mWeatherPanel.setVisibility(View.VISIBLE);
                            }

                            @Override
                            public void onAnimationRepeat(Animation animation) {
                            }
                            });
                        mWeatherPanel.startAnimation(b);
                    }

                    @Override
                    public void onAnimationStart(Animation animation) {
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {
                    }
                });
                mQuickToggles.startAnimation(a);
            } else {
                a.setAnimationListener(new AnimationListener() {

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        mQuickToggles.setVisibility(View.GONE);
                        if ((mWeatherHideStatus == 1) && mWeatherPanelEnabled) {
                            mWeatherPanel.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void onAnimationStart(Animation animation) {
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {
                    }
                });
           mQuickToggles.startAnimation(a);
           if ((mWeatherHideStatus == 1) && mWeatherPanelEnabled) {
               mWeatherPanel.startAnimation(a);
           }
           }
        } else {
            final Animation a =
                    AnimationUtils.makeInAnimation(mContext, true);
            a.setDuration(400);
            final Animation b =
                    AnimationUtils.makeOutAnimation(mContext, true);
            b.setDuration(400);

			mQuickToggles.setVisibility(View.VISIBLE);
			mQuickToggles.requestLayout();
            if ((mWeatherHideStatus == 2) && mWeatherPanelEnabled) {
                b.setAnimationListener(new AnimationListener() {

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        mWeatherPanel.setVisibility(View.GONE);
                        a.setAnimationListener(new AnimationListener() {

                            @Override
                            public void onAnimationEnd(Animation animation) {
                            }

                            @Override
                            public void onAnimationStart(Animation animation) {
                            mQuickToggles.setVisibility(View.VISIBLE);
                            }

                            @Override
                            public void onAnimationRepeat(Animation animation) {
                            }
                            });
                        mQuickToggles.startAnimation(a);
                    }

                    @Override
                    public void onAnimationStart(Animation animation) {
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {
                    }
                });
                mWeatherPanel.startAnimation(b);
            } else {
                a.setAnimationListener(new AnimationListener() {

                    @Override
                    public void onAnimationEnd(Animation animation) {
                    }

                    @Override
                    public void onAnimationStart(Animation animation) {
                        mQuickToggles.setVisibility(View.VISIBLE);
                        if ((mWeatherHideStatus == 1) && mWeatherPanelEnabled) {
                        mWeatherPanel.setVisibility(View.VISIBLE);
                        }
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {
                    }
                });
           mQuickToggles.startAnimation(a);
           if ((mWeatherHideStatus == 1) && mWeatherPanelEnabled) {
           mWeatherPanel.startAnimation(a);
           }
           }
        }

    }

    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
=======
    public void startActivityDismissingKeyguard(Intent intent, boolean onlyProvisioned) {
        if (onlyProvisioned && !isDeviceProvisioned()) return;
        try {
            // Dismiss the lock screen when Settings starts.
            ActivityManagerNative.getDefault().dismissKeyguardOnNextActivity();
        } catch (RemoteException e) {
        }
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        mContext.startActivityAsUser(intent, new UserHandle(UserHandle.USER_CURRENT));
        animateCollapsePanels();
    }

    private View.OnClickListener mSettingsButtonListener = new View.OnClickListener() {
        public void onClick(View v) {
            if (mHasSettingsPanel) {
                animateExpandSettingsPanel();
            } else {
                startActivityDismissingKeyguard(
                        new Intent(android.provider.Settings.ACTION_SETTINGS), true);
            }
        }
    };

    private View.OnClickListener mClockClickListener = new View.OnClickListener() {
        public void onClick(View v) {
            startActivityDismissingKeyguard(
                    new Intent(Intent.ACTION_QUICK_CLOCK), true); // have fun, everyone
        }
    };

    private View.OnClickListener mNotificationButtonListener = new View.OnClickListener() {
        public void onClick(View v) {
            animateExpandNotificationsPanel();
        }
    };

    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            if (DEBUG) Slog.v(TAG, "onReceive: " + intent);
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
            String action = intent.getAction();
            if (Intent.ACTION_CLOSE_SYSTEM_DIALOGS.equals(action)) {
                int flags = CommandQueue.FLAG_EXCLUDE_NONE;
                if (Intent.ACTION_CLOSE_SYSTEM_DIALOGS.equals(action)) {
                    String reason = intent.getStringExtra("reason");
                    if (reason != null && reason.equals(SYSTEM_DIALOG_REASON_RECENT_APPS)) {
                        flags |= CommandQueue.FLAG_EXCLUDE_RECENTS_PANEL;
                    }
                }
<<<<<<< HEAD
                animateCollapse(flags);
            }
            else if (Intent.ACTION_SCREEN_OFF.equals(action)) {
                // no waiting!
                performCollapse();
            }
            else if (Intent.ACTION_CONFIGURATION_CHANGED.equals(action)) {
                updateResources();
                repositionNavigationBar();
                updateExpandedViewPos(EXPANDED_LEAVE_ALONE);
=======
                animateCollapsePanels(flags);
            }
            else if (Intent.ACTION_SCREEN_OFF.equals(action)) {
                // no waiting!
                makeExpandedInvisible();
                notifyNavigationBarScreenOn(false);
            }
            else if (Intent.ACTION_CONFIGURATION_CHANGED.equals(action)) {
                if (DEBUG) {
                    Slog.v(TAG, "configuration changed: " + mContext.getResources().getConfiguration());
                }
                mDisplay.getSize(mCurrentDisplaySize);

                updateResources();
                repositionNavigationBar();
                updateExpandedViewPos(EXPANDED_LEAVE_ALONE);
                updateShowSearchHoldoff();
            }
            else if (Intent.ACTION_SCREEN_ON.equals(action)) {
                // work around problem where mDisplay.getRotation() is not stable while screen is off (bug 7086018)
                repositionNavigationBar();
                notifyNavigationBarScreenOn(true);
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
            }
        }
    };

<<<<<<< HEAD
=======
    @Override
    public void userSwitched(int newUserId) {
        if (MULTIUSER_DEBUG) mNotificationPanelDebugText.setText("USER " + newUserId);
        animateCollapsePanels();
        updateNotificationIcons();
        resetUserSetupObserver();
    }

    private void resetUserSetupObserver() {
        mContext.getContentResolver().unregisterContentObserver(mUserSetupObserver);
        mUserSetupObserver.onChange(false);
        mContext.getContentResolver().registerContentObserver(
                Settings.Secure.getUriFor(Settings.Secure.USER_SETUP_COMPLETE), true,
                mUserSetupObserver,
                mCurrentUserId);
    }

>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
    private void setIntruderAlertVisibility(boolean vis) {
        if (!ENABLE_INTRUDERS) return;
        if (DEBUG) {
            Slog.v(TAG, (vis ? "showing" : "hiding") + " intruder alert window");
        }
        mIntruderAlertView.setVisibility(vis ? View.VISIBLE : View.GONE);
    }

    public void dismissIntruder() {
        if (mCurrentlyIntrudingNotification == null) return;

        try {
            mBarService.onNotificationClear(
                    mCurrentlyIntrudingNotification.pkg,
                    mCurrentlyIntrudingNotification.tag,
                    mCurrentlyIntrudingNotification.id);
        } catch (android.os.RemoteException ex) {
            // oh well
        }
    }

    /**
     * Reload some of our resources when the configuration changes.
     *
     * We don't reload everything when the configuration changes -- we probably
     * should, but getting that smooth is tough.  Someday we'll fix that.  In the
     * meantime, just update the things that we know change.
     */
    void updateResources() {
        final Context context = mContext;
        final Resources res = context.getResources();

<<<<<<< HEAD
        // detect theme change.
        CustomTheme newTheme = res.getConfiguration().customTheme;
        if (newTheme != null &&
                (mCurrentTheme == null || !mCurrentTheme.equals(newTheme))) {
            mCurrentTheme = (CustomTheme)newTheme.clone();
            try {
                Runtime.getRuntime().exec("pkill -TERM -f com.android.systemui");
            } catch (IOException e) {
                // we're screwed here fellas
            }
        } else {

            if (mClearButton instanceof TextView) {
                ((TextView)mClearButton).setText(context.getText(R.string.status_bar_clear_all_button));
            }
            loadDimens();
        }
=======
        if (mClearButton instanceof TextView) {
            ((TextView)mClearButton).setText(context.getText(R.string.status_bar_clear_all_button));
        }

        // Update the QuickSettings container
        if (mQS != null) mQS.updateResources();

        loadDimens();
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
    }

    protected void loadDimens() {
        final Resources res = mContext.getResources();

        mNaturalBarHeight = res.getDimensionPixelSize(
                com.android.internal.R.dimen.status_bar_height);

        int newIconSize = res.getDimensionPixelSize(
            com.android.internal.R.dimen.status_bar_icon_size);
        int newIconHPadding = res.getDimensionPixelSize(
            R.dimen.status_bar_icon_padding);

        if (newIconHPadding != mIconHPadding || newIconSize != mIconSize) {
//            Slog.d(TAG, "size=" + newIconSize + " padding=" + newIconHPadding);
            mIconHPadding = newIconHPadding;
            mIconSize = newIconSize;
            //reloadAllNotificationIcons(); // reload the tray
        }

        mEdgeBorder = res.getDimensionPixelSize(R.dimen.status_bar_edge_ignore);

        mSelfExpandVelocityPx = res.getDimension(R.dimen.self_expand_velocity);
        mSelfCollapseVelocityPx = res.getDimension(R.dimen.self_collapse_velocity);
        mFlingExpandMinVelocityPx = res.getDimension(R.dimen.fling_expand_min_velocity);
        mFlingCollapseMinVelocityPx = res.getDimension(R.dimen.fling_collapse_min_velocity);

        mCollapseMinDisplayFraction = res.getFraction(R.dimen.collapse_min_display_fraction, 1, 1);
        mExpandMinDisplayFraction = res.getFraction(R.dimen.expand_min_display_fraction, 1, 1);

        mExpandAccelPx = res.getDimension(R.dimen.expand_accel);
        mCollapseAccelPx = res.getDimension(R.dimen.collapse_accel);

        mFlingGestureMaxXVelocityPx = res.getDimension(R.dimen.fling_gesture_max_x_velocity);

        mFlingGestureMaxOutputVelocityPx = res.getDimension(R.dimen.fling_gesture_max_output_velocity);

        mNotificationPanelMarginBottomPx
            = (int) res.getDimension(R.dimen.notification_panel_margin_bottom);
<<<<<<< HEAD
        mNotificationPanelMarginLeftPx
            = (int) res.getDimension(R.dimen.notification_panel_margin_left);
        mNotificationPanelGravity = res.getInteger(R.integer.notification_panel_layout_gravity);
        if (mNotificationPanelGravity <= 0) {
            mNotificationPanelGravity = Gravity.CENTER_VERTICAL | Gravity.TOP;
        }
        getNinePatchPadding(res.getDrawable(R.drawable.notification_panel_bg), mNotificationPanelBackgroundPadding);
        final int notificationPanelDecorationHeight =
              res.getDimensionPixelSize(R.dimen.notification_panel_padding_top)
            + res.getDimensionPixelSize(R.dimen.notification_panel_header_height)
            + mNotificationPanelBackgroundPadding.top
            + mNotificationPanelBackgroundPadding.bottom;
        mNotificationPanelMinHeight = 
              notificationPanelDecorationHeight 
            + res.getDimensionPixelSize(R.dimen.close_handle_underlap);

        mCarrierLabelHeight = res.getDimensionPixelSize(R.dimen.carrier_label_height);

        if (false) Slog.v(TAG, "updateResources");
    }

    private static void getNinePatchPadding(Drawable d, Rect outPadding) {
        if (d instanceof NinePatchDrawable) {
            NinePatchDrawable ninePatch = (NinePatchDrawable) d;
            ninePatch.getPadding(outPadding);
        }
=======
        mNotificationPanelMarginPx
            = (int) res.getDimension(R.dimen.notification_panel_margin_left);
        mNotificationPanelGravity = res.getInteger(R.integer.notification_panel_layout_gravity);
        if (mNotificationPanelGravity <= 0) {
            mNotificationPanelGravity = Gravity.LEFT | Gravity.TOP;
        }
        mSettingsPanelGravity = res.getInteger(R.integer.settings_panel_layout_gravity);
        if (mSettingsPanelGravity <= 0) {
            mSettingsPanelGravity = Gravity.RIGHT | Gravity.TOP;
        }

        mCarrierLabelHeight = res.getDimensionPixelSize(R.dimen.carrier_label_height);
        mNotificationHeaderHeight = res.getDimensionPixelSize(R.dimen.notification_panel_header_height);

        mNotificationPanelMinHeightFrac = res.getFraction(R.dimen.notification_panel_min_height_frac, 1, 1);
        if (mNotificationPanelMinHeightFrac < 0f || mNotificationPanelMinHeightFrac > 1f) {
            mNotificationPanelMinHeightFrac = 0f;
        }

        if (false) Slog.v(TAG, "updateResources");
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
    }

    //
    // tracing
    //

    void postStartTracing() {
        mHandler.postDelayed(mStartTracing, 3000);
    }

    void vibrate() {
        android.os.Vibrator vib = (android.os.Vibrator)mContext.getSystemService(
                Context.VIBRATOR_SERVICE);
        vib.vibrate(250);
    }

    Runnable mStartTracing = new Runnable() {
        public void run() {
            vibrate();
            SystemClock.sleep(250);
            Slog.d(TAG, "startTracing");
            android.os.Debug.startMethodTracing("/data/statusbar-traces/trace");
            mHandler.postDelayed(mStopTracing, 10000);
        }
    };

    Runnable mStopTracing = new Runnable() {
        public void run() {
            android.os.Debug.stopMethodTracing();
            Slog.d(TAG, "stopTracing");
            vibrate();
        }
    };

    @Override
    protected void haltTicker() {
        mTicker.halt();
    }

    @Override
    protected boolean shouldDisableNavbarGestures() {
<<<<<<< HEAD
        return mExpanded || (mDisabled & StatusBarManager.DISABLE_HOME) != 0;
=======
        return !isDeviceProvisioned()
                || mExpandedVisible
                || (mDisabled & StatusBarManager.DISABLE_SEARCH) != 0;
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
    }

    private static class FastColorDrawable extends Drawable {
        private final int mColor;

        public FastColorDrawable(int color) {
            mColor = 0xff000000 | color;
        }

        @Override
        public void draw(Canvas canvas) {
            canvas.drawColor(mColor, PorterDuff.Mode.SRC);
        }

        @Override
        public void setAlpha(int alpha) {
        }

        @Override
        public void setColorFilter(ColorFilter cf) {
        }

        @Override
        public int getOpacity() {
            return PixelFormat.OPAQUE;
        }

        @Override
        public void setBounds(int left, int top, int right, int bottom) {
        }

        @Override
        public void setBounds(Rect bounds) {
        }
    }
<<<<<<< HEAD

    class SettingsObserver extends ContentObserver {
        SettingsObserver(Handler handler) {
            super(handler);
        }

        void observe() {
            ContentResolver resolver = mContext.getContentResolver();
            resolver.registerContentObserver(Settings.System.getUriFor(
                    Settings.System.STATUSBAR_SETTINGS_BEHAVIOR), false, this);
            resolver.registerContentObserver(Settings.System.getUriFor(
                    Settings.System.STATUSBAR_TOGGLES_AUTOHIDE), false, this);
            resolver.registerContentObserver(Settings.System.getUriFor(
                    Settings.System.STATUS_BAR_BRIGHTNESS_SLIDER), false, this);
            resolver.registerContentObserver(Settings.System.getUriFor(
                    Settings.System.STATUSBAR_WEATHER_STYLE), false, this);
            resolver.registerContentObserver(Settings.System.getUriFor(
                    Settings.System.STATUSBAR_WEATHER_HIDE), false, this);
            resolver.registerContentObserver(Settings.System.getUriFor(
                    Settings.System.USE_WEATHER), false, this);
            resolver.registerContentObserver(Settings.System.getUriFor(
                    Settings.System.NOTIFICATION_DATE_SHORTCLICK), false, this);
            resolver.registerContentObserver(Settings.System.getUriFor(
                    Settings.System.NOTIFICATION_DATE_LONGCLICK), false, this);
            resolver.registerContentObserver(Settings.System.getUriFor(
                    Settings.System.WEATHER_PANEL_SHORTCLICK), false, this);
            resolver.registerContentObserver(Settings.System.getUriFor(
                    Settings.System.WEATHER_PANEL_LONGCLICK), false, this);
        }

        @Override
        public void onChange(boolean selfChange) {
            updateSettings();
        }
    }

    private class BrightNessContentObserver extends ContentObserver {

        public BrightNessContentObserver() {
            super(new Handler());
        }

        @Override
        public void onChange(boolean selfChange) {
            mIsAutoBrightNess = checkAutoBrightNess();
        }
    }

    private boolean checkAutoBrightNess() {
        return Settings.System.getInt(mContext.getContentResolver(),
                Settings.System.SCREEN_BRIGHTNESS_MODE,
                Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL) == Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC;
    }

    private void updatePropFactorValue() {
        mPropFactor = Float.valueOf((float) android.os.PowerManager.BRIGHTNESS_ON
                / Integer.valueOf(mDisplay.getWidth()).floatValue());
    }

    private void doBrightNess(MotionEvent e) {
        int screenBrightness;
        try {
            screenBrightness = checkMinMax(Float.valueOf((e.getRawX() * mPropFactor.floatValue()))
                    .intValue());
            Settings.System.putInt(mContext.getContentResolver(), "screen_brightness",
                    screenBrightness);
        } catch (NullPointerException e2) {
            return;
        }
        double percent = ((screenBrightness / (double) 255) * 100) + 0.5;
        mBrightnessPercent.setText((int)percent + "%");
        mBrightnessPercent.setVisibility(View.VISIBLE);
        try {
            IPowerManager pw = IPowerManager.Stub.asInterface(ServiceManager.getService("power"));
            if (pw != null) {
                pw.setBacklightBrightness(screenBrightness);
            }
        } catch (RemoteException e1) {
        }
    }

    private void stopBrightnessMessages() {
        if (mIsStatusBarBrightNess && mHandler.hasMessages(MSG_STATUSBAR_BRIGHTNESS)) {
            mHandler.removeMessages(MSG_STATUSBAR_BRIGHTNESS);
        }
        mBrightnessPercent.setVisibility(View.GONE);
        mBrightnessPercent.setText("");
    }

    private int checkMinMax(int brightness) {
        int min = 0;
        int max = 255;

        if (min > brightness) // brightness < 0x1E
            return min;
        else if (max < brightness) { // brightness > 0xFF
            return max;
        }

        return brightness;
    }

    boolean mDropdownSettingsDefualtBehavior = true;

    public void updateSettings() {
        ContentResolver cr = mStatusBarView.getContext().getContentResolver();

        mShortClick = Settings.System.getString(cr,
                Settings.System.NOTIFICATION_DATE_SHORTCLICK);

        mLongClick = Settings.System.getString(cr,
                Settings.System.NOTIFICATION_DATE_LONGCLICK);

        mShortClickWeather = Settings.System.getString(cr,
                Settings.System.WEATHER_PANEL_SHORTCLICK);

        mLongClickWeather = Settings.System.getString(cr,
                Settings.System.WEATHER_PANEL_LONGCLICK);
        
        mDropdownSettingsDefualtBehavior = Settings.System.getBoolean(cr,
                Settings.System.STATUSBAR_SETTINGS_BEHAVIOR, true);

        mQuickTogglesHideAfterCollapse = Settings.System.getBoolean(cr,
                Settings.System.STATUSBAR_TOGGLES_AUTOHIDE, false);

        mIsStatusBarBrightNess = Settings.System.getBoolean(cr,
                Settings.System.STATUS_BAR_BRIGHTNESS_SLIDER, true);

        mWeatherHideStatus = (Settings.System.getInt(cr,
                Settings.System.STATUSBAR_WEATHER_HIDE, 0));

        if (mWeatherHideStatus == 0) {
            mWeatherPanel.setVisibility(View.VISIBLE);
        }
        
        mWeatherPanelEnabled = (Settings.System.getInt(cr,
                Settings.System.STATUSBAR_WEATHER_STYLE, 2) == 1)
                && (Settings.System.getBoolean(cr, Settings.System.USE_WEATHER, false));

        mWeatherPanel.setVisibility(mWeatherPanelEnabled ? View.VISIBLE : View.GONE);

    }
=======
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
}
