package com.xwh.lib.corelib;

import android.graphics.Paint;
import android.graphics.Rect;
import android.widget.TextView;

import com.xwh.lib.corelib.utils.TimeUtils;

import java.text.DecimalFormat;

public class TextTool {
    /**
     * 获取文字的宽度
     *
     * @param paint
     * @param text
     * @return
     */
    public static int getTextWidth(Paint paint, String text) {
        return (int) paint.measureText(text);
    }

    /**
     * 获取文字的高度
     *
     * @param paint
     * @param text
     * @return
     */
    public static int getTextHeight(Paint paint, String text) {
        Rect rect = new Rect();
        paint.getTextBounds(text, 0, text.length(), rect);
        return rect.height();
    }


    public static String getFormatFloatString(Object message) {
        try {
            DecimalFormat decimalFormat = new DecimalFormat("#.0");
            return decimalFormat.format(message);
        } catch (Exception e) {
        }
        return message + "";
    }

    public static String getFormatFloatString(Object message, String format) {
        DecimalFormat decimalFormat = new DecimalFormat(format);
        return decimalFormat.format(message);
    }

    public static void setText(TextView view, String name) {
        if (view == null) return;
        view.setText(TimeUtils.replaceMonth(name));
    }
}
