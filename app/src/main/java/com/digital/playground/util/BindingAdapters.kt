package com.digital.playground.util

import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

@BindingAdapter(value = ["url", "size"], requireAll = false)
fun loadImage(imageView: AppCompatImageView, url: String?, size: Int) {
    if (AppUtils.isValidString(url)) {
        Glide.with(imageView.context).load(url).into(imageView)
    }
}
