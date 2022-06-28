package com.example.dictionary.view.history_screen

import android.content.res.Resources
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.dictionary.R
import com.example.dictionary.databinding.ItemHistoryBinding
import com.example.dictionary.domain.entity.Meanings
import com.example.dictionary.view.favorite_screen.IS_FAVORITE
import com.example.dictionary.view.favorite_screen.IS_NOT_FAVORITE

class HistoryViewHolder(
    private val binding: ItemHistoryBinding,
    private val resources: Resources
) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(meanings: Meanings, favoriteListener: (Meanings) -> Unit) {
        binding.answerTextTextView.text = meanings.answerText
        binding.translateTextView.text = meanings.translation?.translation.toString()
        binding.transcriptionTextView.text = meanings.transcription.toString()
        binding.translateImageView.load("https:${meanings.previewUrl}")
        binding.favoriteIcon.setOnClickListener {
            favoriteListener(meanings)
        }
        when (meanings.isFavorite) {
            IS_FAVORITE -> {
                binding.favoriteIcon.setImageDrawable(
                    resources.getDrawable(
                        R.drawable.ic_baseline_favorite_24,
                        null
                    )
                )
            }
            IS_NOT_FAVORITE -> {
                binding.favoriteIcon.setImageDrawable(
                    resources.getDrawable(
                        R.drawable.ic_baseline_favorite_border_24,
                        null
                    )
                )
            }
        }
    }
}