package com.digital.playground.data

import com.digital.playground.contract.MovieDataSource
import com.digital.playground.contract.Repository
import com.digital.playground.data.mapper.MovieMapper
import com.digital.playground.di.IoDispatcher
import com.digital.playground.di.RemoteDataSource
import com.digital.playground.utils.DataState
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.*
import javax.inject.Inject


/**
 * @Details BlogRepository
 * @Author Roshan Bhagat
 * @see "https://developer.android.com/jetpack/guide/data-layer#architecture"
 * @property dataSource
 * @property ioDispatcher
 * @constructor Create [MovieRepository]
 */
@ViewModelScoped
class MovieRepository @Inject constructor(
    @RemoteDataSource override val dataSource: MovieDataSource,
    private val movieMapper: MovieMapper,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : Repository {

    /**
     * Get movie content.
     * @return [DataState]
     */
    override suspend fun getMovieList(): Flow<DataState> = flow {
        dataSource.getMovieList().onStart {
            emit(DataState.Loading)
        }.catch {
            emit(DataState.Error(it))
        }.collect { moviesList ->
            emit(DataState.Success(movieMapper.mapFromEntityList(moviesList)))
        }
    }

}