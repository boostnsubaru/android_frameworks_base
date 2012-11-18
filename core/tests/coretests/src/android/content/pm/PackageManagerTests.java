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

package android.content.pm;

<<<<<<< HEAD
=======
import static libcore.io.OsConstants.*;

>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
import com.android.frameworks.coretests.R;
import com.android.internal.content.PackageHelper;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
<<<<<<< HEAD
=======
import android.content.pm.PackageInfo;
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Resources;
import android.content.res.Resources.NotFoundException;
import android.net.Uri;
import android.os.Environment;
import android.os.FileUtils;
import android.os.IBinder;
<<<<<<< HEAD
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.StatFs;
=======
import android.os.Process;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.StatFs;
import android.os.SystemClock;
import android.os.UserManager;
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
import android.os.storage.IMountService;
import android.os.storage.StorageListener;
import android.os.storage.StorageManager;
import android.os.storage.StorageResultCode;
import android.provider.Settings;
import android.provider.Settings.SettingNotFoundException;
import android.test.AndroidTestCase;
import android.test.suitebuilder.annotation.LargeTest;
import android.test.suitebuilder.annotation.SmallTest;
import android.util.DisplayMetrics;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

<<<<<<< HEAD
public class PackageManagerTests extends AndroidTestCase {
    private static final boolean localLOGV = true;
    public static final String TAG="PackageManagerTests";
    public final long MAX_WAIT_TIME = 25*1000;
    public final long WAIT_TIME_INCR = 5*1000;
    private static final String SECURE_CONTAINERS_PREFIX = "/mnt/asec";
    private static final int APP_INSTALL_AUTO = PackageHelper.APP_INSTALL_AUTO;
    private static final int APP_INSTALL_DEVICE = PackageHelper.APP_INSTALL_INTERNAL;
    private static final int APP_INSTALL_SDCARD = PackageHelper.APP_INSTALL_EXTERNAL;
    private boolean mOrigState;

    void failStr(String errMsg) {
        Log.w(TAG, "errMsg="+errMsg);
        fail(errMsg);
    }
=======
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import libcore.io.ErrnoException;
import libcore.io.Libcore;
import libcore.io.StructStat;

public class PackageManagerTests extends AndroidTestCase {
    private static final boolean localLOGV = true;

    public static final String TAG = "PackageManagerTests";

    public final long MAX_WAIT_TIME = 25 * 1000;

    public final long WAIT_TIME_INCR = 5 * 1000;

    private static final String APP_LIB_DIR_PREFIX = "/data/app-lib/";

    private static final String SECURE_CONTAINERS_PREFIX = "/mnt/asec/";

    private static final int APP_INSTALL_AUTO = PackageHelper.APP_INSTALL_AUTO;

    private static final int APP_INSTALL_DEVICE = PackageHelper.APP_INSTALL_INTERNAL;

    private static final int APP_INSTALL_SDCARD = PackageHelper.APP_INSTALL_EXTERNAL;

    private boolean mOrigState;

    void failStr(String errMsg) {
        Log.w(TAG, "errMsg=" + errMsg);
        fail(errMsg);
    }

>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
    void failStr(Exception e) {
        failStr(e.getMessage());
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mOrigState = checkMediaState(Environment.MEDIA_MOUNTED);
        if (!mountMedia()) {
            Log.i(TAG, "sdcard not mounted? Some of these tests might fail");
        }
    }

    @Override
    protected void tearDown() throws Exception {
        // Restore media state.
        boolean newState = checkMediaState(Environment.MEDIA_MOUNTED);
        if (newState != mOrigState) {
            if (mOrigState) {
                mountMedia();
            } else {
                unmountMedia();
            }
        }
        super.tearDown();
    }

    private class PackageInstallObserver extends IPackageInstallObserver.Stub {
        public int returnCode;
<<<<<<< HEAD
        private boolean doneFlag = false;

        public void packageInstalled(String packageName, int returnCode) {
            synchronized(this) {
=======

        private boolean doneFlag = false;

        public void packageInstalled(String packageName, int returnCode) {
            synchronized (this) {
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
                this.returnCode = returnCode;
                doneFlag = true;
                notifyAll();
            }
        }

        public boolean isDone() {
            return doneFlag;
        }
    }

    abstract class GenericReceiver extends BroadcastReceiver {
        private boolean doneFlag = false;
<<<<<<< HEAD
        boolean received = false;
        Intent intent;
        IntentFilter filter;
        abstract boolean notifyNow(Intent intent);
=======

        boolean received = false;

        Intent intent;

        IntentFilter filter;

        abstract boolean notifyNow(Intent intent);

>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
        @Override
        public void onReceive(Context context, Intent intent) {
            if (notifyNow(intent)) {
                synchronized (this) {
                    received = true;
                    doneFlag = true;
                    this.intent = intent;
                    notifyAll();
                }
            }
        }

        public boolean isDone() {
            return doneFlag;
        }

        public void setFilter(IntentFilter filter) {
            this.filter = filter;
        }
    }

    class InstallReceiver extends GenericReceiver {
        String pkgName;

        InstallReceiver(String pkgName) {
            this.pkgName = pkgName;
            IntentFilter filter = new IntentFilter(Intent.ACTION_PACKAGE_ADDED);
            filter.addDataScheme("package");
            super.setFilter(filter);
        }

        public boolean notifyNow(Intent intent) {
            String action = intent.getAction();
            if (!Intent.ACTION_PACKAGE_ADDED.equals(action)) {
                return false;
            }
            Uri data = intent.getData();
            String installedPkg = data.getEncodedSchemeSpecificPart();
            if (pkgName.equals(installedPkg)) {
                return true;
            }
            return false;
        }
    }

    private PackageManager getPm() {
        return mContext.getPackageManager();
    }

    private IPackageManager getIPm() {
        IPackageManager ipm  = IPackageManager.Stub.asInterface(
                ServiceManager.getService("package"));
        return ipm;
    }

    public void invokeInstallPackage(Uri packageURI, int flags, GenericReceiver receiver,
            boolean shouldSucceed) {
        PackageInstallObserver observer = new PackageInstallObserver();
        mContext.registerReceiver(receiver, receiver.filter);
        try {
            // Wait on observer
<<<<<<< HEAD
            synchronized(observer) {
                synchronized (receiver) {
                    getPm().installPackage(packageURI, observer, flags, null);
                    long waitTime = 0;
                    while((!observer.isDone()) && (waitTime < MAX_WAIT_TIME) ) {
=======
            synchronized (observer) {
                synchronized (receiver) {
                    getPm().installPackage(packageURI, observer, flags, null);
                    long waitTime = 0;
                    while ((!observer.isDone()) && (waitTime < MAX_WAIT_TIME)) {
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
                        try {
                            observer.wait(WAIT_TIME_INCR);
                            waitTime += WAIT_TIME_INCR;
                        } catch (InterruptedException e) {
                            Log.i(TAG, "Interrupted during sleep", e);
                        }
                    }
<<<<<<< HEAD
                    if(!observer.isDone()) {
=======
                    if (!observer.isDone()) {
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
                        fail("Timed out waiting for packageInstalled callback");
                    }

                    if (shouldSucceed) {
                        if (observer.returnCode != PackageManager.INSTALL_SUCCEEDED) {
                            fail("Package installation should have succeeded, but got code "
                                    + observer.returnCode);
                        }
                    } else {
                        if (observer.returnCode == PackageManager.INSTALL_SUCCEEDED) {
                            fail("Package installation should fail");
                        }

                        /*
                         * We'll never expect get a notification since we
                         * shouldn't succeed.
                         */
                        return;
                    }

                    // Verify we received the broadcast
                    waitTime = 0;
<<<<<<< HEAD
                    while((!receiver.isDone()) && (waitTime < MAX_WAIT_TIME) ) {
=======
                    while ((!receiver.isDone()) && (waitTime < MAX_WAIT_TIME)) {
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
                        try {
                            receiver.wait(WAIT_TIME_INCR);
                            waitTime += WAIT_TIME_INCR;
                        } catch (InterruptedException e) {
                            Log.i(TAG, "Interrupted during sleep", e);
                        }
                    }
<<<<<<< HEAD
                    if(!receiver.isDone()) {
=======
                    if (!receiver.isDone()) {
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
                        fail("Timed out waiting for PACKAGE_ADDED notification");
                    }
                }
            }
        } finally {
            mContext.unregisterReceiver(receiver);
        }
    }

    public void invokeInstallPackageFail(Uri packageURI, int flags, int expectedResult) {
        PackageInstallObserver observer = new PackageInstallObserver();
        try {
            // Wait on observer
<<<<<<< HEAD
            synchronized(observer) {
                getPm().installPackage(packageURI, observer, flags, null);
                long waitTime = 0;
                while((!observer.isDone()) && (waitTime < MAX_WAIT_TIME) ) {
=======
            synchronized (observer) {
                getPm().installPackage(packageURI, observer, flags, null);
                long waitTime = 0;
                while ((!observer.isDone()) && (waitTime < MAX_WAIT_TIME)) {
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
                    try {
                        observer.wait(WAIT_TIME_INCR);
                        waitTime += WAIT_TIME_INCR;
                    } catch (InterruptedException e) {
                        Log.i(TAG, "Interrupted during sleep", e);
                    }
                }
<<<<<<< HEAD
                if(!observer.isDone()) {
=======
                if (!observer.isDone()) {
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
                    fail("Timed out waiting for packageInstalled callback");
                }
                assertEquals(expectedResult, observer.returnCode);
            }
        } finally {
        }
    }

    Uri getInstallablePackage(int fileResId, File outFile) {
        Resources res = mContext.getResources();
        InputStream is = null;
        try {
            is = res.openRawResource(fileResId);
        } catch (NotFoundException e) {
            failStr("Failed to load resource with id: " + fileResId);
        }
        FileUtils.setPermissions(outFile.getPath(),
                FileUtils.S_IRWXU | FileUtils.S_IRWXG | FileUtils.S_IRWXO,
                -1, -1);
        assertTrue(FileUtils.copyToFile(is, outFile));
        FileUtils.setPermissions(outFile.getPath(),
                FileUtils.S_IRWXU | FileUtils.S_IRWXG | FileUtils.S_IRWXO,
                -1, -1);
        return Uri.fromFile(outFile);
    }

    private PackageParser.Package parsePackage(Uri packageURI) {
        final String archiveFilePath = packageURI.getPath();
        PackageParser packageParser = new PackageParser(archiveFilePath);
        File sourceFile = new File(archiveFilePath);
        DisplayMetrics metrics = new DisplayMetrics();
        metrics.setToDefaults();
<<<<<<< HEAD
        PackageParser.Package pkg = packageParser.parsePackage(sourceFile, archiveFilePath, metrics, 0);
        packageParser = null;
        return pkg;
    }
=======
        PackageParser.Package pkg = packageParser.parsePackage(sourceFile, archiveFilePath,
                metrics, 0);
        packageParser = null;
        return pkg;
    }

>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
    private boolean checkSd(long pkgLen) {
        String status = Environment.getExternalStorageState();
        if (!status.equals(Environment.MEDIA_MOUNTED)) {
            return false;
        }
        long sdSize = -1;
<<<<<<< HEAD
        StatFs sdStats = new StatFs(
                Environment.getExternalStorageDirectory().getPath());
        sdSize = (long)sdStats.getAvailableBlocks() *
                (long)sdStats.getBlockSize();
=======
        StatFs sdStats = new StatFs(Environment.getExternalStorageDirectory().getPath());
        sdSize = (long) sdStats.getAvailableBlocks() * (long) sdStats.getBlockSize();
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
        // TODO check for thresholds here
        return pkgLen <= sdSize;

    }
<<<<<<< HEAD
    private boolean checkInt(long pkgLen) {
        StatFs intStats = new StatFs(Environment.getDataDirectory().getPath());
        long intSize = (long)intStats.getBlockCount() *
                (long)intStats.getBlockSize();
        long iSize = (long)intStats.getAvailableBlocks() *
                (long)intStats.getBlockSize();
        // TODO check for thresholds here?
        return pkgLen <= iSize;
    }
    private static final int INSTALL_LOC_INT = 1;
    private static final int INSTALL_LOC_SD = 2;
    private static final int INSTALL_LOC_ERR = -1;
    private int getInstallLoc(int flags, int expInstallLocation, long pkgLen) {
        // Flags explicitly over ride everything else.
        if ((flags & PackageManager.INSTALL_EXTERNAL) != 0 ) {
=======

    private boolean checkInt(long pkgLen) {
        StatFs intStats = new StatFs(Environment.getDataDirectory().getPath());
        long intSize = (long) intStats.getBlockCount() * (long) intStats.getBlockSize();
        long iSize = (long) intStats.getAvailableBlocks() * (long) intStats.getBlockSize();
        // TODO check for thresholds here?
        return pkgLen <= iSize;
    }

    private static final int INSTALL_LOC_INT = 1;

    private static final int INSTALL_LOC_SD = 2;

    private static final int INSTALL_LOC_ERR = -1;

    private int getInstallLoc(int flags, int expInstallLocation, long pkgLen) {
        // Flags explicitly over ride everything else.
        if ((flags & PackageManager.INSTALL_EXTERNAL) != 0) {
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
            return INSTALL_LOC_SD;
        } else if ((flags & PackageManager.INSTALL_INTERNAL) != 0) {
            return INSTALL_LOC_INT;
        }
        // Manifest option takes precedence next
        if (expInstallLocation == PackageInfo.INSTALL_LOCATION_PREFER_EXTERNAL) {
            if (checkSd(pkgLen)) {
<<<<<<< HEAD
               return INSTALL_LOC_SD;
=======
                return INSTALL_LOC_SD;
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
            }
            if (checkInt(pkgLen)) {
                return INSTALL_LOC_INT;
            }
            return INSTALL_LOC_ERR;
        }
        if (expInstallLocation == PackageInfo.INSTALL_LOCATION_INTERNAL_ONLY) {
            if (checkInt(pkgLen)) {
                return INSTALL_LOC_INT;
            }
            return INSTALL_LOC_ERR;
        }
        if (expInstallLocation == PackageInfo.INSTALL_LOCATION_AUTO) {
            // Check for free memory internally
            if (checkInt(pkgLen)) {
                return INSTALL_LOC_INT;
            }
            // Check for free memory externally
            if (checkSd(pkgLen)) {
                return INSTALL_LOC_SD;
            }
            return INSTALL_LOC_ERR;
        }
        // Check for settings preference.
        boolean checkSd = false;
        int userPref = getDefaultInstallLoc();
        if (userPref == APP_INSTALL_DEVICE) {
            if (checkInt(pkgLen)) {
                return INSTALL_LOC_INT;
            }
            return INSTALL_LOC_ERR;
        } else if (userPref == APP_INSTALL_SDCARD) {
            if (checkSd(pkgLen)) {
                return INSTALL_LOC_SD;
            }
            return INSTALL_LOC_ERR;
        }
        // Default system policy for apps with no manifest option specified.
        // Check for free memory internally
        if (checkInt(pkgLen)) {
            return INSTALL_LOC_INT;
        }
        return INSTALL_LOC_ERR;
    }

    private void assertInstall(PackageParser.Package pkg, int flags, int expInstallLocation) {
        try {
            String pkgName = pkg.packageName;
            ApplicationInfo info = getPm().getApplicationInfo(pkgName, 0);
            assertNotNull(info);
            assertEquals(pkgName, info.packageName);
            File dataDir = Environment.getDataDirectory();
            String appInstallPath = new File(dataDir, "app").getPath();
            String drmInstallPath = new File(dataDir, "app-private").getPath();
            File srcDir = new File(info.sourceDir);
            String srcPath = srcDir.getParent();
            File publicSrcDir = new File(info.publicSourceDir);
            String publicSrcPath = publicSrcDir.getParent();
            long pkgLen = new File(info.sourceDir).length();

            int rLoc = getInstallLoc(flags, expInstallLocation, pkgLen);
            if (rLoc == INSTALL_LOC_INT) {
                if ((flags & PackageManager.INSTALL_FORWARD_LOCK) != 0) {
                    assertTrue("The application should be installed forward locked",
                            (info.flags & ApplicationInfo.FLAG_FORWARD_LOCK) != 0);
<<<<<<< HEAD
                    assertTrue("The APK path (" + srcPath + ") should start with "
                            + SECURE_CONTAINERS_PREFIX,
                            srcPath.startsWith(SECURE_CONTAINERS_PREFIX));
                    assertTrue("The public APK path (" + publicSrcPath + ") should start with "
                            + SECURE_CONTAINERS_PREFIX,
                            publicSrcPath.startsWith(SECURE_CONTAINERS_PREFIX));
                    assertTrue("The native library path (" + info.nativeLibraryDir
                            + ") should start with " + SECURE_CONTAINERS_PREFIX,
                            info.nativeLibraryDir.startsWith(SECURE_CONTAINERS_PREFIX));
=======
                    assertStartsWith("The APK path should point to the ASEC",
                            SECURE_CONTAINERS_PREFIX, srcPath);
                    assertStartsWith("The public APK path should point to the ASEC",
                            SECURE_CONTAINERS_PREFIX, publicSrcPath);
                    assertStartsWith("The native library path should point to the ASEC",
                            SECURE_CONTAINERS_PREFIX, info.nativeLibraryDir);
                    try {
                        String compatLib = new File(info.dataDir + "/lib").getCanonicalPath();
                        assertEquals("The compatibility lib directory should be a symbolic link to "
                                + info.nativeLibraryDir,
                                info.nativeLibraryDir, compatLib);
                    } catch (IOException e) {
                        fail("compat check: Can't read " + info.dataDir + "/lib");
                    }
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
                } else {
                    assertFalse((info.flags & ApplicationInfo.FLAG_FORWARD_LOCK) != 0);
                    assertEquals(srcPath, appInstallPath);
                    assertEquals(publicSrcPath, appInstallPath);
<<<<<<< HEAD
                    assertTrue(info.nativeLibraryDir.startsWith(dataDir.getPath()));
=======
                    assertStartsWith("Native library should point to shared lib directory",
                            new File(APP_LIB_DIR_PREFIX, info.packageName).getPath(),
                            info.nativeLibraryDir);
                    assertDirOwnerGroupPerms(
                            "Native library directory should be owned by system:system and 0755",
                            Process.SYSTEM_UID, Process.SYSTEM_UID,
                            S_IRWXU | S_IRGRP | S_IXGRP | S_IROTH | S_IXOTH,
                            info.nativeLibraryDir);
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
                }
                assertFalse((info.flags & ApplicationInfo.FLAG_EXTERNAL_STORAGE) != 0);

                // Make sure the native library dir is not a symlink
                final File nativeLibDir = new File(info.nativeLibraryDir);
                assertTrue("Native library dir should exist at " + info.nativeLibraryDir,
                        nativeLibDir.exists());
                try {
                    assertEquals("Native library dir should not be a symlink",
<<<<<<< HEAD
                            info.nativeLibraryDir,
                            nativeLibDir.getCanonicalPath());
                } catch (IOException e) {
                    fail("Can't read " + nativeLibDir.getPath());
                }
            } else if (rLoc == INSTALL_LOC_SD){
=======
                            info.nativeLibraryDir, nativeLibDir.getCanonicalPath());
                } catch (IOException e) {
                    fail("Can't read " + nativeLibDir.getPath());
                }
            } else if (rLoc == INSTALL_LOC_SD) {
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
                if ((flags & PackageManager.INSTALL_FORWARD_LOCK) != 0) {
                    assertTrue("The application should be installed forward locked",
                            (info.flags & ApplicationInfo.FLAG_FORWARD_LOCK) != 0);
                } else {
                    assertFalse("The application should not be installed forward locked",
                            (info.flags & ApplicationInfo.FLAG_FORWARD_LOCK) != 0);
                }
                assertTrue("Application flags (" + info.flags
                        + ") should contain FLAG_EXTERNAL_STORAGE",
                        (info.flags & ApplicationInfo.FLAG_EXTERNAL_STORAGE) != 0);
                // Might need to check:
                // ((info.flags & ApplicationInfo.FLAG_FORWARD_LOCK) != 0)
<<<<<<< HEAD
                assertTrue("The APK path (" + srcPath + ") should start with "
                        + SECURE_CONTAINERS_PREFIX, srcPath.startsWith(SECURE_CONTAINERS_PREFIX));
                assertTrue("The public APK path (" + publicSrcPath + ") should start with "
                        + SECURE_CONTAINERS_PREFIX,
                        publicSrcPath.startsWith(SECURE_CONTAINERS_PREFIX));
                assertTrue("The native library path (" + info.nativeLibraryDir
                        + ") should start with " + SECURE_CONTAINERS_PREFIX,
                        info.nativeLibraryDir.startsWith(SECURE_CONTAINERS_PREFIX));
=======
                assertStartsWith("The APK path should point to the ASEC",
                        SECURE_CONTAINERS_PREFIX, srcPath);
                assertStartsWith("The public APK path should point to the ASEC",
                        SECURE_CONTAINERS_PREFIX, publicSrcPath);
                assertStartsWith("The native library path should point to the ASEC",
                        SECURE_CONTAINERS_PREFIX, info.nativeLibraryDir);
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a

                // Make sure the native library in /data/data/<app>/lib is a
                // symlink to the ASEC
                final File nativeLibSymLink = new File(info.dataDir, "lib");
                assertTrue("Native library symlink should exist at " + nativeLibSymLink.getPath(),
                        nativeLibSymLink.exists());
                try {
                    assertEquals(nativeLibSymLink.getPath() + " should be a symlink to "
<<<<<<< HEAD
                            + info.nativeLibraryDir, info.nativeLibraryDir, nativeLibSymLink
                            .getCanonicalPath());
=======
                            + info.nativeLibraryDir, info.nativeLibraryDir,
                            nativeLibSymLink.getCanonicalPath());
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
                } catch (IOException e) {
                    fail("Can't read " + nativeLibSymLink.getPath());
                }
            } else {
                // TODO handle error. Install should have failed.
                fail("Install should have failed");
            }
        } catch (NameNotFoundException e) {
            failStr("failed with exception : " + e);
        }
    }

<<<<<<< HEAD
=======
    private void assertDirOwnerGroupPerms(String reason, int uid, int gid, int perms, String path) {
        final StructStat stat;

        try {
            stat = Libcore.os.lstat(path);
        } catch (ErrnoException e) {
            throw new AssertionError(reason + "\n" + "Got: " + path + " does not exist");
        }

        StringBuilder sb = new StringBuilder();

        if (!S_ISDIR(stat.st_mode)) {
            sb.append("\nExpected type: ");
            sb.append(S_IFDIR);
            sb.append("\ngot type: ");
            sb.append((stat.st_mode & S_IFMT));
        }

        if (stat.st_uid != uid) {
            sb.append("\nExpected owner: ");
            sb.append(uid);
            sb.append("\nGot owner: ");
            sb.append(stat.st_uid);
        }

        if (stat.st_gid != gid) {
            sb.append("\nExpected group: ");
            sb.append(gid);
            sb.append("\nGot group: ");
            sb.append(stat.st_gid);
        }

        if ((stat.st_mode & ~S_IFMT) != perms) {
            sb.append("\nExpected permissions: ");
            sb.append(Integer.toOctalString(perms));
            sb.append("\nGot permissions: ");
            sb.append(Integer.toOctalString(stat.st_mode & ~S_IFMT));
        }

        if (sb.length() > 0) {
            throw new AssertionError(reason + sb.toString());
        }
    }

    private static void assertStartsWith(String prefix, String actual) {
        assertStartsWith("", prefix, actual);
    }

    private static void assertStartsWith(String description, String prefix, String actual) {
        if (!actual.startsWith(prefix)) {
            StringBuilder sb = new StringBuilder(description);
            sb.append("\nExpected prefix: ");
            sb.append(prefix);
            sb.append("\n     got: ");
            sb.append(actual);
            sb.append('\n');
            throw new AssertionError(sb.toString());
        }
    }

>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
    private void assertNotInstalled(String pkgName) {
        try {
            ApplicationInfo info = getPm().getApplicationInfo(pkgName, 0);
            fail(pkgName + " shouldnt be installed");
        } catch (NameNotFoundException e) {
        }
<<<<<<< HEAD
=======

        UserManager um = (UserManager) mContext.getSystemService(Context.USER_SERVICE);
        List<UserInfo> users = um.getUsers();
        for (UserInfo user : users) {
            String dataDir = PackageManager.getDataDirForUser(user.id, pkgName);
            assertFalse("Application data directory should not exist: " + dataDir,
                    new File(dataDir).exists());
        }
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
    }

    class InstallParams {
        Uri packageURI;
<<<<<<< HEAD
        PackageParser.Package pkg;
=======

        PackageParser.Package pkg;

>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
        InstallParams(String outFileName, int rawResId) {
            this.pkg = getParsedPackage(outFileName, rawResId);
            this.packageURI = Uri.fromFile(new File(pkg.mScanPath));
        }
<<<<<<< HEAD
=======

>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
        InstallParams(PackageParser.Package pkg) {
            this.packageURI = Uri.fromFile(new File(pkg.mScanPath));
            this.pkg = pkg;
        }
<<<<<<< HEAD
=======

>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
        long getApkSize() {
            File file = new File(pkg.mScanPath);
            return file.length();
        }
    }

<<<<<<< HEAD
    private InstallParams sampleInstallFromRawResource(int flags, boolean cleanUp) {
        return installFromRawResource("install.apk", R.raw.install, flags, cleanUp,
                false, -1, PackageInfo.INSTALL_LOCATION_UNSPECIFIED);
    }

    static final String PERM_PACKAGE = "package";
    static final String PERM_DEFINED = "defined";
    static final String PERM_UNDEFINED = "undefined";
    static final String PERM_USED = "used";
=======
    private InstallParams sampleInstallFromRawResource(int flags, boolean cleanUp) throws Exception {
        return installFromRawResource("install.apk", R.raw.install, flags, cleanUp, false, -1,
                PackageInfo.INSTALL_LOCATION_UNSPECIFIED);
    }

    static final String PERM_PACKAGE = "package";

    static final String PERM_DEFINED = "defined";

    static final String PERM_UNDEFINED = "undefined";

    static final String PERM_USED = "used";

>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
    static final String PERM_NOTUSED = "notused";

    private void assertPermissions(String[] cmds) {
        final PackageManager pm = getPm();
        String pkg = null;
        PackageInfo pkgInfo = null;
        String mode = PERM_DEFINED;
        int i = 0;
        while (i < cmds.length) {
            String cmd = cmds[i++];
            if (cmd == PERM_PACKAGE) {
                pkg = cmds[i++];
                try {
                    pkgInfo = pm.getPackageInfo(pkg,
                            PackageManager.GET_PERMISSIONS
                            | PackageManager.GET_UNINSTALLED_PACKAGES);
                } catch (NameNotFoundException e) {
                    pkgInfo = null;
                }
            } else if (cmd == PERM_DEFINED || cmd == PERM_UNDEFINED
                    || cmd == PERM_USED || cmd == PERM_NOTUSED) {
                mode = cmds[i++];
            } else {
                if (mode == PERM_DEFINED) {
                    try {
                        PermissionInfo pi = pm.getPermissionInfo(cmd, 0);
                        assertNotNull(pi);
                        assertEquals(pi.packageName, pkg);
                        assertEquals(pi.name, cmd);
                        assertNotNull(pkgInfo);
                        boolean found = false;
<<<<<<< HEAD
                        for (int j=0; j<pkgInfo.permissions.length && !found; j++) {
=======
                        for (int j = 0; j < pkgInfo.permissions.length && !found; j++) {
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
                            if (pkgInfo.permissions[j].name.equals(cmd)) {
                                found = true;
                            }
                        }
                        if (!found) {
                            fail("Permission not found: " + cmd);
                        }
                    } catch (NameNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                } else if (mode == PERM_UNDEFINED) {
                    try {
                        pm.getPermissionInfo(cmd, 0);
                        throw new RuntimeException("Permission exists: " + cmd);
                    } catch (NameNotFoundException e) {
                    }
                    if (pkgInfo != null) {
                        boolean found = false;
<<<<<<< HEAD
                        for (int j=0; j<pkgInfo.permissions.length && !found; j++) {
=======
                        for (int j = 0; j < pkgInfo.permissions.length && !found; j++) {
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
                            if (pkgInfo.permissions[j].name.equals(cmd)) {
                                found = true;
                            }
                        }
                        if (found) {
                            fail("Permission still exists: " + cmd);
                        }
                    }
                } else if (mode == PERM_USED || mode == PERM_NOTUSED) {
                    boolean found = false;
<<<<<<< HEAD
                    for (int j=0; j<pkgInfo.requestedPermissions.length && !found; j++) {
=======
                    for (int j = 0; j < pkgInfo.requestedPermissions.length && !found; j++) {
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
                        if (pkgInfo.requestedPermissions[j].equals(cmd)) {
                            found = true;
                        }
                    }
                    if (!found) {
                        fail("Permission not requested: " + cmd);
                    }
                    if (mode == PERM_USED) {
<<<<<<< HEAD
                        if (pm.checkPermission(cmd, pkg)
                                != PackageManager.PERMISSION_GRANTED) {
                            fail("Permission not granted: " + cmd);
                        }
                    } else {
                        if (pm.checkPermission(cmd, pkg)
                                != PackageManager.PERMISSION_DENIED) {
=======
                        if (pm.checkPermission(cmd, pkg) != PackageManager.PERMISSION_GRANTED) {
                            fail("Permission not granted: " + cmd);
                        }
                    } else {
                        if (pm.checkPermission(cmd, pkg) != PackageManager.PERMISSION_DENIED) {
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
                            fail("Permission granted: " + cmd);
                        }
                    }
                }
            }
        }
    }

    private PackageParser.Package getParsedPackage(String outFileName, int rawResId) {
        PackageManager pm = mContext.getPackageManager();
        File filesDir = mContext.getFilesDir();
        File outFile = new File(filesDir, outFileName);
        Uri packageURI = getInstallablePackage(rawResId, outFile);
        PackageParser.Package pkg = parsePackage(packageURI);
        return pkg;
    }

    /*
     * Utility function that reads a apk bundled as a raw resource
     * copies it into own data directory and invokes
     * PackageManager api to install it.
     */
<<<<<<< HEAD
    private void installFromRawResource(InstallParams ip,
            int flags, boolean cleanUp, boolean fail, int result,
            int expInstallLocation) {
=======
    private void installFromRawResource(InstallParams ip, int flags, boolean cleanUp, boolean fail,
            int result, int expInstallLocation) throws Exception {
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
        PackageManager pm = mContext.getPackageManager();
        PackageParser.Package pkg = ip.pkg;
        Uri packageURI = ip.packageURI;
        if ((flags & PackageManager.INSTALL_REPLACE_EXISTING) == 0) {
            // Make sure the package doesn't exist
            try {
                ApplicationInfo appInfo = pm.getApplicationInfo(pkg.packageName,
                        PackageManager.GET_UNINSTALLED_PACKAGES);
                GenericReceiver receiver = new DeleteReceiver(pkg.packageName);
                invokeDeletePackage(pkg.packageName, 0, receiver);
<<<<<<< HEAD
            } catch (NameNotFoundException e1) {
            } catch (Exception e) {
                failStr(e);
=======
            } catch (NameNotFoundException e) {
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
            }
        }
        try {
            if (fail) {
                invokeInstallPackageFail(packageURI, flags, result);
                if ((flags & PackageManager.INSTALL_REPLACE_EXISTING) == 0) {
                    assertNotInstalled(pkg.packageName);
                }
            } else {
                InstallReceiver receiver = new InstallReceiver(pkg.packageName);
                invokeInstallPackage(packageURI, flags, receiver, true);
                // Verify installed information
                assertInstall(pkg, flags, expInstallLocation);
            }
        } finally {
            if (cleanUp) {
                cleanUpInstall(ip);
            }
        }
    }

    /*
     * Utility function that reads a apk bundled as a raw resource
     * copies it into own data directory and invokes
     * PackageManager api to install it.
     */
<<<<<<< HEAD
    private InstallParams installFromRawResource(String outFileName,
            int rawResId, int flags, boolean cleanUp, boolean fail, int result,
            int expInstallLocation) {
=======
    private InstallParams installFromRawResource(String outFileName, int rawResId, int flags,
            boolean cleanUp, boolean fail, int result, int expInstallLocation) throws Exception {
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
        InstallParams ip = new InstallParams(outFileName, rawResId);
        installFromRawResource(ip, flags, cleanUp, fail, result, expInstallLocation);
        return ip;
    }

    @LargeTest
<<<<<<< HEAD
    public void testInstallNormalInternal() {
=======
    public void testInstallNormalInternal() throws Exception {
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
        sampleInstallFromRawResource(0, true);
    }

    @LargeTest
<<<<<<< HEAD
    public void testInstallFwdLockedInternal() {
=======
    public void testInstallFwdLockedInternal() throws Exception {
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
        sampleInstallFromRawResource(PackageManager.INSTALL_FORWARD_LOCK, true);
    }

    @LargeTest
<<<<<<< HEAD
    public void testInstallSdcard() {
=======
    public void testInstallSdcard() throws Exception {
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
        // Do not run on devices with emulated external storage.
        if (Environment.isExternalStorageEmulated()) {
            return;
        }

        mountMedia();
        sampleInstallFromRawResource(PackageManager.INSTALL_EXTERNAL, true);
    }

<<<<<<< HEAD
    /* ------------------------- Test replacing packages --------------*/
    class ReplaceReceiver extends GenericReceiver {
        String pkgName;
        final static int INVALID = -1;
        final static int REMOVED = 1;
        final static int ADDED = 2;
        final static int REPLACED = 3;
        int removed = INVALID;
=======
    /* ------------------------- Test replacing packages -------------- */
    class ReplaceReceiver extends GenericReceiver {
        String pkgName;

        final static int INVALID = -1;

        final static int REMOVED = 1;

        final static int ADDED = 2;

        final static int REPLACED = 3;

        int removed = INVALID;

>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
        // for updated system apps only
        boolean update = false;

        ReplaceReceiver(String pkgName) {
            this.pkgName = pkgName;
            filter = new IntentFilter(Intent.ACTION_PACKAGE_REMOVED);
            filter.addAction(Intent.ACTION_PACKAGE_ADDED);
            if (update) {
                filter.addAction(Intent.ACTION_PACKAGE_REPLACED);
            }
            filter.addDataScheme("package");
            super.setFilter(filter);
        }

        public boolean notifyNow(Intent intent) {
            String action = intent.getAction();
            Uri data = intent.getData();
            String installedPkg = data.getEncodedSchemeSpecificPart();
            if (pkgName == null || !pkgName.equals(installedPkg)) {
                return false;
            }
            if (Intent.ACTION_PACKAGE_REMOVED.equals(action)) {
                removed = REMOVED;
            } else if (Intent.ACTION_PACKAGE_ADDED.equals(action)) {
                if (removed != REMOVED) {
                    return false;
                }
                boolean replacing = intent.getBooleanExtra(Intent.EXTRA_REPLACING, false);
                if (!replacing) {
                    return false;
                }
                removed = ADDED;
                if (!update) {
                    return true;
                }
            } else if (Intent.ACTION_PACKAGE_REPLACED.equals(action)) {
                if (removed != ADDED) {
                    return false;
                }
                removed = REPLACED;
                return true;
            }
            return false;
        }
    }

    /*
     * Utility function that reads a apk bundled as a raw resource
     * copies it into own data directory and invokes
     * PackageManager api to install first and then replace it
     * again.
     */
<<<<<<< HEAD
    private void sampleReplaceFromRawResource(int flags) {
=======
    private void sampleReplaceFromRawResource(int flags) throws Exception {
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
        InstallParams ip = sampleInstallFromRawResource(flags, false);
        boolean replace = ((flags & PackageManager.INSTALL_REPLACE_EXISTING) != 0);
        Log.i(TAG, "replace=" + replace);
        GenericReceiver receiver;
        if (replace) {
            receiver = new ReplaceReceiver(ip.pkg.packageName);
            Log.i(TAG, "Creating replaceReceiver");
        } else {
            receiver = new InstallReceiver(ip.pkg.packageName);
        }
        try {
            invokeInstallPackage(ip.packageURI, flags, receiver, replace);
            if (replace) {
                assertInstall(ip.pkg, flags, ip.pkg.installLocation);
            }
        } finally {
            cleanUpInstall(ip);
        }
    }

    @LargeTest
<<<<<<< HEAD
    public void testReplaceFailNormalInternal() {
=======
    public void testReplaceFailNormalInternal() throws Exception {
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
        sampleReplaceFromRawResource(0);
    }

    @LargeTest
<<<<<<< HEAD
    public void testReplaceFailFwdLockedInternal() {
=======
    public void testReplaceFailFwdLockedInternal() throws Exception {
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
        sampleReplaceFromRawResource(PackageManager.INSTALL_FORWARD_LOCK);
    }

    @LargeTest
<<<<<<< HEAD
    public void testReplaceFailSdcard() {
=======
    public void testReplaceFailSdcard() throws Exception {
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
        // Do not run on devices with emulated external storage.
        if (Environment.isExternalStorageEmulated()) {
            return;
        }

        sampleReplaceFromRawResource(PackageManager.INSTALL_EXTERNAL);
    }

    @LargeTest
<<<<<<< HEAD
    public void testReplaceNormalInternal() {
=======
    public void testReplaceNormalInternal() throws Exception {
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
        sampleReplaceFromRawResource(PackageManager.INSTALL_REPLACE_EXISTING);
    }

    @LargeTest
<<<<<<< HEAD
    public void testReplaceFwdLockedInternal() {
        sampleReplaceFromRawResource(PackageManager.INSTALL_REPLACE_EXISTING |
                PackageManager.INSTALL_FORWARD_LOCK);
    }

    @LargeTest
    public void testReplaceSdcard() {
=======
    public void testReplaceFwdLockedInternal() throws Exception {
        sampleReplaceFromRawResource(PackageManager.INSTALL_REPLACE_EXISTING
                | PackageManager.INSTALL_FORWARD_LOCK);
    }

    @LargeTest
    public void testReplaceSdcard() throws Exception {
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
        // Do not run on devices with emulated external storage.
        if (Environment.isExternalStorageEmulated()) {
            return;
        }

<<<<<<< HEAD
        sampleReplaceFromRawResource(PackageManager.INSTALL_REPLACE_EXISTING |
                PackageManager.INSTALL_EXTERNAL);
    }

    /* -------------- Delete tests ---*/
    class DeleteObserver extends IPackageDeleteObserver.Stub {

        public boolean succeeded;
        private boolean doneFlag = false;

        public boolean isDone() {
            return doneFlag;
        }

        public void packageDeleted(String packageName, int returnCode) throws RemoteException {
            synchronized(this) {
                this.succeeded = returnCode == PackageManager.DELETE_SUCCEEDED;
                doneFlag = true;
                notifyAll();
            }
=======
        sampleReplaceFromRawResource(PackageManager.INSTALL_REPLACE_EXISTING
                | PackageManager.INSTALL_EXTERNAL);
    }

    /* -------------- Delete tests --- */
    private static class DeleteObserver extends IPackageDeleteObserver.Stub {
        private CountDownLatch mLatch = new CountDownLatch(1);

        private int mReturnCode;

        private final String mPackageName;

        private String mObservedPackage;

        public DeleteObserver(String packageName) {
            mPackageName = packageName;
        }

        public boolean isSuccessful() {
            return mReturnCode == PackageManager.DELETE_SUCCEEDED;
        }

        public void packageDeleted(String packageName, int returnCode) throws RemoteException {
            mObservedPackage = packageName;

            mReturnCode = returnCode;

            mLatch.countDown();
        }

        public void waitForCompletion(long timeoutMillis) {
            final long deadline = SystemClock.uptimeMillis() + timeoutMillis;

            long waitTime = timeoutMillis;
            while (waitTime > 0) {
                try {
                    boolean done = mLatch.await(waitTime, TimeUnit.MILLISECONDS);
                    if (done) {
                        assertEquals(mPackageName, mObservedPackage);
                        return;
                    }
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                waitTime = deadline - SystemClock.uptimeMillis();
            }

            throw new AssertionError("Timeout waiting for package deletion");
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
        }
    }

    class DeleteReceiver extends GenericReceiver {
        String pkgName;

        DeleteReceiver(String pkgName) {
            this.pkgName = pkgName;
            IntentFilter filter = new IntentFilter(Intent.ACTION_PACKAGE_REMOVED);
            filter.addDataScheme("package");
            super.setFilter(filter);
        }

        public boolean notifyNow(Intent intent) {
            String action = intent.getAction();
            if (!Intent.ACTION_PACKAGE_REMOVED.equals(action)) {
                return false;
            }
            Uri data = intent.getData();
            String installedPkg = data.getEncodedSchemeSpecificPart();
            if (pkgName.equals(installedPkg)) {
                return true;
            }
            return false;
        }
    }

<<<<<<< HEAD
    public boolean invokeDeletePackage(final String pkgName, int flags,
            GenericReceiver receiver) throws Exception {
        DeleteObserver observer = new DeleteObserver();
        final boolean received = false;
        mContext.registerReceiver(receiver, receiver.filter);
        try {
            // Wait on observer
            synchronized(observer) {
                synchronized (receiver) {
                    getPm().deletePackage(pkgName, observer, flags);
                    long waitTime = 0;
                    while((!observer.isDone()) && (waitTime < MAX_WAIT_TIME) ) {
                        observer.wait(WAIT_TIME_INCR);
                        waitTime += WAIT_TIME_INCR;
                    }
                    if(!observer.isDone()) {
                        throw new Exception("Timed out waiting for packageInstalled callback");
                    }
                    // Verify we received the broadcast
                    waitTime = 0;
                    while((!receiver.isDone()) && (waitTime < MAX_WAIT_TIME) ) {
                        receiver.wait(WAIT_TIME_INCR);
                        waitTime += WAIT_TIME_INCR;
                    }
                    if(!receiver.isDone()) {
                        throw new Exception("Timed out waiting for PACKAGE_REMOVED notification");
                    }
                    return receiver.received;
                }
            }
=======
    public boolean invokeDeletePackage(final String pkgName, int flags, GenericReceiver receiver)
            throws Exception {
        ApplicationInfo info = getPm().getApplicationInfo(pkgName,
                PackageManager.GET_UNINSTALLED_PACKAGES);

        mContext.registerReceiver(receiver, receiver.filter);
        try {
            DeleteObserver observer = new DeleteObserver(pkgName);

            getPm().deletePackage(pkgName, observer, flags | PackageManager.DELETE_ALL_USERS);
            observer.waitForCompletion(MAX_WAIT_TIME);

            assertUninstalled(info);

            // Verify we received the broadcast
            // TODO replace this with a CountDownLatch
            synchronized (receiver) {
                long waitTime = 0;
                while ((!receiver.isDone()) && (waitTime < MAX_WAIT_TIME)) {
                    receiver.wait(WAIT_TIME_INCR);
                    waitTime += WAIT_TIME_INCR;
                }
                if (!receiver.isDone()) {
                    throw new Exception("Timed out waiting for PACKAGE_REMOVED notification");
                }
            }
            return receiver.received;
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
        } finally {
            mContext.unregisterReceiver(receiver);
        }
    }

<<<<<<< HEAD
    public void deleteFromRawResource(int iFlags, int dFlags) {
        InstallParams ip = sampleInstallFromRawResource(iFlags, false);
        boolean retainData = ((dFlags & PackageManager.DONT_DELETE_DATA) != 0);
        GenericReceiver receiver = new DeleteReceiver(ip.pkg.packageName);
        DeleteObserver observer = new DeleteObserver();
=======
    private static void assertUninstalled(ApplicationInfo info) throws Exception {
        File nativeLibraryFile = new File(info.nativeLibraryDir);
        assertFalse("Native library directory should be erased", nativeLibraryFile.exists());
    }

    public void deleteFromRawResource(int iFlags, int dFlags) throws Exception {
        InstallParams ip = sampleInstallFromRawResource(iFlags, false);
        boolean retainData = ((dFlags & PackageManager.DELETE_KEEP_DATA) != 0);
        GenericReceiver receiver = new DeleteReceiver(ip.pkg.packageName);
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
        try {
            assertTrue(invokeDeletePackage(ip.pkg.packageName, dFlags, receiver));
            ApplicationInfo info = null;
            Log.i(TAG, "okay4");
            try {
<<<<<<< HEAD
            info = getPm().getApplicationInfo(ip.pkg.packageName,
                    PackageManager.GET_UNINSTALLED_PACKAGES);
=======
                info = getPm().getApplicationInfo(ip.pkg.packageName,
                        PackageManager.GET_UNINSTALLED_PACKAGES);
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
            } catch (NameNotFoundException e) {
                info = null;
            }
            if (retainData) {
                assertNotNull(info);
                assertEquals(info.packageName, ip.pkg.packageName);
                File file = new File(info.dataDir);
                assertTrue(file.exists());
            } else {
                assertNull(info);
            }
        } catch (Exception e) {
            failStr(e);
        } finally {
            cleanUpInstall(ip);
        }
    }

    @LargeTest
<<<<<<< HEAD
    public void testDeleteNormalInternal() {
=======
    public void testDeleteNormalInternal() throws Exception {
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
        deleteFromRawResource(0, 0);
    }

    @LargeTest
<<<<<<< HEAD
    public void testDeleteFwdLockedInternal() {
=======
    public void testDeleteFwdLockedInternal() throws Exception {
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
        deleteFromRawResource(PackageManager.INSTALL_FORWARD_LOCK, 0);
    }

    @LargeTest
<<<<<<< HEAD
    public void testDeleteSdcard() {
=======
    public void testDeleteSdcard() throws Exception {
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
        // Do not run on devices with emulated external storage.
        if (Environment.isExternalStorageEmulated()) {
            return;
        }

        deleteFromRawResource(PackageManager.INSTALL_EXTERNAL, 0);
    }

    @LargeTest
<<<<<<< HEAD
    public void testDeleteNormalInternalRetainData() {
        deleteFromRawResource(0, PackageManager.DONT_DELETE_DATA);
    }

    @LargeTest
    public void testDeleteFwdLockedInternalRetainData() {
        deleteFromRawResource(PackageManager.INSTALL_FORWARD_LOCK, PackageManager.DONT_DELETE_DATA);
    }

    @LargeTest
    public void testDeleteSdcardRetainData() {
=======
    public void testDeleteNormalInternalRetainData() throws Exception {
        deleteFromRawResource(0, PackageManager.DELETE_KEEP_DATA);
    }

    @LargeTest
    public void testDeleteFwdLockedInternalRetainData() throws Exception {
        deleteFromRawResource(PackageManager.INSTALL_FORWARD_LOCK, PackageManager.DELETE_KEEP_DATA);
    }

    @LargeTest
    public void testDeleteSdcardRetainData() throws Exception {
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
        // Do not run on devices with emulated external storage.
        if (Environment.isExternalStorageEmulated()) {
            return;
        }

<<<<<<< HEAD
        deleteFromRawResource(PackageManager.INSTALL_EXTERNAL, PackageManager.DONT_DELETE_DATA);
    }

    /* sdcard mount/unmount tests ******/

    class SdMountReceiver extends GenericReceiver {
        String pkgNames[];
=======
        deleteFromRawResource(PackageManager.INSTALL_EXTERNAL, PackageManager.DELETE_KEEP_DATA);
    }

    /* sdcard mount/unmount tests ***** */

    class SdMountReceiver extends GenericReceiver {
        String pkgNames[];

>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
        boolean status = true;

        SdMountReceiver(String[] pkgNames) {
            this.pkgNames = pkgNames;
            IntentFilter filter = new IntentFilter(Intent.ACTION_EXTERNAL_APPLICATIONS_AVAILABLE);
            super.setFilter(filter);
        }

        public boolean notifyNow(Intent intent) {
            Log.i(TAG, "okay 1");
            String action = intent.getAction();
            if (!Intent.ACTION_EXTERNAL_APPLICATIONS_AVAILABLE.equals(action)) {
                return false;
            }
            String rpkgList[] = intent.getStringArrayExtra(Intent.EXTRA_CHANGED_PACKAGE_LIST);
            for (String pkg : pkgNames) {
                boolean found = false;
                for (String rpkg : rpkgList) {
                    if (rpkg.equals(pkg)) {
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    status = false;
                    return true;
                }
            }
            return true;
        }
    }

    class SdUnMountReceiver extends GenericReceiver {
        String pkgNames[];
<<<<<<< HEAD
=======

>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
        boolean status = true;

        SdUnMountReceiver(String[] pkgNames) {
            this.pkgNames = pkgNames;
            IntentFilter filter = new IntentFilter(Intent.ACTION_EXTERNAL_APPLICATIONS_UNAVAILABLE);
            super.setFilter(filter);
        }

        public boolean notifyNow(Intent intent) {
            String action = intent.getAction();
            if (!Intent.ACTION_EXTERNAL_APPLICATIONS_UNAVAILABLE.equals(action)) {
                return false;
            }
            String rpkgList[] = intent.getStringArrayExtra(Intent.EXTRA_CHANGED_PACKAGE_LIST);
            for (String pkg : pkgNames) {
                boolean found = false;
                for (String rpkg : rpkgList) {
                    if (rpkg.equals(pkg)) {
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    status = false;
                    return true;
                }
            }
            return true;
        }
    }

    IMountService getMs() {
        IBinder service = ServiceManager.getService("mount");
        if (service != null) {
            return IMountService.Stub.asInterface(service);
        } else {
            Log.e(TAG, "Can't get mount service");
        }
        return null;
    }

    boolean checkMediaState(String desired) {
        try {
            String mPath = Environment.getExternalStorageDirectory().getPath();
            String actual = getMs().getVolumeState(mPath);
            if (desired.equals(actual)) {
                return true;
            } else {
                return false;
            }
        } catch (RemoteException e) {
            Log.e(TAG, "Exception while checking media state", e);
            return false;
        }
    }

    boolean mountMedia() {
        // We can't mount emulated storage.
        if (Environment.isExternalStorageEmulated()) {
            return true;
        }

        if (checkMediaState(Environment.MEDIA_MOUNTED)) {
            return true;
        }

        final String path = Environment.getExternalStorageDirectory().toString();
        StorageListener observer = new StorageListener(Environment.MEDIA_MOUNTED);
        StorageManager sm = (StorageManager) mContext.getSystemService(Context.STORAGE_SERVICE);
        sm.registerListener(observer);
        try {
            // Wait on observer
            synchronized (observer) {
                int ret = getMs().mountVolume(path);
                if (ret != StorageResultCode.OperationSucceeded) {
                    throw new Exception("Could not mount the media");
                }
                long waitTime = 0;
                while ((!observer.isDone()) && (waitTime < MAX_WAIT_TIME)) {
                    observer.wait(WAIT_TIME_INCR);
                    waitTime += WAIT_TIME_INCR;
                }
                if (!observer.isDone()) {
                    throw new Exception("Timed out waiting for unmount media notification");
                }
                return true;
            }
        } catch (Exception e) {
            Log.e(TAG, "Exception : " + e);
            return false;
        } finally {
            sm.unregisterListener(observer);
        }
    }

    private boolean unmountMedia() {
        // We can't unmount emulated storage.
        if (Environment.isExternalStorageEmulated()) {
            return true;
        }

        if (checkMediaState(Environment.MEDIA_UNMOUNTED)) {
            return true;
        }

        final String path = Environment.getExternalStorageDirectory().getPath();
        StorageListener observer = new StorageListener(Environment.MEDIA_UNMOUNTED);
        StorageManager sm = (StorageManager) mContext.getSystemService(Context.STORAGE_SERVICE);
        sm.registerListener(observer);
        try {
            // Wait on observer
<<<<<<< HEAD
            synchronized(observer) {
                getMs().unmountVolume(path, true, false);
                long waitTime = 0;
                while((!observer.isDone()) && (waitTime < MAX_WAIT_TIME) ) {
                    observer.wait(WAIT_TIME_INCR);
                    waitTime += WAIT_TIME_INCR;
                }
                if(!observer.isDone()) {
=======
            synchronized (observer) {
                getMs().unmountVolume(path, true, false);
                long waitTime = 0;
                while ((!observer.isDone()) && (waitTime < MAX_WAIT_TIME)) {
                    observer.wait(WAIT_TIME_INCR);
                    waitTime += WAIT_TIME_INCR;
                }
                if (!observer.isDone()) {
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
                    throw new Exception("Timed out waiting for unmount media notification");
                }
                return true;
            }
        } catch (Exception e) {
            Log.e(TAG, "Exception : " + e);
            return false;
        } finally {
            sm.unregisterListener(observer);
        }
    }

<<<<<<< HEAD
    private boolean mountFromRawResource() {
=======
    private boolean mountFromRawResource() throws Exception {
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
        // Install pkg on sdcard
        InstallParams ip = sampleInstallFromRawResource(PackageManager.INSTALL_EXTERNAL, false);
        if (localLOGV) Log.i(TAG, "Installed pkg on sdcard");
        boolean origState = checkMediaState(Environment.MEDIA_MOUNTED);
        boolean registeredReceiver = false;
        SdMountReceiver receiver = new SdMountReceiver(new String[]{ip.pkg.packageName});
        try {
            if (localLOGV) Log.i(TAG, "Unmounting media");
            // Unmount media
            assertTrue(unmountMedia());
            if (localLOGV) Log.i(TAG, "Unmounted media");
            // Register receiver here
            PackageManager pm = getPm();
            mContext.registerReceiver(receiver, receiver.filter);
            registeredReceiver = true;

            // Wait on receiver
            synchronized (receiver) {
                if (localLOGV) Log.i(TAG, "Mounting media");
                // Mount media again
                assertTrue(mountMedia());
                if (localLOGV) Log.i(TAG, "Mounted media");
                if (localLOGV) Log.i(TAG, "Waiting for notification");
                long waitTime = 0;
                // Verify we received the broadcast
                waitTime = 0;
<<<<<<< HEAD
                while((!receiver.isDone()) && (waitTime < MAX_WAIT_TIME) ) {
=======
                while ((!receiver.isDone()) && (waitTime < MAX_WAIT_TIME)) {
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
                    receiver.wait(WAIT_TIME_INCR);
                    waitTime += WAIT_TIME_INCR;
                }
                if(!receiver.isDone()) {
                    failStr("Timed out waiting for EXTERNAL_APPLICATIONS notification");
                }
                return receiver.received;
            }
        } catch (InterruptedException e) {
            failStr(e);
            return false;
        } finally {
<<<<<<< HEAD
            if (registeredReceiver) mContext.unregisterReceiver(receiver);
=======
            if (registeredReceiver) {
                mContext.unregisterReceiver(receiver);
            }
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
            // Restore original media state
            if (origState) {
                mountMedia();
            } else {
                unmountMedia();
            }
            if (localLOGV) Log.i(TAG, "Cleaning up install");
            cleanUpInstall(ip);
        }
    }

    /*
     * Install package on sdcard. Unmount and then mount the media.
     * (Use PackageManagerService private api for now)
     * Make sure the installed package is available.
     */
    @LargeTest
<<<<<<< HEAD
    public void testMountSdNormalInternal() {
=======
    public void testMountSdNormalInternal() throws Exception {
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
        // Do not run on devices with emulated external storage.
        if (Environment.isExternalStorageEmulated()) {
            return;
        }

        assertTrue(mountFromRawResource());
    }

<<<<<<< HEAD
    void cleanUpInstall(InstallParams ip) {
=======
    void cleanUpInstall(InstallParams ip) throws Exception {
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
        if (ip == null) {
            return;
        }
        Runtime.getRuntime().gc();
<<<<<<< HEAD
        Log.i(TAG, "Deleting package : " + ip.pkg.packageName);
        getPm().deletePackage(ip.pkg.packageName, null, 0);
        File outFile = new File(ip.pkg.mScanPath);
        if (outFile != null && outFile.exists()) {
            outFile.delete();
        }
    }
    void cleanUpInstall(String pkgName) {
=======

        final String packageName = ip.pkg.packageName;
        Log.i(TAG, "Deleting package : " + packageName);

        ApplicationInfo info = null;
        try {
            info = getPm().getApplicationInfo(packageName, PackageManager.GET_UNINSTALLED_PACKAGES);
        } catch (NameNotFoundException ignored) {
        }

        DeleteObserver observer = new DeleteObserver(packageName);
        getPm().deletePackage(packageName, observer, PackageManager.DELETE_ALL_USERS);
        observer.waitForCompletion(MAX_WAIT_TIME);

        try {
            if (info != null) {
                assertUninstalled(info);
            }
        } finally {
            File outFile = new File(ip.pkg.mScanPath);
            if (outFile != null && outFile.exists()) {
                outFile.delete();
            }
        }
    }

    private void cleanUpInstall(String pkgName) throws Exception {
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
        if (pkgName == null) {
            return;
        }
        Log.i(TAG, "Deleting package : " + pkgName);
        try {
            ApplicationInfo info = getPm().getApplicationInfo(pkgName,
                    PackageManager.GET_UNINSTALLED_PACKAGES);
<<<<<<< HEAD
            if (info != null) {
                getPm().deletePackage(pkgName, null, 0);
            }
        } catch (NameNotFoundException e) {}
    }

    @LargeTest
    public void testManifestInstallLocationInternal() {
=======

            if (info != null) {
                DeleteObserver observer = new DeleteObserver(pkgName);
                getPm().deletePackage(pkgName, observer, PackageManager.DELETE_ALL_USERS);
                observer.waitForCompletion(MAX_WAIT_TIME);
                assertUninstalled(info);
            }
        } catch (NameNotFoundException e) {
        }
    }

    @LargeTest
    public void testManifestInstallLocationInternal() throws Exception {
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
        installFromRawResource("install.apk", R.raw.install_loc_internal,
                0, true, false, -1, PackageInfo.INSTALL_LOCATION_INTERNAL_ONLY);
    }

    @LargeTest
<<<<<<< HEAD
    public void testManifestInstallLocationSdcard() {
=======
    public void testManifestInstallLocationSdcard() throws Exception {
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
        // Do not run on devices with emulated external storage.
        if (Environment.isExternalStorageEmulated()) {
            return;
        }

        installFromRawResource("install.apk", R.raw.install_loc_sdcard,
                0, true, false, -1, PackageInfo.INSTALL_LOCATION_PREFER_EXTERNAL);
    }

    @LargeTest
<<<<<<< HEAD
    public void testManifestInstallLocationAuto() {
=======
    public void testManifestInstallLocationAuto() throws Exception {
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
        installFromRawResource("install.apk", R.raw.install_loc_auto,
                0, true, false, -1, PackageInfo.INSTALL_LOCATION_AUTO);
    }

    @LargeTest
<<<<<<< HEAD
    public void testManifestInstallLocationUnspecified() {
=======
    public void testManifestInstallLocationUnspecified() throws Exception {
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
        installFromRawResource("install.apk", R.raw.install_loc_unspecified,
                0, true, false, -1, PackageInfo.INSTALL_LOCATION_UNSPECIFIED);
    }

    @LargeTest
<<<<<<< HEAD
    public void testManifestInstallLocationFwdLockedFlagSdcard() {
=======
    public void testManifestInstallLocationFwdLockedFlagSdcard() throws Exception {
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
        // Do not run on devices with emulated external storage.
        if (Environment.isExternalStorageEmulated()) {
            return;
        }

        installFromRawResource("install.apk", R.raw.install_loc_unspecified,
                PackageManager.INSTALL_FORWARD_LOCK |
                PackageManager.INSTALL_EXTERNAL, true, false, -1,
                PackageInfo.INSTALL_LOCATION_PREFER_EXTERNAL);
    }

    @LargeTest
<<<<<<< HEAD
    public void testManifestInstallLocationFwdLockedSdcard() {
=======
    public void testManifestInstallLocationFwdLockedSdcard() throws Exception {
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
        // Do not run on devices with emulated external storage.
        if (Environment.isExternalStorageEmulated()) {
            return;
        }

        installFromRawResource("install.apk", R.raw.install_loc_sdcard,
<<<<<<< HEAD
                PackageManager.INSTALL_FORWARD_LOCK, true, false,
                -1,
=======
                PackageManager.INSTALL_FORWARD_LOCK, true, false, -1,
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
                PackageInfo.INSTALL_LOCATION_PREFER_EXTERNAL);
    }

    /*
     * Install a package on internal flash via PackageManager install flag. Replace
     * the package via flag to install on sdcard. Make sure the new flag overrides
     * the old install location.
     */
    @LargeTest
<<<<<<< HEAD
    public void testReplaceFlagInternalSdcard() {
=======
    public void testReplaceFlagInternalSdcard() throws Exception {
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
        // Do not run on devices with emulated external storage.
        if (Environment.isExternalStorageEmulated()) {
            return;
        }

        int iFlags = 0;
        int rFlags = PackageManager.INSTALL_EXTERNAL;
        InstallParams ip = sampleInstallFromRawResource(iFlags, false);
        GenericReceiver receiver = new ReplaceReceiver(ip.pkg.packageName);
        int replaceFlags = rFlags | PackageManager.INSTALL_REPLACE_EXISTING;
        try {
            invokeInstallPackage(ip.packageURI, replaceFlags, receiver, true);
            assertInstall(ip.pkg, rFlags, ip.pkg.installLocation);
        } catch (Exception e) {
            failStr("Failed with exception : " + e);
        } finally {
            cleanUpInstall(ip);
        }
    }

    /*
     * Install a package on sdcard via PackageManager install flag. Replace
     * the package with no flags or manifest option and make sure the old
     * install location is retained.
     */
    @LargeTest
<<<<<<< HEAD
    public void testReplaceFlagSdcardInternal() {
=======
    public void testReplaceFlagSdcardInternal() throws Exception {
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
        // Do not run on devices with emulated external storage.
        if (Environment.isExternalStorageEmulated()) {
            return;
        }

        int iFlags = PackageManager.INSTALL_EXTERNAL;
        int rFlags = 0;
        InstallParams ip = sampleInstallFromRawResource(iFlags, false);
        GenericReceiver receiver = new ReplaceReceiver(ip.pkg.packageName);
        int replaceFlags = rFlags | PackageManager.INSTALL_REPLACE_EXISTING;
        try {
            invokeInstallPackage(ip.packageURI, replaceFlags, receiver, true);
            assertInstall(ip.pkg, iFlags, ip.pkg.installLocation);
        } catch (Exception e) {
            failStr("Failed with exception : " + e);
        } finally {
            cleanUpInstall(ip);
        }
    }

    @LargeTest
<<<<<<< HEAD
    public void testManifestInstallLocationReplaceInternalSdcard() {
=======
    public void testManifestInstallLocationReplaceInternalSdcard() throws Exception {
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
        // Do not run on devices with emulated external storage.
        if (Environment.isExternalStorageEmulated()) {
            return;
        }

        int iFlags = 0;
        int iApk = R.raw.install_loc_internal;
        int rFlags = 0;
        int rApk = R.raw.install_loc_sdcard;
        InstallParams ip = installFromRawResource("install.apk", iApk,
                iFlags, false,
                false, -1, PackageInfo.INSTALL_LOCATION_INTERNAL_ONLY);
        GenericReceiver receiver = new ReplaceReceiver(ip.pkg.packageName);
        int replaceFlags = rFlags | PackageManager.INSTALL_REPLACE_EXISTING;
        try {
            InstallParams rp = installFromRawResource("install.apk", rApk,
                    replaceFlags, false,
                    false, -1, PackageInfo.INSTALL_LOCATION_PREFER_EXTERNAL);
            assertInstall(rp.pkg, replaceFlags, rp.pkg.installLocation);
        } catch (Exception e) {
            failStr("Failed with exception : " + e);
        } finally {
            cleanUpInstall(ip);
        }
    }

    @LargeTest
<<<<<<< HEAD
    public void testManifestInstallLocationReplaceSdcardInternal() {
=======
    public void testManifestInstallLocationReplaceSdcardInternal() throws Exception {
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
        // Do not run on devices with emulated external storage.
        if (Environment.isExternalStorageEmulated()) {
            return;
        }

        int iFlags = 0;
        int iApk = R.raw.install_loc_sdcard;
        int rFlags = 0;
        int rApk = R.raw.install_loc_unspecified;
        InstallParams ip = installFromRawResource("install.apk", iApk,
                iFlags, false,
                false, -1, PackageInfo.INSTALL_LOCATION_PREFER_EXTERNAL);
        int replaceFlags = rFlags | PackageManager.INSTALL_REPLACE_EXISTING;
        try {
            InstallParams rp = installFromRawResource("install.apk", rApk,
                    replaceFlags, false,
                    false, -1, PackageInfo.INSTALL_LOCATION_PREFER_EXTERNAL);
            assertInstall(rp.pkg, replaceFlags, ip.pkg.installLocation);
        } catch (Exception e) {
            failStr("Failed with exception : " + e);
        } finally {
            cleanUpInstall(ip);
        }
    }

    class MoveReceiver extends GenericReceiver {
        String pkgName;
<<<<<<< HEAD
        final static int INVALID = -1;
        final static int REMOVED = 1;
        final static int ADDED = 2;
=======

        final static int INVALID = -1;

        final static int REMOVED = 1;

        final static int ADDED = 2;

>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
        int removed = INVALID;

        MoveReceiver(String pkgName) {
            this.pkgName = pkgName;
            filter = new IntentFilter(Intent.ACTION_EXTERNAL_APPLICATIONS_AVAILABLE);
            filter.addAction(Intent.ACTION_EXTERNAL_APPLICATIONS_UNAVAILABLE);
            super.setFilter(filter);
        }

        public boolean notifyNow(Intent intent) {
            String action = intent.getAction();
            Log.i(TAG, "MoveReceiver::" + action);
            if (Intent.ACTION_EXTERNAL_APPLICATIONS_UNAVAILABLE.equals(action)) {
                String[] list = intent.getStringArrayExtra(Intent.EXTRA_CHANGED_PACKAGE_LIST);
                if (list != null) {
                    for (String pkg : list) {
                        if (pkg.equals(pkgName)) {
                            removed = REMOVED;
                            break;
                        }
                    }
                }
                removed = REMOVED;
            } else if (Intent.ACTION_EXTERNAL_APPLICATIONS_AVAILABLE.equals(action)) {
                if (removed != REMOVED) {
                    return false;
                }
                String[] list = intent.getStringArrayExtra(Intent.EXTRA_CHANGED_PACKAGE_LIST);
                if (list != null) {
                    for (String pkg : list) {
                        if (pkg.equals(pkgName)) {
                            removed = ADDED;
                            return true;
                        }
                    }
                }
            }
            return false;
        }
    }

    private class PackageMoveObserver extends IPackageMoveObserver.Stub {
        public int returnCode;
<<<<<<< HEAD
        private boolean doneFlag = false;
        public String packageName;
        public PackageMoveObserver(String pkgName) {
            packageName = pkgName;
        }
=======

        private boolean doneFlag = false;

        public String packageName;

        public PackageMoveObserver(String pkgName) {
            packageName = pkgName;
        }

>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
        public void packageMoved(String packageName, int returnCode) {
            Log.i("DEBUG_MOVE::", "pkg = " + packageName + ", " + "ret = " + returnCode);
            if (!packageName.equals(this.packageName)) {
                return;
            }
<<<<<<< HEAD
            synchronized(this) {
=======
            synchronized (this) {
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
                this.returnCode = returnCode;
                doneFlag = true;
                notifyAll();
            }
        }

        public boolean isDone() {
            return doneFlag;
        }
    }

<<<<<<< HEAD
    public boolean invokeMovePackage(String pkgName, int flags,
            GenericReceiver receiver) throws Exception {
=======
    public boolean invokeMovePackage(String pkgName, int flags, GenericReceiver receiver)
            throws Exception {
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
        PackageMoveObserver observer = new PackageMoveObserver(pkgName);
        final boolean received = false;
        mContext.registerReceiver(receiver, receiver.filter);
        try {
            // Wait on observer
<<<<<<< HEAD
            synchronized(observer) {
                synchronized (receiver) {
                    getPm().movePackage(pkgName, observer, flags);
                    long waitTime = 0;
                    while((!observer.isDone()) && (waitTime < MAX_WAIT_TIME) ) {
                        observer.wait(WAIT_TIME_INCR);
                        waitTime += WAIT_TIME_INCR;
                    }
                    if(!observer.isDone()) {
=======
            synchronized (observer) {
                synchronized (receiver) {
                    getPm().movePackage(pkgName, observer, flags);
                    long waitTime = 0;
                    while ((!observer.isDone()) && (waitTime < MAX_WAIT_TIME)) {
                        observer.wait(WAIT_TIME_INCR);
                        waitTime += WAIT_TIME_INCR;
                    }
                    if (!observer.isDone()) {
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
                        throw new Exception("Timed out waiting for pkgmove callback");
                    }
                    if (observer.returnCode != PackageManager.MOVE_SUCCEEDED) {
                        return false;
                    }
                    // Verify we received the broadcast
                    waitTime = 0;
<<<<<<< HEAD
                    while((!receiver.isDone()) && (waitTime < MAX_WAIT_TIME) ) {
                        receiver.wait(WAIT_TIME_INCR);
                        waitTime += WAIT_TIME_INCR;
                    }
                    if(!receiver.isDone()) {
=======
                    while ((!receiver.isDone()) && (waitTime < MAX_WAIT_TIME)) {
                        receiver.wait(WAIT_TIME_INCR);
                        waitTime += WAIT_TIME_INCR;
                    }
                    if (!receiver.isDone()) {
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
                        throw new Exception("Timed out waiting for MOVE notifications");
                    }
                    return receiver.received;
                }
            }
        } finally {
            mContext.unregisterReceiver(receiver);
        }
    }
<<<<<<< HEAD
=======

>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
    private boolean invokeMovePackageFail(String pkgName, int flags, int errCode) throws Exception {
        PackageMoveObserver observer = new PackageMoveObserver(pkgName);
        try {
            // Wait on observer
<<<<<<< HEAD
            synchronized(observer) {
                getPm().movePackage(pkgName, observer, flags);
                long waitTime = 0;
                while((!observer.isDone()) && (waitTime < MAX_WAIT_TIME) ) {
                    observer.wait(WAIT_TIME_INCR);
                    waitTime += WAIT_TIME_INCR;
                }
                if(!observer.isDone()) {
=======
            synchronized (observer) {
                getPm().movePackage(pkgName, observer, flags);
                long waitTime = 0;
                while ((!observer.isDone()) && (waitTime < MAX_WAIT_TIME)) {
                    observer.wait(WAIT_TIME_INCR);
                    waitTime += WAIT_TIME_INCR;
                }
                if (!observer.isDone()) {
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
                    throw new Exception("Timed out waiting for pkgmove callback");
                }
                assertEquals(errCode, observer.returnCode);
            }
        } finally {
        }
        return true;
    }

    private int getDefaultInstallLoc() {
        int origDefaultLoc = PackageInfo.INSTALL_LOCATION_AUTO;
        try {
<<<<<<< HEAD
            origDefaultLoc = Settings.System.getInt(mContext.getContentResolver(), Settings.Secure.DEFAULT_INSTALL_LOCATION);
=======
            origDefaultLoc = Settings.Global.getInt(mContext.getContentResolver(),
                    Settings.Global.DEFAULT_INSTALL_LOCATION);
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
        } catch (SettingNotFoundException e1) {
        }
        return origDefaultLoc;
    }

    private void setInstallLoc(int loc) {
<<<<<<< HEAD
        Settings.System.putInt(mContext.getContentResolver(),
                Settings.Secure.DEFAULT_INSTALL_LOCATION, loc);
    }
=======
        Settings.Global.putInt(mContext.getContentResolver(),
                Settings.Global.DEFAULT_INSTALL_LOCATION, loc);
    }

>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
    /*
     * Tests for moving apps between internal and external storage
     */
    /*
     * Utility function that reads a apk bundled as a raw resource
     * copies it into own data directory and invokes
     * PackageManager api to install first and then replace it
     * again.
     */

<<<<<<< HEAD
    private void moveFromRawResource(String outFileName,
            int rawResId, int installFlags, int moveFlags, boolean cleanUp,
            boolean fail, int result) {
=======
    private void moveFromRawResource(String outFileName, int rawResId, int installFlags,
            int moveFlags, boolean cleanUp, boolean fail, int result) throws Exception {
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
        int origDefaultLoc = getDefaultInstallLoc();
        InstallParams ip = null;
        try {
            setInstallLoc(PackageHelper.APP_INSTALL_AUTO);
            // Install first
            ip = installFromRawResource("install.apk", rawResId, installFlags, false,
                    false, -1, PackageInfo.INSTALL_LOCATION_UNSPECIFIED);
            ApplicationInfo oldAppInfo = getPm().getApplicationInfo(ip.pkg.packageName, 0);
            if (fail) {
                assertTrue(invokeMovePackageFail(ip.pkg.packageName, moveFlags, result));
                ApplicationInfo info = getPm().getApplicationInfo(ip.pkg.packageName, 0);
                assertNotNull(info);
                assertEquals(oldAppInfo.flags, info.flags);
            } else {
                // Create receiver based on expRetCode
                MoveReceiver receiver = new MoveReceiver(ip.pkg.packageName);
<<<<<<< HEAD
                boolean retCode = invokeMovePackage(ip.pkg.packageName, moveFlags,
                        receiver);
=======
                boolean retCode = invokeMovePackage(ip.pkg.packageName, moveFlags, receiver);
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
                assertTrue(retCode);
                ApplicationInfo info = getPm().getApplicationInfo(ip.pkg.packageName, 0);
                assertNotNull("ApplicationInfo for recently installed application should exist",
                        info);
                if ((moveFlags & PackageManager.MOVE_INTERNAL) != 0) {
                    assertTrue("ApplicationInfo.FLAG_EXTERNAL_STORAGE flag should NOT be set",
                            (info.flags & ApplicationInfo.FLAG_EXTERNAL_STORAGE) == 0);
<<<<<<< HEAD
                    assertTrue("ApplicationInfo.nativeLibraryDir should start with " + info.dataDir,
                            info.nativeLibraryDir.startsWith(info.dataDir));
                } else if ((moveFlags & PackageManager.MOVE_EXTERNAL_MEDIA) != 0){
                    assertTrue("ApplicationInfo.FLAG_EXTERNAL_STORAGE flag should be set",
                            (info.flags & ApplicationInfo.FLAG_EXTERNAL_STORAGE) != 0);
                    assertTrue("ApplicationInfo.nativeLibraryDir should start with " + SECURE_CONTAINERS_PREFIX,
                            info.nativeLibraryDir.startsWith(SECURE_CONTAINERS_PREFIX));
                    final File nativeLibSymLink = new File(info.dataDir, "lib");
                    assertTrue("The data directory should have a 'lib' symlink that points to the ASEC container",
                            nativeLibSymLink.getCanonicalPath().startsWith(SECURE_CONTAINERS_PREFIX));
=======
                    assertStartsWith("Native library dir should be in dataDir",
                            info.dataDir, info.nativeLibraryDir);
                } else if ((moveFlags & PackageManager.MOVE_EXTERNAL_MEDIA) != 0) {
                    assertTrue("ApplicationInfo.FLAG_EXTERNAL_STORAGE flag should be set",
                            (info.flags & ApplicationInfo.FLAG_EXTERNAL_STORAGE) != 0);
                    assertStartsWith("Native library dir should point to ASEC",
                            SECURE_CONTAINERS_PREFIX, info.nativeLibraryDir);
                    final File nativeLibSymLink = new File(info.dataDir, "lib");
                    assertStartsWith("The data directory should have a 'lib' symlink that points to the ASEC container",
                            SECURE_CONTAINERS_PREFIX, nativeLibSymLink.getCanonicalPath());
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
                }
            }
        } catch (NameNotFoundException e) {
            failStr("Pkg hasnt been installed correctly");
        } catch (Exception e) {
            failStr("Failed with exception : " + e);
        } finally {
            if (ip != null) {
                cleanUpInstall(ip);
            }
            // Restore default install location
            setInstallLoc(origDefaultLoc);
        }
    }
<<<<<<< HEAD
    private void sampleMoveFromRawResource(int installFlags, int moveFlags, boolean fail,
            int result) {
=======

    private void sampleMoveFromRawResource(int installFlags, int moveFlags, boolean fail,
            int result) throws Exception {
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
        moveFromRawResource("install.apk",
                R.raw.install, installFlags, moveFlags, true,
                fail, result);
    }

    @LargeTest
<<<<<<< HEAD
    public void testMoveAppInternalToExternal() {
=======
    public void testMoveAppInternalToExternal() throws Exception {
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
        // Do not run on devices with emulated external storage.
        if (Environment.isExternalStorageEmulated()) {
            return;
        }

        int installFlags = PackageManager.INSTALL_INTERNAL;
        int moveFlags = PackageManager.MOVE_EXTERNAL_MEDIA;
        boolean fail = false;
        int result = PackageManager.MOVE_SUCCEEDED;
        sampleMoveFromRawResource(installFlags, moveFlags, fail, result);
    }

    @LargeTest
<<<<<<< HEAD
    public void testMoveAppInternalToInternal() {
=======
    public void testMoveAppInternalToInternal() throws Exception {
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
        int installFlags = PackageManager.INSTALL_INTERNAL;
        int moveFlags = PackageManager.MOVE_INTERNAL;
        boolean fail = true;
        int result = PackageManager.MOVE_FAILED_INVALID_LOCATION;
        sampleMoveFromRawResource(installFlags, moveFlags, fail, result);
    }

    @LargeTest
<<<<<<< HEAD
    public void testMoveAppExternalToExternal() {
=======
    public void testMoveAppExternalToExternal() throws Exception {
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
        // Do not run on devices with emulated external storage.
        if (Environment.isExternalStorageEmulated()) {
            return;
        }

        int installFlags = PackageManager.INSTALL_EXTERNAL;
        int moveFlags = PackageManager.MOVE_EXTERNAL_MEDIA;
        boolean fail = true;
        int result = PackageManager.MOVE_FAILED_INVALID_LOCATION;
        sampleMoveFromRawResource(installFlags, moveFlags, fail, result);
    }

    @LargeTest
<<<<<<< HEAD
    public void testMoveAppExternalToInternal() {
=======
    public void testMoveAppExternalToInternal() throws Exception {
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
        // Do not run on devices with emulated external storage.
        if (Environment.isExternalStorageEmulated()) {
            return;
        }

        int installFlags = PackageManager.INSTALL_EXTERNAL;
        int moveFlags = PackageManager.MOVE_INTERNAL;
        boolean fail = false;
        int result = PackageManager.MOVE_SUCCEEDED;
        sampleMoveFromRawResource(installFlags, moveFlags, fail, result);
    }

    @LargeTest
<<<<<<< HEAD
    public void testMoveAppForwardLocked() {
=======
    public void testMoveAppForwardLocked() throws Exception {
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
        // Do not run on devices with emulated external storage.
        if (Environment.isExternalStorageEmulated()) {
            return;
        }

        int installFlags = PackageManager.INSTALL_FORWARD_LOCK;
        int moveFlags = PackageManager.MOVE_EXTERNAL_MEDIA;
        boolean fail = false;
        int result = PackageManager.MOVE_SUCCEEDED;
        sampleMoveFromRawResource(installFlags, moveFlags, fail, result);
    }

    @LargeTest
<<<<<<< HEAD
    public void testMoveAppFailInternalToExternalDelete() {
=======
    public void testMoveAppFailInternalToExternalDelete() throws Exception {
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
        // Do not run on devices with emulated external storage.
        if (Environment.isExternalStorageEmulated()) {
            return;
        }

        int installFlags = 0;
        int moveFlags = PackageManager.MOVE_EXTERNAL_MEDIA;
        boolean fail = true;
        final int result = PackageManager.MOVE_FAILED_DOESNT_EXIST;

        int rawResId = R.raw.install;
        int origDefaultLoc = getDefaultInstallLoc();
        InstallParams ip = null;
        try {
            PackageManager pm = getPm();
            setInstallLoc(PackageHelper.APP_INSTALL_AUTO);
            // Install first
            ip = installFromRawResource("install.apk", R.raw.install, installFlags, false,
                    false, -1, PackageInfo.INSTALL_LOCATION_UNSPECIFIED);
            // Delete the package now retaining data.
            GenericReceiver receiver = new DeleteReceiver(ip.pkg.packageName);
<<<<<<< HEAD
            invokeDeletePackage(ip.pkg.packageName, PackageManager.DONT_DELETE_DATA, receiver);
=======
            invokeDeletePackage(ip.pkg.packageName, PackageManager.DELETE_KEEP_DATA, receiver);
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
            assertTrue(invokeMovePackageFail(ip.pkg.packageName, moveFlags, result));
        } catch (Exception e) {
            failStr(e);
        } finally {
            if (ip != null) {
                cleanUpInstall(ip);
            }
            // Restore default install location
            setInstallLoc(origDefaultLoc);
        }
    }

    /*
     * Test that an install error code is returned when media is unmounted
     * and package installed on sdcard via package manager flag.
     */
    @LargeTest
<<<<<<< HEAD
    public void testInstallSdcardUnmount() {
=======
    public void testInstallSdcardUnmount() throws Exception {
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
        // Do not run on devices with emulated external storage.
        if (Environment.isExternalStorageEmulated()) {
            return;
        }

        boolean origState = checkMediaState(Environment.MEDIA_MOUNTED);
        try {
            // Unmount sdcard
            assertTrue(unmountMedia());
            // Try to install and make sure an error code is returned.
            installFromRawResource("install.apk", R.raw.install,
                    PackageManager.INSTALL_EXTERNAL, false,
                    true, PackageManager.INSTALL_FAILED_MEDIA_UNAVAILABLE,
                    PackageInfo.INSTALL_LOCATION_AUTO);
        } finally {
            // Restore original media state
            if (origState) {
                mountMedia();
            } else {
                unmountMedia();
            }
        }
    }

    /*
     * Unmount sdcard. Try installing an app with manifest option to install
     * on sdcard. Make sure it gets installed on internal flash.
     */
    @LargeTest
<<<<<<< HEAD
    public void testInstallManifestSdcardUnmount() {
=======
    public void testInstallManifestSdcardUnmount() throws Exception {
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
        // Do not run on devices with emulated external storage.
        if (Environment.isExternalStorageEmulated()) {
            return;
        }

        boolean origState = checkMediaState(Environment.MEDIA_MOUNTED);
        try {
            // Unmount sdcard
            assertTrue(unmountMedia());
            InstallParams ip = new InstallParams("install.apk", R.raw.install_loc_sdcard);
            installFromRawResource(ip, 0, true, false, -1,
                    PackageInfo.INSTALL_LOCATION_INTERNAL_ONLY);
        } finally {
            // Restore original media state
            if (origState) {
                mountMedia();
            } else {
                unmountMedia();
            }
        }
    }

<<<<<<< HEAD
   /*---------- Recommended install location tests ----*/
   /* Precedence: FlagManifestExistingUser
    * PrecedenceSuffixes:
    * Flag : FlagI, FlagE, FlagF
    * I - internal, E - external, F - forward locked, Flag suffix absent if not using any option.
    * Manifest: ManifestI, ManifestE, ManifestA, Manifest suffix absent if not using any option.
    * Existing: Existing suffix absent if not existing.
    * User: UserI, UserE, UserA, User suffix absent if not existing.
    *
    */
=======
    /*---------- Recommended install location tests ----*/
    /*
     * PrecedenceSuffixes:
     * Flag : FlagI, FlagE, FlagF
     * I - internal, E - external, F - forward locked, Flag suffix absent if not using any option.
     * Manifest: ManifestI, ManifestE, ManifestA, Manifest suffix absent if not using any option.
     * Existing: Existing suffix absent if not existing.
     * User: UserI, UserE, UserA, User suffix absent if not existing.
     *
     */
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a

    /*
     * Install an app on internal flash
     */
    @LargeTest
<<<<<<< HEAD
    public void testFlagI() {
=======
    public void testFlagI() throws Exception {
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
        sampleInstallFromRawResource(PackageManager.INSTALL_INTERNAL, true);
    }

    /*
     * Install an app on sdcard.
     */
    @LargeTest
<<<<<<< HEAD
    public void testFlagE() {
=======
    public void testFlagE() throws Exception {
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
        // Do not run on devices with emulated external storage.
        if (Environment.isExternalStorageEmulated()) {
            return;
        }

        sampleInstallFromRawResource(PackageManager.INSTALL_EXTERNAL, true);
    }

    /*
     * Install an app forward-locked.
     */
    @LargeTest
<<<<<<< HEAD
    public void testFlagF() {
=======
    public void testFlagF() throws Exception {
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
        sampleInstallFromRawResource(PackageManager.INSTALL_FORWARD_LOCK, true);
    }

    /*
     * Install an app with both internal and external flags set. should fail
     */
    @LargeTest
<<<<<<< HEAD
    public void testFlagIE() {
=======
    public void testFlagIE() throws Exception {
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
        installFromRawResource("install.apk", R.raw.install,
                PackageManager.INSTALL_EXTERNAL | PackageManager.INSTALL_INTERNAL,
                false,
                true, PackageManager.INSTALL_FAILED_INVALID_INSTALL_LOCATION,
                PackageInfo.INSTALL_LOCATION_AUTO);
    }

    /*
     * Install an app with both internal and forward-lock flags set.
     */
    @LargeTest
<<<<<<< HEAD
    public void testFlagIF() {
=======
    public void testFlagIF() throws Exception {
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
        sampleInstallFromRawResource(PackageManager.INSTALL_FORWARD_LOCK
                | PackageManager.INSTALL_INTERNAL, true);
    }

    /*
     * Install an app with both external and forward-lock flags set.
     */
    @LargeTest
<<<<<<< HEAD
    public void testFlagEF() {
=======
    public void testFlagEF() throws Exception {
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
        // Do not run on devices with emulated external storage.
        if (Environment.isExternalStorageEmulated()) {
            return;
        }

        sampleInstallFromRawResource(PackageManager.INSTALL_FORWARD_LOCK
                | PackageManager.INSTALL_EXTERNAL, true);
    }

    /*
     * Install an app with both internal and external flags set with forward
     * lock. Should fail.
     */
    @LargeTest
<<<<<<< HEAD
    public void testFlagIEF() {
=======
    public void testFlagIEF() throws Exception {
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
        installFromRawResource("install.apk", R.raw.install,
                PackageManager.INSTALL_FORWARD_LOCK | PackageManager.INSTALL_INTERNAL |
                PackageManager.INSTALL_EXTERNAL,
                false,
                true, PackageManager.INSTALL_FAILED_INVALID_INSTALL_LOCATION,
                PackageInfo.INSTALL_LOCATION_AUTO);
    }

<<<<<<< HEAD
   /*
    * Install an app with both internal and manifest option set.
    * should install on internal.
    */
   @LargeTest
   public void testFlagIManifestI() {
       installFromRawResource("install.apk", R.raw.install_loc_internal,
               PackageManager.INSTALL_INTERNAL,
               true,
               false, -1,
               PackageInfo.INSTALL_LOCATION_INTERNAL_ONLY);
   }
   /*
    * Install an app with both internal and manifest preference for
    * preferExternal. Should install on internal.
    */
   @LargeTest
   public void testFlagIManifestE() {
       installFromRawResource("install.apk", R.raw.install_loc_sdcard,
               PackageManager.INSTALL_INTERNAL,
               true,
               false, -1,
               PackageInfo.INSTALL_LOCATION_INTERNAL_ONLY);
   }
   /*
    * Install an app with both internal and manifest preference for
    * auto. should install internal.
    */
   @LargeTest
   public void testFlagIManifestA() {
       installFromRawResource("install.apk", R.raw.install_loc_auto,
               PackageManager.INSTALL_INTERNAL,
               true,
               false, -1,
               PackageInfo.INSTALL_LOCATION_INTERNAL_ONLY);
   }
   /*
    * Install an app with both external and manifest option set.
    * should install externally.
    */
   @LargeTest
   public void testFlagEManifestI() {
=======
    /*
     * Install an app with both internal and manifest option set.
     * should install on internal.
     */
    @LargeTest
    public void testFlagIManifestI() throws Exception {
        installFromRawResource("install.apk", R.raw.install_loc_internal,
                PackageManager.INSTALL_INTERNAL,
                true,
                false, -1,
                PackageInfo.INSTALL_LOCATION_INTERNAL_ONLY);
    }
    /*
     * Install an app with both internal and manifest preference for
     * preferExternal. Should install on internal.
     */
    @LargeTest
    public void testFlagIManifestE() throws Exception {
        installFromRawResource("install.apk", R.raw.install_loc_sdcard,
                PackageManager.INSTALL_INTERNAL,
                true,
                false, -1,
                PackageInfo.INSTALL_LOCATION_INTERNAL_ONLY);
    }
    /*
     * Install an app with both internal and manifest preference for
     * auto. should install internal.
     */
    @LargeTest
    public void testFlagIManifestA() throws Exception {
        installFromRawResource("install.apk", R.raw.install_loc_auto,
                PackageManager.INSTALL_INTERNAL,
                true,
                false, -1,
                PackageInfo.INSTALL_LOCATION_INTERNAL_ONLY);
    }
    /*
     * Install an app with both external and manifest option set.
     * should install externally.
     */
    @LargeTest
    public void testFlagEManifestI() throws Exception {
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
        // Do not run on devices with emulated external storage.
        if (Environment.isExternalStorageEmulated()) {
            return;
        }

<<<<<<< HEAD
       installFromRawResource("install.apk", R.raw.install_loc_internal,
               PackageManager.INSTALL_EXTERNAL,
               true,
               false, -1,
               PackageInfo.INSTALL_LOCATION_PREFER_EXTERNAL);
   }

   /*
    * Install an app with both external and manifest preference for
    * preferExternal. Should install externally.
    */
   @LargeTest
   public void testFlagEManifestE() {
=======
        installFromRawResource("install.apk", R.raw.install_loc_internal,
                PackageManager.INSTALL_EXTERNAL,
                true,
                false, -1,
                PackageInfo.INSTALL_LOCATION_PREFER_EXTERNAL);
    }

    /*
     * Install an app with both external and manifest preference for
     * preferExternal. Should install externally.
     */
    @LargeTest
    public void testFlagEManifestE() throws Exception {
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
        // Do not run on devices with emulated external storage.
        if (Environment.isExternalStorageEmulated()) {
            return;
        }

        installFromRawResource("install.apk", R.raw.install_loc_sdcard,
                PackageManager.INSTALL_EXTERNAL,
                true,
                false, -1,
                PackageInfo.INSTALL_LOCATION_PREFER_EXTERNAL);
    }

    /*
     * Install an app with both external and manifest preference for
     * auto. should install on external media.
     */
    @LargeTest
<<<<<<< HEAD
    public void testFlagEManifestA() {
=======
    public void testFlagEManifestA() throws Exception {
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
        // Do not run on devices with emulated external storage.
        if (Environment.isExternalStorageEmulated()) {
            return;
        }

        installFromRawResource("install.apk", R.raw.install_loc_auto,
                PackageManager.INSTALL_EXTERNAL,
                true,
                false, -1,
                PackageInfo.INSTALL_LOCATION_PREFER_EXTERNAL);
    }

    /*
     * Install an app with fwd locked flag set and install location set to
     * internal. should install internally.
     */
    @LargeTest
<<<<<<< HEAD
    public void testFlagFManifestI() {
=======
    public void testFlagFManifestI() throws Exception {
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
        installFromRawResource("install.apk", R.raw.install_loc_internal,
                PackageManager.INSTALL_FORWARD_LOCK,
                true,
                false, -1,
                PackageInfo.INSTALL_LOCATION_INTERNAL_ONLY);
    }

    /*
     * Install an app with fwd locked flag set and install location set to
     * preferExternal. Should install externally.
     */
    @LargeTest
<<<<<<< HEAD
    public void testFlagFManifestE() {
=======
    public void testFlagFManifestE() throws Exception {
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
        // Do not run on devices with emulated external storage.
        if (Environment.isExternalStorageEmulated()) {
            return;
        }

        installFromRawResource("install.apk", R.raw.install_loc_sdcard,
                PackageManager.INSTALL_FORWARD_LOCK,
                true,
                false, -1,
                PackageInfo.INSTALL_LOCATION_PREFER_EXTERNAL);
    }

    /*
     * Install an app with fwd locked flag set and install location set to auto.
     * should install externally.
     */
    @LargeTest
<<<<<<< HEAD
    public void testFlagFManifestA() {
=======
    public void testFlagFManifestA() throws Exception {
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
        // Do not run on devices with emulated external storage.
        if (Environment.isExternalStorageEmulated()) {
            return;
        }

        installFromRawResource("install.apk", R.raw.install_loc_auto,
                PackageManager.INSTALL_FORWARD_LOCK,
                true,
                false, -1,
                PackageInfo.INSTALL_LOCATION_AUTO);
    }

<<<<<<< HEAD
    /* The following test functions verify install location for existing apps.
=======
    /*
     * The following test functions verify install location for existing apps.
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
     * ie existing app can be installed internally or externally. If install
     * flag is explicitly set it should override current location. If manifest location
     * is set, that should over ride current location too. if not the existing install
     * location should be honoured.
     * testFlagI/E/F/ExistingI/E -
     */
    @LargeTest
<<<<<<< HEAD
    public void testFlagIExistingI() {
=======
    public void testFlagIExistingI() throws Exception {
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
        int iFlags = PackageManager.INSTALL_INTERNAL;
        int rFlags = PackageManager.INSTALL_INTERNAL | PackageManager.INSTALL_REPLACE_EXISTING;
        // First install.
        installFromRawResource("install.apk", R.raw.install,
                iFlags,
                false,
                false, -1,
                -1);
        // Replace now
        installFromRawResource("install.apk", R.raw.install,
                rFlags,
                true,
                false, -1,
                -1);
    }

    @LargeTest
<<<<<<< HEAD
    public void testFlagIExistingE() {
=======
    public void testFlagIExistingE() throws Exception {
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
        // Do not run on devices with emulated external storage.
        if (Environment.isExternalStorageEmulated()) {
            return;
        }

        int iFlags = PackageManager.INSTALL_EXTERNAL;
        int rFlags = PackageManager.INSTALL_INTERNAL | PackageManager.INSTALL_REPLACE_EXISTING;
        // First install.
        installFromRawResource("install.apk", R.raw.install,
                iFlags,
                false,
                false, -1,
                -1);
        // Replace now
        installFromRawResource("install.apk", R.raw.install,
                rFlags,
                true,
                false, -1,
                -1);
    }

    @LargeTest
<<<<<<< HEAD
    public void testFlagEExistingI() {
=======
    public void testFlagEExistingI() throws Exception {
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
        // Do not run on devices with emulated external storage.
        if (Environment.isExternalStorageEmulated()) {
            return;
        }

        int iFlags = PackageManager.INSTALL_INTERNAL;
        int rFlags = PackageManager.INSTALL_EXTERNAL | PackageManager.INSTALL_REPLACE_EXISTING;
        // First install.
        installFromRawResource("install.apk", R.raw.install,
                iFlags,
                false,
                false, -1,
                -1);
        // Replace now
        installFromRawResource("install.apk", R.raw.install,
                rFlags,
                true,
                false, -1,
                -1);
    }

    @LargeTest
<<<<<<< HEAD
    public void testFlagEExistingE() {
=======
    public void testFlagEExistingE() throws Exception {
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
        // Do not run on devices with emulated external storage.
        if (Environment.isExternalStorageEmulated()) {
            return;
        }

        int iFlags = PackageManager.INSTALL_EXTERNAL;
        int rFlags = PackageManager.INSTALL_EXTERNAL | PackageManager.INSTALL_REPLACE_EXISTING;
        // First install.
        installFromRawResource("install.apk", R.raw.install,
                iFlags,
                false,
                false, -1,
                -1);
        // Replace now
        installFromRawResource("install.apk", R.raw.install,
                rFlags,
                true,
                false, -1,
                -1);
    }

    @LargeTest
<<<<<<< HEAD
    public void testFlagFExistingI() {
=======
    public void testFlagFExistingI() throws Exception {
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
        int iFlags = PackageManager.INSTALL_INTERNAL;
        int rFlags = PackageManager.INSTALL_FORWARD_LOCK | PackageManager.INSTALL_REPLACE_EXISTING;
        // First install.
        installFromRawResource("install.apk", R.raw.install,
                iFlags,
                false,
                false, -1,
                -1);
        // Replace now
        installFromRawResource("install.apk", R.raw.install,
                rFlags,
                true,
                false, -1,
                -1);
    }

    @LargeTest
<<<<<<< HEAD
    public void testFlagFExistingE() {
=======
    public void testFlagFExistingE() throws Exception {
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
        // Do not run on devices with emulated external storage.
        if (Environment.isExternalStorageEmulated()) {
            return;
        }

        int iFlags = PackageManager.INSTALL_EXTERNAL;
        int rFlags = PackageManager.INSTALL_FORWARD_LOCK | PackageManager.INSTALL_REPLACE_EXISTING;
        // First install.
        installFromRawResource("install.apk", R.raw.install,
                iFlags,
                false,
                false, -1,
                -1);
        // Replace now
        installFromRawResource("install.apk", R.raw.install,
                rFlags,
                true,
                false, -1,
                -1);
    }

    /*
     * The following set of tests verify the installation of apps with
     * install location attribute set to internalOnly, preferExternal and auto.
     * The manifest option should dictate the install location.
     * public void testManifestI/E/A
     * TODO out of memory fall back behaviour.
     */
    @LargeTest
<<<<<<< HEAD
    public void testManifestI() {
=======
    public void testManifestI() throws Exception {
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
        installFromRawResource("install.apk", R.raw.install_loc_internal,
                0,
                true,
                false, -1,
                PackageInfo.INSTALL_LOCATION_INTERNAL_ONLY);
    }

    @LargeTest
<<<<<<< HEAD
    public void testManifestE() {
=======
    public void testManifestE() throws Exception {
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
        // Do not run on devices with emulated external storage.
        if (Environment.isExternalStorageEmulated()) {
            return;
        }

        installFromRawResource("install.apk", R.raw.install_loc_sdcard,
                0,
                true,
                false, -1,
                PackageInfo.INSTALL_LOCATION_PREFER_EXTERNAL);
    }

    @LargeTest
<<<<<<< HEAD
    public void testManifestA() {
=======
    public void testManifestA() throws Exception {
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
        installFromRawResource("install.apk", R.raw.install_loc_auto,
                0,
                true,
                false, -1,
                PackageInfo.INSTALL_LOCATION_INTERNAL_ONLY);
    }

    /*
     * The following set of tests verify the installation of apps
     * with install location attribute set to internalOnly, preferExternal and auto
     * for already existing apps. The manifest option should take precedence.
     * TODO add out of memory fall back behaviour.
     * testManifestI/E/AExistingI/E
     */
    @LargeTest
<<<<<<< HEAD
    public void testManifestIExistingI() {
=======
    public void testManifestIExistingI() throws Exception {
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
        int iFlags = PackageManager.INSTALL_INTERNAL;
        int rFlags = PackageManager.INSTALL_REPLACE_EXISTING;
        // First install.
        installFromRawResource("install.apk", R.raw.install,
                iFlags,
                false,
                false, -1,
                -1);
        // Replace now
        installFromRawResource("install.apk", R.raw.install_loc_internal,
                rFlags,
                true,
                false, -1,
                PackageInfo.INSTALL_LOCATION_INTERNAL_ONLY);
    }

    @LargeTest
<<<<<<< HEAD
    public void testManifestIExistingE() {
=======
    public void testManifestIExistingE() throws Exception {
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
        // Do not run on devices with emulated external storage.
        if (Environment.isExternalStorageEmulated()) {
            return;
        }

        int iFlags = PackageManager.INSTALL_EXTERNAL;
        int rFlags = PackageManager.INSTALL_REPLACE_EXISTING;
        // First install.
        installFromRawResource("install.apk", R.raw.install,
                iFlags,
                false,
                false, -1,
                -1);
        // Replace now
        installFromRawResource("install.apk", R.raw.install_loc_internal,
                rFlags,
                true,
                false, -1,
                PackageInfo.INSTALL_LOCATION_INTERNAL_ONLY);
    }

    @LargeTest
<<<<<<< HEAD
    public void testManifestEExistingI() {
=======
    public void testManifestEExistingI() throws Exception {
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
        // Do not run on devices with emulated external storage.
        if (Environment.isExternalStorageEmulated()) {
            return;
        }

        int iFlags = PackageManager.INSTALL_INTERNAL;
        int rFlags = PackageManager.INSTALL_REPLACE_EXISTING;
        // First install.
        installFromRawResource("install.apk", R.raw.install,
                iFlags,
                false,
                false, -1,
                -1);
        // Replace now
        installFromRawResource("install.apk", R.raw.install_loc_sdcard,
                rFlags,
                true,
                false, -1,
                PackageInfo.INSTALL_LOCATION_PREFER_EXTERNAL);
    }

    @LargeTest
<<<<<<< HEAD
    public void testManifestEExistingE() {
=======
    public void testManifestEExistingE() throws Exception {
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
        // Do not run on devices with emulated external storage.
        if (Environment.isExternalStorageEmulated()) {
            return;
        }

        int iFlags = PackageManager.INSTALL_EXTERNAL;
        int rFlags = PackageManager.INSTALL_REPLACE_EXISTING;
        // First install.
        installFromRawResource("install.apk", R.raw.install,
                iFlags,
                false,
                false, -1,
                -1);
        // Replace now
        installFromRawResource("install.apk", R.raw.install_loc_sdcard,
                rFlags,
                true,
                false, -1,
                PackageInfo.INSTALL_LOCATION_PREFER_EXTERNAL);
    }

    @LargeTest
<<<<<<< HEAD
    public void testManifestAExistingI() {
=======
    public void testManifestAExistingI() throws Exception {
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
        int iFlags = PackageManager.INSTALL_INTERNAL;
        int rFlags = PackageManager.INSTALL_REPLACE_EXISTING;
        // First install.
        installFromRawResource("install.apk", R.raw.install,
                iFlags,
                false,
                false, -1,
                -1);
        // Replace now
        installFromRawResource("install.apk", R.raw.install_loc_auto,
                rFlags,
                true,
                false, -1,
                PackageInfo.INSTALL_LOCATION_AUTO);
    }

    @LargeTest
<<<<<<< HEAD
    public void testManifestAExistingE() {
=======
    public void testManifestAExistingE() throws Exception {
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
        // Do not run on devices with emulated external storage.
        if (Environment.isExternalStorageEmulated()) {
            return;
        }

        int iFlags = PackageManager.INSTALL_EXTERNAL;
        int rFlags = PackageManager.INSTALL_REPLACE_EXISTING;
        // First install.
        installFromRawResource("install.apk", R.raw.install,
                iFlags,
                false,
                false, -1,
                -1);
        // Replace now
        installFromRawResource("install.apk", R.raw.install_loc_auto,
                rFlags,
                true,
                false, -1,
                PackageInfo.INSTALL_LOCATION_PREFER_EXTERNAL);
    }

<<<<<<< HEAD
   /*
    * The following set of tests check install location for existing
    * application based on user setting.
    */
   private int getExpectedInstallLocation(int userSetting) {
       int iloc = PackageInfo.INSTALL_LOCATION_UNSPECIFIED;
       boolean enable = getUserSettingSetInstallLocation();
       if (enable) {
           if (userSetting == PackageHelper.APP_INSTALL_AUTO) {
               iloc = PackageInfo.INSTALL_LOCATION_AUTO;
           } else if (userSetting == PackageHelper.APP_INSTALL_EXTERNAL) {
               iloc = PackageInfo.INSTALL_LOCATION_PREFER_EXTERNAL;
           } else if (userSetting == PackageHelper.APP_INSTALL_INTERNAL) {
               iloc = PackageInfo.INSTALL_LOCATION_INTERNAL_ONLY;
           }
       }
       return iloc;
   }
   private void setExistingXUserX(int userSetting, int iFlags, int iloc) {
       int rFlags = PackageManager.INSTALL_REPLACE_EXISTING;
       // First install.
       installFromRawResource("install.apk", R.raw.install,
               iFlags,
               false,
               false, -1,
               PackageInfo.INSTALL_LOCATION_UNSPECIFIED);
       int origSetting = getDefaultInstallLoc();
       try {
           // Set user setting
           setInstallLoc(userSetting);
           // Replace now
           installFromRawResource("install.apk", R.raw.install,
                   rFlags,
                   true,
                   false, -1,
                   iloc);
       } finally {
           setInstallLoc(origSetting);
       }
   }
   @LargeTest
   public void testExistingIUserI() {
       int userSetting = PackageHelper.APP_INSTALL_INTERNAL;
       int iFlags = PackageManager.INSTALL_INTERNAL;
       setExistingXUserX(userSetting, iFlags, PackageInfo.INSTALL_LOCATION_INTERNAL_ONLY);
   }

    @LargeTest
    public void testExistingIUserE() {
=======
    /*
     * The following set of tests check install location for existing
     * application based on user setting.
     */
    private int getExpectedInstallLocation(int userSetting) {
        int iloc = PackageInfo.INSTALL_LOCATION_UNSPECIFIED;
        boolean enable = getUserSettingSetInstallLocation();
        if (enable) {
            if (userSetting == PackageHelper.APP_INSTALL_AUTO) {
                iloc = PackageInfo.INSTALL_LOCATION_AUTO;
            } else if (userSetting == PackageHelper.APP_INSTALL_EXTERNAL) {
                iloc = PackageInfo.INSTALL_LOCATION_PREFER_EXTERNAL;
            } else if (userSetting == PackageHelper.APP_INSTALL_INTERNAL) {
                iloc = PackageInfo.INSTALL_LOCATION_INTERNAL_ONLY;
            }
        }
        return iloc;
    }

    private void setExistingXUserX(int userSetting, int iFlags, int iloc) throws Exception {
        int rFlags = PackageManager.INSTALL_REPLACE_EXISTING;
        // First install.
        installFromRawResource("install.apk", R.raw.install,
                iFlags,
                false,
                false, -1,
                PackageInfo.INSTALL_LOCATION_UNSPECIFIED);
        int origSetting = getDefaultInstallLoc();
        try {
            // Set user setting
            setInstallLoc(userSetting);
            // Replace now
            installFromRawResource("install.apk", R.raw.install,
                    rFlags,
                    true,
                    false, -1,
                    iloc);
        } finally {
            setInstallLoc(origSetting);
        }
    }
    @LargeTest
    public void testExistingIUserI() throws Exception {
        int userSetting = PackageHelper.APP_INSTALL_INTERNAL;
        int iFlags = PackageManager.INSTALL_INTERNAL;
        setExistingXUserX(userSetting, iFlags, PackageInfo.INSTALL_LOCATION_INTERNAL_ONLY);
    }

    @LargeTest
    public void testExistingIUserE() throws Exception {
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
        // Do not run on devices with emulated external storage.
        if (Environment.isExternalStorageEmulated()) {
            return;
        }

        int userSetting = PackageHelper.APP_INSTALL_EXTERNAL;
        int iFlags = PackageManager.INSTALL_INTERNAL;
        setExistingXUserX(userSetting, iFlags, PackageInfo.INSTALL_LOCATION_INTERNAL_ONLY);
    }

<<<<<<< HEAD
   @LargeTest
   public void testExistingIUserA() {
       int userSetting = PackageHelper.APP_INSTALL_AUTO;
       int iFlags = PackageManager.INSTALL_INTERNAL;
       setExistingXUserX(userSetting, iFlags, PackageInfo.INSTALL_LOCATION_INTERNAL_ONLY);
   }

    @LargeTest
    public void testExistingEUserI() {
=======
    @LargeTest
    public void testExistingIUserA() throws Exception {
        int userSetting = PackageHelper.APP_INSTALL_AUTO;
        int iFlags = PackageManager.INSTALL_INTERNAL;
        setExistingXUserX(userSetting, iFlags, PackageInfo.INSTALL_LOCATION_INTERNAL_ONLY);
    }

    @LargeTest
    public void testExistingEUserI() throws Exception {
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
        // Do not run on devices with emulated external storage.
        if (Environment.isExternalStorageEmulated()) {
            return;
        }

        int userSetting = PackageHelper.APP_INSTALL_INTERNAL;
        int iFlags = PackageManager.INSTALL_EXTERNAL;
        setExistingXUserX(userSetting, iFlags, PackageInfo.INSTALL_LOCATION_PREFER_EXTERNAL);
    }

    @LargeTest
<<<<<<< HEAD
    public void testExistingEUserE() {
=======
    public void testExistingEUserE() throws Exception {
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
        // Do not run on devices with emulated external storage.
        if (Environment.isExternalStorageEmulated()) {
            return;
        }

        int userSetting = PackageHelper.APP_INSTALL_EXTERNAL;
        int iFlags = PackageManager.INSTALL_EXTERNAL;
        setExistingXUserX(userSetting, iFlags, PackageInfo.INSTALL_LOCATION_PREFER_EXTERNAL);
    }

    @LargeTest
<<<<<<< HEAD
    public void testExistingEUserA() {
=======
    public void testExistingEUserA() throws Exception {
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
        // Do not run on devices with emulated external storage.
        if (Environment.isExternalStorageEmulated()) {
            return;
        }

        int userSetting = PackageHelper.APP_INSTALL_AUTO;
        int iFlags = PackageManager.INSTALL_EXTERNAL;
        setExistingXUserX(userSetting, iFlags, PackageInfo.INSTALL_LOCATION_PREFER_EXTERNAL);
    }

<<<<<<< HEAD
   /*
    * The following set of tests verify that the user setting defines
    * the install location.
    *
    */
   private boolean getUserSettingSetInstallLocation() {
       try {
           return Settings.System.getInt(mContext.getContentResolver(), Settings.Secure.SET_INSTALL_LOCATION) != 0;

       } catch (SettingNotFoundException e1) {
       }
       return false;
   }

   private void setUserSettingSetInstallLocation(boolean value) {
       Settings.System.putInt(mContext.getContentResolver(),
               Settings.Secure.SET_INSTALL_LOCATION, value ? 1 : 0);
   }
   private void setUserX(boolean enable, int userSetting, int iloc) {
       boolean origUserSetting = getUserSettingSetInstallLocation();
       int origSetting = getDefaultInstallLoc();
       try {
           setUserSettingSetInstallLocation(enable);
           // Set user setting
           setInstallLoc(userSetting);
           // Replace now
           installFromRawResource("install.apk", R.raw.install,
                   0,
                   true,
                   false, -1,
                   iloc);
       } finally {
           // Restore original setting
           setUserSettingSetInstallLocation(origUserSetting);
           setInstallLoc(origSetting);
       }
   }
   @LargeTest
   public void testUserI() {
       int userSetting = PackageHelper.APP_INSTALL_INTERNAL;
       int iloc = getExpectedInstallLocation(userSetting);
       setUserX(true, userSetting, iloc);
   }

    @LargeTest
    public void testUserE() {
=======
    /*
     * The following set of tests verify that the user setting defines
     * the install location.
     *
     */
    private boolean getUserSettingSetInstallLocation() {
        try {
            return Settings.Global.getInt(
                    mContext.getContentResolver(), Settings.Global.SET_INSTALL_LOCATION) != 0;
        } catch (SettingNotFoundException e1) {
        }
        return false;
    }

    private void setUserSettingSetInstallLocation(boolean value) {
        Settings.Global.putInt(mContext.getContentResolver(),
                Settings.Global.SET_INSTALL_LOCATION, value ? 1 : 0);
    }

    private void setUserX(boolean enable, int userSetting, int iloc) throws Exception {
        boolean origUserSetting = getUserSettingSetInstallLocation();
        int origSetting = getDefaultInstallLoc();
        try {
            setUserSettingSetInstallLocation(enable);
            // Set user setting
            setInstallLoc(userSetting);
            // Replace now
            installFromRawResource("install.apk", R.raw.install,
                    0,
                    true,
                    false, -1,
                    iloc);
        } finally {
            // Restore original setting
            setUserSettingSetInstallLocation(origUserSetting);
            setInstallLoc(origSetting);
        }
    }
    @LargeTest
    public void testUserI() throws Exception {
        int userSetting = PackageHelper.APP_INSTALL_INTERNAL;
        int iloc = getExpectedInstallLocation(userSetting);
        setUserX(true, userSetting, iloc);
    }

    @LargeTest
    public void testUserE() throws Exception {
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
        // Do not run on devices with emulated external storage.
        if (Environment.isExternalStorageEmulated()) {
            return;
        }

        int userSetting = PackageHelper.APP_INSTALL_EXTERNAL;
        int iloc = getExpectedInstallLocation(userSetting);
        setUserX(true, userSetting, iloc);
    }

<<<<<<< HEAD
   @LargeTest
   public void testUserA() {
       int userSetting = PackageHelper.APP_INSTALL_AUTO;
       int iloc = getExpectedInstallLocation(userSetting);
       setUserX(true, userSetting, iloc);
   }
   /*
    * The following set of tests turn on/off the basic
    * user setting for turning on install location.
    */
   @LargeTest
   public void testUserPrefOffUserI() {
       int userSetting = PackageHelper.APP_INSTALL_INTERNAL;
       int iloc = PackageInfo.INSTALL_LOCATION_UNSPECIFIED;
       setUserX(false, userSetting, iloc);
   }

    @LargeTest
    public void testUserPrefOffUserE() {
=======
    @LargeTest
    public void testUserA() throws Exception {
        int userSetting = PackageHelper.APP_INSTALL_AUTO;
        int iloc = getExpectedInstallLocation(userSetting);
        setUserX(true, userSetting, iloc);
    }

    /*
     * The following set of tests turn on/off the basic
     * user setting for turning on install location.
     */
    @LargeTest
    public void testUserPrefOffUserI() throws Exception {
        int userSetting = PackageHelper.APP_INSTALL_INTERNAL;
        int iloc = PackageInfo.INSTALL_LOCATION_UNSPECIFIED;
        setUserX(false, userSetting, iloc);
    }

    @LargeTest
    public void testUserPrefOffUserE() throws Exception {
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
        // Do not run on devices with emulated external storage.
        if (Environment.isExternalStorageEmulated()) {
            return;
        }

        int userSetting = PackageHelper.APP_INSTALL_EXTERNAL;
        int iloc = PackageInfo.INSTALL_LOCATION_UNSPECIFIED;
        setUserX(false, userSetting, iloc);
    }

<<<<<<< HEAD
   @LargeTest
   public void testUserPrefOffA() {
       int userSetting = PackageHelper.APP_INSTALL_AUTO;
       int iloc = PackageInfo.INSTALL_LOCATION_UNSPECIFIED;
       setUserX(false, userSetting, iloc);
   }
=======
    @LargeTest
    public void testUserPrefOffA() throws Exception {
        int userSetting = PackageHelper.APP_INSTALL_AUTO;
        int iloc = PackageInfo.INSTALL_LOCATION_UNSPECIFIED;
        setUserX(false, userSetting, iloc);
    }
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a

    static final String BASE_PERMISSIONS_DEFINED[] = new String[] {
        PERM_PACKAGE, "com.android.unit_tests.install_decl_perm",
        PERM_DEFINED,
        "com.android.frameworks.coretests.NORMAL",
        "com.android.frameworks.coretests.DANGEROUS",
        "com.android.frameworks.coretests.SIGNATURE",
    };

    static final String BASE_PERMISSIONS_UNDEFINED[] = new String[] {
        PERM_PACKAGE, "com.android.frameworks.coretests.install_decl_perm",
        PERM_UNDEFINED,
        "com.android.frameworks.coretests.NORMAL",
        "com.android.frameworks.coretests.DANGEROUS",
        "com.android.frameworks.coretests.SIGNATURE",
    };

    static final String BASE_PERMISSIONS_USED[] = new String[] {
        PERM_PACKAGE, "com.android.frameworks.coretests.install_use_perm_good",
        PERM_USED,
        "com.android.frameworks.coretests.NORMAL",
        "com.android.frameworks.coretests.DANGEROUS",
        "com.android.frameworks.coretests.SIGNATURE",
    };

    static final String BASE_PERMISSIONS_NOTUSED[] = new String[] {
        PERM_PACKAGE, "com.android.frameworks.coretests.install_use_perm_good",
        PERM_NOTUSED,
        "com.android.frameworks.coretests.NORMAL",
        "com.android.frameworks.coretests.DANGEROUS",
        "com.android.frameworks.coretests.SIGNATURE",
    };

    static final String BASE_PERMISSIONS_SIGUSED[] = new String[] {
        PERM_PACKAGE, "com.android.frameworks.coretests.install_use_perm_good",
        PERM_USED,
        "com.android.frameworks.coretests.SIGNATURE",
        PERM_NOTUSED,
        "com.android.frameworks.coretests.NORMAL",
        "com.android.frameworks.coretests.DANGEROUS",
    };

    /*
     * Ensure that permissions are properly declared.
     */
    @LargeTest
<<<<<<< HEAD
    public void testInstallDeclaresPermissions() {
=======
    public void testInstallDeclaresPermissions() throws Exception {
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
        InstallParams ip = null;
        InstallParams ip2 = null;
        try {
            // **: Upon installing a package, are its declared permissions published?

            int iFlags = PackageManager.INSTALL_INTERNAL;
            int iApk = R.raw.install_decl_perm;
            ip = installFromRawResource("install.apk", iApk,
                    iFlags, false,
                    false, -1, PackageInfo.INSTALL_LOCATION_INTERNAL_ONLY);
            assertInstall(ip.pkg, iFlags, ip.pkg.installLocation);
            assertPermissions(BASE_PERMISSIONS_DEFINED);

            // **: Upon installing package, are its permissions granted?

            int i2Flags = PackageManager.INSTALL_INTERNAL;
            int i2Apk = R.raw.install_use_perm_good;
            ip2 = installFromRawResource("install2.apk", i2Apk,
                    i2Flags, false,
                    false, -1, PackageInfo.INSTALL_LOCATION_INTERNAL_ONLY);
            assertInstall(ip2.pkg, i2Flags, ip2.pkg.installLocation);
            assertPermissions(BASE_PERMISSIONS_USED);

            // **: Upon removing but not deleting, are permissions retained?

            GenericReceiver receiver = new DeleteReceiver(ip.pkg.packageName);

            try {
<<<<<<< HEAD
                invokeDeletePackage(ip.pkg.packageName, PackageManager.DONT_DELETE_DATA, receiver);
=======
                invokeDeletePackage(ip.pkg.packageName, PackageManager.DELETE_KEEP_DATA, receiver);
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
            } catch (Exception e) {
                failStr(e);
            }
            assertPermissions(BASE_PERMISSIONS_DEFINED);
            assertPermissions(BASE_PERMISSIONS_USED);

            // **: Upon re-installing, are permissions retained?

            ip = installFromRawResource("install.apk", iApk,
                    iFlags | PackageManager.INSTALL_REPLACE_EXISTING, false,
                    false, -1, PackageInfo.INSTALL_LOCATION_INTERNAL_ONLY);
            assertInstall(ip.pkg, iFlags, ip.pkg.installLocation);
            assertPermissions(BASE_PERMISSIONS_DEFINED);
            assertPermissions(BASE_PERMISSIONS_USED);

            // **: Upon deleting package, are all permissions removed?

            try {
                invokeDeletePackage(ip.pkg.packageName, 0, receiver);
                ip = null;
            } catch (Exception e) {
                failStr(e);
            }
            assertPermissions(BASE_PERMISSIONS_UNDEFINED);
            assertPermissions(BASE_PERMISSIONS_NOTUSED);

            // **: Delete package using permissions; nothing to check here.

            GenericReceiver receiver2 = new DeleteReceiver(ip2.pkg.packageName);
            try {
                invokeDeletePackage(ip2.pkg.packageName, 0, receiver);
                ip2 = null;
            } catch (Exception e) {
                failStr(e);
            }

            // **: Re-install package using permissions; no permissions can be granted.

            ip2 = installFromRawResource("install2.apk", i2Apk,
                    i2Flags, false,
                    false, -1, PackageInfo.INSTALL_LOCATION_INTERNAL_ONLY);
            assertInstall(ip2.pkg, i2Flags, ip2.pkg.installLocation);
            assertPermissions(BASE_PERMISSIONS_NOTUSED);

            // **: Upon installing declaring package, are sig permissions granted
            // to other apps (but not other perms)?

            ip = installFromRawResource("install.apk", iApk,
                    iFlags, false,
                    false, -1, PackageInfo.INSTALL_LOCATION_INTERNAL_ONLY);
            assertInstall(ip.pkg, iFlags, ip.pkg.installLocation);
            assertPermissions(BASE_PERMISSIONS_DEFINED);
            assertPermissions(BASE_PERMISSIONS_SIGUSED);

            // **: Re-install package using permissions; are all permissions granted?

            ip2 = installFromRawResource("install2.apk", i2Apk,
                    i2Flags | PackageManager.INSTALL_REPLACE_EXISTING, false,
                    false, -1, PackageInfo.INSTALL_LOCATION_INTERNAL_ONLY);
            assertInstall(ip2.pkg, i2Flags, ip2.pkg.installLocation);
            assertPermissions(BASE_PERMISSIONS_NOTUSED);

            // **: Upon deleting package, are all permissions removed?

            try {
                invokeDeletePackage(ip.pkg.packageName, 0, receiver);
                ip = null;
            } catch (Exception e) {
                failStr(e);
            }
            assertPermissions(BASE_PERMISSIONS_UNDEFINED);
            assertPermissions(BASE_PERMISSIONS_NOTUSED);

            // **: Delete package using permissions; nothing to check here.

            try {
                invokeDeletePackage(ip2.pkg.packageName, 0, receiver);
                ip2 = null;
            } catch (Exception e) {
                failStr(e);
            }

        } finally {
            if (ip2 != null) {
                cleanUpInstall(ip2);
            }
            if (ip != null) {
                cleanUpInstall(ip);
            }
        }
    }

    /*
     * Ensure that permissions are properly declared.
     */
    @LargeTest
<<<<<<< HEAD
    public void testInstallOnSdPermissionsUnmount() {
=======
    public void testInstallOnSdPermissionsUnmount() throws Exception {
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
        InstallParams ip = null;
        boolean origMediaState = checkMediaState(Environment.MEDIA_MOUNTED);
        try {
            // **: Upon installing a package, are its declared permissions published?
            int iFlags = PackageManager.INSTALL_INTERNAL;
            int iApk = R.raw.install_decl_perm;
            ip = installFromRawResource("install.apk", iApk,
                    iFlags, false,
                    false, -1, PackageInfo.INSTALL_LOCATION_INTERNAL_ONLY);
            assertInstall(ip.pkg, iFlags, ip.pkg.installLocation);
            assertPermissions(BASE_PERMISSIONS_DEFINED);
            // Unmount media here
            assertTrue(unmountMedia());
            // Mount media again
            mountMedia();
            //Check permissions now
            assertPermissions(BASE_PERMISSIONS_DEFINED);
        } finally {
            if (ip != null) {
                cleanUpInstall(ip);
            }
        }
    }

    /* This test creates a stale container via MountService and then installs
     * a package and verifies that the stale container is cleaned up and install
     * is successful.
     * Please note that this test is very closely tied to the framework's
     * naming convention for secure containers.
     */
    @LargeTest
<<<<<<< HEAD
    public void testInstallSdcardStaleContainer() {
=======
    public void testInstallSdcardStaleContainer() throws Exception {
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
        // Do not run on devices with emulated external storage.
        if (Environment.isExternalStorageEmulated()) {
            return;
        }

        boolean origMediaState = checkMediaState(Environment.MEDIA_MOUNTED);
        try {
            // Mount media first
            mountMedia();
            String outFileName = "install.apk";
            int rawResId = R.raw.install;
            PackageManager pm = mContext.getPackageManager();
            File filesDir = mContext.getFilesDir();
            File outFile = new File(filesDir, outFileName);
            Uri packageURI = getInstallablePackage(rawResId, outFile);
            PackageParser.Package pkg = parsePackage(packageURI);
            assertNotNull(pkg);
            // Install an app on sdcard.
            installFromRawResource(outFileName, rawResId,
                    PackageManager.INSTALL_EXTERNAL, false,
                    false, -1, PackageInfo.INSTALL_LOCATION_UNSPECIFIED);
            // Unmount sdcard
            unmountMedia();
            // Delete the app on sdcard to leave a stale container on sdcard.
            GenericReceiver receiver = new DeleteReceiver(pkg.packageName);
            assertTrue(invokeDeletePackage(pkg.packageName, 0, receiver));
            mountMedia();
            // Reinstall the app and make sure it gets installed.
            installFromRawResource(outFileName, rawResId,
                    PackageManager.INSTALL_EXTERNAL, true,
                    false, -1, PackageInfo.INSTALL_LOCATION_UNSPECIFIED);
        } catch (Exception e) {
            failStr(e.getMessage());
        } finally {
            if (origMediaState) {
                mountMedia();
            } else {
                unmountMedia();
            }

        }
    }

    /* This test installs an application on sdcard and unmounts media.
     * The app is then re-installed on internal storage. The sdcard is mounted
     * and verified that the re-installation on internal storage takes precedence.
     */
    @LargeTest
<<<<<<< HEAD
    public void testInstallSdcardStaleContainerReinstall() {
=======
    public void testInstallSdcardStaleContainerReinstall() throws Exception {
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
        // Do not run on devices with emulated external storage.
        if (Environment.isExternalStorageEmulated()) {
            return;
        }

        boolean origMediaState = checkMediaState(Environment.MEDIA_MOUNTED);
        try {
            // Mount media first
            mountMedia();
            String outFileName = "install.apk";
            int rawResId = R.raw.install;
            PackageManager pm = mContext.getPackageManager();
            File filesDir = mContext.getFilesDir();
            File outFile = new File(filesDir, outFileName);
            Uri packageURI = getInstallablePackage(rawResId, outFile);
            PackageParser.Package pkg = parsePackage(packageURI);
            assertNotNull(pkg);
            // Install an app on sdcard.
            installFromRawResource(outFileName, rawResId,
                    PackageManager.INSTALL_EXTERNAL, false,
                    false, -1, PackageInfo.INSTALL_LOCATION_UNSPECIFIED);
            // Unmount sdcard
            unmountMedia();
            // Reinstall the app and make sure it gets installed on internal storage.
            installFromRawResource(outFileName, rawResId,
                    PackageManager.INSTALL_REPLACE_EXISTING, false,
                    false, -1, PackageInfo.INSTALL_LOCATION_UNSPECIFIED);
            mountMedia();
            // Verify that the app installed is on internal storage.
<<<<<<< HEAD
            assertInstall(pkg, 0, PackageInfo.INSTALL_LOCATION_INTERNAL_ONLY);            
=======
            assertInstall(pkg, 0, PackageInfo.INSTALL_LOCATION_INTERNAL_ONLY);
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
        } catch (Exception e) {
            failStr(e.getMessage());
        } finally {
            if (origMediaState) {
                mountMedia();
            } else {
                unmountMedia();
            }
        }
    }

    /*
     * The following series of tests are related to upgrading apps with
     * different certificates.
     */
    private int APP1_UNSIGNED = R.raw.install_app1_unsigned;
<<<<<<< HEAD
    private int APP1_CERT1 = R.raw.install_app1_cert1;
    private int APP1_CERT2 = R.raw.install_app1_cert2;
    private int APP1_CERT1_CERT2 = R.raw.install_app1_cert1_cert2;
    private int APP1_CERT3_CERT4 = R.raw.install_app1_cert3_cert4;
    private int APP1_CERT3 = R.raw.install_app1_cert3;
    private int APP2_UNSIGNED = R.raw.install_app2_unsigned;
    private int APP2_CERT1 = R.raw.install_app2_cert1;
    private int APP2_CERT2 = R.raw.install_app2_cert2;
    private int APP2_CERT1_CERT2 = R.raw.install_app2_cert1_cert2;
    private int APP2_CERT3 = R.raw.install_app2_cert3;

    private InstallParams replaceCerts(int apk1, int apk2, boolean cleanUp, boolean fail, int retCode) {
=======

    private int APP1_CERT1 = R.raw.install_app1_cert1;

    private int APP1_CERT2 = R.raw.install_app1_cert2;

    private int APP1_CERT1_CERT2 = R.raw.install_app1_cert1_cert2;

    private int APP1_CERT3_CERT4 = R.raw.install_app1_cert3_cert4;

    private int APP1_CERT3 = R.raw.install_app1_cert3;

    private int APP2_UNSIGNED = R.raw.install_app2_unsigned;

    private int APP2_CERT1 = R.raw.install_app2_cert1;

    private int APP2_CERT2 = R.raw.install_app2_cert2;

    private int APP2_CERT1_CERT2 = R.raw.install_app2_cert1_cert2;

    private int APP2_CERT3 = R.raw.install_app2_cert3;

    private InstallParams replaceCerts(int apk1, int apk2, boolean cleanUp, boolean fail,
            int retCode) throws Exception {
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
        int rFlags = PackageManager.INSTALL_REPLACE_EXISTING;
        String apk1Name = "install1.apk";
        String apk2Name = "install2.apk";
        PackageParser.Package pkg1 = getParsedPackage(apk1Name, apk1);
        try {
            InstallParams ip = installFromRawResource(apk1Name, apk1, 0, false,
                    false, -1, PackageInfo.INSTALL_LOCATION_UNSPECIFIED);
            installFromRawResource(apk2Name, apk2, rFlags, false,
                    fail, retCode, PackageInfo.INSTALL_LOCATION_UNSPECIFIED);
            return ip;
        } catch (Exception e) {
            failStr(e.getMessage());
        } finally {
            if (cleanUp) {
                cleanUpInstall(pkg1.packageName);
            }
        }
        return null;
    }
<<<<<<< HEAD
=======

>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
    /*
     * Test that an app signed with two certificates can be upgraded by the
     * same app signed with two certificates.
     */
    @LargeTest
<<<<<<< HEAD
    public void testReplaceMatchAllCerts() {
=======
    public void testReplaceMatchAllCerts() throws Exception {
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
        replaceCerts(APP1_CERT1_CERT2, APP1_CERT1_CERT2, true, false, -1);
    }

    /*
     * Test that an app signed with two certificates cannot be upgraded
     * by an app signed with a different certificate.
     */
    @LargeTest
<<<<<<< HEAD
    public void testReplaceMatchNoCerts1() {
        replaceCerts(APP1_CERT1_CERT2, APP1_CERT3, true, true,
                PackageManager.INSTALL_PARSE_FAILED_INCONSISTENT_CERTIFICATES);
    }
=======
    public void testReplaceMatchNoCerts1() throws Exception {
        replaceCerts(APP1_CERT1_CERT2, APP1_CERT3, true, true,
                PackageManager.INSTALL_PARSE_FAILED_INCONSISTENT_CERTIFICATES);
    }

>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
    /*
     * Test that an app signed with two certificates cannot be upgraded
     * by an app signed with a different certificate.
     */
    @LargeTest
<<<<<<< HEAD
    public void testReplaceMatchNoCerts2() {
        replaceCerts(APP1_CERT1_CERT2, APP1_CERT3_CERT4, true, true,
                PackageManager.INSTALL_PARSE_FAILED_INCONSISTENT_CERTIFICATES);
    }
=======
    public void testReplaceMatchNoCerts2() throws Exception {
        replaceCerts(APP1_CERT1_CERT2, APP1_CERT3_CERT4, true, true,
                PackageManager.INSTALL_PARSE_FAILED_INCONSISTENT_CERTIFICATES);
    }

>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
    /*
     * Test that an app signed with two certificates cannot be upgraded by
     * an app signed with a subset of initial certificates.
     */
    @LargeTest
<<<<<<< HEAD
    public void testReplaceMatchSomeCerts1() {
        replaceCerts(APP1_CERT1_CERT2, APP1_CERT1, true, true,
                PackageManager.INSTALL_PARSE_FAILED_INCONSISTENT_CERTIFICATES);
    }
=======
    public void testReplaceMatchSomeCerts1() throws Exception {
        replaceCerts(APP1_CERT1_CERT2, APP1_CERT1, true, true,
                PackageManager.INSTALL_PARSE_FAILED_INCONSISTENT_CERTIFICATES);
    }

>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
    /*
     * Test that an app signed with two certificates cannot be upgraded by
     * an app signed with the last certificate.
     */
    @LargeTest
<<<<<<< HEAD
    public void testReplaceMatchSomeCerts2() {
        replaceCerts(APP1_CERT1_CERT2, APP1_CERT2, true, true,
                PackageManager.INSTALL_PARSE_FAILED_INCONSISTENT_CERTIFICATES);
    }
=======
    public void testReplaceMatchSomeCerts2() throws Exception {
        replaceCerts(APP1_CERT1_CERT2, APP1_CERT2, true, true,
                PackageManager.INSTALL_PARSE_FAILED_INCONSISTENT_CERTIFICATES);
    }

>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
    /*
     * Test that an app signed with a certificate can be upgraded by app
     * signed with a superset of certificates.
     */
    @LargeTest
<<<<<<< HEAD
    public void testReplaceMatchMoreCerts() {
        replaceCerts(APP1_CERT1, APP1_CERT1_CERT2, true, true,
                PackageManager.INSTALL_PARSE_FAILED_INCONSISTENT_CERTIFICATES);
    }
=======
    public void testReplaceMatchMoreCerts() throws Exception {
        replaceCerts(APP1_CERT1, APP1_CERT1_CERT2, true, true,
                PackageManager.INSTALL_PARSE_FAILED_INCONSISTENT_CERTIFICATES);
    }

>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
    /*
     * Test that an app signed with a certificate can be upgraded by app
     * signed with a superset of certificates. Then verify that the an app
     * signed with the original set of certs cannot upgrade the new one.
     */
    @LargeTest
<<<<<<< HEAD
    public void testReplaceMatchMoreCertsReplaceSomeCerts() {
=======
    public void testReplaceMatchMoreCertsReplaceSomeCerts() throws Exception {
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
        InstallParams ip = replaceCerts(APP1_CERT1, APP1_CERT1_CERT2, false, true,
                PackageManager.INSTALL_PARSE_FAILED_INCONSISTENT_CERTIFICATES);
        try {
            int rFlags = PackageManager.INSTALL_REPLACE_EXISTING;
            installFromRawResource("install.apk", APP1_CERT1, rFlags, false,
                    false, -1,
                    PackageInfo.INSTALL_LOCATION_UNSPECIFIED);
        } catch (Exception e) {
            failStr(e.getMessage());
        } finally {
            if (ip != null) {
                cleanUpInstall(ip);
            }
        }
    }
<<<<<<< HEAD
    /*
     * The following tests are related to testing the checkSignatures
     * api.
     */
    private void checkSignatures(int apk1, int apk2, int expMatchResult) {
        checkSharedSignatures(apk1, apk2, true, false, -1, expMatchResult);
    }
    @LargeTest
    public void testCheckSignaturesAllMatch() {
=======

    /**
     * The following tests are related to testing the checkSignatures api.
     */
    private void checkSignatures(int apk1, int apk2, int expMatchResult) throws Exception {
        checkSharedSignatures(apk1, apk2, true, false, -1, expMatchResult);
    }

    @LargeTest
    public void testCheckSignaturesAllMatch() throws Exception {
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
        int apk1 = APP1_CERT1_CERT2;
        int apk2 = APP2_CERT1_CERT2;
        checkSignatures(apk1, apk2, PackageManager.SIGNATURE_MATCH);
    }
<<<<<<< HEAD
    @LargeTest
    public void testCheckSignaturesNoMatch() {
=======

    @LargeTest
    public void testCheckSignaturesNoMatch() throws Exception {
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
        int apk1 = APP1_CERT1;
        int apk2 = APP2_CERT2;
        checkSignatures(apk1, apk2, PackageManager.SIGNATURE_NO_MATCH);
    }
<<<<<<< HEAD
    @LargeTest
    public void testCheckSignaturesSomeMatch1() {
=======

    @LargeTest
    public void testCheckSignaturesSomeMatch1() throws Exception {
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
        int apk1 = APP1_CERT1_CERT2;
        int apk2 = APP2_CERT1;
        checkSignatures(apk1, apk2, PackageManager.SIGNATURE_NO_MATCH);
    }
<<<<<<< HEAD
    @LargeTest
    public void testCheckSignaturesSomeMatch2() {
=======

    @LargeTest
    public void testCheckSignaturesSomeMatch2() throws Exception {
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
        int apk1 = APP1_CERT1_CERT2;
        int apk2 = APP2_CERT2;
        checkSignatures(apk1, apk2, PackageManager.SIGNATURE_NO_MATCH);
    }
<<<<<<< HEAD
    @LargeTest
    public void testCheckSignaturesMoreMatch() {
=======

    @LargeTest
    public void testCheckSignaturesMoreMatch() throws Exception {
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
        int apk1 = APP1_CERT1;
        int apk2 = APP2_CERT1_CERT2;
        checkSignatures(apk1, apk2, PackageManager.SIGNATURE_NO_MATCH);
    }
<<<<<<< HEAD
    @LargeTest
    public void testCheckSignaturesUnknown() {
=======

    @LargeTest
    public void testCheckSignaturesUnknown() throws Exception {
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
        int apk1 = APP1_CERT1_CERT2;
        int apk2 = APP2_CERT1_CERT2;
        String apk1Name = "install1.apk";
        String apk2Name = "install2.apk";
        InstallParams ip1 = null;

        try {
            ip1 = installFromRawResource(apk1Name, apk1, 0, false,
                    false, -1, PackageInfo.INSTALL_LOCATION_UNSPECIFIED);
            PackageManager pm = mContext.getPackageManager();
            // Delete app2
            File filesDir = mContext.getFilesDir();
            File outFile = new File(filesDir, apk2Name);
            int rawResId = apk2;
            Uri packageURI = getInstallablePackage(rawResId, outFile);
            PackageParser.Package pkg = parsePackage(packageURI);
<<<<<<< HEAD
            getPm().deletePackage(pkg.packageName, null, 0);
=======
            getPm().deletePackage(pkg.packageName, null, PackageManager.DELETE_ALL_USERS);
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
            // Check signatures now
            int match = mContext.getPackageManager().checkSignatures(
                    ip1.pkg.packageName, pkg.packageName);
            assertEquals(PackageManager.SIGNATURE_UNKNOWN_PACKAGE, match);
        } finally {
            if (ip1 != null) {
                cleanUpInstall(ip1);
            }
        }
    }
<<<<<<< HEAD
    @LargeTest
    public void testInstallNoCertificates() {
=======

    @LargeTest
    public void testInstallNoCertificates() throws Exception {
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
        int apk1 = APP1_UNSIGNED;
        String apk1Name = "install1.apk";
        InstallParams ip1 = null;

        try {
            installFromRawResource(apk1Name, apk1, 0, false,
                    true, PackageManager.INSTALL_PARSE_FAILED_NO_CERTIFICATES,
                    PackageInfo.INSTALL_LOCATION_UNSPECIFIED);
        } finally {
        }
    }
<<<<<<< HEAD
    /* The following tests are related to apps using shared uids signed
     * with different certs.
     */
    private int SHARED1_UNSIGNED = R.raw.install_shared1_unsigned;
    private int SHARED1_CERT1 = R.raw.install_shared1_cert1;
    private int SHARED1_CERT2 = R.raw.install_shared1_cert2;
    private int SHARED1_CERT1_CERT2 = R.raw.install_shared1_cert1_cert2;
    private int SHARED2_UNSIGNED = R.raw.install_shared2_unsigned;
    private int SHARED2_CERT1 = R.raw.install_shared2_cert1;
    private int SHARED2_CERT2 = R.raw.install_shared2_cert2;
    private int SHARED2_CERT1_CERT2 = R.raw.install_shared2_cert1_cert2;
    private void checkSharedSignatures(int apk1, int apk2, boolean cleanUp, boolean fail, int retCode, int expMatchResult) {
=======

    /*
     * The following tests are related to apps using shared uids signed with
     * different certs.
     */
    private int SHARED1_UNSIGNED = R.raw.install_shared1_unsigned;

    private int SHARED1_CERT1 = R.raw.install_shared1_cert1;

    private int SHARED1_CERT2 = R.raw.install_shared1_cert2;

    private int SHARED1_CERT1_CERT2 = R.raw.install_shared1_cert1_cert2;

    private int SHARED2_UNSIGNED = R.raw.install_shared2_unsigned;

    private int SHARED2_CERT1 = R.raw.install_shared2_cert1;

    private int SHARED2_CERT2 = R.raw.install_shared2_cert2;

    private int SHARED2_CERT1_CERT2 = R.raw.install_shared2_cert1_cert2;

    private void checkSharedSignatures(int apk1, int apk2, boolean cleanUp, boolean fail,
            int retCode, int expMatchResult) throws Exception {
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
        String apk1Name = "install1.apk";
        String apk2Name = "install2.apk";
        PackageParser.Package pkg1 = getParsedPackage(apk1Name, apk1);
        PackageParser.Package pkg2 = getParsedPackage(apk2Name, apk2);

        try {
            // Clean up before testing first.
            cleanUpInstall(pkg1.packageName);
            cleanUpInstall(pkg2.packageName);
<<<<<<< HEAD
            installFromRawResource(apk1Name, apk1, 0, false,
                    false, -1, PackageInfo.INSTALL_LOCATION_UNSPECIFIED);
            if (fail) {
                installFromRawResource(apk2Name, apk2, 0, false,
                        true, retCode, PackageInfo.INSTALL_LOCATION_UNSPECIFIED);
            } else {
                installFromRawResource(apk2Name, apk2, 0, false,
                        false, -1, PackageInfo.INSTALL_LOCATION_UNSPECIFIED);
                int match = mContext.getPackageManager().checkSignatures(
                        pkg1.packageName, pkg2.packageName);
=======
            installFromRawResource(apk1Name, apk1, 0, false, false, -1,
                    PackageInfo.INSTALL_LOCATION_UNSPECIFIED);
            if (fail) {
                installFromRawResource(apk2Name, apk2, 0, false, true, retCode,
                        PackageInfo.INSTALL_LOCATION_UNSPECIFIED);
            } else {
                installFromRawResource(apk2Name, apk2, 0, false, false, -1,
                        PackageInfo.INSTALL_LOCATION_UNSPECIFIED);
                int match = mContext.getPackageManager().checkSignatures(pkg1.packageName,
                        pkg2.packageName);
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
                assertEquals(expMatchResult, match);
            }
        } finally {
            if (cleanUp) {
                cleanUpInstall(pkg1.packageName);
                cleanUpInstall(pkg2.packageName);
            }
        }
    }
<<<<<<< HEAD
    @LargeTest
    public void testCheckSignaturesSharedAllMatch() {
=======

    @LargeTest
    public void testCheckSignaturesSharedAllMatch() throws Exception {
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
        int apk1 = SHARED1_CERT1_CERT2;
        int apk2 = SHARED2_CERT1_CERT2;
        boolean fail = false;
        int retCode = -1;
        int expMatchResult = PackageManager.SIGNATURE_MATCH;
        checkSharedSignatures(apk1, apk2, true, fail, retCode, expMatchResult);
    }
<<<<<<< HEAD
    @LargeTest
    public void testCheckSignaturesSharedNoMatch() {
=======

    @LargeTest
    public void testCheckSignaturesSharedNoMatch() throws Exception {
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
        int apk1 = SHARED1_CERT1;
        int apk2 = SHARED2_CERT2;
        boolean fail = true;
        int retCode = PackageManager.INSTALL_FAILED_SHARED_USER_INCOMPATIBLE;
        int expMatchResult = -1;
        checkSharedSignatures(apk1, apk2, true, fail, retCode, expMatchResult);
    }
<<<<<<< HEAD
    /*
     * Test that an app signed with cert1 and cert2 cannot be replaced when signed with cert1 alone.
     */
    @LargeTest
    public void testCheckSignaturesSharedSomeMatch1() {
=======

    /*
     * Test that an app signed with cert1 and cert2 cannot be replaced when
     * signed with cert1 alone.
     */
    @LargeTest
    public void testCheckSignaturesSharedSomeMatch1() throws Exception {
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
        int apk1 = SHARED1_CERT1_CERT2;
        int apk2 = SHARED2_CERT1;
        boolean fail = true;
        int retCode = PackageManager.INSTALL_FAILED_SHARED_USER_INCOMPATIBLE;
        int expMatchResult = -1;
        checkSharedSignatures(apk1, apk2, true, fail, retCode, expMatchResult);
    }
<<<<<<< HEAD
    /*
     * Test that an app signed with cert1 and cert2 cannot be replaced when signed with cert2 alone.
     */
    @LargeTest
    public void testCheckSignaturesSharedSomeMatch2() {
=======

    /*
     * Test that an app signed with cert1 and cert2 cannot be replaced when
     * signed with cert2 alone.
     */
    @LargeTest
    public void testCheckSignaturesSharedSomeMatch2() throws Exception {
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
        int apk1 = SHARED1_CERT1_CERT2;
        int apk2 = SHARED2_CERT2;
        boolean fail = true;
        int retCode = PackageManager.INSTALL_FAILED_SHARED_USER_INCOMPATIBLE;
        int expMatchResult = -1;
        checkSharedSignatures(apk1, apk2, true, fail, retCode, expMatchResult);
    }
<<<<<<< HEAD
    @LargeTest
    public void testCheckSignaturesSharedUnknown() {
=======

    @LargeTest
    public void testCheckSignaturesSharedUnknown() throws Exception {
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
        int apk1 = SHARED1_CERT1_CERT2;
        int apk2 = SHARED2_CERT1_CERT2;
        String apk1Name = "install1.apk";
        String apk2Name = "install2.apk";
        InstallParams ip1 = null;

        try {
            ip1 = installFromRawResource(apk1Name, apk1, 0, false,
                    false, -1, PackageInfo.INSTALL_LOCATION_UNSPECIFIED);
            PackageManager pm = mContext.getPackageManager();
            // Delete app2
            PackageParser.Package pkg = getParsedPackage(apk2Name, apk2);
<<<<<<< HEAD
            getPm().deletePackage(pkg.packageName, null, 0);
=======
            getPm().deletePackage(pkg.packageName, null, PackageManager.DELETE_ALL_USERS);
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
            // Check signatures now
            int match = mContext.getPackageManager().checkSignatures(
                    ip1.pkg.packageName, pkg.packageName);
            assertEquals(PackageManager.SIGNATURE_UNKNOWN_PACKAGE, match);
        } finally {
            if (ip1 != null) {
                cleanUpInstall(ip1);
            }
        }
    }

    @LargeTest
<<<<<<< HEAD
    public void testReplaceFirstSharedMatchAllCerts() {
=======
    public void testReplaceFirstSharedMatchAllCerts() throws Exception {
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
        int apk1 = SHARED1_CERT1;
        int apk2 = SHARED2_CERT1;
        int rapk1 = SHARED1_CERT1;
        checkSignatures(apk1, apk2, PackageManager.SIGNATURE_MATCH);
        replaceCerts(apk1, rapk1, true, false, -1);
    }
<<<<<<< HEAD
    @LargeTest
    public void testReplaceSecondSharedMatchAllCerts() {
=======

    @LargeTest
    public void testReplaceSecondSharedMatchAllCerts() throws Exception {
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
        int apk1 = SHARED1_CERT1;
        int apk2 = SHARED2_CERT1;
        int rapk2 = SHARED2_CERT1;
        checkSignatures(apk1, apk2, PackageManager.SIGNATURE_MATCH);
        replaceCerts(apk2, rapk2, true, false, -1);
    }
<<<<<<< HEAD
    @LargeTest
    public void testReplaceFirstSharedMatchSomeCerts() {
=======

    @LargeTest
    public void testReplaceFirstSharedMatchSomeCerts() throws Exception {
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
        int apk1 = SHARED1_CERT1_CERT2;
        int apk2 = SHARED2_CERT1_CERT2;
        int rapk1 = SHARED1_CERT1;
        boolean fail = true;
        int retCode = PackageManager.INSTALL_PARSE_FAILED_INCONSISTENT_CERTIFICATES;
        checkSharedSignatures(apk1, apk2, false, false, -1, PackageManager.SIGNATURE_MATCH);
        installFromRawResource("install.apk", rapk1, PackageManager.INSTALL_REPLACE_EXISTING, true,
                fail, retCode, PackageInfo.INSTALL_LOCATION_UNSPECIFIED);
    }
<<<<<<< HEAD
    @LargeTest
    public void testReplaceSecondSharedMatchSomeCerts() {
=======

    @LargeTest
    public void testReplaceSecondSharedMatchSomeCerts() throws Exception {
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
        int apk1 = SHARED1_CERT1_CERT2;
        int apk2 = SHARED2_CERT1_CERT2;
        int rapk2 = SHARED2_CERT1;
        boolean fail = true;
        int retCode = PackageManager.INSTALL_PARSE_FAILED_INCONSISTENT_CERTIFICATES;
        checkSharedSignatures(apk1, apk2, false, false, -1, PackageManager.SIGNATURE_MATCH);
        installFromRawResource("install.apk", rapk2, PackageManager.INSTALL_REPLACE_EXISTING, true,
                fail, retCode, PackageInfo.INSTALL_LOCATION_UNSPECIFIED);
    }
<<<<<<< HEAD
    @LargeTest
    public void testReplaceFirstSharedMatchNoCerts() {
=======

    @LargeTest
    public void testReplaceFirstSharedMatchNoCerts() throws Exception {
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
        int apk1 = SHARED1_CERT1;
        int apk2 = SHARED2_CERT1;
        int rapk1 = SHARED1_CERT2;
        boolean fail = true;
        int retCode = PackageManager.INSTALL_PARSE_FAILED_INCONSISTENT_CERTIFICATES;
        checkSharedSignatures(apk1, apk2, false, false, -1, PackageManager.SIGNATURE_MATCH);
        installFromRawResource("install.apk", rapk1, PackageManager.INSTALL_REPLACE_EXISTING, true,
                fail, retCode, PackageInfo.INSTALL_LOCATION_UNSPECIFIED);
    }
<<<<<<< HEAD
    @LargeTest
    public void testReplaceSecondSharedMatchNoCerts() {
=======

    @LargeTest
    public void testReplaceSecondSharedMatchNoCerts() throws Exception {
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
        int apk1 = SHARED1_CERT1;
        int apk2 = SHARED2_CERT1;
        int rapk2 = SHARED2_CERT2;
        boolean fail = true;
        int retCode = PackageManager.INSTALL_PARSE_FAILED_INCONSISTENT_CERTIFICATES;
        checkSharedSignatures(apk1, apk2, false, false, -1, PackageManager.SIGNATURE_MATCH);
        installFromRawResource("install.apk", rapk2, PackageManager.INSTALL_REPLACE_EXISTING, true,
                fail, retCode, PackageInfo.INSTALL_LOCATION_UNSPECIFIED);
    }
<<<<<<< HEAD
    @LargeTest
    public void testReplaceFirstSharedMatchMoreCerts() {
=======

    @LargeTest
    public void testReplaceFirstSharedMatchMoreCerts() throws Exception {
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
        int apk1 = SHARED1_CERT1;
        int apk2 = SHARED2_CERT1;
        int rapk1 = SHARED1_CERT1_CERT2;
        boolean fail = true;
        int retCode = PackageManager.INSTALL_PARSE_FAILED_INCONSISTENT_CERTIFICATES;
        checkSharedSignatures(apk1, apk2, false, false, -1, PackageManager.SIGNATURE_MATCH);
        installFromRawResource("install.apk", rapk1, PackageManager.INSTALL_REPLACE_EXISTING, true,
                fail, retCode, PackageInfo.INSTALL_LOCATION_UNSPECIFIED);
    }
<<<<<<< HEAD
    @LargeTest
    public void testReplaceSecondSharedMatchMoreCerts() {
=======

    @LargeTest
    public void testReplaceSecondSharedMatchMoreCerts() throws Exception {
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
        int apk1 = SHARED1_CERT1;
        int apk2 = SHARED2_CERT1;
        int rapk2 = SHARED2_CERT1_CERT2;
        boolean fail = true;
        int retCode = PackageManager.INSTALL_PARSE_FAILED_INCONSISTENT_CERTIFICATES;
        checkSharedSignatures(apk1, apk2, false, false, -1, PackageManager.SIGNATURE_MATCH);
        installFromRawResource("install.apk", rapk2, PackageManager.INSTALL_REPLACE_EXISTING, true,
                fail, retCode, PackageInfo.INSTALL_LOCATION_UNSPECIFIED);
    }

    /**
     * Unknown features should be allowed to install. This prevents older phones
     * from rejecting new packages that specify features that didn't exist when
     * an older phone existed. All older phones are assumed to have those
     * features.
     * <p>
     * Right now we allow all packages to be installed regardless of their
     * features.
     */
    @LargeTest
<<<<<<< HEAD
    public void testUsesFeatureUnknownFeature() {
=======
    public void testUsesFeatureUnknownFeature() throws Exception {
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
        int retCode = PackageManager.INSTALL_SUCCEEDED;
        installFromRawResource("install.apk", R.raw.install_uses_feature, 0, true, false, retCode,
                PackageInfo.INSTALL_LOCATION_UNSPECIFIED);
    }

    @LargeTest
<<<<<<< HEAD
    public void testInstallNonexistentFile() {
=======
    public void testInstallNonexistentFile() throws Exception {
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
        int retCode = PackageManager.INSTALL_FAILED_INVALID_URI;
        File invalidFile = new File("/nonexistent-file.apk");
        invokeInstallPackageFail(Uri.fromFile(invalidFile), 0, retCode);
    }

    @SmallTest
<<<<<<< HEAD
    public void testGetVerifierDeviceIdentity() {
=======
    public void testGetVerifierDeviceIdentity() throws Exception {
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
        PackageManager pm = getPm();
        VerifierDeviceIdentity id = pm.getVerifierDeviceIdentity();

        assertNotNull("Verifier device identity should not be null", id);
    }

<<<<<<< HEAD
=======
    public void testGetInstalledPackages() throws Exception {
        List<PackageInfo> packages = getPm().getInstalledPackages(0);
        assertNotNull("installed packages cannot be null", packages);
        assertTrue("installed packages cannot be empty", packages.size() > 0);
    }

    public void testGetUnInstalledPackages() throws Exception {
        List<PackageInfo> packages = getPm().getInstalledPackages(
                PackageManager.GET_UNINSTALLED_PACKAGES);
        assertNotNull("installed packages cannot be null", packages);
        assertTrue("installed packages cannot be empty", packages.size() > 0);
    }

    /**
     * Test that getInstalledPackages returns all the data specified in flags.
     */
    public void testGetInstalledPackagesAll() throws Exception {
        int flags = PackageManager.GET_ACTIVITIES | PackageManager.GET_GIDS
                | PackageManager.GET_CONFIGURATIONS | PackageManager.GET_INSTRUMENTATION
                | PackageManager.GET_PERMISSIONS | PackageManager.GET_PROVIDERS
                | PackageManager.GET_RECEIVERS | PackageManager.GET_SERVICES
                | PackageManager.GET_SIGNATURES | PackageManager.GET_UNINSTALLED_PACKAGES;

        List<PackageInfo> packages = getPm().getInstalledPackages(flags);
        assertNotNull("installed packages cannot be null", packages);
        assertTrue("installed packages cannot be empty", packages.size() > 0);

        PackageInfo packageInfo = null;

        // Find the package with all components specified in the AndroidManifest
        // to ensure no null values
        for (PackageInfo pi : packages) {
            if ("com.android.frameworks.coretests.install_complete_package_info"
                    .equals(pi.packageName)) {
                packageInfo = pi;
                break;
            }
        }
        assertNotNull("activities should not be null", packageInfo.activities);
        assertNotNull("configPreferences should not be null", packageInfo.configPreferences);
        assertNotNull("instrumentation should not be null", packageInfo.instrumentation);
        assertNotNull("permissions should not be null", packageInfo.permissions);
        assertNotNull("providers should not be null", packageInfo.providers);
        assertNotNull("receivers should not be null", packageInfo.receivers);
        assertNotNull("services should not be null", packageInfo.services);
        assertNotNull("signatures should not be null", packageInfo.signatures);
    }

    /**
     * Test that getInstalledPackages returns all the data specified in
     * flags when the GET_UNINSTALLED_PACKAGES flag is set.
     */
    public void testGetUnInstalledPackagesAll() throws Exception {
        int flags = PackageManager.GET_UNINSTALLED_PACKAGES
                | PackageManager.GET_ACTIVITIES | PackageManager.GET_GIDS
                | PackageManager.GET_CONFIGURATIONS | PackageManager.GET_INSTRUMENTATION
                | PackageManager.GET_PERMISSIONS | PackageManager.GET_PROVIDERS
                | PackageManager.GET_RECEIVERS | PackageManager.GET_SERVICES
                | PackageManager.GET_SIGNATURES | PackageManager.GET_UNINSTALLED_PACKAGES;

        List<PackageInfo> packages = getPm().getInstalledPackages(flags);
        assertNotNull("installed packages cannot be null", packages);
        assertTrue("installed packages cannot be empty", packages.size() > 0);

        PackageInfo packageInfo = null;

        // Find the package with all components specified in the AndroidManifest
        // to ensure no null values
        for (PackageInfo pi : packages) {
            if ("com.android.frameworks.coretests.install_complete_package_info"
                    .equals(pi.packageName)) {
                packageInfo = pi;
                break;
            }
        }
        assertNotNull("activities should not be null", packageInfo.activities);
        assertNotNull("configPreferences should not be null", packageInfo.configPreferences);
        assertNotNull("instrumentation should not be null", packageInfo.instrumentation);
        assertNotNull("permissions should not be null", packageInfo.permissions);
        assertNotNull("providers should not be null", packageInfo.providers);
        assertNotNull("receivers should not be null", packageInfo.receivers);
        assertNotNull("services should not be null", packageInfo.services);
        assertNotNull("signatures should not be null", packageInfo.signatures);
    }

    public void testInstall_BadDex_CleanUp() throws Exception {
        int retCode = PackageManager.INSTALL_FAILED_DEXOPT;
        installFromRawResource("install.apk", R.raw.install_bad_dex, 0, true, true, retCode,
                PackageInfo.INSTALL_LOCATION_UNSPECIFIED);
    }

>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
    /*---------- Recommended install location tests ----*/
    /*
     * TODO's
     * check version numbers for upgrades
     * check permissions of installed packages
     * how to do tests on updated system apps?
     * verify updates to system apps cannot be installed on the sdcard.
     */
}
