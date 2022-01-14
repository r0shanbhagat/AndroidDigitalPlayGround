package com.digital.playground.repository.model

import com.google.gson.annotations.SerializedName
/**
 * @Details MovieModel
 * @Author Roshan Bhagat
 */
data class MovieModel(
    @SerializedName("name")
    val name: String,
    @SerializedName("imageUrl")
    val imageUrl: String,
    @SerializedName("category")
    val category: String,
    @SerializedName("desc")
    val desc: String,
)
