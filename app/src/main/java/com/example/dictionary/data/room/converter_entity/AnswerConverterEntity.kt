package com.example.dictionary.data.room.converter_entity

import com.example.dictionary.data.room.entity.AnswerDTO
import com.example.dictionary.domain.entity.Answer

fun toAnswer(answerDTO: AnswerDTO): Answer{
    return Answer(
        id = answerDTO.id,
        text = answerDTO.text,
        meanings = answerDTO.meanings
    )
}

fun toAnswerDTO(answer: Answer): AnswerDTO{
    return AnswerDTO(
        id = answer.id,
        text = answer.text,
        meanings = answer.meanings
    )
}