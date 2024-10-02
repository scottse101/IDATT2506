package com.example.leksjon_4

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private var currentIndex = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun onMovieSelected(movie: Movie) {
        Log.d("MainActivity", "Selected movie: ${movie.title}")
        val detailFragment = supportFragmentManager.findFragmentById(R.id.fragment_detail) as? DetailFragment
        detailFragment?.updateMovieDetails(movie)
    }

    fun showNextMovie() {
        currentIndex = (currentIndex + 1) % MovieData.movieData.size
        onMovieSelected(MovieData.movieData[currentIndex])
    }

    fun showPreviousMovie() {
        currentIndex = if (currentIndex - 1 < 0) MovieData.movieData.size - 1 else currentIndex - 1
        onMovieSelected(MovieData.movieData[currentIndex])
    }
}
