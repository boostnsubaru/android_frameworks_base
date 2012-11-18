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

package com.android.server.location;

<<<<<<< HEAD
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.location.Criteria;
import android.location.ILocationProvider;
import android.location.Location;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.WorkSource;
import android.util.Log;

import com.android.internal.location.DummyLocationProvider;

/**
 * A class for proxying location providers implemented as services.
 *
 * {@hide}
 */
public class LocationProviderProxy implements LocationProviderInterface {

    private static final String TAG = "LocationProviderProxy";

    public static final String SERVICE_ACTION =
        "com.android.location.service.NetworkLocationProvider";

    private final Context mContext;
    private final String mName;
    private final Intent mIntent;
    private final Handler mHandler;
    private final Object mMutex = new Object();  // synchronizes access to non-final members
    private Connection mServiceConnection;  // never null after ctor

    // cached values set by the location manager
    private boolean mLocationTracking = false;
    private boolean mEnabled = false;
    private long mMinTime = -1;
    private WorkSource mMinTimeSource = new WorkSource();
    private int mNetworkState;
    private NetworkInfo mNetworkInfo;

    // constructor for proxying location providers implemented in a separate service
    public LocationProviderProxy(Context context, String name, String packageName,
            Handler handler) {
        mContext = context;
        mName = name;
        mIntent = new Intent(SERVICE_ACTION);
        mHandler = handler;
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
                    Context.BIND_AUTO_CREATE | Context.BIND_NOT_FOREGROUND |
                    Context.BIND_ALLOW_OOM_MANAGEMENT);
        }
    }

    private class Connection implements ServiceConnection, Runnable {

        private ILocationProvider mProvider;

        // for caching requiresNetwork, requiresSatellite, etc.
        private DummyLocationProvider mCachedAttributes;  // synchronized by mMutex

        public void onServiceConnected(ComponentName className, IBinder service) {
            synchronized (this) {
                mProvider = ILocationProvider.Stub.asInterface(service);
                if (mProvider != null) {
                    mHandler.post(this);
                }
            }
        }

        public void onServiceDisconnected(ComponentName className) {
            synchronized (this) {
                mProvider = null;
            }
        }

        public synchronized ILocationProvider getProvider() {
            return mProvider;
        }

        public synchronized DummyLocationProvider getCachedAttributes() {
            return mCachedAttributes;
        }

        public void run() {
            synchronized (mMutex) {
                if (mServiceConnection != this) {
                    // This ServiceConnection no longer the one we want to bind to.
                    return;
                }
                ILocationProvider provider = getProvider();
                if (provider == null) {
                    return;
                }

                // resend previous values from the location manager if the service has restarted
                try {
                    if (mEnabled) {
                        provider.enable();
                    }
                    if (mLocationTracking) {
                        provider.enableLocationTracking(true);
                    }
                    if (mMinTime >= 0) {
                        provider.setMinTime(mMinTime, mMinTimeSource);
                    }
                    if (mNetworkInfo != null) {
                        provider.updateNetworkState(mNetworkState, mNetworkInfo);
                    }
                } catch (RemoteException e) {
                }

                // init cache of parameters
                if (mCachedAttributes == null) {
                    try {
                        mCachedAttributes = new DummyLocationProvider(mName, null);
                        mCachedAttributes.setRequiresNetwork(provider.requiresNetwork());
                        mCachedAttributes.setRequiresSatellite(provider.requiresSatellite());
                        mCachedAttributes.setRequiresCell(provider.requiresCell());
                        mCachedAttributes.setHasMonetaryCost(provider.hasMonetaryCost());
                        mCachedAttributes.setSupportsAltitude(provider.supportsAltitude());
                        mCachedAttributes.setSupportsSpeed(provider.supportsSpeed());
                        mCachedAttributes.setSupportsBearing(provider.supportsBearing());
                        mCachedAttributes.setPowerRequirement(provider.getPowerRequirement());
                        mCachedAttributes.setAccuracy(provider.getAccuracy());
                    } catch (RemoteException e) {
                        mCachedAttributes = null;
                    }
                }
=======
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.List;

import android.content.Context;
import android.location.LocationProvider;
import android.os.Bundle;
import android.os.Handler;
import android.os.RemoteException;
import android.os.UserHandle;
import android.os.WorkSource;
import android.util.Log;

import com.android.internal.location.ProviderProperties;
import com.android.internal.location.ILocationProvider;
import com.android.internal.location.ProviderRequest;
import com.android.server.LocationManagerService;
import com.android.server.ServiceWatcher;

/**
 * Proxy for ILocationProvider implementations.
 */
public class LocationProviderProxy implements LocationProviderInterface {
    private static final String TAG = "LocationProviderProxy";
    private static final boolean D = LocationManagerService.D;

    private final Context mContext;
    private final String mName;
    private final ServiceWatcher mServiceWatcher;

    private Object mLock = new Object();

    // cached values set by the location manager, synchronized on mLock
    private ProviderProperties mProperties;
    private boolean mEnabled = false;
    private ProviderRequest mRequest = null;
    private WorkSource mWorksource = new WorkSource();

    public static LocationProviderProxy createAndBind(Context context, String name, String action,
            List<String> initialPackageNames, Handler handler, int userId) {
        LocationProviderProxy proxy = new LocationProviderProxy(context, name, action,
                initialPackageNames, handler, userId);
        if (proxy.bind()) {
            return proxy;
        } else {
            return null;
        }
    }

    private LocationProviderProxy(Context context, String name, String action,
            List<String> initialPackageNames, Handler handler, int userId) {
        mContext = context;
        mName = name;
        mServiceWatcher = new ServiceWatcher(mContext, TAG, action, initialPackageNames,
                mNewServiceWork, handler, userId);
    }

    private boolean bind () {
        return mServiceWatcher.start();
    }

    private ILocationProvider getService() {
        return ILocationProvider.Stub.asInterface(mServiceWatcher.getBinder());
    }

    public String getConnectedPackageName() {
        return mServiceWatcher.getBestPackageName();
    }

    /**
     * Work to apply current state to a newly connected provider.
     * Remember we can switch the service that implements a providers
     * at run-time, so need to apply current state.
     */
    private Runnable mNewServiceWork = new Runnable() {
        @Override
        public void run() {
            if (D) Log.d(TAG, "applying state to connected service");

            boolean enabled;
            ProviderProperties properties = null;
            ProviderRequest request;
            WorkSource source;
            ILocationProvider service;
            synchronized (mLock) {
                enabled = mEnabled;
                request = mRequest;
                source = mWorksource;
                service = getService();
            }

            if (service == null) return;

            try {
                // load properties from provider
                properties = service.getProperties();
                if (properties == null) {
                    Log.e(TAG, mServiceWatcher.getBestPackageName() +
                            " has invalid locatino provider properties");
                }

                // apply current state to new service
                if (enabled) {
                    service.enable();
                    if (request != null) {
                        service.setRequest(request, source);
                    }
                }
            } catch (RemoteException e) {
                Log.w(TAG, e);
            } catch (Exception e) {
                // never let remote service crash system server
                Log.e(TAG, "Exception from " + mServiceWatcher.getBestPackageName(), e);
            }

            synchronized (mLock) {
                mProperties = properties;
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
            }
        }
    };

<<<<<<< HEAD
=======
    @Override
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
    public String getName() {
        return mName;
    }

<<<<<<< HEAD
    private DummyLocationProvider getCachedAttributes() {
        synchronized (mMutex) {
            return mServiceConnection.getCachedAttributes();
        }
    }

    public boolean requiresNetwork() {
        DummyLocationProvider cachedAttributes = getCachedAttributes();
        if (cachedAttributes != null) {
            return cachedAttributes.requiresNetwork();
        } else {
            return false;
        }
    }

    public boolean requiresSatellite() {
        DummyLocationProvider cachedAttributes = getCachedAttributes();
        if (cachedAttributes != null) {
            return cachedAttributes.requiresSatellite();
        } else {
            return false;
        }
    }

    public boolean requiresCell() {
        DummyLocationProvider cachedAttributes = getCachedAttributes();
        if (cachedAttributes != null) {
            return cachedAttributes.requiresCell();
        } else {
            return false;
        }
    }

    public boolean hasMonetaryCost() {
        DummyLocationProvider cachedAttributes = getCachedAttributes();
        if (cachedAttributes != null) {
            return cachedAttributes.hasMonetaryCost();
        } else {
            return false;
        }
    }

    public boolean supportsAltitude() {
        DummyLocationProvider cachedAttributes = getCachedAttributes();
        if (cachedAttributes != null) {
            return cachedAttributes.supportsAltitude();
        } else {
            return false;
        }
    }

    public boolean supportsSpeed() {
        DummyLocationProvider cachedAttributes = getCachedAttributes();
        if (cachedAttributes != null) {
            return cachedAttributes.supportsSpeed();
        } else {
            return false;
        }
    }

     public boolean supportsBearing() {
        DummyLocationProvider cachedAttributes = getCachedAttributes();
        if (cachedAttributes != null) {
            return cachedAttributes.supportsBearing();
        } else {
            return false;
        }
    }

    public int getPowerRequirement() {
        DummyLocationProvider cachedAttributes = getCachedAttributes();
        if (cachedAttributes != null) {
            return cachedAttributes.getPowerRequirement();
        } else {
            return -1;
        }
    }

    public int getAccuracy() {
        DummyLocationProvider cachedAttributes = getCachedAttributes();
        if (cachedAttributes != null) {
            return cachedAttributes.getAccuracy();
        } else {
            return -1;
        }
    }

    public boolean meetsCriteria(Criteria criteria) {
        synchronized (mMutex) {
            ILocationProvider provider = mServiceConnection.getProvider();
            if (provider != null) {
                try {
                    return provider.meetsCriteria(criteria);
                } catch (RemoteException e) {
                }
            }
        }
        // default implementation if we lost connection to the provider
        if ((criteria.getAccuracy() != Criteria.NO_REQUIREMENT) &&
            (criteria.getAccuracy() < getAccuracy())) {
            return false;
        }
        int criteriaPower = criteria.getPowerRequirement();
        if ((criteriaPower != Criteria.NO_REQUIREMENT) &&
            (criteriaPower < getPowerRequirement())) {
            return false;
        }
        if (criteria.isAltitudeRequired() && !supportsAltitude()) {
            return false;
        }
        if (criteria.isSpeedRequired() && !supportsSpeed()) {
            return false;
        }
        if (criteria.isBearingRequired() && !supportsBearing()) {
            return false;
        }
        return true;
    }

    public void enable() {
        synchronized (mMutex) {
            mEnabled = true;
            ILocationProvider provider = mServiceConnection.getProvider();
            if (provider != null) {
                try {
                    provider.enable();
                } catch (RemoteException e) {
                }
            }
        }
    }

    public void disable() {
        synchronized (mMutex) {
            mEnabled = false;
            ILocationProvider provider = mServiceConnection.getProvider();
            if (provider != null) {
                try {
                    provider.disable();
                } catch (RemoteException e) {
                }
            }
        }
    }

    public boolean isEnabled() {
        synchronized (mMutex) {
            return mEnabled;
        }
    }

    public int getStatus(Bundle extras) {
        ILocationProvider provider;
        synchronized (mMutex) {
            provider = mServiceConnection.getProvider();
        }
        if (provider != null) {
            try {
                return provider.getStatus(extras);
            } catch (RemoteException e) {
            }
        }
        return 0;
    }

    public long getStatusUpdateTime() {
        ILocationProvider provider;
        synchronized (mMutex) {
            provider = mServiceConnection.getProvider();
        }
        if (provider != null) {
            try {
                return provider.getStatusUpdateTime();
            } catch (RemoteException e) {
            }
        }
        return 0;
     }

    public String getInternalState() {
        ILocationProvider provider;
        synchronized (mMutex) {
            provider = mServiceConnection.getProvider();
        }
        if (provider != null) {
            try {
                return provider.getInternalState();
            } catch (RemoteException e) {
                Log.e(TAG, "getInternalState failed", e);
            }
        }
        return null;
    }

    public boolean isLocationTracking() {
        synchronized (mMutex) {
            return mLocationTracking;
        }
    }

    public void enableLocationTracking(boolean enable) {
        synchronized (mMutex) {
            mLocationTracking = enable;
            if (!enable) {
                mMinTime = -1;
                mMinTimeSource.clear();
            }
            ILocationProvider provider = mServiceConnection.getProvider();
            if (provider != null) {
                try {
                    provider.enableLocationTracking(enable);
                } catch (RemoteException e) {
                }
            }
        }
    }

    public boolean requestSingleShotFix() {
        return false;
    }

    public long getMinTime() {
        synchronized (mMutex) {
            return mMinTime;
        }
    }

    public void setMinTime(long minTime, WorkSource ws) {
        synchronized (mMutex) {
            mMinTime = minTime;
            mMinTimeSource.set(ws);
            ILocationProvider provider = mServiceConnection.getProvider();
            if (provider != null) {
                try {
                    provider.setMinTime(minTime, ws);
                } catch (RemoteException e) {
                }
            }
        }
    }

    public void updateNetworkState(int state, NetworkInfo info) {
        synchronized (mMutex) {
            mNetworkState = state;
            mNetworkInfo = info;
            ILocationProvider provider = mServiceConnection.getProvider();
            if (provider != null) {
                try {
                    provider.updateNetworkState(state, info);
                } catch (RemoteException e) {
                }
            }
        }
    }

    public void updateLocation(Location location) {
        synchronized (mMutex) {
            ILocationProvider provider = mServiceConnection.getProvider();
            if (provider != null) {
                try {
                    provider.updateLocation(location);
                } catch (RemoteException e) {
                }
            }
        }
    }

    public boolean sendExtraCommand(String command, Bundle extras) {
        synchronized (mMutex) {
            ILocationProvider provider = mServiceConnection.getProvider();
            if (provider != null) {
                try {
                    return provider.sendExtraCommand(command, extras);
                } catch (RemoteException e) {
                }
            }
        }
        return false;
    }

    public void addListener(int uid) {
        synchronized (mMutex) {
            ILocationProvider provider = mServiceConnection.getProvider();
            if (provider != null) {
                try {
                    provider.addListener(uid);
                } catch (RemoteException e) {
                }
            }
        }
    }

    public void removeListener(int uid) {
        synchronized (mMutex) {
            ILocationProvider provider = mServiceConnection.getProvider();
            if (provider != null) {
                try {
                    provider.removeListener(uid);
                } catch (RemoteException e) {
                }
            }
        }
    }
}
=======
    @Override
    public ProviderProperties getProperties() {
        synchronized (mLock) {
            return mProperties;
        }
    }

    @Override
    public void enable() {
        synchronized (mLock) {
            mEnabled = true;
        }
        ILocationProvider service = getService();
        if (service == null) return;

        try {
            service.enable();
        } catch (RemoteException e) {
            Log.w(TAG, e);
        } catch (Exception e) {
            // never let remote service crash system server
            Log.e(TAG, "Exception from " + mServiceWatcher.getBestPackageName(), e);
        }
    }

    @Override
    public void disable() {
        synchronized (mLock) {
            mEnabled = false;
        }
        ILocationProvider service = getService();
        if (service == null) return;

        try {
            service.disable();
        } catch (RemoteException e) {
            Log.w(TAG, e);
        } catch (Exception e) {
            // never let remote service crash system server
            Log.e(TAG, "Exception from " + mServiceWatcher.getBestPackageName(), e);
        }
    }

    @Override
    public boolean isEnabled() {
        synchronized (mLock) {
            return mEnabled;
        }
    }

    @Override
    public void setRequest(ProviderRequest request, WorkSource source) {
        synchronized (mLock) {
            mRequest = request;
            mWorksource = source;
        }
        ILocationProvider service = getService();
        if (service == null) return;

        try {
            service.setRequest(request, source);
        } catch (RemoteException e) {
            Log.w(TAG, e);
        } catch (Exception e) {
            // never let remote service crash system server
            Log.e(TAG, "Exception from " + mServiceWatcher.getBestPackageName(), e);
        }
    }

    @Override
    public void switchUser(int userId) {
        mServiceWatcher.switchUser(userId);
    }

    @Override
    public void dump(FileDescriptor fd, PrintWriter pw, String[] args) {
        pw.append("REMOTE SERVICE");
        pw.append(" name=").append(mName);
        pw.append(" pkg=").append(mServiceWatcher.getBestPackageName());
        pw.append(" version=").append("" + mServiceWatcher.getBestVersion());
        pw.append('\n');

        ILocationProvider service = getService();
        if (service == null) {
            pw.println("service down (null)");
            return;
        }
        pw.flush();

        try {
            service.asBinder().dump(fd, args);
        } catch (RemoteException e) {
            pw.println("service down (RemoteException)");
            Log.w(TAG, e);
        } catch (Exception e) {
            pw.println("service down (Exception)");
            // never let remote service crash system server
            Log.e(TAG, "Exception from " + mServiceWatcher.getBestPackageName(), e);
        }
    }

    @Override
    public int getStatus(Bundle extras) {
        ILocationProvider service = getService();
        if (service == null) return LocationProvider.TEMPORARILY_UNAVAILABLE;

        try {
            return service.getStatus(extras);
        } catch (RemoteException e) {
            Log.w(TAG, e);
        } catch (Exception e) {
            // never let remote service crash system server
            Log.e(TAG, "Exception from " + mServiceWatcher.getBestPackageName(), e);
        }
        return LocationProvider.TEMPORARILY_UNAVAILABLE;
    }

    @Override
    public long getStatusUpdateTime() {
        ILocationProvider service = getService();
        if (service == null) return 0;

        try {
            return service.getStatusUpdateTime();
        } catch (RemoteException e) {
            Log.w(TAG, e);
        } catch (Exception e) {
            // never let remote service crash system server
            Log.e(TAG, "Exception from " + mServiceWatcher.getBestPackageName(), e);
        }
        return 0;
    }

    @Override
    public boolean sendExtraCommand(String command, Bundle extras) {
        ILocationProvider service = getService();
        if (service == null) return false;

        try {
            return service.sendExtraCommand(command, extras);
        } catch (RemoteException e) {
            Log.w(TAG, e);
        } catch (Exception e) {
            // never let remote service crash system server
            Log.e(TAG, "Exception from " + mServiceWatcher.getBestPackageName(), e);
        }
        return false;
    }
 }
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
