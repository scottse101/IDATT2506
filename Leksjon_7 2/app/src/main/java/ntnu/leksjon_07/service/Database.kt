package ntnu.leksjon_07.service

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class Database(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

	companion object {
		const val DATABASE_NAME = "movies.db"
		const val DATABASE_VERSION = 1

		const val TABLE_MOVIES = "Movies"
		const val COL_TITLE = "Title"
		const val COL_DIRECTOR = "Director"
		const val COL_ACTORS = "Actors"
	}

	override fun onCreate(db: SQLiteDatabase) {
		val createMoviesTable = """
            CREATE TABLE $TABLE_MOVIES (
                $COL_TITLE TEXT PRIMARY KEY,
                $COL_DIRECTOR TEXT,
                $COL_ACTORS TEXT
            )
        """
		db.execSQL(createMoviesTable)
	}

	override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
		db.execSQL("DROP TABLE IF EXISTS $TABLE_MOVIES")
		onCreate(db)
	}

	fun insertMovie(title: String, director: String, actors: String) {
		val db = writableDatabase
		val contentValues = ContentValues().apply {
			put(COL_TITLE, title)
			put(COL_DIRECTOR, director)
			put(COL_ACTORS, actors)
		}
		db.insert(TABLE_MOVIES, null, contentValues)
	}

    @SuppressLint("Range")
    fun getAllDirectors(): ArrayList<String> {
		val directors = ArrayList<String>()
		val query = "SELECT DISTINCT $COL_DIRECTOR FROM $TABLE_MOVIES"
		val db = readableDatabase
		val cursor = db.rawQuery(query, null)
		if (cursor.moveToFirst()) {
			do {
				directors.add(cursor.getString(cursor.getColumnIndex(COL_DIRECTOR)))
			} while (cursor.moveToNext())
		}
		cursor.close()
		return directors
	}

    @SuppressLint("Range")
    fun getAllMovies(): ArrayList<String> {
		val movies = ArrayList<String>()
		val query = "SELECT $COL_TITLE FROM $TABLE_MOVIES"
		val db = readableDatabase
		val cursor = db.rawQuery(query, null)
		if (cursor.moveToFirst()) {
			do {
				movies.add(cursor.getString(cursor.getColumnIndex(COL_TITLE)))
			} while (cursor.moveToNext())
		}
		cursor.close()
		return movies
	}

	@SuppressLint("Range")
    fun getAllActors(): ArrayList<String> {
		val actors = ArrayList<String>()
		val query = "SELECT $COL_ACTORS FROM $TABLE_MOVIES"
		val db = readableDatabase
		val cursor = db.rawQuery(query, null)
		if (cursor.moveToFirst()) {
			do {
				actors.add(cursor.getString(cursor.getColumnIndex(COL_ACTORS)))
			} while (cursor.moveToNext())
		}
		cursor.close()
		return actors
	}

    @SuppressLint("Range")
    fun getAllMoviesActorsAndDirectors(): ArrayList<String> {
		val moviesAndActorsAndDirectors = ArrayList<String>()
		val query = "SELECT $COL_TITLE, $COL_DIRECTOR FROM $TABLE_MOVIES"
		val db = readableDatabase
		val cursor = db.rawQuery(query, null)
		if (cursor.moveToFirst()) {
			do {
				val movie = cursor.getString(cursor.getColumnIndex(COL_TITLE))
				val director = cursor.getString(cursor.getColumnIndex(COL_DIRECTOR))
				val actors = cursor.getString(cursor.getColumnIndex(COL_ACTORS))
				getAllMoviesActorsAndDirectors().add("$movie by $actors by $director")
			} while (cursor.moveToNext())
		}
		cursor.close()
		return moviesAndActorsAndDirectors
	}

    @SuppressLint("Range")
    fun getMoviesByDirector(director: String): ArrayList<String> {
		val movies = ArrayList<String>()
		val query = "SELECT $COL_TITLE FROM $TABLE_MOVIES WHERE $COL_DIRECTOR = ?"
		val db = readableDatabase
		val cursor = db.rawQuery(query, arrayOf(director))
		if (cursor.moveToFirst()) {
			do {
				movies.add(cursor.getString(cursor.getColumnIndex(COL_TITLE)))
			} while (cursor.moveToNext())
		}
		cursor.close()
		return movies
	}

    @SuppressLint("Range")
    fun getActorsByMovie(movieTitle: String): ArrayList<String> {
		val actors = ArrayList<String>()
		val query = "SELECT $COL_ACTORS FROM $TABLE_MOVIES WHERE $COL_TITLE = ?"
		val db = readableDatabase
		val cursor = db.rawQuery(query, arrayOf(movieTitle))
		if (cursor.moveToFirst()) {
			actors.add(cursor.getString(cursor.getColumnIndex(COL_ACTORS)))
		}
		cursor.close()
		return actors
	}
}
