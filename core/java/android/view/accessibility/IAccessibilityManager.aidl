/* //device/java/android/android/app/INotificationManager.aidl
**
** Copyright 2009, The Android Open Source Project
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

package android.view.accessibility;

import android.accessibilityservice.AccessibilityServiceInfo;
import android.accessibilityservice.IAccessibilityServiceConnection;
import android.accessibilityservice.IAccessibilityServiceClient;
<<<<<<< HEAD
=======
import android.content.ComponentName;
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.accessibility.IAccessibilityInteractionConnection;
import android.view.accessibility.IAccessibilityManagerClient;
import android.view.IWindow;

/**
 * Interface implemented by the AccessibilityManagerService called by
 * the AccessibilityMasngers.
 *
 * @hide
 */
interface IAccessibilityManager {

<<<<<<< HEAD
    int addClient(IAccessibilityManagerClient client);

    boolean sendAccessibilityEvent(in AccessibilityEvent uiEvent);

    List<AccessibilityServiceInfo> getInstalledAccessibilityServiceList();

    List<AccessibilityServiceInfo> getEnabledAccessibilityServiceList(int feedbackType);

    void interrupt();

    int addAccessibilityInteractionConnection(IWindow windowToken,
        in IAccessibilityInteractionConnection connection);
=======
    int addClient(IAccessibilityManagerClient client, int userId);

    boolean sendAccessibilityEvent(in AccessibilityEvent uiEvent, int userId);

    List<AccessibilityServiceInfo> getInstalledAccessibilityServiceList(int userId);

    List<AccessibilityServiceInfo> getEnabledAccessibilityServiceList(int feedbackType, int userId);

    void interrupt(int userId);

    int addAccessibilityInteractionConnection(IWindow windowToken,
        in IAccessibilityInteractionConnection connection, int userId);
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a

    void removeAccessibilityInteractionConnection(IWindow windowToken);

    void registerUiTestAutomationService(IAccessibilityServiceClient client,
        in AccessibilityServiceInfo info);

    void unregisterUiTestAutomationService(IAccessibilityServiceClient client);
<<<<<<< HEAD
=======

    void temporaryEnableAccessibilityStateUntilKeyguardRemoved(in ComponentName service,
            boolean touchExplorationEnabled);
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
}
