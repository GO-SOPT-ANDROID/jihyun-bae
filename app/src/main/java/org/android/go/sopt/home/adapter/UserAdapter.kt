package org.android.go.sopt.home.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import org.android.go.sopt.data.remote.model.ResponseListUsersDto
import org.android.go.sopt.databinding.ItemUserBinding

class UserAdapter(context: Context) :
    ListAdapter<ResponseListUsersDto.Data, RecyclerView.ViewHolder>(diffUtil) {
    private val inflater by lazy { LayoutInflater.from(context) }

    class UserViewHolder(
        private val binding: ItemUserBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(data: ResponseListUsersDto.Data) {
            with(binding) {
                ivItemUserImg.load(data.avatar)
                tvItemUserName.text = "${data.first_name} ${data.last_name}"
                tvItemUserEmail.text = data.email
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val binding = ItemUserBinding.inflate(inflater, parent, false)
        return UserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is UserViewHolder -> holder.onBind(
                currentList[position]
            )
        }
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<ResponseListUsersDto.Data>() {
            override fun areItemsTheSame(
                oldItem: ResponseListUsersDto.Data,
                newItem: ResponseListUsersDto.Data
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: ResponseListUsersDto.Data,
                newItem: ResponseListUsersDto.Data
            ): Boolean {
                return oldItem == newItem
            }

        }
    }
}