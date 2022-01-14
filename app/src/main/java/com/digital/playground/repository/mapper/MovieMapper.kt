package com.digital.playground.repository.mapper

import com.digital.playground.repository.model.Movie
import com.digital.playground.repository.model.MovieModel
import com.digital.playground.util.EntityMapper
import javax.inject.Inject
/**
 * @Details MovieMapper
 * @Author Roshan Bhagat
 */
class MovieMapper
@Inject
constructor() : EntityMapper<MovieModel, Movie> {
    override fun mapFromEntity(entity: MovieModel): Movie {
        return Movie(
            title = entity.name,
            body = entity.name,
            image = entity.imageUrl,
            category = entity.category
        )
    }

    fun mapFromEntityList(entities: List<MovieModel>): List<Movie> {
        return entities.map { mapFromEntity(it) }
    }

}