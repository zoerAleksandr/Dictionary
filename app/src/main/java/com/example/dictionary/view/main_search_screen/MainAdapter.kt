package com.example.dictionary.view.main_search_screen

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.dictionary.databinding.ItemBinding
import com.example.dictionary.domain.entity.Meanings

class MainAdapter(
    private val clickListener: (Meanings?) -> Unit
) : RecyclerView.Adapter<MainViewHolder>() {
    private var meanings: List<Meanings>? = listOf()

    fun setData(data: List<Meanings>?) {
        meanings = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val binding = ItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return MainViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.bind(meanings?.get(position), clickListener)
    }

    override fun getItemCount(): Int = meanings?.size ?: 0
}