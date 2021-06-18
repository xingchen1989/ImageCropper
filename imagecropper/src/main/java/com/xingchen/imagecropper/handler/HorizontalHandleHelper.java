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
import com.xingchen.imagecropper.utils.AspectRatioUtil;

/**
 * Handle helper class to handle horizontal handles (i.e. top and bottom handles).
 */
class HorizontalHandleHelper extends HandleHelper {
    
    // Constructor /////////////////////////////////////////////////////////////////////////////////

    HorizontalHandleHelper(Edge edge) {
        super(edge, null);
    }

    // HandleHelper Methods ////////////////////////////////////////////////////////////////////////

    @Override
    void updateCropWindow(float x, float y, float targetAspectRatio, float snapRadius, @NonNull RectF imageRect) {
        // Adjust this Edge accordingly.
        mHorizontalEdge.adjustCoordinate(x, y, imageRect, snapRadius, targetAspectRatio);

        float left = Edge.LEFT.getCoordinate();
        float right = Edge.RIGHT.getCoordinate();

        // After this Edge is moved, our crop window is now out of proportion.
        float targetWidth = AspectRatioUtil.calculateWidth(Edge.getHeight(), targetAspectRatio);

        // Adjust the crop window so that it maintains the given aspect ratio by
        // moving the adjacent edges symmetrically in or out.
        float difference = targetWidth - Edge.getWidth();
        float halfDifference = difference / 2;
        left -= halfDifference;
        right += halfDifference;

        Edge.LEFT.setCoordinate(left);
        Edge.RIGHT.setCoordinate(right);

        // Check if we have gone out of bounds on the sides, and fix.
        if (Edge.LEFT.isOutsideMargin(imageRect, snapRadius)
                && mHorizontalEdge.isNewRectangleOutOfBounds(Edge.LEFT, imageRect, targetAspectRatio)) {
            Edge.RIGHT.offset(-Edge.LEFT.snapToRect(imageRect));
            mHorizontalEdge.adjustCoordinate(targetAspectRatio);
        }

        if (Edge.RIGHT.isOutsideMargin(imageRect, snapRadius)
                && mHorizontalEdge.isNewRectangleOutOfBounds(Edge.RIGHT, imageRect, targetAspectRatio)) {
            Edge.LEFT.offset(- Edge.RIGHT.snapToRect(imageRect));
            mHorizontalEdge.adjustCoordinate(targetAspectRatio);
        }
    }
}
