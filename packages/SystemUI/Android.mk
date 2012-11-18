LOCAL_PATH:= $(call my-dir)
include $(CLEAR_VARS)

LOCAL_MODULE_TAGS := optional

LOCAL_SRC_FILES := $(call all-java-files-under, src) \
    ../../../ex/carousel/java/com/android/ex/carousel/carousel.rs

<<<<<<< HEAD
LOCAL_JAVA_LIBRARIES := services

LOCAL_STATIC_JAVA_LIBRARIES := android-common-carousel android-support-v4
=======
LOCAL_JAVA_LIBRARIES := services telephony-common

LOCAL_STATIC_JAVA_LIBRARIES := android-common-carousel
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a

LOCAL_PACKAGE_NAME := SystemUI
LOCAL_CERTIFICATE := platform

LOCAL_PROGUARD_FLAG_FILES := proguard.flags

include $(BUILD_PACKAGE)

include $(call all-makefiles-under,$(LOCAL_PATH))
