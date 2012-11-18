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

package android.webkit;

import android.content.Context;

/**
 * This class allows developers to determine whether any WebView used in the
 * application has stored any of the following types of browsing data and
 * to clear any such stored data for all WebViews in the application.
 * <ul>
<<<<<<< HEAD
 *  <li>Username/password pairs entered into web forms</li>
=======
 *  <li>Username/password pairs for web forms</li>
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
 *  <li>HTTP authentication username/password pairs</li>
 *  <li>Data entered into text fields (e.g. for autocomplete suggestions)</li>
 * </ul>
 */
public class WebViewDatabase {
<<<<<<< HEAD
    // TODO: deprecate/hide this.
=======
    /**
     * @hide Since API level {@link android.os.Build.VERSION_CODES#JELLY_BEAN_MR1}
     */
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
    protected static final String LOGTAG = "webviewdatabase";

    /**
     * @hide Only for use by WebViewProvider implementations.
     */
    protected WebViewDatabase() {
    }

<<<<<<< HEAD
    public static synchronized WebViewDatabase getInstance(Context context) {
=======
    public static WebViewDatabase getInstance(Context context) {
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
        return WebViewFactory.getProvider().getWebViewDatabase(context);
    }

    /**
<<<<<<< HEAD
     * Gets whether there are any username/password combinations
     * from web pages saved.
     *
     * @return true if there are any username/passwords used in web
     *         forms saved
=======
     * Gets whether there are any saved username/password pairs for web forms.
     * Note that these are unrelated to HTTP authentication credentials.
     *
     * @return true if there are any saved username/password pairs
     * @see WebView#savePassword
     * @see clearUsernamePassword
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
     */
    public boolean hasUsernamePassword() {
        throw new MustOverrideException();
    }

    /**
<<<<<<< HEAD
     * Clears any username/password combinations saved from web forms.
=======
     * Clears any saved username/password pairs for web forms.
     * Note that these are unrelated to HTTP authentication credentials.
     *
     * @see WebView#savePassword
     * @see hasUsernamePassword
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
     */
    public void clearUsernamePassword() {
        throw new MustOverrideException();
    }

    /**
<<<<<<< HEAD
     * Gets whether there are any HTTP authentication username/password combinations saved.
     *
     * @return true if there are any HTTP authentication username/passwords saved
=======
     * Gets whether there are any saved credentials for HTTP authentication.
     *
     * @return whether there are any saved credentials
     * @see Webview#getHttpAuthUsernamePassword
     * @see Webview#setHttpAuthUsernamePassword
     * @see clearHttpAuthUsernamePassword
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
     */
    public boolean hasHttpAuthUsernamePassword() {
        throw new MustOverrideException();
    }

    /**
<<<<<<< HEAD
     * Clears any HTTP authentication username/passwords that are saved.
=======
     * Clears any saved credentials for HTTP authentication.
     *
     * @see Webview#getHttpAuthUsernamePassword
     * @see Webview#setHttpAuthUsernamePassword
     * @see hasHttpAuthUsernamePassword
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
     */
    public void clearHttpAuthUsernamePassword() {
        throw new MustOverrideException();
    }

    /**
<<<<<<< HEAD
     * Gets whether there is any previously-entered form data saved.
     *
     * @return true if there is form data saved
=======
     * Gets whether there is any saved data for web forms.
     *
     * @return whether there is any saved data for web forms
     * @see clearFormData
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
     */
    public boolean hasFormData() {
        throw new MustOverrideException();
    }

    /**
<<<<<<< HEAD
     * Clears any stored previously-entered form data.
=======
     * Clears any saved data for web forms.
     *
     * @see hasFormData
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
     */
    public void clearFormData() {
        throw new MustOverrideException();
    }
}
