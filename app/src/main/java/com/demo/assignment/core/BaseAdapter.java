package com.demo.assignment.core;

import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.demo.assignment.ui.callback.IItemClick;

import org.jetbrains.annotations.NotNull;

import java.util.List;


public abstract class BaseAdapter<M, T extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<T> {
    public List<M> itemList;
    private IItemClick<M> itemClick;


    /**
     * Instantiates a new Abstract recycler adapter.
     *
     * @param itemList the item array list
     */
    public BaseAdapter(List<M> itemList) {
        this.itemList = itemList;

    }

    @NotNull
    @Override
    public T onCreateViewHolder(@NotNull ViewGroup viewGroup, int viewType) {
        return setViewHolder(viewGroup, viewType);
    }

    @Override
    public void onBindViewHolder(@NotNull T holder, int position) {
        onBindData(holder, position, itemList.get(position));
    }

    @Override
    public int getItemCount() {
        return null != itemList
                && !itemList.isEmpty() ? itemList.size() : 0;
    }


    public IItemClick<M> getClickListener() {
        return itemClick;
    }

    public void setItemClickListener(IItemClick<M> itemClick) {
        this.itemClick = itemClick;
    }

    /**
     * Add items.
     *
     * @param itemAdd the saved card itemz
     */
    @SuppressWarnings({"unused", "RedundantSuppression"})
    public void addItems(List<M> itemAdd) {
        itemList = itemAdd;
        this.notifyDataSetChanged();
    }


    /**
     * Gets item.
     *
     * @param position the position
     * @return the item
     */
    @SuppressWarnings({"unused", "RedundantSuppression"})
    public M getItem(int position) {
        return null != itemList && !itemList.isEmpty()
                ? itemList.get(position) : null;
    }

    /**
     * Gets Adapter ItemList.
     *
     * @return the item
     */
    @SuppressWarnings({"unused", "RedundantSuppression"})
    public List<M> getItemList() {
        return itemList;
    }


    /**
     * Sets view holder.
     *
     * @param parent   the parent
     * @param viewType :View
     * @return the view holder
     */
    public abstract T setViewHolder(ViewGroup parent, int viewType);

    /**
     * On bind data.
     *
     * @param viewHolder the t
     * @param itemVal    the val
     */
    public abstract void onBindData(T viewHolder, int position, M itemVal);


}
