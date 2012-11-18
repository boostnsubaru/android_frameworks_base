/*
 * Copyright (C) 2010 The Android Open Source Project
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

package com.android.server.location;

<<<<<<< HEAD
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.location.Address;
import android.location.GeocoderParams;
import android.location.IGeocodeProvider;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.SystemClock;
import android.util.Log;

import java.util.List;

/**
 * A class for proxying IGeocodeProvider implementations.
 *
 * {@hide}
 */
public class GeocoderProxy {

    private static final String TAG = "GeocoderProxy";

    public static final String SERVICE_ACTION =
        "com.android.location.service.GeocodeProvider";

    private final Context mContext;
    private final Intent mIntent;
    private final Object mMutex = new Object();  // synchronizes access to mServiceConnection
    private Connection mServiceConnection;  // never null after ctor

    public GeocoderProxy(Context context, String packageName) {
        mContext = context;
        mIntent = new Intent(SERVICE_ACTION);
        reconnect(packageName);
    }

    /** Bind to service. Will reconnect if already connected */
    public void reconnect(String packageName) {
        synchronized (mMutex) {
            if (mServiceConnection != null) {
                mContext.unbindService(mServiceConnection);
            }
            mServiceConnection = new Connection();
            mIntent.setPackage(packageName);
            mContext.bindService(mIntent, mServiceConnection,
                    Context.BIND_AUTO_CREATE | Context.BIND_NOT_FOREGROUND
                    | Context.BIND_ALLOW_OOM_MANAGEMENT);
        }
    }

    private class Connection implements ServiceConnection {

        private IGeocodeProvider mProvider;

        public void onServiceConnected(ComponentName className, IBinder service) {
            synchronized (this) {
                mProvider = IGeocodeProvider.Stub.asInterface(service);
            }
        }

        public void onServiceDisconnected(ComponentName className) {
            synchronized (this) {
                mProvider = null;
            }
        }

        public IGeocodeProvider getProvider() {
            synchronized (this) {
                return mProvider;
            }
        }
=======
import android.content.Context;
import android.location.Address;
import android.location.GeocoderParams;
import android.location.IGeocodeProvider;
import android.os.RemoteException;
import android.os.UserHandle;
import android.util.Log;

import com.android.server.ServiceWatcher;
import java.util.List;

/**
 * Proxy for IGeocodeProvider implementations.
 */
public class GeocoderProxy {
    private static final String TAG = "GeocoderProxy";

    private static final String SERVICE_ACTION = "com.android.location.service.GeocodeProvider";

    private final Context mContext;
    private final ServiceWatcher mServiceWatcher;

    public static GeocoderProxy createAndBind(Context context,
            List<String> initialPackageNames, int userId) {
        GeocoderProxy proxy = new GeocoderProxy(context, initialPackageNames, userId);
        if (proxy.bind()) {
            return proxy;
        } else {
            return null;
        }
    }

    public GeocoderProxy(Context context, List<String> initialPackageNames, int userId) {
        mContext = context;

        mServiceWatcher = new ServiceWatcher(mContext, TAG, SERVICE_ACTION, initialPackageNames,
                null, null, userId);
    }

    private boolean bind () {
        return mServiceWatcher.start();
    }

    private IGeocodeProvider getService() {
        return IGeocodeProvider.Stub.asInterface(mServiceWatcher.getBinder());
    }

    public String getConnectedPackageName() {
        return mServiceWatcher.getBestPackageName();
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
    }

    public String getFromLocation(double latitude, double longitude, int maxResults,
            GeocoderParams params, List<Address> addrs) {
<<<<<<< HEAD
        IGeocodeProvider provider;
        synchronized (mMutex) {
            provider = mServiceConnection.getProvider();
        }
        if (provider != null) {
            try {
                return provider.getFromLocation(latitude, longitude, maxResults,
                        params, addrs);
            } catch (RemoteException e) {
                Log.e(TAG, "getFromLocation failed", e);
=======
        IGeocodeProvider provider = getService();
        if (provider != null) {
            try {
                return provider.getFromLocation(latitude, longitude, maxResults, params, addrs);
            } catch (RemoteException e) {
                Log.w(TAG, e);
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
            }
        }
        return "Service not Available";
    }

    public String getFromLocationName(String locationName,
            double lowerLeftLatitude, double lowerLeftLongitude,
            double upperRightLatitude, double upperRightLongitude, int maxResults,
            GeocoderParams params, List<Address> addrs) {
<<<<<<< HEAD
        IGeocodeProvider provider;
        synchronized (mMutex) {
            provider = mServiceConnection.getProvider();
        }
=======
        IGeocodeProvider provider = getService();
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
        if (provider != null) {
            try {
                return provider.getFromLocationName(locationName, lowerLeftLatitude,
                        lowerLeftLongitude, upperRightLatitude, upperRightLongitude,
                        maxResults, params, addrs);
            } catch (RemoteException e) {
<<<<<<< HEAD
                Log.e(TAG, "getFromLocationName failed", e);
=======
                Log.w(TAG, e);
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
            }
        }
        return "Service not Available";
    }
<<<<<<< HEAD
=======

>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
}
