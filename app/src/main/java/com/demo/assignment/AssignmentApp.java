package com.demo.assignment;

import android.content.Context;

import androidx.multidex.MultiDex;
import androidx.multidex.MultiDexApplication;

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
        appComponent = DaggerAppComponent.builder().application(this).build();
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }
}
