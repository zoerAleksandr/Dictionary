package com.example.dictionary.data.room

import com.example.dictionary.data.room.converter_entity.toAnswerDTO
import com.example.dictionary.domain.entity.Answer
import com.example.dictionary.domain.repository.IRepository
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single

class RepositoryRoomImpl(private val dao: AnswerDAO) : IRepository<List<Answer>> {
    override fun getData(text: String): Observable<List<Answer>> {
        return dao.getMeaningsListByAnswer(text)
    }

    fun saveAnswerToLocal(answer: Answer): Single<Long> {
        return dao.saveAnswer(toAnswerDTO(answer))
    }
}