package com.digital.playground.repository;

import com.digital.playground.repository.model.RandomJokesModel;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

public interface ApiService {

    @GET("/jokes/random?limitTo=[nerdy]")
    Observable<RandomJokesModel> getJokesList(@QueryMap Map<String, String> parameter);
}