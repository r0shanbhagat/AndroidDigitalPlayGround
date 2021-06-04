package com.demo.assignment.core;

import android.view.ViewGroup;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.demo.assignment.ui.callback.IItemClick;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;


public abstract class BaseAdapter<T extends RecyclerView.ViewHolder, M> extends RecyclerView.Adapter<T> {
    public List<M> itemArrayList;
    private OnLoadMoreListener onLoadMoreListener;
    private IItemClick<M> itemClick;


    /**
     * Instantiates a new Abstract recycler adapter.
     *
     * @param itemArrayList the item array list
     */
    public BaseAdapter(List<M> itemArrayList) {
        this.itemArrayList = itemArrayList;

    }

    @NotNull
    @Override
    public T onCreateViewHolder(@NotNull ViewGroup viewGroup, int viewType) {
        return setViewHolder(viewGroup, viewType);
    }

    @Override
    public void onBindViewHolder(@NotNull T holder, int position) {
        onBindData(holder, itemArrayList.get(position));
    }

    @Override
    public int getItemCount() {
        return null != itemArrayList
                && !itemArrayList.isEmpty() ? itemArrayList.size() : 0;
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
        itemArrayList = itemAdd;
        this.notifyDataSetChanged();
    }

    /**
     * Sets on load more listener.
     *
     * @param recyclerView        the recycler view
     * @param mOnLoadMoreListener the m on load more listener
     */
    public void setOnLoadMoreListener(RecyclerView recyclerView, OnLoadMoreListener mOnLoadMoreListener) {
        this.onLoadMoreListener = mOnLoadMoreListener;
        final LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NotNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int visibleItemCount = Objects.requireNonNull(layoutManager).getChildCount();
                int totalItemCount = layoutManager.getItemCount();
                int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();
                if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount && firstVisibleItemPosition >= 0) {
                    if (onLoadMoreListener != null) {
                        onLoadMoreListener.onLoadMore();
                    }
                    // isLoading = true;
                }

            }
        });
    }

    /**
     * Gets item.
     *
     * @param position the position
     * @return the item
     */
    @SuppressWarnings({"unused", "RedundantSuppression"})
    public M getItem(int position) {
        return itemArrayList.get(position);
    }

    /**
     * Gets Adapter ItemList.
     *
     * @return the item
     */
    @SuppressWarnings({"unused", "RedundantSuppression"})
    public List<M> getItemList() {
        return itemArrayList;
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
    public abstract void onBindData(T viewHolder, M itemVal);


    /**
     * The interface On load more listener.
     */
    public static abstract class OnLoadMoreListener {

        protected void onLoadMore() {
        }

        protected void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
        }


    }

}
