package com.digital.playground.core

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.digital.playground.ui.callback.IItemClick

//TODO refer https://medium.com/@sandipsavaliya/tired-with-adapters-4240e5f45c24
//https://proandroiddev.com/flexible-recyclerview-adapter-with-mvvm-and-data-binding-74f75caef66a
//https://techpaliyal.com/create-universal-recycler-view-adapter-with-mvvm-and-data-binding/
abstract class BaseRvAdapter<T : RecyclerView.ViewHolder?>(var itemList: List<*>?) :
    RecyclerView.Adapter<T>() {
    var onItemClickListener: IItemClick<*>? = null
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): T {
        return setViewHolder(viewGroup, viewType)
    }

    override fun onBindViewHolder(holder: T, position: Int) {
        onBindData(holder, itemList!![position])
    }

    override fun getItemCount(): Int {
        return if (null != itemList && !itemList!!.isEmpty()) itemList!!.size else 0
    }

    /**
     * Add items.
     *
     * @param itemAdd the saved card itemz
     */
    fun addItems(itemAdd: List<*>?) {
        itemList = itemAdd
        notifyDataSetChanged() //TODO USE DIFF UTIL
    }

    /**
     * Gets item.
     *
     * @param position the position
     * @return the item
     */
    fun getItem(position: Int): Any {
        return itemList!![position]!!
    }

    /**
     * Sets view holder.
     *
     * @param parent   the parent
     * @param viewType
     * @return the view holder
     */
    abstract fun setViewHolder(parent: ViewGroup?, viewType: Int): T

    /**
     * On bind data.
     *
     * @param viewHolder the t
     * @param itemVal    the val
     */
    abstract fun onBindData(viewHolder: T, itemVal: Any?)
}