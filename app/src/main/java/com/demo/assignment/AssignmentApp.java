package com.demo.assignment;

import android.content.Context;

import androidx.multidex.MultiDex;
import androidx.multidex.MultiDexApplication;

import dagger.hilt.android.HiltAndroidApp;


@HiltAndroidApp
public class AssignmentApp extends MultiDexApplication {

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //TODO Global level Event Handling
    }

}
