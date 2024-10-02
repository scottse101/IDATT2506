package com.example.leksjon_4

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private var currentIndex = 0

    val movieData = listOf(
        Movie("Batman Begins", "Description of Batman Begins", R.drawable.batman_begins),
        Movie("The Dark Knight", "Description of The Dark Knight", R.drawable.dark_knight),
        Movie("The Prestige", "Description of The Prestige", R.drawable.prestige)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun onMovieSelected(movie: Movie) {
        val detailFragment = supportFragmentManager.findFragmentById(R.id.fragment_detail) as? DetailFragment
        detailFragment?.updateMovieDetails(movie)
    }

    fun showNextMovie() {
        currentIndex = (currentIndex + 1) % movieData.size
        onMovieSelected(movieData[currentIndex])
    }

    fun showPreviousMovie() {
        currentIndex = if (currentIndex - 1 < 0) movieData.size - 1 else currentIndex - 1
        onMovieSelected(movieData[currentIndex])
    }
}
