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

package android.os;

<<<<<<< HEAD
=======
import android.util.Log;

>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
import java.util.ArrayList;
import java.util.HashMap;

/**
 * UEventObserver is an abstract class that receives UEvent's from the kernel.<p>
 *
 * Subclass UEventObserver, implementing onUEvent(UEvent event), then call
 * startObserving() with a match string. The UEvent thread will then call your
 * onUEvent() method when a UEvent occurs that contains your match string.<p>
 *
 * Call stopObserving() to stop receiving UEvent's.<p>
 *
 * There is only one UEvent thread per process, even if that process has
 * multiple UEventObserver subclass instances. The UEvent thread starts when
 * the startObserving() is called for the first time in that process. Once
 * started the UEvent thread will not stop (although it can stop notifying
 * UEventObserver's via stopObserving()).<p>
 *
 * @hide
*/
public abstract class UEventObserver {
<<<<<<< HEAD
    private static final String TAG = UEventObserver.class.getSimpleName();
=======
    private static final String TAG = "UEventObserver";
    private static final boolean DEBUG = false;

    private static UEventThread sThread;

    private static native void nativeSetup();
    private static native String nativeWaitForNextEvent();
    private static native void nativeAddMatch(String match);
    private static native void nativeRemoveMatch(String match);

    public UEventObserver() {
    }

    @Override
    protected void finalize() throws Throwable {
        try {
            stopObserving();
        } finally {
            super.finalize();
        }
    }

    private static UEventThread getThread() {
        synchronized (UEventObserver.class) {
            if (sThread == null) {
                sThread = new UEventThread();
                sThread.start();
            }
            return sThread;
        }
    }

    private static UEventThread peekThread() {
        synchronized (UEventObserver.class) {
            return sThread;
        }
    }

    /**
     * Begin observation of UEvent's.<p>
     * This method will cause the UEvent thread to start if this is the first
     * invocation of startObserving in this process.<p>
     * Once called, the UEvent thread will call onUEvent() when an incoming
     * UEvent matches the specified string.<p>
     * This method can be called multiple times to register multiple matches.
     * Only one call to stopObserving is required even with multiple registered
     * matches.
     *
     * @param match A substring of the UEvent to match.  Try to be as specific
     * as possible to avoid incurring unintended additional cost from processing
     * irrelevant messages.  Netlink messages can be moderately high bandwidth and
     * are expensive to parse.  For example, some devices may send one netlink message
     * for each vsync period.
     */
    public final void startObserving(String match) {
        if (match == null || match.isEmpty()) {
            throw new IllegalArgumentException("match substring must be non-empty");
        }

        final UEventThread t = getThread();
        t.addObserver(match, this);
    }

    /**
     * End observation of UEvent's.<p>
     * This process's UEvent thread will never call onUEvent() on this
     * UEventObserver after this call. Repeated calls have no effect.
     */
    public final void stopObserving() {
        final UEventThread t = getThread();
        if (t != null) {
            t.removeObserver(this);
        }
    }

    /**
     * Subclasses of UEventObserver should override this method to handle
     * UEvents.
     */
    public abstract void onUEvent(UEvent event);
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a

    /**
     * Representation of a UEvent.
     */
<<<<<<< HEAD
    static public class UEvent {
        // collection of key=value pairs parsed from the uevent message
        public HashMap<String,String> mMap = new HashMap<String,String>();
=======
    public static final class UEvent {
        // collection of key=value pairs parsed from the uevent message
        private final HashMap<String,String> mMap = new HashMap<String,String>();
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a

        public UEvent(String message) {
            int offset = 0;
            int length = message.length();

            while (offset < length) {
                int equals = message.indexOf('=', offset);
<<<<<<< HEAD
                int at = message.indexOf(0, offset);
=======
                int at = message.indexOf('\0', offset);
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
                if (at < 0) break;

                if (equals > offset && equals < at) {
                    // key is before the equals sign, and value is after
                    mMap.put(message.substring(offset, equals),
                            message.substring(equals + 1, at));
                }

                offset = at + 1;
            }
        }

        public String get(String key) {
            return mMap.get(key);
        }

        public String get(String key, String defaultValue) {
            String result = mMap.get(key);
            return (result == null ? defaultValue : result);
        }

        public String toString() {
            return mMap.toString();
        }
    }

<<<<<<< HEAD
    private static UEventThread sThread;
    private static boolean sThreadStarted = false;

    private static class UEventThread extends Thread {
=======
    private static final class UEventThread extends Thread {
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
        /** Many to many mapping of string match to observer.
         *  Multimap would be better, but not available in android, so use
         *  an ArrayList where even elements are the String match and odd
         *  elements the corresponding UEventObserver observer */
<<<<<<< HEAD
        private ArrayList<Object> mObservers = new ArrayList<Object>();
        
        UEventThread() {
            super("UEventObserver");
        }
        
        public void run() {
            native_setup();

            byte[] buffer = new byte[1024];
            int len;
            while (true) {
                len = next_event(buffer);
                if (len > 0) {
                    String bufferStr = new String(buffer, 0, len);  // easier to search a String
                    synchronized (mObservers) {
                        for (int i = 0; i < mObservers.size(); i += 2) {
                            if (bufferStr.indexOf((String)mObservers.get(i)) != -1) {
                                ((UEventObserver)mObservers.get(i+1))
                                        .onUEvent(new UEvent(bufferStr));
                            }
                        }
                    }
                }
            }
        }
        public void addObserver(String match, UEventObserver observer) {
            synchronized(mObservers) {
                mObservers.add(match);
                mObservers.add(observer);
            }
        }
        /** Removes every key/value pair where value=observer from mObservers */
        public void removeObserver(UEventObserver observer) {
            synchronized(mObservers) {
                boolean found = true;
                while (found) {
                    found = false;
                    for (int i = 0; i < mObservers.size(); i += 2) {
                        if (mObservers.get(i+1) == observer) {
                            mObservers.remove(i+1);
                            mObservers.remove(i);
                            found = true;
                            break;
                        }
=======
        private final ArrayList<Object> mKeysAndObservers = new ArrayList<Object>();

        private final ArrayList<UEventObserver> mTempObserversToSignal =
                new ArrayList<UEventObserver>();

        public UEventThread() {
            super("UEventObserver");
        }

        @Override
        public void run() {
            nativeSetup();

            while (true) {
                String message = nativeWaitForNextEvent();
                if (message != null) {
                    if (DEBUG) {
                        Log.d(TAG, message);
                    }
                    sendEvent(message);
                }
            }
        }

        private void sendEvent(String message) {
            synchronized (mKeysAndObservers) {
                final int N = mKeysAndObservers.size();
                for (int i = 0; i < N; i += 2) {
                    final String key = (String)mKeysAndObservers.get(i);
                    if (message.contains(key)) {
                        final UEventObserver observer =
                                (UEventObserver)mKeysAndObservers.get(i + 1);
                        mTempObserversToSignal.add(observer);
                    }
                }
            }

            if (!mTempObserversToSignal.isEmpty()) {
                final UEvent event = new UEvent(message);
                final int N = mTempObserversToSignal.size();
                for (int i = 0; i < N; i++) {
                    final UEventObserver observer = mTempObserversToSignal.get(i);
                    observer.onUEvent(event);
                }
                mTempObserversToSignal.clear();
            }
        }

        public void addObserver(String match, UEventObserver observer) {
            synchronized (mKeysAndObservers) {
                mKeysAndObservers.add(match);
                mKeysAndObservers.add(observer);
                nativeAddMatch(match);
            }
        }

        /** Removes every key/value pair where value=observer from mObservers */
        public void removeObserver(UEventObserver observer) {
            synchronized (mKeysAndObservers) {
                for (int i = 0; i < mKeysAndObservers.size(); ) {
                    if (mKeysAndObservers.get(i + 1) == observer) {
                        mKeysAndObservers.remove(i + 1);
                        final String match = (String)mKeysAndObservers.remove(i);
                        nativeRemoveMatch(match);
                    } else {
                        i += 2;
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
                    }
                }
            }
        }
    }
<<<<<<< HEAD

    private static native void native_setup();
    private static native int next_event(byte[] buffer);

    private static final synchronized void ensureThreadStarted() {
        if (sThreadStarted == false) {
            sThread = new UEventThread();
            sThread.start();
            sThreadStarted = true;
        }
    }

    /**
     * Begin observation of UEvent's.<p>
     * This method will cause the UEvent thread to start if this is the first
     * invocation of startObserving in this process.<p>
     * Once called, the UEvent thread will call onUEvent() when an incoming
     * UEvent matches the specified string.<p>
     * This method can be called multiple times to register multiple matches.
     * Only one call to stopObserving is required even with multiple registered
     * matches.
     * @param match A substring of the UEvent to match. Use "" to match all
     *              UEvent's
     */
    public final synchronized void startObserving(String match) {
        ensureThreadStarted();
        sThread.addObserver(match, this);
    }

    /**
     * End observation of UEvent's.<p>
     * This process's UEvent thread will never call onUEvent() on this
     * UEventObserver after this call. Repeated calls have no effect.
     */
    public final synchronized void stopObserving() {
        sThread.removeObserver(this);
    }

    /**
     * Subclasses of UEventObserver should override this method to handle
     * UEvents.
     */
    public abstract void onUEvent(UEvent event);

    protected void finalize() throws Throwable {
        try {
            stopObserving();
        } finally {
            super.finalize();
        }
    }
=======
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
}
