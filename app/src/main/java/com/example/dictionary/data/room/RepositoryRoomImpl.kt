package com.example.dictionary.data.room

import com.example.dictionary.data.room.converter_entity.toAnswerDTO
import com.example.dictionary.domain.entity.Answer
import com.example.dictionary.domain.repository.IRepository

class RepositoryRoomImpl(private val dao: AnswerDAO) : IRepository<List<Answer>> {
    override suspend fun getData(text: String): List<Answer> {
        return dao.getMeaningsListByAnswerAsync(text)
    }

    fun saveAnswerToLocal(answer: Answer): Long {
        return dao.saveAnswerAsync(toAnswerDTO(answer))
    }
}