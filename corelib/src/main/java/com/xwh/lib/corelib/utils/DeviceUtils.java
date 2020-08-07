package com.xwh.lib.corelib.utils;

import android.content.Context;
import android.graphics.Point;
import android.provider.Settings;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;

import androidx.annotation.NonNull;

import com.xwh.lib.corelib.WHUtil;

public class DeviceUtils {


    @NonNull
    public static Point getScreenSize(Context context) {
        Point point = new Point();
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm != null ? wm.getDefaultDisplay() : null;
        if (display != null) {
            display.getSize(point);
        }
        return point;
    }

    public static int dip2px(int dps) {
        return Math.round(WHUtil.getContext().getResources().getDisplayMetrics().density * dps);

    }

    public void getIMSI() {

    }

    public static String getAndroidID() {
        return Settings.Secure.getString(
                WHUtil.getContext().getContentResolver(),
                Settings.Secure.ANDROID_ID
        );
    }
}
