package com.example.leksjon_4

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun onMovieSelected(movie: Movie) {
        Log.d("MainActivity", "Selected movie: ${movie.title}")

        val detailFragment = supportFragmentManager.findFragmentById(R.id.fragment_detail) as DetailFragment?
        detailFragment?.updateMovieDetails(movie)
    }
}
