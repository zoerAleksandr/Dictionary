package com.example.dictionary.data.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.dictionary.domain.entity.Meanings

@Entity(tableName = "answer")
data class AnswerDTO(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val text: String,
    val meanings: List<Meanings>?
)