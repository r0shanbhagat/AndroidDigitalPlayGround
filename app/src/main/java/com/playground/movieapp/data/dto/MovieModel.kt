package com.playground.movieapp.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class MovieModel(
    var title: String,
    var body: String,
    var image: String,
    var year: String,
    var imdb: String

) 