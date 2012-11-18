/*
 * Copyright (C) 2009 The Android Open Source Project
<<<<<<< HEAD
 * Copyright (c) 2011, 2012, Code Aurora Forum. All rights reserved.
=======
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
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

package android.webkit;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.SurfaceTexture;
import android.media.MediaPlayer;
import android.net.http.EventHandler;
import android.net.http.Headers;
import android.net.http.RequestHandle;
import android.net.http.RequestQueue;
import android.net.http.SslCertificate;
import android.net.http.SslError;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
<<<<<<< HEAD
=======
import android.view.KeyEvent;
import android.view.View;
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
<<<<<<< HEAD
 * <p>Proxy for HTML5 video views.</p>
=======
 * <p>Proxy for HTML5 video views.
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
 */
class HTML5VideoViewProxy extends Handler
                          implements MediaPlayer.OnPreparedListener,
                          MediaPlayer.OnCompletionListener,
                          MediaPlayer.OnErrorListener,
                          MediaPlayer.OnInfoListener,
<<<<<<< HEAD
                          MediaPlayer.OnVideoSizeChangedListener {
=======
                          SurfaceTexture.OnFrameAvailableListener,
                          View.OnKeyListener {
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
    // Logging tag.
    private static final String LOGTAG = "HTML5VideoViewProxy";

    // Message Ids for WebCore thread -> UI thread communication.
    private static final int PLAY                = 100;
    private static final int SEEK                = 101;
    private static final int PAUSE               = 102;
    private static final int ERROR               = 103;
    private static final int LOAD_DEFAULT_POSTER = 104;
    private static final int BUFFERING_START     = 105;
    private static final int BUFFERING_END       = 106;
<<<<<<< HEAD
    private static final int INIT                = 107;
    private static final int TERM                = 108;
    private static final int SET_VOLUME          = 109;
    private static final int LOAD                = 110;
    private static final int LOAD_METADATA       = 111;
    private static final int ENTER_FULLSCREEN    = 112;
    private static final int EXIT_FULLSCREEN     = 113;
=======
    private static final int ENTER_FULLSCREEN    = 107;
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a

    // Message Ids to be handled on the WebCore thread
    private static final int PREPARED          = 200;
    private static final int ENDED             = 201;
    private static final int POSTER_FETCHED    = 202;
    private static final int PAUSED            = 203;
    private static final int STOPFULLSCREEN    = 204;
<<<<<<< HEAD
    private static final int SIZE_CHANGED      = 205;
    private static final int PLAYING           = 206;
=======
    private static final int RESTORESTATE      = 205;
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a

    // Timer thread -> UI thread
    private static final int TIMEUPDATE = 300;

    // The C++ MediaPlayerPrivateAndroid object.
    int mNativePointer;
    // The handler for WebCore thread messages;
    private Handler mWebCoreHandler;
    // The WebViewClassic instance that created this view.
    private WebViewClassic mWebView;
    // The poster image to be shown when the video is not playing.
    // This ref prevents the bitmap from being GC'ed.
    private Bitmap mPoster;
    // The poster downloader.
    private PosterDownloader mPosterDownloader;
    // The seek position.
    private int mSeekPosition;
<<<<<<< HEAD
    // The video layer ID
    private int mVideoLayerId;

    // A helper class to control the playback. This executes on the UI thread!
    private final class VideoPlayer {
        private HTML5VideoViewProxy mProxy;
        private HTML5VideoView mHTML5VideoView;

        private boolean isVideoSelfEnded = false;
        // The cached volume before HTML5VideoView is initialized.
        // This should be set back to -1.0f every time after the
        // function mHTML5VideoView.setVolume is called.
        private float mCachedVolume = -1.0f;
        // Cached media position used to preserve playback position when
        // resuming suspended video
        private int mCachedPosition;

        private void setPlayerBuffering(boolean playerBuffering) {
            mHTML5VideoView.setPlayerBuffering(playerBuffering);
        }

        VideoPlayer(HTML5VideoViewProxy proxy) {
            mProxy = proxy;
=======
    // A helper class to control the playback. This executes on the UI thread!
    private static final class VideoPlayer {
        // The proxy that is currently playing (if any).
        private static HTML5VideoViewProxy mCurrentProxy;
        // The VideoView instance. This is a singleton for now, at least until
        // http://b/issue?id=1973663 is fixed.
        private static HTML5VideoView mHTML5VideoView;

        private static boolean isVideoSelfEnded = false;

        private static void setPlayerBuffering(boolean playerBuffering) {
            mHTML5VideoView.setPlayerBuffering(playerBuffering);
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
        }

        // Every time webView setBaseLayer, this will be called.
        // When we found the Video layer, then we set the Surface Texture to it.
<<<<<<< HEAD
        // By using the baseLayer and the current video Layer ID, we can
        // identify the exact layer on the UI thread to use the SurfaceTexture.
        // We should never save the base layer handle since its lifetime is not
        // guaranteed outside of the function call from WebView::setBaseLayer.
        //
        // This function allows layer value to be null. If layer is null, only
        // the player state will be set in native code. This allows the proxy to
        // save the player state in the native video layer.
        public void setBaseLayer(int layer) {
            if (mHTML5VideoView != null) {
                int playerState = mHTML5VideoView.getCurrentState();
                if (mHTML5VideoView.getPlayerBuffering())
                    playerState = HTML5VideoView.STATE_BUFFERING;

                nativeSendSurfaceTexture(mHTML5VideoView.getSurfaceTexture(),
                        layer, mVideoLayerId, mHTML5VideoView.getTextureName(),
                        playerState, mNativePointer);

                // Re-attach the inline GL context
                // TODO: Find a better place to call this.
                mHTML5VideoView.attachToInlineGlContextIfNeeded();
            }
        }

        public void suspend() {
            if (mHTML5VideoView != null) {
                mHTML5VideoView.pause();
                mCachedPosition = getCurrentPosition();
                mHTML5VideoView.release();
                // Call setBaseLayer to update VideoLayerAndroid player state
                // This is important for flagging the associated texture for recycling
                setBaseLayer(0);
                mHTML5VideoView = null;
                // isVideoSelfEnded is false when video playback
                // has ended but is not complete.
                // isVideoSelfEnded is true only when playback is complete.
                isVideoSelfEnded = false;
                end();
            }
        }

        public void enterFullscreenVideo(String url, float x, float y, float w, float h) {
            if (ensureHTML5VideoView(url, mCachedPosition, false)) {
                mHTML5VideoView.prepareDataAndDisplayMode();
            }
            mHTML5VideoView.enterFullscreenVideoState(mWebView, x, y, w, h);
        }

        public void exitFullscreenVideo(float x, float y, float w, float h) {
            if (mHTML5VideoView != null) {
                mHTML5VideoView.exitFullscreenVideoState(x, y, w, h);
            }
        }

        public void webkitExitFullscreenVideo() {
            if (!mHTML5VideoView.fullscreenExited() && mHTML5VideoView.isFullscreenMode()) {
                WebChromeClient client = mWebView.getWebChromeClient();
=======
        // Otherwise, we may want to delete the Surface Texture to save memory.
        public static void setBaseLayer(int layer) {
            // Don't do this for full screen mode.
            if (mHTML5VideoView != null
                && !mHTML5VideoView.isFullScreenMode()
                && !mHTML5VideoView.isReleased()) {
                int currentVideoLayerId = mHTML5VideoView.getVideoLayerId();
                SurfaceTexture surfTexture =
                        HTML5VideoInline.getSurfaceTexture(currentVideoLayerId);
                int textureName = mHTML5VideoView.getTextureName();

                if (layer != 0 && surfTexture != null && currentVideoLayerId != -1) {
                    int playerState = mHTML5VideoView.getCurrentState();
                    if (mHTML5VideoView.getPlayerBuffering())
                        playerState = HTML5VideoView.STATE_PREPARING;
                    boolean foundInTree = nativeSendSurfaceTexture(surfTexture,
                            layer, currentVideoLayerId, textureName,
                            playerState);
                    if (playerState >= HTML5VideoView.STATE_PREPARED
                            && !foundInTree) {
                        mHTML5VideoView.pauseAndDispatch(mCurrentProxy);
                    }
                }
            }
        }

        // When a WebView is paused, we also want to pause the video in it.
        public static void pauseAndDispatch() {
            if (mHTML5VideoView != null) {
                mHTML5VideoView.pauseAndDispatch(mCurrentProxy);
            }
        }

        public static void enterFullScreenVideo(int layerId, String url,
                HTML5VideoViewProxy proxy, WebViewClassic webView) {
                // Save the inline video info and inherit it in the full screen
                int savePosition = 0;
                boolean canSkipPrepare = false;
                boolean forceStart = false;
                if (mHTML5VideoView != null) {
                    // We don't allow enter full screen mode while the previous
                    // full screen video hasn't finished yet.
                    if (!mHTML5VideoView.fullScreenExited() && mHTML5VideoView.isFullScreenMode()) {
                        Log.w(LOGTAG, "Try to reenter the full screen mode");
                        return;
                    }
                    int playerState = mHTML5VideoView.getCurrentState();
                    // If we are playing the same video, then it is better to
                    // save the current position.
                    if (layerId == mHTML5VideoView.getVideoLayerId()) {
                        savePosition = mHTML5VideoView.getCurrentPosition();
                        canSkipPrepare = (playerState == HTML5VideoView.STATE_PREPARING
                                || playerState == HTML5VideoView.STATE_PREPARED
                                || playerState == HTML5VideoView.STATE_PLAYING)
                                && !mHTML5VideoView.isFullScreenMode();
                    }
                    if (!canSkipPrepare) {
                        mHTML5VideoView.reset();
                    } else {
                        forceStart = playerState == HTML5VideoView.STATE_PREPARING
                                || playerState == HTML5VideoView.STATE_PLAYING;
                    }
                }
                mHTML5VideoView = new HTML5VideoFullScreen(proxy.getContext(),
                        layerId, savePosition, canSkipPrepare);
                mHTML5VideoView.setStartWhenPrepared(forceStart);
                mCurrentProxy = proxy;
                mHTML5VideoView.setVideoURI(url, mCurrentProxy);
                mHTML5VideoView.enterFullScreenVideoState(layerId, proxy, webView);
        }

        public static void exitFullScreenVideo(HTML5VideoViewProxy proxy,
                WebViewClassic webView) {
            if (!mHTML5VideoView.fullScreenExited() && mHTML5VideoView.isFullScreenMode()) {
                WebChromeClient client = webView.getWebChromeClient();
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
                if (client != null) {
                    client.onHideCustomView();
                }
            }
        }

        // This is on the UI thread.
<<<<<<< HEAD
        public void loadMetadata(String url) {
            if (ensureHTML5VideoView(url, 0, false)) {
                mHTML5VideoView.retrieveMetadata(mProxy);
            }
        }

        public void load(String url) {
            if (ensureHTML5VideoView(url, 0, false)) {
                mHTML5VideoView.prepareDataAndDisplayMode();
            }
        }

        public void play(String url, int time) {
            if (ensureHTML5VideoView(url, time, true)) {
                mHTML5VideoView.prepareDataAndDisplayMode();
                mHTML5VideoView.seekTo(time);
            } else {
                // Here, we handle the case when we keep playing with one video
                if (!mHTML5VideoView.isPlaying()) {
                    mHTML5VideoView.start();
                    setBaseLayer(0);
                }
            }
        }

        public boolean isPlaying() {
            return (mHTML5VideoView != null && mHTML5VideoView.isPlaying());
        }

        public int getCurrentPosition() {
=======
        // When native tell Java to play, we need to check whether or not it is
        // still the same video by using videoLayerId and treat it differently.
        public static void play(String url, int time, HTML5VideoViewProxy proxy,
                WebChromeClient client, int videoLayerId) {
            int currentVideoLayerId = -1;
            boolean backFromFullScreenMode = false;
            if (mHTML5VideoView != null) {
                currentVideoLayerId = mHTML5VideoView.getVideoLayerId();
                backFromFullScreenMode = mHTML5VideoView.fullScreenExited();

                // When playing video back to back in full screen mode,
                // javascript will switch the src and call play.
                // In this case, we can just reuse the same full screen view,
                // and play the video after prepared.
                if (mHTML5VideoView.isFullScreenMode()
                    && !backFromFullScreenMode
                    && currentVideoLayerId != videoLayerId
                    && mCurrentProxy != proxy) {
                    mCurrentProxy = proxy;
                    mHTML5VideoView.setStartWhenPrepared(true);
                    mHTML5VideoView.setVideoURI(url, proxy);
                    mHTML5VideoView.reprepareData(proxy);
                    return;
                }
            }

            boolean skipPrepare = false;
            boolean createInlineView = false;
            if (backFromFullScreenMode
                && currentVideoLayerId == videoLayerId
                && !mHTML5VideoView.isReleased()) {
                skipPrepare = true;
                createInlineView = true;
            } else if(backFromFullScreenMode
                || currentVideoLayerId != videoLayerId
                || HTML5VideoInline.surfaceTextureDeleted()) {
                // Here, we handle the case when switching to a new video,
                // either inside a WebView or across WebViews
                // For switching videos within a WebView or across the WebView,
                // we need to pause the old one and re-create a new media player
                // inside the HTML5VideoView.
                if (mHTML5VideoView != null) {
                    if (!backFromFullScreenMode) {
                        mHTML5VideoView.pauseAndDispatch(mCurrentProxy);
                    }
                    mHTML5VideoView.reset();
                }
                createInlineView = true;
            }
            if (createInlineView) {
                mCurrentProxy = proxy;
                mHTML5VideoView = new HTML5VideoInline(videoLayerId, time, skipPrepare);

                mHTML5VideoView.setVideoURI(url, mCurrentProxy);
                mHTML5VideoView.prepareDataAndDisplayMode(proxy);
                return;
            }

            if (mCurrentProxy == proxy) {
                // Here, we handle the case when we keep playing with one video
                if (!mHTML5VideoView.isPlaying()) {
                    mHTML5VideoView.seekTo(time);
                    mHTML5VideoView.start();
                }
            } else if (mCurrentProxy != null) {
                // Some other video is already playing. Notify the caller that
                // its playback ended.
                proxy.dispatchOnEnded();
            }
        }

        public static boolean isPlaying(HTML5VideoViewProxy proxy) {
            return (mCurrentProxy == proxy && mHTML5VideoView != null
                    && mHTML5VideoView.isPlaying());
        }

        public static int getCurrentPosition() {
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
            int currentPosMs = 0;
            if (mHTML5VideoView != null) {
                currentPosMs = mHTML5VideoView.getCurrentPosition();
            }
            return currentPosMs;
        }

<<<<<<< HEAD
        public void seek(int time) {
            if (time >= 0 && mHTML5VideoView != null) {
=======
        public static void seek(int time, HTML5VideoViewProxy proxy) {
            if (mCurrentProxy == proxy && time >= 0 && mHTML5VideoView != null) {
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
                mHTML5VideoView.seekTo(time);
            }
        }

<<<<<<< HEAD
        public void pause() {
            if (mHTML5VideoView != null) {
=======
        public static void pause(HTML5VideoViewProxy proxy) {
            if (mCurrentProxy == proxy && mHTML5VideoView != null) {
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
                mHTML5VideoView.pause();
            }
        }

<<<<<<< HEAD
        public void onPrepared() {
            if (mCachedVolume >= 0.0f) {
                mHTML5VideoView.setVolume(mCachedVolume);
                mCachedVolume = -1.0f;
            }
            setBaseLayer(0);
        }

        public void end() {
            if (mHTML5VideoView != null)
                mHTML5VideoView.showControllerInFullscreen();
            if (mProxy != null) {
                if (isVideoSelfEnded)
                    mProxy.dispatchOnEnded();
                else
                    mProxy.dispatchOnPaused();
            }
            isVideoSelfEnded = false;
        }

        public void setVolume(float volume) {
            if (mHTML5VideoView != null) {
                mHTML5VideoView.setVolume(volume);
                mCachedVolume = -1.0f;
            } else {
                mCachedVolume = volume;
            }
        }

        // Return true if we have to allocate a new HTML5VideoView.
        // Otherwise return false and we can reuse the previously allocated HTML5VideoView
        private boolean ensureHTML5VideoView(String url, int time, boolean willPlay) {
            if (mHTML5VideoView == null) {
                mHTML5VideoView = new HTML5VideoView(mProxy, time);
                mHTML5VideoView.setStartWhenPrepared(willPlay);
                mHTML5VideoView.setVideoURI(url);
                return true;
            }
            return false;
        }

        public boolean isPrepared() {
            return mHTML5VideoView.getCurrentState() >= HTML5VideoView.STATE_PREPARED;
        }
    }
    private VideoPlayer mVideoPlayer;

    // A bunch event listeners for our VideoView
    // MediaPlayer.OnPreparedListener
    public void onPrepared(MediaPlayer mp) {
        mVideoPlayer.onPrepared();
=======
        public static void onPrepared() {
            if (!mHTML5VideoView.isFullScreenMode()) {
                mHTML5VideoView.start();
            }
        }

        public static void end() {
            mHTML5VideoView.showControllerInFullScreen();
            if (mCurrentProxy != null) {
                if (isVideoSelfEnded)
                    mCurrentProxy.dispatchOnEnded();
                else
                    mCurrentProxy.dispatchOnPaused();
            }
            isVideoSelfEnded = false;
        }
    }

    // A bunch event listeners for our VideoView
    // MediaPlayer.OnPreparedListener
    @Override
    public void onPrepared(MediaPlayer mp) {
        VideoPlayer.onPrepared();
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
        Message msg = Message.obtain(mWebCoreHandler, PREPARED);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("dur", new Integer(mp.getDuration()));
        map.put("width", new Integer(mp.getVideoWidth()));
        map.put("height", new Integer(mp.getVideoHeight()));
        msg.obj = map;
        mWebCoreHandler.sendMessage(msg);
    }

<<<<<<< HEAD
    //MediaPlayer.OnVideoSizeChangedListener
    public void onVideoSizeChanged(MediaPlayer mp, int width, int height) {
        Message msg = Message.obtain(mWebCoreHandler, SIZE_CHANGED);
        Map<String, Object> map = new HashMap<String, Object>();
        if (mVideoPlayer.isPrepared())
            map.put("dur", new Integer(mp.getDuration()));
        else
            map.put("dur", new Integer(0));
        map.put("width", new Integer(width));
        map.put("height", new Integer(height));
        msg.obj = map;
        mWebCoreHandler.sendMessage(msg);
    }

    // MediaPlayer.OnCompletionListener;
=======
    // MediaPlayer.OnCompletionListener;
    @Override
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
    public void onCompletion(MediaPlayer mp) {
        // The video ended by itself, so we need to
        // send a message to the UI thread to dismiss
        // the video view and to return to the WebView.
        // arg1 == 1 means the video ends by itself.
        sendMessage(obtainMessage(ENDED, 1, 0));
    }

    // MediaPlayer.OnErrorListener
<<<<<<< HEAD
=======
    @Override
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
    public boolean onError(MediaPlayer mp, int what, int extra) {
        sendMessage(obtainMessage(ERROR));
        return false;
    }

    public void dispatchOnEnded() {
        Message msg = Message.obtain(mWebCoreHandler, ENDED);
        mWebCoreHandler.sendMessage(msg);
    }

    public void dispatchOnPaused() {
        Message msg = Message.obtain(mWebCoreHandler, PAUSED);
        mWebCoreHandler.sendMessage(msg);
    }

<<<<<<< HEAD
    public void dispatchOnPlaying() {
        Message msg = Message.obtain(mWebCoreHandler, PLAYING);
        mWebCoreHandler.sendMessage(msg);
    }

    public void dispatchOnStopFullscreen() {
        Message msg = Message.obtain(mWebCoreHandler, STOPFULLSCREEN);
        mWebCoreHandler.sendMessage(msg);
    }

    public void updateSizeAndDuration(int width, int height, int duration) {
        Message msg = Message.obtain(mWebCoreHandler, SIZE_CHANGED);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("dur", new Integer(duration));
        map.put("width", new Integer(width));
        map.put("height", new Integer(height));
        msg.obj = map;
=======
    public void dispatchOnStopFullScreen(boolean stillPlaying) {
        Message msg = Message.obtain(mWebCoreHandler, STOPFULLSCREEN);
        msg.arg1 = stillPlaying ? 1 : 0;
        mWebCoreHandler.sendMessage(msg);
    }

    public void dispatchOnRestoreState() {
        Message msg = Message.obtain(mWebCoreHandler, RESTORESTATE);
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
        mWebCoreHandler.sendMessage(msg);
    }

    public void onTimeupdate() {
        sendMessage(obtainMessage(TIMEUPDATE));
    }

<<<<<<< HEAD
=======
    // When there is a frame ready from surface texture, we should tell WebView
    // to refresh.
    @Override
    public void onFrameAvailable(SurfaceTexture surfaceTexture) {
        // TODO: This should support partial invalidation too.
        mWebView.invalidate();
    }

>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
    // Handler for the messages from WebCore or Timer thread to the UI thread.
    @Override
    public void handleMessage(Message msg) {
        // This executes on the UI thread.
        switch (msg.what) {
            case PLAY: {
                String url = (String) msg.obj;
<<<<<<< HEAD
                int seekPosition = msg.arg1;
                mVideoPlayer.play(url, seekPosition);
                break;
            }
            case LOAD_METADATA: {
                String url = (String) msg.obj;
                mVideoPlayer.loadMetadata(url);
                break;
            }
            case LOAD: {
                String url = (String) msg.obj;
                mVideoPlayer.load(url);
=======
                WebChromeClient client = mWebView.getWebChromeClient();
                int videoLayerID = msg.arg1;
                if (client != null) {
                    VideoPlayer.play(url, mSeekPosition, this, client, videoLayerID);
                }
                break;
            }
            case ENTER_FULLSCREEN:{
                String url = (String) msg.obj;
                WebChromeClient client = mWebView.getWebChromeClient();
                int videoLayerID = msg.arg1;
                if (client != null) {
                    VideoPlayer.enterFullScreenVideo(videoLayerID, url, this, mWebView);
                }
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
                break;
            }
            case SEEK: {
                Integer time = (Integer) msg.obj;
                mSeekPosition = time;
<<<<<<< HEAD
                mVideoPlayer.seek(mSeekPosition);
                break;
            }
            case PAUSE: {
                mVideoPlayer.pause();
=======
                VideoPlayer.seek(mSeekPosition, this);
                break;
            }
            case PAUSE: {
                VideoPlayer.pause(this);
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
                break;
            }
            case ENDED:
                if (msg.arg1 == 1)
<<<<<<< HEAD
                    mVideoPlayer.isVideoSelfEnded = true;
                mVideoPlayer.end();
=======
                    VideoPlayer.isVideoSelfEnded = true;
                VideoPlayer.end();
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
                break;
            case ERROR: {
                WebChromeClient client = mWebView.getWebChromeClient();
                if (client != null) {
                    client.onHideCustomView();
                }
                break;
            }
            case LOAD_DEFAULT_POSTER: {
                WebChromeClient client = mWebView.getWebChromeClient();
                if (client != null) {
                    doSetPoster(client.getDefaultVideoPoster());
                }
                break;
            }
            case TIMEUPDATE: {
<<<<<<< HEAD
                if (mVideoPlayer.isPlaying()) {
=======
                if (VideoPlayer.isPlaying(this)) {
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
                    sendTimeupdate();
                }
                break;
            }
            case BUFFERING_START: {
<<<<<<< HEAD
                mVideoPlayer.setPlayerBuffering(true);
                break;
            }
            case BUFFERING_END: {
                mVideoPlayer.setPlayerBuffering(false);
                break;
            }
            case INIT: {
                // Pass Proxy into webview, such that every time we have a setBaseLayer
                // call, we tell this Proxy to call the native to update the layer tree
                // for the Video Layer's surface texture info
                mWebView.registerHTML5VideoViewProxy(this);
                break;
            }
            case TERM: {
                mVideoPlayer.suspend();
                mWebView.unregisterHTML5VideoViewProxy(this);
                break;
            }
            case SET_VOLUME: {
                float vol = ((Float)msg.obj).floatValue();
                mVideoPlayer.setVolume(vol);
                break;
            }
            case ENTER_FULLSCREEN: {
                InlineVideoInfo info = (InlineVideoInfo)msg.obj;
                mVideoPlayer.enterFullscreenVideo(info.getUrl(),
                        info.getX(), info.getY(), info.getWidth(), info.getHeight());
                break;
            }
            case EXIT_FULLSCREEN: {
                InlineVideoInfo info = (InlineVideoInfo)msg.obj;
                mVideoPlayer.exitFullscreenVideo(info.getX(), info.getY(),
                        info.getWidth(), info.getHeight());
=======
                VideoPlayer.setPlayerBuffering(true);
                break;
            }
            case BUFFERING_END: {
                VideoPlayer.setPlayerBuffering(false);
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
                break;
            }
        }
    }

    // Everything below this comment executes on the WebCore thread, except for
    // the EventHandler methods, which are called on the network thread.

    // A helper class that knows how to download posters
    private static final class PosterDownloader implements EventHandler {
        // The request queue. This is static as we have one queue for all posters.
        private static RequestQueue mRequestQueue;
        private static int mQueueRefCount = 0;
        // The poster URL
        private URL mUrl;
        // The proxy we're doing this for.
        private final HTML5VideoViewProxy mProxy;
        // The poster bytes. We only touch this on the network thread.
        private ByteArrayOutputStream mPosterBytes;
        // The request handle. We only touch this on the WebCore thread.
        private RequestHandle mRequestHandle;
        // The response status code.
        private int mStatusCode;
        // The response headers.
        private Headers mHeaders;
        // The handler to handle messages on the WebCore thread.
        private Handler mHandler;

        public PosterDownloader(String url, HTML5VideoViewProxy proxy) {
            try {
                mUrl = new URL(url);
            } catch (MalformedURLException e) {
                mUrl = null;
            }
            mProxy = proxy;
            mHandler = new Handler();
        }
        // Start the download. Called on WebCore thread.
        public void start() {
            retainQueue();

            if (mUrl == null) {
                return;
            }

            // Only support downloading posters over http/https.
            // FIXME: Add support for other schemes. WebKit seems able to load
            // posters over other schemes e.g. file://, but gets the dimensions wrong.
            String protocol = mUrl.getProtocol();
            if ("http".equals(protocol) || "https".equals(protocol)) {
                mRequestHandle = mRequestQueue.queueRequest(mUrl.toString(), "GET", null,
                        this, null, 0);
            }
        }
        // Cancel the download if active and release the queue. Called on WebCore thread.
        public void cancelAndReleaseQueue() {
            if (mRequestHandle != null) {
                mRequestHandle.cancel();
                mRequestHandle = null;
            }
            releaseQueue();
        }
        // EventHandler methods. Executed on the network thread.
<<<<<<< HEAD
=======
        @Override
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
        public void status(int major_version,
                int minor_version,
                int code,
                String reason_phrase) {
            mStatusCode = code;
        }

<<<<<<< HEAD
=======
        @Override
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
        public void headers(Headers headers) {
            mHeaders = headers;
        }

<<<<<<< HEAD
=======
        @Override
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
        public void data(byte[] data, int len) {
            if (mPosterBytes == null) {
                mPosterBytes = new ByteArrayOutputStream();
            }
            mPosterBytes.write(data, 0, len);
        }

<<<<<<< HEAD
=======
        @Override
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
        public void endData() {
            if (mStatusCode == 200) {
                if (mPosterBytes.size() > 0) {
                    Bitmap poster = BitmapFactory.decodeByteArray(
                            mPosterBytes.toByteArray(), 0, mPosterBytes.size());
                    mProxy.doSetPoster(poster);
                }
                cleanup();
            } else if (mStatusCode >= 300 && mStatusCode < 400) {
                // We have a redirect.
                try {
                    mUrl = new URL(mHeaders.getLocation());
                } catch (MalformedURLException e) {
                    mUrl = null;
                }
                if (mUrl != null) {
                    mHandler.post(new Runnable() {
<<<<<<< HEAD
=======
                       @Override
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
                       public void run() {
                           if (mRequestHandle != null) {
                               mRequestHandle.setupRedirect(mUrl.toString(), mStatusCode,
                                       new HashMap<String, String>());
                           }
                       }
                    });
                }
            }
        }

<<<<<<< HEAD
=======
        @Override
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
        public void certificate(SslCertificate certificate) {
            // Don't care.
        }

<<<<<<< HEAD
=======
        @Override
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
        public void error(int id, String description) {
            cleanup();
        }

<<<<<<< HEAD
=======
        @Override
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
        public boolean handleSslErrorRequest(SslError error) {
            // Don't care. If this happens, data() will never be called so
            // mPosterBytes will never be created, so no need to call cleanup.
            return false;
        }
        // Tears down the poster bytes stream. Called on network thread.
        private void cleanup() {
            if (mPosterBytes != null) {
                try {
                    mPosterBytes.close();
                } catch (IOException ignored) {
                    // Ignored.
                } finally {
                    mPosterBytes = null;
                }
            }
        }

        // Queue management methods. Called on WebCore thread.
        private void retainQueue() {
            if (mRequestQueue == null) {
                mRequestQueue = new RequestQueue(mProxy.getContext());
            }
            mQueueRefCount++;
        }

        private void releaseQueue() {
            if (mQueueRefCount == 0) {
                return;
            }
            if (--mQueueRefCount == 0) {
                mRequestQueue.shutdown();
                mRequestQueue = null;
            }
        }
    }

    /**
     * Private constructor.
     * @param webView is the WebView that hosts the video.
     * @param nativePtr is the C++ pointer to the MediaPlayerPrivate object.
     */
<<<<<<< HEAD
    private HTML5VideoViewProxy(WebViewClassic webView, int nativePtr, int videoLayerId) {
=======
    private HTML5VideoViewProxy(WebViewClassic webView, int nativePtr) {
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
        // This handler is for the main (UI) thread.
        super(Looper.getMainLooper());
        // Save the WebView object.
        mWebView = webView;
<<<<<<< HEAD
        // Save the native ptr
        mNativePointer = nativePtr;
        // Save the videoLayerId. This is needed early in order to support fullscreen mode
        // before video playback
        mVideoLayerId = videoLayerId;
        // create the message handler for this thread
        createWebCoreHandler();
        mVideoPlayer = new VideoPlayer(this);
        Message message = obtainMessage(INIT);
        sendMessage(message);
=======
        // Pass Proxy into webview, such that every time we have a setBaseLayer
        // call, we tell this Proxy to call the native to update the layer tree
        // for the Video Layer's surface texture info
        mWebView.setHTML5VideoViewProxy(this);
        // Save the native ptr
        mNativePointer = nativePtr;
        // create the message handler for this thread
        createWebCoreHandler();
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
    }

    private void createWebCoreHandler() {
        mWebCoreHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case PREPARED: {
                        Map<String, Object> map = (Map<String, Object>) msg.obj;
                        Integer duration = (Integer) map.get("dur");
                        Integer width = (Integer) map.get("width");
                        Integer height = (Integer) map.get("height");
                        nativeOnPrepared(duration.intValue(), width.intValue(),
                                height.intValue(), mNativePointer);
                        break;
                    }
<<<<<<< HEAD
                    case SIZE_CHANGED: {
                        Map<String, Object> map = (Map<String, Object>) msg.obj;
                        Integer duration = (Integer) map.get("dur");
                        Integer width = (Integer) map.get("width");
                        Integer height = (Integer) map.get("height");
                        nativeOnSizeChanged(duration.intValue(), width.intValue(),
                                height.intValue(), mNativePointer);
                        break;
                    }
=======
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
                    case ENDED:
                        mSeekPosition = 0;
                        nativeOnEnded(mNativePointer);
                        break;
                    case PAUSED:
                        nativeOnPaused(mNativePointer);
                        break;
<<<<<<< HEAD
                    case PLAYING:
                        nativeOnPlaying(mNativePointer);
                        break;
=======
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
                    case POSTER_FETCHED:
                        Bitmap poster = (Bitmap) msg.obj;
                        nativeOnPosterFetched(poster, mNativePointer);
                        break;
                    case TIMEUPDATE:
                        nativeOnTimeupdate(msg.arg1, mNativePointer);
                        break;
                    case STOPFULLSCREEN:
<<<<<<< HEAD
                        nativeOnStopFullscreen(mNativePointer);
=======
                        nativeOnStopFullscreen(msg.arg1, mNativePointer);
                        break;
                    case RESTORESTATE:
                        nativeOnRestoreState(mNativePointer);
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
                        break;
                }
            }
        };
    }

    private void doSetPoster(Bitmap poster) {
        if (poster == null) {
            return;
        }
        // Save a ref to the bitmap and send it over to the WebCore thread.
        mPoster = poster;
        Message msg = Message.obtain(mWebCoreHandler, POSTER_FETCHED);
        msg.obj = poster;
        mWebCoreHandler.sendMessage(msg);
    }

    private void sendTimeupdate() {
        Message msg = Message.obtain(mWebCoreHandler, TIMEUPDATE);
<<<<<<< HEAD
        msg.arg1 = mVideoPlayer.getCurrentPosition();
=======
        msg.arg1 = VideoPlayer.getCurrentPosition();
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
        mWebCoreHandler.sendMessage(msg);
    }

    public Context getContext() {
        return mWebView.getContext();
    }

    // The public methods below are all called from WebKit only.
    /**
     * Play a video stream.
     * @param url is the URL of the video stream.
     */
<<<<<<< HEAD
    public void play(String url, int position) {
        if (url == null) {
            return;
        }
        Message message = obtainMessage(PLAY);
        message.arg1 = position;
        message.obj = url;
        sendMessage(message);
    }

    /**
     * Load a video stream.
     * @param url is the URL of the video stream.
     */
    public void loadVideo(String url) {
        if (url == null) {
            return;
        }
        Message message = obtainMessage(LOAD);
=======
    public void play(String url, int position, int videoLayerID) {
        if (url == null) {
            return;
        }

        if (position > 0) {
            seek(position);
        }
        Message message = obtainMessage(PLAY);
        message.arg1 = videoLayerID;
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
        message.obj = url;
        sendMessage(message);
    }

    /**
<<<<<<< HEAD
     * Load video metadata.
     * @param url is the URL of the video stream.
     */
    public void loadMetadata(String url) {
        if (url == null) {
            return;
        }
        Message message = obtainMessage(LOAD_METADATA);
=======
     * Play a video stream in full screen mode.
     * @param url is the URL of the video stream.
     */
    public void enterFullscreenForVideoLayer(String url, int videoLayerID) {
        if (url == null) {
            return;
        }

        Message message = obtainMessage(ENTER_FULLSCREEN);
        message.arg1 = videoLayerID;
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
        message.obj = url;
        sendMessage(message);
    }

    /**
     * Seek into the video stream.
     * @param  time is the position in the video stream.
     */
    public void seek(int time) {
        Message message = obtainMessage(SEEK);
        message.obj = new Integer(time);
        sendMessage(message);
    }

    /**
     * Pause the playback.
     */
    public void pause() {
        Message message = obtainMessage(PAUSE);
        sendMessage(message);
    }

    /**
     * Tear down this proxy object.
     */
    public void teardown() {
        // This is called by the C++ MediaPlayerPrivate dtor.
        // Cancel any active poster download.
        if (mPosterDownloader != null) {
            mPosterDownloader.cancelAndReleaseQueue();
        }
<<<<<<< HEAD
        Message message = obtainMessage(TERM);
        sendMessage(message);
=======
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
        mNativePointer = 0;
    }

    /**
     * Load the poster image.
     * @param url is the URL of the poster image.
     */
    public void loadPoster(String url) {
        if (url == null) {
            Message message = obtainMessage(LOAD_DEFAULT_POSTER);
            sendMessage(message);
            return;
        }
        // Cancel any active poster download.
        if (mPosterDownloader != null) {
            mPosterDownloader.cancelAndReleaseQueue();
        }
        // Load the poster asynchronously
        mPosterDownloader = new PosterDownloader(url, this);
        mPosterDownloader.start();
    }

<<<<<<< HEAD
    public void enterFullscreen(String url, float x, float y, float w, float h) {
        if (url == null)
            return;
        Message message = obtainMessage(ENTER_FULLSCREEN);
        message.obj = new InlineVideoInfo(url, x, y, w, h);
        sendMessage(message);
    }

    public void exitFullscreen(float x, float y, float w, float h) {
        Message message = obtainMessage(EXIT_FULLSCREEN);
        message.obj = new InlineVideoInfo(null, x, y, w, h);
        sendMessage(message);
    }

    private static final class InlineVideoInfo {
        private String mUrl;
        private float mX;
        private float mY;
        private float mWidth;
        private float mHeight;

        public InlineVideoInfo(String url, float x, float y, float w, float h) {
            mUrl = url;
            mX = x;
            mY = y;
            mWidth = w;
            mHeight = h;
        }

        public String getUrl() {
            return mUrl;
        }

        public float getX() {
            return mX;
        }

        public float getY() {
            return mY;
        }

        public float getWidth() {
            return mWidth;
        }

        public float getHeight() {
            return mHeight;
        }
    }

    // These functions are called from UI thread only by WebView.
    public void setBaseLayer(int layer) {
        mVideoPlayer.setBaseLayer(layer);
    }

    public void pauseAndDispatch() {
        // mVideoPlayer.pause will always dispatch notification
        mVideoPlayer.pause();
    }

    public void suspend() {
        mVideoPlayer.suspend();
    }

    public void webkitEnterFullscreen() {
        nativePrepareEnterFullscreen(mNativePointer);
    }

    public void prepareExitFullscreen() {
        nativePrepareExitFullscreen(mNativePointer);
    }

    public void webKitExitFullscreen() {
        mVideoPlayer.webkitExitFullscreenVideo();
    }

    public int getVideoLayerId() {
        return mVideoLayerId;
    }
    // End functions called from UI thread only by WebView

    /**
     * Change the volume of the playback
     */
    public void setVolume(float volume) {
        Message message = obtainMessage(SET_VOLUME);
        message.obj = new Float(volume);
        sendMessage(message);
=======
    // These three function are called from UI thread only by WebView.
    public void setBaseLayer(int layer) {
        VideoPlayer.setBaseLayer(layer);
    }

    public void pauseAndDispatch() {
        VideoPlayer.pauseAndDispatch();
    }

    public void enterFullScreenVideo(int layerId, String url) {
        VideoPlayer.enterFullScreenVideo(layerId, url, this, mWebView);
    }

    public void exitFullScreenVideo() {
        VideoPlayer.exitFullScreenVideo(this, mWebView);
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
    }

    /**
     * The factory for HTML5VideoViewProxy instances.
     * @param webViewCore is the WebViewCore that is requesting the proxy.
     *
     * @return a new HTML5VideoViewProxy object.
     */
<<<<<<< HEAD
    public static HTML5VideoViewProxy getInstance(WebViewCore webViewCore, int nativePtr, int videoLayerId) {
        return new HTML5VideoViewProxy(webViewCore.getWebViewClassic(), nativePtr, videoLayerId);
=======
    public static HTML5VideoViewProxy getInstance(WebViewCore webViewCore, int nativePtr) {
        return new HTML5VideoViewProxy(webViewCore.getWebViewClassic(), nativePtr);
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
    }

    /* package */ WebViewClassic getWebView() {
        return mWebView;
    }

    private native void nativeOnPrepared(int duration, int width, int height, int nativePointer);
<<<<<<< HEAD
    private native void nativeOnSizeChanged(int duration, int width, int height, int nativePointer);
    private native void nativeOnEnded(int nativePointer);
    private native void nativeOnPaused(int nativePointer);
    private native void nativeOnPlaying(int nativePointer);
    private native void nativeOnPosterFetched(Bitmap poster, int nativePointer);
    private native void nativeOnTimeupdate(int position, int nativePointer);
    private native void nativeOnStopFullscreen(int nativePointer);
    private native static boolean nativeSendSurfaceTexture(SurfaceTexture texture,
            int baseLayer, int videoLayerId, int textureName,
            int playerState, int nativePointer);
    private native void nativePrepareEnterFullscreen(int nativePointer);
    private native void nativePrepareExitFullscreen(int nativePoint);
=======
    private native void nativeOnEnded(int nativePointer);
    private native void nativeOnPaused(int nativePointer);
    private native void nativeOnPosterFetched(Bitmap poster, int nativePointer);
    private native void nativeOnTimeupdate(int position, int nativePointer);
    private native void nativeOnStopFullscreen(int stillPlaying, int nativePointer);
    private native void nativeOnRestoreState(int nativePointer);
    private native static boolean nativeSendSurfaceTexture(SurfaceTexture texture,
            int baseLayer, int videoLayerId, int textureName,
            int playerState);
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a

    @Override
    public boolean onInfo(MediaPlayer mp, int what, int extra) {
        if (what == MediaPlayer.MEDIA_INFO_BUFFERING_START) {
            sendMessage(obtainMessage(BUFFERING_START, what, extra));
        } else if (what == MediaPlayer.MEDIA_INFO_BUFFERING_END) {
            sendMessage(obtainMessage(BUFFERING_END, what, extra));
        }
        return false;
    }
<<<<<<< HEAD
=======

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (event.getAction() == KeyEvent.ACTION_DOWN) {
                return true;
            } else if (event.getAction() == KeyEvent.ACTION_UP && !event.isCanceled()) {
                VideoPlayer.exitFullScreenVideo(this, mWebView);
                return true;
            }
        }
        return false;
    }
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
}
