package com.digital.playground.contract

import com.digital.playground.data.api.MovieService
import com.digital.playground.data.model.Movie
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
     * Get search result data.
     *
     * @param searchTitle Search title
     * @param pageIndex Page index
     * @return
     */
    suspend fun getSearchResultData(searchTitle: String, pageIndex: Int): Flow<List<MovieModel>>

    /**
     * Get movie details data.
     *
     * @param imdbId Imdb id
     * @return [Flow]
     */
    suspend fun getMovieDetailsData(imdbId: String): Flow<Movie>
}