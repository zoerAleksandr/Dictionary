package com.example.dictionary.domain.repository

import io.reactivex.rxjava3.core.Observable

interface IRepository<T> {
    fun getData(text: String): Observable<T>
}