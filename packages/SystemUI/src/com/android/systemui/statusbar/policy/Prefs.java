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

package com.android.systemui.statusbar.policy;

import android.content.Context;
import android.content.SharedPreferences;

public class Prefs {
    private static final String SHARED_PREFS_NAME = "status_bar";

    // a boolean
    public static final String DO_NOT_DISTURB_PREF = "do_not_disturb";
    public static final boolean DO_NOT_DISTURB_DEFAULT = false;

    public static final String SHOWN_COMPAT_MODE_HELP = "shown_compat_mode_help";
<<<<<<< HEAD

    private static final String LAST_BATTERY_LEVEL = "last_battery_level";
=======
    public static final String SHOWN_QUICK_SETTINGS_HELP = "shown_quick_settings_help";
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a

    public static SharedPreferences read(Context context) {
        return context.getSharedPreferences(Prefs.SHARED_PREFS_NAME, Context.MODE_PRIVATE);
    }

    public static SharedPreferences.Editor edit(Context context) {
        return context.getSharedPreferences(Prefs.SHARED_PREFS_NAME, Context.MODE_PRIVATE).edit();
    }
<<<<<<< HEAD

    public static void setLastBatteryLevel(Context c, int level) {
        edit(c).putInt(LAST_BATTERY_LEVEL, level).commit();
    }

    public static int getLastBatteryLevel(Context c) {
        return read(c).getInt(LAST_BATTERY_LEVEL, 50);
    }
=======
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
}
