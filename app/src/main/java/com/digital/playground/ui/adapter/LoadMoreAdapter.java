package com.digital.playground.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.paging.PagingDataAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.digital.playground.R;
import com.digital.playground.databinding.ItemBannerBinding;
import com.digital.playground.databinding.ListItemLoadMoreBinding;
import com.digital.playground.repository.model.MoviesModel;
import com.digital.playground.ui.callback.IItemClick;
import com.digital.playground.util.AppUtils;

import org.jetbrains.annotations.NotNull;


public class LoadMoreAdapter extends PagingDataAdapter<MoviesModel, RecyclerView.ViewHolder> {

    private static final int LOADING = 0;
    private static final int ITEM = 1;
    private static final int BANNER = 2;
    private IItemClick<MoviesModel> itemClick;

    /**
     * @param diffCallback:DiffUtil
     */
    public LoadMoreAdapter(@NotNull DiffUtil.ItemCallback<MoviesModel> diffCallback) {
        super(diffCallback);
    }

    public void setItemClickListener(IItemClick<MoviesModel> itemClick) {
        this.itemClick = itemClick;
    }


    @NotNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        RecyclerView.ViewHolder viewHolder;
        if (viewType == BANNER) {
            ItemBannerBinding bannerBinding =
                    DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()),
                            R.layout.item_banner, viewGroup, false);
            viewHolder = new BannerVH(bannerBinding, itemClick);
        } else {
            ListItemLoadMoreBinding itemBinding =
                    DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()),
                            R.layout.list_item_load_more, viewGroup, false);

            viewHolder = new ItemVH(itemBinding, itemClick);
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NotNull RecyclerView.ViewHolder holder, int position) {
        MoviesModel itemVal = getItem(position);
        int itemViewType = getItemViewType(position);
        if (itemViewType == BANNER) {
            ((BannerVH) holder).bindView(itemVal);
        } else {
            ((ItemVH) holder).bindView(itemVal);
        }
    }


    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return BANNER;
        } else {
            return (position == getItemCount()) ? ITEM : LOADING;
        }
    }


    /**
     * Header ViewHolder
     */
    private static class BannerVH extends RecyclerView.ViewHolder {
        private final ItemBannerBinding binding;
        private MoviesModel itemModel;

        private BannerVH(ItemBannerBinding binding, final IItemClick<MoviesModel> holderClick) {
            super(binding.getRoot());
            this.binding = binding;
            itemView.setOnClickListener(v -> {
                if (null == holderClick) {
                    AppUtils.showLog("Holder", "Trying to work on a null object ,returning.");
                    return;
                }
                holderClick.onItemClick(itemModel);
            });

        }

        private void bindView(MoviesModel itemModel) {
            this.itemModel = itemModel;
            binding.setItemModel(itemModel);
        }
    }


    private static class ItemVH extends RecyclerView.ViewHolder {
        private final ListItemLoadMoreBinding binding;
        private MoviesModel itemModel;

        private ItemVH(ListItemLoadMoreBinding viewDataBinding, final IItemClick<MoviesModel> holderClick) {
            super(viewDataBinding.getRoot());
            binding = viewDataBinding;
            itemView.setOnClickListener(v -> {
                if (null == holderClick) {
                    AppUtils.showLog("Holder", "Trying to work on a null object ,returning.");
                    return;
                }
                holderClick.onItemClick(itemModel);
            });

        }

        private void bindView(MoviesModel itemModel) {
            this.itemModel = itemModel;
            AppUtils.loadImageWithCallback(binding.moviePoster, itemModel.getPosterPath(), () ->
                    binding.movieProgress.setVisibility(View.GONE));
            binding.setItemModel(itemModel);
        }
    }


}
