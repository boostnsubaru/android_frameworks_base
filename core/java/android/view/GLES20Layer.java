/*
 * Copyright (C) 2011 The Android Open Source Project
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


package android.view;

import android.graphics.Bitmap;
<<<<<<< HEAD
=======
import android.graphics.Paint;
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a

/**
 * An OpenGL ES 2.0 implementation of {@link HardwareLayer}.
 */
abstract class GLES20Layer extends HardwareLayer {
    int mLayer;
    Finalizer mFinalizer;

    GLES20Layer() {
    }

    GLES20Layer(int width, int height, boolean opaque) {
        super(width, height, opaque);
    }

    /**
     * Returns the native layer object used to render this layer.
     * 
     * @return A pointer to the native layer object, or 0 if the object is NULL
     */
    public int getLayer() {
        return mLayer;
    }

    @Override
<<<<<<< HEAD
    boolean copyInto(Bitmap bitmap) {
        return GLES20Canvas.nCopyLayer(mLayer, bitmap.mNativeBitmap);
    }
    
    @Override
    void update(int width, int height, boolean isOpaque) {
        super.update(width, height, isOpaque);
=======
    void setLayerPaint(Paint paint) {
        if (paint != null) {
            GLES20Canvas.nSetLayerPaint(mLayer, paint.mNativePaint);
            GLES20Canvas.nSetLayerColorFilter(mLayer, paint.getColorFilter() != null ?
                    paint.getColorFilter().nativeColorFilter : 0);
        }
    }

    @Override
    boolean copyInto(Bitmap bitmap) {
        return GLES20Canvas.nCopyLayer(mLayer, bitmap.mNativeBitmap);
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
    }

    @Override
    void destroy() {
        if (mFinalizer != null) {
            mFinalizer.destroy();
            mFinalizer = null;
        }
        mLayer = 0;
    }
<<<<<<< HEAD
    
    @Override
    void flush() {
        if (mLayer != 0) {
            GLES20Canvas.nFlushLayer(mLayer);
        }
=======

    @Override
    void clearStorage() {
        if (mLayer != 0) GLES20Canvas.nClearLayerTexture(mLayer);
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
    }

    static class Finalizer {
        private int mLayerId;

        public Finalizer(int layerId) {
            mLayerId = layerId;
        }

        @Override
        protected void finalize() throws Throwable {
            try {
                if (mLayerId != 0) {
                    GLES20Canvas.nDestroyLayerDeferred(mLayerId);
                }
            } finally {
                super.finalize();
            }
        }

        void destroy() {
            GLES20Canvas.nDestroyLayer(mLayerId);
            mLayerId = 0;
        }
    }
}
