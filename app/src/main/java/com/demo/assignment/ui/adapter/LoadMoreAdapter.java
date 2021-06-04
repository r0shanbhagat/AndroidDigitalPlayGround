package com.demo.assignment.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.demo.assignment.R;
import com.demo.assignment.core.BaseAdapter;
import com.demo.assignment.databinding.ItemBannerBinding;
import com.demo.assignment.databinding.ItemFooterProgressBinding;
import com.demo.assignment.databinding.ListItemLoadMoreBinding;
import com.demo.assignment.repository.model.MoviesModel;
import com.demo.assignment.ui.callback.IItemClick;
import com.demo.assignment.util.AppUtils;

import java.util.List;

import javax.annotation.Nullable;


public class LoadMoreAdapter extends BaseAdapter<RecyclerView.ViewHolder, MoviesModel> {

    private static final int ITEM = 0;
    private static final int LOADING = 1;
    private static final int BANNER = 2;
    private boolean isLoadingAdded, retryPageLoad;
    private String errorMsg;

    public LoadMoreAdapter(List<MoviesModel> listItems) {
        super(listItems);
    }


    @Override
    public RecyclerView.ViewHolder setViewHolder(ViewGroup viewGroup, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        switch (viewType) {
            case ITEM:
                ListItemLoadMoreBinding itemBinding =
                        DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()),
                                R.layout.list_item_load_more, viewGroup, false);

                viewHolder = new ItemVH(itemBinding, getClickListener());
                break;
            case LOADING:
                ItemFooterProgressBinding footerBinding =
                        DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()),
                                R.layout.item_footer_progress, viewGroup, false);
                viewHolder = new FooterProgressVH(footerBinding, getClickListener());
                break;
            case BANNER:
                ItemBannerBinding bannerBinding =
                        DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()),
                                R.layout.item_banner, viewGroup, false);
                viewHolder = new BannerVH(bannerBinding, getClickListener());
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindData(RecyclerView.ViewHolder holder, int position, MoviesModel itemVal) {
        switch (getItemViewType(position)) {
            case ITEM:
                ((ItemVH) holder).bindView(itemVal);
                break;
            case LOADING:
                ((FooterProgressVH) holder).bindView();
                break;
            case BANNER:
                ((BannerVH) holder).bindView(itemVal);
                break;
        }
    }


    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return BANNER;
        } else {
            return (position == itemList.size() - 1 && isLoadingAdded) ? LOADING : ITEM;
        }
    }

    public void updateList(List<MoviesModel> moviesList) {
        itemList.addAll(moviesList);
        notifyItemInserted(itemList.size() - 1);
    }

    /**
     * Displays Pagination retry footer view along with appropriate errorMsg
     *
     * @param show:boolean
     * @param errorMsg     to display if page load fails
     */
    public void showRetry(boolean show, @Nullable String errorMsg) {
        retryPageLoad = show;
        notifyItemChanged(itemList.size() - 1);

        if (errorMsg != null)
            this.errorMsg = errorMsg;
    }

    public void addLoadingFooter() {
        isLoadingAdded = true;
        itemList.add(new MoviesModel());
        notifyItemInserted(itemList.size() - 1);
    }

    public void removeLoadingFooter() {
        isLoadingAdded = false;
        if (AppUtils.isListNotEmpty(itemList)) {
            int position = itemList.size() - 1;
            MoviesModel moviesModel = getItem(position);
            if (moviesModel != null) {
                itemList.remove(position);
                notifyItemRemoved(position);
            }
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
            binding.setItemModel(itemModel);
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

    private class FooterProgressVH extends RecyclerView.ViewHolder {
        private final ItemFooterProgressBinding binding;
        private final IItemClick<MoviesModel> iClick;

        private FooterProgressVH(ItemFooterProgressBinding binding, final IItemClick<MoviesModel> iClick) {
            super(binding.getRoot());
            this.binding = binding;
            this.iClick = iClick;
            binding.loadmoreRetry.setOnClickListener(v -> handleRetryScenario());
            binding.loadmoreErrorlayout.setOnClickListener(v -> handleRetryScenario());
        }

        private void handleRetryScenario() {
            if (null != iClick) {
                iClick.retryPageLoad();
                showRetry(false, null);
            }
        }

        private void bindView() {
            if (retryPageLoad) {
                binding.loadmoreErrorlayout.setVisibility(View.VISIBLE);
                binding.loadmoreProgress.setVisibility(View.GONE);
                binding.loadmoreErrortxt.setText(errorMsg != null ? errorMsg
                        : binding.loadmoreErrortxt.getContext().getString(R.string.error_msg_unknown));
            } else {
                binding.loadmoreErrorlayout.setVisibility(View.GONE);
                binding.loadmoreProgress.setVisibility(View.VISIBLE);
            }
        }
    }


}
