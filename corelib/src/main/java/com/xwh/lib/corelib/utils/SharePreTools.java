package com.xwh.lib.corelib.utils;

import android.app.Activity;
import android.content.SharedPreferences;
import android.util.Log;

import com.xwh.lib.corelib.WHUtil;

public class SharePreTools {

    public static SharePreTools getInstance() {
        return Stub.sharePreTools;
    }

    static class Stub {
        public static final SharePreTools sharePreTools = new SharePreTools();
    }

    public void save(String tag, String message) {
        getSharePre().edit().putString(tag, message).commit();
    }

    public void save(String tag, float message) {
        getSharePre().edit().putFloat(tag, message).commit();
    }

    public void save(String tag, boolean message) {
        getSharePre().edit().putBoolean(tag, message).commit();
    }

    public float getFloat(String tag) {
        return getSharePre().getFloat(tag, Float.NaN);
    }

    public boolean getBoolean(String tag,boolean isDef) {
        return getSharePre().getBoolean(tag, isDef);
    }

    public String getString(String tag) {
        return getSharePre().getString(tag, "");
    }

    public SharedPreferences getSharePre() {
        if (WHUtil.getContext() == null) {
            throw new RuntimeException("WHUtils not init");
        }
        return WHUtil.getContext().getSharedPreferences("app", Activity.MODE_PRIVATE);

    }
}
