/*
 * Copyright (C) 2008 The Android Open Source Project
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

import android.os.Parcel;
import android.os.Parcelable;
<<<<<<< HEAD
=======
import android.os.UserHandle;
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a

/**
 * implementation of PackageStats associated with a
 * application package.
 */
public class PackageStats implements Parcelable {
    /** Name of the package to which this stats applies. */
    public String packageName;

<<<<<<< HEAD
=======
    /** @hide */
    public int userHandle;

>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
    /** Size of the code (e.g., APK) */
    public long codeSize;

    /**
     * Size of the internal data size for the application. (e.g.,
     * /data/data/<app>)
     */
    public long dataSize;

    /** Size of cache used by the application. (e.g., /data/data/<app>/cache) */
    public long cacheSize;

    /**
     * Size of the secure container on external storage holding the
     * application's code.
     */
    public long externalCodeSize;

    /**
     * Size of the external data used by the application (e.g.,
     * <sdcard>/Android/data/<app>)
     */
    public long externalDataSize;

    /**
     * Size of the external cache used by the application (i.e., on the SD
     * card). If this is a subdirectory of the data directory, this size will be
     * subtracted out of the external data size.
     */
    public long externalCacheSize;

    /** Size of the external media size used by the application. */
    public long externalMediaSize;

    /** Size of the package's OBBs placed on external media. */
    public long externalObbSize;

    public static final Parcelable.Creator<PackageStats> CREATOR
            = new Parcelable.Creator<PackageStats>() {
        public PackageStats createFromParcel(Parcel in) {
            return new PackageStats(in);
        }

        public PackageStats[] newArray(int size) {
            return new PackageStats[size];
        }
    };

    public String toString() {
        final StringBuilder sb = new StringBuilder("PackageStats{");
        sb.append(Integer.toHexString(System.identityHashCode(this)));
<<<<<<< HEAD
        sb.append(" packageName=");
        sb.append(packageName);
        sb.append(",codeSize=");
        sb.append(codeSize);
        sb.append(",dataSize=");
        sb.append(dataSize);
        sb.append(",cacheSize=");
        sb.append(cacheSize);
        sb.append(",externalCodeSize=");
        sb.append(externalCodeSize);
        sb.append(",externalDataSize=");
        sb.append(externalDataSize);
        sb.append(",externalCacheSize=");
        sb.append(externalCacheSize);
        sb.append(",externalMediaSize=");
        sb.append(externalMediaSize);
        sb.append(",externalObbSize=");
        sb.append(externalObbSize);
=======
        sb.append(" ");
        sb.append(packageName);
        if (codeSize != 0) {
            sb.append(" code=");
            sb.append(codeSize);
        }
        if (dataSize != 0) {
            sb.append(" data=");
            sb.append(dataSize);
        }
        if (cacheSize != 0) {
            sb.append(" cache=");
            sb.append(cacheSize);
        }
        if (externalCodeSize != 0) {
            sb.append(" extCode=");
            sb.append(externalCodeSize);
        }
        if (externalDataSize != 0) {
            sb.append(" extData=");
            sb.append(externalDataSize);
        }
        if (externalCacheSize != 0) {
            sb.append(" extCache=");
            sb.append(externalCacheSize);
        }
        if (externalMediaSize != 0) {
            sb.append(" media=");
            sb.append(externalMediaSize);
        }
        if (externalObbSize != 0) {
            sb.append(" obb=");
            sb.append(externalObbSize);
        }
        sb.append("}");
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
        return sb.toString();
    }

    public PackageStats(String pkgName) {
        packageName = pkgName;
<<<<<<< HEAD
=======
        userHandle = UserHandle.myUserId();
    }

    /** @hide */
    public PackageStats(String pkgName, int userHandle) {
        this.packageName = pkgName;
        this.userHandle = userHandle;
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
    }

    public PackageStats(Parcel source) {
        packageName = source.readString();
<<<<<<< HEAD
=======
        userHandle = source.readInt();
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
        codeSize = source.readLong();
        dataSize = source.readLong();
        cacheSize = source.readLong();
        externalCodeSize = source.readLong();
        externalDataSize = source.readLong();
        externalCacheSize = source.readLong();
        externalMediaSize = source.readLong();
        externalObbSize = source.readLong();
    }

    public PackageStats(PackageStats pStats) {
        packageName = pStats.packageName;
<<<<<<< HEAD
=======
        userHandle = pStats.userHandle;
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
        codeSize = pStats.codeSize;
        dataSize = pStats.dataSize;
        cacheSize = pStats.cacheSize;
        externalCodeSize = pStats.externalCodeSize;
        externalDataSize = pStats.externalDataSize;
        externalCacheSize = pStats.externalCacheSize;
        externalMediaSize = pStats.externalMediaSize;
        externalObbSize = pStats.externalObbSize;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int parcelableFlags){
        dest.writeString(packageName);
<<<<<<< HEAD
=======
        dest.writeInt(userHandle);
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
        dest.writeLong(codeSize);
        dest.writeLong(dataSize);
        dest.writeLong(cacheSize);
        dest.writeLong(externalCodeSize);
        dest.writeLong(externalDataSize);
        dest.writeLong(externalCacheSize);
        dest.writeLong(externalMediaSize);
        dest.writeLong(externalObbSize);
    }
}
