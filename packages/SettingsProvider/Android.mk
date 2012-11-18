LOCAL_PATH:= $(call my-dir)
include $(CLEAR_VARS)

LOCAL_MODULE_TAGS := optional

LOCAL_SRC_FILES := $(call all-subdir-java-files)

<<<<<<< HEAD
LOCAL_JAVA_LIBRARIES := 
=======
LOCAL_JAVA_LIBRARIES := telephony-common
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a

LOCAL_PACKAGE_NAME := SettingsProvider
LOCAL_CERTIFICATE := platform

include $(BUILD_PACKAGE)

########################
include $(call all-makefiles-under,$(LOCAL_PATH))
