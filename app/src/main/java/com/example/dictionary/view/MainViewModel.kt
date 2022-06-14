package com.example.dictionary.view

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import com.example.dictionary.data.retrofit.RepositoryRetrofitImpl
import com.example.dictionary.data.room.RepositoryRoomImpl
import com.example.dictionary.domain.entity.Answer
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.schedulers.Schedulers
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

const val QUERY = "query"

class MainViewModel(private val savedStateHandle: SavedStateHandle) :
    MainViewModelContract.MainViewModel(), KoinComponent {
    private val repoRemote: RepositoryRetrofitImpl by inject()
    private val repoLocal: RepositoryRoomImpl by inject()
    private val compositeDisposable = CompositeDisposable()
    override val meaningsLiveData: MutableLiveData<AppState> =
        MutableLiveData<AppState>()

    override fun getData(text: String, isOnline: Boolean) {
        setQuerySavedState(text)
        if (isOnline) {
            getDataFromRemote(text)
            meaningsLiveData.postValue(AppState.IsOnline)
        } else {
            getDataFromLocal(text)
            meaningsLiveData.postValue(AppState.IsOffline)
        }
    }

    override fun getDataFromRemote(text: String) {
        compositeDisposable.add(
            repoRemote.getData(text)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                    onNext = {
                        meaningsLiveData.postValue(
                            AppState.Success(it)
                        )
                        saveAnswerToLocal(it[0])
                    },
                    onError = {
                        meaningsLiveData.postValue(
                            AppState.Error(it)
                        )
                    }
                )
        )
    }

    override fun getDataFromLocal(text: String) {
        compositeDisposable.add(
            repoLocal.getData(text)
                .observeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                    onNext = {
                        meaningsLiveData.postValue(
                            AppState.Success(it)
                        )
                    },
                    onError = {
                        meaningsLiveData.postValue(
                            AppState.Error(it)
                        )
                    }
                )
        )
    }

    override fun saveAnswerToLocal(answer: Answer) {
        compositeDisposable.add(
            Single.just(answer)
                .observeOn(Schedulers.io())
                .concatMap {
                    repoLocal.saveAnswerToLocal(it)
                }
                .subscribe()
        )
    }

    override fun getQuerySavedState(key: String): String? {
        return savedStateHandle.get<String>(key)
    }

    private fun setQuerySavedState(query: String) {
        savedStateHandle.set(QUERY, query)
    }

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }
}