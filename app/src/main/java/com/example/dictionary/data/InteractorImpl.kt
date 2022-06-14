package com.example.dictionary.data

import com.example.dictionary.data.retrofit.RepositoryRetrofitImpl
import com.example.dictionary.data.room.RepositoryRoomImpl
import com.example.dictionary.domain.presenter.Interactor
import com.example.dictionary.view.AppState
import io.reactivex.rxjava3.core.Observable
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class InteractorImpl : Interactor<AppState>, KoinComponent {
    private val remoteRepository: RepositoryRetrofitImpl by inject()
    private val localRepository: RepositoryRoomImpl by inject()

    override fun getData(text: String, fromRemoteSource: Boolean): Observable<AppState> {
        return if (fromRemoteSource) {
            remoteRepository.getData(text).map { AppState.Success(it) }
        } else {
            localRepository.getData(text).map { AppState.Success(it) }
        }
    }
}