package com.example.dictionary.di

import com.example.dictionary.data.retrofit.RepositoryRetrofitImpl
import com.example.dictionary.data.room.RepositoryRoomImpl
import com.example.dictionary.domain.repository.IRepository
import org.koin.dsl.module

val repositoryModule = module {
    single<IRepository> { RepositoryRetrofitImpl() }
    single<IRepository> { RepositoryRoomImpl() }
}