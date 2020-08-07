package com.example.myapplication;

import android.app.Application;

import com.xwh.lib.corelib.WHUtil;

public class MyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        WHUtil.init(this);
    }
}
