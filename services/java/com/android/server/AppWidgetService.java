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

package com.android.server;

<<<<<<< HEAD
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
=======
import android.app.ActivityManagerNative;
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
import android.appwidget.AppWidgetProviderInfo;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
<<<<<<< HEAD
import android.content.ServiceConnection;
=======
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
import android.content.pm.PackageManager;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
<<<<<<< HEAD
import android.util.Pair;
=======
import android.os.UserHandle;
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
import android.util.Slog;
import android.util.SparseArray;
import android.widget.RemoteViews;

import com.android.internal.appwidget.IAppWidgetHost;
import com.android.internal.appwidget.IAppWidgetService;
<<<<<<< HEAD
import com.android.internal.widget.IRemoteViewsAdapterConnection;

import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
=======
import com.android.internal.util.IndentingPrintWriter;

import java.io.FileDescriptor;
import java.io.PrintWriter;
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
import java.util.List;
import java.util.Locale;


/**
 * Redirects calls to this service to the instance of the service for the appropriate user.
 */
class AppWidgetService extends IAppWidgetService.Stub
{
    private static final String TAG = "AppWidgetService";

<<<<<<< HEAD
    /*
     * When identifying a Host or Provider based on the calling process, use the uid field.
     * When identifying a Host or Provider based on a package manager broadcast, use the
     * package given.
     */

    static class Provider {
        int uid;
        AppWidgetProviderInfo info;
        ArrayList<AppWidgetId> instances = new ArrayList<AppWidgetId>();
        PendingIntent broadcast;
        boolean zombie; // if we're in safe mode, don't prune this just because nobody references it
        
        int tag;    // for use while saving state (the index)
    }

    static class Host {
        int uid;
        int hostId;
        String packageName;
        ArrayList<AppWidgetId> instances = new ArrayList<AppWidgetId>();
        IAppWidgetHost callbacks;
        boolean zombie; // if we're in safe mode, don't prune this just because nobody references it
        
        int tag;    // for use while saving state (the index)
    }

    static class AppWidgetId {
        int appWidgetId;
        Provider provider;
        RemoteViews views;
        Host host;
    }

    /**
     * Acts as a proxy between the ServiceConnection and the RemoteViewsAdapterConnection.
     * This needs to be a static inner class since a reference to the ServiceConnection is held
     * globally and may lead us to leak AppWidgetService instances (if there were more than one).
     */
    static class ServiceConnectionProxy implements ServiceConnection {
        private final IBinder mConnectionCb;

        ServiceConnectionProxy(Pair<Integer, Intent.FilterComparison> key, IBinder connectionCb) {
            mConnectionCb = connectionCb;
        }
        public void onServiceConnected(ComponentName name, IBinder service) {
            final IRemoteViewsAdapterConnection cb =
                IRemoteViewsAdapterConnection.Stub.asInterface(mConnectionCb);
            try {
                cb.onServiceConnected(service);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        public void onServiceDisconnected(ComponentName name) {
            disconnect();
        }
        public void disconnect() {
            final IRemoteViewsAdapterConnection cb =
                IRemoteViewsAdapterConnection.Stub.asInterface(mConnectionCb);
            try {
                cb.onServiceDisconnected();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    Context mContext;
    Locale mLocale;
    PackageManager mPackageManager;
    AlarmManager mAlarmManager;
    ArrayList<Provider> mInstalledProviders = new ArrayList<Provider>();
    int mNextAppWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID + 1;
    final ArrayList<AppWidgetId> mAppWidgetIds = new ArrayList<AppWidgetId>();
    ArrayList<Host> mHosts = new ArrayList<Host>();
    boolean mSafeMode;


=======
    Context mContext;
    Locale mLocale;
    PackageManager mPackageManager;
    boolean mSafeMode;

>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
    private final SparseArray<AppWidgetServiceImpl> mAppWidgetServices;

    AppWidgetService(Context context) {
        mContext = context;
        mAppWidgetServices = new SparseArray<AppWidgetServiceImpl>(5);
        AppWidgetServiceImpl primary = new AppWidgetServiceImpl(context, 0);
        mAppWidgetServices.append(0, primary);
    }

    public void systemReady(boolean safeMode) {
        mSafeMode = safeMode;

        mAppWidgetServices.get(0).systemReady(safeMode);

        // Register for the boot completed broadcast, so we can send the
        // ENABLE broacasts. If we try to send them now, they time out,
        // because the system isn't ready to handle them yet.
<<<<<<< HEAD
        mContext.registerReceiver(mBroadcastReceiver,
=======
        mContext.registerReceiverAsUser(mBroadcastReceiver, UserHandle.ALL,
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
                new IntentFilter(Intent.ACTION_BOOT_COMPLETED), null, null);

        // Register for configuration changes so we can update the names
        // of the widgets when the locale changes.
<<<<<<< HEAD
        mContext.registerReceiver(mBroadcastReceiver, new IntentFilter(
                Intent.ACTION_CONFIGURATION_CHANGED), null, null);
=======
        mContext.registerReceiverAsUser(mBroadcastReceiver, UserHandle.ALL,
                new IntentFilter(Intent.ACTION_CONFIGURATION_CHANGED), null, null);
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a

        // Register for broadcasts about package install, etc., so we can
        // update the provider list.
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_PACKAGE_ADDED);
        filter.addAction(Intent.ACTION_PACKAGE_CHANGED);
        filter.addAction(Intent.ACTION_PACKAGE_REMOVED);
        filter.addDataScheme("package");
<<<<<<< HEAD
        mContext.registerReceiver(mBroadcastReceiver, filter);
=======
        mContext.registerReceiverAsUser(mBroadcastReceiver, UserHandle.ALL,
                filter, null, null);
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
        // Register for events related to sdcard installation.
        IntentFilter sdFilter = new IntentFilter();
        sdFilter.addAction(Intent.ACTION_EXTERNAL_APPLICATIONS_AVAILABLE);
        sdFilter.addAction(Intent.ACTION_EXTERNAL_APPLICATIONS_UNAVAILABLE);
<<<<<<< HEAD
        mContext.registerReceiver(mBroadcastReceiver, sdFilter);

        IntentFilter userFilter = new IntentFilter();
        userFilter.addAction(Intent.ACTION_USER_REMOVED);
        mContext.registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                onUserRemoved(intent.getIntExtra(Intent.EXTRA_USERID, -1));
=======
        mContext.registerReceiverAsUser(mBroadcastReceiver, UserHandle.ALL,
                sdFilter, null, null);

        IntentFilter userFilter = new IntentFilter();
        userFilter.addAction(Intent.ACTION_USER_REMOVED);
        userFilter.addAction(Intent.ACTION_USER_STOPPING);
        mContext.registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (Intent.ACTION_USER_REMOVED.equals(intent.getAction())) {
                    onUserRemoved(intent.getIntExtra(Intent.EXTRA_USER_HANDLE,
                            UserHandle.USER_NULL));
                } else if (Intent.ACTION_USER_STOPPING.equals(intent.getAction())) {
                    onUserStopping(intent.getIntExtra(Intent.EXTRA_USER_HANDLE,
                            UserHandle.USER_NULL));
                }
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
            }
        }, userFilter);
    }

<<<<<<< HEAD
    @Override
    public int allocateAppWidgetId(String packageName, int hostId) throws RemoteException {
        return getImplForUser().allocateAppWidgetId(packageName, hostId);
=======
    /**
     * This returns the user id of the caller, if the caller is not the system process,
     * otherwise it assumes that the calls are from the lockscreen and hence are meant for the
     * current user. TODO: Instead, have lockscreen make explicit calls with userId
     */
    private int getCallingOrCurrentUserId() {
        int callingUid = Binder.getCallingUid();
        // Also check the PID because Settings (power control widget) also runs as System UID
        if (callingUid == android.os.Process.myUid()
                && Binder.getCallingPid() == android.os.Process.myPid()) {
            try {
                return ActivityManagerNative.getDefault().getCurrentUser().id;
            } catch (RemoteException re) {
                return UserHandle.getUserId(callingUid);
            }
        } else {
            return UserHandle.getUserId(callingUid);
        }
    }

    @Override
    public int allocateAppWidgetId(String packageName, int hostId) throws RemoteException {
        return getImplForUser(getCallingOrCurrentUserId()).allocateAppWidgetId(
                packageName, hostId);
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
    }
    
    @Override
    public void deleteAppWidgetId(int appWidgetId) throws RemoteException {
<<<<<<< HEAD
        getImplForUser().deleteAppWidgetId(appWidgetId);
=======
        getImplForUser(getCallingOrCurrentUserId()).deleteAppWidgetId(appWidgetId);
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
    }

    @Override
    public void deleteHost(int hostId) throws RemoteException {
<<<<<<< HEAD
        getImplForUser().deleteHost(hostId);
=======
        getImplForUser(getCallingOrCurrentUserId()).deleteHost(hostId);
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
    }

    @Override
    public void deleteAllHosts() throws RemoteException {
<<<<<<< HEAD
        getImplForUser().deleteAllHosts();
    }

    @Override
    public void bindAppWidgetId(int appWidgetId, ComponentName provider) throws RemoteException {
        getImplForUser().bindAppWidgetId(appWidgetId, provider);
=======
        getImplForUser(getCallingOrCurrentUserId()).deleteAllHosts();
    }

    @Override
    public void bindAppWidgetId(int appWidgetId, ComponentName provider, Bundle options)
            throws RemoteException {
        getImplForUser(getCallingOrCurrentUserId()).bindAppWidgetId(appWidgetId, provider,
                options);
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
    }

    @Override
    public boolean bindAppWidgetIdIfAllowed(
<<<<<<< HEAD
            String packageName, int appWidgetId, ComponentName provider) throws RemoteException {
        return getImplForUser().bindAppWidgetIdIfAllowed(packageName, appWidgetId, provider);
=======
            String packageName, int appWidgetId, ComponentName provider, Bundle options)
                    throws RemoteException {
        return getImplForUser(getCallingOrCurrentUserId()).bindAppWidgetIdIfAllowed(
                packageName, appWidgetId, provider, options);
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
    }

    @Override
    public boolean hasBindAppWidgetPermission(String packageName) throws RemoteException {
<<<<<<< HEAD
        return getImplForUser().hasBindAppWidgetPermission(packageName);
=======
        return getImplForUser(getCallingOrCurrentUserId()).hasBindAppWidgetPermission(
                packageName);
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
    }

    @Override
    public void setBindAppWidgetPermission(String packageName, boolean permission)
            throws RemoteException {
<<<<<<< HEAD
        getImplForUser().setBindAppWidgetPermission(packageName, permission);
=======
        getImplForUser(getCallingOrCurrentUserId()).setBindAppWidgetPermission(
                packageName, permission);
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
    }

    @Override
    public void bindRemoteViewsService(int appWidgetId, Intent intent, IBinder connection)
            throws RemoteException {
<<<<<<< HEAD
        getImplForUser().bindRemoteViewsService(appWidgetId, intent, connection);
=======
        getImplForUser(getCallingOrCurrentUserId()).bindRemoteViewsService(
                appWidgetId, intent, connection);
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
    }

    @Override
    public int[] startListening(IAppWidgetHost host, String packageName, int hostId,
            List<RemoteViews> updatedViews) throws RemoteException {
<<<<<<< HEAD
        return getImplForUser().startListening(host, packageName, hostId, updatedViews);
    }

    public void onUserRemoved(int userId) {
        AppWidgetServiceImpl impl = mAppWidgetServices.get(userId);
        if (userId < 1) return;

        if (impl == null) {
            AppWidgetServiceImpl.getSettingsFile(userId).delete();
        } else {
            impl.onUserRemoved();
        }
    }

    private AppWidgetServiceImpl getImplForUser() {
        final int userId = Binder.getOrigCallingUser();
        AppWidgetServiceImpl service = mAppWidgetServices.get(userId);
        if (service == null) {
            Slog.e(TAG, "Unable to find AppWidgetServiceImpl for the current user");
            // TODO: Verify that it's a valid user
            service = new AppWidgetServiceImpl(mContext, userId);
            service.systemReady(mSafeMode);
            // Assume that BOOT_COMPLETED was received, as this is a non-primary user.
            service.sendInitialBroadcasts();
            mAppWidgetServices.append(userId, service);
        }

=======
        return getImplForUser(getCallingOrCurrentUserId()).startListening(host,
                packageName, hostId, updatedViews);
    }

    public void onUserRemoved(int userId) {
        if (userId < 1) return;
        synchronized (mAppWidgetServices) {
            AppWidgetServiceImpl impl = mAppWidgetServices.get(userId);
            mAppWidgetServices.remove(userId);

            if (impl == null) {
                AppWidgetServiceImpl.getSettingsFile(userId).delete();
            } else {
                impl.onUserRemoved();
            }
        }
    }

    public void onUserStopping(int userId) {
        if (userId < 1) return;
        synchronized (mAppWidgetServices) {
            AppWidgetServiceImpl impl = mAppWidgetServices.get(userId);
            if (impl != null) {
                mAppWidgetServices.remove(userId);
                impl.onUserStopping();
            }
        }
    }

    private AppWidgetServiceImpl getImplForUser(int userId) {
        boolean sendInitial = false;
        AppWidgetServiceImpl service;
        synchronized (mAppWidgetServices) {
            service = mAppWidgetServices.get(userId);
            if (service == null) {
                Slog.i(TAG, "Unable to find AppWidgetServiceImpl for user " + userId + ", adding");
                // TODO: Verify that it's a valid user
                service = new AppWidgetServiceImpl(mContext, userId);
                service.systemReady(mSafeMode);
                // Assume that BOOT_COMPLETED was received, as this is a non-primary user.
                mAppWidgetServices.append(userId, service);
                sendInitial = true;
            }
        }
        if (sendInitial) {
            service.sendInitialBroadcasts();
        }
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
        return service;
    }

    @Override
    public int[] getAppWidgetIds(ComponentName provider) throws RemoteException {
<<<<<<< HEAD
        return getImplForUser().getAppWidgetIds(provider);
=======
        return getImplForUser(getCallingOrCurrentUserId()).getAppWidgetIds(provider);
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
    }

    @Override
    public AppWidgetProviderInfo getAppWidgetInfo(int appWidgetId) throws RemoteException {
<<<<<<< HEAD
        return getImplForUser().getAppWidgetInfo(appWidgetId);
=======
        return getImplForUser(getCallingOrCurrentUserId()).getAppWidgetInfo(appWidgetId);
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
    }

    @Override
    public RemoteViews getAppWidgetViews(int appWidgetId) throws RemoteException {
<<<<<<< HEAD
        return getImplForUser().getAppWidgetViews(appWidgetId);
=======
        return getImplForUser(getCallingOrCurrentUserId()).getAppWidgetViews(appWidgetId);
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
    }

    @Override
    public void updateAppWidgetOptions(int appWidgetId, Bundle options) {
<<<<<<< HEAD
        getImplForUser().updateAppWidgetOptions(appWidgetId, options);
=======
        getImplForUser(getCallingOrCurrentUserId()).updateAppWidgetOptions(appWidgetId, options);
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
    }

    @Override
    public Bundle getAppWidgetOptions(int appWidgetId) {
<<<<<<< HEAD
        return getImplForUser().getAppWidgetOptions(appWidgetId);
    }

    static int[] getAppWidgetIds(Provider p) {
        int instancesSize = p.instances.size();
        int appWidgetIds[] = new int[instancesSize];
        for (int i=0; i<instancesSize; i++) {
            appWidgetIds[i] = p.instances.get(i).appWidgetId;
        }
        return appWidgetIds;
=======
        return getImplForUser(getCallingOrCurrentUserId()).getAppWidgetOptions(appWidgetId);
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
    }

    @Override
    public List<AppWidgetProviderInfo> getInstalledProviders() throws RemoteException {
<<<<<<< HEAD
        return getImplForUser().getInstalledProviders();
=======
        return getImplForUser(getCallingOrCurrentUserId()).getInstalledProviders();
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
    }

    @Override
    public void notifyAppWidgetViewDataChanged(int[] appWidgetIds, int viewId)
            throws RemoteException {
<<<<<<< HEAD
        getImplForUser().notifyAppWidgetViewDataChanged(appWidgetIds, viewId);
=======
        getImplForUser(getCallingOrCurrentUserId()).notifyAppWidgetViewDataChanged(
                appWidgetIds, viewId);
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
    }

    @Override
    public void partiallyUpdateAppWidgetIds(int[] appWidgetIds, RemoteViews views)
            throws RemoteException {
<<<<<<< HEAD
        getImplForUser().partiallyUpdateAppWidgetIds(appWidgetIds, views);
=======
        getImplForUser(getCallingOrCurrentUserId()).partiallyUpdateAppWidgetIds(
                appWidgetIds, views);
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
    }

    @Override
    public void stopListening(int hostId) throws RemoteException {
<<<<<<< HEAD
        getImplForUser().stopListening(hostId);
=======
        getImplForUser(getCallingOrCurrentUserId()).stopListening(hostId);
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
    }

    @Override
    public void unbindRemoteViewsService(int appWidgetId, Intent intent) throws RemoteException {
<<<<<<< HEAD
        getImplForUser().unbindRemoteViewsService(appWidgetId, intent);
=======
        getImplForUser(getCallingOrCurrentUserId()).unbindRemoteViewsService(
                appWidgetId, intent);
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
    }

    @Override
    public void updateAppWidgetIds(int[] appWidgetIds, RemoteViews views) throws RemoteException {
<<<<<<< HEAD
        getImplForUser().updateAppWidgetIds(appWidgetIds, views);
=======
        getImplForUser(getCallingOrCurrentUserId()).updateAppWidgetIds(appWidgetIds, views);
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
    }

    @Override
    public void updateAppWidgetProvider(ComponentName provider, RemoteViews views)
            throws RemoteException {
<<<<<<< HEAD
        getImplForUser().updateAppWidgetProvider(provider, views);
=======
        getImplForUser(getCallingOrCurrentUserId()).updateAppWidgetProvider(provider, views);
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
    }

    @Override
    public void dump(FileDescriptor fd, PrintWriter pw, String[] args) {
<<<<<<< HEAD
        // Dump the state of all the app widget providers
        for (int i = 0; i < mAppWidgetServices.size(); i++) {
            AppWidgetServiceImpl service = mAppWidgetServices.valueAt(i);
            service.dump(fd, pw, args);
=======
        mContext.enforceCallingOrSelfPermission(android.Manifest.permission.DUMP, TAG);

        // Dump the state of all the app widget providers
        synchronized (mAppWidgetServices) {
            IndentingPrintWriter ipw = new IndentingPrintWriter(pw, "  ");
            for (int i = 0; i < mAppWidgetServices.size(); i++) {
                pw.println("User: " + mAppWidgetServices.keyAt(i));
                ipw.increaseIndent();
                AppWidgetServiceImpl service = mAppWidgetServices.valueAt(i);
                service.dump(fd, ipw, args);
                ipw.decreaseIndent();
            }
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
        }
    }

    BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            // Slog.d(TAG, "received " + action);
            if (Intent.ACTION_BOOT_COMPLETED.equals(action)) {
<<<<<<< HEAD
                getImplForUser().sendInitialBroadcasts();
=======
                int userId = intent.getIntExtra(Intent.EXTRA_USER_HANDLE, UserHandle.USER_NULL);
                if (userId >= 0) {
                    getImplForUser(userId).sendInitialBroadcasts();
                } else {
                    Slog.w(TAG, "Incorrect user handle supplied in " + intent);
                }
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
            } else if (Intent.ACTION_CONFIGURATION_CHANGED.equals(action)) {
                for (int i = 0; i < mAppWidgetServices.size(); i++) {
                    AppWidgetServiceImpl service = mAppWidgetServices.valueAt(i);
                    service.onConfigurationChanged();
                }
            } else {
<<<<<<< HEAD
                for (int i = 0; i < mAppWidgetServices.size(); i++) {
                    AppWidgetServiceImpl service = mAppWidgetServices.valueAt(i);
                    service.onBroadcastReceived(intent);
=======
                int sendingUser = getSendingUserId();
                if (sendingUser == UserHandle.USER_ALL) {
                    for (int i = 0; i < mAppWidgetServices.size(); i++) {
                        AppWidgetServiceImpl service = mAppWidgetServices.valueAt(i);
                        service.onBroadcastReceived(intent);
                    }
                } else {
                    AppWidgetServiceImpl service = mAppWidgetServices.get(sendingUser);
                    if (service != null) {
                        service.onBroadcastReceived(intent);
                    }
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
                }
            }
        }
    };
}
