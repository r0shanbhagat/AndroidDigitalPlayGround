package com.digital.playground.di.component;

import com.digital.playground.AssignmentApp;
import com.digital.playground.di.module.AppModule;
import com.digital.playground.ui.activity.ExerciseActivity;
import com.digital.playground.ui.fragment.LoginFragment;
import com.digital.playground.ui.fragment.RandomJokesFragment;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;


@Singleton
@Component(modules = AppModule.class)
public interface AppComponent<V> {

    void inject(ExerciseActivity activity);

    void inject(RandomJokesFragment randomJokesFragment);

    void inject(LoginFragment loginFragment);


    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(AssignmentApp application);

        AppComponent build();
    }

}