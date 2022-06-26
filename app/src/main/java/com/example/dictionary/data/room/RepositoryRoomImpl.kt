package com.example.dictionary.data.room

import com.example.dictionary.data.room.entity.AnswerWithMeanings
import com.example.dictionary.domain.entity.Meanings
import com.example.dictionary.domain.repository.IRepository

class RepositoryRoomImpl(private val dao: AnswerDAO) : IRepository<List<Meanings>> {
    fun getAllData(): List<Meanings> {
        return dao.getAllData()
    }

    override suspend fun getData(text: String): List<Meanings> {
        return dao.getMeaningsByAnswerText(text)
    }

    fun insertAnswerWithMeanings(answerWithMeanings: AnswerWithMeanings) {
        dao.insertAnswerWithMeanings(answerWithMeanings)
    }
}