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

package android.os;

<<<<<<< HEAD
/**
 * Retrieve overall information about the space on a filesystem.  This is a
 * Wrapper for Unix statfs().
 */
public class StatFs {
    /**
     * Construct a new StatFs for looking at the stats of the
     * filesystem at <var>path</var>.  Upon construction, the stat of
     * the file system will be performed, and the values retrieved available
     * from the methods on this class.
     * 
     * @param path A path in the desired file system to state.
     */
    public StatFs(String path) { native_setup(path); }
    
    /**
     * Perform a restat of the file system referenced by this object.  This
     * is the same as re-constructing the object with the same file system
     * path, and the new stat values are available upon return.
     */
    public void restat(String path) { native_restat(path); }

    @Override
    protected void finalize() { native_finalize(); }

    /**
     * The size, in bytes, of a block on the file system.  This corresponds
     * to the Unix statfs.f_bsize field.
     */
    public native int getBlockSize();

    /**
     * The total number of blocks on the file system.  This corresponds
     * to the Unix statfs.f_blocks field.
     */
    public native int getBlockCount();

    /**
     * The total number of blocks that are free on the file system, including
     * reserved blocks (that are not available to normal applications).  This
     * corresponds to the Unix statfs.f_bfree field.  Most applications will
     * want to use {@link #getAvailableBlocks()} instead.
     */
    public native int getFreeBlocks();

    /**
     * The number of blocks that are free on the file system and available to
     * applications.  This corresponds to the Unix statfs.f_bavail field.
     */
    public native int getAvailableBlocks();    
    
    private int mNativeContext;
    private native void native_restat(String path);
    private native void native_setup(String path);
    private native void native_finalize();
=======
import libcore.io.ErrnoException;
import libcore.io.Libcore;
import libcore.io.StructStatFs;

/**
 * Retrieve overall information about the space on a filesystem. This is a
 * wrapper for Unix statfs().
 */
public class StatFs {
    private StructStatFs mStat;

    /**
     * Construct a new StatFs for looking at the stats of the filesystem at
     * {@code path}. Upon construction, the stat of the file system will be
     * performed, and the values retrieved available from the methods on this
     * class.
     *
     * @param path path in the desired file system to stat.
     */
    public StatFs(String path) {
        mStat = doStat(path);
    }

    private static StructStatFs doStat(String path) {
        try {
            return Libcore.os.statfs(path);
        } catch (ErrnoException e) {
            throw new IllegalArgumentException("Invalid path: " + path, e);
        }
    }

    /**
     * Perform a restat of the file system referenced by this object. This is
     * the same as re-constructing the object with the same file system path,
     * and the new stat values are available upon return.
     */
    public void restat(String path) {
        mStat = doStat(path);
    }

    /**
     * The size, in bytes, of a block on the file system. This corresponds to
     * the Unix {@code statfs.f_bsize} field.
     */
    public int getBlockSize() {
        return (int) mStat.f_bsize;
    }

    /**
     * The total number of blocks on the file system. This corresponds to the
     * Unix {@code statfs.f_blocks} field.
     */
    public int getBlockCount() {
        return (int) mStat.f_blocks;
    }

    /**
     * The total number of blocks that are free on the file system, including
     * reserved blocks (that are not available to normal applications). This
     * corresponds to the Unix {@code statfs.f_bfree} field. Most applications
     * will want to use {@link #getAvailableBlocks()} instead.
     */
    public int getFreeBlocks() {
        return (int) mStat.f_bfree;
    }

    /**
     * The number of blocks that are free on the file system and available to
     * applications. This corresponds to the Unix {@code statfs.f_bavail} field.
     */
    public int getAvailableBlocks() {
        return (int) mStat.f_bavail;
    }
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
}
