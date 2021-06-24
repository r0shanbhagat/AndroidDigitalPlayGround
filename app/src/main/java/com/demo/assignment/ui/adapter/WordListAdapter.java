package com.demo.assignment.ui.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.demo.assignment.R;
import com.demo.assignment.database.Word;
import com.demo.assignment.databinding.ListItemWordBinding;

import org.jetbrains.annotations.NotNull;

public class WordListAdapter extends ListAdapter<Word, WordListAdapter.WordVH> {

    public WordListAdapter(@NonNull DiffUtil.ItemCallback<Word> diffCallback) {
        super(diffCallback);
    }

    @NotNull
    @Override
    public WordVH onCreateViewHolder(@NotNull ViewGroup viewGroup, int viewType) {
        ListItemWordBinding binding =
                DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()),
                        R.layout.list_item_word, viewGroup, false);
        return new WordVH(binding);
    }

    @Override
    public void onBindViewHolder(WordVH holder, int position) {
        Word current = getItem(position);
        holder.bindView(current.getWord());
    }


    static class WordVH extends RecyclerView.ViewHolder {
        private final ListItemWordBinding binding;

        private WordVH(ListItemWordBinding viewDataBinding) {
            super(viewDataBinding.getRoot());
            binding = viewDataBinding;
        }

        private void bindView(String word) {
            binding.textView.setText(word);
        }
    }
}