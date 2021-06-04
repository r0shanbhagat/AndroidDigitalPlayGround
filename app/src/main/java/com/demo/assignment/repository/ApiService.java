package com.demo.assignment.repository;


import com.demo.assignment.repository.model.HerosModel;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface ApiService {

    @GET("json/movies.json")
    Observable<List<HerosModel>> getHeroesList();

}