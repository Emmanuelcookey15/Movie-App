package com.emmanuel.cookey.movieapp.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.emmanuel.cookey.movieapp.R
import com.emmanuel.cookey.movieapp.databinding.MovieItemBinding
import com.emmanuel.cookey.movieapp.data.model.Movie

class MovieListAdapter(private val movies: MutableList<Movie>, private var listener: (Movie) -> Unit):
    RecyclerView.Adapter<MovieListAdapter.MovieHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieHolder {
        val binding = MovieItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return MovieHolder(binding)
    }

    override fun getItemCount(): Int {
        return movies.size ?: 0
    }

    fun setMovies(
        movieList: List<Movie>,
        function: () -> Unit
    ) {
        this.movies.clear()
        this.movies.addAll(movieList)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: MovieHolder, position: Int) {
        holder.binding.movieTitle.text = movies[position].title
        Glide.with(holder.binding.root)
            .load(movies[position].getFullImageUrl())
            .error(R.drawable.ic_local_movies_gray)
            .fallback(R.drawable.ic_local_movies_gray)
            .into(holder.binding.movieImage)
        holder.binding.root.setOnClickListener { listener(movies.get(position)) }

    }

    inner class MovieHolder(val binding: MovieItemBinding): RecyclerView.ViewHolder(binding.root) {}

}
