package com.digital.playground.data.api

import com.digital.playground.data.dto.MovieDetailModel
import com.digital.playground.data.dto.SearchResults
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get


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
        client.get(DISCOVER_MOVIE.format(pageIndex)).body()

    override suspend fun getMovieDetail(id: Int): MovieDetailModel =
        client.get(GET_MOVIE_DETAIL.format(id)).body()

}