package com.example.dictionary.data.room.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey
import com.example.dictionary.domain.entity.Translation
import org.jetbrains.annotations.NotNull

@Entity(
    tableName = "meanings",
    foreignKeys = [
        ForeignKey(
            entity = AnswerDTO::class,
            parentColumns = ["text"],
            childColumns = ["answerText"],
            onDelete = CASCADE,
            onUpdate = CASCADE
        )
    ]
)
data class MeaningsDTO(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    @NotNull
    val answerText: String,
    val translation: Translation?,
    val previewUrl: String?,
    val transcription: String?,
    val soundUrl: String?
)