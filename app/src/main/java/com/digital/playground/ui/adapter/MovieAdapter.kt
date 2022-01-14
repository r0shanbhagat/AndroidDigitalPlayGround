package com.digital.playground.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.digital.playground.databinding.ListItemMovieBinding
import com.digital.playground.repository.model.Movie
import com.digital.playground.util.AppUtils
/**
 * @Details MovieAdapter
 * @Author Roshan Bhagat
 */
class MovieAdapter : RecyclerView.Adapter<MainViewHolder>() {

    var movies = mutableListOf<Movie>()

    fun setMovieList(updatedMovies: List<Movie>?) {
        this.movies = updatedMovies?.toMutableList()!!
        notifyDataSetChanged()
    }


    fun clearList() {
        if (AppUtils.isListNotEmpty(movies)) {
            movies.clear()
            notifyDataSetChanged()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        val binding = ListItemMovieBinding.inflate(inflater, parent, false)
        return MainViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val movie = movies[position]
        holder.binding.name.text = movie.title
        Glide.with(holder.itemView.context).load(movie.image).into(holder.binding.imageview)

    }

    override fun getItemCount(): Int {
        return movies.size
    }
}

class MainViewHolder(val binding: ListItemMovieBinding) : RecyclerView.ViewHolder(binding.root)