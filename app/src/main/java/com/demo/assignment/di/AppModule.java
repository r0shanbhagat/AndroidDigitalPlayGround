package com.demo.assignment.di;

import com.demo.assignment.repository.model.LoginModel;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public class AppModule {

    @Provides
    @Singleton
    LoginModel provideLoginModel() {
        return new LoginModel();
    }
}
