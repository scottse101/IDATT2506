package com.example.oving3

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.oving3.model.Friend

class FriendAdapter(
    private val friendList: MutableList<Friend>,
    private val onFriendClick: (Friend, Int) -> Unit  // Funksjon som kalles ved klikk på venn
) : RecyclerView.Adapter<FriendAdapter.FriendViewHolder>() {

    class FriendViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameTextView: TextView = itemView.findViewById(R.id.tv_friend_name)
        val birthDateTextView: TextView = itemView.findViewById(R.id.tv_friend_birth_date)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.friend_list_item, parent, false)
        return FriendViewHolder(view)
    }

    override fun onBindViewHolder(holder: FriendViewHolder, position: Int) {
        val friend = friendList[position]
        holder.nameTextView.text = friend.name
        holder.birthDateTextView.text = friend.birthDate

        // Klikk på venn-elementet
        holder.itemView.setOnClickListener {
            val context = holder.itemView.context
            val intent = Intent(context, ViewFriendActivity::class.java)
            intent.putExtra("friend_name", friend.name)
            intent.putExtra("friend_birth_date", friend.birthDate)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return friendList.size
    }
}
