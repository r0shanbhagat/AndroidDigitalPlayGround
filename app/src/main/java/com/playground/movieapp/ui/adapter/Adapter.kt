package com.playground.movieapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.databinding.library.baseAdapters.BR
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.digital.playground.ui.adapter.MovieDiffCallback
import com.playground.movieapp.ui.callback.IItemClick

class Adapter : RecyclerView.Adapter<ViewHolder>() {

    var listItems: MutableList<ItemViewModel> = ArrayList()
    private val viewTypeToLayoutId: MutableMap<Int, Int> = mutableMapOf()
    var callback: IItemClick<ItemViewModel>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ViewDataBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            viewTypeToLayoutId[viewType] ?: 0,
            parent,
            false
        )
        return ViewHolder(binding, callback)
    }

    override fun getItemViewType(position: Int): Int {
        val item = listItems[position]
        if (!viewTypeToLayoutId.containsKey(item.viewType)) {
            viewTypeToLayoutId[item.viewType] = item.layoutId
        }
        return item.viewType
    }

    override fun getItemCount(): Int = listItems.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val itemModel = listItems[position]
        holder.bind(itemModel)
    }

    fun updateItems(updatedItems: List<ItemViewModel>) {
        updatedItems.let {
            val diffCallback = MovieDiffCallback(listItems, updatedItems)
            val diffResult = DiffUtil.calculateDiff(diffCallback)
            listItems.clear()
            listItems.addAll(updatedItems)
            diffResult.dispatchUpdatesTo(this)
        }

    }

}

class ViewHolder constructor(
    private val binding: ViewDataBinding,
    private var callback: IItemClick<ItemViewModel>?
) : RecyclerView.ViewHolder(binding.root) {
    private lateinit var itemModel: ItemViewModel

    init {
        itemView.setOnClickListener { callback?.onItemClick(itemModel) }
    }

    fun bind(itemModel: ItemViewModel) {
        this.itemModel = itemModel
        binding.setVariable(BR.itemModel, itemModel)
    }
}
