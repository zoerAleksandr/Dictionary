package com.example.dictionary.view

import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.dictionary.databinding.ItemBinding
import com.example.dictionary.domain.entity.Meanings

class MainViewHolder(
    private val binding: ItemBinding,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(
        meanings: Meanings?,
        clickListener: (Meanings?) -> Unit
    ) {
        if (meanings != null) {
            binding.translateTextView.text = meanings.translation?.translation.toString()
            binding.transcriptionTextView.text = meanings.transcription.toString()
            binding.translateImageView.load("https:$meanings.previewUrl")
            binding.songImageButton.setOnClickListener {
                clickListener.invoke(meanings)
            }
        }
    }
}