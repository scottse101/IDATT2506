package com.example.leksjon_4

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import android.widget.ImageView
import android.widget.TextView

class DetailFragment : Fragment() {

    private lateinit var movieTitle: TextView
    private lateinit var movieDescription: TextView
    private lateinit var movieImage: ImageView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_detail, container, false)
        movieTitle = view.findViewById(R.id.movie_title)
        movieDescription = view.findViewById(R.id.movie_description)
        movieImage = view.findViewById(R.id.movie_image)
        return view
    }

    fun updateMovieDetails(movie: Movie) {
        movieTitle.text = movie.title
        movieDescription.text = movie.description
        movieImage.setImageResource(movie.imageResId)
    }
}
