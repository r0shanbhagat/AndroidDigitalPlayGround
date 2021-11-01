package com.digital.playground.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.paging.LoadState;
import androidx.paging.LoadStateAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.digital.playground.R;
import com.digital.playground.databinding.ItemFooterProgressBinding;
import com.digital.playground.repository.model.MoviesModel;
import com.digital.playground.ui.callback.IItemClick;
import com.digital.playground.util.AppUtils;

import org.jetbrains.annotations.NotNull;


public class MoviesLoadStateAdapter extends LoadStateAdapter<MoviesLoadStateAdapter.FooterProgressVH> {
    private final IItemClick<MoviesModel> itemClick;

    public MoviesLoadStateAdapter(IItemClick<MoviesModel> itemClick) {
        this.itemClick = itemClick;
    }

    @NotNull
    @Override
    public FooterProgressVH onCreateViewHolder(@NotNull ViewGroup viewGroup, @NotNull LoadState loadState) {
        // Return new LoadStateViewHolder object
        ItemFooterProgressBinding footerBinding =
                DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()),
                        R.layout.item_footer_progress, viewGroup, false);
        return new FooterProgressVH(footerBinding, itemClick);
    }

    @Override
    public void onBindViewHolder(@NotNull FooterProgressVH holder,
                                 @NotNull LoadState loadState) {
        holder.bindView(loadState);
    }

    static class FooterProgressVH extends RecyclerView.ViewHolder {
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
            }
        }

        private void bindView(LoadState loadState) {
            if (loadState instanceof LoadState.Error) {
                LoadState.Error loadStateError = (LoadState.Error) loadState;
                binding.loadmoreErrorlayout.setVisibility(View.VISIBLE);
                binding.loadmoreErrortxt.setText(AppUtils.fetchErrorMessage(binding.loadmoreErrortxt.getContext(),
                        loadStateError.getError()));
                binding.loadmoreProgress.setVisibility(View.GONE);
            } else {
                //Loading
                binding.loadmoreErrorlayout.setVisibility(View.GONE);
                binding.loadmoreProgress.setVisibility(View.VISIBLE);
            }
        }
    }
}