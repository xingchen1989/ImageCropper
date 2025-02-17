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
 * HandleHelper class to handle vertical handles (i.e. left and right handles).
 */
class VerticalHandleHelper extends HandleHelper {

    // Constructor /////////////////////////////////////////////////////////////////////////////////

    VerticalHandleHelper(Edge edge) {
        super(null, edge);
    }

    // HandleHelper Methods ////////////////////////////////////////////////////////////////////////

    @Override
    void updateCropWindow(float x, float y, float targetAspectRatio, float snapRadius, @NonNull RectF imageRect) {
        // Adjust this Edge accordingly.
        mVerticalEdge.adjustCoordinate(x, y, imageRect, snapRadius, targetAspectRatio);

        float top = Edge.TOP.getCoordinate();
        float bottom = Edge.BOTTOM.getCoordinate();

        // After this Edge is moved, our crop window is now out of proportion.
        float targetHeight = AspectRatioUtil.calculateHeight(Edge.getWidth(), targetAspectRatio);

        // Adjust the crop window so that it maintains the given aspect ratio by
        // moving the adjacent edges symmetrically in or out.
        float difference = targetHeight - Edge.getHeight();
        float halfDifference = difference / 2;
        top -= halfDifference;
        bottom += halfDifference;

        Edge.TOP.setCoordinate(top);
        Edge.BOTTOM.setCoordinate(bottom);

        // Check if we have gone out of bounds on the top or bottom, and fix.
        if (Edge.TOP.isOutsideMargin(imageRect, snapRadius)
                && mVerticalEdge.isNewRectangleOutOfBounds(Edge.TOP, imageRect, targetAspectRatio)) {
            Edge.BOTTOM.offset(-Edge.TOP.snapToRect(imageRect));
            mVerticalEdge.adjustCoordinate(targetAspectRatio);
        }

        if (Edge.BOTTOM.isOutsideMargin(imageRect, snapRadius)
                && mVerticalEdge.isNewRectangleOutOfBounds(Edge.BOTTOM, imageRect, targetAspectRatio)) {
            Edge.TOP.offset(-Edge.BOTTOM.snapToRect(imageRect));
            mVerticalEdge.adjustCoordinate(targetAspectRatio);
        }
    }
}
