package com.digital.playground.data.mapper

import com.digital.playground.data.model.MovieResponse
import com.digital.playground.ui.adapter.MovieModel
import com.digital.playground.utils.EntityMapper
import javax.inject.Inject


/**
 * @Details MovieMapper
 * @Author Roshan Bhagat
 *
 * @constructor
 */
class MovieMapper @Inject constructor() : EntityMapper<MovieResponse, MovieModel> {

    override fun mapFromEntity(entity: MovieResponse): MovieModel {
        return MovieModel(
            title = entity.name,
            body = entity.name,
            image = entity.imageUrl,
            category = entity.category
        )
    }

    /**
     * Map from entity list
     *
     * @param entities
     * @return
     */
    fun mapFromEntityList(entities: List<MovieResponse>?): List<MovieModel> {
        return entities?.map {
            mapFromEntity(it)
        } ?: emptyList()
    }

}