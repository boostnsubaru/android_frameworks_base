/*
 * Copyright (C) 2009 The Android Open Source Project
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

/**
<<<<<<< HEAD
 * This class is simply a container for the methods used to configure WebKit's
 * mock Geolocation service for use in LayoutTests.
 * @hide
 */
public final class MockGeolocation {

    // Global instance of a MockGeolocation
    private static MockGeolocation sMockGeolocation;
=======
 * Used to configure the mock Geolocation client for the LayoutTests.
 * @hide
 */
public final class MockGeolocation {
    private WebViewCore mWebViewCore;

    public MockGeolocation(WebViewCore webViewCore) {
        mWebViewCore = webViewCore;
    }

    /**
     * Sets use of the mock Geolocation client. Also resets that client.
     */
    public void setUseMock() {
        nativeSetUseMock(mWebViewCore);
    }
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a

    /**
     * Set the position for the mock Geolocation service.
     */
    public void setPosition(double latitude, double longitude, double accuracy) {
        // This should only ever be called on the WebKit thread.
<<<<<<< HEAD
        nativeSetPosition(latitude, longitude, accuracy);
=======
        nativeSetPosition(mWebViewCore, latitude, longitude, accuracy);
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
    }

    /**
     * Set the error for the mock Geolocation service.
     */
    public void setError(int code, String message) {
        // This should only ever be called on the WebKit thread.
<<<<<<< HEAD
        nativeSetError(code, message);
    }

    /**
     * Get the global instance of MockGeolocation.
     * @return The global MockGeolocation instance.
     */
    public static MockGeolocation getInstance() {
      if (sMockGeolocation == null) {
          sMockGeolocation = new MockGeolocation();
      }
      return sMockGeolocation;
    }

    // Native functions
    private static native void nativeSetPosition(double latitude, double longitude, double accuracy);
    private static native void nativeSetError(int code, String message);
=======
        nativeSetError(mWebViewCore, code, message);
    }

    public void setPermission(boolean allow) {
        // This should only ever be called on the WebKit thread.
        nativeSetPermission(mWebViewCore, allow);
    }

    // Native functions
    private static native void nativeSetUseMock(WebViewCore webViewCore);
    private static native void nativeSetPosition(WebViewCore webViewCore, double latitude,
            double longitude, double accuracy);
    private static native void nativeSetError(WebViewCore webViewCore, int code, String message);
    private static native void nativeSetPermission(WebViewCore webViewCore, boolean allow);
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
}
