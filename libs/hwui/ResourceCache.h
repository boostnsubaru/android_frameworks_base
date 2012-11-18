/*
 * Copyright (C) 2010 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

#ifndef ANDROID_HWUI_RESOURCE_CACHE_H
#define ANDROID_HWUI_RESOURCE_CACHE_H

#include <cutils/compiler.h>

#include <SkBitmap.h>
#include <SkiaColorFilter.h>
#include <SkiaShader.h>
#include <utils/KeyedVector.h>
<<<<<<< HEAD
=======
#include "Layer.h"
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a

namespace android {
namespace uirenderer {

/**
 * Type of Resource being cached
 */
enum ResourceType {
    kBitmap,
    kShader,
    kColorFilter,
    kPath,
<<<<<<< HEAD
=======
    kLayer
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
};

class ResourceReference {
public:

    ResourceReference() { refCount = 0; recycled = false; destroyed = false;}
    ResourceReference(ResourceType type) {
        refCount = 0; recycled = false; destroyed = false; resourceType = type;
    }

    int refCount;
    bool recycled;
    bool destroyed;
    ResourceType resourceType;
};

class ANDROID_API ResourceCache {
<<<<<<< HEAD
    KeyedVector<void *, ResourceReference *>* mCache;
public:
    ResourceCache();
    ~ResourceCache();
=======
public:
    ResourceCache();
    ~ResourceCache();

    /**
     * When using these two methods, make sure to only invoke the *Locked()
     * variants of increment/decrementRefcount(), recyle() and destructor()
     */
    void lock();
    void unlock();

>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
    void incrementRefcount(SkPath* resource);
    void incrementRefcount(SkBitmap* resource);
    void incrementRefcount(SkiaShader* resource);
    void incrementRefcount(SkiaColorFilter* resource);
<<<<<<< HEAD
    void incrementRefcount(const void* resource, ResourceType resourceType);
    void decrementRefcount(void* resource);
=======
    void incrementRefcount(Layer* resource);

    void incrementRefcountLocked(SkPath* resource);
    void incrementRefcountLocked(SkBitmap* resource);
    void incrementRefcountLocked(SkiaShader* resource);
    void incrementRefcountLocked(SkiaColorFilter* resource);
    void incrementRefcountLocked(Layer* resource);

>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
    void decrementRefcount(SkBitmap* resource);
    void decrementRefcount(SkPath* resource);
    void decrementRefcount(SkiaShader* resource);
    void decrementRefcount(SkiaColorFilter* resource);
<<<<<<< HEAD
    void recycle(SkBitmap* resource);
=======
    void decrementRefcount(Layer* resource);

    void decrementRefcountLocked(SkBitmap* resource);
    void decrementRefcountLocked(SkPath* resource);
    void decrementRefcountLocked(SkiaShader* resource);
    void decrementRefcountLocked(SkiaColorFilter* resource);
    void decrementRefcountLocked(Layer* resource);

>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
    void destructor(SkPath* resource);
    void destructor(SkBitmap* resource);
    void destructor(SkiaShader* resource);
    void destructor(SkiaColorFilter* resource);
<<<<<<< HEAD
private:
    void deleteResourceReference(void* resource, ResourceReference* ref);
    void incrementRefcount(void* resource, ResourceType resourceType);
=======

    void destructorLocked(SkPath* resource);
    void destructorLocked(SkBitmap* resource);
    void destructorLocked(SkiaShader* resource);
    void destructorLocked(SkiaColorFilter* resource);

    bool recycle(SkBitmap* resource);
    bool recycleLocked(SkBitmap* resource);

private:
    void deleteResourceReferenceLocked(void* resource, ResourceReference* ref);

    void incrementRefcount(void* resource, ResourceType resourceType);
    void incrementRefcountLocked(void* resource, ResourceType resourceType);

    void decrementRefcount(void* resource);
    void decrementRefcountLocked(void* resource);

>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
    void logCache();

    /**
     * Used to increment, decrement, and destroy. Incrementing is generally accessed on the UI
     * thread, but destroying resources may be called from the GC thread, the finalizer thread,
     * or a reference queue finalization thread.
     */
    mutable Mutex mLock;
<<<<<<< HEAD
=======

    KeyedVector<void*, ResourceReference*>* mCache;
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
};

}; // namespace uirenderer
}; // namespace android

#endif // ANDROID_HWUI_RESOURCE_CACHE_H
