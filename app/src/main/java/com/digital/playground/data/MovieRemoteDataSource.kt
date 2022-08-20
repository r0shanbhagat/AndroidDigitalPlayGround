package com.digital.playground.data

import com.digital.playground.contract.MovieDataSource
import com.digital.playground.data.api.MovieService
import com.digital.playground.data.model.MovieResponse
import com.digital.playground.di.IoDispatcher
import com.digital.playground.utils.DataState
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject


/**
 * @Details :MovieRemoteDataSource
 * @Author Roshan Bhagat
 *
 * @property apiService
 * @property ioDispatcher
 * @constructor Create Movie parse remote data source
 */
@ViewModelScoped
class MovieRemoteDataSource @Inject constructor(
    override val apiService: MovieService,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : MovieDataSource {

    /**
     * @return [DataState]
     */
    override suspend fun getMovieList(): Flow<List<MovieResponse>> = flow {
        val movie = apiService.getMoviesList()
        emit(movie)
    }.flowOn(ioDispatcher)

}
