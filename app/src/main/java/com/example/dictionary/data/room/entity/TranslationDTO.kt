package com.example.dictionary.data.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TranslationDTO(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val translation: String
)
