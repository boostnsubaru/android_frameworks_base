/*
 * Copyright (C) 2006 The Android Open Source Project
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

import android.os.Handler;

/**
<<<<<<< HEAD
 * HTTP authentication request that must be handled by the user interface.
 * WebView creates the object and hands it to the current {@link WebViewClient},
 * which must call either {@link #proceed(String, String)} or {@link #cancel()}.
=======
 * Represents a request for HTTP authentication. Instances of this class are
 * created by the WebView and passed to
 * {@link WebViewClient#onReceivedHttpAuthRequest}. The host application must
 * call either {@link #proceed} or {@link #cancel} to set the WebView's
 * response to the request.
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
 */
public class HttpAuthHandler extends Handler {

    /**
<<<<<<< HEAD
     * Package-private constructor needed for API compatibility.
     */
    HttpAuthHandler() {
    }

    /**
     * @return True if we can use user credentials on record
     * (ie, if we did not fail trying to use them last time)
=======
     * @hide Only for use by WebViewProvider implementations.
     */
    public HttpAuthHandler() {
    }

    /**
     * Gets whether the credentials stored for the current host (i.e. the host
     * for which {@link WebViewClient#onReceivedHttpAuthRequest} was called)
     * are suitable for use. Credentials are not suitable if they have
     * previously been rejected by the server for the current request.
     *
     * @return whether the credentials are suitable for use
     * @see Webview#getHttpAuthUsernamePassword
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
     */
    public boolean useHttpAuthUsernamePassword() {
        return false;
    }

    /**
<<<<<<< HEAD
     * Cancel the authorization request.
=======
     * Instructs the WebView to cancel the authentication request.
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
     */
    public void cancel() {
    }

    /**
<<<<<<< HEAD
     * Proceed with the authorization with the given credentials.
=======
     * Instructs the WebView to proceed with the authentication with the given
     * credentials. Credentials for use with this method can be retrieved from
     * the WebView's store using {@link WebView#getHttpAuthUsernamePassword}.
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
     */
    public void proceed(String username, String password) {
    }

    /**
<<<<<<< HEAD
     * return true if the prompt dialog should be suppressed.
=======
     * Gets whether the prompt dialog should be suppressed.
     *
     * @return whether the prompt dialog should be suppressed
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
     * @hide
     */
    public boolean suppressDialog() {
        return false;
    }
}
