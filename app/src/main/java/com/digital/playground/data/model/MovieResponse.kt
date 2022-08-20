package com.digital.playground.data.model

import com.google.gson.annotations.SerializedName

/**
 * @Details MovieModel
 * @Author Roshan Bhagat
 */
data class MovieResponse(
    @SerializedName("name")
    val name: String,
    @SerializedName("imageUrl")
    val imageUrl: String,
    @SerializedName("category")
    val category: String,
    @SerializedName("desc")
    val desc: String,
)
