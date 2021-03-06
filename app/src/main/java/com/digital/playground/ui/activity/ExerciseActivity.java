package com.digital.playground.ui.activity;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.digital.playground.R;
import com.digital.playground.core.BaseActivity;
import com.digital.playground.core.BaseViewModel;
import com.digital.playground.databinding.ActivityExerciseBinding;
import com.digital.playground.util.AppUtils;

import java.util.Objects;


public class ExerciseActivity extends BaseActivity<ActivityExerciseBinding, BaseViewModel> {

    @NonNull
    @Override
    protected BaseViewModel createViewModel() {
        return null;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_exercise;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*
         * Adding Navigation Callback for Analytics
         */
        addNavigateCallback();
    }

    /**
     * Adding Navigation Callback
     */
    private void addNavigateCallback() {
        NavHostFragment navHostFragment =
                (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        NavController navController = null;
        if (navHostFragment != null) {
            navController = navHostFragment.getNavController();
        }
        //Adding OnDestinationChangedListener
        Objects.requireNonNull(navController).addOnDestinationChangedListener((controller, destination, arguments) ->
                AppUtils.showLog(ExerciseActivity.class.getSimpleName(),
                        Objects.requireNonNull(destination.getLabel()).toString()));
    }
}