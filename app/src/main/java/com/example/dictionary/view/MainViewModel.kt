package com.example.dictionary.view

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import com.example.dictionary.data.retrofit.RepositoryRetrofitImpl
import com.example.dictionary.data.room.RepositoryRoomImpl
import com.example.dictionary.domain.entity.Answer
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

const val QUERY = "query"

class MainViewModel(private val savedStateHandle: SavedStateHandle) :
    MainViewModelContract.MainViewModel(), KoinComponent {
    private val repoRemote: RepositoryRetrofitImpl by inject()
    private val repoLocal: RepositoryRoomImpl by inject()
    private var jobRemote: Job? = null

    override val meaningsLiveData: MutableLiveData<AppState> =
        MutableLiveData<AppState>()

    override fun getData(text: String, isOnline: Boolean) {
        jobRemote?.let {
            cancelJob(it)
        }
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
        jobRemote?.cancel()
        jobRemote = viewModelScope.launch {
            val answer = repoRemote.getData(text)
            meaningsLiveData.postValue(
                AppState.Success(answer)
            )
            saveAnswerToLocal(answer[0])
        }
    }

    override fun getDataFromLocal(text: String) {
        viewModelScope.launch {
            meaningsLiveData.postValue(
                AppState.Success(repoLocal.getData(text))
            )
        }
    }

    override fun saveAnswerToLocal(answer: Answer) {
        viewModelScope.launch {
            repoLocal.saveAnswerToLocal(answer)
        }
    }

    override fun getQuerySavedState(key: String): String? {
        return savedStateHandle.get<String>(key)
    }

    override fun handlerError(error: Throwable) {
        meaningsLiveData.postValue(AppState.Error(error))
    }

    private fun setQuerySavedState(query: String) {
        savedStateHandle.set(QUERY, query)
    }
}