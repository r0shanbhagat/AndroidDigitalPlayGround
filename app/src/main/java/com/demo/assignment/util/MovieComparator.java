package com.demo.assignment.util;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import com.demo.assignment.repository.model.MoviesModel;

/*
    Comparator for comparing Movie object to avoid duplicates
 */
public class MovieComparator extends DiffUtil.ItemCallback<MoviesModel> {
    @Override
    public boolean areItemsTheSame(@NonNull MoviesModel oldItem, @NonNull MoviesModel newItem) {
        return oldItem.getId().equals(newItem.getId());
    }

    @Override
    public boolean areContentsTheSame(@NonNull MoviesModel oldItem, @NonNull MoviesModel newItem) {
        return oldItem.getId().equals(newItem.getId());
    }
}
