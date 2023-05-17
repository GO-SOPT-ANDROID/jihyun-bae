package org.android.go.sopt.home.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import org.android.go.sopt.data.remote.model.ResponseKakaoSearchDto
import org.android.go.sopt.databinding.ItemSearchBinding
import org.android.go.sopt.util.extension.ItemDiffCallback

class SearchAdapter(context: Context) :
    ListAdapter<ResponseKakaoSearchDto.Document, RecyclerView.ViewHolder>(
        ItemDiffCallback<ResponseKakaoSearchDto.Document>(
            onContentsTheSame = { old, new -> old == new },
            onItemsTheSame = { old, new -> old.url == new.url }
        )
    ) {
    private val inflater by lazy { LayoutInflater.from(context) }

    class SearchViewHolder(
        private val binding: ItemSearchBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(document: ResponseKakaoSearchDto.Document) {
            with(binding) {
                tvItemSearchTitle.text = document.title
                tvSearchContents.text = document.contents
                tvSearchUrl.text = document.url
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = ItemSearchBinding.inflate(inflater, parent, false)
        return SearchViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is SearchViewHolder -> holder.onBind(
                currentList[position]
            )
        }
    }
}