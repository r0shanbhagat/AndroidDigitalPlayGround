package com.digital.playground.data.api

import com.digital.playground.data.model.Movie
import com.digital.playground.data.model.SearchResults
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import javax.inject.Singleton


/**
 * @Details ApiService
 * @Author Roshan Bhagat
 * @constructor Create Api service
 */
@Singleton
interface MovieService {

    /**
     * Performs a GET call to obtain a paginated list of movies
     * @param key API Key
     * @param searchTitle feature source the movie should come from
     * @param pageIndex Page number of the data where the movie should come from
     * @return [Response] instance of [SearchResults] type
     */
    @GET("?type=movie")
    suspend fun getSearchResultData(
        @Query(value = "s") searchTitle: String,
        @Query(value = "apiKey") key: String,
        @Query(value = "page") pageIndex: Int
    ): SearchResults

    /**
     * Performs a GET call to obtain a paginated list of movies
     * @param apiKey API Key
     * @param imdbId source id of movie should come from
     * @return [Response] instance of [Movie] type
     */
    @GET("?plot=full")
    suspend fun getMovieDetailsData(
        @Query(value = "i") imdbId: String,
        @Query(value = "apiKey") apiKey: String
    ): Movie

}