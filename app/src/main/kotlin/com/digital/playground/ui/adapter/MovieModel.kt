package com.digital.playground.ui.adapter

import android.os.Parcelable
import androidx.databinding.BaseObservable
import com.digital.playground.R
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize

@Parcelize
data class MovieModel(
    val id: Int,
    val title: String,
    val body: String,
    val image: String,
    val year: String,
    val imdb: String

) : BaseObservable(), Parcelable, ItemViewModel {

    @IgnoredOnParcel
    override val layoutId: Int = R.layout.item_movie_card

    @IgnoredOnParcel
    override val viewType: Int = 0
}