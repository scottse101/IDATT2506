package ntnu.leksjon_01

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onCreateOptionsMenu(meny: Menu): Boolean {
        super.onCreateOptionsMenu(meny)
        meny.add("Scott")
        meny.add("Emonanekkul")
        Log.d("INFT2501" , "meny laget") //vises i LogCat
        return true // gjor at menyen vil vises
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.title) {
            "Scott" -> {
                Log.w("INFT2051", "Fornavn er trykket av brukeren")
                true
        }
            "Emonanekkul" -> {
            Log.e("INFT2051", "Etternavn er trykket av brukeren")
            true
            }
        else -> super.onOptionsItemSelected(item)
        }
    }
}