package com.example.dictionary.view.favorite_screen

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.dictionary.databinding.ItemHistoryBinding
import com.example.dictionary.domain.entity.Meanings

class FavoriteAdapter(private val favoriteListener: (Meanings) -> Unit) :
    RecyclerView.Adapter<FavoriteViewHolder>() {
    private val list = mutableListOf<Meanings>()

    fun setData(data: List<Meanings>) {
        list.clear()
        list.addAll(data)
        notifyDataSetChanged()
    }

    fun removeMeanings(meanings: Meanings) {
        val index = list.indexOf(meanings)
        list.removeAt(index)
        notifyItemRemoved(index)
    }

    fun changeIconFavorite(meanings: Meanings){
        val index = list.indexOf(meanings)
        notifyItemChanged(index)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val binding = ItemHistoryBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return FavoriteViewHolder(binding, parent.resources)
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        holder.bind(list[position], favoriteListener)
    }

    override fun getItemCount(): Int = list.size
}