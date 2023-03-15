package com.ardwiinoo.githubuserapp.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ardwiinoo.githubuserapp.R
import com.ardwiinoo.githubuserapp.data.User
import com.ardwiinoo.githubuserapp.databinding.ItemUserRowBinding
import com.ardwiinoo.githubuserapp.view.DetailActivity
import com.bumptech.glide.Glide

class UserListAdapter(private val listUser: List<User>) : RecyclerView.Adapter<UserListAdapter.ViewHolder>() {

    class ViewHolder(var binding: ItemUserRowBinding) :RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemUserRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val users = listUser[position]

        holder.apply {
            binding.apply {
                tvUserUsername.text =users.login
                Glide.with(itemView.context)
                    .load(users.avatarUrl)
                    .error(R.drawable.baseline_person_24)
                    .into(imgUserAvatar)

                // intent
                itemView.setOnClickListener {
                    val intent = Intent(itemView.context, DetailActivity::class.java)
                    intent.putExtra(DetailActivity.EXTRA_USERNAME, users.login)
                    itemView.context.startActivity(intent)
                }
            }
        }
    }

    override fun getItemCount(): Int = listUser.size

}