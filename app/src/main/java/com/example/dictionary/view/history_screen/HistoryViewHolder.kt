package com.example.dictionary.view.history_screen

import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.dictionary.databinding.ItemHistoryBinding
import com.example.dictionary.domain.entity.Meanings

class HistoryViewHolder(private val binding: ItemHistoryBinding) :
    RecyclerView.ViewHolder(binding.root) {
        fun bind(meanings: Meanings){
            binding.translateTextView.text = meanings.translation?.translation.toString()
            binding.transcriptionTextView.text = meanings.transcription.toString()
            binding.translateImageView.load("https:${meanings.previewUrl}")
        }
}