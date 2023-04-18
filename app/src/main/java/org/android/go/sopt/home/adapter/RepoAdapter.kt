package org.android.go.sopt.home.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.selection.ItemDetailsLookup
import androidx.recyclerview.selection.ItemKeyProvider
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import org.android.go.sopt.databinding.ItemGithubRepoBinding
import org.android.go.sopt.home.data.Repo

class RepoAdapter(context: Context) : ListAdapter<Repo, RepoAdapter.RepoViewHolder>(diffUtil) {
    private val inflater by lazy { LayoutInflater.from(context) }
    lateinit var selectionTracker: SelectionTracker<Long>

    init {
        setHasStableIds(true)
    }

    class RepoViewHolder(
        private val binding: ItemGithubRepoBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(data: Repo, selectionTracker: SelectionTracker<Long>) = with(itemView) {
            binding.ivItemGithubRepoImg.setImageDrawable(binding.root.context.getDrawable(data.image))
            binding.tvItemGithubRepoName.text = data.name
            binding.tvItemGithubRepoAuthor.text = data.author
            bindSelectedState(this, selectionTracker.isSelected(data.id.toLong()))
        }

        private fun bindSelectedState(view: View, selected: Boolean) {
            view.isActivated = selected
        }

        fun getItemDetails(): ItemDetailsLookup.ItemDetails<Long> =
            object : ItemDetailsLookup.ItemDetails<Long>() {
                override fun getPosition(): Int = bindingAdapterPosition
                override fun getSelectionKey(): Long =
                    (bindingAdapter as RepoAdapter).currentList[bindingAdapterPosition].id.toLong()
            }
    }

    class SelectionDetailLookUp(private val recyclerView: RecyclerView) :
        ItemDetailsLookup<Long>() {
        override fun getItemDetails(e: MotionEvent): ItemDetails<Long>? {
            val view = recyclerView.findChildViewUnder(e.x, e.y)
            if (view != null) {
                return (recyclerView.getChildViewHolder(view) as RepoViewHolder)
                    .getItemDetails()
            }
            return null
        }
    }

    class RepoKeyProvider(private val adapter: RepoAdapter) : ItemKeyProvider<Long>(
        SCOPE_CACHED
    ) {
        override fun getKey(position: Int): Long = adapter.currentList[position].id.toLong()
        override fun getPosition(key: Long): Int =
            adapter.currentList.indexOfFirst { it.id.toLong() == key }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoViewHolder {
        val binding = ItemGithubRepoBinding.inflate(inflater, parent, false)
        return RepoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RepoViewHolder, position: Int) {
        holder.onBind(currentList[position], selectionTracker)
    }

    override fun getItemId(position: Int): Long = position.toLong()

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<Repo>() {
            override fun areItemsTheSame(oldItem: Repo, newItem: Repo): Boolean {
                return oldItem.name == newItem.name
            }

            override fun areContentsTheSame(oldItem: Repo, newItem: Repo): Boolean {
                return oldItem == newItem
            }
        }
    }
}