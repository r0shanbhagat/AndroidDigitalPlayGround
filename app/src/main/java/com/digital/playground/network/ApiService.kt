package com.digital.playground.network

import com.digital.playground.repository.model.Movie
import retrofit2.http.GET
/**
 * @Details ApiService
 * @Author Roshan Bhagat
 */
interface ApiService {
    @GET("/movielist.json")
    suspend fun getAllMovies(): List<Movie>
}