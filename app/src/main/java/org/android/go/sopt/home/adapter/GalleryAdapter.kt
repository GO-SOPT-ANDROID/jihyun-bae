package org.android.go.sopt.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.android.go.sopt.databinding.ItemGalleryBinding

class GalleryAdapter(
    _itemList: List<Int> = listOf(),
) : RecyclerView.Adapter<GalleryAdapter.GalleryViewHolder>() {
    private var galleryItemList: List<Int> = _itemList

    class GalleryViewHolder(
        private val binding: ItemGalleryBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(item: Int) {
            binding.ivItemGallery.setImageResource(item)
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): GalleryAdapter.GalleryViewHolder {
        val binding = ItemGalleryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GalleryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GalleryAdapter.GalleryViewHolder, position: Int) {
        holder.onBind(galleryItemList[position])
    }

    override fun getItemCount(): Int = galleryItemList.size

    fun setGalleryItemList(galleryItemList: List<Int>) {
        this.galleryItemList = galleryItemList
        notifyDataSetChanged()
    }
}