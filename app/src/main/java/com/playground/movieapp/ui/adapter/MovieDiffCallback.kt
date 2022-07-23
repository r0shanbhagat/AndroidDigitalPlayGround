package com.digital.playground.ui.adapter

import androidx.annotation.Nullable
import androidx.core.graphics.component1
import androidx.core.graphics.component2
import androidx.core.graphics.component3
import androidx.recyclerview.widget.DiffUtil
import com.playground.movieapp.ui.adapter.ItemViewModel

class MovieDiffCallback(
    private val oldList: List<ItemViewModel>,
    private val newList: List<ItemViewModel>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].viewType == newList[newItemPosition].viewType
    }

    override fun areContentsTheSame(oldPosition: Int, newPosition: Int): Boolean {
        val (_, value, name) = oldList[oldPosition].viewType
        val (_, value1, name1) = newList[newPosition].viewType

        return name == name1 && value == value1
    }

    @Nullable
    override fun getChangePayload(oldPosition: Int, newPosition: Int): Any? {
        return super.getChangePayload(oldPosition, newPosition)
    }
}