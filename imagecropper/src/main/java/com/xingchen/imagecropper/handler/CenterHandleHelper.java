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
 * HandleHelper class to handle the center handle.
 */
class CenterHandleHelper extends HandleHelper {

    // Constructor /////////////////////////////////////////////////////////////////////////////////
    CenterHandleHelper() {
        super(null, null);
    }

    // HandleHelper Methods ////////////////////////////////////////////////////////////////////////
    @Override
    void updateCropWindow(float x, float y, float snapRadius, @NonNull RectF imageRect) {
        float left = Edge.LEFT.getCoordinate();
        float top = Edge.TOP.getCoordinate();
        float right = Edge.RIGHT.getCoordinate();
        float bottom = Edge.BOTTOM.getCoordinate();

        float offsetX = x - (left + right) / 2;
        float offsetY = y - (top + bottom) / 2;

        // Adjust the crop window.
        Edge.LEFT.offset(offsetX);
        Edge.TOP.offset(offsetY);
        Edge.RIGHT.offset(offsetX);
        Edge.BOTTOM.offset(offsetY);

        // Check if we have gone out of bounds on the sides, and fix.
        if (Edge.LEFT.isOutsideMargin(imageRect, snapRadius)) {
            final float offset = Edge.LEFT.snapToRect(imageRect);
            Edge.RIGHT.offset(offset);
        } else if (Edge.RIGHT.isOutsideMargin(imageRect, snapRadius)) {
            final float offset = Edge.RIGHT.snapToRect(imageRect);
            Edge.LEFT.offset(offset);
        }

        // Check if we have gone out of bounds on the top or bottom, and fix.
        if (Edge.TOP.isOutsideMargin(imageRect, snapRadius)) {
            final float offset = Edge.TOP.snapToRect(imageRect);
            Edge.BOTTOM.offset(offset);
        } else if (Edge.BOTTOM.isOutsideMargin(imageRect, snapRadius)) {
            final float offset = Edge.BOTTOM.snapToRect(imageRect);
            Edge.TOP.offset(offset);
        }
    }

    @Override
    void updateCropWindow(float x, float y, float targetAspectRatio, float snapRadius, @NonNull RectF imageRect) {
        updateCropWindow(x, y, snapRadius, imageRect);
    }
}
