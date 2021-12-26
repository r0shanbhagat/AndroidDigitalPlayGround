package com.digital.playground.repository

import com.digital.playground.network.ApiService
import com.digital.playground.repository.mapper.MovieMapper
import com.digital.playground.repository.model.Movie
import com.thecode.dagger_hilt_mvvm.util.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MovieRepository
constructor(
    private val apiService: ApiService,
    private val movieMapper: MovieMapper,
) {
    suspend fun getMovies(): Flow<DataState<List<Movie>>> = flow {
        emit(DataState.Loading)
        try {
            val moviesData = apiService.getAllMovies()
            val movieList = movieMapper.mapFromEntityList(moviesData)
            emit(DataState.Success(movieList))
        } catch (e: Exception) {
            emit(DataState.Error(e))
        }
    }
}