package org.android.go.sopt.home.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.android.go.sopt.databinding.ItemGithubRepoBinding
import org.android.go.sopt.home.data.Repo

class RepoAdapter(context: Context) : RecyclerView.Adapter<RepoAdapter.RepoViewHolder>() {
    private val inflater by lazy { LayoutInflater.from(context) }
    private var repoList: List<Repo> = emptyList()

    class RepoViewHolder(
        private val binding: ItemGithubRepoBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(data: Repo) {
            binding.ivItemGithubRepoImg.setImageDrawable(binding.root.context.getDrawable(data.image))
            binding.tvItemGithubRepoName.text = data.name
            binding.tvItemGithubRepoAuthor.text = data.author
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoAdapter.RepoViewHolder {
        val binding = ItemGithubRepoBinding.inflate(inflater, parent, false)
        return RepoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RepoAdapter.RepoViewHolder, position: Int) {
        holder.onBind(repoList[position])
    }

    override fun getItemCount(): Int = repoList.size

    fun setRepoList(repoList: List<Repo>) {
        this.repoList = repoList.toList()
        notifyDataSetChanged()
    }
}