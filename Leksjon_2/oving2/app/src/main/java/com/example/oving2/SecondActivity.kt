package com.example.oving2

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast

class SecondActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        val randomValue = intent.getIntExtra("RANDOM_VALUE", 0)

        val resultIntent = Intent()
        resultIntent.putExtra("RESULT", randomValue)
        setResult(Activity.RESULT_OK, resultIntent)

        finish()

        Toast.makeText(applicationContext, "Mottatt tilfeldig tall: $randomValue", Toast.LENGTH_LONG).show()
    }
}

