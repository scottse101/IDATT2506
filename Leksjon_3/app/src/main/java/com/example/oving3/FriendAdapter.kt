package com.example.oving3

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.oving3.model.Friend

class FriendAdapter(
    private val friendList: MutableList<Friend>,
    private val onFriendClick: (Friend, Int) -> Unit
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

        holder.itemView.setOnClickListener {
            onFriendClick(friend, position)
        }
    }

    override fun getItemCount(): Int {
        return friendList.size
    }
}
