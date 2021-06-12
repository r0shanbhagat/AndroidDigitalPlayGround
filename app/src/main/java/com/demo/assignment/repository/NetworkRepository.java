package com.demo.assignment.repository;

import android.content.Context;

import com.demo.assignment.BuildConfig;
import com.demo.assignment.repository.logging.Level;
import com.demo.assignment.repository.logging.Logger;
import com.demo.assignment.repository.logging.LoggingInterceptor;
import com.demo.assignment.repository.logging.NoInternetException;
import com.demo.assignment.util.AppUtils;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


public class NetworkRepository {
    private static NetworkRepository projectRepository;
    private final ApiService apiService;

    /**
     * NetworkRepository
     *
     * @param context context
     */
    private NetworkRepository(Context context) {
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .baseUrl(ApiConstants.BASE_URL)
                .client(provideOkHttp(context))
                .build();
        apiService = retrofit.create(ApiService.class);
    }

    /**
     * LoggingInterceptor for Retrofit
     *
     * @return :LoggingInterceptor
     */
    private LoggingInterceptor provideInterceptor() {
        return new LoggingInterceptor.Builder()
                .loggable(BuildConfig.DEBUG)
                .logger(Logger.DEFAULT)
                .setLevel(Level.BODY)
                .build();
    }

    /**
     * OkHttpClient
     *
     * @param context:Context
     * @return :OkHttpClient
     */
    private OkHttpClient provideOkHttp(Context context) {
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
        httpClient.addInterceptor(provideInterceptor());
        return httpClient.build();
    }

    /**
     * @param context :Context
     * @return : ApiService
     */
    public static synchronized ApiService getService(Context context) {
        if (projectRepository == null) {
            projectRepository = new NetworkRepository(context);
        }
        return projectRepository.apiService;
    }
}
