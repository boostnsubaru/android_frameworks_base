/*
 * Copyright (C) 2009 The Android Open Source Project
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

package com.android.internal.view;

import android.content.res.Configuration;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.os.RemoteException;
import android.view.DragEvent;
import android.view.IWindow;
import android.view.IWindowSession;

public class BaseIWindow extends IWindow.Stub {
    private IWindowSession mSession;
    public int mSeq;
<<<<<<< HEAD
    
    public void setSession(IWindowSession session) {
        mSession = session;
    }
    
    public void resized(int w, int h, Rect contentInsets,
=======

    public void setSession(IWindowSession session) {
        mSession = session;
    }

    @Override
    public void resized(Rect frame, Rect contentInsets,
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
            Rect visibleInsets, boolean reportDraw, Configuration newConfig) {
        if (reportDraw) {
            try {
                mSession.finishDrawing(this);
            } catch (RemoteException e) {
            }
        }
    }

<<<<<<< HEAD
    public void dispatchAppVisibility(boolean visible) {
    }

    public void dispatchGetNewSurface() {
    }

    public void dispatchScreenState(boolean on) {
    }

    public void windowFocusChanged(boolean hasFocus, boolean touchEnabled) {
    }

    public void executeCommand(String command, String parameters, ParcelFileDescriptor out) {
    }
    
    public void closeSystemDialogs(String reason) {
    }
    
=======
    @Override
    public void moved(int newX, int newY) {
    }

    @Override
    public void dispatchAppVisibility(boolean visible) {
    }

    @Override
    public void dispatchGetNewSurface() {
    }

    @Override
    public void dispatchScreenState(boolean on) {
    }

    @Override
    public void windowFocusChanged(boolean hasFocus, boolean touchEnabled) {
    }

    @Override
    public void executeCommand(String command, String parameters, ParcelFileDescriptor out) {
    }

    @Override
    public void closeSystemDialogs(String reason) {
    }

    @Override
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
    public void dispatchWallpaperOffsets(float x, float y, float xStep, float yStep, boolean sync) {
        if (sync) {
            try {
                mSession.wallpaperOffsetsComplete(asBinder());
            } catch (RemoteException e) {
            }
        }
    }

<<<<<<< HEAD
    public void dispatchDragEvent(DragEvent event) {
    }

=======
    @Override
    public void dispatchDragEvent(DragEvent event) {
    }

    @Override
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
    public void dispatchSystemUiVisibilityChanged(int seq, int globalUi,
            int localValue, int localChanges) {
        mSeq = seq;
    }

<<<<<<< HEAD
=======
    @Override
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
    public void dispatchWallpaperCommand(String action, int x, int y,
            int z, Bundle extras, boolean sync) {
        if (sync) {
            try {
                mSession.wallpaperCommandComplete(asBinder(), null);
            } catch (RemoteException e) {
            }
        }
    }

<<<<<<< HEAD
=======
    @Override
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
    public void doneAnimating() {
    }
}
