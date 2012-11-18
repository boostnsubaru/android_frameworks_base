LOCAL_PATH := $(call my-dir)
include $(CLEAR_VARS)

LOCAL_MODULE_TAGS := tests

LOCAL_SRC_FILES := $(call all-java-files-under, src)

LOCAL_SDK_VERSION := current

LOCAL_PACKAGE_NAME := com.android.overlaytest.overlay

<<<<<<< HEAD
LOCAL_AAPT_FLAGS := -o

=======
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
include $(BUILD_PACKAGE)
