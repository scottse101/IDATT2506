package com.example.leksjon_4

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MovieAdapter(
    private val movieList: List<Movie>,
    private val clickListener: (Movie) -> Unit
) : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.movie_item, parent, false)
        return MovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = movieList[position]
        holder.bind(movie, clickListener)
    }

    override fun getItemCount(): Int {
        return movieList.size
    }

    class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val titleTextView: TextView = itemView.findViewById(R.id.movie_title)

        fun bind(movie: Movie, clickListener: (Movie) -> Unit) {
            titleTextView.text = movie.title
            itemView.setOnClickListener { clickListener(movie) }
        }
    }
}

