package com.xwh.lib.corelib;

import android.app.Application;
import android.content.Context;

import androidx.annotation.IntDef;

import com.xwh.lib.corelib.utils.LogUtils;

public class WHUtil {

    private static Context applicationContext;
    private static Application application;

    public static void init(Application application) {
        if (application == null) {
            LogUtils.errorLog("Error: application is null");
            return;
        }
        applicationContext = application.getApplicationContext();
    }

    public static Context getContext() {
        return applicationContext;
    }

    public static Application getApplication() {
        return application;
    }

    public static String getString(int stringId) {
        if (stringId == 0) return "";
        return WHUtil.getContext().getString(stringId);
    }

    public static int getColor(int colorId) {
        return WHUtil.getContext().getResources().getColor(colorId);
    }
}
