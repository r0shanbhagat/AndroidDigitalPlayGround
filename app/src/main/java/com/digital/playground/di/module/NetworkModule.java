package com.digital.playground.di.module;

import android.content.Context;

import com.digital.playground.BuildConfig;
import com.digital.playground.repository.ApiConstants;
import com.digital.playground.repository.ApiService;
import com.digital.playground.repository.logging.Level;
import com.digital.playground.repository.logging.Logger;
import com.digital.playground.repository.logging.LoggingInterceptor;
import com.digital.playground.repository.logging.NoInternetException;
import com.digital.playground.util.AppUtils;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author ihsan on 2/28/18.
 */
@Module
public class NetworkModule {

    @Provides
    LoggingInterceptor provideInterceptor() {
        return new LoggingInterceptor.Builder()
                .loggable(BuildConfig.DEBUG)
                .logger(Logger.DEFAULT)
                .setLevel(Level.BODY)
                .build();
    }

    @Provides
    @Singleton
    OkHttpClient provideOkHttp(Context context, LoggingInterceptor logging) {
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
