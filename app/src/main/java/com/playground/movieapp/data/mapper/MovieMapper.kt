package com.playground.movieapp.data.mapper

import com.playground.movieapp.contract.EntityMapper
import com.playground.movieapp.data.dto.Result
import com.playground.movieapp.data.dto.SearchResults
import com.playground.movieapp.ui.adapter.MovieModel
import javax.inject.Inject


/**
 * @Details MovieMapper
 * @Author Roshan Bhagat
 *
 * @constructor
 */
class MovieMapper @Inject constructor() : EntityMapper<Result, MovieModel> {

    override fun mapFromEntity(entity: Result): MovieModel {
        return MovieModel(
            id = entity.id,
            title = entity.title,
            body = entity.overview,
            image = entity.posterPath,
            year = entity.releaseDate,
            imdb = entity.popularity.toString()
        )
    }

    /**
     * Map from entity list
     *
     * @param entities
     * @return
     */
    fun mapFromEntityList(entities: SearchResults?): List<MovieModel> {
        return entities?.results?.map {
            mapFromEntity(it)
        } ?: emptyList()
    }

}