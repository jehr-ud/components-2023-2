package com.ud.listusersapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ud.listuserfront.models.User
import com.ud.listusersapp.R

class UserListAdapter(private val context: Context, private val userList: MutableList<User>) :
    RecyclerView.Adapter<UserListAdapter.ViewHolder>() {

    fun updateUsers(newUsers: List<User>) {
        userList.clear()
        userList.addAll(newUsers)
        notifyDataSetChanged()
    }

    interface OnUserItemClickListener {
        fun onUserItemClick(user: User)
    }

    private var itemClickListener: OnUserItemClickListener? = null

    // Method to set the click listener
    fun setOnUserItemClickListener(listener: OnUserItemClickListener) {
        itemClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.user_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = userList[position]

        holder.textViewId.text = "Username: ${user.id}"
        holder.textViewUsername.text = "Username: ${user.username}"

        holder.itemView.setOnClickListener {
            itemClickListener?.onUserItemClick(user)
        }
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewUsername: TextView = itemView.findViewById(R.id.textViewUsername)
        val textViewId: TextView = itemView.findViewById(R.id.textViewId)
    }
}
