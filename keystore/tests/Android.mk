LOCAL_PATH:= $(call my-dir)
include $(CLEAR_VARS)

# We only want this apk build for tests.
LOCAL_MODULE_TAGS := tests
LOCAL_CERTIFICATE := platform

<<<<<<< HEAD
LOCAL_JAVA_LIBRARIES := android.test.runner
=======
LOCAL_JAVA_LIBRARIES := android.test.runner bouncycastle
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a

# Include all test java files.
LOCAL_SRC_FILES := $(call all-java-files-under, src)

LOCAL_PACKAGE_NAME := KeyStoreTests

include $(BUILD_PACKAGE)
