package com.digital.playground.repository.mapper

import com.digital.playground.repository.model.Movie
import com.digital.playground.ui.adapter.MovieListingModel
import com.digital.playground.util.EntityMapper
import javax.inject.Inject

/**
 * @Details MovieMapper
 * @Author Roshan Bhagat
 */
class MovieMapper
@Inject
constructor() : EntityMapper<Movie, MovieListingModel> {
    override fun mapFromEntity(entity: Movie): MovieListingModel {
        return MovieListingModel(
            title = entity.name,
            body = entity.name,
            image = entity.imageUrl,
            category = entity.category
        )
    }

    fun mapFromEntityList(entities: List<Movie>): List<MovieListingModel> {
        return entities.map { mapFromEntity(it) }
    }

}