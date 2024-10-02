package com.example.leksjon_4

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ListFragment : Fragment() {

    private lateinit var movieList: RecyclerView
    private lateinit var movieAdapter: MovieAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_list, container, false)

        movieList = view.findViewById(R.id.recycler_view)

        movieList.layoutManager = LinearLayoutManager(context)
        Log.d("ListFragment", "MovieData size: ${MovieData.movieData.size}")

        movieAdapter = MovieAdapter(MovieData.movieData) { movie ->
            (activity as MainActivity).onMovieSelected(movie)
        }

        movieList.adapter = movieAdapter

        return view
    }
}



