package com.example.dictionary.domain.presenter

import io.reactivex.rxjava3.core.Observable

interface Interactor<T : Any> {
    fun getData(text: String, fromRemoteSource: Boolean): Observable<T>
}