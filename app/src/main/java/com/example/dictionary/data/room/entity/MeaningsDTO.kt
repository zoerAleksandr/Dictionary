package com.example.dictionary.data.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.dictionary.domain.entity.Translation

@Entity
data class MeaningsDTO(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val translation: Translation?,
    val previewUrl: String?,
    val transcription: String?,
    val soundUrl: String?
)