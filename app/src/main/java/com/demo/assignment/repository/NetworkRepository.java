package com.demo.assignment.repository;

import android.content.Context;

import com.demo.assignment.util.AppUtil;

import java.util.Objects;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkRepository {
    //Base Url
    private static final String BASE_URL = "https://api.icndb.com/";
    private static NetworkRepository projectRepository;
    private final ApiService apiService;

    /**
     * NetworkRepository
     * @param context context
     */
    private NetworkRepository(Context context) {
        OkHttpClient.Builder httpClient =
                new OkHttpClient.Builder();
        httpClient.addInterceptor(chain -> {
            if (!AppUtil.isNetworkConnected(context)) {
                throw new HttpInterceptor.NoInternetException("",
                        new Throwable(String.valueOf(HttpInterceptor.NoInternetException.class)));
            }
            Request request = chain.request();
            Response response = chain.proceed(request);
            AppUtil.showLog("NetworkRepository", "Request:" + request.toString()
                    + "\nResponse:" + Objects.requireNonNull(response.body()).toString());
            return response;
        });

        /*
         * Setting Retrofit properties
         */
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(httpClient.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        apiService = retrofit.create(ApiService.class);
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
