package com.digital.playground.contract

import com.digital.playground.data.api.MovieService
import com.digital.playground.data.dto.MovieDetailModel
import com.digital.playground.ui.adapter.MovieModel
import kotlinx.coroutines.flow.Flow


/**
 * @Details :IRepository
 * @Author Roshan Bhagat
 *@Link https://developer.android.com/topic/architecture/data-layer
 *
 * @param
 * @constructor Create Repository
 */
interface Repository {

    val apiService: MovieService


    /**
     * Performs a GET call to obtain a paginated list of movies
     *
     * @param pageIndex Page index
     * @return
     */
    suspend fun discoverMovies(pageIndex: Int): Flow<List<MovieModel>>

    /**
     * Base on Movies Id fetch the detail of movie
     *
     * @param movieId movieId id
     * @return [Flow]
     */
    suspend fun getMovieDetail(movieId: Int): Flow<MovieDetailModel>

}