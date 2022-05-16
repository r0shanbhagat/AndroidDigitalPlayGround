package com.digital.playground.data.model

import com.google.gson.annotations.SerializedName

data class SearchResults(
    @SerializedName("Response")
    val response: String? = "",
    @SerializedName("Search")
    val searches: List<Search>? = listOf(),
    @SerializedName("totalResults")
    val totalResults: String? = ""
)