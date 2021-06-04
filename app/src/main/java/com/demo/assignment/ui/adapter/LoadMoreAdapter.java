package com.demo.assignment.ui.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.demo.assignment.R;
import com.demo.assignment.core.BaseAdapter;
import com.demo.assignment.databinding.ListItemLoadMoreBinding;
import com.demo.assignment.repository.model.HerosModel;
import com.demo.assignment.ui.callback.IItemClick;
import com.demo.assignment.util.AppUtils;

import java.util.ArrayList;
import java.util.List;


public class LoadMoreAdapter extends BaseAdapter<LoadMoreAdapter.ItemViewHolder, HerosModel> {

    public LoadMoreAdapter(List<HerosModel> listItems) {
        super(listItems);
    }


    @Override
    public ItemViewHolder setViewHolder(ViewGroup viewGroup, int viewType) {
        ListItemLoadMoreBinding listItemBinding =
                DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()),
                        R.layout.list_item_load_more, viewGroup, false);

        return new ItemViewHolder(listItemBinding, getClickListener());
    }

    @Override
    public void onBindData(ItemViewHolder itemViewHolder, HerosModel model) {
        itemViewHolder.bindView(model);

    }

    public void updateList(List<HerosModel> herosModelList) {
        if (null == itemArrayList) {
            itemArrayList = new ArrayList<>();
        }
        itemArrayList.addAll(herosModelList);
        notifyDataSetChanged();
    }


    static class ItemViewHolder extends RecyclerView.ViewHolder {
        private final ListItemLoadMoreBinding binding;
        private HerosModel itemModel;

        public ItemViewHolder(ListItemLoadMoreBinding viewDataBinding, final IItemClick<HerosModel> holderClick) {
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

        public void bindView(HerosModel itemModel) {
            this.itemModel = itemModel;
            if (itemModel == null) {
                AppUtils.showLog("Holder", "Trying to work on a null object ,returning.");
                return;
            }
            binding.setItemModel(itemModel);
        }
    }


}
