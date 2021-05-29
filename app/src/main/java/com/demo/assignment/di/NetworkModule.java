package com.demo.assignment.di;

import android.content.Context;

import com.demo.assignment.BuildConfig;
import com.demo.assignment.repository.ApiConstants;
import com.demo.assignment.repository.ApiService;
import com.demo.assignment.repository.logging.Level;
import com.demo.assignment.repository.logging.Logger;
import com.demo.assignment.repository.logging.LoggingInterceptor;
import com.demo.assignment.repository.logging.NoInternetException;
import com.demo.assignment.util.AppUtils;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
@InstallIn(SingletonComponent.class)
public class NetworkModule {

    @Provides
    @Singleton
    LoggingInterceptor provideLogInterceptor() {
        return new LoggingInterceptor.Builder()
                .loggable(BuildConfig.DEBUG)
                .logger(Logger.DEFAULT)
                .setLevel(Level.BODY)
                .build();
    }

    @Provides
    @Singleton
    Interceptor provideErrorInterceptor(@ApplicationContext Context context) {
        return chain -> {
            if (!AppUtils.isNetworkConnected(context)) {
                throw new NoInternetException("",
                        new Throwable(String.valueOf(NoInternetException.class)));
            }
            Request request = chain.request();
            return chain.proceed(request);
        };
    }

    @Provides
    @Singleton
    OkHttpClient provideOkHttp(Interceptor error, LoggingInterceptor logging) {
        OkHttpClient.Builder httpClient =
                new OkHttpClient.Builder();
        httpClient.addInterceptor(error);
        httpClient.addInterceptor(logging);
        return httpClient.build();
    }

    @Provides
    @Singleton
    Retrofit provideRetrofit(OkHttpClient client) {
        return new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(ApiConstants.BASE_URL)
                .client(client)
                .build();
    }

    @Provides
    @Singleton
    ApiService provideApi(Retrofit retrofit) {
        return retrofit.create(ApiService.class);
    }
}
