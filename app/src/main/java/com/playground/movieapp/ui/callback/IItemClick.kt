package com.playground.movieapp.ui.callback

/**
 * @Details IItemClick
 * @Author Roshan Bhagat
 */
interface IItemClick<T> {
    fun onItemClick(item: T)
}