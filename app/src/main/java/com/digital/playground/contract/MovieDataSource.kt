package com.digital.playground.contract

import com.digital.playground.data.api.MovieService
import com.digital.playground.data.model.MovieResponse
import kotlinx.coroutines.flow.Flow


/**
 * @Details :MovieService
 * @Author Roshan Bhagat
 * @param
 * @constructor Create Movie data source
 */
interface MovieDataSource {
    val apiService: MovieService

    /**
     * Get MovieList
     *
     * @return
     */
    suspend fun getMovieList(): Flow<List<MovieResponse>>

}