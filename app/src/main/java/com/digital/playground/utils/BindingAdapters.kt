package com.digital.playground.utils

import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter
import coil.load
import com.digital.playground.data.api.MovieService
import com.playground.movieapp.R

@BindingAdapter("url")
fun loadImage(imageView: AppCompatImageView, url: String?) {
    if (isValidString(url)) {
        imageView.load(MovieService.BASE_IMAGE_URL.format(url)) {
            placeholder(R.drawable.image_not_found)
        }
    }
}
