package com.xwh.lib.corelib.utils;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;

import com.xwh.lib.corelib.WHUtil;

public class DrawableUtils {

    public static Drawable getShape(int Radius, int fillColor) {
        return getShape(Radius, Radius, Radius, Radius, fillColor);
    }

    public static Drawable getShape(int topLeftRadius, int topRightRadius, int bottomRightRadius, int bottomLeftRadius, int fillColor) {
        Drawable shape = getShape(topLeftRadius, topRightRadius, bottomRightRadius, bottomLeftRadius, fillColor, 0, 0, null, null);
        return shape;
    }

    public static Drawable getShape(int Radius, int[] JBColors, GradientDrawable.Orientation type) {
        return getShape(Radius, 0, 0, 0, JBColors, type);
    }

    public static Drawable getShape(int[] JBColors, GradientDrawable.Orientation type) {
        return getShape(0, 0, 0, 0, JBColors, type);
    }

    public static Drawable getStrokeShape(int strokeWidth, int storkeColor) {
        return getShape(0, 0, strokeWidth, storkeColor, null, null);
    }
  public static Drawable getStrokeShape(int radio,int strokeWidth, int storkeColor) {

        return getShape(radio, 0, strokeWidth, storkeColor, null, null);
    }

    public static Drawable getShape(int radio, int fillColor, int strokeWidth, int storkeColor, int[] JBColors, GradientDrawable.Orientation type) {
        return getShape(radio, radio, radio, radio, fillColor, strokeWidth, storkeColor, JBColors, type);
    }

    public static Drawable getShape(int topLeftRadius, int topRightRadius, int bottomRightRadius, int bottomLeftRadius, int fillColor, int strokeWidth, int storkeColor, int[] JBColors, GradientDrawable.Orientation type) {
        topLeftRadius = DeviceUtils.dip2px(topLeftRadius);
        topRightRadius = DeviceUtils.dip2px(topRightRadius);
        bottomLeftRadius = DeviceUtils.dip2px(bottomLeftRadius);
        bottomRightRadius = DeviceUtils.dip2px(bottomRightRadius);

        GradientDrawable gd;//创建drawable

        if (JBColors == null || JBColors.length == 0) {
            gd = new GradientDrawable();//创建drawable
        } else {
            gd = new GradientDrawable(type, JBColors);//创建drawable
        }

        if (fillColor != 0) {
            gd.setColor(fillColor);
        }
        if (strokeWidth > 0) {
            gd.setStroke(strokeWidth, storkeColor);

        }
        if (topLeftRadius + topRightRadius + bottomLeftRadius + bottomRightRadius > 0) {
            gd.setCornerRadii(new float[]{topLeftRadius,
                    topLeftRadius, topRightRadius, topRightRadius,
                    bottomRightRadius, bottomRightRadius, bottomLeftRadius,
                    bottomLeftRadius});
        }


        return gd;
    }


}
