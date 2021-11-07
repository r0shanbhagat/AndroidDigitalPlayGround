package com.digital.playground.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.digital.playground.databinding.ListItemMovieBinding
import com.digital.playground.repository.model.MovieModel

class MovieAdapter : RecyclerView.Adapter<MainViewHolder>() {

    var movies = mutableListOf<MovieModel>()

    fun setMovieList(movies: List<MovieModel>) {
        this.movies = movies.toMutableList()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        val binding = ListItemMovieBinding.inflate(inflater, parent, false)
        return MainViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val movie = movies[position]
        holder.binding.name.text = movie.name
        Glide.with(holder.itemView.context).load(movie.imageUrl).into(holder.binding.imageview)

    }

    override fun getItemCount(): Int {
        return movies.size
    }
}

class MainViewHolder(val binding: ListItemMovieBinding) : RecyclerView.ViewHolder(binding.root)