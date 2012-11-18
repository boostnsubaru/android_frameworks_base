/*
 * Copyright (C) 2007 The Android Open Source Project
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

package android.view;

<<<<<<< HEAD
import android.content.res.CompatibilityInfo.Translator;
import android.graphics.*;
=======
import dalvik.system.CloseGuard;

import android.content.res.CompatibilityInfo.Translator;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.Region;
import android.graphics.SurfaceTexture;
import android.os.IBinder;
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
import android.os.Parcelable;
import android.os.Parcel;
import android.os.SystemProperties;
import android.util.Log;

/**
 * Handle onto a raw buffer that is being managed by the screen compositor.
 */
public class Surface implements Parcelable {
<<<<<<< HEAD
    private static final String LOG_TAG = "Surface";
    private static final boolean DEBUG_RELEASE = false;
    
    /* orientations for setOrientation() */
    public static final int ROTATION_0       = 0;
    public static final int ROTATION_90      = 1;
    public static final int ROTATION_180     = 2;
    public static final int ROTATION_270     = 3;

    private static final boolean headless = "1".equals(
        SystemProperties.get("ro.config.headless", "0"));

    private static void checkHeadless() {
        if(headless) {
            throw new UnsupportedOperationException("Device is headless");
        }
    }

    /**
     * Create Surface from a {@link SurfaceTexture}.
     *
     * Images drawn to the Surface will be made available to the {@link
     * SurfaceTexture}, which can attach them an OpenGL ES texture via {@link
     * SurfaceTexture#updateTexImage}.
     *
     * @param surfaceTexture The {@link SurfaceTexture} that is updated by this
     * Surface.
     */
    public Surface(SurfaceTexture surfaceTexture) {
        checkHeadless();

        if (DEBUG_RELEASE) {
            mCreationStack = new Exception();
        }
        mCanvas = new CompatibleCanvas();
        initFromSurfaceTexture(surfaceTexture);
    }

    /**
     * Does this object hold a valid surface?  Returns true if it holds
     * a physical surface, so lockCanvas() will succeed.  Otherwise
     * returns false.
     */
    public native   boolean isValid();

    /** Release the local reference to the server-side surface.  
     * Always call release() when you're done with a Surface. This will
     * make the surface invalid.
     */
    public native void release();

    /** draw into a surface */
    public Canvas lockCanvas(Rect dirty) throws OutOfResourcesException, IllegalArgumentException {
        /*
         * the dirty rectangle may be expanded to the surface's size, if for
         * instance it has been resized or if the bits were lost, since the last
         * call.
         */
        return lockCanvasNative(dirty);
    }

    /** unlock the surface and asks a page flip */
    public native   void unlockCanvasAndPost(Canvas canvas);

    /** 
     * unlock the surface. the screen won't be updated until
     * post() or postAll() is called
     */
    public native   void unlockCanvas(Canvas canvas);

    @Override
    public String toString() {
        return "Surface(name=" + mName + ", identity=" + getIdentity() + ")";
    }

    public int describeContents() {
        return 0;
    }

    public native   void readFromParcel(Parcel source);
    public native   void writeToParcel(Parcel dest, int flags);

    /**
     * Exception thrown when a surface couldn't be created or resized
     */
    public static class OutOfResourcesException extends Exception {
        public OutOfResourcesException() {
        }
        public OutOfResourcesException(String name) {
            super(name);
        }
    }
    
    /*
     * -----------------------------------------------------------------------
     * No user serviceable parts beyond this point
     * -----------------------------------------------------------------------
     */

    /* flags used in constructor (keep in sync with ISurfaceComposer.h) */

    /** Surface is created hidden @hide */
    public static final int HIDDEN              = 0x00000004;

    /** The surface contains secure content, special measures will
     * be taken to disallow the surface's content to be copied from
     * another process. In particular, screenshots and VNC servers will
     * be disabled, but other measures can take place, for instance the
     * surface might not be hardware accelerated. 
     * @hide*/
    public static final int SECURE              = 0x00000080;
    
    /** Creates a surface where color components are interpreted as 
     *  "non pre-multiplied" by their alpha channel. Of course this flag is
     *  meaningless for surfaces without an alpha channel. By default
     *  surfaces are pre-multiplied, which means that each color component is
     *  already multiplied by its alpha value. In this case the blending
     *  equation used is:
     *  
     *    DEST = SRC + DEST * (1-SRC_ALPHA)
     *    
     *  By contrast, non pre-multiplied surfaces use the following equation:
     *  
     *    DEST = SRC * SRC_ALPHA * DEST * (1-SRC_ALPHA)
     *    
     *  pre-multiplied surfaces must always be used if transparent pixels are
     *  composited on top of each-other into the surface. A pre-multiplied
     *  surface can never lower the value of the alpha component of a given
     *  pixel.
     *  
     *  In some rare situations, a non pre-multiplied surface is preferable.
     *  
     *  @hide
     */
    public static final int NON_PREMULTIPLIED   = 0x00000100;
    
    /**
     * Indicates that the surface must be considered opaque, even if its
     * pixel format is set to translucent. This can be useful if an
     * application needs full RGBA 8888 support for instance but will
     * still draw every pixel opaque.
     * 
     * @hide
     */
    public static final int OPAQUE              = 0x00000400;
    
    /**
     * Application requires a hardware-protected path to an
     * external display sink. If a hardware-protected path is not available,
     * then this surface will not be displayed on the external sink.
     *
     * @hide
     */
    public static final int PROTECTED_APP       = 0x00000800;

    // 0x1000 is reserved for an independent DRM protected flag in framework

    /** Creates a normal surface. This is the default. @hide */
    public static final int FX_SURFACE_NORMAL   = 0x00000000;
    
    /** Creates a Blur surface. Everything behind this surface is blurred
     * by some amount. The quality and refresh speed of the blur effect
     * is not settable or guaranteed.
     * It is an error to lock a Blur surface, since it doesn't have
     * a backing store.
=======
    private static final String TAG = "Surface";

    private static final boolean HEADLESS = "1".equals(
        SystemProperties.get("ro.config.headless", "0"));

    public static final Parcelable.Creator<Surface> CREATOR =
            new Parcelable.Creator<Surface>() {
        public Surface createFromParcel(Parcel source) {
            try {
                Surface s = new Surface();
                s.readFromParcel(source);
                return s;
            } catch (Exception e) {
                Log.e(TAG, "Exception creating surface from parcel", e);
                return null;
            }
        }

        public Surface[] newArray(int size) {
            return new Surface[size];
        }
    };

    /**
     * Rotation constant: 0 degree rotation (natural orientation)
     */
    public static final int ROTATION_0 = 0;

    /**
     * Rotation constant: 90 degree rotation.
     */
    public static final int ROTATION_90 = 1;

    /**
     * Rotation constant: 180 degree rotation.
     */
    public static final int ROTATION_180 = 2;

    /**
     * Rotation constant: 270 degree rotation.
     */
    public static final int ROTATION_270 = 3;

    /* built-in physical display ids (keep in sync with ISurfaceComposer.h)
     * these are different from the logical display ids used elsewhere in the framework */

    /**
     * Built-in physical display id: Main display.
     * Use only with {@link #getBuiltInDisplay()}.
     * @hide
     */
    public static final int BUILT_IN_DISPLAY_ID_MAIN = 0;

    /**
     * Built-in physical display id: Attached HDMI display.
     * Use only with {@link #getBuiltInDisplay()}.
     * @hide
     */
    public static final int BUILT_IN_DISPLAY_ID_HDMI = 1;

    /* flags used in constructor (keep in sync with ISurfaceComposerClient.h) */

    /**
     * Surface creation flag: Surface is created hidden
     * @hide */
    public static final int HIDDEN = 0x00000004;

    /**
     * Surface creation flag: The surface contains secure content, special
     * measures will be taken to disallow the surface's content to be copied
     * from another process. In particular, screenshots and VNC servers will
     * be disabled, but other measures can take place, for instance the
     * surface might not be hardware accelerated. 
     * @hide
     */
    public static final int SECURE = 0x00000080;

    /**
     * Surface creation flag: Creates a surface where color components are interpreted
     * as "non pre-multiplied" by their alpha channel. Of course this flag is
     * meaningless for surfaces without an alpha channel. By default
     * surfaces are pre-multiplied, which means that each color component is
     * already multiplied by its alpha value. In this case the blending
     * equation used is:
     *
     *    DEST = SRC + DEST * (1-SRC_ALPHA)
     *
     * By contrast, non pre-multiplied surfaces use the following equation:
     *
     *    DEST = SRC * SRC_ALPHA * DEST * (1-SRC_ALPHA)
     *
     * pre-multiplied surfaces must always be used if transparent pixels are
     * composited on top of each-other into the surface. A pre-multiplied
     * surface can never lower the value of the alpha component of a given
     * pixel.
     *
     * In some rare situations, a non pre-multiplied surface is preferable.
     * @hide
     */
    public static final int NON_PREMULTIPLIED = 0x00000100;

    /**
     * Surface creation flag: Indicates that the surface must be considered opaque,
     * even if its pixel format is set to translucent. This can be useful if an
     * application needs full RGBA 8888 support for instance but will
     * still draw every pixel opaque.
     * @hide
     */
    public static final int OPAQUE = 0x00000400;

    /**
     * Surface creation flag: Application requires a hardware-protected path to an
     * external display sink. If a hardware-protected path is not available,
     * then this surface will not be displayed on the external sink.
     * @hide
     */
    public static final int PROTECTED_APP = 0x00000800;

    // 0x1000 is reserved for an independent DRM protected flag in framework

    /**
     * Surface creation flag: Creates a normal surface.
     * This is the default.
     * @hide
     */
    public static final int FX_SURFACE_NORMAL   = 0x00000000;

    /**
     * Surface creation flag: Creates a Blur surface.
     * Everything behind this surface is blurred by some amount.
     * The quality and refresh speed of the blur effect is not settable or guaranteed.
     * It is an error to lock a Blur surface, since it doesn't have a backing store.
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
     * @hide
     * @deprecated
     */
    @Deprecated
<<<<<<< HEAD
    public static final int FX_SURFACE_BLUR     = 0x00010000;
    
    /** Creates a Dim surface. Everything behind this surface is dimmed
     * by the amount specified in {@link #setAlpha}.
     * It is an error to lock a Dim surface, since it doesn't have
     * a backing store.
     * @hide
     */
    public static final int FX_SURFACE_DIM     = 0x00020000;

    /** @hide */
    public static final int FX_SURFACE_SCREENSHOT   = 0x00030000;

    /** Mask used for FX values above @hide */
    public static final int FX_SURFACE_MASK     = 0x000F0000;

    /* flags used with setFlags() (keep in sync with ISurfaceComposer.h) */
    
    /** Hide the surface. Equivalent to calling hide(). @hide */
    public static final int SURFACE_HIDDEN    = 0x01;
    
    /** Freeze the surface. Equivalent to calling freeze(). @hide */
    public static final int SURFACE_FROZEN     = 0x02;

    /** Enable dithering when compositing this surface @hide */
    public static final int SURFACE_DITHER    = 0x04;

=======
    public static final int FX_SURFACE_BLUR = 0x00010000;

    /**
     * Surface creation flag: Creates a Dim surface.
     * Everything behind this surface is dimmed by the amount specified
     * in {@link #setAlpha}.  It is an error to lock a Dim surface, since it
     * doesn't have a backing store.
     * @hide
     */
    public static final int FX_SURFACE_DIM = 0x00020000;

    /**
     * @hide
     */
    public static final int FX_SURFACE_SCREENSHOT = 0x00030000;

    /**
     * Mask used for FX values above.
     * @hide
     */
    public static final int FX_SURFACE_MASK = 0x000F0000;

    /* flags used with setFlags() (keep in sync with ISurfaceComposer.h) */
    
    /**
     * Surface flag: Hide the surface.
     * Equivalent to calling hide().
     * @hide
     */
    public static final int SURFACE_HIDDEN = 0x01;


    private final CloseGuard mCloseGuard = CloseGuard.get();
    private String mName;

    // Note: These fields are accessed by native code.
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
    // The mSurfaceControl will only be present for Surfaces used by the window
    // server or system processes. When this class is parceled we defer to the
    // mSurfaceControl to do the parceling. Otherwise we parcel the
    // mNativeSurface.
<<<<<<< HEAD
    private int mSurfaceControl;
    private int mSaveCount;
    private Canvas mCanvas;
    private int mNativeSurface;
    private int mSurfaceGenerationId;
    private String mName;
=======
    private int mNativeSurface; // Surface*
    private int mNativeSurfaceControl; // SurfaceControl*
    private int mGenerationId; // incremented each time mNativeSurface changes
    private final Canvas mCanvas = new CompatibleCanvas();
    private int mCanvasSaveCount; // Canvas save count at time of lockCanvas()
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a

    // The Translator for density compatibility mode.  This is used for scaling
    // the canvas to perform the appropriate density transformation.
    private Translator mCompatibilityTranslator;

    // A matrix to scale the matrix set by application. This is set to null for
    // non compatibility mode.
    private Matrix mCompatibleMatrix;

<<<<<<< HEAD
    private Exception mCreationStack;


    /*
     * We use a class initializer to allow the native code to cache some
     * field offsets.
     */
    native private static void nativeClassInit();
    static { nativeClassInit(); }

    /** create a surface @hide */
    public Surface(SurfaceSession s,
            int pid, int display, int w, int h, int format, int flags)
        throws OutOfResourcesException {
        checkHeadless();

        if (DEBUG_RELEASE) {
            mCreationStack = new Exception();
        }
        mCanvas = new CompatibleCanvas();
        init(s,pid,null,display,w,h,format,flags);
    }

    /** create a surface with a name @hide */
    public Surface(SurfaceSession s,
            int pid, String name, int display, int w, int h, int format, int flags)
        throws OutOfResourcesException {
        checkHeadless();

        if (DEBUG_RELEASE) {
            mCreationStack = new Exception();
        }
        mCanvas = new CompatibleCanvas();
        init(s,pid,name,display,w,h,format,flags);
        mName = name;
    }

    /**
     * Create an empty surface, which will later be filled in by
     * readFromParcel().
     * @hide
     */
    public Surface() {
        checkHeadless();

        if (DEBUG_RELEASE) {
            mCreationStack = new Exception();
        }
        mCanvas = new CompatibleCanvas();
    }

    private Surface(Parcel source) throws OutOfResourcesException {
        init(source);
    }

    /**
     * Copy another surface to this one.  This surface now holds a reference
     * to the same data as the original surface, and is -not- the owner.
     * This is for use by the window manager when returning a window surface
     * back from a client, converting it from the representation being managed
     * by the window manager to the representation the client uses to draw
     * in to it.
     * @hide
     */
    public native void copyFrom(Surface o);

    /**
     * Transfer the native state from 'o' to this surface, releasing it
     * from 'o'.  This is for use in the client side for drawing into a
     * surface; not guaranteed to work on the window manager side.
     * This is for use by the client to move the underlying surface from
     * one Surface object to another, in particular in SurfaceFlinger.
     * @hide.
     */
    public native void transferFrom(Surface o);

    /** @hide */
    public int getGenerationId() {
        return mSurfaceGenerationId;
    }


    /**
     * Whether the consumer of this Surface is running behind the producer;
     * that is, isConsumerRunningBehind() returns true if the consumer is more
     * than one buffer ahead of the producer.
     * @hide
     */
    public native boolean isConsumerRunningBehind();

    /**
     * A Canvas class that can handle the compatibility mode. This does two
     * things differently.
     * <ul>
     * <li>Returns the width and height of the target metrics, rather than
     * native. For example, the canvas returns 320x480 even if an app is running
     * in WVGA high density.
     * <li>Scales the matrix in setMatrix by the application scale, except if
     * the matrix looks like obtained from getMatrix. This is a hack to handle
     * the case that an application uses getMatrix to keep the original matrix,
     * set matrix of its own, then set the original matrix back. There is no
     * perfect solution that works for all cases, and there are a lot of cases
     * that this model does not work, but we hope this works for many apps.
     * </ul>
     */
    private class CompatibleCanvas extends Canvas {
        // A temp matrix to remember what an application obtained via {@link getMatrix}
        private Matrix mOrigMatrix = null;

        @Override
        public int getWidth() {
            int w = super.getWidth();
            if (mCompatibilityTranslator != null) {
                w = (int)(w * mCompatibilityTranslator.applicationInvertedScale + .5f);
            }
            return w;
        }

        @Override
        public int getHeight() {
            int h = super.getHeight();
            if (mCompatibilityTranslator != null) {
                h = (int)(h * mCompatibilityTranslator.applicationInvertedScale + .5f);
            }
            return h;
        }

        @Override
        public void setMatrix(Matrix matrix) {
            if (mCompatibleMatrix == null || mOrigMatrix == null || mOrigMatrix.equals(matrix)) {
                // don't scale the matrix if it's not compatibility mode, or
                // the matrix was obtained from getMatrix.
                super.setMatrix(matrix);
            } else {
                Matrix m = new Matrix(mCompatibleMatrix);
                m.preConcat(matrix);
                super.setMatrix(m);
            }
        }

        @Override
        public void getMatrix(Matrix m) {
            super.getMatrix(m);
            if (mOrigMatrix == null) {
                mOrigMatrix = new Matrix(); 
            }
            mOrigMatrix.set(m);
        }
=======
    private native void nativeCreate(SurfaceSession session, String name,
            int w, int h, int format, int flags)
            throws OutOfResourcesException;
    private native void nativeCreateFromSurfaceTexture(SurfaceTexture surfaceTexture)
            throws OutOfResourcesException;
    private native void nativeRelease();
    private native void nativeDestroy();

    private native boolean nativeIsValid();
    private native int nativeGetIdentity();
    private native boolean nativeIsConsumerRunningBehind();

    private native Canvas nativeLockCanvas(Rect dirty);
    private native void nativeUnlockCanvasAndPost(Canvas canvas);

    private static native Bitmap nativeScreenshot(IBinder displayToken,
            int width, int height, int minLayer, int maxLayer, boolean allLayers);

    private static native void nativeOpenTransaction();
    private static native void nativeCloseTransaction();
    private static native void nativeSetAnimationTransaction();

    private native void nativeSetLayer(int zorder);
    private native void nativeSetPosition(float x, float y);
    private native void nativeSetSize(int w, int h);
    private native void nativeSetTransparentRegionHint(Region region);
    private native void nativeSetAlpha(float alpha);
    private native void nativeSetMatrix(float dsdx, float dtdx, float dsdy, float dtdy);
    private native void nativeSetFlags(int flags, int mask);
    private native void nativeSetWindowCrop(Rect crop);
    private native void nativeSetLayerStack(int layerStack);

    private static native IBinder nativeGetBuiltInDisplay(int physicalDisplayId);
    private static native IBinder nativeCreateDisplay(String name, boolean secure);
    private static native void nativeSetDisplaySurface(
            IBinder displayToken, Surface surface);
    private static native void nativeSetDisplayLayerStack(
            IBinder displayToken, int layerStack);
    private static native void nativeSetDisplayProjection(
            IBinder displayToken, int orientation, Rect layerStackRect, Rect displayRect);
    private static native boolean nativeGetDisplayInfo(
            IBinder displayToken, PhysicalDisplayInfo outInfo);
    private static native void nativeBlankDisplay(IBinder displayToken);
    private static native void nativeUnblankDisplay(IBinder displayToken);

    private native void nativeCopyFrom(Surface other);
    private native void nativeTransferFrom(Surface other);
    private native void nativeReadFromParcel(Parcel source);
    private native void nativeWriteToParcel(Parcel dest);


    /**
     * Create an empty surface, which will later be filled in by readFromParcel().
     * @hide
     */
    public Surface() {
        checkHeadless();

        mCloseGuard.open("release");
    }

    /**
     * Create a surface with a name.
     *
     * The surface creation flags specify what kind of surface to create and
     * certain options such as whether the surface can be assumed to be opaque
     * and whether it should be initially hidden.  Surfaces should always be
     * created with the {@link #HIDDEN} flag set to ensure that they are not
     * made visible prematurely before all of the surface's properties have been
     * configured.
     *
     * Good practice is to first create the surface with the {@link #HIDDEN} flag
     * specified, open a transaction, set the surface layer, layer stack, alpha,
     * and position, call {@link #show} if appropriate, and close the transaction.
     *
     * @param session The surface session, must not be null.
     * @param name The surface name, must not be null.
     * @param w The surface initial width.
     * @param h The surface initial height.
     * @param flags The surface creation flags.  Should always include {@link #HIDDEN}
     * in the creation flags.
     * @hide
     */
    public Surface(SurfaceSession session,
            String name, int w, int h, int format, int flags)
            throws OutOfResourcesException {
        if (session == null) {
            throw new IllegalArgumentException("session must not be null");
        }
        if (name == null) {
            throw new IllegalArgumentException("name must not be null");
        }

        if ((flags & HIDDEN) == 0) {
            Log.w(TAG, "Surfaces should always be created with the HIDDEN flag set "
                    + "to ensure that they are not made visible prematurely before "
                    + "all of the surface's properties have been configured.  "
                    + "Set the other properties and make the surface visible within "
                    + "a transaction.  New surface name: " + name,
                    new Throwable());
        }

        checkHeadless();

        mName = name;
        nativeCreate(session, name, w, h, format, flags);

        mCloseGuard.open("release");
    }

    /**
     * Create Surface from a {@link SurfaceTexture}.
     *
     * Images drawn to the Surface will be made available to the {@link
     * SurfaceTexture}, which can attach them to an OpenGL ES texture via {@link
     * SurfaceTexture#updateTexImage}.
     *
     * @param surfaceTexture The {@link SurfaceTexture} that is updated by this
     * Surface.
     */
    public Surface(SurfaceTexture surfaceTexture) {
        if (surfaceTexture == null) {
            throw new IllegalArgumentException("surfaceTexture must not be null");
        }

        checkHeadless();

        mName = surfaceTexture.toString();
        try {
            nativeCreateFromSurfaceTexture(surfaceTexture);
        } catch (OutOfResourcesException ex) {
            // We can't throw OutOfResourcesException because it would be an API change.
            throw new RuntimeException(ex);
        }

        mCloseGuard.open("release");
    }

    @Override
    protected void finalize() throws Throwable {
        try {
            if (mCloseGuard != null) {
                mCloseGuard.warnIfOpen();
            }
            nativeRelease();
        } finally {
            super.finalize();
        }
    }

    /**
     * Release the local reference to the server-side surface.
     * Always call release() when you're done with a Surface.
     * This will make the surface invalid.
     */
    public void release() {
        nativeRelease();
        mCloseGuard.close();
    }

    /**
     * Free all server-side state associated with this surface and
     * release this object's reference.  This method can only be
     * called from the process that created the service.
     * @hide
     */
    public void destroy() {
        nativeDestroy();
        mCloseGuard.close();
    }

    /**
     * Returns true if this object holds a valid surface.
     *
     * @return True if it holds a physical surface, so lockCanvas() will succeed.
     * Otherwise returns false.
     */
    public boolean isValid() {
        return nativeIsValid();
    }

    /**
     * Gets the generation number of this surface, incremented each time
     * the native surface contained within this object changes.
     *
     * @return The current generation number.
     * @hide
     */
    public int getGenerationId() {
        return mGenerationId;
    }

    /**
     * Returns true if the consumer of this Surface is running behind the producer.
     *
     * @return True if the consumer is more than one buffer ahead of the producer.
     * @hide
     */
    public boolean isConsumerRunningBehind() {
        return nativeIsConsumerRunningBehind();
    }

    /**
     * Gets a {@link Canvas} for drawing into this surface.
     *
     * After drawing into the provided {@link Canvas}, the caller should
     * invoke {@link #unlockCanvasAndPost} to post the new contents to the surface.
     *
     * @param dirty A rectangle that represents the dirty region that the caller wants
     * to redraw.  This function may choose to expand the dirty rectangle if for example
     * the surface has been resized or if the previous contents of the surface were
     * not available.  The caller should redraw the entire dirty region as represented
     * by the contents of the dirty rect upon return from this function.
     * The caller may also pass <code>null</code> instead, in the case where the
     * entire surface should be redrawn.
     * @return A canvas for drawing into the surface.
     */
    public Canvas lockCanvas(Rect dirty)
            throws OutOfResourcesException, IllegalArgumentException {
        return nativeLockCanvas(dirty);
    }

    /**
     * Posts the new contents of the {@link Canvas} to the surface and
     * releases the {@link Canvas}.
     *
     * @param canvas The canvas previously obtained from {@link #lockCanvas}.
     */
    public void unlockCanvasAndPost(Canvas canvas) {
        nativeUnlockCanvasAndPost(canvas);
    }

    /** 
     * @deprecated This API has been removed and is not supported.  Do not use.
     */
    @Deprecated
    public void unlockCanvas(Canvas canvas) {
        throw new UnsupportedOperationException();
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
    }

    /**
     * Sets the translator used to scale canvas's width/height in compatibility
     * mode.
     */
    void setCompatibilityTranslator(Translator translator) {
        if (translator != null) {
            float appScale = translator.applicationScale;
            mCompatibleMatrix = new Matrix();
            mCompatibleMatrix.setScale(appScale, appScale);
        }
    }
<<<<<<< HEAD
    
    /** Free all server-side state associated with this surface and
     * release this object's reference. @hide */
    public native void destroy();
    
    private native Canvas lockCanvasNative(Rect dirty);   
    
    /*
     * set display parameters & screenshots
     */
    
    /**
     * Freezes the specified display, No updating of the screen will occur
     * until unfreezeDisplay() is called. Everything else works as usual though,
     * in particular transactions.
     * @param display
     * @hide
     */
    public static native   void freezeDisplay(int display);

    /**
     * resume updating the specified display.
     * @param display
     * @hide
     */
    public static native   void unfreezeDisplay(int display);

    /**
     * set the orientation of the given display.
     * @param display
     * @param orientation
     * @param flags Currently unused, set to 0.
     * @hide
     */
    public static native   void setOrientation(int display, int orientation, int flags);

    /**
     * set the orientation of the given display.
     * @param display
     * @param orientation
     * @hide
     */
    public static void setOrientation(int display, int orientation) {
        setOrientation(display, orientation, 0);
    }
    
    /**
=======

    /**
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
     * Like {@link #screenshot(int, int, int, int)} but includes all
     * Surfaces in the screenshot.
     *
     * @hide
     */
<<<<<<< HEAD
    public static native Bitmap screenshot(int width, int height);
    
=======
    public static Bitmap screenshot(int width, int height) {
        // TODO: should take the display as a parameter
        IBinder displayToken = getBuiltInDisplay(BUILT_IN_DISPLAY_ID_MAIN);
        return nativeScreenshot(displayToken, width, height, 0, 0, true);
    }

>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
    /**
     * Copy the current screen contents into a bitmap and return it.
     *
     * @param width The desired width of the returned bitmap; the raw
     * screen will be scaled down to this size.
     * @param height The desired height of the returned bitmap; the raw
     * screen will be scaled down to this size.
     * @param minLayer The lowest (bottom-most Z order) surface layer to
     * include in the screenshot.
     * @param maxLayer The highest (top-most Z order) surface layer to
     * include in the screenshot.
<<<<<<< HEAD
     * @return Returns a Bitmap containing the screen contents.
     *
     * @hide
     */
    public static native Bitmap screenshot(int width, int height, int minLayer, int maxLayer);

    
=======
     * @return Returns a Bitmap containing the screen contents, or null
     * if an error occurs.
     *
     * @hide
     */
    public static Bitmap screenshot(int width, int height, int minLayer, int maxLayer) {
        // TODO: should take the display as a parameter
        IBinder displayToken = getBuiltInDisplay(BUILT_IN_DISPLAY_ID_MAIN);
        return nativeScreenshot(displayToken, width, height, minLayer, maxLayer, false);
    }

>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
    /*
     * set surface parameters.
     * needs to be inside open/closeTransaction block
     */
<<<<<<< HEAD
    
    /** start a transaction @hide */
    public static native   void openTransaction();
    /** end a transaction @hide */
    public static native   void closeTransaction();
    /** @hide */
    public native   void setLayer(int zorder);
    /** @hide */
    public void setPosition(int x, int y) { setPosition((float)x, (float)y); }
    /** @hide */
    public native   void setPosition(float x, float y);
    /** @hide */
    public native   void setSize(int w, int h);
    /** @hide */
    public native   void hide();
    /** @hide */
    public native   void show();
    /** @hide */
    public native   void setTransparentRegionHint(Region region);
    /** @hide */
    public native   void setAlpha(float alpha);
    /** @hide */
    public native   void setMatrix(float dsdx, float dtdx, float dsdy, float dtdy);
    /** @hide */
    public native   void freeze();
    /** @hide */
    public native   void unfreeze();
    /** @hide */
    public native   void setFreezeTint(int tint);
    /** @hide */
    public native   void setFlags(int flags, int mask);
    /** @hide */
    public native   void setWindowCrop(Rect crop);


   
    public static final Parcelable.Creator<Surface> CREATOR
            = new Parcelable.Creator<Surface>()
    {
        public Surface createFromParcel(Parcel source) {
            try {
                return new Surface(source);
            } catch (Exception e) {
                Log.e(LOG_TAG, "Exception creating surface from parcel", e);
            }
            return null;
        }

        public Surface[] newArray(int size) {
            return new Surface[size];
        }
    };

    @Override
    protected void finalize() throws Throwable {
        try {
            super.finalize();
        } finally {
            if (mNativeSurface != 0 || mSurfaceControl != 0) {
                if (DEBUG_RELEASE) {
                    Log.w(LOG_TAG, "Surface.finalize() has work. You should have called release() (" 
                            + mNativeSurface + ", " + mSurfaceControl + ")", mCreationStack);
                } else {
                    Log.w(LOG_TAG, "Surface.finalize() has work. You should have called release() (" 
                            + mNativeSurface + ", " + mSurfaceControl + ")");
                }
            }
            release();            
        }
    }
    
    private native void init(SurfaceSession s,
            int pid, String name, int display, int w, int h, int format, int flags)
            throws OutOfResourcesException;

    private native void init(Parcel source);

    private native void initFromSurfaceTexture(SurfaceTexture surfaceTexture);

    private native int getIdentity();
=======

    /** start a transaction @hide */
    public static void openTransaction() {
        nativeOpenTransaction();
    }

    /** end a transaction @hide */
    public static void closeTransaction() {
        nativeCloseTransaction();
    }

    /** flag the transaction as an animation @hide */
    public static void setAnimationTransaction() {
        nativeSetAnimationTransaction();
    }

    /** @hide */
    public void setLayer(int zorder) {
        nativeSetLayer(zorder);
    }

    /** @hide */
    public void setPosition(int x, int y) {
        nativeSetPosition((float)x, (float)y);
    }

    /** @hide */
    public void setPosition(float x, float y) {
        nativeSetPosition(x, y);
    }

    /** @hide */
    public void setSize(int w, int h) {
        nativeSetSize(w, h);
    }

    /** @hide */
    public void hide() {
        nativeSetFlags(SURFACE_HIDDEN, SURFACE_HIDDEN);
    }

    /** @hide */
    public void show() {
        nativeSetFlags(0, SURFACE_HIDDEN);
    }

    /** @hide */
    public void setTransparentRegionHint(Region region) {
        nativeSetTransparentRegionHint(region);
    }

    /** @hide */
    public void setAlpha(float alpha) {
        nativeSetAlpha(alpha);
    }

    /** @hide */
    public void setMatrix(float dsdx, float dtdx, float dsdy, float dtdy) {
        nativeSetMatrix(dsdx, dtdx, dsdy, dtdy);
    }

    /** @hide */
    public void setFlags(int flags, int mask) {
        nativeSetFlags(flags, mask);
    }

    /** @hide */
    public void setWindowCrop(Rect crop) {
        nativeSetWindowCrop(crop);
    }

    /** @hide */
    public void setLayerStack(int layerStack) {
        nativeSetLayerStack(layerStack);
    }

    /** @hide */
    public static IBinder getBuiltInDisplay(int builtInDisplayId) {
        return nativeGetBuiltInDisplay(builtInDisplayId);
    }

    /** @hide */
    public static IBinder createDisplay(String name, boolean secure) {
        if (name == null) {
            throw new IllegalArgumentException("name must not be null");
        }
        return nativeCreateDisplay(name, secure);
    }

    /** @hide */
    public static void setDisplaySurface(IBinder displayToken, Surface surface) {
        if (displayToken == null) {
            throw new IllegalArgumentException("displayToken must not be null");
        }
        nativeSetDisplaySurface(displayToken, surface);
    }

    /** @hide */
    public static void setDisplayLayerStack(IBinder displayToken, int layerStack) {
        if (displayToken == null) {
            throw new IllegalArgumentException("displayToken must not be null");
        }
        nativeSetDisplayLayerStack(displayToken, layerStack);
    }

    /** @hide */
    public static void setDisplayProjection(IBinder displayToken,
            int orientation, Rect layerStackRect, Rect displayRect) {
        if (displayToken == null) {
            throw new IllegalArgumentException("displayToken must not be null");
        }
        if (layerStackRect == null) {
            throw new IllegalArgumentException("layerStackRect must not be null");
        }
        if (displayRect == null) {
            throw new IllegalArgumentException("displayRect must not be null");
        }
        nativeSetDisplayProjection(displayToken, orientation, layerStackRect, displayRect);
    }

    /** @hide */
    public static boolean getDisplayInfo(IBinder displayToken, PhysicalDisplayInfo outInfo) {
        if (displayToken == null) {
            throw new IllegalArgumentException("displayToken must not be null");
        }
        if (outInfo == null) {
            throw new IllegalArgumentException("outInfo must not be null");
        }
        return nativeGetDisplayInfo(displayToken, outInfo);
    }

    /** @hide */
    public static void blankDisplay(IBinder displayToken) {
        if (displayToken == null) {
            throw new IllegalArgumentException("displayToken must not be null");
        }
        nativeBlankDisplay(displayToken);
    }

    /** @hide */
    public static void unblankDisplay(IBinder displayToken) {
        if (displayToken == null) {
            throw new IllegalArgumentException("displayToken must not be null");
        }
        nativeUnblankDisplay(displayToken);
    }

    /**
     * Copy another surface to this one.  This surface now holds a reference
     * to the same data as the original surface, and is -not- the owner.
     * This is for use by the window manager when returning a window surface
     * back from a client, converting it from the representation being managed
     * by the window manager to the representation the client uses to draw
     * in to it.
     * @hide
     */
    public void copyFrom(Surface other) {
        if (other == null) {
            throw new IllegalArgumentException("other must not be null");
        }
        if (other != this) {
            nativeCopyFrom(other);
        }
    }

    /**
     * Transfer the native state from 'other' to this surface, releasing it
     * from 'other'.  This is for use in the client side for drawing into a
     * surface; not guaranteed to work on the window manager side.
     * This is for use by the client to move the underlying surface from
     * one Surface object to another, in particular in SurfaceFlinger.
     * @hide.
     */
    public void transferFrom(Surface other) {
        if (other == null) {
            throw new IllegalArgumentException("other must not be null");
        }
        if (other != this) {
            nativeTransferFrom(other);
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public void readFromParcel(Parcel source) {
        if (source == null) {
            throw new IllegalArgumentException("source must not be null");
        }

        mName = source.readString();
        nativeReadFromParcel(source);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (dest == null) {
            throw new IllegalArgumentException("dest must not be null");
        }

        dest.writeString(mName);
        nativeWriteToParcel(dest);
        if ((flags & Parcelable.PARCELABLE_WRITE_RETURN_VALUE) != 0) {
            release();
        }
    }

    @Override
    public String toString() {
        return "Surface(name=" + mName + ", identity=" + nativeGetIdentity() + ")";
    }

    private static void checkHeadless() {
        if (HEADLESS) {
            throw new UnsupportedOperationException("Device is headless");
        }
    }

    /**
     * Exception thrown when a surface couldn't be created or resized.
     */
    public static class OutOfResourcesException extends Exception {
        public OutOfResourcesException() {
        }

        public OutOfResourcesException(String name) {
            super(name);
        }
    }

    /**
     * Describes the properties of a physical display known to surface flinger.
     * @hide
     */
    public static final class PhysicalDisplayInfo {
        public int width;
        public int height;
        public float refreshRate;
        public float density;
        public float xDpi;
        public float yDpi;
        public boolean secure;

        public PhysicalDisplayInfo() {
        }

        public PhysicalDisplayInfo(PhysicalDisplayInfo other) {
            copyFrom(other);
        }

        @Override
        public boolean equals(Object o) {
            return o instanceof PhysicalDisplayInfo && equals((PhysicalDisplayInfo)o);
        }

        public boolean equals(PhysicalDisplayInfo other) {
            return other != null
                    && width == other.width
                    && height == other.height
                    && refreshRate == other.refreshRate
                    && density == other.density
                    && xDpi == other.xDpi
                    && yDpi == other.yDpi
                    && secure == other.secure;
        }

        @Override
        public int hashCode() {
            return 0; // don't care
        }

        public void copyFrom(PhysicalDisplayInfo other) {
            width = other.width;
            height = other.height;
            refreshRate = other.refreshRate;
            density = other.density;
            xDpi = other.xDpi;
            yDpi = other.yDpi;
            secure = other.secure;
        }

        // For debugging purposes
        @Override
        public String toString() {
            return "PhysicalDisplayInfo{" + width + " x " + height + ", " + refreshRate + " fps, "
                    + "density " + density + ", " + xDpi + " x " + yDpi + " dpi, secure " + secure
                    + "}";
        }
    }

    /**
     * A Canvas class that can handle the compatibility mode.
     * This does two things differently.
     * <ul>
     * <li>Returns the width and height of the target metrics, rather than
     * native. For example, the canvas returns 320x480 even if an app is running
     * in WVGA high density.
     * <li>Scales the matrix in setMatrix by the application scale, except if
     * the matrix looks like obtained from getMatrix. This is a hack to handle
     * the case that an application uses getMatrix to keep the original matrix,
     * set matrix of its own, then set the original matrix back. There is no
     * perfect solution that works for all cases, and there are a lot of cases
     * that this model does not work, but we hope this works for many apps.
     * </ul>
     */
    private final class CompatibleCanvas extends Canvas {
        // A temp matrix to remember what an application obtained via {@link getMatrix}
        private Matrix mOrigMatrix = null;

        @Override
        public int getWidth() {
            int w = super.getWidth();
            if (mCompatibilityTranslator != null) {
                w = (int)(w * mCompatibilityTranslator.applicationInvertedScale + .5f);
            }
            return w;
        }

        @Override
        public int getHeight() {
            int h = super.getHeight();
            if (mCompatibilityTranslator != null) {
                h = (int)(h * mCompatibilityTranslator.applicationInvertedScale + .5f);
            }
            return h;
        }

        @Override
        public void setMatrix(Matrix matrix) {
            if (mCompatibleMatrix == null || mOrigMatrix == null || mOrigMatrix.equals(matrix)) {
                // don't scale the matrix if it's not compatibility mode, or
                // the matrix was obtained from getMatrix.
                super.setMatrix(matrix);
            } else {
                Matrix m = new Matrix(mCompatibleMatrix);
                m.preConcat(matrix);
                super.setMatrix(m);
            }
        }

        @Override
        public void getMatrix(Matrix m) {
            super.getMatrix(m);
            if (mOrigMatrix == null) {
                mOrigMatrix = new Matrix(); 
            }
            mOrigMatrix.set(m);
        }
    }
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
}
