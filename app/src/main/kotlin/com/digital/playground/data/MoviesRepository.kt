package com.digital.playground.data

import com.digital.playground.contract.Repository
import com.digital.playground.data.api.MovieService
import com.digital.playground.data.dto.MovieDetailModel
import com.digital.playground.data.dto.MovieModel
import com.digital.playground.data.mapper.MovieMapper
import com.digital.playground.di.IoDispatcher
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
 * @constructor Create [MoviesRepository]
 */
@ViewModelScoped
class MoviesRepository @Inject constructor(
    private val apiService: MovieService,
    private val movieMapper: MovieMapper,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : Repository {

    /**
     * The app doesn't need that much information about the movies because it only displays the
     * content of the movies on the screen, along with basic information about its year of release
     * and rating. It's a good practice to separate model classes and have your repositories expose
     * only the data that the other layers of the hierarchy require. For example, here is how you
     * might trim down the SearchResults from the network in order to expose an Movie model
     * class to the domain and UI layers: With Helper of Mapper
     *
     * @return
     */
    override suspend fun discoverMovies(pageIndex: Int): Flow<List<MovieModel>> = flow {
        val searchResults = apiService.discoverMovie(pageIndex)
        emit(movieMapper.mapFromEntityList(searchResults))
    }.flowOn(ioDispatcher)

    override suspend fun getMovieDetail(movieId: Int): Flow<MovieDetailModel> = flow {
        emit(apiService.getMovieDetail(movieId))
    }.flowOn(ioDispatcher)


}