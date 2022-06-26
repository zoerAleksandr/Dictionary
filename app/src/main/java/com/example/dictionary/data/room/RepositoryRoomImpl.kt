package com.example.dictionary.data.room

import com.example.dictionary.data.room.converter_entity.toAnswerDTO
import com.example.dictionary.domain.entity.Answer
import com.example.dictionary.domain.repository.IRepository

class RepositoryRoomImpl(private val dao: AnswerDAO) : IRepository<Answer> {
    fun getAllData(): Answer{
        return dao.getAllData()
    }

    override suspend fun getData(text: String): Answer {
        return dao.getAnswerByTextAsync(text)
    }

    fun saveAnswerToLocal(answer: Answer): Long {
        return dao.saveAnswerAsync(toAnswerDTO(answer))
    }
}