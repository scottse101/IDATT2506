package ntnu.leksjon_07

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import ntnu.leksjon_07.databinding.MinLayoutBinding
import ntnu.leksjon_07.managers.FileManager
import ntnu.leksjon_07.managers.MyPreferenceManager
import ntnu.leksjon_07.service.Database
import java.util.*
import java.io.BufferedReader
import java.io.InputStreamReader

class MainActivity : AppCompatActivity() {

	private lateinit var db: Database
	private lateinit var minLayout: MinLayoutBinding

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		minLayout = MinLayoutBinding.inflate(layoutInflater)
		setContentView(minLayout.root)

		db = Database(context = this)

		readMoviesFileAndInsertToDB()

		MyPreferenceManager(activity = this).updateNightMode()
		FileManager(activity = this)
	}

	private fun readMoviesFileAndInsertToDB() {
		try {
			val inputStream = resources.openRawResource(R.raw.movies)
			val reader = BufferedReader(InputStreamReader(inputStream))

			reader.useLines { lines ->
				lines.forEach { line ->
					val parts = line.split(";")
					if (parts.size == 3) {
						val title = parts[0].trim()
						val director = parts[1].trim()
						val actors = parts[2].trim()

						db.insertMovie(title, director, actors)
					}
				}
			}
		} catch (e: Exception) {
			e.printStackTrace()
		}
	}

	private fun showResults(list: ArrayList<String>) {
		val res = StringBuilder()
		for (s in list) {
			res.append("$s\n")
		}
		minLayout.result.text = res.toString()
	}


	override fun onCreateOptionsMenu(menu: Menu): Boolean {
		menuInflater.inflate(R.menu.settings, menu)
		menu.add(0, 1, 0, "All directors")
		menu.add(0, 2, 0, "All movies")
		menu.add(0, 3, 0, "All actors")
		menu.add(0, 4, 0, "All movies, directors and actors")
		menu.add(0, 5, 0, "Movies by director")
		menu.add(0, 6, 0, "Actors by movie")
		return super.onCreateOptionsMenu(menu)
	}

	override fun onOptionsItemSelected(item: MenuItem): Boolean {
		when (item.itemId) {
			R.id.settings -> startActivity(Intent(this, SettingsActivity::class.java))
			1 -> showResults(db.getAllDirectors())
			2 -> showResults(db.getAllMovies())
			3 -> showResults(db.getAllActors())
			4 -> showResults(db.getAllMoviesActorsAndDirectors())
			5 -> showResults(db.getMoviesByDirector("Francis Ford Coppola"))
			6 -> showResults(db.getActorsByMovie("The Godfather"))
			else -> return false
		}
		return super.onOptionsItemSelected(item)
	}

	override fun onResume() {
		super.onResume()

		val myPreferenceManager = MyPreferenceManager(this)
		val selectedColor = myPreferenceManager.getString("backgroundColor", "white")
		val minLayout = findViewById<View>(R.id.minLayout)

		when (selectedColor) {
			"white" -> minLayout.setBackgroundColor(ContextCompat.getColor(this, R.color.white))
			"blue" -> minLayout.setBackgroundColor(ContextCompat.getColor(this, R.color.blue))
			"green" -> minLayout.setBackgroundColor(ContextCompat.getColor(this, R.color.green))
			else -> minLayout.setBackgroundColor(ContextCompat.getColor(this, R.color.white))
		}
		Log.d("MainActivity", "Selected color: $selectedColor")
	}

}
