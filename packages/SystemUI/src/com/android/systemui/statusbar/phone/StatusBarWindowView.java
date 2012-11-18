/*
 * Copyright (C) 2012 The Android Open Source Project
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

import android.content.Context;
<<<<<<< HEAD
=======
import android.graphics.Canvas;
import android.graphics.Paint;
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
<<<<<<< HEAD
=======
import android.view.View;
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
import android.widget.FrameLayout;
import android.widget.ScrollView;
import android.widget.TextSwitcher;

import com.android.systemui.ExpandHelper;
import com.android.systemui.R;
<<<<<<< HEAD
=======
import com.android.systemui.statusbar.BaseStatusBar;
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
import com.android.systemui.statusbar.policy.NotificationRowLayout;


public class StatusBarWindowView extends FrameLayout
{
<<<<<<< HEAD
    private static final String TAG = "StatusBarWindowView";

    private ExpandHelper mExpandHelper;
    private NotificationRowLayout latestItems;
=======
    public static final String TAG = "StatusBarWindowView";
    public static final boolean DEBUG = BaseStatusBar.DEBUG;

    private ExpandHelper mExpandHelper;
    private NotificationRowLayout latestItems;
    private NotificationPanelView mNotificationPanel;
    private ScrollView mScrollView;
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a

    PhoneStatusBar mService;

    public StatusBarWindowView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setMotionEventSplittingEnabled(false);
<<<<<<< HEAD
=======
        setWillNotDraw(!DEBUG);
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
    }

    @Override
    protected void onAttachedToWindow () {
        super.onAttachedToWindow();
        latestItems = (NotificationRowLayout) findViewById(R.id.latestItems);
<<<<<<< HEAD
        ScrollView scroller = (ScrollView) findViewById(R.id.scroll);
=======
        mScrollView = (ScrollView) findViewById(R.id.scroll);
        mNotificationPanel = (NotificationPanelView) findViewById(R.id.notification_panel);
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
        int minHeight = getResources().getDimensionPixelSize(R.dimen.notification_row_min_height);
        int maxHeight = getResources().getDimensionPixelSize(R.dimen.notification_row_max_height);
        mExpandHelper = new ExpandHelper(mContext, latestItems, minHeight, maxHeight);
        mExpandHelper.setEventSource(this);
<<<<<<< HEAD
        mExpandHelper.setScrollView(scroller);
    }

    @Override
    public void dispatchWindowFocusChanged(boolean hasFocus) {
        this.setFocusableInTouchMode(hasFocus);
        this.requestFocus();
=======
        mExpandHelper.setScrollView(mScrollView);
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        boolean down = event.getAction() == KeyEvent.ACTION_DOWN;
        switch (event.getKeyCode()) {
        case KeyEvent.KEYCODE_BACK:
            if (!down) {
<<<<<<< HEAD
                mService.animateCollapse();
=======
                mService.animateCollapsePanels();
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
            }
            return true;
        }
        return super.dispatchKeyEvent(event);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
<<<<<<< HEAD
        MotionEvent cancellation = MotionEvent.obtain(ev);
        cancellation.setAction(MotionEvent.ACTION_CANCEL);

        boolean intercept = mExpandHelper.onInterceptTouchEvent(ev) ||
                super.onInterceptTouchEvent(ev);
        if (intercept) {
            latestItems.onInterceptTouchEvent(cancellation);
=======
        boolean intercept = false;
        if (mNotificationPanel.isFullyExpanded() && mScrollView.getVisibility() == View.VISIBLE) {
            intercept = mExpandHelper.onInterceptTouchEvent(ev);
        }
        if (!intercept) {
            super.onInterceptTouchEvent(ev);
        }
        if (intercept) {
            MotionEvent cancellation = MotionEvent.obtain(ev);
            cancellation.setAction(MotionEvent.ACTION_CANCEL);
            latestItems.onInterceptTouchEvent(cancellation);
            cancellation.recycle();
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
        }
        return intercept;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
<<<<<<< HEAD
        boolean handled = mExpandHelper.onTouchEvent(ev) ||
                super.onTouchEvent(ev);
        return handled;
    }
=======
        boolean handled = false;
        if (mNotificationPanel.isFullyExpanded()) {
            handled = mExpandHelper.onTouchEvent(ev);
        }
        if (!handled) {
            handled = super.onTouchEvent(ev);
        }
        return handled;
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (DEBUG) {
            Paint pt = new Paint();
            pt.setColor(0x80FFFF00);
            pt.setStrokeWidth(12.0f);
            pt.setStyle(Paint.Style.STROKE);
            canvas.drawRect(0, 0, canvas.getWidth(), canvas.getHeight(), pt);
        }
    }

    public void cancelExpandHelper() {
        if (mExpandHelper != null) {
            mExpandHelper.cancel();
        }
    }
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
}

