package org.android.go.sopt.home.adapter

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import org.android.go.sopt.databinding.ItemGalleryBinding
import org.android.go.sopt.util.extension.ItemDiffCallback

class GalleryAdapter(context: Context) : ListAdapter<Uri, RecyclerView.ViewHolder>(
    ItemDiffCallback<Uri>(
        onContentsTheSame = { old, new -> old == new },
        onItemsTheSame = { old, new -> old == new }
    )
) {
    private val inflater by lazy { LayoutInflater.from(context) }

    class GalleryViewHolder(
        private val binding: ItemGalleryBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(item: Uri) {
            binding.ivItemGallery.load(item)
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        val binding = ItemGalleryBinding.inflate(inflater, parent, false)
        return GalleryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is GalleryViewHolder -> holder.onBind(
                currentList[position]
            )
        }
    }

    fun addListItem(newItem: Uri) {
        val tempCurrentList = mutableListOf<Uri>()
        tempCurrentList.addAll(currentList)
        tempCurrentList.add(newItem)
        submitList(tempCurrentList)
    }
}