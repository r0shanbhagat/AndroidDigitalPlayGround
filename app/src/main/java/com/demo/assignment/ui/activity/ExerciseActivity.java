package com.demo.assignment.ui.activity;

import android.os.Bundle;

import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.demo.assignment.R;
import com.demo.assignment.core.BaseActivity;
import com.demo.assignment.core.BaseViewModel;
import com.demo.assignment.databinding.ActivityExerciseBinding;
import com.demo.assignment.util.AppUtils;

import java.util.Objects;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ExerciseActivity extends BaseActivity<ActivityExerciseBinding, BaseViewModel> {

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_exercise;
    }

    @Override
    protected Class<BaseViewModel> getViewModel() {
        return null;
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