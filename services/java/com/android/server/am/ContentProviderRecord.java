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

package com.android.server.am;

import android.app.IActivityManager.ContentProviderHolder;
import android.content.ComponentName;
import android.content.IContentProvider;
import android.content.pm.ApplicationInfo;
import android.content.pm.ProviderInfo;
import android.os.IBinder;
import android.os.IBinder.DeathRecipient;
import android.os.Process;
import android.os.RemoteException;
<<<<<<< HEAD
=======
import android.os.UserHandle;
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
import android.util.Slog;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
<<<<<<< HEAD
import java.util.Iterator;
=======
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a

class ContentProviderRecord {
    final ActivityManagerService service;
    public final ProviderInfo info;
    final int uid;
    final ApplicationInfo appInfo;
    final ComponentName name;
<<<<<<< HEAD
=======
    final boolean singleton;
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
    public IContentProvider provider;
    public boolean noReleaseNeeded;
    // All attached clients
    final ArrayList<ContentProviderConnection> connections
            = new ArrayList<ContentProviderConnection>();
    //final HashSet<ProcessRecord> clients = new HashSet<ProcessRecord>();
    // Handles for non-framework processes supported by this provider
    HashMap<IBinder, ExternalProcessHandle> externalProcessTokenToHandle;
    // Count for external process for which we have no handles.
    int externalProcessNoHandleCount;
    ProcessRecord proc; // if non-null, hosting process.
    ProcessRecord launchingApp; // if non-null, waiting for this app to be launched.
    String stringName;
    String shortStringName;

    public ContentProviderRecord(ActivityManagerService _service, ProviderInfo _info,
<<<<<<< HEAD
            ApplicationInfo ai, ComponentName _name) {
=======
            ApplicationInfo ai, ComponentName _name, boolean _singleton) {
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
        service = _service;
        info = _info;
        uid = ai.uid;
        appInfo = ai;
        name = _name;
<<<<<<< HEAD
=======
        singleton = _singleton;
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
        noReleaseNeeded = uid == 0 || uid == Process.SYSTEM_UID;
    }

    public ContentProviderRecord(ContentProviderRecord cpr) {
        service = cpr.service;
        info = cpr.info;
        uid = cpr.uid;
        appInfo = cpr.appInfo;
        name = cpr.name;
<<<<<<< HEAD
=======
        singleton = cpr.singleton;
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
        noReleaseNeeded = cpr.noReleaseNeeded;
    }

    public ContentProviderHolder newHolder(ContentProviderConnection conn) {
        ContentProviderHolder holder = new ContentProviderHolder(info);
        holder.provider = provider;
        holder.noReleaseNeeded = noReleaseNeeded;
        holder.connection = conn;
        return holder;
    }

    public boolean canRunHere(ProcessRecord app) {
        return (info.multiprocess || info.processName.equals(app.processName))
<<<<<<< HEAD
                && (uid == Process.SYSTEM_UID || uid == app.info.uid);
=======
                && uid == app.info.uid;
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
    }

    public void addExternalProcessHandleLocked(IBinder token) {
        if (token == null) {
            externalProcessNoHandleCount++;
        } else {
            if (externalProcessTokenToHandle == null) {
                externalProcessTokenToHandle = new HashMap<IBinder, ExternalProcessHandle>();
            }
            ExternalProcessHandle handle = externalProcessTokenToHandle.get(token);
            if (handle == null) {
                handle = new ExternalProcessHandle(token);
                externalProcessTokenToHandle.put(token, handle);
            }
            handle.mAcquisitionCount++;
        }
    }

    public boolean removeExternalProcessHandleLocked(IBinder token) {
        if (hasExternalProcessHandles()) {
            boolean hasHandle = false;
            if (externalProcessTokenToHandle != null) {
                ExternalProcessHandle handle = externalProcessTokenToHandle.get(token);
                if (handle != null) {
                    hasHandle = true;
                    handle.mAcquisitionCount--;
                    if (handle.mAcquisitionCount == 0) {
                        removeExternalProcessHandleInternalLocked(token);
                        return true;
                    }
                }
            }
            if (!hasHandle) {
                externalProcessNoHandleCount--;
                return true;
            }
        }
        return false;
    }

    private void removeExternalProcessHandleInternalLocked(IBinder token) {
        ExternalProcessHandle handle = externalProcessTokenToHandle.get(token);
        handle.unlinkFromOwnDeathLocked();
        externalProcessTokenToHandle.remove(token);
        if (externalProcessTokenToHandle.size() == 0) {
            externalProcessTokenToHandle = null;
        }
    }

    public boolean hasExternalProcessHandles() {
        return (externalProcessTokenToHandle != null || externalProcessNoHandleCount > 0);
    }

    void dump(PrintWriter pw, String prefix, boolean full) {
        if (full) {
            pw.print(prefix); pw.print("package=");
                    pw.print(info.applicationInfo.packageName);
                    pw.print(" process="); pw.println(info.processName);
        }
        pw.print(prefix); pw.print("proc="); pw.println(proc);
        if (launchingApp != null) {
            pw.print(prefix); pw.print("launchingApp="); pw.println(launchingApp);
        }
        if (full) {
            pw.print(prefix); pw.print("uid="); pw.print(uid);
                    pw.print(" provider="); pw.println(provider);
        }
<<<<<<< HEAD
=======
        if (singleton) {
            pw.print(prefix); pw.print("singleton="); pw.println(singleton);
        }
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
        pw.print(prefix); pw.print("authority="); pw.println(info.authority);
        if (full) {
            if (info.isSyncable || info.multiprocess || info.initOrder != 0) {
                pw.print(prefix); pw.print("isSyncable="); pw.print(info.isSyncable);
                        pw.print(" multiprocess="); pw.print(info.multiprocess);
                        pw.print(" initOrder="); pw.println(info.initOrder);
            }
        }
        if (full) {
            if (hasExternalProcessHandles()) {
                pw.print(prefix); pw.print("externals=");
                        pw.println(externalProcessTokenToHandle.size());
            }
        } else {
            if (connections.size() > 0 || externalProcessNoHandleCount > 0) {
                pw.print(prefix); pw.print(connections.size());
                        pw.print(" connections, "); pw.print(externalProcessNoHandleCount);
                        pw.println(" external handles");
            }
        }
        if (connections.size() > 0) {
            if (full) {
                pw.print(prefix); pw.println("Connections:");
            }
            for (int i=0; i<connections.size(); i++) {
                ContentProviderConnection conn = connections.get(i);
                pw.print(prefix); pw.print("  -> "); pw.println(conn.toClientString());
                if (conn.provider != this) {
                    pw.print(prefix); pw.print("    *** WRONG PROVIDER: ");
                            pw.println(conn.provider);
                }
            }
        }
    }

    @Override
    public String toString() {
        if (stringName != null) {
            return stringName;
        }
        StringBuilder sb = new StringBuilder(128);
        sb.append("ContentProviderRecord{");
        sb.append(Integer.toHexString(System.identityHashCode(this)));
<<<<<<< HEAD
=======
        sb.append(" u");
        sb.append(UserHandle.getUserId(uid));
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
        sb.append(' ');
        sb.append(name.flattenToShortString());
        sb.append('}');
        return stringName = sb.toString();
    }

    public String toShortString() {
        if (shortStringName != null) {
            return shortStringName;
        }
        StringBuilder sb = new StringBuilder(128);
        sb.append(Integer.toHexString(System.identityHashCode(this)));
        sb.append('/');
        sb.append(name.flattenToShortString());
        return shortStringName = sb.toString();
    }

<<<<<<< HEAD
    protected void finalize() throws Throwable {
        try {
            synchronized (service) {
                if (externalProcessTokenToHandle != null) {
                    Iterator iterator = externalProcessTokenToHandle.keySet().iterator();
                    while (iterator.hasNext()) {
                         removeExternalProcessHandleInternalLocked((IBinder)iterator.next());
                    }
                }
            }
        } finally {
             super.finalize();
        }
    }

=======
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
    // This class represents a handle from an external process to a provider.
    private class ExternalProcessHandle implements DeathRecipient {
        private static final String LOG_TAG = "ExternalProcessHanldle";

        private final IBinder mToken;
        private int mAcquisitionCount;

        public ExternalProcessHandle(IBinder token) {
            mToken = token;
            try {
                token.linkToDeath(this, 0);
            } catch (RemoteException re) {
                Slog.e(LOG_TAG, "Couldn't register for death for token: " + mToken, re);
            }
        }

        public void unlinkFromOwnDeathLocked() {
            mToken.unlinkToDeath(this, 0);
        }

        @Override
        public void binderDied() {
            synchronized (service) {
                if (hasExternalProcessHandles() &&
                        externalProcessTokenToHandle.get(mToken) != null) {
                    removeExternalProcessHandleInternalLocked(mToken);
                }                        
            }
        }
    }
}
