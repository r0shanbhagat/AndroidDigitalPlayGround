package com.digital.playground.contract

import com.digital.playground.utils.DataState
import kotlinx.coroutines.flow.Flow

/**
 * I use case
 *
 * @param
 * @constructor Create empty I use case
 */
interface UseCase {

    val repository: Repository

    /**
     * Get movie content
     *
     * @return
     */
    suspend fun getMovieList(): Flow<DataState>


}