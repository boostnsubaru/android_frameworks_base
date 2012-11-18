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

package com.android.tools.layoutlib.create;

import java.io.PrintWriter;
import java.io.StringWriter;

public class Log {

    private boolean mVerbose = false;

    public void setVerbose(boolean verbose) {
        mVerbose = verbose;
    }

    public void debug(String format, Object... args) {
        if (mVerbose) {
            info(format, args);
        }
    }

<<<<<<< HEAD
=======
    /** Similar to debug() but doesn't do a \n automatically. */
    public void debugNoln(String format, Object... args) {
        if (mVerbose) {
            String s = String.format(format, args);
            System.out.print(s);
        }
    }

>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
    public void info(String format, Object... args) {
        String s = String.format(format, args);
        outPrintln(s);
    }
<<<<<<< HEAD
    
=======

>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
    public void error(String format, Object... args) {
        String s = String.format(format, args);
        errPrintln(s);
    }

    public void exception(Throwable t, String format, Object... args) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        t.printStackTrace(pw);
        pw.flush();
        error(format + "\n" + sw.toString(), args);
    }
<<<<<<< HEAD
    
=======

>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
    /** for unit testing */
    protected void errPrintln(String msg) {
        System.err.println(msg);
    }
<<<<<<< HEAD
    
=======

>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
    /** for unit testing */
    protected void outPrintln(String msg) {
        System.out.println(msg);
    }
<<<<<<< HEAD
    
=======

>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
}
