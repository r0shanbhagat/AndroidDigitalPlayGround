package com.demo.assignment.di.component;

import com.demo.assignment.AssignmentApp;
import com.demo.assignment.di.module.AppModule;
import com.demo.assignment.ui.activity.ExerciseActivity;
import com.demo.assignment.ui.fragment.RandomJokesFragment;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;


@Singleton
@Component(modules = AppModule.class)
public interface AppComponent<V> {

    void inject(ExerciseActivity activity);

    void inject(RandomJokesFragment randomJokesFragment);


    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(AssignmentApp application);

        AppComponent build();
    }

}