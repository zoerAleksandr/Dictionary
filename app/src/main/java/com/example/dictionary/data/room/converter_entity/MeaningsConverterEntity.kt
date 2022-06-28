package com.example.dictionary.data.room.converter_entity

import com.example.dictionary.data.room.entity.MeaningsDTO
import com.example.dictionary.domain.entity.Meanings

fun toMeaningsDTO(meanings: Meanings, answerText: String): MeaningsDTO {
    return MeaningsDTO(
        id = meanings.id,
        answerText = answerText,
        translation = meanings.translation,
        previewUrl = meanings.previewUrl,
        transcription = meanings.transcription,
        soundUrl = meanings.soundUrl,
        isFavorite = meanings.isFavorite
    )
}

fun toListMeaningsDTO(listMeanings: List<Meanings>, answerText: String): List<MeaningsDTO> {
    val list = mutableListOf<MeaningsDTO>()
    for (meanings in listMeanings) {
        list.add(toMeaningsDTO(meanings, answerText))
    }
    return list
}