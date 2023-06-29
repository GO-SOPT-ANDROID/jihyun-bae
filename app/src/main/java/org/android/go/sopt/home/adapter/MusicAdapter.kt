package org.android.go.sopt.home.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import org.android.go.sopt.data.remote.model.ResponseMusicDto
import org.android.go.sopt.databinding.ItemMusicBinding
import org.android.go.sopt.util.extension.ItemDiffCallback

class MusicAdapter(context: Context) :
    ListAdapter<ResponseMusicDto.MusicData, RecyclerView.ViewHolder>(
        ItemDiffCallback<ResponseMusicDto.MusicData>(
            onContentsTheSame = { old, new -> old == new },
            onItemsTheSame = { old, new -> old == new }
        )
    ) {
    private val inflater by lazy { LayoutInflater.from(context) }

    class MusicViewHolder(
        private val binding: ItemMusicBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(data: ResponseMusicDto.MusicData) {
            binding.music = data
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = ItemMusicBinding.inflate(inflater, parent, false)
        return MusicViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is MusicViewHolder -> holder.onBind(
                currentList[position]
            )
        }
    }

}