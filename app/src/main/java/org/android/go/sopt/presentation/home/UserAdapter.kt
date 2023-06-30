package org.android.go.sopt.presentation.home

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import org.android.go.sopt.data.model.response.ResponseListUsersDto
import org.android.go.sopt.databinding.ItemUserBinding
import org.android.go.sopt.util.extension.ItemDiffCallback

class UserAdapter(context: Context) :
    ListAdapter<ResponseListUsersDto.Data, RecyclerView.ViewHolder>(
        ItemDiffCallback<ResponseListUsersDto.Data>(
            onContentsTheSame = { old, new -> old == new },
            onItemsTheSame = { old, new -> old.id == new.id }
        )
    ) {
    private val inflater by lazy { LayoutInflater.from(context) }

    class UserViewHolder(
        private val binding: ItemUserBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(data: ResponseListUsersDto.Data) {
            binding.user = data
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
}