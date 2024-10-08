package com.example.leksjon_5

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.*
import java.net.CookieHandler
import java.net.CookieManager
import java.net.HttpURLConnection
import java.net.URL

class MainActivity : AppCompatActivity() {

    private lateinit var nameEditText: EditText
    private lateinit var cardNumberEditText: EditText
    private lateinit var guessEditText: EditText
    private lateinit var startGameButton: Button
    private lateinit var submitGuessButton: Button
    private lateinit var responseTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val cookieManager = CookieManager()
        CookieHandler.setDefault(cookieManager)

        nameEditText = findViewById(R.id.et_name)
        cardNumberEditText = findViewById(R.id.et_card_number)
        guessEditText = findViewById(R.id.et_guess)
        startGameButton = findViewById(R.id.btn_start_game)
        submitGuessButton = findViewById(R.id.btn_submit_guess)
        responseTextView = findViewById(R.id.tv_response)

        startGameButton.setOnClickListener {
            val name = nameEditText.text.toString()
            val cardNumber = cardNumberEditText.text.toString()

            if (name.isNotEmpty() && cardNumber.length == 16) {
                startGame(name, cardNumber)
            } else {
                Toast.makeText(this, "Vennligst fyll ut navn og kortnummer", Toast.LENGTH_SHORT).show()
            }
        }

        submitGuessButton.setOnClickListener {
            val guess = guessEditText.text.toString()
            if (guess.isNotEmpty()) {
                submitGuess(guess)
            } else {
                Toast.makeText(this, "Skriv inn et tall for å tippe", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun startGame(name: String, cardNumber: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val urlString = "https://bigdata.idi.ntnu.no/mobil/tallspill.jsp?navn=$name&kortnummer=$cardNumber"
            val url = URL(urlString)

            val response = url.readText()
            withContext(Dispatchers.Main) {
                if (response.contains("Oppgi et tall mellom")) {
                    guessEditText.visibility = View.VISIBLE
                    submitGuessButton.visibility = View.VISIBLE
                }
                responseTextView.text = response
            }
        }
    }

    private fun submitGuess(guess: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val urlString = "https://bigdata.idi.ntnu.no/mobil/tallspill.jsp?tall=$guess"
            val url = URL(urlString)

            val response = url.readText()
            withContext(Dispatchers.Main) {
                responseTextView.text = response
                if (response.contains("vunnet")) {
                    guessEditText.visibility = View.GONE
                    submitGuessButton.visibility = View.GONE
                }
            }
        }
    }
}
