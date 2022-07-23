package com.playground.movieapp.data.api

import com.playground.movieapp.data.dto.MovieDetailModel
import com.playground.movieapp.data.dto.SearchResults
import io.ktor.client.*
import io.ktor.client.request.*


/**
 * @Details :MovieServiceImpl
 * @Author Roshan Bhagat
 */
class MovieServiceImpl(private val client: HttpClient) : MovieService {
    companion object {
        const val DISCOVER_MOVIE = "3/discover/movie?page=%d"
        const val GET_MOVIE_DETAIL = "3/movie/%d"
    }

    override suspend fun discoverMovie(pageIndex: Int): SearchResults =
        client.get(DISCOVER_MOVIE.format(pageIndex))

    override suspend fun getMovieDetail(id: Int): MovieDetailModel =
        client.get(GET_MOVIE_DETAIL.format(id))

}