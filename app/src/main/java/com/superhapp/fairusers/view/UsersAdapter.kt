package com.superhapp.fairusers.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.superhapp.fairusers.R
import com.superhapp.fairusers.view.model.User
import kotlinx.android.synthetic.main.item_user.view.*

class UsersAdapter(
    private var users: List<User>,
    private val listener: Listener?) : RecyclerView.Adapter<UsersAdapter.UViewHolder>() {

    interface Listener {
        fun onItemClicked(userId: String)
    }

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): UViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_user, parent, false)
        return UViewHolder(view)
    }

    override fun onBindViewHolder(vh: UViewHolder, position: Int) {
        vh.bind(users[position])
        vh.itemView.setOnClickListener {
            listener?.onItemClicked(users[position].id)
        }
    }

    override fun getItemCount(): Int {
        return users.size
    }

    fun update(data: List<User>) {
        users = data
        notifyDataSetChanged()
    }

    class UViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val textViewName: TextView = view.textViewName
        private val textViewEmail: TextView = view.textViewEmail
        private val imageView: ImageView = view.imageView
        fun bind(user: User) {
            "${user.title.capitalize()} ${user.firstName.capitalize()} ${user.lastName.capitalize()}".also { textViewName.text = it }
            textViewEmail.text = user.email
            Picasso.get().load(user.picture).into(imageView)
        }
    }
}