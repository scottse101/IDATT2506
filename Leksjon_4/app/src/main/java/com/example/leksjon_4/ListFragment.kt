package com.example.leksjon_4

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView

class ListFragment : Fragment() {

    private lateinit var movieList: RecyclerView
    private lateinit var movieAdapter: MovieAdapter
    private val movieData = listOf(
        Movie("Batman Begins", "Description of Batman Begins", R.drawable.batman_begins),
        Movie("The Dark Knight", "Description of The Dark Knight", R.drawable.dark_knight)
        // Add more movies
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_list, container, false)
        movieList = view.findViewById(R.id.recycler_view)
        movieAdapter = MovieAdapter(movieData) { movie ->
            (activity as MainActivity).onMovieSelected(movie)
        }
        movieList.adapter = movieAdapter
        return view
    }
}
