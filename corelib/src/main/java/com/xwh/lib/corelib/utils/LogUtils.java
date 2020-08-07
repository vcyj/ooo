package com.xwh.lib.corelib.utils;


import android.text.TextUtils;
import android.util.Log;

import java.util.Locale;

import bodyfast.zerofasting.fastic.corelib.BuildConfig;

public class LogUtils {

    private static String customTagPrefix = "CoreLib";

    private static boolean DEBUG;

    private LogUtils() {
        throw new RuntimeException();
    }

    public static boolean isDebug() {
        return DEBUG;
    }

    public static void setDebug(boolean b) {
        DEBUG = b;
    }

    public static void setPrefix(String prefix) {
        if (prefix != null) customTagPrefix = prefix;
    }

    private static String generateTag() {
        StackTraceElement caller = new Throwable().getStackTrace()[2];
        String tag = "%s.%s(L:%d)";
        String callerClazzName = caller.getClassName();
        callerClazzName = callerClazzName.substring(callerClazzName.lastIndexOf(".") + 1);
        tag = String.format(Locale.ENGLISH, tag, callerClazzName, caller.getMethodName(), caller.getLineNumber());
        tag = TextUtils.isEmpty(customTagPrefix) ? tag : customTagPrefix + ":" + tag;
        return tag;
    }

    public static void d(String content) {
        if (!DEBUG || content == null) return;
        String tag = generateTag();
        Log.d(tag, content);
    }

    public static void d(String content, Throwable tr) {
        if (!DEBUG || content == null) return;
        String tag = generateTag();
        Log.d(tag, content, tr);
    }

    public static void e(String content) {
        if (!DEBUG || content == null || !BuildConfig.DEBUG) return;
        String tag = generateTag();
        Log.e(tag, content);
    }

    public static void e(String content, Throwable tr) {
        if (!DEBUG || content == null) return;
        String tag = generateTag();
        Log.e(tag, content, tr);
    }

    public static void i(String content) {
        if (!DEBUG || content == null) return;
        Log.i(customTagPrefix, content);
    }

    public static void i(String content, Throwable tr) {
        if (!DEBUG || content == null) return;
        String tag = generateTag();

        Log.i(tag, content, tr);
    }

    public static void v(String content) {
        if (!DEBUG || content == null) return;
        String tag = generateTag();

        Log.v(tag, content);
    }

    public static void v(String content, Throwable tr) {
        if (!DEBUG || content == null) return;
        String tag = generateTag();
        Log.v(tag, content, tr);

    }




    public static void errorLog(String message) {
        Log.e(customTagPrefix, message);
    }

}
