package com.demo.assignment.di.module;

import android.content.Context;

import com.demo.assignment.repository.ApiConstants;
import com.demo.assignment.repository.ApiService;
import com.demo.assignment.repository.LoggingInterceptor;
import com.demo.assignment.util.AppUtils;

import java.util.Objects;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author ihsan on 2/28/18.
 */
@Module
public class NetworkModule {

    //TODO ADD LOGGER
 /*   @Provides
    @Singleton
    LoggingInterceptor provideInterceptor() {
        return new LoggingInterceptor.Builder()
                .loggable(BuildConfig.DEBUG)
                .setLevel(Level.BASIC)
                .log(Platform.INFO)
                .request("Request")
                .response("Response")
                .addQueryParam("apiKey", BuildConfig.API_KEY)
                .build();
    }*/

    @Provides
    @Singleton
    OkHttpClient provideOkHttp(Context context) {
        OkHttpClient.Builder httpClient =
                new OkHttpClient.Builder();
        httpClient.addInterceptor(chain -> {
            if (!AppUtils.isNetworkConnected(context)) {
                throw new LoggingInterceptor.NoInternetException("",
                        new Throwable(String.valueOf(LoggingInterceptor.NoInternetException.class)));
            }
            Request request = chain.request();
            Response response = chain.proceed(request);
            AppUtils.showLog("NetworkRepository", "Request:" + request.toString()
                    + "\nResponse:" + Objects.requireNonNull(response.body()).toString());
            return response;
        });
        return httpClient.build();
    }

    @Provides
    @Singleton
    Retrofit provideRetrofit(OkHttpClient client) {
        return new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(ApiConstants.BASE_URL_JOKES)
                .client(client)
                .build();
    }

    @Provides
    @Singleton
    ApiService provideApi(Retrofit retrofit) {
        return retrofit.create(ApiService.class);
    }
}
