package com.digital.playground.ui.adapter

import android.os.Parcelable
import androidx.databinding.BaseObservable
import com.digital.playground.R
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MovieModel(
    var title: String,
    var body: String,
    var image: String,
    var year: String,
    var imdb: String

) : BaseObservable(), Parcelable, ItemViewModel {

    override val layoutId: Int = R.layout.list_item_movie

    override val viewType: Int = 0
}