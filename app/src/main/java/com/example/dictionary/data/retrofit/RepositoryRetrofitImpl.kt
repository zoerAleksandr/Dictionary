package com.example.dictionary.data.retrofit

import com.example.dictionary.domain.entity.Answer
import com.example.dictionary.domain.repository.IRepository

class RepositoryRetrofitImpl(private val remoteProvider: RetrofitImpl) :
    IRepository<List<Answer>> {
    override suspend fun getData(text: String): List<Answer> {
        return remoteProvider.getData(text)
    }
}