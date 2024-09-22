package com.example.oving2

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import kotlin.random.Random


class MainActivity : Activity() {

    private val REQUEST_CODE = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val number1TextView: TextView = findViewById(R.id.number1TextView)
        val number2TextView: TextView = findViewById(R.id.number2TextView)
        val userAnswerEditText: EditText = findViewById(R.id.userAnswerEditText)
        val upperLimitEditText: EditText = findViewById(R.id.upperLimitEditText)

        val addButton: Button = findViewById(R.id.addButton)
        val multiplyButton: Button = findViewById(R.id.multiplyButton)

        fun updateRandomNumbers() {
            val upperLimitString = upperLimitEditText.text.toString()
            val upperLimit = if (upperLimitString.isNotEmpty()) upperLimitString.toInt() else 100

            val randomNumber1 = Random.nextInt(from = 1, until = upperLimit + 1)
            val randomNumber2 = Random.nextInt(from = 1, until = upperLimit + 1)

            number1TextView.text = randomNumber1.toString()
            number2TextView.text = randomNumber2.toString()
        }

        addButton.setOnClickListener {
            val number1 = number1TextView.text.toString().toInt()
            val number2 = number2TextView.text.toString().toInt()
            val userAnswerString = userAnswerEditText.text.toString()
            val userAnswer = if (userAnswerString.isNotEmpty()) userAnswerString.toInt() else 0

            val correctAnswer = number1 + number2

            if (userAnswer == correctAnswer) {
                Toast.makeText(this, "Riktig", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Feil. Riktig svar er $correctAnswer", Toast.LENGTH_SHORT).show()
            }

            updateRandomNumbers()
        }

        multiplyButton.setOnClickListener {
            val number1 = number1TextView.text.toString().toInt()
            val number2 = number2TextView.text.toString().toInt()
            val userAnswerString = userAnswerEditText.text.toString()
            val userAnswer = if (userAnswerString.isNotEmpty()) userAnswerString.toInt() else 0

            val correctAnswer = number1 * number2

            if (userAnswer == correctAnswer) {
                Toast.makeText(this, "Riktig", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Feil. Riktig svar er $correctAnswer", Toast.LENGTH_SHORT).show()
            }

            updateRandomNumbers()
        }

        val randomValue = Random.nextInt(from = 0, until = 101)

        val intent = Intent(this, SecondActivity::class.java)
        intent.putExtra("RANDOM_VALUE", randomValue)
        startActivityForResult(intent, REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            val result = data?.getIntExtra("RESULT", 0)
            if (result != null) {
                Toast.makeText(this, "Resultat fra aktivitet: $result", Toast.LENGTH_LONG).show()

                val resultTextView: TextView = findViewById(R.id.resultTextView)
                resultTextView.text = "Resultat: $result"
            }
        }

    }
}

