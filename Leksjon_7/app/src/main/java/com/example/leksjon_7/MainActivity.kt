package com.example.leksjon_7

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.example.leksjon_7.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var movieAdapter: MovieAdapter
    private lateinit var db: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "movies-database"
        ).build()

        movieAdapter = MovieAdapter()
        binding.recyclerView.layoutManager = LinearLayoutManager(this@MainActivity)
        binding.recyclerView.adapter = movieAdapter

        lifecycleScope.launch {
            loadMoviesFromFile()
            displayAllMovies()
        }

        binding.searchButton.setOnClickListener {
            val directorName = binding.directorEditText.text.toString()
            if (directorName.isNotEmpty()) {
                lifecycleScope.launch {
                    searchMoviesByDirector(directorName)
                }
            }
        }
    }

    private suspend fun loadMoviesFromFile() {
        withContext(Dispatchers.IO) {
            val inputStream = resources.openRawResource(R.raw.movies)
            val bufferedReader = inputStream.bufferedReader()
            val movieData = bufferedReader.use { it.readText() }

            val movies = movieData.split("\n\n")
            for (movieEntry in movies) {
                val lines = movieEntry.split("\n")
                val title = lines[0].split("= ")[1]
                val director = lines[1].split("= ")[1]
                val actors = lines[2].split("= ")[1]

                val movie = Movie(0, title, director, actors)
                db.movieDao().insert(movie)
            }
        }
    }

    private suspend fun displayAllMovies() {
        withContext(Dispatchers.IO) {
            val movieList = db.movieDao().getAllMovies()
            withContext(Dispatchers.Main) {
                movieAdapter.submitList(movieList)
            }
        }
    }

    private suspend fun searchMoviesByDirector(directorName: String) {
        withContext(Dispatchers.IO) {
            val movieList = db.movieDao().getMoviesByDirector(directorName)
            withContext(Dispatchers.Main) {
                movieAdapter.submitList(movieList)
            }
        }
    }
}
