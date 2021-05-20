package com.demo.assignment.repository;

import com.demo.assignment.repository.model.RandomJokesModel;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

public interface ApiService {

    @GET("/jokes/random?limitTo=[nerdy]")
    Observable<RandomJokesModel> getJokesList(@QueryMap Map<String, String> parameter);
}