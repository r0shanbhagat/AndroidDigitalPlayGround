package com.digital.playground.data.mapper

import com.digital.playground.contract.EntityMapper
import com.digital.playground.data.dto.Result
import com.digital.playground.data.dto.SearchResults
import com.digital.playground.ui.adapter.MovieModel
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