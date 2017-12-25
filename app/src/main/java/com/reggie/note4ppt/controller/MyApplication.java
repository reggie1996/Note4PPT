package com.reggie.note4ppt.controller;

import android.app.Application;

import com.raizlabs.android.dbflow.config.FlowManager;

/**
 * Created by 24073 on 2017/12/6.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        FlowManager.init(this);
    }
}
