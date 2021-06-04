package com.demo.assignment.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.demo.assignment.R;
import com.demo.assignment.core.BaseActivity;
import com.demo.assignment.core.BaseViewModel;
import com.demo.assignment.load.MainActivity;


public class SplashActivity extends BaseActivity {
    private final static int SPLASH_DISPLAY_LENGTH = 1500; //InMilliSecond

    @NonNull
    @Override
    protected BaseViewModel createViewModel() {
        return null;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_splash;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Launching Main Screen after SPLASH_DISPLAY_LENGTH
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            startActivity(new Intent(SplashActivity.this, MainActivity.class));
            finish();
        }, SPLASH_DISPLAY_LENGTH);
    }


}
