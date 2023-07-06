package org.android.go.sopt.presentation.home

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import org.android.go.sopt.databinding.ItemTitleBinding
import org.android.go.sopt.domain.model.Title
import org.android.go.sopt.util.extension.ItemDiffCallback

class TitleAdapter(context: Context) : ListAdapter<Title, RecyclerView.ViewHolder>(
    ItemDiffCallback<Title>(
        onContentsTheSame = { old, new -> old == new },
        onItemsTheSame = { old, new -> old.title == new.title }
    )
) {
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

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is TitleViewHolder -> holder.onBind(
                currentList[position]
            )
        }
    }
}