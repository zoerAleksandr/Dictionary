package com.example.dictionary.view.history_screen

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.dictionary.databinding.ItemHistoryBinding
import com.example.dictionary.domain.entity.Answer
import com.example.dictionary.domain.entity.Meanings

class HistoryAdapter : RecyclerView.Adapter<HistoryViewHolder>() {
    private val list = mutableListOf<Meanings>()

    fun setData(data: List<Answer>) {
        list.clear()
        for (answer in data) {
            answer.meanings?.get(0)?.let {
                list.add(it)
            }
        }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val binding = ItemHistoryBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return HistoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int = list.size
}