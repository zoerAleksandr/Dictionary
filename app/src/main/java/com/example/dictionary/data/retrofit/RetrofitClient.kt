package com.example.dictionary.data.retrofit

import com.example.dictionary.domain.entity.Answer
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitClient {
    @GET("words/search")
    fun searchAsync(@Query("search") word: String): Deferred<List<Answer>>
}