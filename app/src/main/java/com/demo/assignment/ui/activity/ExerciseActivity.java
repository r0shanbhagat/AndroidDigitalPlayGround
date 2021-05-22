package com.demo.assignment.ui.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.demo.assignment.R;
import com.demo.assignment.util.AppUtils;

import java.util.Objects;

public class ExerciseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise);
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