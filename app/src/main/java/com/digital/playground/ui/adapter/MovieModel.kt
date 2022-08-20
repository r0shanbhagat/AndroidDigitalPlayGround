package com.digital.playground.ui.adapter

import androidx.databinding.BaseObservable
import com.digital.playground.R
import com.digital.playground.ui.viewmodel.MovieViewModel

data class MovieModel(
    var title: String,
    var body: String,
    var image: String,
    var category: String
) : BaseObservable(), ItemViewModel {

    override val layoutId: Int = R.layout.item_movie_list

    override val viewType: Int = MovieViewModel.LISTING_ITEM
}