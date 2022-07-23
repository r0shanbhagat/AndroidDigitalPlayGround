package com.playground.movieapp.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SearchResults(
    @SerialName("page")
    val page: Int,
    @SerialName("results")
    val results: List<Result>?,
    @SerialName("total_pages")
    val totalPages: Int,
    @SerialName("total_results")
    val totalResults: Int
)