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
=======
import java.io.FileDescriptor;
import java.io.PrintWriter;

import com.android.internal.location.ProviderProperties;
import com.android.internal.location.ProviderRequest;

>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
import android.location.Criteria;
import android.location.ILocationManager;
import android.location.Location;
import android.location.LocationManager;
import android.location.LocationProvider;
<<<<<<< HEAD
import android.net.NetworkInfo;
=======
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
import android.os.Bundle;
import android.os.RemoteException;
import android.os.WorkSource;
import android.util.Log;

<<<<<<< HEAD
=======

>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
/**
 * A passive location provider reports locations received from other providers
 * for clients that want to listen passively without actually triggering
 * location updates.
 *
 * {@hide}
 */
public class PassiveProvider implements LocationProviderInterface {
<<<<<<< HEAD

    private static final String TAG = "PassiveProvider";

    private final ILocationManager mLocationManager;
    private boolean mTracking;
=======
    private static final String TAG = "PassiveProvider";

    private static final ProviderProperties PROPERTIES = new ProviderProperties(
            false, false, false, false, false, false, false,
            Criteria.POWER_LOW, Criteria.ACCURACY_COARSE);

    private final ILocationManager mLocationManager;
    private boolean mReportLocation;
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a

    public PassiveProvider(ILocationManager locationManager) {
        mLocationManager = locationManager;
    }

<<<<<<< HEAD
=======
    @Override
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
    public String getName() {
        return LocationManager.PASSIVE_PROVIDER;
    }

<<<<<<< HEAD
    public boolean requiresNetwork() {
        return false;
    }

    public boolean requiresSatellite() {
        return false;
    }

    public boolean requiresCell() {
        return false;
    }

    public boolean hasMonetaryCost() {
        return false;
    }

    public boolean supportsAltitude() {
        return false;
    }

    public boolean supportsSpeed() {
        return false;
    }

    public boolean supportsBearing() {
        return false;
    }

    public int getPowerRequirement() {
        return -1;
    }

    public boolean meetsCriteria(Criteria criteria) {
        // We do not want to match the special passive provider based on criteria.
        return false;
    }

    public int getAccuracy() {
        return -1;
    }

=======
    @Override
    public ProviderProperties getProperties() {
        return PROPERTIES;
    }

    @Override
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
    public boolean isEnabled() {
        return true;
    }

<<<<<<< HEAD
    public void enable() {
    }

    public void disable() {
    }

    public int getStatus(Bundle extras) {
        if (mTracking) {
=======
    @Override
    public void enable() {
    }

    @Override
    public void disable() {
    }

    @Override
    public int getStatus(Bundle extras) {
        if (mReportLocation) {
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
            return LocationProvider.AVAILABLE;
        } else {
            return LocationProvider.TEMPORARILY_UNAVAILABLE;
        }
    }

<<<<<<< HEAD
=======
    @Override
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
    public long getStatusUpdateTime() {
        return -1;
    }

<<<<<<< HEAD
    public String getInternalState() {
        return null;
    }

    public void enableLocationTracking(boolean enable) {
        mTracking = enable;
    }

    public boolean requestSingleShotFix() {
        return false;
    }

    public void setMinTime(long minTime, WorkSource ws) {
    }

    public void updateNetworkState(int state, NetworkInfo info) {
    }

    public void updateLocation(Location location) {
        if (mTracking) {
=======
    @Override
    public void setRequest(ProviderRequest request, WorkSource source) {
        mReportLocation = request.reportLocation;
    }

    @Override
    public void switchUser(int userId) {
        // nothing to do here
    }

    public void updateLocation(Location location) {
        if (mReportLocation) {
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
            try {
                // pass the location back to the location manager
                mLocationManager.reportLocation(location, true);
            } catch (RemoteException e) {
                Log.e(TAG, "RemoteException calling reportLocation");
            }
        }
    }

<<<<<<< HEAD
=======
    @Override
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
    public boolean sendExtraCommand(String command, Bundle extras) {
        return false;
    }

<<<<<<< HEAD
    public void addListener(int uid) {
    }

    public void removeListener(int uid) {
=======
    @Override
    public void dump(FileDescriptor fd, PrintWriter pw, String[] args) {
        pw.println("mReportLocation=" + mReportLocation);
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
    }
}
