package com.demo.assignment.repository;


import com.demo.assignment.repository.model.TopRatedMovies;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {
    @GET("movie/top_rated")
    Single<TopRatedMovies> getTopRatedMovies(@Query("page") int pageIndex);

    // @GET("movie/popular")

}