package org.android.go.sopt.home.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import org.android.go.sopt.databinding.ItemSearchEmptyBinding
import org.android.go.sopt.util.extension.ItemDiffCallback

class SearchEmptyAdapter(context: Context) :
    ListAdapter<String, RecyclerView.ViewHolder>(
        ItemDiffCallback<String>(
            onContentsTheSame = { old, new -> old == new },
            onItemsTheSame = { old, new -> old == new }
        )
    ) {
    private val inflater by lazy { LayoutInflater.from(context) }

    class SearchEmptyViewHolder(
        private val binding: ItemSearchEmptyBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(query: String) {
            binding.tvSearchEmptyMessage.text = "${query}에 대한 검색결과가 없습니다."
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchEmptyViewHolder {
        val binding = ItemSearchEmptyBinding.inflate(inflater, parent, false)
        return SearchEmptyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is SearchEmptyViewHolder -> holder.onBind(
                currentList[position]
            )
        }
    }
}