package com.digital.playground.di;

import android.content.Context;

import com.digital.playground.BuildConfig;
import com.digital.playground.R;
import com.digital.playground.di.qualifier.ErrorQualifier;
import com.digital.playground.di.qualifier.RequestQualifier;
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
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
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
    @RequestQualifier
    Interceptor requestInterceptor(@ApplicationContext Context context) {
        return chain -> {
            Request original = chain.request();
            HttpUrl originalHttpUrl = original.url();
            HttpUrl url = originalHttpUrl.newBuilder()
                    // Add request interceptor to add API key as query string parameter to each request
                    .addQueryParameter("api_key", context.getString(R.string.my_api_key))
                    .addQueryParameter("language", "en_US")
                    .build();
            Request.Builder requestBuilder = original.newBuilder()
                    .url(url);

            Request request = requestBuilder.build();
            return chain.proceed(request);
        };
    }

    @Provides
    @Singleton
    @ErrorQualifier
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
    OkHttpClient provideOkHttp(@RequestQualifier Interceptor request,
                               @ErrorQualifier Interceptor error, LoggingInterceptor logging) {
        OkHttpClient.Builder httpClient =
                new OkHttpClient.Builder();
        httpClient.addInterceptor(request);
        httpClient.addInterceptor(error);
        httpClient.addInterceptor(logging);
        return httpClient.build();
    }

    @Provides
    @Singleton
    Retrofit provideRetrofit(OkHttpClient client) {
        return new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
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
