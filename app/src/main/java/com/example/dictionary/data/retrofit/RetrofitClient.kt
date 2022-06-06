package com.example.dictionary.data.retrofit

import com.example.dictionary.domain.entity.Answer
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitClient {
    @GET("words/search")
    fun search(@Query("search") word: String): Observable<List<Answer>>
}