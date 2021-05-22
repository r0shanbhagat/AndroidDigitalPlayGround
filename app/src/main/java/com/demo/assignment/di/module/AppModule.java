package com.demo.assignment.di.module;

import android.content.Context;

import com.demo.assignment.AssignmentApp;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
@Singleton
@Module(includes = {ViewModelModule.class, NetworkModule.class})
public class AppModule {

    @Provides
    @Singleton
    Context provideContext(AssignmentApp application) {
        return application.getApplicationContext();
    }
}
