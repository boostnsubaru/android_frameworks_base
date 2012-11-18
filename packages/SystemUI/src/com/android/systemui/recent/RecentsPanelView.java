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

package com.android.systemui.recent;

import android.animation.Animator;
import android.animation.LayoutTransition;
<<<<<<< HEAD
import android.app.ActivityManager;
import android.app.ActivityManagerNative;
import android.app.ActivityOptions;
=======
import android.animation.TimeInterpolator;
import android.app.ActivityManager;
import android.app.ActivityManagerNative;
import android.app.ActivityOptions;
import android.app.TaskStackBuilder;
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Matrix;
<<<<<<< HEAD
import android.graphics.Rect;
=======
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
import android.graphics.Shader.TileMode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
<<<<<<< HEAD
import android.os.RemoteException;
import android.os.ServiceManager;
import android.provider.Settings;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.IWindowManager;
=======
import android.os.Bundle;
import android.os.RemoteException;
import android.os.UserHandle;
import android.provider.Settings;
import android.util.AttributeSet;
import android.util.Log;
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
<<<<<<< HEAD
import android.view.WindowManager;
import android.view.accessibility.AccessibilityEvent;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
=======
import android.view.accessibility.AccessibilityEvent;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.android.systemui.R;
import com.android.systemui.statusbar.BaseStatusBar;
<<<<<<< HEAD
import com.android.systemui.statusbar.CommandQueue;
=======
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
import com.android.systemui.statusbar.phone.PhoneStatusBar;
import com.android.systemui.statusbar.tablet.StatusBarPanel;
import com.android.systemui.statusbar.tablet.TabletStatusBar;

import java.util.ArrayList;

public class RecentsPanelView extends FrameLayout implements OnItemClickListener, RecentsCallback,
<<<<<<< HEAD
        StatusBarPanel, Animator.AnimatorListener, View.OnTouchListener {
    static final String TAG = "RecentsPanelView";
    static final boolean DEBUG = TabletStatusBar.DEBUG || PhoneStatusBar.DEBUG || false;
    private Context mContext;
    private BaseStatusBar mBar;
=======
        StatusBarPanel, Animator.AnimatorListener {
    static final String TAG = "RecentsPanelView";
    static final boolean DEBUG = TabletStatusBar.DEBUG || PhoneStatusBar.DEBUG || false;
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
    private PopupMenu mPopup;
    private View mRecentsScrim;
    private View mRecentsNoApps;
    private ViewGroup mRecentsContainer;
    private StatusBarTouchProxy mStatusBarTouchProxy;

    private boolean mShowing;
    private boolean mWaitingToShow;
<<<<<<< HEAD
    private boolean mWaitingToShowAnimated;
    private boolean mReadyToShow;
    private int mNumItemsWaitingForThumbnailsAndIcons;
    private Choreographer mChoreo;
    OnRecentsPanelVisibilityChangedListener mVisibilityChangedListener;

    ImageView mPlaceholderThumbnail;
    View mTransitionBg;
    boolean mHideRecentsAfterThumbnailScaleUpStarted;

    private Button mRecentsKillAllButton;

    private RecentTasksLoader mRecentTasksLoader;
    private ArrayList<TaskDescription> mRecentTaskDescriptions;
    private Runnable mPreloadTasksRunnable;
    private boolean mRecentTasksDirty = true;
=======
    private int mNumItemsWaitingForThumbnailsAndIcons;
    private ViewHolder mItemToAnimateInWhenWindowAnimationIsFinished;
    private boolean mWaitingForWindowAnimation;

    private RecentTasksLoader mRecentTasksLoader;
    private ArrayList<TaskDescription> mRecentTaskDescriptions;
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
    private TaskDescriptionAdapter mListAdapter;
    private int mThumbnailWidth;
    private boolean mFitThumbnailToXY;
    private int mRecentItemLayoutId;
<<<<<<< HEAD
    private boolean mFirstScreenful = true;
    private boolean mHighEndGfx;

    public static interface OnRecentsPanelVisibilityChangedListener {
        public void onRecentsPanelVisibilityChanged(boolean visible);
    }

=======
    private boolean mHighEndGfx;

>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
    public static interface RecentsScrollView {
        public int numItemsInOneScreenful();
        public void setAdapter(TaskDescriptionAdapter adapter);
        public void setCallback(RecentsCallback callback);
        public void setMinSwipeAlpha(float minAlpha);
<<<<<<< HEAD
=======
        public View findViewForTask(int persistentTaskId);
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
    }

    private final class OnLongClickDelegate implements View.OnLongClickListener {
        View mOtherView;
        OnLongClickDelegate(View other) {
            mOtherView = other;
        }
        public boolean onLongClick(View v) {
            return mOtherView.performLongClick();
        }
    }

    /* package */ final static class ViewHolder {
        View thumbnailView;
        ImageView thumbnailViewImage;
        Bitmap thumbnailViewImageBitmap;
        ImageView iconView;
        TextView labelView;
        TextView descriptionView;
<<<<<<< HEAD
=======
        View calloutLine;
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
        TaskDescription taskDescription;
        boolean loadedThumbnailAndIcon;
    }

    /* package */ final class TaskDescriptionAdapter extends BaseAdapter {
        private LayoutInflater mInflater;

        public TaskDescriptionAdapter(Context context) {
            mInflater = LayoutInflater.from(context);
        }

        public int getCount() {
            return mRecentTaskDescriptions != null ? mRecentTaskDescriptions.size() : 0;
        }

        public Object getItem(int position) {
            return position; // we only need the index
        }

        public long getItemId(int position) {
            return position; // we just need something unique for this position
        }

        public View createView(ViewGroup parent) {
            View convertView = mInflater.inflate(mRecentItemLayoutId, parent, false);
            ViewHolder holder = new ViewHolder();
            holder.thumbnailView = convertView.findViewById(R.id.app_thumbnail);
            holder.thumbnailViewImage =
                    (ImageView) convertView.findViewById(R.id.app_thumbnail_image);
            // If we set the default thumbnail now, we avoid an onLayout when we update
            // the thumbnail later (if they both have the same dimensions)
<<<<<<< HEAD
            if (mRecentTasksLoader != null) {
                updateThumbnail(holder, mRecentTasksLoader.getDefaultThumbnail(), false, false);
            }
            holder.iconView = (ImageView) convertView.findViewById(R.id.app_icon);
            if (mRecentTasksLoader != null) {
                holder.iconView.setImageBitmap(mRecentTasksLoader.getDefaultIcon());
            }
            holder.labelView = (TextView) convertView.findViewById(R.id.app_label);
=======
            updateThumbnail(holder, mRecentTasksLoader.getDefaultThumbnail(), false, false);
            holder.iconView = (ImageView) convertView.findViewById(R.id.app_icon);
            holder.iconView.setImageBitmap(mRecentTasksLoader.getDefaultIcon());
            holder.labelView = (TextView) convertView.findViewById(R.id.app_label);
            holder.calloutLine = convertView.findViewById(R.id.recents_callout_line);
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
            holder.descriptionView = (TextView) convertView.findViewById(R.id.app_description);

            convertView.setTag(holder);
            return convertView;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = createView(parent);
<<<<<<< HEAD
                if (convertView.getParent() != null) {
                    throw new RuntimeException("Recycled child has parent");
                }
            } else {
                if (convertView.getParent() != null) {
                    throw new RuntimeException("Recycled child has parent");
                }
=======
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
            }
            ViewHolder holder = (ViewHolder) convertView.getTag();

            // index is reverse since most recent appears at the bottom...
            final int index = mRecentTaskDescriptions.size() - position - 1;

            final TaskDescription td = mRecentTaskDescriptions.get(index);

            holder.labelView.setText(td.getLabel());
            holder.thumbnailView.setContentDescription(td.getLabel());
            holder.loadedThumbnailAndIcon = td.isLoaded();
            if (td.isLoaded()) {
                updateThumbnail(holder, td.getThumbnail(), true, false);
                updateIcon(holder, td.getIcon(), true, false);
                mNumItemsWaitingForThumbnailsAndIcons--;
            }
<<<<<<< HEAD
=======
            if (index == 0) {
                if (mWaitingForWindowAnimation) {
                    if (mItemToAnimateInWhenWindowAnimationIsFinished != null) {
                        holder.iconView.setAlpha(1f);
                        holder.iconView.setTranslationX(0f);
                        holder.iconView.setTranslationY(0f);
                        holder.labelView.setAlpha(1f);
                        holder.labelView.setTranslationX(0f);
                        holder.labelView.setTranslationY(0f);
                        if (holder.calloutLine != null) {
                            holder.calloutLine.setAlpha(1f);
                            holder.calloutLine.setTranslationX(0f);
                            holder.calloutLine.setTranslationY(0f);
                        }
                    }
                    mItemToAnimateInWhenWindowAnimationIsFinished = holder;
                    final int translation = -getResources().getDimensionPixelSize(
                            R.dimen.status_bar_recents_app_icon_translate_distance);
                    final Configuration config = getResources().getConfiguration();
                    if (config.orientation == Configuration.ORIENTATION_PORTRAIT) {
                        holder.iconView.setAlpha(0f);
                        holder.iconView.setTranslationX(translation);
                        holder.labelView.setAlpha(0f);
                        holder.labelView.setTranslationX(translation);
                        holder.calloutLine.setAlpha(0f);
                        holder.calloutLine.setTranslationX(translation);
                    } else {
                        holder.iconView.setAlpha(0f);
                        holder.iconView.setTranslationY(translation);
                    }
                }
            }
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a

            holder.thumbnailView.setTag(td);
            holder.thumbnailView.setOnLongClickListener(new OnLongClickDelegate(convertView));
            holder.taskDescription = td;
            return convertView;
        }

        public void recycleView(View v) {
            ViewHolder holder = (ViewHolder) v.getTag();
            updateThumbnail(holder, mRecentTasksLoader.getDefaultThumbnail(), false, false);
            holder.iconView.setImageBitmap(mRecentTasksLoader.getDefaultIcon());
            holder.iconView.setVisibility(INVISIBLE);
            holder.labelView.setText(null);
            holder.thumbnailView.setContentDescription(null);
            holder.thumbnailView.setTag(null);
            holder.thumbnailView.setOnLongClickListener(null);
            holder.thumbnailView.setVisibility(INVISIBLE);
<<<<<<< HEAD
=======
            holder.iconView.setAlpha(1f);
            holder.iconView.setTranslationX(0f);
            holder.iconView.setTranslationY(0f);
            holder.labelView.setAlpha(1f);
            holder.labelView.setTranslationX(0f);
            holder.labelView.setTranslationY(0f);
            if (holder.calloutLine != null) {
                holder.calloutLine.setAlpha(1f);
                holder.calloutLine.setTranslationX(0f);
                holder.calloutLine.setTranslationY(0f);
            }
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
            holder.taskDescription = null;
            holder.loadedThumbnailAndIcon = false;
        }
    }

    public RecentsPanelView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RecentsPanelView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
<<<<<<< HEAD
        mContext = context;
=======
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
        updateValuesFromResources();

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.RecentsPanelView,
                defStyle, 0);

        mRecentItemLayoutId = a.getResourceId(R.styleable.RecentsPanelView_recentItemLayout, 0);
<<<<<<< HEAD
=======
        mRecentTasksLoader = RecentTasksLoader.getInstance(context);
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
        a.recycle();
    }

    public int numItemsInOneScreenful() {
        if (mRecentsContainer instanceof RecentsScrollView){
            RecentsScrollView scrollView
                    = (RecentsScrollView) mRecentsContainer;
            return scrollView.numItemsInOneScreenful();
        }  else {
            throw new IllegalArgumentException("missing Recents[Horizontal]ScrollView");
        }
    }

<<<<<<< HEAD
    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && !event.isCanceled()) {
            show(false, false);
            return true;
        }
        return super.onKeyUp(keyCode, event);
    }

=======
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
    private boolean pointInside(int x, int y, View v) {
        final int l = v.getLeft();
        final int r = v.getRight();
        final int t = v.getTop();
        final int b = v.getBottom();
        return x >= l && x < r && y >= t && y < b;
    }

    public boolean isInContentArea(int x, int y) {
        if (pointInside(x, y, mRecentsContainer)) {
            return true;
        } else if (mStatusBarTouchProxy != null &&
                pointInside(x, y, mStatusBarTouchProxy)) {
            return true;
        } else {
            return false;
        }
    }

<<<<<<< HEAD
    public void show(boolean show, boolean animate) {
        if (show) {
            refreshRecentTasksList(null, true);
            mWaitingToShow = true;
            mWaitingToShowAnimated = animate;
            showIfReady();
        } else {
            show(show, animate, null, false);
=======
    public void show(boolean show) {
        show(show, null, false, false);
    }

    public void show(boolean show, ArrayList<TaskDescription> recentTaskDescriptions,
            boolean firstScreenful, boolean waitingForWindowAnimation) {
        mWaitingForWindowAnimation = waitingForWindowAnimation;
        if (show) {
            mWaitingToShow = true;
            refreshRecentTasksList(recentTaskDescriptions, firstScreenful);
            showIfReady();
        } else {
            showImpl(false);
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
        }
    }

    private void showIfReady() {
<<<<<<< HEAD
        // mWaitingToShow = there was a touch up on the recents button
        // mReadyToShow = we've created views for the first screenful of items
        if (mWaitingToShow && mReadyToShow) { // && mNumItemsWaitingForThumbnailsAndIcons <= 0
            show(true, mWaitingToShowAnimated, null, false);
=======
        // mWaitingToShow => there was a touch up on the recents button
        // mRecentTaskDescriptions != null => we've created views for the first screenful of items
        if (mWaitingToShow && mRecentTaskDescriptions != null) {
            showImpl(true);
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
        }
    }

    static void sendCloseSystemWindows(Context context, String reason) {
        if (ActivityManagerNative.isSystemReady()) {
            try {
                ActivityManagerNative.getDefault().closeSystemDialogs(reason);
            } catch (RemoteException e) {
            }
        }
    }

<<<<<<< HEAD
    public void show(boolean show, boolean animate,
            ArrayList<TaskDescription> recentTaskDescriptions, boolean firstScreenful) {
        sendCloseSystemWindows(mContext, BaseStatusBar.SYSTEM_DIALOG_REASON_RECENT_APPS);

        if (show) {
            // Need to update list of recent apps before we set visibility so this view's
            // content description is updated before it gets focus for TalkBack mode
            refreshRecentTasksList(recentTaskDescriptions, firstScreenful);

            // if there are no apps, either bring up a "No recent apps" message, or just
            // quit early
            boolean noApps = !mFirstScreenful && (mRecentTaskDescriptions.size() == 0);
            if (mRecentsNoApps != null) {
                mRecentsNoApps.setAlpha(1f);
                mRecentsNoApps.setVisibility(noApps ? View.VISIBLE : View.INVISIBLE);
            } else {
                if (noApps) {
                   if (DEBUG) Log.v(TAG, "Nothing to show");
                    // Need to set recent tasks to dirty so that next time we load, we
                    // refresh the list of tasks
                    mRecentTasksLoader.cancelLoadingThumbnailsAndIcons();
                    mRecentTasksDirty = true;

                    mWaitingToShow = false;
                    mReadyToShow = false;
                    return;
                }
            }
        } else {
            // Need to set recent tasks to dirty so that next time we load, we
            // refresh the list of tasks
            mRecentTasksLoader.cancelLoadingThumbnailsAndIcons();
            mRecentTasksDirty = true;
            mWaitingToShow = false;
            mReadyToShow = false;
        }
        if (animate) {
            if (mShowing != show) {
                mShowing = show;
                if (show) {
                    setVisibility(View.VISIBLE);
                }
                mChoreo.startAnimation(show);
            }
        } else {
            mShowing = show;
            setVisibility(show ? View.VISIBLE : View.GONE);
            mChoreo.jumpTo(show);
            onAnimationEnd(null);
        }
        if (show) {
=======
    private void showImpl(boolean show) {
        sendCloseSystemWindows(mContext, BaseStatusBar.SYSTEM_DIALOG_REASON_RECENT_APPS);

        mShowing = show;

        if (show) {
            // if there are no apps, bring up a "No recent apps" message
            boolean noApps = mRecentTaskDescriptions != null
                    && (mRecentTaskDescriptions.size() == 0);
            mRecentsNoApps.setAlpha(1f);
            mRecentsNoApps.setVisibility(noApps ? View.VISIBLE : View.INVISIBLE);

            onAnimationEnd(null);
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
            setFocusable(true);
            setFocusableInTouchMode(true);
            requestFocus();
        } else {
<<<<<<< HEAD
=======
            mWaitingToShow = false;
            // call onAnimationEnd() and clearRecentTasksList() in onUiHidden()
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
            if (mPopup != null) {
                mPopup.dismiss();
            }
        }
    }

<<<<<<< HEAD
    public void dismiss() {
        hide(true);
    }

    public void hide(boolean animate) {
        if (!animate) {
            setVisibility(View.GONE);
        }
        if (mBar != null) {
            // This will indirectly cause show(false, ...) to get called
            mBar.animateCollapse(CommandQueue.FLAG_EXCLUDE_NONE);
        }
=======
    public void onUiHidden() {
        if (!mShowing && mRecentTaskDescriptions != null) {
            onAnimationEnd(null);
            clearRecentTasksList();
        }
    }

    public void dismiss() {
        ((RecentsActivity) mContext).dismissAndGoHome();
    }

    public void dismissAndGoBack() {
        ((RecentsActivity) mContext).dismissAndGoBack();
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
    }

    public void onAnimationCancel(Animator animation) {
    }

    public void onAnimationEnd(Animator animation) {
        if (mShowing) {
            final LayoutTransition transitioner = new LayoutTransition();
            ((ViewGroup)mRecentsContainer).setLayoutTransition(transitioner);
            createCustomAnimations(transitioner);
        } else {
            ((ViewGroup)mRecentsContainer).setLayoutTransition(null);
<<<<<<< HEAD
            clearRecentTasksList();
=======
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
        }
    }

    public void onAnimationRepeat(Animator animation) {
    }

    public void onAnimationStart(Animator animation) {
    }

<<<<<<< HEAD
    /**
     * We need to be aligned at the bottom.  LinearLayout can't do this, so instead,
     * let LinearLayout do all the hard work, and then shift everything down to the bottom.
     */
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        mChoreo.setPanelHeight(mRecentsContainer.getHeight());
    }

=======
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
    @Override
    public boolean dispatchHoverEvent(MotionEvent event) {
        // Ignore hover events outside of this panel bounds since such events
        // generate spurious accessibility events with the panel content when
        // tapping outside of it, thus confusing the user.
        final int x = (int) event.getX();
        final int y = (int) event.getY();
        if (x >= 0 && x < getWidth() && y >= 0 && y < getHeight()) {
            return super.dispatchHoverEvent(event);
        }
        return true;
    }

    /**
     * Whether the panel is showing, or, if it's animating, whether it will be
     * when the animation is done.
     */
    public boolean isShowing() {
        return mShowing;
    }

<<<<<<< HEAD
    public void setBar(BaseStatusBar bar) {
        mBar = bar;

    }

=======
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
    public void setStatusBarView(View statusBarView) {
        if (mStatusBarTouchProxy != null) {
            mStatusBarTouchProxy.setStatusBar(statusBarView);
        }
    }

    public void setRecentTasksLoader(RecentTasksLoader loader) {
        mRecentTasksLoader = loader;
    }

<<<<<<< HEAD
    public void setOnVisibilityChangedListener(OnRecentsPanelVisibilityChangedListener l) {
        mVisibilityChangedListener = l;

    }

    public void setVisibility(int visibility) {
        if (mVisibilityChangedListener != null) {
            mVisibilityChangedListener.onRecentsPanelVisibilityChanged(visibility == VISIBLE);
        }
        super.setVisibility(visibility);
    }

=======
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
    public void updateValuesFromResources() {
        final Resources res = mContext.getResources();
        mThumbnailWidth = Math.round(res.getDimension(R.dimen.status_bar_recents_thumbnail_width));
        mFitThumbnailToXY = res.getBoolean(R.bool.config_recents_thumbnail_image_fits_to_xy);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

<<<<<<< HEAD
        mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
=======
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
        mRecentsContainer = (ViewGroup) findViewById(R.id.recents_container);
        mStatusBarTouchProxy = (StatusBarTouchProxy) findViewById(R.id.status_bar_touch_proxy);
        mListAdapter = new TaskDescriptionAdapter(mContext);
        if (mRecentsContainer instanceof RecentsScrollView){
            RecentsScrollView scrollView
                    = (RecentsScrollView) mRecentsContainer;
            scrollView.setAdapter(mListAdapter);
            scrollView.setCallback(this);
        } else {
            throw new IllegalArgumentException("missing Recents[Horizontal]ScrollView");
        }

        mRecentsScrim = findViewById(R.id.recents_bg_protect);
        mRecentsNoApps = findViewById(R.id.recents_no_apps);
<<<<<<< HEAD
        mChoreo = new Choreographer(this, mRecentsScrim, mRecentsContainer, mRecentsNoApps, this);

        if (mRecentsScrim != null) {
            Display d = ((WindowManager)mContext.getSystemService(Context.WINDOW_SERVICE))
                .getDefaultDisplay();
            mHighEndGfx = ActivityManager.isHighEndGfx(d);
=======

        if (mRecentsScrim != null) {
            mHighEndGfx = ActivityManager.isHighEndGfx();
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
            if (!mHighEndGfx) {
                mRecentsScrim.setBackground(null);
            } else if (mRecentsScrim.getBackground() instanceof BitmapDrawable) {
                // In order to save space, we make the background texture repeat in the Y direction
                ((BitmapDrawable) mRecentsScrim.getBackground()).setTileModeY(TileMode.REPEAT);
            }
        }
<<<<<<< HEAD

        boolean recent_kill_all_button = Settings.System.getInt(mContext.getContentResolver(),
                      Settings.System.RECENT_KILL_ALL_BUTTON, 0) == 1;

        mRecentsKillAllButton = (Button) findViewById(R.id.recents_kill_all_button);
        if (mRecentsKillAllButton != null){
            if (recent_kill_all_button){ //set the listener
                mRecentsKillAllButton.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        killAllRecentApps();
                    }
                });
            } else { // hide the button completely (gone)
                mRecentsKillAllButton.setVisibility(View.GONE);
            }
        }

        mPreloadTasksRunnable = new Runnable() {
            public void run() {
                // If we set our visibility to INVISIBLE here, we avoid an extra call to
                // onLayout later when we become visible (because onLayout is always called
                // when going from GONE)
                if (!mShowing) {
                    setVisibility(INVISIBLE);
                    refreshRecentTasksList();
                }
            }
        };
=======
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
    }

    public void setMinSwipeAlpha(float minAlpha) {
        if (mRecentsContainer instanceof RecentsScrollView){
            RecentsScrollView scrollView
                = (RecentsScrollView) mRecentsContainer;
            scrollView.setMinSwipeAlpha(minAlpha);
        }
    }

    private void createCustomAnimations(LayoutTransition transitioner) {
        transitioner.setDuration(200);
        transitioner.setStartDelay(LayoutTransition.CHANGE_DISAPPEARING, 0);
        transitioner.setAnimator(LayoutTransition.DISAPPEARING, null);
    }

    private void updateIcon(ViewHolder h, Drawable icon, boolean show, boolean anim) {
        if (icon != null) {
            h.iconView.setImageDrawable(icon);
            if (show && h.iconView.getVisibility() != View.VISIBLE) {
                if (anim) {
                    h.iconView.setAnimation(
                            AnimationUtils.loadAnimation(mContext, R.anim.recent_appear));
                }
                h.iconView.setVisibility(View.VISIBLE);
            }
        }
    }

    private void updateThumbnail(ViewHolder h, Bitmap thumbnail, boolean show, boolean anim) {
        if (thumbnail != null) {
            // Should remove the default image in the frame
            // that this now covers, to improve scrolling speed.
            // That can't be done until the anim is complete though.
            h.thumbnailViewImage.setImageBitmap(thumbnail);

            // scale the image to fill the full width of the ImageView. do this only if
            // we haven't set a bitmap before, or if the bitmap size has changed
            if (h.thumbnailViewImageBitmap == null ||
                h.thumbnailViewImageBitmap.getWidth() != thumbnail.getWidth() ||
                h.thumbnailViewImageBitmap.getHeight() != thumbnail.getHeight()) {
                if (mFitThumbnailToXY) {
                    h.thumbnailViewImage.setScaleType(ScaleType.FIT_XY);
                } else {
                    Matrix scaleMatrix = new Matrix();
                    float scale = mThumbnailWidth / (float) thumbnail.getWidth();
                    scaleMatrix.setScale(scale, scale);
                    h.thumbnailViewImage.setScaleType(ScaleType.MATRIX);
                    h.thumbnailViewImage.setImageMatrix(scaleMatrix);
                }
            }
            if (show && h.thumbnailView.getVisibility() != View.VISIBLE) {
                if (anim) {
                    h.thumbnailView.setAnimation(
                            AnimationUtils.loadAnimation(mContext, R.anim.recent_appear));
                }
                h.thumbnailView.setVisibility(View.VISIBLE);
            }
            h.thumbnailViewImageBitmap = thumbnail;
        }
    }

    void onTaskThumbnailLoaded(TaskDescription td) {
        synchronized (td) {
            if (mRecentsContainer != null) {
                ViewGroup container = mRecentsContainer;
                if (container instanceof RecentsScrollView) {
                    container = (ViewGroup) container.findViewById(
                            R.id.recents_linear_layout);
                }
                // Look for a view showing this thumbnail, to update.
                for (int i=0; i < container.getChildCount(); i++) {
                    View v = container.getChildAt(i);
                    if (v.getTag() instanceof ViewHolder) {
                        ViewHolder h = (ViewHolder)v.getTag();
                        if (!h.loadedThumbnailAndIcon && h.taskDescription == td) {
                            // only fade in the thumbnail if recents is already visible-- we
                            // show it immediately otherwise
                            //boolean animateShow = mShowing &&
                            //    mRecentsContainer.getAlpha() > ViewConfiguration.ALPHA_THRESHOLD;
                            boolean animateShow = false;
                            updateIcon(h, td.getIcon(), true, animateShow);
                            updateThumbnail(h, td.getThumbnail(), true, animateShow);
                            h.loadedThumbnailAndIcon = true;
                            mNumItemsWaitingForThumbnailsAndIcons--;
                        }
                    }
                }
            }
<<<<<<< HEAD
            }
        showIfReady();
    }

    // additional optimization when we have software system buttons - start loading the recent
    // tasks on touch down
    @Override
    public boolean onTouch(View v, MotionEvent ev) {
        if (!mShowing) {
            int action = ev.getAction() & MotionEvent.ACTION_MASK;
            if (action == MotionEvent.ACTION_DOWN) {
                post(mPreloadTasksRunnable);
            } else if (action == MotionEvent.ACTION_CANCEL) {
                setVisibility(GONE);
                clearRecentTasksList();
                // Remove the preloader if we haven't called it yet
                removeCallbacks(mPreloadTasksRunnable);
            } else if (action == MotionEvent.ACTION_UP) {
                // Remove the preloader if we haven't called it yet
                removeCallbacks(mPreloadTasksRunnable);
                if (!v.isPressed()) {
                    setVisibility(GONE);
                    clearRecentTasksList();
                }
            }
        }
        return false;
    }

    public void preloadRecentTasksList() {
        if (!mShowing) {
            mPreloadTasksRunnable.run();
        }
    }

    public void clearRecentTasksList() {
        // Clear memory used by screenshots
        if (!mShowing && mRecentTaskDescriptions != null) {
            mRecentTasksLoader.cancelLoadingThumbnailsAndIcons();
            mRecentTaskDescriptions.clear();
            mListAdapter.notifyDataSetInvalidated();
            mRecentTasksDirty = true;
        }
    }

=======
        }
        showIfReady();
    }

    public void onWindowAnimationStart() {
        if (mItemToAnimateInWhenWindowAnimationIsFinished != null) {
            final int startDelay = 150;
            final int duration = 250;
            final ViewHolder holder = mItemToAnimateInWhenWindowAnimationIsFinished;
            final TimeInterpolator cubic = new DecelerateInterpolator(1.5f);
            for (View v :
                new View[] { holder.iconView, holder.labelView, holder.calloutLine }) {
                if (v != null) {
                    v.animate().translationX(0).translationY(0).alpha(1f).setStartDelay(startDelay)
                            .setDuration(duration).setInterpolator(cubic);
                }
            }
            mItemToAnimateInWhenWindowAnimationIsFinished = null;
            mWaitingForWindowAnimation = false;
        }
    }

    public void clearRecentTasksList() {
        // Clear memory used by screenshots
        if (mRecentTaskDescriptions != null) {
            mRecentTasksLoader.cancelLoadingThumbnailsAndIcons(this);
            onTaskLoadingCancelled();
        }
    }

    public void onTaskLoadingCancelled() {
        // Gets called by RecentTasksLoader when it's cancelled
        if (mRecentTaskDescriptions != null) {
            mRecentTaskDescriptions = null;
            mListAdapter.notifyDataSetInvalidated();
        }
    }

    public void refreshViews() {
        mListAdapter.notifyDataSetInvalidated();
        updateUiElements();
        showIfReady();
    }

>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
    public void refreshRecentTasksList() {
        refreshRecentTasksList(null, false);
    }

    private void refreshRecentTasksList(
            ArrayList<TaskDescription> recentTasksList, boolean firstScreenful) {
<<<<<<< HEAD
        if (mRecentTasksDirty) {
            if (recentTasksList != null) {
                mFirstScreenful = true;
                onTasksLoaded(recentTasksList);
            } else {
                mFirstScreenful = true;
                mRecentTasksLoader.loadTasksInBackground();
            }
            mRecentTasksDirty = false;
        }
    }

    public void onTasksLoaded(ArrayList<TaskDescription> tasks) {
        if (!mFirstScreenful && tasks.size() == 0) {
            return;
        }
        mNumItemsWaitingForThumbnailsAndIcons = mFirstScreenful 
                ? tasks.size() : mRecentTaskDescriptions == null 
=======
        if (mRecentTaskDescriptions == null && recentTasksList != null) {
            onTasksLoaded(recentTasksList, firstScreenful);
        } else {
            mRecentTasksLoader.loadTasksInBackground();
        }
    }

    public void onTasksLoaded(ArrayList<TaskDescription> tasks, boolean firstScreenful) {
        mNumItemsWaitingForThumbnailsAndIcons = firstScreenful
                ? tasks.size() : mRecentTaskDescriptions == null
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
                        ? 0 : mRecentTaskDescriptions.size();
        if (mRecentTaskDescriptions == null) {
            mRecentTaskDescriptions = new ArrayList<TaskDescription>(tasks);
        } else {
            mRecentTaskDescriptions.addAll(tasks);
        }
<<<<<<< HEAD
        mListAdapter.notifyDataSetInvalidated();
        updateUiElements(getResources().getConfiguration());
        mReadyToShow = true;
        mFirstScreenful = false;
        showIfReady();
    }

    public ArrayList<TaskDescription> getRecentTasksList() {
        return mRecentTaskDescriptions;
    }

    public boolean getFirstScreenful() {
        return mFirstScreenful;
    }

    private void updateUiElements(Configuration config) {
        final int items = mRecentTaskDescriptions.size();
=======
        if (((RecentsActivity) mContext).isActivityShowing()) {
            refreshViews();
        }
    }

    private void updateUiElements() {
        final int items = mRecentTaskDescriptions != null
                ? mRecentTaskDescriptions.size() : 0;
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a

        mRecentsContainer.setVisibility(items > 0 ? View.VISIBLE : View.GONE);

        // Set description for accessibility
<<<<<<< HEAD
        int numRecentApps = mRecentTaskDescriptions.size();
=======
        int numRecentApps = mRecentTaskDescriptions != null
                ? mRecentTaskDescriptions.size() : 0;
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
        String recentAppsAccessibilityDescription;
        if (numRecentApps == 0) {
            recentAppsAccessibilityDescription =
                getResources().getString(R.string.status_bar_no_recent_apps);
        } else {
            recentAppsAccessibilityDescription = getResources().getQuantityString(
                R.plurals.status_bar_accessibility_recent_apps, numRecentApps, numRecentApps);
        }
        setContentDescription(recentAppsAccessibilityDescription);
    }

<<<<<<< HEAD

    boolean mThumbnailScaleUpStarted;
=======
    public boolean simulateClick(int persistentTaskId) {
        if (mRecentsContainer instanceof RecentsScrollView){
            RecentsScrollView scrollView
                = (RecentsScrollView) mRecentsContainer;
            View v = scrollView.findViewForTask(persistentTaskId);
            if (v != null) {
                handleOnClick(v);
                return true;
            }
        }
        return false;
    }

>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
    public void handleOnClick(View view) {
        ViewHolder holder = (ViewHolder)view.getTag();
        TaskDescription ad = holder.taskDescription;
        final Context context = view.getContext();
        final ActivityManager am = (ActivityManager)
                context.getSystemService(Context.ACTIVITY_SERVICE);
        Bitmap bm = holder.thumbnailViewImageBitmap;
        boolean usingDrawingCache;
        if (bm.getWidth() == holder.thumbnailViewImage.getWidth() &&
                bm.getHeight() == holder.thumbnailViewImage.getHeight()) {
            usingDrawingCache = false;
        } else {
            holder.thumbnailViewImage.setDrawingCacheEnabled(true);
            bm = holder.thumbnailViewImage.getDrawingCache();
            usingDrawingCache = true;
        }
<<<<<<< HEAD

        if (mPlaceholderThumbnail == null) {
            mPlaceholderThumbnail =
                    (ImageView) findViewById(R.id.recents_transition_placeholder_icon);
        }
        if (mTransitionBg == null) {
            mTransitionBg = (View) findViewById(R.id.recents_transition_background);

            IWindowManager wm = IWindowManager.Stub.asInterface(
                    ServiceManager.getService(Context.WINDOW_SERVICE));
            try {
                if (!wm.hasSystemNavBar()) {
                    FrameLayout.LayoutParams lp =
                            (FrameLayout.LayoutParams) mTransitionBg.getLayoutParams();
                    int statusBarHeight = getResources().
                            getDimensionPixelSize(com.android.internal.R.dimen.status_bar_height);
                    lp.setMargins(0, statusBarHeight, 0, 0);
                    mTransitionBg.setLayoutParams(lp);
                }
            } catch (RemoteException e) {
                Log.w(TAG, "Failing checking whether status bar is visible", e);
            }
        }

        final ImageView placeholderThumbnail = mPlaceholderThumbnail;
        mHideRecentsAfterThumbnailScaleUpStarted = false;
        placeholderThumbnail.setVisibility(VISIBLE);
        if (!usingDrawingCache) {
            placeholderThumbnail.setImageBitmap(bm);
        } else {
            Bitmap b2 = bm.copy(bm.getConfig(), true);
            placeholderThumbnail.setImageBitmap(b2);
        }
        Rect r = new Rect();
        holder.thumbnailViewImage.getGlobalVisibleRect(r);

        placeholderThumbnail.setTranslationX(r.left);
        placeholderThumbnail.setTranslationY(r.top);

        show(false, true);

        mThumbnailScaleUpStarted = false;
        ActivityOptions opts = ActivityOptions.makeDelayedThumbnailScaleUpAnimation(
                holder.thumbnailViewImage, bm, 0, 0,
                new ActivityOptions.OnAnimationStartedListener() {
                    @Override public void onAnimationStarted() {
                        mThumbnailScaleUpStarted = true;
                        if (!mHighEndGfx) {
                            mPlaceholderThumbnail.setVisibility(INVISIBLE);
                        }
                        if (mHideRecentsAfterThumbnailScaleUpStarted) {
                            hideWindow();
                        }
                    }
                });
        if (ad.taskId >= 0) {
            // This is an active task; it should just go to the foreground.
            am.moveTaskToFront(ad.taskId, ActivityManager.MOVE_TASK_WITH_HOME,
                    opts.toBundle());
=======
        Bundle opts = (bm == null) ?
                null :
                ActivityOptions.makeThumbnailScaleUpAnimation(
                        holder.thumbnailViewImage, bm, 0, 0, null).toBundle();

        show(false);
        if (ad.taskId >= 0) {
            // This is an active task; it should just go to the foreground.
            am.moveTaskToFront(ad.taskId, ActivityManager.MOVE_TASK_WITH_HOME,
                    opts);
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
        } else {
            Intent intent = ad.intent;
            intent.addFlags(Intent.FLAG_ACTIVITY_LAUNCHED_FROM_HISTORY
                    | Intent.FLAG_ACTIVITY_TASK_ON_HOME
                    | Intent.FLAG_ACTIVITY_NEW_TASK);
            if (DEBUG) Log.v(TAG, "Starting activity " + intent);
<<<<<<< HEAD
            context.startActivity(intent, opts.toBundle());
=======
            context.startActivityAsUser(intent, opts,
                    new UserHandle(UserHandle.USER_CURRENT));
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
        }
        if (usingDrawingCache) {
            holder.thumbnailViewImage.setDrawingCacheEnabled(false);
        }
    }

<<<<<<< HEAD
    public void hideWindow() {
        if (!mThumbnailScaleUpStarted) {
            mHideRecentsAfterThumbnailScaleUpStarted = true;
        } else {
            setVisibility(GONE);
            mTransitionBg.setVisibility(INVISIBLE);
            mPlaceholderThumbnail.setVisibility(INVISIBLE);
            mHideRecentsAfterThumbnailScaleUpStarted = false;
        }
    }

=======
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        handleOnClick(view);
    }

    public void handleSwipe(View view) {
        TaskDescription ad = ((ViewHolder) view.getTag()).taskDescription;
        if (ad == null) {
            Log.v(TAG, "Not able to find activity description for swiped task; view=" + view +
                    " tag=" + view.getTag());
            return;
        }
        if (DEBUG) Log.v(TAG, "Jettison " + ad.getLabel());
        mRecentTaskDescriptions.remove(ad);

        // Handled by widget containers to enable LayoutTransitions properly
        // mListAdapter.notifyDataSetChanged();

        if (mRecentTaskDescriptions.size() == 0) {
<<<<<<< HEAD
            hide(false);
=======
            dismissAndGoBack();
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
        }

        // Currently, either direction means the same thing, so ignore direction and remove
        // the task.
        final ActivityManager am = (ActivityManager)
                mContext.getSystemService(Context.ACTIVITY_SERVICE);
        if (am != null) {
            am.removeTask(ad.persistentTaskId, ActivityManager.REMOVE_TASK_KILL_PROCESS);

            // Accessibility feedback
            setContentDescription(
                    mContext.getString(R.string.accessibility_recents_item_dismissed, ad.getLabel()));
            sendAccessibilityEvent(AccessibilityEvent.TYPE_VIEW_SELECTED);
            setContentDescription(null);
        }
    }

    private void startApplicationDetailsActivity(String packageName) {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                Uri.fromParts("package", packageName, null));
<<<<<<< HEAD
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        getContext().startActivity(intent);
=======
        intent.setComponent(intent.resolveActivity(mContext.getPackageManager()));
        TaskStackBuilder.create(getContext())
                .addNextIntentWithParentStack(intent).startActivities();
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
    }

    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (mPopup != null) {
            return true;
        } else {
            return super.onInterceptTouchEvent(ev);
        }
    }

    public void handleLongPress(
            final View selectedView, final View anchorView, final View thumbnailView) {
        thumbnailView.setSelected(true);
        final PopupMenu popup =
            new PopupMenu(mContext, anchorView == null ? selectedView : anchorView);
        mPopup = popup;
        popup.getMenuInflater().inflate(R.menu.recent_popup_menu, popup.getMenu());
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.recent_remove_item) {
                    mRecentsContainer.removeViewInLayout(selectedView);
                } else if (item.getItemId() == R.id.recent_inspect_item) {
                    ViewHolder viewHolder = (ViewHolder) selectedView.getTag();
                    if (viewHolder != null) {
                        final TaskDescription ad = viewHolder.taskDescription;
                        startApplicationDetailsActivity(ad.packageName);
<<<<<<< HEAD
                        mBar.animateCollapse(CommandQueue.FLAG_EXCLUDE_NONE);
=======
                        show(false);
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
                    } else {
                        throw new IllegalStateException("Oops, no tag on view " + selectedView);
                    }
                } else {
                    return false;
                }
                return true;
            }
        });
        popup.setOnDismissListener(new PopupMenu.OnDismissListener() {
            public void onDismiss(PopupMenu menu) {
                thumbnailView.setSelected(false);
                mPopup = null;
            }
        });
        popup.show();
    }
<<<<<<< HEAD

    private void killAllRecentApps(){
        final ActivityManager am = (ActivityManager)
                mContext.getSystemService(Context.ACTIVITY_SERVICE);
        if(!mRecentTaskDescriptions.isEmpty()){
            for(TaskDescription ad : mRecentTaskDescriptions){
                am.removeTask(ad.persistentTaskId, ActivityManager.REMOVE_TASK_KILL_PROCESS);
                // Accessibility feedback
                setContentDescription(
                        mContext.getString(R.string.accessibility_recents_item_dismissed, ad.getLabel()));
                sendAccessibilityEvent(AccessibilityEvent.TYPE_VIEW_SELECTED);
                setContentDescription(null);
            }
            mRecentTaskDescriptions.clear();
        }
        hide(false);
    }
=======
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
}
