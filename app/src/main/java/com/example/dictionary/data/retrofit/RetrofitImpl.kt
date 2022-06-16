package com.example.dictionary.data.retrofit

import com.example.dictionary.BuildConfig
import com.example.dictionary.domain.entity.Answer
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val BASE_URL = "https://dictionary.skyeng.ru/api/public/v1/"

class RetrofitImpl {

    suspend fun getData(word: String): List<Answer> {
        return createRetrofit().create(RetrofitClient::class.java).searchAsync(word).await()
    }

    private fun createRetrofit(): Retrofit {
        return if (BuildConfig.isDebug) {
            createRetrofitWithLog()
        } else {
            createRetrofitWithOutLog()
        }
    }

    private fun createRetrofitWithLog(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(
                GsonConverterFactory.create(
                    GsonBuilder().setLenient().create()
                )
            )
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .client(createClient())
            .build()
    }

    private fun createRetrofitWithOutLog(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(
                GsonConverterFactory.create(
                    GsonBuilder().setLenient().create()
                )
            )
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()
    }

    private fun createClient(): OkHttpClient {
        val loggingInterceptor =
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor(loggingInterceptor)
        return httpClient.build()
    }
}