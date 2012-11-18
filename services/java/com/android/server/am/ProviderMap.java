/*
 * Copyright (C) 2011 The Android Open Source Project
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

import android.content.ComponentName;
import android.os.Binder;
<<<<<<< HEAD
import android.os.Process;
import android.os.RemoteException;
import android.os.UserId;
=======
import android.os.RemoteException;
import android.os.UserHandle;
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
import android.util.Slog;
import android.util.SparseArray;

import java.io.FileDescriptor;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
<<<<<<< HEAD
import java.util.Map.Entry;
import java.util.Set;
=======
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a

/**
 * Keeps track of content providers by authority (name) and class. It separates the mapping by
 * user and ones that are not user-specific (system providers).
 */
public class ProviderMap {

    private static final String TAG = "ProviderMap";

    private static final boolean DBG = false;

<<<<<<< HEAD
    private final HashMap<String, ContentProviderRecord> mGlobalByName
            = new HashMap<String, ContentProviderRecord>();
    private final HashMap<ComponentName, ContentProviderRecord> mGlobalByClass
=======
    private final ActivityManagerService mAm;

    private final HashMap<String, ContentProviderRecord> mSingletonByName
            = new HashMap<String, ContentProviderRecord>();
    private final HashMap<ComponentName, ContentProviderRecord> mSingletonByClass
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
            = new HashMap<ComponentName, ContentProviderRecord>();

    private final SparseArray<HashMap<String, ContentProviderRecord>> mProvidersByNamePerUser
            = new SparseArray<HashMap<String, ContentProviderRecord>>();
    private final SparseArray<HashMap<ComponentName, ContentProviderRecord>> mProvidersByClassPerUser
            = new SparseArray<HashMap<ComponentName, ContentProviderRecord>>();

<<<<<<< HEAD
=======
    ProviderMap(ActivityManagerService am) {
        mAm = am;
    }

>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
    ContentProviderRecord getProviderByName(String name) {
        return getProviderByName(name, -1);
    }

    ContentProviderRecord getProviderByName(String name, int userId) {
        if (DBG) {
            Slog.i(TAG, "getProviderByName: " + name + " , callingUid = " + Binder.getCallingUid());
        }
        // Try to find it in the global list
<<<<<<< HEAD
        ContentProviderRecord record = mGlobalByName.get(name);
=======
        ContentProviderRecord record = mSingletonByName.get(name);
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
        if (record != null) {
            return record;
        }

        // Check the current user's list
        return getProvidersByName(userId).get(name);
    }

    ContentProviderRecord getProviderByClass(ComponentName name) {
        return getProviderByClass(name, -1);
    }

    ContentProviderRecord getProviderByClass(ComponentName name, int userId) {
        if (DBG) {
            Slog.i(TAG, "getProviderByClass: " + name + ", callingUid = " + Binder.getCallingUid());
        }
        // Try to find it in the global list
<<<<<<< HEAD
        ContentProviderRecord record = mGlobalByClass.get(name);
=======
        ContentProviderRecord record = mSingletonByClass.get(name);
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
        if (record != null) {
            return record;
        }

        // Check the current user's list
        return getProvidersByClass(userId).get(name);
    }

    void putProviderByName(String name, ContentProviderRecord record) {
        if (DBG) {
            Slog.i(TAG, "putProviderByName: " + name + " , callingUid = " + Binder.getCallingUid()
                + ", record uid = " + record.appInfo.uid);
        }
<<<<<<< HEAD
        if (record.appInfo.uid < Process.FIRST_APPLICATION_UID) {
            mGlobalByName.put(name, record);
        } else {
            final int userId = UserId.getUserId(record.appInfo.uid);
=======
        if (record.singleton) {
            mSingletonByName.put(name, record);
        } else {
            final int userId = UserHandle.getUserId(record.appInfo.uid);
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
            getProvidersByName(userId).put(name, record);
        }
    }

    void putProviderByClass(ComponentName name, ContentProviderRecord record) {
        if (DBG) {
            Slog.i(TAG, "putProviderByClass: " + name + " , callingUid = " + Binder.getCallingUid()
                + ", record uid = " + record.appInfo.uid);
        }
<<<<<<< HEAD
        if (record.appInfo.uid < Process.FIRST_APPLICATION_UID) {
            mGlobalByClass.put(name, record);
        } else {
            final int userId = UserId.getUserId(record.appInfo.uid);
=======
        if (record.singleton) {
            mSingletonByClass.put(name, record);
        } else {
            final int userId = UserHandle.getUserId(record.appInfo.uid);
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
            getProvidersByClass(userId).put(name, record);
        }
    }

<<<<<<< HEAD
    void removeProviderByName(String name, int optionalUserId) {
        if (mGlobalByName.containsKey(name)) {
            if (DBG)
                Slog.i(TAG, "Removing from globalByName name=" + name);
            mGlobalByName.remove(name);
        } else {
            // TODO: Verify this works, i.e., the caller happens to be from the correct user
            if (DBG)
                Slog.i(TAG,
                        "Removing from providersByName name=" + name + " user="
                        + (optionalUserId == -1 ? Binder.getOrigCallingUser() : optionalUserId));
            getProvidersByName(optionalUserId).remove(name);
        }
    }

    void removeProviderByClass(ComponentName name, int optionalUserId) {
        if (mGlobalByClass.containsKey(name)) {
            if (DBG)
                Slog.i(TAG, "Removing from globalByClass name=" + name);
            mGlobalByClass.remove(name);
        } else {
            if (DBG)
                Slog.i(TAG,
                        "Removing from providersByClass name=" + name + " user="
                        + (optionalUserId == -1 ? Binder.getOrigCallingUser() : optionalUserId));
            getProvidersByClass(optionalUserId).remove(name);
        }
    }

    private HashMap<String, ContentProviderRecord> getProvidersByName(int optionalUserId) {
        final int userId = optionalUserId >= 0
                ? optionalUserId : Binder.getOrigCallingUser();
=======
    void removeProviderByName(String name, int userId) {
        if (mSingletonByName.containsKey(name)) {
            if (DBG)
                Slog.i(TAG, "Removing from globalByName name=" + name);
            mSingletonByName.remove(name);
        } else {
            if (userId < 0) throw new IllegalArgumentException("Bad user " + userId);
            if (DBG)
                Slog.i(TAG,
                        "Removing from providersByName name=" + name + " user=" + userId);
            HashMap<String, ContentProviderRecord> map = getProvidersByName(userId);
            // map returned by getProvidersByName wouldn't be null
            map.remove(name);
            if (map.size() == 0) {
                mProvidersByNamePerUser.remove(userId);
            }
        }
    }

    void removeProviderByClass(ComponentName name, int userId) {
        if (mSingletonByClass.containsKey(name)) {
            if (DBG)
                Slog.i(TAG, "Removing from globalByClass name=" + name);
            mSingletonByClass.remove(name);
        } else {
            if (userId < 0) throw new IllegalArgumentException("Bad user " + userId);
            if (DBG)
                Slog.i(TAG,
                        "Removing from providersByClass name=" + name + " user=" + userId);
            HashMap<ComponentName, ContentProviderRecord> map = getProvidersByClass(userId);
            // map returned by getProvidersByClass wouldn't be null
            map.remove(name);
            if (map.size() == 0) {
                mProvidersByClassPerUser.remove(userId);
            }
        }
    }

    private HashMap<String, ContentProviderRecord> getProvidersByName(int userId) {
        if (userId < 0) throw new IllegalArgumentException("Bad user " + userId);
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
        final HashMap<String, ContentProviderRecord> map = mProvidersByNamePerUser.get(userId);
        if (map == null) {
            HashMap<String, ContentProviderRecord> newMap = new HashMap<String, ContentProviderRecord>();
            mProvidersByNamePerUser.put(userId, newMap);
            return newMap;
        } else {
            return map;
        }
    }

<<<<<<< HEAD
    HashMap<ComponentName, ContentProviderRecord> getProvidersByClass(int optionalUserId) {
        final int userId = optionalUserId >= 0
                ? optionalUserId : Binder.getOrigCallingUser();
        final HashMap<ComponentName, ContentProviderRecord> map = mProvidersByClassPerUser.get(userId);
        if (map == null) {
            HashMap<ComponentName, ContentProviderRecord> newMap = new HashMap<ComponentName, ContentProviderRecord>();
=======
    HashMap<ComponentName, ContentProviderRecord> getProvidersByClass(int userId) {
        if (userId < 0) throw new IllegalArgumentException("Bad user " + userId);
        final HashMap<ComponentName, ContentProviderRecord> map
                = mProvidersByClassPerUser.get(userId);
        if (map == null) {
            HashMap<ComponentName, ContentProviderRecord> newMap
                    = new HashMap<ComponentName, ContentProviderRecord>();
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
            mProvidersByClassPerUser.put(userId, newMap);
            return newMap;
        } else {
            return map;
        }
    }

<<<<<<< HEAD
=======
    private boolean collectForceStopProvidersLocked(String name, int appId,
            boolean doit, boolean evenPersistent, int userId,
            HashMap<ComponentName, ContentProviderRecord> providers,
            ArrayList<ContentProviderRecord> result) {
        boolean didSomething = false;
        for (ContentProviderRecord provider : providers.values()) {
            if ((name == null || provider.info.packageName.equals(name))
                    && (provider.proc == null || evenPersistent || !provider.proc.persistent)) {
                if (!doit) {
                    return true;
                }
                didSomething = true;
                result.add(provider);
            }
        }
        return didSomething;
    }

    boolean collectForceStopProviders(String name, int appId,
            boolean doit, boolean evenPersistent, int userId,
            ArrayList<ContentProviderRecord> result) {
        boolean didSomething = collectForceStopProvidersLocked(name, appId, doit,
                evenPersistent, userId, mSingletonByClass, result);
        if (!doit && didSomething) {
            return true;
        }
        if (userId == UserHandle.USER_ALL) {
            for (int i=0; i<mProvidersByClassPerUser.size(); i++) {
                if (collectForceStopProvidersLocked(name, appId, doit, evenPersistent,
                        userId, mProvidersByClassPerUser.valueAt(i), result)) {
                    if (!doit) {
                        return true;
                    }
                    didSomething = true;
                }
            }
        } else {
            HashMap<ComponentName, ContentProviderRecord> items
                    = getProvidersByClass(userId);
            if (items != null) {
                didSomething |= collectForceStopProvidersLocked(name, appId, doit,
                        evenPersistent, userId, items, result);
            }
        }
        return didSomething;
    }

>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
    private void dumpProvidersByClassLocked(PrintWriter pw, boolean dumpAll,
            HashMap<ComponentName, ContentProviderRecord> map) {
        Iterator<Map.Entry<ComponentName, ContentProviderRecord>> it = map.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<ComponentName, ContentProviderRecord> e = it.next();
            ContentProviderRecord r = e.getValue();
            pw.print("  * ");
            pw.println(r);
            r.dump(pw, "    ", dumpAll);
        }
    }

    private void dumpProvidersByNameLocked(PrintWriter pw,
            HashMap<String, ContentProviderRecord> map) {
        Iterator<Map.Entry<String, ContentProviderRecord>> it = map.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, ContentProviderRecord> e = it.next();
            ContentProviderRecord r = e.getValue();
            pw.print("  ");
            pw.print(e.getKey());
            pw.print(": ");
            pw.println(r.toShortString());
        }
    }

    void dumpProvidersLocked(PrintWriter pw, boolean dumpAll) {
<<<<<<< HEAD
        boolean needSep = false;
        if (mGlobalByClass.size() > 0) {
            if (needSep)
                pw.println(" ");
            pw.println("  Published content providers (by class):");
            dumpProvidersByClassLocked(pw, dumpAll, mGlobalByClass);
        }

        if (mProvidersByClassPerUser.size() > 1) {
            pw.println("");
            for (int i = 0; i < mProvidersByClassPerUser.size(); i++) {
                HashMap<ComponentName, ContentProviderRecord> map = mProvidersByClassPerUser.valueAt(i);
                pw.println("  User " + mProvidersByClassPerUser.keyAt(i) + ":");
                dumpProvidersByClassLocked(pw, dumpAll, map);
                pw.println(" ");
            }
        } else if (mProvidersByClassPerUser.size() == 1) {
            HashMap<ComponentName, ContentProviderRecord> map = mProvidersByClassPerUser.valueAt(0);
            dumpProvidersByClassLocked(pw, dumpAll, map);
        }
        needSep = true;

        if (dumpAll) {
            pw.println(" ");
            pw.println("  Authority to provider mappings:");
            dumpProvidersByNameLocked(pw, mGlobalByName);

            for (int i = 0; i < mProvidersByNamePerUser.size(); i++) {
                if (i > 0) {
                    pw.println("  User " + mProvidersByNamePerUser.keyAt(i) + ":");
                }
=======
        if (mSingletonByClass.size() > 0) {
            pw.println("  Published single-user content providers (by class):");
            dumpProvidersByClassLocked(pw, dumpAll, mSingletonByClass);
        }

        pw.println("");
        for (int i = 0; i < mProvidersByClassPerUser.size(); i++) {
            HashMap<ComponentName, ContentProviderRecord> map = mProvidersByClassPerUser.valueAt(i);
            pw.println("");
            pw.println("  Published user " + mProvidersByClassPerUser.keyAt(i)
                    + " content providers (by class):");
            dumpProvidersByClassLocked(pw, dumpAll, map);
        }

        if (dumpAll) {
            pw.println("");
            pw.println("  Single-user authority to provider mappings:");
            dumpProvidersByNameLocked(pw, mSingletonByName);

            for (int i = 0; i < mProvidersByNamePerUser.size(); i++) {
                pw.println("");
                pw.println("  User " + mProvidersByNamePerUser.keyAt(i)
                        + " authority to provider mappings:");
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
                dumpProvidersByNameLocked(pw, mProvidersByNamePerUser.valueAt(i));
            }
        }
    }

    protected boolean dumpProvider(FileDescriptor fd, PrintWriter pw, String name, String[] args,
            int opti, boolean dumpAll) {
<<<<<<< HEAD
        ArrayList<ContentProviderRecord> providers = new ArrayList<ContentProviderRecord>();

        if ("all".equals(name)) {
            synchronized (this) {
                for (ContentProviderRecord r1 : getProvidersByClass(-1).values()) {
                    providers.add(r1);
                }
            }
        } else {
            ComponentName componentName = name != null
                    ? ComponentName.unflattenFromString(name) : null;
            int objectId = 0;
            if (componentName == null) {
                // Not a '/' separated full component name; maybe an object ID?
                try {
                    objectId = Integer.parseInt(name, 16);
                    name = null;
                    componentName = null;
                } catch (RuntimeException e) {
                }
            }

            synchronized (this) {
                for (ContentProviderRecord r1 : getProvidersByClass(-1).values()) {
=======
        ArrayList<ContentProviderRecord> allProviders = new ArrayList<ContentProviderRecord>();
        ArrayList<ContentProviderRecord> providers = new ArrayList<ContentProviderRecord>();

        synchronized (mAm) {
            allProviders.addAll(mSingletonByClass.values());
            for (int i=0; i<mProvidersByClassPerUser.size(); i++) {
                allProviders.addAll(mProvidersByClassPerUser.valueAt(i).values());
            }

            if ("all".equals(name)) {
                providers.addAll(allProviders);
            } else {
                ComponentName componentName = name != null
                        ? ComponentName.unflattenFromString(name) : null;
                int objectId = 0;
                if (componentName == null) {
                    // Not a '/' separated full component name; maybe an object ID?
                    try {
                        objectId = Integer.parseInt(name, 16);
                        name = null;
                        componentName = null;
                    } catch (RuntimeException e) {
                    }
                }

                for (int i=0; i<allProviders.size(); i++) {
                    ContentProviderRecord r1 = allProviders.get(i);
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
                    if (componentName != null) {
                        if (r1.name.equals(componentName)) {
                            providers.add(r1);
                        }
                    } else if (name != null) {
                        if (r1.name.flattenToString().contains(name)) {
                            providers.add(r1);
                        }
                    } else if (System.identityHashCode(r1) == objectId) {
                        providers.add(r1);
                    }
                }
            }
        }

        if (providers.size() <= 0) {
            return false;
        }

        boolean needSep = false;
        for (int i=0; i<providers.size(); i++) {
            if (needSep) {
                pw.println();
            }
            needSep = true;
            dumpProvider("", fd, pw, providers.get(i), args, dumpAll);
        }
        return true;
    }

    /**
     * Invokes IApplicationThread.dumpProvider() on the thread of the specified provider if
     * there is a thread associated with the provider.
     */
    private void dumpProvider(String prefix, FileDescriptor fd, PrintWriter pw,
            final ContentProviderRecord r, String[] args, boolean dumpAll) {
        String innerPrefix = prefix + "  ";
<<<<<<< HEAD
        synchronized (this) {
=======
        synchronized (mAm) {
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
            pw.print(prefix); pw.print("PROVIDER ");
                    pw.print(r);
                    pw.print(" pid=");
                    if (r.proc != null) pw.println(r.proc.pid);
                    else pw.println("(not running)");
            if (dumpAll) {
                r.dump(pw, innerPrefix, true);
            }
        }
        if (r.proc != null && r.proc.thread != null) {
            pw.println("    Client:");
            pw.flush();
            try {
                TransferPipe tp = new TransferPipe();
                try {
                    r.proc.thread.dumpProvider(
                            tp.getWriteFd().getFileDescriptor(), r.provider.asBinder(), args);
                    tp.setBufferPrefix("      ");
                    // Short timeout, since blocking here can
                    // deadlock with the application.
                    tp.go(fd, 2000);
                } finally {
                    tp.kill();
                }
            } catch (IOException ex) {
                pw.println("      Failure while dumping the provider: " + ex);
            } catch (RemoteException ex) {
                pw.println("      Got a RemoteException while dumping the service");
            }
        }
    }
<<<<<<< HEAD


=======
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
}
