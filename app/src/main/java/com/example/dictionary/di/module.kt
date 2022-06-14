package com.example.dictionary.di

import com.example.dictionary.data.retrofit.RepositoryRetrofitImpl
import com.example.dictionary.data.retrofit.RetrofitImpl
import org.koin.dsl.module

val module = module {
    single { RetrofitImpl() }
    single { RepositoryRetrofitImpl(remoteProvider = get()) }
}