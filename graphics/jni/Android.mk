LOCAL_PATH:= $(call my-dir)
include $(CLEAR_VARS)

LOCAL_SRC_FILES:= \
    android_renderscript_RenderScript.cpp

LOCAL_SHARED_LIBRARIES := \
        libandroid_runtime \
        libandroidfw \
        libnativehelper \
        libRS \
        libcutils \
        libskia \
        libutils \
        libui \
        libgui

LOCAL_STATIC_LIBRARIES :=

rs_generated_include_dir := $(call intermediates-dir-for,SHARED_LIBRARIES,libRS,,)

LOCAL_C_INCLUDES += \
	$(JNI_H_INCLUDE) \
	frameworks/rs \
	$(rs_generated_include_dir) \
	$(call include-path-for, corecg graphics)

LOCAL_CFLAGS +=

LOCAL_LDLIBS := -lpthread
LOCAL_ADDITIONAL_DEPENDENCIES := $(addprefix $(rs_generated_include_dir)/,rsgApiFuncDecl.h)
LOCAL_MODULE:= librs_jni
LOCAL_ADDITIONAL_DEPENDENCIES += $(rs_generated_source)
LOCAL_MODULE_TAGS := optional
<<<<<<< HEAD
LOCAL_REQUIRED_MODULES := libRS
=======
LOCAL_REQUIRED_MODULES := libRS libRSDriver
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a

include $(BUILD_SHARED_LIBRARY)
