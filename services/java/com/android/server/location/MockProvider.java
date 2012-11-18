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

import android.location.Criteria;
import android.location.ILocationManager;
import android.location.Location;
import android.location.LocationProvider;
<<<<<<< HEAD
import android.net.NetworkInfo;
=======
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
import android.os.Bundle;
import android.os.RemoteException;
import android.os.WorkSource;
import android.util.Log;
import android.util.PrintWriterPrinter;

<<<<<<< HEAD
import java.io.PrintWriter;

=======

import java.io.FileDescriptor;
import java.io.PrintWriter;

import com.android.internal.location.ProviderProperties;
import com.android.internal.location.ProviderRequest;

>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
/**
 * A mock location provider used by LocationManagerService to implement test providers.
 *
 * {@hide}
 */
public class MockProvider implements LocationProviderInterface {
    private final String mName;
<<<<<<< HEAD
    private final ILocationManager mLocationManager;
    private final boolean mRequiresNetwork;
    private final boolean mRequiresSatellite;
    private final boolean mRequiresCell;
    private final boolean mHasMonetaryCost;
    private final boolean mSupportsAltitude;
    private final boolean mSupportsSpeed;
    private final boolean mSupportsBearing;
    private final int mPowerRequirement;
    private final int mAccuracy;
    private final Location mLocation;
    private int mStatus;
    private long mStatusUpdateTime;
    private final Bundle mExtras = new Bundle();
=======
    private final ProviderProperties mProperties;
    private final ILocationManager mLocationManager;

    private final Location mLocation;
    private final Bundle mExtras = new Bundle();

    private int mStatus;
    private long mStatusUpdateTime;
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
    private boolean mHasLocation;
    private boolean mHasStatus;
    private boolean mEnabled;

    private static final String TAG = "MockProvider";

<<<<<<< HEAD
    public MockProvider(String name,  ILocationManager locationManager,
        boolean requiresNetwork, boolean requiresSatellite,
        boolean requiresCell, boolean hasMonetaryCost, boolean supportsAltitude,
        boolean supportsSpeed, boolean supportsBearing, int powerRequirement, int accuracy) {
        mName = name;
        mLocationManager = locationManager;
        mRequiresNetwork = requiresNetwork;
        mRequiresSatellite = requiresSatellite;
        mRequiresCell = requiresCell;
        mHasMonetaryCost = hasMonetaryCost;
        mSupportsAltitude = supportsAltitude;
        mSupportsBearing = supportsBearing;
        mSupportsSpeed = supportsSpeed;
        mPowerRequirement = powerRequirement;
        mAccuracy = accuracy;
        mLocation = new Location(name);
    }

=======
    public MockProvider(String name, ILocationManager locationManager,
            ProviderProperties properties) {
        if (properties == null) throw new NullPointerException("properties is null");

        mName = name;
        mLocationManager = locationManager;
        mProperties = properties;
        mLocation = new Location(name);
    }

    @Override
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
    public String getName() {
        return mName;
    }

<<<<<<< HEAD
=======
    @Override
    public ProviderProperties getProperties() {
        return mProperties;
    }

    @Override
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
    public void disable() {
        mEnabled = false;
    }

<<<<<<< HEAD
=======
    @Override
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
    public void enable() {
        mEnabled = true;
    }

<<<<<<< HEAD
=======
    @Override
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
    public boolean isEnabled() {
        return mEnabled;
    }

<<<<<<< HEAD
=======
    @Override
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
    public int getStatus(Bundle extras) {
        if (mHasStatus) {
            extras.clear();
            extras.putAll(mExtras);
            return mStatus;
        } else {
            return LocationProvider.AVAILABLE;
        }
    }

<<<<<<< HEAD
=======
    @Override
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
    public long getStatusUpdateTime() {
        return mStatusUpdateTime;
    }

<<<<<<< HEAD
    public int getAccuracy() {
        return mAccuracy;
    }

    public int getPowerRequirement() {
        return mPowerRequirement;
    }

    public boolean hasMonetaryCost() {
        return mHasMonetaryCost;
    }

    public boolean requiresCell() {
        return mRequiresCell;
    }

    public boolean requiresNetwork() {
        return mRequiresNetwork;
    }

    public boolean requiresSatellite() {
        return mRequiresSatellite;
    }

    public boolean supportsAltitude() {
        return mSupportsAltitude;
    }

    public boolean supportsBearing() {
        return mSupportsBearing;
    }

    public boolean supportsSpeed() {
        return mSupportsSpeed;
    }

    public boolean meetsCriteria(Criteria criteria) {
        if ((criteria.getAccuracy() != Criteria.NO_REQUIREMENT) &&
            (criteria.getAccuracy() < mAccuracy)) {
            return false;
        }
        int criteriaPower = criteria.getPowerRequirement();
        if ((criteriaPower != Criteria.NO_REQUIREMENT) &&
            (criteriaPower < mPowerRequirement)) {
            return false;
        }
        if (criteria.isAltitudeRequired() && !mSupportsAltitude) {
            return false;
        }
        if (criteria.isSpeedRequired() && !mSupportsSpeed) {
            return false;
        }
        if (criteria.isBearingRequired() && !mSupportsBearing) {
            return false;
        }
        return true;
    }

    public void setLocation(Location l) {
        mLocation.set(l);
        mHasLocation = true;
        try {
            mLocationManager.reportLocation(mLocation, false);
        } catch (RemoteException e) {
            Log.e(TAG, "RemoteException calling reportLocation");
=======
    public void setLocation(Location l) {
        mLocation.set(l);
        mHasLocation = true;
        if (mEnabled) {
            try {
                mLocationManager.reportLocation(mLocation, false);
            } catch (RemoteException e) {
                Log.e(TAG, "RemoteException calling reportLocation");
            }
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
        }
    }

    public void clearLocation() {
        mHasLocation = false;
    }

    public void setStatus(int status, Bundle extras, long updateTime) {
        mStatus = status;
        mStatusUpdateTime = updateTime;
        mExtras.clear();
        if (extras != null) {
            mExtras.putAll(extras);
        }
        mHasStatus = true;
    }

    public void clearStatus() {
        mHasStatus = false;
        mStatusUpdateTime = 0;
    }

<<<<<<< HEAD
    public String getInternalState() {
        return null;
    }

    public void enableLocationTracking(boolean enable) {
    }

    public boolean requestSingleShotFix() {
        return false;
    }

    public void setMinTime(long minTime, WorkSource ws) {
    }

    public void updateNetworkState(int state, NetworkInfo info) {
    }

    public void updateLocation(Location location) {
    }

    public boolean sendExtraCommand(String command, Bundle extras) {
        return false;
    }

    public void addListener(int uid) {
    }

    public void removeListener(int uid) {
=======
    @Override
    public void dump(FileDescriptor fd, PrintWriter pw, String[] args) {
        dump(pw, "");
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
    }

    public void dump(PrintWriter pw, String prefix) {
        pw.println(prefix + mName);
        pw.println(prefix + "mHasLocation=" + mHasLocation);
        pw.println(prefix + "mLocation:");
        mLocation.dump(new PrintWriterPrinter(pw), prefix + "  ");
        pw.println(prefix + "mHasStatus=" + mHasStatus);
        pw.println(prefix + "mStatus=" + mStatus);
        pw.println(prefix + "mStatusUpdateTime=" + mStatusUpdateTime);
        pw.println(prefix + "mExtras=" + mExtras);
    }
<<<<<<< HEAD
=======

    @Override
    public void setRequest(ProviderRequest request, WorkSource source) { }

    @Override
    public void switchUser(int userId) {
        // nothing to do here
    }

    @Override
    public boolean sendExtraCommand(String command, Bundle extras) {
        return false;
    }
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
}
