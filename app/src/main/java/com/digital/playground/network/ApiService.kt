package com.digital.playground.network

import com.digital.playground.repository.model.MovieModel
import retrofit2.http.GET

interface ApiService {
    @GET("/movielist.json")
    suspend fun getAllMovies(): List<MovieModel>
}