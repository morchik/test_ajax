# Copyright (C) 2010 The Android Open Source Project
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#      http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
#
LOCAL_PATH := $(call my-dir)

#include $(CLEAR_VARS)
#LIB_PATH := $(LOCAL_PATH)/lib
#LOCAL_MODULE := crypto
#LOCAL_SRC_FILES := openssl/lib/$(TARGET_ARCH)/libcrypto.so
#LOCAL_EXPORT_C_INCLUDES := openssl
#include $(PREBUILT_SHARED_LIBRARY)

#include $(CLEAR_VARS)
#LIB_PATH := $(LOCAL_PATH)/lib
#LOCAL_MODULE := ssl
#LOCAL_SRC_FILES := libssl.so
#LOCAL_EXPORT_C_INCLUDES := openssl
#include $(PREBUILT_SHARED_LIBRARY)

include $(CLEAR_VARS)
LOCAL_MODULE    := encodeFile
LOCAL_SRC_FILES := encodeFile.cpp
#LOCAL_SHARED_LIBRARIES := crypto
LOCAL_LDLIBS    := $(LOCAL_PATH)/openssl/lib/$(TARGET_ARCH)/libcrypto.so
LOCAL_C_INCLUDES := $(LOCAL_PATH)

#LOCAL_SHARED_LIBRARIES := libcrypto libssl
#LOCAL_STATIC_LIBRARIES := android_native_app_glue

include $(BUILD_SHARED_LIBRARY)
include $(call all-subdir-makefiles)

# $(call import-module,android/native_app_glue)
