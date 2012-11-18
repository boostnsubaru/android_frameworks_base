# Copyright 2007-2008 The Android Open Source Project


LOCAL_PATH:= $(call my-dir)
include $(CLEAR_VARS)

LOCAL_MODULE_TAGS := optional

LOCAL_SRC_FILES := $(call all-java-files-under, src)

LOCAL_PACKAGE_NAME := WAPPushManager

<<<<<<< HEAD
=======
LOCAL_JAVA_LIBRARIES += telephony-common
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
LOCAL_STATIC_JAVA_LIBRARIES += android-common

LOCAL_PROGUARD_FLAGS := -include $(LOCAL_PATH)/proguard.flags

include $(BUILD_PACKAGE)

# This finds and builds the test apk as well, so a single make does both.
include $(call all-makefiles-under,$(LOCAL_PATH))
