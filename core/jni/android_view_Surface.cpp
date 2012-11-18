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

#define LOG_TAG "Surface"

#include <stdio.h>

<<<<<<< HEAD
#include "android_util_Binder.h"
#include "android/graphics/GraphicsJNI.h"

#include <binder/IMemory.h>

=======
#include "android_os_Parcel.h"
#include "android_util_Binder.h"
#include "android/graphics/GraphicsJNI.h"
#include "android/graphics/Region.h"

#include <binder/IMemory.h>

#include <gui/ISurfaceComposer.h>
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
#include <gui/Surface.h>
#include <gui/SurfaceComposerClient.h>
#include <gui/SurfaceTexture.h>

<<<<<<< HEAD
=======
#include <ui/DisplayInfo.h>
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
#include <ui/Rect.h>
#include <ui/Region.h>

#include <EGL/egl.h>

#include <SkCanvas.h>
#include <SkBitmap.h>
#include <SkRegion.h>
#include <SkPixelRef.h>

#include "jni.h"
#include "JNIHelp.h"
#include <android_runtime/AndroidRuntime.h>
#include <android_runtime/android_view_Surface.h>
<<<<<<< HEAD
#include <android_runtime/android_graphics_SurfaceTexture.h>
#include <utils/misc.h>
=======
#include <android_runtime/android_view_SurfaceSession.h>
#include <android_runtime/android_graphics_SurfaceTexture.h>
#include <utils/misc.h>
#include <utils/Log.h>

#include <ScopedUtfChars.h>
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a


// ----------------------------------------------------------------------------

namespace android {

<<<<<<< HEAD
enum {
    // should match Parcelable.java
    PARCELABLE_WRITE_RETURN_VALUE = 0x0001
};

// ----------------------------------------------------------------------------

static const char* const OutOfResourcesException =
    "android/view/Surface$OutOfResourcesException";

const char* const kSurfaceSessionClassPathName = "android/view/SurfaceSession";
const char* const kSurfaceClassPathName = "android/view/Surface";

struct sso_t {
    jfieldID client;
};
static sso_t sso;

struct so_t {
    jfieldID surfaceControl;
    jfieldID surfaceGenerationId;
    jfieldID surface;
    jfieldID saveCount;
    jfieldID canvas;
};
static so_t so;

struct ro_t {
    jfieldID l;
    jfieldID t;
    jfieldID r;
    jfieldID b;
};
static ro_t ro;

struct po_t {
    jfieldID x;
    jfieldID y;
};
static po_t po;

struct co_t {
    jfieldID surfaceFormat;
};
static co_t co;

struct no_t {
    jfieldID native_canvas;
    jfieldID native_region;
    jfieldID native_parcel;
};
static no_t no;


// ----------------------------------------------------------------------------
// ----------------------------------------------------------------------------
// ----------------------------------------------------------------------------

static void SurfaceSession_init(JNIEnv* env, jobject clazz)
{
    sp<SurfaceComposerClient> client = new SurfaceComposerClient;
    client->incStrong(clazz);
    env->SetIntField(clazz, sso.client, (int)client.get());
}

static void SurfaceSession_destroy(JNIEnv* env, jobject clazz)
{
    SurfaceComposerClient* client =
            (SurfaceComposerClient*)env->GetIntField(clazz, sso.client);
    if (client != 0) {
        client->decStrong(clazz);
        env->SetIntField(clazz, sso.client, 0);
    }
}

static void SurfaceSession_kill(JNIEnv* env, jobject clazz)
{
    SurfaceComposerClient* client =
            (SurfaceComposerClient*)env->GetIntField(clazz, sso.client);
    if (client != 0) {
        client->dispose();
        client->decStrong(clazz);
        env->SetIntField(clazz, sso.client, 0);
    }
}

// ----------------------------------------------------------------------------

static sp<SurfaceControl> getSurfaceControl(JNIEnv* env, jobject clazz)
{
    SurfaceControl* const p =
        (SurfaceControl*)env->GetIntField(clazz, so.surfaceControl);
    return sp<SurfaceControl>(p);
}

static void setSurfaceControl(JNIEnv* env, jobject clazz,
        const sp<SurfaceControl>& surface)
{
    SurfaceControl* const p =
        (SurfaceControl*)env->GetIntField(clazz, so.surfaceControl);
    if (surface.get()) {
        surface->incStrong(clazz);
    }
    if (p) {
        p->decStrong(clazz);
    }
    env->SetIntField(clazz, so.surfaceControl, (int)surface.get());
}

static sp<Surface> getSurface(JNIEnv* env, jobject clazz)
{
    sp<Surface> result(Surface_getSurface(env, clazz));
    if (result == 0) {
=======
static const char* const OutOfResourcesException =
    "android/view/Surface$OutOfResourcesException";

static struct {
    jclass clazz;
    jfieldID mNativeSurface;
    jfieldID mNativeSurfaceControl;
    jfieldID mGenerationId;
    jfieldID mCanvas;
    jfieldID mCanvasSaveCount;
    jmethodID ctor;
} gSurfaceClassInfo;

static struct {
    jfieldID left;
    jfieldID top;
    jfieldID right;
    jfieldID bottom;
} gRectClassInfo;

static struct {
    jfieldID mNativeCanvas;
    jfieldID mSurfaceFormat;
} gCanvasClassInfo;

static struct {
    jfieldID width;
    jfieldID height;
    jfieldID refreshRate;
    jfieldID density;
    jfieldID xDpi;
    jfieldID yDpi;
    jfieldID secure;
} gPhysicalDisplayInfoClassInfo;


class ScreenshotPixelRef : public SkPixelRef {
public:
    ScreenshotPixelRef(SkColorTable* ctable) {
        fCTable = ctable;
        SkSafeRef(ctable);
        setImmutable();
    }

    virtual ~ScreenshotPixelRef() {
        SkSafeUnref(fCTable);
    }

    status_t update(const sp<IBinder>& display, int width, int height,
            int minLayer, int maxLayer, bool allLayers) {
        status_t res = (width > 0 && height > 0)
                ? (allLayers
                        ? mScreenshot.update(display, width, height)
                        : mScreenshot.update(display, width, height, minLayer, maxLayer))
                : mScreenshot.update(display);
        if (res != NO_ERROR) {
            return res;
        }

        return NO_ERROR;
    }

    uint32_t getWidth() const {
        return mScreenshot.getWidth();
    }

    uint32_t getHeight() const {
        return mScreenshot.getHeight();
    }

    uint32_t getStride() const {
        return mScreenshot.getStride();
    }

    uint32_t getFormat() const {
        return mScreenshot.getFormat();
    }

protected:
    // overrides from SkPixelRef
    virtual void* onLockPixels(SkColorTable** ct) {
        *ct = fCTable;
        return (void*)mScreenshot.getPixels();
    }

    virtual void onUnlockPixels() {
    }

private:
    ScreenshotClient mScreenshot;
    SkColorTable*    fCTable;

    typedef SkPixelRef INHERITED;
};


// ----------------------------------------------------------------------------

static sp<SurfaceControl> getSurfaceControl(JNIEnv* env, jobject surfaceObj) {
    return reinterpret_cast<SurfaceControl*>(
            env->GetIntField(surfaceObj, gSurfaceClassInfo.mNativeSurfaceControl));
}

static void setSurfaceControl(JNIEnv* env, jobject surfaceObj,
        const sp<SurfaceControl>& surface) {
    SurfaceControl* const p = reinterpret_cast<SurfaceControl*>(
            env->GetIntField(surfaceObj, gSurfaceClassInfo.mNativeSurfaceControl));
    if (surface.get()) {
        surface->incStrong(surfaceObj);
    }
    if (p) {
        p->decStrong(surfaceObj);
    }
    env->SetIntField(surfaceObj, gSurfaceClassInfo.mNativeSurfaceControl,
            reinterpret_cast<jint>(surface.get()));
}

static sp<Surface> getSurface(JNIEnv* env, jobject surfaceObj) {
    sp<Surface> result(android_view_Surface_getSurface(env, surfaceObj));
    if (result == NULL) {
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
        /*
         * if this method is called from the WindowManager's process, it means
         * the client is is not remote, and therefore is allowed to have
         * a Surface (data), so we create it here.
         * If we don't have a SurfaceControl, it means we're in a different
         * process.
         */

<<<<<<< HEAD
        SurfaceControl* const control =
            (SurfaceControl*)env->GetIntField(clazz, so.surfaceControl);
        if (control) {
            result = control->getSurface();
            if (result != 0) {
                result->incStrong(clazz);
                env->SetIntField(clazz, so.surface, (int)result.get());
=======
        SurfaceControl* const control = reinterpret_cast<SurfaceControl*>(
                env->GetIntField(surfaceObj, gSurfaceClassInfo.mNativeSurfaceControl));
        if (control) {
            result = control->getSurface();
            if (result != NULL) {
                result->incStrong(surfaceObj);
                env->SetIntField(surfaceObj, gSurfaceClassInfo.mNativeSurface,
                        reinterpret_cast<jint>(result.get()));
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
            }
        }
    }
    return result;
}

<<<<<<< HEAD
sp<ANativeWindow> android_Surface_getNativeWindow(
        JNIEnv* env, jobject clazz) {
    return getSurface(env, clazz);
}

bool android_Surface_isInstanceOf(JNIEnv* env, jobject obj) {
    jclass surfaceClass = env->FindClass(kSurfaceClassPathName);
    return env->IsInstanceOf(obj, surfaceClass);
}

sp<Surface> Surface_getSurface(JNIEnv* env, jobject clazz) {
    sp<Surface> surface((Surface*)env->GetIntField(clazz, so.surface));
    return surface;
}

void setSurface(JNIEnv* env, jobject clazz, const sp<Surface>& surface)
{
    Surface* const p = (Surface*)env->GetIntField(clazz, so.surface);
    if (surface.get()) {
        surface->incStrong(clazz);
    }
    if (p) {
        p->decStrong(clazz);
    }
    env->SetIntField(clazz, so.surface, (int)surface.get());
    // This test is conservative and it would be better to compare the ISurfaces
    if (p && p != surface.get()) {
        jint generationId = env->GetIntField(clazz, so.surfaceGenerationId);
        generationId++;
        env->SetIntField(clazz, so.surfaceGenerationId, generationId);
    }
}

// ----------------------------------------------------------------------------

static void Surface_init(
        JNIEnv* env, jobject clazz,
        jobject session,
        jint, jstring jname, jint dpy, jint w, jint h, jint format, jint flags)
{
    if (session == NULL) {
        doThrowNPE(env);
        return;
    }

    SurfaceComposerClient* client =
            (SurfaceComposerClient*)env->GetIntField(session, sso.client);

    sp<SurfaceControl> surface;
    if (jname == NULL) {
        surface = client->createSurface(dpy, w, h, format, flags);
    } else {
        const jchar* str = env->GetStringCritical(jname, 0);
        const String8 name(str, env->GetStringLength(jname));
        env->ReleaseStringCritical(jname, str);
        surface = client->createSurface(name, dpy, w, h, format, flags);
    }

    if (surface == 0) {
        jniThrowException(env, OutOfResourcesException, NULL);
        return;
    }
    setSurfaceControl(env, clazz, surface);
}

static void Surface_initFromSurfaceTexture(
        JNIEnv* env, jobject clazz, jobject jst)
{
    sp<SurfaceTexture> st(SurfaceTexture_getSurfaceTexture(env, jst));

=======
sp<ANativeWindow> android_view_Surface_getNativeWindow(JNIEnv* env, jobject surfaceObj) {
    return getSurface(env, surfaceObj);
}

bool android_view_Surface_isInstanceOf(JNIEnv* env, jobject obj) {
    return env->IsInstanceOf(obj, gSurfaceClassInfo.clazz);
}

sp<Surface> android_view_Surface_getSurface(JNIEnv* env, jobject surfaceObj) {
    return reinterpret_cast<Surface*>(
            env->GetIntField(surfaceObj, gSurfaceClassInfo.mNativeSurface));
}

static void setSurface(JNIEnv* env, jobject surfaceObj, const sp<Surface>& surface) {
    Surface* const p = reinterpret_cast<Surface*>(
            env->GetIntField(surfaceObj, gSurfaceClassInfo.mNativeSurface));
    if (surface.get()) {
        surface->incStrong(surfaceObj);
    }
    if (p) {
        p->decStrong(surfaceObj);
    }
    env->SetIntField(surfaceObj, gSurfaceClassInfo.mNativeSurface,
            reinterpret_cast<jint>(surface.get()));

    // This test is conservative and it would be better to compare the ISurfaces
    if (p && p != surface.get()) {
        jint generationId = env->GetIntField(surfaceObj,
                gSurfaceClassInfo.mGenerationId);
        generationId++;
        env->SetIntField(surfaceObj,
                gSurfaceClassInfo.mGenerationId, generationId);
    }
}

static sp<ISurfaceTexture> getISurfaceTexture(JNIEnv* env, jobject surfaceObj) {
    if (surfaceObj) {
        sp<Surface> surface(getSurface(env, surfaceObj));
        if (surface != NULL) {
            return surface->getSurfaceTexture();
        }
    }
    return NULL;
}

jobject android_view_Surface_createFromISurfaceTexture(JNIEnv* env,
        const sp<ISurfaceTexture>& surfaceTexture) {
    if (surfaceTexture == NULL) {
        return NULL;
    }

    sp<Surface> surface(new Surface(surfaceTexture));
    if (surface == NULL) {
        return NULL;
    }

    jobject surfaceObj = env->NewObject(gSurfaceClassInfo.clazz, gSurfaceClassInfo.ctor);
    if (surfaceObj == NULL) {
        if (env->ExceptionCheck()) {
            ALOGE("Could not create instance of Surface from ISurfaceTexture.");
            LOGE_EX(env);
            env->ExceptionClear();
        }
        return NULL;
    }

    setSurface(env, surfaceObj, surface);
    return surfaceObj;
}


// ----------------------------------------------------------------------------

static void nativeCreate(JNIEnv* env, jobject surfaceObj, jobject sessionObj,
        jstring nameStr, jint w, jint h, jint format, jint flags) {
    ScopedUtfChars name(env, nameStr);
    sp<SurfaceComposerClient> client(android_view_SurfaceSession_getClient(env, sessionObj));

    sp<SurfaceControl> surface = client->createSurface(
            String8(name.c_str()), w, h, format, flags);
    if (surface == NULL) {
        jniThrowException(env, OutOfResourcesException, NULL);
        return;
    }

    setSurfaceControl(env, surfaceObj, surface);
}

static void nativeCreateFromSurfaceTexture(JNIEnv* env, jobject surfaceObj,
        jobject surfaceTextureObj) {
    sp<SurfaceTexture> st(SurfaceTexture_getSurfaceTexture(env, surfaceTextureObj));
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
    if (st == NULL) {
        jniThrowException(env, "java/lang/IllegalArgumentException",
                "SurfaceTexture has already been released");
        return;
    }
<<<<<<< HEAD
=======

>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
    sp<ISurfaceTexture> bq = st->getBufferQueue();

    sp<Surface> surface(new Surface(bq));
    if (surface == NULL) {
        jniThrowException(env, OutOfResourcesException, NULL);
        return;
    }
<<<<<<< HEAD
    setSurfaceControl(env, clazz, NULL);
    setSurface(env, clazz, surface);
}

static void Surface_initParcel(JNIEnv* env, jobject clazz, jobject argParcel)
{
    Parcel* parcel = (Parcel*)env->GetIntField(argParcel, no.native_parcel);
    if (parcel == NULL) {
        doThrowNPE(env);
        return;
    }

    sp<Surface> sur(Surface::readFromParcel(*parcel));
    setSurface(env, clazz, sur);
}

static jint Surface_getIdentity(JNIEnv* env, jobject clazz)
{
    const sp<SurfaceControl>& control(getSurfaceControl(env, clazz));
    if (control != 0) return (jint) control->getIdentity();
    const sp<Surface>& surface(getSurface(env, clazz));
    if (surface != 0) return (jint) surface->getIdentity();
    return -1;
}

static void Surface_destroy(JNIEnv* env, jobject clazz, uintptr_t *ostack)
{
    const sp<SurfaceControl>& surface(getSurfaceControl(env, clazz));
    if (SurfaceControl::isValid(surface)) {
        surface->clear();
    }
    setSurfaceControl(env, clazz, 0);
    setSurface(env, clazz, 0);
}

static void Surface_release(JNIEnv* env, jobject clazz, uintptr_t *ostack)
{
    setSurfaceControl(env, clazz, 0);
    setSurface(env, clazz, 0);
}

static jboolean Surface_isValid(JNIEnv* env, jobject clazz)
{
    const sp<SurfaceControl>& surfaceControl(getSurfaceControl(env, clazz));
    if (surfaceControl != 0) {
        return SurfaceControl::isValid(surfaceControl) ? JNI_TRUE : JNI_FALSE;
    }
    const sp<Surface>& surface(getSurface(env, clazz));
    return Surface::isValid(surface) ? JNI_TRUE : JNI_FALSE;
}

static jboolean Surface_isConsumerRunningBehind(JNIEnv* env, jobject clazz)
{
    int value = 0;
    const sp<Surface>& surface(getSurface(env, clazz));
    if (!Surface::isValid(surface)) {
        doThrowIAE(env);
        return 0;
    }
    ANativeWindow* anw = static_cast<ANativeWindow *>(surface.get());
    anw->query(anw, NATIVE_WINDOW_CONSUMER_RUNNING_BEHIND, &value);
    return (jboolean)value;
}

static inline SkBitmap::Config convertPixelFormat(PixelFormat format)
{
=======

    setSurface(env, surfaceObj, surface);
}

static void nativeRelease(JNIEnv* env, jobject surfaceObj) {
    setSurfaceControl(env, surfaceObj, NULL);
    setSurface(env, surfaceObj, NULL);
}

static void nativeDestroy(JNIEnv* env, jobject surfaceObj) {
    sp<SurfaceControl> surfaceControl(getSurfaceControl(env, surfaceObj));
    if (SurfaceControl::isValid(surfaceControl)) {
        surfaceControl->clear();
    }
    setSurfaceControl(env, surfaceObj, NULL);
    setSurface(env, surfaceObj, NULL);
}

static jboolean nativeIsValid(JNIEnv* env, jobject surfaceObj) {
    sp<SurfaceControl> surfaceControl(getSurfaceControl(env, surfaceObj));
    if (surfaceControl != NULL) {
        return SurfaceControl::isValid(surfaceControl) ? JNI_TRUE : JNI_FALSE;
    }

    sp<Surface> surface(getSurface(env, surfaceObj));
    return Surface::isValid(surface) ? JNI_TRUE : JNI_FALSE;
}

static jint nativeGetIdentity(JNIEnv* env, jobject surfaceObj) {
    sp<SurfaceControl> control(getSurfaceControl(env, surfaceObj));
    if (control != NULL) {
        return jint(control->getIdentity());
    }

    sp<Surface> surface(getSurface(env, surfaceObj));
    if (surface != NULL) {
        return jint(surface->getIdentity());
    }

    return -1;
}

static jboolean nativeIsConsumerRunningBehind(JNIEnv* env, jobject surfaceObj) {
    sp<Surface> surface(getSurface(env, surfaceObj));
    if (!Surface::isValid(surface)) {
        doThrowIAE(env);
        return JNI_FALSE;
    }

    int value = 0;
    ANativeWindow* anw = static_cast<ANativeWindow*>(surface.get());
    anw->query(anw, NATIVE_WINDOW_CONSUMER_RUNNING_BEHIND, &value);
    return value;
}

static inline SkBitmap::Config convertPixelFormat(PixelFormat format) {
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
    /* note: if PIXEL_FORMAT_RGBX_8888 means that all alpha bytes are 0xFF, then
        we can map to SkBitmap::kARGB_8888_Config, and optionally call
        bitmap.setIsOpaque(true) on the resulting SkBitmap (as an accelerator)
    */
    switch (format) {
    case PIXEL_FORMAT_RGBX_8888:    return SkBitmap::kARGB_8888_Config;
    case PIXEL_FORMAT_RGBA_8888:    return SkBitmap::kARGB_8888_Config;
    case PIXEL_FORMAT_RGBA_4444:    return SkBitmap::kARGB_4444_Config;
    case PIXEL_FORMAT_RGB_565:      return SkBitmap::kRGB_565_Config;
    case PIXEL_FORMAT_A_8:          return SkBitmap::kA8_Config;
    default:                        return SkBitmap::kNo_Config;
    }
}

<<<<<<< HEAD
static jobject Surface_lockCanvas(JNIEnv* env, jobject clazz, jobject dirtyRect)
{
    const sp<Surface>& surface(getSurface(env, clazz));
    if (!Surface::isValid(surface)) {
        doThrowIAE(env);
        return 0;
=======
static jobject nativeLockCanvas(JNIEnv* env, jobject surfaceObj, jobject dirtyRectObj) {
    sp<Surface> surface(getSurface(env, surfaceObj));
    if (!Surface::isValid(surface)) {
        doThrowIAE(env);
        return NULL;
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
    }

    // get dirty region
    Region dirtyRegion;
<<<<<<< HEAD
    if (dirtyRect) {
        Rect dirty;
        dirty.left  = env->GetIntField(dirtyRect, ro.l);
        dirty.top   = env->GetIntField(dirtyRect, ro.t);
        dirty.right = env->GetIntField(dirtyRect, ro.r);
        dirty.bottom= env->GetIntField(dirtyRect, ro.b);
=======
    if (dirtyRectObj) {
        Rect dirty;
        dirty.left = env->GetIntField(dirtyRectObj, gRectClassInfo.left);
        dirty.top = env->GetIntField(dirtyRectObj, gRectClassInfo.top);
        dirty.right = env->GetIntField(dirtyRectObj, gRectClassInfo.right);
        dirty.bottom = env->GetIntField(dirtyRectObj, gRectClassInfo.bottom);
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
        if (!dirty.isEmpty()) {
            dirtyRegion.set(dirty);
        }
    } else {
<<<<<<< HEAD
        dirtyRegion.set(Rect(0x3FFF,0x3FFF));
=======
        dirtyRegion.set(Rect(0x3FFF, 0x3FFF));
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
    }

    Surface::SurfaceInfo info;
    status_t err = surface->lock(&info, &dirtyRegion);
    if (err < 0) {
        const char* const exception = (err == NO_MEMORY) ?
<<<<<<< HEAD
            OutOfResourcesException :
            "java/lang/IllegalArgumentException";
        jniThrowException(env, exception, NULL);
        return 0;
    }

    // Associate a SkCanvas object to this surface
    jobject canvas = env->GetObjectField(clazz, so.canvas);
    env->SetIntField(canvas, co.surfaceFormat, info.format);

    SkCanvas* nativeCanvas = (SkCanvas*)env->GetIntField(canvas, no.native_canvas);
=======
                OutOfResourcesException :
                "java/lang/IllegalArgumentException";
        jniThrowException(env, exception, NULL);
        return NULL;
    }

    // Associate a SkCanvas object to this surface
    jobject canvasObj = env->GetObjectField(surfaceObj, gSurfaceClassInfo.mCanvas);
    env->SetIntField(canvasObj, gCanvasClassInfo.mSurfaceFormat, info.format);

    SkCanvas* nativeCanvas = reinterpret_cast<SkCanvas*>(
            env->GetIntField(canvasObj, gCanvasClassInfo.mNativeCanvas));
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
    SkBitmap bitmap;
    ssize_t bpr = info.s * bytesPerPixel(info.format);
    bitmap.setConfig(convertPixelFormat(info.format), info.w, info.h, bpr);
    if (info.format == PIXEL_FORMAT_RGBX_8888) {
        bitmap.setIsOpaque(true);
    }
    if (info.w > 0 && info.h > 0) {
        bitmap.setPixels(info.bits);
    } else {
        // be safe with an empty bitmap.
        bitmap.setPixels(NULL);
    }
    nativeCanvas->setBitmapDevice(bitmap);

    SkRegion clipReg;
    if (dirtyRegion.isRect()) { // very common case
        const Rect b(dirtyRegion.getBounds());
        clipReg.setRect(b.left, b.top, b.right, b.bottom);
    } else {
        size_t count;
        Rect const* r = dirtyRegion.getArray(&count);
        while (count) {
            clipReg.op(r->left, r->top, r->right, r->bottom, SkRegion::kUnion_Op);
            r++, count--;
        }
    }

    nativeCanvas->clipRegion(clipReg);

    int saveCount = nativeCanvas->save();
<<<<<<< HEAD
    env->SetIntField(clazz, so.saveCount, saveCount);

    if (dirtyRect) {
        const Rect& bounds(dirtyRegion.getBounds());
        env->SetIntField(dirtyRect, ro.l, bounds.left);
        env->SetIntField(dirtyRect, ro.t, bounds.top);
        env->SetIntField(dirtyRect, ro.r, bounds.right);
        env->SetIntField(dirtyRect, ro.b, bounds.bottom);
    }

    return canvas;
}

static void Surface_unlockCanvasAndPost(
        JNIEnv* env, jobject clazz, jobject argCanvas)
{
    jobject canvas = env->GetObjectField(clazz, so.canvas);
    if (env->IsSameObject(canvas, argCanvas) == JNI_FALSE) {
=======
    env->SetIntField(surfaceObj, gSurfaceClassInfo.mCanvasSaveCount, saveCount);

    if (dirtyRectObj) {
        const Rect& bounds(dirtyRegion.getBounds());
        env->SetIntField(dirtyRectObj, gRectClassInfo.left, bounds.left);
        env->SetIntField(dirtyRectObj, gRectClassInfo.top, bounds.top);
        env->SetIntField(dirtyRectObj, gRectClassInfo.right, bounds.right);
        env->SetIntField(dirtyRectObj, gRectClassInfo.bottom, bounds.bottom);
    }

    return canvasObj;
}

static void nativeUnlockCanvasAndPost(JNIEnv* env, jobject surfaceObj, jobject canvasObj) {
    jobject ownCanvasObj = env->GetObjectField(surfaceObj, gSurfaceClassInfo.mCanvas);
    if (!env->IsSameObject(ownCanvasObj, canvasObj)) {
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
        doThrowIAE(env);
        return;
    }

<<<<<<< HEAD
    const sp<Surface>& surface(getSurface(env, clazz));
    if (!Surface::isValid(surface))
        return;

    // detach the canvas from the surface
    SkCanvas* nativeCanvas = (SkCanvas*)env->GetIntField(canvas, no.native_canvas);
    int saveCount = env->GetIntField(clazz, so.saveCount);
    nativeCanvas->restoreToCount(saveCount);
    nativeCanvas->setBitmapDevice(SkBitmap());
    env->SetIntField(clazz, so.saveCount, 0);
=======
    sp<Surface> surface(getSurface(env, surfaceObj));
    if (!Surface::isValid(surface)) {
        return;
    }

    // detach the canvas from the surface
    SkCanvas* nativeCanvas = reinterpret_cast<SkCanvas*>(
            env->GetIntField(canvasObj, gCanvasClassInfo.mNativeCanvas));
    int saveCount = env->GetIntField(surfaceObj, gSurfaceClassInfo.mCanvasSaveCount);
    nativeCanvas->restoreToCount(saveCount);
    nativeCanvas->setBitmapDevice(SkBitmap());
    env->SetIntField(surfaceObj, gSurfaceClassInfo.mCanvasSaveCount, 0);
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a

    // unlock surface
    status_t err = surface->unlockAndPost();
    if (err < 0) {
        doThrowIAE(env);
    }
}

<<<<<<< HEAD
static void Surface_unlockCanvas(
        JNIEnv* env, jobject clazz, jobject argCanvas)
{
    // XXX: this API has been removed
    doThrowIAE(env);
}

static void Surface_openTransaction(
        JNIEnv* env, jobject clazz)
{
    SurfaceComposerClient::openGlobalTransaction();
}

static void Surface_closeTransaction(
        JNIEnv* env, jobject clazz)
{
    SurfaceComposerClient::closeGlobalTransaction();
}

static void Surface_setOrientation(
        JNIEnv* env, jobject clazz, jint display, jint orientation, jint flags)
{
    int err = SurfaceComposerClient::setOrientation(display, orientation, flags);
    if (err < 0) {
        doThrowIAE(env);
    }
}

static void Surface_freezeDisplay(
        JNIEnv* env, jobject clazz, jint display)
{
    int err = SurfaceComposerClient::freezeDisplay(display, 0);
    if (err < 0) {
        doThrowIAE(env);
    }
}

static void Surface_unfreezeDisplay(
        JNIEnv* env, jobject clazz, jint display)
{
    int err = SurfaceComposerClient::unfreezeDisplay(display, 0);
    if (err < 0) {
        doThrowIAE(env);
    }
}

class ScreenshotPixelRef : public SkPixelRef {
public:
    ScreenshotPixelRef(SkColorTable* ctable) {
        fCTable = ctable;
        SkSafeRef(ctable);
        setImmutable();
    }
    virtual ~ScreenshotPixelRef() {
        SkSafeUnref(fCTable);
    }

    status_t update(int width, int height, int minLayer, int maxLayer, bool allLayers) {
        status_t res = (width > 0 && height > 0)
                ? (allLayers
                        ? mScreenshot.update(width, height)
                        : mScreenshot.update(width, height, minLayer, maxLayer))
                : mScreenshot.update();
        if (res != NO_ERROR) {
            return res;
        }

        return NO_ERROR;
    }

    uint32_t getWidth() const {
        return mScreenshot.getWidth();
    }

    uint32_t getHeight() const {
        return mScreenshot.getHeight();
    }

    uint32_t getStride() const {
        return mScreenshot.getStride();
    }

    uint32_t getFormat() const {
        return mScreenshot.getFormat();
    }

protected:
    // overrides from SkPixelRef
    virtual void* onLockPixels(SkColorTable** ct) {
        *ct = fCTable;
        return (void*)mScreenshot.getPixels();
    }

    virtual void onUnlockPixels() {
    }

private:
    ScreenshotClient mScreenshot;
    SkColorTable*    fCTable;

    typedef SkPixelRef INHERITED;
};

static jobject doScreenshot(JNIEnv* env, jobject clazz, jint width, jint height,
        jint minLayer, jint maxLayer, bool allLayers)
{
    ScreenshotPixelRef* pixels = new ScreenshotPixelRef(NULL);
    if (pixels->update(width, height, minLayer, maxLayer, allLayers) != NO_ERROR) {
        delete pixels;
        return 0;
=======
static jobject nativeScreenshot(JNIEnv* env, jclass clazz, jobject displayTokenObj,
        jint width, jint height, jint minLayer, jint maxLayer, bool allLayers) {
    sp<IBinder> displayToken = ibinderForJavaObject(env, displayTokenObj);
    if (displayToken == NULL) {
        return NULL;
    }

    ScreenshotPixelRef* pixels = new ScreenshotPixelRef(NULL);
    if (pixels->update(displayToken, width, height,
            minLayer, maxLayer, allLayers) != NO_ERROR) {
        delete pixels;
        return NULL;
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
    }

    uint32_t w = pixels->getWidth();
    uint32_t h = pixels->getHeight();
    uint32_t s = pixels->getStride();
    uint32_t f = pixels->getFormat();
    ssize_t bpr = s * android::bytesPerPixel(f);

    SkBitmap* bitmap = new SkBitmap();
    bitmap->setConfig(convertPixelFormat(f), w, h, bpr);
    if (f == PIXEL_FORMAT_RGBX_8888) {
        bitmap->setIsOpaque(true);
    }

    if (w > 0 && h > 0) {
        bitmap->setPixelRef(pixels)->unref();
        bitmap->lockPixels();
    } else {
        // be safe with an empty bitmap.
        delete pixels;
        bitmap->setPixels(NULL);
    }

    return GraphicsJNI::createBitmap(env, bitmap, false, NULL);
}

<<<<<<< HEAD
static jobject Surface_screenshotAll(JNIEnv* env, jobject clazz, jint width, jint height)
{
    return doScreenshot(env, clazz, width, height, 0, 0, true);
}

static jobject Surface_screenshot(JNIEnv* env, jobject clazz, jint width, jint height,
        jint minLayer, jint maxLayer)
{
    return doScreenshot(env, clazz, width, height, minLayer, maxLayer, false);
}

static void Surface_setLayer(
        JNIEnv* env, jobject clazz, jint zorder)
{
    const sp<SurfaceControl>& surface(getSurfaceControl(env, clazz));
    if (surface == 0) return;
    status_t err = surface->setLayer(zorder);
    if (err<0 && err!=NO_INIT) {
=======
static void nativeOpenTransaction(JNIEnv* env, jclass clazz) {
    SurfaceComposerClient::openGlobalTransaction();
}

static void nativeCloseTransaction(JNIEnv* env, jclass clazz) {
    SurfaceComposerClient::closeGlobalTransaction();
}

static void nativeSetAnimationTransaction(JNIEnv* env, jclass clazz) {
    SurfaceComposerClient::setAnimationTransaction();
}

static void nativeSetLayer(JNIEnv* env, jobject surfaceObj, jint zorder) {
    sp<SurfaceControl> surface(getSurfaceControl(env, surfaceObj));
    if (surface == NULL) return;

    status_t err = surface->setLayer(zorder);
    if (err < 0 && err != NO_INIT) {
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
        doThrowIAE(env);
    }
}

<<<<<<< HEAD
static void Surface_setPosition(
        JNIEnv* env, jobject clazz, jfloat x, jfloat y)
{
    const sp<SurfaceControl>& surface(getSurfaceControl(env, clazz));
    if (surface == 0) return;
    status_t err = surface->setPosition(x, y);
    if (err<0 && err!=NO_INIT) {
=======
static void nativeSetPosition(JNIEnv* env, jobject surfaceObj, jfloat x, jfloat y) {
    sp<SurfaceControl> surface(getSurfaceControl(env, surfaceObj));
    if (surface == NULL) return;

    status_t err = surface->setPosition(x, y);
    if (err < 0 && err != NO_INIT) {
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
        doThrowIAE(env);
    }
}

<<<<<<< HEAD
static void Surface_setSize(
        JNIEnv* env, jobject clazz, jint w, jint h)
{
    const sp<SurfaceControl>& surface(getSurfaceControl(env, clazz));
    if (surface == 0) return;
    status_t err = surface->setSize(w, h);
    if (err<0 && err!=NO_INIT) {
=======
static void nativeSetSize(JNIEnv* env, jobject surfaceObj, jint w, jint h) {
    sp<SurfaceControl> surface(getSurfaceControl(env, surfaceObj));
    if (surface == NULL) return;

    status_t err = surface->setSize(w, h);
    if (err < 0 && err != NO_INIT) {
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
        doThrowIAE(env);
    }
}

<<<<<<< HEAD
static void Surface_hide(
        JNIEnv* env, jobject clazz)
{
    const sp<SurfaceControl>& surface(getSurfaceControl(env, clazz));
    if (surface == 0) return;
    status_t err = surface->hide();
    if (err<0 && err!=NO_INIT) {
        doThrowIAE(env);
    }
}

static void Surface_show(
        JNIEnv* env, jobject clazz)
{
    const sp<SurfaceControl>& surface(getSurfaceControl(env, clazz));
    if (surface == 0) return;
    status_t err = surface->show();
    if (err<0 && err!=NO_INIT) {
=======
static void nativeSetFlags(JNIEnv* env, jobject surfaceObj, jint flags, jint mask) {
    sp<SurfaceControl> surface(getSurfaceControl(env, surfaceObj));
    if (surface == NULL) return;

    status_t err = surface->setFlags(flags, mask);
    if (err < 0 && err != NO_INIT) {
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
        doThrowIAE(env);
    }
}

<<<<<<< HEAD
static void Surface_freeze(
        JNIEnv* env, jobject clazz)
{
    const sp<SurfaceControl>& surface(getSurfaceControl(env, clazz));
    if (surface == 0) return;
    status_t err = surface->freeze();
    if (err<0 && err!=NO_INIT) {
        doThrowIAE(env);
    }
}

static void Surface_unfreeze(
        JNIEnv* env, jobject clazz)
{
    const sp<SurfaceControl>& surface(getSurfaceControl(env, clazz));
    if (surface == 0) return;
    status_t err = surface->unfreeze();
    if (err<0 && err!=NO_INIT) {
        doThrowIAE(env);
    }
}

static void Surface_setFlags(
        JNIEnv* env, jobject clazz, jint flags, jint mask)
{
    const sp<SurfaceControl>& surface(getSurfaceControl(env, clazz));
    if (surface == 0) return;
    status_t err = surface->setFlags(flags, mask);
    if (err<0 && err!=NO_INIT) {
        doThrowIAE(env);
    }
}

static void Surface_setTransparentRegion(
        JNIEnv* env, jobject clazz, jobject argRegion)
{
    const sp<SurfaceControl>& surface(getSurfaceControl(env, clazz));
    if (surface == 0) return;
    SkRegion* nativeRegion = (SkRegion*)env->GetIntField(argRegion, no.native_region);

    const SkIRect& b(nativeRegion->getBounds());
    Region reg(Rect(b.fLeft, b.fTop, b.fRight, b.fBottom));
    if (nativeRegion->isComplex()) {
        SkRegion::Iterator it(*nativeRegion);
=======
static void nativeSetTransparentRegionHint(JNIEnv* env, jobject surfaceObj, jobject regionObj) {
    sp<SurfaceControl> surface(getSurfaceControl(env, surfaceObj));
    if (surface == NULL) return;

    SkRegion* region = android_graphics_Region_getSkRegion(env, regionObj);
    if (!region) {
        doThrowIAE(env);
        return;
    }

    const SkIRect& b(region->getBounds());
    Region reg(Rect(b.fLeft, b.fTop, b.fRight, b.fBottom));
    if (region->isComplex()) {
        SkRegion::Iterator it(*region);
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
        while (!it.done()) {
            const SkIRect& r(it.rect());
            reg.addRectUnchecked(r.fLeft, r.fTop, r.fRight, r.fBottom);
            it.next();
        }
    }

    status_t err = surface->setTransparentRegionHint(reg);
<<<<<<< HEAD
    if (err<0 && err!=NO_INIT) {
=======
    if (err < 0 && err != NO_INIT) {
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
        doThrowIAE(env);
    }
}

<<<<<<< HEAD
static void Surface_setAlpha(
        JNIEnv* env, jobject clazz, jfloat alpha)
{
    const sp<SurfaceControl>& surface(getSurfaceControl(env, clazz));
    if (surface == 0) return;
    status_t err = surface->setAlpha(alpha);
    if (err<0 && err!=NO_INIT) {
=======
static void nativeSetAlpha(JNIEnv* env, jobject surfaceObj, jfloat alpha) {
    sp<SurfaceControl> surface(getSurfaceControl(env, surfaceObj));
    if (surface == NULL) return;

    status_t err = surface->setAlpha(alpha);
    if (err < 0 && err != NO_INIT) {
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
        doThrowIAE(env);
    }
}

<<<<<<< HEAD
static void Surface_setMatrix(
        JNIEnv* env, jobject clazz,
        jfloat dsdx, jfloat dtdx, jfloat dsdy, jfloat dtdy)
{
    const sp<SurfaceControl>& surface(getSurfaceControl(env, clazz));
    if (surface == 0) return;
    status_t err = surface->setMatrix(dsdx, dtdx, dsdy, dtdy);
    if (err<0 && err!=NO_INIT) {
=======
static void nativeSetMatrix(JNIEnv* env, jobject surfaceObj,
        jfloat dsdx, jfloat dtdx, jfloat dsdy, jfloat dtdy) {
    sp<SurfaceControl> surface(getSurfaceControl(env, surfaceObj));
    if (surface == NULL) return;

    status_t err = surface->setMatrix(dsdx, dtdx, dsdy, dtdy);
    if (err < 0 && err != NO_INIT) {
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
        doThrowIAE(env);
    }
}

<<<<<<< HEAD
static void Surface_setFreezeTint(
        JNIEnv* env, jobject clazz,
        jint tint)
{
    const sp<SurfaceControl>& surface(getSurfaceControl(env, clazz));
    if (surface == 0) return;
    status_t err = surface->setFreezeTint(tint);
    if (err<0 && err!=NO_INIT) {
=======
static void nativeSetWindowCrop(JNIEnv* env, jobject surfaceObj, jobject cropObj) {
    const sp<SurfaceControl>& surface(getSurfaceControl(env, surfaceObj));
    if (surface == NULL) return;

    Rect crop;
    if (cropObj) {
        crop.left = env->GetIntField(cropObj, gRectClassInfo.left);
        crop.top = env->GetIntField(cropObj, gRectClassInfo.top);
        crop.right = env->GetIntField(cropObj, gRectClassInfo.right);
        crop.bottom = env->GetIntField(cropObj, gRectClassInfo.bottom);
    } else {
        crop.left = crop.top = crop.right = crop.bottom = 0;
    }

    status_t err = surface->setCrop(crop);
    if (err < 0 && err != NO_INIT) {
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
        doThrowIAE(env);
    }
}

<<<<<<< HEAD
static void Surface_setWindowCrop(JNIEnv* env, jobject thiz, jobject crop)
{
    const sp<SurfaceControl>& surface(getSurfaceControl(env, thiz));
    if (surface == 0) return;

    Rect nativeCrop;
    if (crop) {
        nativeCrop.left  = env->GetIntField(crop, ro.l);
        nativeCrop.top   = env->GetIntField(crop, ro.t);
        nativeCrop.right = env->GetIntField(crop, ro.r);
        nativeCrop.bottom= env->GetIntField(crop, ro.b);
    } else {
        nativeCrop.left = nativeCrop.top = nativeCrop.right =
                nativeCrop.bottom = 0;
    }

    status_t err = surface->setCrop(nativeCrop);
    if (err<0 && err!=NO_INIT) {
=======
static void nativeSetLayerStack(JNIEnv* env, jobject surfaceObj, jint layerStack) {
    sp<SurfaceControl> surface(getSurfaceControl(env, surfaceObj));
    if (surface == NULL) return;

    status_t err = surface->setLayerStack(layerStack);
    if (err < 0 && err != NO_INIT) {
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
        doThrowIAE(env);
    }
}

<<<<<<< HEAD
// ----------------------------------------------------------------------------

static void Surface_copyFrom(
        JNIEnv* env, jobject clazz, jobject other)
{
    if (clazz == other)
        return;

    if (other == NULL) {
        doThrowNPE(env);
        return;
    }

=======
static jobject nativeGetBuiltInDisplay(JNIEnv* env, jclass clazz, jint id) {
    sp<IBinder> token(SurfaceComposerClient::getBuiltInDisplay(id));
    return javaObjectForIBinder(env, token);
}

static jobject nativeCreateDisplay(JNIEnv* env, jclass clazz, jstring nameObj,
        jboolean secure) {
    ScopedUtfChars name(env, nameObj);
    sp<IBinder> token(SurfaceComposerClient::createDisplay(
            String8(name.c_str()), bool(secure)));
    return javaObjectForIBinder(env, token);
}

static void nativeSetDisplaySurface(JNIEnv* env, jclass clazz,
        jobject tokenObj, jobject surfaceObj) {
    sp<IBinder> token(ibinderForJavaObject(env, tokenObj));
    if (token == NULL) return;

    sp<ISurfaceTexture> surfaceTexture(getISurfaceTexture(env, surfaceObj));
    SurfaceComposerClient::setDisplaySurface(token, surfaceTexture);
}

static void nativeSetDisplayLayerStack(JNIEnv* env, jclass clazz,
        jobject tokenObj, jint layerStack) {
    sp<IBinder> token(ibinderForJavaObject(env, tokenObj));
    if (token == NULL) return;

    SurfaceComposerClient::setDisplayLayerStack(token, layerStack);
}

static void nativeSetDisplayProjection(JNIEnv* env, jclass clazz,
        jobject tokenObj, jint orientation, jobject layerStackRectObj, jobject displayRectObj) {
    sp<IBinder> token(ibinderForJavaObject(env, tokenObj));
    if (token == NULL) return;

    Rect layerStackRect;
    layerStackRect.left = env->GetIntField(layerStackRectObj, gRectClassInfo.left);
    layerStackRect.top = env->GetIntField(layerStackRectObj, gRectClassInfo.top);
    layerStackRect.right = env->GetIntField(layerStackRectObj, gRectClassInfo.right);
    layerStackRect.bottom = env->GetIntField(layerStackRectObj, gRectClassInfo.bottom);

    Rect displayRect;
    displayRect.left = env->GetIntField(displayRectObj, gRectClassInfo.left);
    displayRect.top = env->GetIntField(displayRectObj, gRectClassInfo.top);
    displayRect.right = env->GetIntField(displayRectObj, gRectClassInfo.right);
    displayRect.bottom = env->GetIntField(displayRectObj, gRectClassInfo.bottom);

    SurfaceComposerClient::setDisplayProjection(token, orientation, layerStackRect, displayRect);
}

static jboolean nativeGetDisplayInfo(JNIEnv* env, jclass clazz,
        jobject tokenObj, jobject infoObj) {
    sp<IBinder> token(ibinderForJavaObject(env, tokenObj));
    if (token == NULL) return JNI_FALSE;

    DisplayInfo info;
    if (SurfaceComposerClient::getDisplayInfo(token, &info)) {
        return JNI_FALSE;
    }

    env->SetIntField(infoObj, gPhysicalDisplayInfoClassInfo.width, info.w);
    env->SetIntField(infoObj, gPhysicalDisplayInfoClassInfo.height, info.h);
    env->SetFloatField(infoObj, gPhysicalDisplayInfoClassInfo.refreshRate, info.fps);
    env->SetFloatField(infoObj, gPhysicalDisplayInfoClassInfo.density, info.density);
    env->SetFloatField(infoObj, gPhysicalDisplayInfoClassInfo.xDpi, info.xdpi);
    env->SetFloatField(infoObj, gPhysicalDisplayInfoClassInfo.yDpi, info.ydpi);
    env->SetBooleanField(infoObj, gPhysicalDisplayInfoClassInfo.secure, info.secure);
    return JNI_TRUE;
}

static void nativeBlankDisplay(JNIEnv* env, jclass clazz, jobject tokenObj) {
    sp<IBinder> token(ibinderForJavaObject(env, tokenObj));
    if (token == NULL) return;

    ALOGD_IF_SLOW(100, "Excessive delay in blankDisplay() while turning screen off");
    SurfaceComposerClient::blankDisplay(token);
}

static void nativeUnblankDisplay(JNIEnv* env, jclass clazz, jobject tokenObj) {
    sp<IBinder> token(ibinderForJavaObject(env, tokenObj));
    if (token == NULL) return;

    ALOGD_IF_SLOW(100, "Excessive delay in unblankDisplay() while turning screen on");
    SurfaceComposerClient::unblankDisplay(token);
}

// ----------------------------------------------------------------------------

static void nativeCopyFrom(JNIEnv* env, jobject surfaceObj, jobject otherObj) {
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
    /*
     * This is used by the WindowManagerService just after constructing
     * a Surface and is necessary for returning the Surface reference to
     * the caller. At this point, we should only have a SurfaceControl.
     */

<<<<<<< HEAD
    const sp<SurfaceControl>& surface = getSurfaceControl(env, clazz);
    const sp<SurfaceControl>& rhs = getSurfaceControl(env, other);
    if (!SurfaceControl::isSameSurface(surface, rhs)) {
        // we reassign the surface only if it's a different one
        // otherwise we would loose our client-side state.
        setSurfaceControl(env, clazz, rhs);
    }
}

static void Surface_transferFrom(
        JNIEnv* env, jobject clazz, jobject other)
{
    if (clazz == other)
        return;

    if (other == NULL) {
        doThrowNPE(env);
        return;
    }

    sp<SurfaceControl> control(getSurfaceControl(env, other));
    sp<Surface> surface(Surface_getSurface(env, other));
    setSurfaceControl(env, clazz, control);
    setSurface(env, clazz, surface);
    setSurfaceControl(env, other, 0);
    setSurface(env, other, 0);
}

static void Surface_readFromParcel(
        JNIEnv* env, jobject clazz, jobject argParcel)
{
    Parcel* parcel = (Parcel*)env->GetIntField( argParcel, no.native_parcel);
=======
    sp<SurfaceControl> surface(getSurfaceControl(env, surfaceObj));
    sp<SurfaceControl> other(getSurfaceControl(env, otherObj));
    if (!SurfaceControl::isSameSurface(surface, other)) {
        // we reassign the surface only if it's a different one
        // otherwise we would loose our client-side state.
        setSurfaceControl(env, surfaceObj, other);
    }
}

static void nativeTransferFrom(JNIEnv* env, jobject surfaceObj, jobject otherObj) {
    sp<SurfaceControl> control(getSurfaceControl(env, otherObj));
    sp<Surface> surface(android_view_Surface_getSurface(env, otherObj));
    setSurfaceControl(env, surfaceObj, control);
    setSurface(env, surfaceObj, surface);
    setSurfaceControl(env, otherObj, NULL);
    setSurface(env, otherObj, NULL);
}

static void nativeReadFromParcel(JNIEnv* env, jobject surfaceObj, jobject parcelObj) {
    Parcel* parcel = parcelForJavaObject(env, parcelObj);
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
    if (parcel == NULL) {
        doThrowNPE(env);
        return;
    }

<<<<<<< HEAD
    sp<Surface> sur(Surface::readFromParcel(*parcel));
    setSurface(env, clazz, sur);
}

static void Surface_writeToParcel(
        JNIEnv* env, jobject clazz, jobject argParcel, jint flags)
{
    Parcel* parcel = (Parcel*)env->GetIntField(
            argParcel, no.native_parcel);

=======
    sp<Surface> surface(Surface::readFromParcel(*parcel));
    setSurfaceControl(env, surfaceObj, NULL);
    setSurface(env, surfaceObj, surface);
}

static void nativeWriteToParcel(JNIEnv* env, jobject surfaceObj, jobject parcelObj) {
    Parcel* parcel = parcelForJavaObject(env, parcelObj);
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
    if (parcel == NULL) {
        doThrowNPE(env);
        return;
    }

    // The Java instance may have a SurfaceControl (in the case of the
    // WindowManager or a system app). In that case, we defer to the
    // SurfaceControl to send its ISurface. Otherwise, if the Surface is
    // available we let it parcel itself. Finally, if the Surface is also
    // NULL we fall back to using the SurfaceControl path which sends an
    // empty surface; this matches legacy behavior.
<<<<<<< HEAD
    const sp<SurfaceControl>& control(getSurfaceControl(env, clazz));
    if (control != NULL) {
        SurfaceControl::writeSurfaceToParcel(control, parcel);
    } else {
        sp<Surface> surface(Surface_getSurface(env, clazz));
=======
    sp<SurfaceControl> control(getSurfaceControl(env, surfaceObj));
    if (control != NULL) {
        SurfaceControl::writeSurfaceToParcel(control, parcel);
    } else {
        sp<Surface> surface(android_view_Surface_getSurface(env, surfaceObj));
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
        if (surface != NULL) {
            Surface::writeToParcel(surface, parcel);
        } else {
            SurfaceControl::writeSurfaceToParcel(NULL, parcel);
        }
    }
<<<<<<< HEAD
    if (flags & PARCELABLE_WRITE_RETURN_VALUE) {
        setSurfaceControl(env, clazz, NULL);
        setSurface(env, clazz, NULL);
    }
}

// ----------------------------------------------------------------------------
// ----------------------------------------------------------------------------
// ----------------------------------------------------------------------------

static void nativeClassInit(JNIEnv* env, jclass clazz);

static JNINativeMethod gSurfaceSessionMethods[] = {
    {"init",     "()V",  (void*)SurfaceSession_init },
    {"destroy",  "()V",  (void*)SurfaceSession_destroy },
    {"kill",     "()V",  (void*)SurfaceSession_kill },
};

static JNINativeMethod gSurfaceMethods[] = {
    {"nativeClassInit",     "()V",  (void*)nativeClassInit },
    {"init",                "(Landroid/view/SurfaceSession;ILjava/lang/String;IIIII)V",  (void*)Surface_init },
    {"init",                "(Landroid/os/Parcel;)V",  (void*)Surface_initParcel },
    {"initFromSurfaceTexture", "(Landroid/graphics/SurfaceTexture;)V", (void*)Surface_initFromSurfaceTexture },
    {"getIdentity",         "()I",  (void*)Surface_getIdentity },
    {"destroy",             "()V",  (void*)Surface_destroy },
    {"release",             "()V",  (void*)Surface_release },
    {"copyFrom",            "(Landroid/view/Surface;)V",  (void*)Surface_copyFrom },
    {"transferFrom",        "(Landroid/view/Surface;)V",  (void*)Surface_transferFrom },
    {"isValid",             "()Z",  (void*)Surface_isValid },
    {"lockCanvasNative",    "(Landroid/graphics/Rect;)Landroid/graphics/Canvas;",  (void*)Surface_lockCanvas },
    {"unlockCanvasAndPost", "(Landroid/graphics/Canvas;)V", (void*)Surface_unlockCanvasAndPost },
    {"unlockCanvas",        "(Landroid/graphics/Canvas;)V", (void*)Surface_unlockCanvas },
    {"openTransaction",     "()V",  (void*)Surface_openTransaction },
    {"closeTransaction",    "()V",  (void*)Surface_closeTransaction },
    {"setOrientation",      "(III)V", (void*)Surface_setOrientation },
    {"freezeDisplay",       "(I)V", (void*)Surface_freezeDisplay },
    {"unfreezeDisplay",     "(I)V", (void*)Surface_unfreezeDisplay },
    {"screenshot",          "(II)Landroid/graphics/Bitmap;", (void*)Surface_screenshotAll },
    {"screenshot",          "(IIII)Landroid/graphics/Bitmap;", (void*)Surface_screenshot },
    {"setLayer",            "(I)V", (void*)Surface_setLayer },
    {"setPosition",         "(FF)V",(void*)Surface_setPosition },
    {"setSize",             "(II)V",(void*)Surface_setSize },
    {"hide",                "()V",  (void*)Surface_hide },
    {"show",                "()V",  (void*)Surface_show },
    {"freeze",              "()V",  (void*)Surface_freeze },
    {"unfreeze",            "()V",  (void*)Surface_unfreeze },
    {"setFlags",            "(II)V",(void*)Surface_setFlags },
    {"setTransparentRegionHint","(Landroid/graphics/Region;)V", (void*)Surface_setTransparentRegion },
    {"setAlpha",            "(F)V", (void*)Surface_setAlpha },
    {"setMatrix",           "(FFFF)V",  (void*)Surface_setMatrix },
    {"setFreezeTint",       "(I)V",  (void*)Surface_setFreezeTint },
    {"readFromParcel",      "(Landroid/os/Parcel;)V", (void*)Surface_readFromParcel },
    {"writeToParcel",       "(Landroid/os/Parcel;I)V", (void*)Surface_writeToParcel },
    {"isConsumerRunningBehind", "()Z", (void*)Surface_isConsumerRunningBehind },
    {"setWindowCrop",       "(Landroid/graphics/Rect;)V", (void*)Surface_setWindowCrop },
};

void nativeClassInit(JNIEnv* env, jclass clazz)
{
    so.surface = env->GetFieldID(clazz, ANDROID_VIEW_SURFACE_JNI_ID, "I");
    so.surfaceGenerationId = env->GetFieldID(clazz, "mSurfaceGenerationId", "I");
    so.surfaceControl = env->GetFieldID(clazz, "mSurfaceControl", "I");
    so.saveCount = env->GetFieldID(clazz, "mSaveCount", "I");
    so.canvas    = env->GetFieldID(clazz, "mCanvas", "Landroid/graphics/Canvas;");

    jclass surfaceSession = env->FindClass("android/view/SurfaceSession");
    sso.client = env->GetFieldID(surfaceSession, "mClient", "I");

    jclass canvas = env->FindClass("android/graphics/Canvas");
    no.native_canvas = env->GetFieldID(canvas, "mNativeCanvas", "I");
    co.surfaceFormat = env->GetFieldID(canvas, "mSurfaceFormat", "I");

    jclass region = env->FindClass("android/graphics/Region");
    no.native_region = env->GetFieldID(region, "mNativeRegion", "I");

    jclass parcel = env->FindClass("android/os/Parcel");
    no.native_parcel = env->GetFieldID(parcel, "mNativePtr", "I");

    jclass rect = env->FindClass("android/graphics/Rect");
    ro.l = env->GetFieldID(rect, "left", "I");
    ro.t = env->GetFieldID(rect, "top", "I");
    ro.r = env->GetFieldID(rect, "right", "I");
    ro.b = env->GetFieldID(rect, "bottom", "I");

    jclass point = env->FindClass("android/graphics/Point");
    po.x = env->GetFieldID(point, "x", "I");
    po.y = env->GetFieldID(point, "y", "I");
}

int register_android_view_Surface(JNIEnv* env)
{
    int err;
    err = AndroidRuntime::registerNativeMethods(env, kSurfaceSessionClassPathName,
            gSurfaceSessionMethods, NELEM(gSurfaceSessionMethods));

    err |= AndroidRuntime::registerNativeMethods(env, kSurfaceClassPathName,
            gSurfaceMethods, NELEM(gSurfaceMethods));
=======
}

// ----------------------------------------------------------------------------

static JNINativeMethod gSurfaceMethods[] = {
    {"nativeCreate", "(Landroid/view/SurfaceSession;Ljava/lang/String;IIII)V",
            (void*)nativeCreate },
    {"nativeCreateFromSurfaceTexture", "(Landroid/graphics/SurfaceTexture;)V",
            (void*)nativeCreateFromSurfaceTexture },
    {"nativeRelease", "()V",
            (void*)nativeRelease },
    {"nativeDestroy", "()V",
            (void*)nativeDestroy },
    {"nativeIsValid", "()Z",
            (void*)nativeIsValid },
    {"nativeGetIdentity", "()I",
            (void*)nativeGetIdentity },
    {"nativeIsConsumerRunningBehind", "()Z",
            (void*)nativeIsConsumerRunningBehind },
    {"nativeLockCanvas", "(Landroid/graphics/Rect;)Landroid/graphics/Canvas;",
            (void*)nativeLockCanvas },
    {"nativeUnlockCanvasAndPost", "(Landroid/graphics/Canvas;)V",
            (void*)nativeUnlockCanvasAndPost },
    {"nativeScreenshot", "(Landroid/os/IBinder;IIIIZ)Landroid/graphics/Bitmap;",
            (void*)nativeScreenshot },
    {"nativeOpenTransaction", "()V",
            (void*)nativeOpenTransaction },
    {"nativeCloseTransaction", "()V",
            (void*)nativeCloseTransaction },
    {"nativeSetAnimationTransaction", "()V",
            (void*)nativeSetAnimationTransaction },
    {"nativeSetLayer", "(I)V",
            (void*)nativeSetLayer },
    {"nativeSetPosition", "(FF)V",
            (void*)nativeSetPosition },
    {"nativeSetSize", "(II)V",
            (void*)nativeSetSize },
    {"nativeSetTransparentRegionHint", "(Landroid/graphics/Region;)V",
            (void*)nativeSetTransparentRegionHint },
    {"nativeSetAlpha", "(F)V",
            (void*)nativeSetAlpha },
    {"nativeSetMatrix", "(FFFF)V",
            (void*)nativeSetMatrix },
    {"nativeSetFlags", "(II)V",
            (void*)nativeSetFlags },
    {"nativeSetWindowCrop", "(Landroid/graphics/Rect;)V",
            (void*)nativeSetWindowCrop },
    {"nativeSetLayerStack", "(I)V",
            (void*)nativeSetLayerStack },
    {"nativeGetBuiltInDisplay", "(I)Landroid/os/IBinder;",
            (void*)nativeGetBuiltInDisplay },
    {"nativeCreateDisplay", "(Ljava/lang/String;Z)Landroid/os/IBinder;",
            (void*)nativeCreateDisplay },
    {"nativeSetDisplaySurface", "(Landroid/os/IBinder;Landroid/view/Surface;)V",
            (void*)nativeSetDisplaySurface },
    {"nativeSetDisplayLayerStack", "(Landroid/os/IBinder;I)V",
            (void*)nativeSetDisplayLayerStack },
    {"nativeSetDisplayProjection", "(Landroid/os/IBinder;ILandroid/graphics/Rect;Landroid/graphics/Rect;)V",
            (void*)nativeSetDisplayProjection },
    {"nativeGetDisplayInfo", "(Landroid/os/IBinder;Landroid/view/Surface$PhysicalDisplayInfo;)Z",
            (void*)nativeGetDisplayInfo },
    {"nativeBlankDisplay", "(Landroid/os/IBinder;)V",
            (void*)nativeBlankDisplay },
    {"nativeUnblankDisplay", "(Landroid/os/IBinder;)V",
            (void*)nativeUnblankDisplay },
    {"nativeCopyFrom", "(Landroid/view/Surface;)V",
            (void*)nativeCopyFrom },
    {"nativeTransferFrom", "(Landroid/view/Surface;)V",
            (void*)nativeTransferFrom },
    {"nativeReadFromParcel", "(Landroid/os/Parcel;)V",
            (void*)nativeReadFromParcel },
    {"nativeWriteToParcel", "(Landroid/os/Parcel;)V",
            (void*)nativeWriteToParcel },
};

int register_android_view_Surface(JNIEnv* env)
{
    int err = AndroidRuntime::registerNativeMethods(env, "android/view/Surface",
            gSurfaceMethods, NELEM(gSurfaceMethods));

    jclass clazz = env->FindClass("android/view/Surface");
    gSurfaceClassInfo.clazz = jclass(env->NewGlobalRef(clazz));
    gSurfaceClassInfo.mNativeSurface =
            env->GetFieldID(gSurfaceClassInfo.clazz, ANDROID_VIEW_SURFACE_JNI_ID, "I");
    gSurfaceClassInfo.mNativeSurfaceControl =
            env->GetFieldID(gSurfaceClassInfo.clazz, "mNativeSurfaceControl", "I");
    gSurfaceClassInfo.mGenerationId =
            env->GetFieldID(gSurfaceClassInfo.clazz, "mGenerationId", "I");
    gSurfaceClassInfo.mCanvas =
            env->GetFieldID(gSurfaceClassInfo.clazz, "mCanvas", "Landroid/graphics/Canvas;");
    gSurfaceClassInfo.mCanvasSaveCount =
            env->GetFieldID(gSurfaceClassInfo.clazz, "mCanvasSaveCount", "I");
    gSurfaceClassInfo.ctor = env->GetMethodID(gSurfaceClassInfo.clazz, "<init>", "()V");

    clazz = env->FindClass("android/graphics/Canvas");
    gCanvasClassInfo.mNativeCanvas = env->GetFieldID(clazz, "mNativeCanvas", "I");
    gCanvasClassInfo.mSurfaceFormat = env->GetFieldID(clazz, "mSurfaceFormat", "I");

    clazz = env->FindClass("android/graphics/Rect");
    gRectClassInfo.left = env->GetFieldID(clazz, "left", "I");
    gRectClassInfo.top = env->GetFieldID(clazz, "top", "I");
    gRectClassInfo.right = env->GetFieldID(clazz, "right", "I");
    gRectClassInfo.bottom = env->GetFieldID(clazz, "bottom", "I");

    clazz = env->FindClass("android/view/Surface$PhysicalDisplayInfo");
    gPhysicalDisplayInfoClassInfo.width = env->GetFieldID(clazz, "width", "I");
    gPhysicalDisplayInfoClassInfo.height = env->GetFieldID(clazz, "height", "I");
    gPhysicalDisplayInfoClassInfo.refreshRate = env->GetFieldID(clazz, "refreshRate", "F");
    gPhysicalDisplayInfoClassInfo.density = env->GetFieldID(clazz, "density", "F");
    gPhysicalDisplayInfoClassInfo.xDpi = env->GetFieldID(clazz, "xDpi", "F");
    gPhysicalDisplayInfoClassInfo.yDpi = env->GetFieldID(clazz, "yDpi", "F");
    gPhysicalDisplayInfoClassInfo.secure = env->GetFieldID(clazz, "secure", "Z");
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
    return err;
}

};
