package com.example.dictionary.view

import androidx.lifecycle.MutableLiveData
import com.example.dictionary.data.retrofit.RepositoryRetrofitImpl
import com.example.dictionary.data.room.RepositoryRoomImpl
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.schedulers.Schedulers
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class MainViewModel:
    MainViewModelContract.MainViewModel(), KoinComponent {
    private val repoRemote: RepositoryRetrofitImpl by inject()
    private val repoLocal: RepositoryRoomImpl by inject()
    private val compositeDisposable = CompositeDisposable()
    override val meaningsLiveData: MutableLiveData<AppState> =
        MutableLiveData<AppState>()

    override fun getData(text: String, isOnline: Boolean) {
        if (isOnline) {
            getDataFromRemote(text)

        } else {
            getDataFromLocal(text)
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
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
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

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }
}