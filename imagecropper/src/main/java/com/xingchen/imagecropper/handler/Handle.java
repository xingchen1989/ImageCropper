/*
 * Copyright 2013, Edmodo, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this work except in compliance with the License.
 * You may obtain a copy of the License in the LICENSE file, or at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */

package com.xingchen.imagecropper.handler;

import android.graphics.RectF;

import androidx.annotation.NonNull;

import com.xingchen.imagecropper.edge.Edge;

/**
 * Enum representing a pressable, draggable Handle on the crop window.
 */
public enum Handle {
    TOP_LEFT(new CornerHandleHelper(Edge.TOP, Edge.LEFT)),
    TOP_RIGHT(new CornerHandleHelper(Edge.TOP, Edge.RIGHT)),
    BOTTOM_LEFT(new CornerHandleHelper(Edge.BOTTOM, Edge.LEFT)),
    BOTTOM_RIGHT(new CornerHandleHelper(Edge.BOTTOM, Edge.RIGHT)),
    LEFT(new VerticalHandleHelper(Edge.LEFT)),
    TOP(new HorizontalHandleHelper(Edge.TOP)),
    RIGHT(new VerticalHandleHelper(Edge.RIGHT)),
    BOTTOM(new HorizontalHandleHelper(Edge.BOTTOM)),
    CENTER(new CenterHandleHelper());

    // Member Variables ////////////////////////////////////////////////////////////////////////////

    private final HandleHelper mHelper;

    // Constructors ////////////////////////////////////////////////////////////////////////////////

    Handle(HandleHelper helper) {
        mHelper = helper;
    }

    // Public Methods //////////////////////////////////////////////////////////

    public void updateCropWindow(float x, float y, float snapRadius, @NonNull RectF imageRect) {
        mHelper.updateCropWindow(x, y, snapRadius, imageRect);
    }

    public void updateCropWindow(float x, float y, float targetAspectRatio, float snapRadius, @NonNull RectF imageRect) {
        mHelper.updateCropWindow(x, y, targetAspectRatio, snapRadius, imageRect);
    }
}
