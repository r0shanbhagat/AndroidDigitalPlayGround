package com.demo.assignment;

import android.app.Activity;
import android.app.Application;

import androidx.fragment.app.Fragment;

import com.demo.assignment.di.component.AppComponent;
import com.demo.assignment.di.component.DaggerAppComponent;


public class AssignmentApp extends MultiDexApplication {
    private AppComponent appComponent;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        appComponent=  DaggerAppComponent.builder().application(this).build();
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }
}
