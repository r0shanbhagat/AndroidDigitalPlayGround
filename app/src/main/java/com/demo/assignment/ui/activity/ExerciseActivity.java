package com.demo.assignment.ui.activity;

import android.os.Bundle;
import android.util.Log;

import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.demo.assignment.R;
import com.demo.assignment.core.BaseActivity;
import com.demo.assignment.core.BaseViewModel;
import com.demo.assignment.databinding.ActivityExerciseBinding;
import com.demo.assignment.util.AppUtils;

import java.util.Objects;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ExerciseActivity extends BaseActivity<ActivityExerciseBinding, BaseViewModel> {

    @Inject
    ExerciseViewModel.ExerciseViewModelFactory assistedFactory;


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

        //Using of Hilt assisted
        Bundle bundle = new Bundle();
        bundle.putString("NAME", "TEST");
        ExerciseViewModel.MyViewModelFactory viewModelFactory = assistedFactory.create(this, "10");
        ExerciseViewModel mViewModel = new ViewModelProvider(this, viewModelFactory).get(ExerciseViewModel.class);

        Log.i("Roshan", mViewModel.getArgs());

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