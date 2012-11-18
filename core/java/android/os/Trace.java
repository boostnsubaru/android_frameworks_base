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

package android.os;

<<<<<<< HEAD
=======
import android.util.Log;

>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
/**
 * Writes trace events to the kernel trace buffer.  These trace events can be
 * collected using the "atrace" program for offline analysis.
 *
 * This tracing mechanism is independent of the method tracing mechanism
 * offered by {@link Debug#startMethodTracing}.  In particular, it enables
 * tracing of events that occur across processes.
 *
 * @hide
 */
public final class Trace {
<<<<<<< HEAD
=======
    private static final String TAG = "Trace";

>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
    // These tags must be kept in sync with frameworks/native/include/utils/Trace.h.
    public static final long TRACE_TAG_NEVER = 0;
    public static final long TRACE_TAG_ALWAYS = 1L << 0;
    public static final long TRACE_TAG_GRAPHICS = 1L << 1;
    public static final long TRACE_TAG_INPUT = 1L << 2;
    public static final long TRACE_TAG_VIEW = 1L << 3;
    public static final long TRACE_TAG_WEBVIEW = 1L << 4;
    public static final long TRACE_TAG_WINDOW_MANAGER = 1L << 5;
    public static final long TRACE_TAG_ACTIVITY_MANAGER = 1L << 6;
    public static final long TRACE_TAG_SYNC_MANAGER = 1L << 7;
    public static final long TRACE_TAG_AUDIO = 1L << 8;
    public static final long TRACE_TAG_VIDEO = 1L << 9;
<<<<<<< HEAD
=======
    public static final long TRACE_TAG_CAMERA = 1L << 10;
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a

    public static final int TRACE_FLAGS_START_BIT = 1;
    public static final String[] TRACE_TAGS = {
        "Graphics", "Input", "View", "WebView", "Window Manager",
<<<<<<< HEAD
        "Activity Manager", "Sync Manager", "Audio", "Video",
=======
        "Activity Manager", "Sync Manager", "Audio", "Video", "Camera",
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
    };

    public static final String PROPERTY_TRACE_TAG_ENABLEFLAGS = "debug.atrace.tags.enableflags";

<<<<<<< HEAD
    private static long sEnabledTags = nativeGetEnabledTags();
=======
    // This works as a "not ready" flag because TRACE_TAG_ALWAYS is always set.
    private static final long TRACE_FLAGS_NOT_READY = 0;

    // Must be volatile to avoid word tearing.
    private static volatile long sEnabledTags = TRACE_FLAGS_NOT_READY;
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a

    private static native long nativeGetEnabledTags();
    private static native void nativeTraceCounter(long tag, String name, int value);
    private static native void nativeTraceBegin(long tag, String name);
    private static native void nativeTraceEnd(long tag);

    static {
<<<<<<< HEAD
        SystemProperties.addChangeCallback(new Runnable() {
            @Override public void run() {
                sEnabledTags = nativeGetEnabledTags();
=======
        // We configure two separate change callbacks, one in Trace.cpp and one here.  The
        // native callback reads the tags from the system property, and this callback
        // reads the value that the native code retrieved.  It's essential that the native
        // callback executes first.
        //
        // The system provides ordering through a priority level.  Callbacks made through
        // SystemProperties.addChangeCallback currently have a negative priority, while
        // our native code is using a priority of zero.
        SystemProperties.addChangeCallback(new Runnable() {
            @Override public void run() {
                cacheEnabledTags();
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
            }
        });
    }

    private Trace() {
    }

    /**
<<<<<<< HEAD
=======
     * Caches a copy of the enabled-tag bits.  The "master" copy is held by the native code,
     * and comes from the PROPERTY_TRACE_TAG_ENABLEFLAGS property.
     * <p>
     * If the native code hasn't yet read the property, we will cause it to do one-time
     * initialization.  We don't want to do this during class init, because this class is
     * preloaded, so all apps would be stuck with whatever the zygote saw.  (The zygote
     * doesn't see the system-property update broadcasts.)
     * <p>
     * We want to defer initialization until the first use by an app, post-zygote.
     * <p>
     * We're okay if multiple threads call here simultaneously -- the native state is
     * synchronized, and sEnabledTags is volatile (prevents word tearing).
     */
    private static long cacheEnabledTags() {
        long tags = nativeGetEnabledTags();
        if (tags == TRACE_FLAGS_NOT_READY) {
            Log.w(TAG, "Unexpected value from nativeGetEnabledTags: " + tags);
            // keep going
        }
        sEnabledTags = tags;
        return tags;
    }

    /**
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
     * Returns true if a trace tag is enabled.
     *
     * @param traceTag The trace tag to check.
     * @return True if the trace tag is valid.
     */
    public static boolean isTagEnabled(long traceTag) {
<<<<<<< HEAD
        return (sEnabledTags & traceTag) != 0;
=======
        long tags = sEnabledTags;
        if (tags == TRACE_FLAGS_NOT_READY) {
            tags = cacheEnabledTags();
        }
        return (tags & traceTag) != 0;
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
    }

    /**
     * Writes trace message to indicate the value of a given counter.
     *
     * @param traceTag The trace tag.
     * @param counterName The counter name to appear in the trace.
     * @param counterValue The counter value.
     */
    public static void traceCounter(long traceTag, String counterName, int counterValue) {
<<<<<<< HEAD
        if ((sEnabledTags & traceTag) != 0) {
=======
        if (isTagEnabled(traceTag)) {
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
            nativeTraceCounter(traceTag, counterName, counterValue);
        }
    }

    /**
     * Writes a trace message to indicate that a given method has begun.
     * Must be followed by a call to {@link #traceEnd} using the same tag.
     *
     * @param traceTag The trace tag.
     * @param methodName The method name to appear in the trace.
     */
    public static void traceBegin(long traceTag, String methodName) {
<<<<<<< HEAD
        if ((sEnabledTags & traceTag) != 0) {
=======
        if (isTagEnabled(traceTag)) {
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
            nativeTraceBegin(traceTag, methodName);
        }
    }

    /**
     * Writes a trace message to indicate that the current method has ended.
     * Must be called exactly once for each call to {@link #traceBegin} using the same tag.
     *
     * @param traceTag The trace tag.
     */
    public static void traceEnd(long traceTag) {
<<<<<<< HEAD
        if ((sEnabledTags & traceTag) != 0) {
=======
        if (isTagEnabled(traceTag)) {
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
            nativeTraceEnd(traceTag);
        }
    }
}
