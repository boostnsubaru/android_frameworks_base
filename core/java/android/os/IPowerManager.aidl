/* //device/java/android/android/os/IPowerManager.aidl
**
** Copyright 2007, The Android Open Source Project
**
** Licensed under the Apache License, Version 2.0 (the "License"); 
** you may not use this file except in compliance with the License. 
** You may obtain a copy of the License at 
**
**     http://www.apache.org/licenses/LICENSE-2.0 
**
** Unless required by applicable law or agreed to in writing, software 
** distributed under the License is distributed on an "AS IS" BASIS, 
** WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
** See the License for the specific language governing permissions and 
** limitations under the License.
*/

package android.os;

import android.os.WorkSource;

/** @hide */

interface IPowerManager
{
<<<<<<< HEAD
    // WARNING: changes in acquireWakeLock() signature must be reflected in IPowerManager.cpp/h
    void acquireWakeLock(int flags, IBinder lock, String tag, in WorkSource ws);
    void updateWakeLockWorkSource(IBinder lock, in WorkSource ws);
    void goToSleep(long time);
    void goToSleepWithReason(long time, int reason);
    // WARNING: changes in releaseWakeLock() signature must be reflected in IPowerManager.cpp/h
    void releaseWakeLock(IBinder lock, int flags);
    void userActivity(long when, boolean noChangeLights);
    void userActivityWithForce(long when, boolean noChangeLights, boolean force);
    void clearUserActivityTimeout(long now, long timeout);
    void setPokeLock(int pokey, IBinder lock, String tag);
    int getSupportedWakeLockFlags();
    void setStayOnSetting(int val);
    void setMaximumScreenOffTimeount(int timeMs);
    void preventScreenOn(boolean prevent);
    boolean isScreenOn();
    void reboot(String reason);
    void crash(String message);

    // sets the brightness of the backlights (screen, keyboard, button) 0-255
    void setBacklightBrightness(int brightness);
    void setAttentionLight(boolean on, int color);
    void setAutoBrightnessAdjustment(float adj);

    // custom backlight things
    int getLightSensorValue();
    int getRawLightSensorValue();
    int getLightSensorScreenBrightness();
    int getLightSensorButtonBrightness();
    int getLightSensorKeyboardBrightness();

    void cpuBoost(int duration);
=======
    // WARNING: The first two methods must remain the first two methods because their
    // transaction numbers must not change unless IPowerManager.cpp is also updated.
    void acquireWakeLock(IBinder lock, int flags, String tag, in WorkSource ws);
    void releaseWakeLock(IBinder lock, int flags);

    void updateWakeLockWorkSource(IBinder lock, in WorkSource ws);
    boolean isWakeLockLevelSupported(int level);

    void userActivity(long time, int event, int flags);
    void wakeUp(long time);
    void goToSleep(long time, int reason);
    void nap(long time);

    boolean isScreenOn();
    void reboot(boolean confirm, String reason, boolean wait);
    void shutdown(boolean confirm, boolean wait);
    void crash(String message);

    void setStayOnSetting(int val);
    void setMaximumScreenOffTimeoutFromDeviceAdmin(int timeMs);

    // temporarily overrides the screen brightness settings to allow the user to
    // see the effect of a settings change without applying it immediately
    void setTemporaryScreenBrightnessSettingOverride(int brightness);
    void setTemporaryScreenAutoBrightnessAdjustmentSettingOverride(float adj);

    // sets the attention light (used by phone app only)
    void setAttentionLight(boolean on, int color);
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
}
