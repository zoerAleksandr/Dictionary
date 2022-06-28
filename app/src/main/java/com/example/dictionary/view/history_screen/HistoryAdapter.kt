package com.example.dictionary.view.history_screen

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.dictionary.databinding.ItemHistoryBinding
import com.example.dictionary.domain.entity.Meanings

class HistoryAdapter(
    private val favoriteListener: (Meanings) -> Unit
) : RecyclerView.Adapter<HistoryViewHolder>() {
    private val list = mutableListOf<Meanings>()

    fun setData(data: List<Meanings>) {
        list.clear()
        list.addAll(data)
        notifyDataSetChanged()
    }

    fun updateMeanings(meanings: Meanings){
        val index = list.indexOf(meanings)
        list[index] = meanings
        notifyItemChanged(index, meanings)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val binding = ItemHistoryBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return HistoryViewHolder(binding, parent.resources)
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        holder.bind(list[position], favoriteListener)
    }

    override fun getItemCount(): Int = list.size
}