LOCAL_PATH:= $(call my-dir)

# the library
# ============================================================
include $(CLEAR_VARS)

LOCAL_SRC_FILES := \
            $(call all-subdir-java-files) \
	    com/android/server/EventLogTags.logtags \
	    com/android/server/am/EventLogTags.logtags

LOCAL_MODULE:= services

<<<<<<< HEAD
LOCAL_JAVA_LIBRARIES := android.policy

LOCAL_NO_EMMA_INSTRUMENT := true
LOCAL_NO_EMMA_COMPILE := true
=======
LOCAL_JAVA_LIBRARIES := android.policy telephony-common
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a

include $(BUILD_JAVA_LIBRARY)

include $(BUILD_DROIDDOC)
