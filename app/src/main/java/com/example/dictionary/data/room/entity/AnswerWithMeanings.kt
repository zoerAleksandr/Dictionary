package com.example.dictionary.data.room.entity

import androidx.room.Embedded
import androidx.room.Relation

data class AnswerWithMeanings(
    @Embedded
    val answerDTO: AnswerDTO,

    @Relation(
        parentColumn = "text",
        entityColumn = "answerText"
    )
    val listMeanings: List<MeaningsDTO>
)
