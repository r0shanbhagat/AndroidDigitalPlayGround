package com.digital.playground.data

import com.digital.playground.BuildConfig
import com.digital.playground.data.api.MovieService
import com.digital.playground.data.mapper.MovieMapper
import com.digital.playground.data.model.Movie
import com.digital.playground.di.IoDispatcher
import com.digital.playground.ui.adapter.MovieModel
import com.digital.playground.utils.ViewState
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject


/**
 * @Details BlogRepository
 * @Author Roshan Bhagat
 * @property movieMapper
 * @property ioDispatcher
 * @constructor Create [MovieRepository]
 */
@ViewModelScoped
class MovieRepository @Inject constructor(
    override val apiService: MovieService,
    private val movieMapper: MovieMapper,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : com.digital.playground.contract.Repository {

    /**
     * @return [ViewState]
     */
    override suspend fun getSearchResultData(
        searchTitle: String,
        pageIndex: Int
    ): Flow<List<MovieModel>> {
        return flow {
            val searchResults = apiService.getSearchResultData(
                searchTitle, BuildConfig.API_KEY, pageIndex
            )
            emit(movieMapper.mapFromEntityList(searchResults))
        }.flowOn(ioDispatcher)
    }


    override suspend fun getMovieDetailsData(imdbId: String): Flow<Movie> {
        return flow {
            val movie = apiService.getMovieDetailsData(
                imdbId, BuildConfig.API_KEY
            )
            emit(movie)
        }.flowOn(ioDispatcher)
    }
}