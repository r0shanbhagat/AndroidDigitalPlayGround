package com.digital.playground.repository

import com.digital.playground.repository.model.MovieModel
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET

interface ApiService {
    @GET("/movielist.json")
    fun getAllMovies(): Observable<List<MovieModel>>
}