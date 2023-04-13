package org.android.go.sopt.home.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.android.go.sopt.databinding.ItemTitleBinding

class TitleAdapter(context: Context) : RecyclerView.Adapter<TitleAdapter.TitleViewHolder>() {
    private val inflater by lazy { LayoutInflater.from(context) }
    private var titleList: List<String> = emptyList()

    class TitleViewHolder(
        private val binding: ItemTitleBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(data: String) {
            binding.tvItemTitle.text = data
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TitleViewHolder {
        val binding = ItemTitleBinding.inflate(inflater, parent, false)
        return TitleViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TitleViewHolder, position: Int) {
        holder.onBind(titleList[position])
    }

    override fun getItemCount(): Int = titleList.size

    fun setTitleList(titleList: List<String>) {
        this.titleList = titleList.toList()
        notifyDataSetChanged()
    }
}