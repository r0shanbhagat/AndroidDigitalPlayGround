package com.digital.playground.contract

import com.digital.playground.utils.DataState
import kotlinx.coroutines.flow.Flow


/**
 * @Details :IRepository
 * @Author Roshan Bhagat
 *
 * @param
 * @constructor Create Repository
 */
interface Repository {

    val dataSource: MovieDataSource

    /**
     * Get MovieList
     *
     * @return
     */
    suspend fun getMovieList(): Flow<DataState>

}