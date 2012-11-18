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

#define LOG_TAG "OpenGLRenderer"

<<<<<<< HEAD
#include <GLES2/gl2.h>

#include <SkCanvas.h>
#include <SkGradientShader.h>

#include <utils/threads.h>

=======
#include <utils/threads.h>

#include "Caches.h"
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
#include "Debug.h"
#include "GradientCache.h"
#include "Properties.h"

namespace android {
namespace uirenderer {

///////////////////////////////////////////////////////////////////////////////
<<<<<<< HEAD
=======
// Defines
///////////////////////////////////////////////////////////////////////////////

#define GRADIENT_TEXTURE_HEIGHT 2
#define GRADIENT_BYTES_PER_PIXEL 4

///////////////////////////////////////////////////////////////////////////////
// Functions
///////////////////////////////////////////////////////////////////////////////

template<typename T>
static inline T min(T a, T b) {
    return a < b ? a : b;
}

///////////////////////////////////////////////////////////////////////////////
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
// Constructors/destructor
///////////////////////////////////////////////////////////////////////////////

GradientCache::GradientCache():
        mCache(GenerationCache<GradientCacheEntry, Texture*>::kUnlimitedCapacity),
        mSize(0), mMaxSize(MB(DEFAULT_GRADIENT_CACHE_SIZE)) {
    char property[PROPERTY_VALUE_MAX];
    if (property_get(PROPERTY_GRADIENT_CACHE_SIZE, property, NULL) > 0) {
        INIT_LOGD("  Setting gradient cache size to %sMB", property);
        setMaxSize(MB(atof(property)));
    } else {
        INIT_LOGD("  Using default gradient cache size of %.2fMB", DEFAULT_GRADIENT_CACHE_SIZE);
    }

<<<<<<< HEAD
=======
    glGetIntegerv(GL_MAX_TEXTURE_SIZE, &mMaxTextureSize);

>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
    mCache.setOnEntryRemovedListener(this);
}

GradientCache::GradientCache(uint32_t maxByteSize):
        mCache(GenerationCache<GradientCacheEntry, Texture*>::kUnlimitedCapacity),
        mSize(0), mMaxSize(maxByteSize) {
    mCache.setOnEntryRemovedListener(this);
}

GradientCache::~GradientCache() {
    mCache.clear();
}

///////////////////////////////////////////////////////////////////////////////
// Size management
///////////////////////////////////////////////////////////////////////////////

uint32_t GradientCache::getSize() {
    return mSize;
}

uint32_t GradientCache::getMaxSize() {
    return mMaxSize;
}

void GradientCache::setMaxSize(uint32_t maxSize) {
    mMaxSize = maxSize;
    while (mSize > mMaxSize) {
        mCache.removeOldest();
    }
}

///////////////////////////////////////////////////////////////////////////////
// Callbacks
///////////////////////////////////////////////////////////////////////////////

void GradientCache::operator()(GradientCacheEntry& shader, Texture*& texture) {
    if (texture) {
<<<<<<< HEAD
        const uint32_t size = texture->width * texture->height * 4;
=======
        const uint32_t size = texture->width * texture->height * GRADIENT_BYTES_PER_PIXEL;
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
        mSize -= size;
    }

    if (texture) {
        glDeleteTextures(1, &texture->id);
        delete texture;
    }
}

///////////////////////////////////////////////////////////////////////////////
// Caching
///////////////////////////////////////////////////////////////////////////////

<<<<<<< HEAD
Texture* GradientCache::get(uint32_t* colors, float* positions,
        int count, SkShader::TileMode tileMode) {

    GradientCacheEntry gradient(colors, positions, count, tileMode);
    Texture* texture = mCache.get(gradient);

    if (!texture) {
        texture = addLinearGradient(gradient, colors, positions, count, tileMode);
=======
Texture* GradientCache::get(uint32_t* colors, float* positions, int count) {

    GradientCacheEntry gradient(colors, positions, count);
    Texture* texture = mCache.get(gradient);

    if (!texture) {
        texture = addLinearGradient(gradient, colors, positions, count);
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
    }

    return texture;
}

void GradientCache::clear() {
    mCache.clear();
}

<<<<<<< HEAD
Texture* GradientCache::addLinearGradient(GradientCacheEntry& gradient,
        uint32_t* colors, float* positions, int count, SkShader::TileMode tileMode) {
    SkBitmap bitmap;
    bitmap.setConfig(SkBitmap::kARGB_8888_Config, 1024, 1);
    bitmap.allocPixels();
    bitmap.eraseColor(0);

    SkCanvas canvas(bitmap);

    SkPoint points[2];
    points[0].set(0.0f, 0.0f);
    points[1].set(bitmap.width(), 0.0f);

    SkShader* localShader = SkGradientShader::CreateLinear(points,
            reinterpret_cast<const SkColor*>(colors), positions, count, tileMode);

    SkPaint p;
    p.setStyle(SkPaint::kStrokeAndFill_Style);
    p.setShader(localShader)->unref();

    canvas.drawRectCoords(0.0f, 0.0f, bitmap.width(), 1.0f, p);

    // Asume the cache is always big enough
    const uint32_t size = bitmap.rowBytes() * bitmap.height();
=======
void GradientCache::getGradientInfo(const uint32_t* colors, const int count,
        GradientInfo& info) {
    uint32_t width = 256 * (count - 1);

    if (!Caches::getInstance().extensions.hasNPot()) {
        width = 1 << (31 - __builtin_clz(width));
    }

    bool hasAlpha = false;
    for (int i = 0; i < count; i++) {
        if (((colors[i] >> 24) & 0xff) < 255) {
            hasAlpha = true;
            break;
        }
    }

    info.width = min(width, uint32_t(mMaxTextureSize));
    info.hasAlpha = hasAlpha;
}

Texture* GradientCache::addLinearGradient(GradientCacheEntry& gradient,
        uint32_t* colors, float* positions, int count) {

    GradientInfo info;
    getGradientInfo(colors, count, info);

    Texture* texture = new Texture;
    texture->width = info.width;
    texture->height = GRADIENT_TEXTURE_HEIGHT;
    texture->blend = info.hasAlpha;
    texture->generation = 1;

    // Asume the cache is always big enough
    const uint32_t size = texture->width * texture->height * GRADIENT_BYTES_PER_PIXEL;
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
    while (mSize + size > mMaxSize) {
        mCache.removeOldest();
    }

<<<<<<< HEAD
    Texture* texture = new Texture;
    generateTexture(&bitmap, texture);
=======
    generateTexture(colors, positions, count, texture);
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a

    mSize += size;
    mCache.put(gradient, texture);

    return texture;
}

<<<<<<< HEAD
void GradientCache::generateTexture(SkBitmap* bitmap, Texture* texture) {
    SkAutoLockPixels autoLock(*bitmap);
    if (!bitmap->readyToDraw()) {
        ALOGE("Cannot generate texture from shader");
        return;
    }

    texture->generation = bitmap->getGenerationID();
    texture->width = bitmap->width();
    texture->height = bitmap->height();
=======
void GradientCache::generateTexture(uint32_t* colors, float* positions,
        int count, Texture* texture) {

    const uint32_t width = texture->width;
    const GLsizei rowBytes = width * GRADIENT_BYTES_PER_PIXEL;
    uint32_t pixels[width * texture->height];

    int currentPos = 1;

    float startA = (colors[0] >> 24) & 0xff;
    float startR = (colors[0] >> 16) & 0xff;
    float startG = (colors[0] >>  8) & 0xff;
    float startB = (colors[0] >>  0) & 0xff;

    float endA = (colors[1] >> 24) & 0xff;
    float endR = (colors[1] >> 16) & 0xff;
    float endG = (colors[1] >>  8) & 0xff;
    float endB = (colors[1] >>  0) & 0xff;

    float start = positions[0];
    float distance = positions[1] - start;

    uint8_t* p = (uint8_t*) pixels;
    for (uint32_t x = 0; x < width; x++) {
        float pos = x / float(width - 1);
        if (pos > positions[currentPos]) {
            startA = endA;
            startR = endR;
            startG = endG;
            startB = endB;
            start = positions[currentPos];

            currentPos++;

            endA = (colors[currentPos] >> 24) & 0xff;
            endR = (colors[currentPos] >> 16) & 0xff;
            endG = (colors[currentPos] >>  8) & 0xff;
            endB = (colors[currentPos] >>  0) & 0xff;
            distance = positions[currentPos] - start;
        }

        float amount = (pos - start) / distance;
        float oppAmount = 1.0f - amount;

        const float alpha = startA * oppAmount + endA * amount;
        const float a = alpha / 255.0f;
        *p++ = uint8_t(a * (startR * oppAmount + endR * amount));
        *p++ = uint8_t(a * (startG * oppAmount + endG * amount));
        *p++ = uint8_t(a * (startB * oppAmount + endB * amount));
        *p++ = uint8_t(alpha);
    }

    for (int i = 1; i < GRADIENT_TEXTURE_HEIGHT; i++) {
        memcpy(pixels + width * i, pixels, rowBytes);
    }
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a

    glGenTextures(1, &texture->id);

    glBindTexture(GL_TEXTURE_2D, texture->id);
<<<<<<< HEAD
    glPixelStorei(GL_UNPACK_ALIGNMENT, bitmap->bytesPerPixel());

    texture->blend = !bitmap->isOpaque();
    glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, bitmap->rowBytesAsPixels(), texture->height, 0,
            GL_RGBA, GL_UNSIGNED_BYTE, bitmap->getPixels());
=======
    glPixelStorei(GL_UNPACK_ALIGNMENT, GRADIENT_BYTES_PER_PIXEL);

    glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, texture->height, 0,
            GL_RGBA, GL_UNSIGNED_BYTE, pixels);
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a

    texture->setFilter(GL_LINEAR);
    texture->setWrap(GL_CLAMP_TO_EDGE);
}

}; // namespace uirenderer
}; // namespace android
