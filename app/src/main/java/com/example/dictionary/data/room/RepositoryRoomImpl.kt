package com.example.dictionary.data.room

import com.example.dictionary.domain.entity.Answer
import com.example.dictionary.domain.repository.IRepository
import io.reactivex.rxjava3.core.Observable

class RepositoryRoomImpl : IRepository<List<Answer>> {
    override fun getData(text: String): Observable<List<Answer>> {
        TODO("Not yet implemented")
    }
}