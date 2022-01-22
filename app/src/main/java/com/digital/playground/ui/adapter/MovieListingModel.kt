package com.digital.playground.ui.adapter

import androidx.databinding.BaseObservable
import com.digital.playground.R
import com.digital.playground.ui.viewmodel.MovieListViewModel

class MovieListingModel(
    var title: String,
    var body: String,
    var image: String,
    var category: String
) : BaseObservable(), ItemViewModel {

    override val layoutId: Int = R.layout.item_movie_list

    override val viewType: Int = MovieListViewModel.LISTING_ITEM

    fun onClick() {
        // onItemClick("$make $model for $price")
    }
}