package org.android.go.sopt.home.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.ViewGroup
import androidx.recyclerview.selection.ItemDetailsLookup
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import org.android.go.sopt.databinding.ItemGithubRepoBinding
import org.android.go.sopt.databinding.ItemTitleBinding
import org.android.go.sopt.home.data.Home

class HomeAdapter(context: Context) : ListAdapter<Home, RecyclerView.ViewHolder>(diffUtil) {
    private val inflater by lazy { LayoutInflater.from(context) }
    lateinit var selectionTracker: SelectionTracker<Long>

    init {
        setHasStableIds(true)
    }

    class RepoViewHolder(
        private val binding: ItemGithubRepoBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(data: Home, isActivated: Boolean = false) {
            binding.ivItemGithubRepoImg.setImageDrawable(binding.root.context.getDrawable(data.image))
            binding.tvItemGithubRepoName.text = data.name
            binding.tvItemGithubRepoAuthor.text = data.author
            itemView.isActivated = isActivated
        }

        fun getItemDetails(): ItemDetailsLookup.ItemDetails<Long> =
            object : ItemDetailsLookup.ItemDetails<Long>() {
                override fun getPosition(): Int = bindingAdapterPosition
                override fun getSelectionKey(): Long = itemId
            }
    }

    class TitleViewHolder(
        private val binding: ItemTitleBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun getItemDetails(): ItemDetailsLookup.ItemDetails<Long> =
            object : ItemDetailsLookup.ItemDetails<Long>() {
                override fun getPosition(): Int = bindingAdapterPosition
                override fun getSelectionKey(): Long = itemId
            }
    }

    class HomeDetailLookUp(private val recyclerView: RecyclerView) :
        ItemDetailsLookup<Long>() {
        override fun getItemDetails(event: MotionEvent): ItemDetails<Long>? {
            val view = recyclerView.findChildViewUnder(event.x, event.y)
            if (view != null) {
                if (recyclerView.getChildViewHolder(view) is RepoViewHolder) {
                    return (recyclerView.getChildViewHolder(view) as RepoViewHolder)
                        .getItemDetails()
                } else {
                    return (recyclerView.getChildViewHolder(view) as TitleViewHolder)
                        .getItemDetails()
                }
            }
            return null
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_TITLE -> {
                val binding = ItemTitleBinding.inflate(inflater, parent, false)
                TitleViewHolder(binding)
            }
            else -> {
                val binding = ItemGithubRepoBinding.inflate(inflater, parent, false)
                RepoViewHolder(binding)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        selectionTracker?.let {
            when (holder) {
                is RepoViewHolder -> holder.onBind(
                    currentList[position],
                    it.isSelected(position.toLong())
                )
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (position) {
            0 -> VIEW_TYPE_TITLE
            else -> VIEW_TYPE_REPO
        }
    }

    override fun getItemId(position: Int): Long = position.toLong()

    fun removeListItem(itemIndex: Int) {
        val tempCurrentList = mutableListOf<Home>()
        tempCurrentList.addAll(currentList)
        tempCurrentList.removeAt(itemIndex)
        submitList(tempCurrentList)
    }

    fun addListItem(newItem: Home) {
        val tempCurrentList = mutableListOf<Home>()
        tempCurrentList.addAll(currentList)
        tempCurrentList.add(newItem)
        submitList(tempCurrentList)
    }

    companion object {
        const val VIEW_TYPE_TITLE = 0
        const val VIEW_TYPE_REPO = 1

        val diffUtil = object : DiffUtil.ItemCallback<Home>() {
            override fun areItemsTheSame(oldItem: Home, newItem: Home): Boolean {
                return oldItem.name == newItem.name
            }

            override fun areContentsTheSame(oldItem: Home, newItem: Home): Boolean {
                return oldItem == newItem
            }
        }
    }
}