package com.demo.assignment.di;

import android.content.Context;

import com.demo.assignment.BuildConfig;
import com.demo.assignment.repository.ApiConstants;
import com.demo.assignment.repository.ApiService;
import com.demo.assignment.repository.logging.Level;
import com.demo.assignment.repository.logging.Logger;
import com.demo.assignment.repository.logging.LoggingInterceptor;
import com.demo.assignment.repository.logging.NoInternetException;
import com.demo.assignment.repository.model.LoginModel;
import com.demo.assignment.util.AppUtils;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ViewModelComponent;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.android.scopes.ViewModelScoped;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
@InstallIn(ViewModelComponent.class)
public class NetworkModule {

    @Provides
    @ViewModelScoped
    LoggingInterceptor provideInterceptor() {
        return new LoggingInterceptor.Builder()
                .loggable(BuildConfig.DEBUG)
                .logger(Logger.DEFAULT)
                .setLevel(Level.BODY)
                .build();
    }

    @Provides
    @ViewModelScoped
    OkHttpClient provideOkHttp(@ApplicationContext Context context, LoggingInterceptor logging) {
        OkHttpClient.Builder httpClient =
                new OkHttpClient.Builder();
        httpClient.addInterceptor(chain -> {
            if (!AppUtils.isNetworkConnected(context)) {
                throw new NoInternetException("",
                        new Throwable(String.valueOf(NoInternetException.class)));
            }
            Request request = chain.request();
            return chain.proceed(request);
        });
        httpClient.addInterceptor(logging);
        return httpClient.build();
    }

    @Provides
    @ViewModelScoped
    Retrofit provideRetrofit(OkHttpClient client) {
        return new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(ApiConstants.BASE_URL)
                .client(client)
                .build();
    }

    @Provides
    @ViewModelScoped
    ApiService provideApi(Retrofit retrofit) {
        return retrofit.create(ApiService.class);
    }

    @Provides
    @ViewModelScoped
    LoginModel provideLoginModel() {
        return new LoginModel();
    }
}
