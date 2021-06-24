//package com.demo.assignment.ui.adapter;
//
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//
//import androidx.annotation.Nullable;
//import androidx.databinding.DataBindingUtil;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.demo.assignment.R;
//
//import com.demo.assignment.util.AppUtils;
//
//
//
//public class WordsAdapter extends BaseAdapter<WordsAdapter.ItemVH, MoviesModel> {
//
//    private static final int ITEM = 0;
//    private static final int LOADING = 1;
//    private static final int BANNER = 2;
//    private boolean isLoadingAdded, retryPageLoad;
//    private String errorMsg;
//
//    public WordsAdapter(List<MoviesModel> listItems) {
//        super(listItems);
//    }
//
//
//    @Override
//    public ItemVH setViewHolder(ViewGroup viewGroup, int viewType) {
//        RecyclerView.ViewHolder viewHolder = null;
//        switch (viewType) {
//            case ITEM:
//                ListItemLoadMoreBinding itemBinding =
//                        DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()),
//                                R.layout.list_item_load_more, viewGroup, false);
//
//                viewHolder = new ItemVH(itemBinding, getClickListener());
//                break;
//            case LOADING:
//                ItemFooterProgressBinding footerBinding =
//                        DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()),
//                                R.layout.item_footer_progress, viewGroup, false);
//                viewHolder = new FooterProgressVH(footerBinding, getClickListener());
//                break;
//            case BANNER:
//                ItemBannerBinding bannerBinding =
//                        DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()),
//                                R.layout.item_banner, viewGroup, false);
//                viewHolder = new BannerVH(bannerBinding, getClickListener());
//                break;
//        }
//        return viewHolder;
//    }
//
//    @Override
//    public void onBindData(ItemVH holder, MoviesModel itemVal) {
//        holder.bindView(itemVal);
//    }
//
//
//    /*
//     * Utility Method Starts here
//     */
//    public void updateList(List<MoviesModel> moviesList) {
//        itemList.addAll(moviesList);
//        notifyItemInserted(itemList.size() - 1);
//    }
//
//
//    static class ItemVH extends RecyclerView.ViewHolder {
//        private final ListItemLoadMoreBinding binding;
//        private MoviesModel itemModel;
//
//        private ItemVH(ListItemLoadMoreBinding viewDataBinding, final IItemClick<MoviesModel> holderClick) {
//            super(viewDataBinding.getRoot());
//            binding = viewDataBinding;
//            itemView.setOnClickListener(v -> {
//                if (null == holderClick) {
//                    AppUtils.showLog("Holder", "Trying to work on a null object ,returning.");
//                    return;
//                }
//                holderClick.onItemClick(itemModel);
//            });
//
//        }
//
//        private void bindView(MoviesModel itemModel) {
//            this.itemModel = itemModel;
//            AppUtils.loadImageWithCallback(binding.moviePoster, itemModel.getPosterPath(), () -> {
//                binding.movieProgress.setVisibility(View.GONE);
//            });
//            binding.setItemModel(itemModel);
//        }
//    }
//
//
//}
