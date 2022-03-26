package com.digital.playground.repository

import com.digital.playground.network.ApiService
import com.digital.playground.repository.mapper.MovieMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * @Details MovieRepository
 * @Author Roshan Bhagat
 */
class MovieRepository
constructor(
    private val apiService: ApiService,
    private val movieMapper: MovieMapper,
) {

    suspend fun getMovies() = withContext(Dispatchers.IO) {
        val moviesData = apiService.getAllMovies()
        movieMapper.mapFromEntityList(moviesData)
    }
}