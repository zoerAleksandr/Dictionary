package com.example.dictionary.data.retrofit

import com.example.dictionary.domain.entity.Answer
import com.example.dictionary.domain.repository.IRepository
import io.reactivex.rxjava3.core.Observable

class RepositoryRetrofitImpl(private val remoteProvider: RetrofitImpl) :
    IRepository<List<Answer>> {
    override fun getData(text: String): Observable<List<Answer>> {
        return remoteProvider.getData(text)
    }
}