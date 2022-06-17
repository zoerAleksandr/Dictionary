package com.example.dictionary.domain.repository

interface IRepository<T> {
    suspend fun getData(text: String): T
}