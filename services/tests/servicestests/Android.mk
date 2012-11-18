LOCAL_PATH:= $(call my-dir)
include $(CLEAR_VARS)

# We only want this apk build for tests.
LOCAL_MODULE_TAGS := tests

# Include all test java files.
LOCAL_SRC_FILES := $(call all-java-files-under, src)

LOCAL_STATIC_JAVA_LIBRARIES := \
    easymocklib \
<<<<<<< HEAD
    guava
=======
    guava \
    littlemock
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a

LOCAL_JAVA_LIBRARIES := android.test.runner services

LOCAL_PACKAGE_NAME := FrameworksServicesTests

LOCAL_CERTIFICATE := platform

include $(BUILD_PACKAGE)

