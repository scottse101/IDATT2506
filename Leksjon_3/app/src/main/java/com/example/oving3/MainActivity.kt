package com.example.oving3

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import com.example.oving3.model.Friend

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var friendAdapter: FriendAdapter
    private lateinit var addButton: Button
    private lateinit var nameEditText: EditText
    private lateinit var birthDateEditText: EditText

    private val friendList = mutableListOf<Friend>()

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_friend)

        recyclerView = findViewById(R.id.rv_friend_list)
        addButton = findViewById(R.id.btn_add_friend)
        nameEditText = findViewById(R.id.et_name)
        birthDateEditText = findViewById(R.id.et_birth_date)

        recyclerView.layoutManager = LinearLayoutManager(this)
        friendAdapter = FriendAdapter(friendList) { friend, position ->
            showEditFriendDialog(friend, position)
        }

        recyclerView.adapter = friendAdapter

        addButton.setOnClickListener {
            val name = nameEditText.text.toString()
            val birthDate = birthDateEditText.text.toString()

            if (name.isNotBlank() && birthDate.isNotBlank()) {
                val newFriend = Friend(name, birthDate)
                friendList.add(newFriend)
                friendAdapter.notifyItemInserted(friendList.size - 1)
                nameEditText.text.clear()
                birthDateEditText.text.clear()
            }
        }
    }

    private fun showEditFriendDialog(friend: Friend, position: Int) {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_edit_friend, null)
        val nameEditText = dialogView.findViewById<EditText>(R.id.et_edit_name)
        val birthDateEditText = dialogView.findViewById<EditText>(R.id.et_edit_birth_date)

        nameEditText.setText(friend.name)
        birthDateEditText.setText(friend.birthDate)

        AlertDialog.Builder(this)
            .setTitle("Edit Friend")
            .setView(dialogView)
            .setPositiveButton("Save") { _, _ ->
                friend.name = nameEditText.text.toString()
                friend.birthDate = birthDateEditText.text.toString()
                friendAdapter.notifyItemChanged(position)
            }
            .setNegativeButton("Cancel", null)
            .show()
    }
}
