package com.xwh.lib.corelib.utils;

import android.widget.Toast;

import com.xwh.lib.corelib.WHUtil;

public class ToashUtils {
    public static void show(String message) {
        Toast.makeText(WHUtil.getContext(), message, Toast.LENGTH_SHORT).show();
    }

    public static void showLong(String message) {
        Toast.makeText(WHUtil.getContext(), message, Toast.LENGTH_LONG).show();
    }


}
