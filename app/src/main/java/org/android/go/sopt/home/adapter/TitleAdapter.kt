package org.android.go.sopt.home.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import org.android.go.sopt.databinding.ItemTitleBinding
import org.android.go.sopt.home.data.Title

class TitleAdapter(context: Context) : ListAdapter<Title, TitleAdapter.TitleViewHolder>(diffUtil) {
    private val inflater by lazy { LayoutInflater.from(context) }

    class TitleViewHolder(
        private val binding: ItemTitleBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(data: Title) {
            binding.tvItemTitle.text = data.title
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TitleViewHolder {
        val binding = ItemTitleBinding.inflate(inflater, parent, false)
        return TitleViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TitleViewHolder, position: Int) {
        holder.onBind(currentList[position])
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<Title>() {
            override fun areItemsTheSame(oldItem: Title, newItem: Title): Boolean {
                return oldItem.title == newItem.title
            }

            override fun areContentsTheSame(oldItem: Title, newItem: Title): Boolean {
                return oldItem == newItem
            }

        }
    }
}