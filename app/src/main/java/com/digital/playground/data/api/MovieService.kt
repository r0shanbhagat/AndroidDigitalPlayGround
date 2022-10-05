package com.digital.playground.data.api

import com.digital.playground.data.dto.MovieDetailModel
import com.digital.playground.data.dto.SearchResults

/**
 * @Details ApiService
 * @Author Roshan Bhagat
 * @constructor Create Api service
 */
interface MovieService {
    /**
     * Performs a GET call to obtain a paginated list of movies
     * @param pageIndex: Integer Page number of the database from which movie should be fetched
     *  Response instance of [SearchResults] type
     */
    suspend fun discoverMovie(pageIndex: Int): SearchResults

    /**
     * Base on Movies Id fetch the details of movie
     *  Response instance of [MovieDetailModel] type
     * @param id: Integer id of movie based on˚ which movie should be fetched
     * @return
     */
    suspend fun getMovieDetail(id: Int): MovieDetailModel


}