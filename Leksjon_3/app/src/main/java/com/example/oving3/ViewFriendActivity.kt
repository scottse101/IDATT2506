package com.example.oving3

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.oving3.model.Friend

class ViewFriendActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_friend)

        // Hent vennedata fra Intent
        val friendName = intent.getStringExtra("friend_name")
        val friendBirthDate = intent.getStringExtra("friend_birth_date")

        // Koble til TextViews
        val nameTextView = findViewById<TextView>(R.id.tv_view_friend_name)
        val birthDateTextView = findViewById<TextView>(R.id.tv_view_friend_birth_date)

        // Vis vennens informasjon
        nameTextView.text = friendName
        birthDateTextView.text = friendBirthDate
    }
}
