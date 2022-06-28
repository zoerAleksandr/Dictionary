package com.example.dictionary.view.main_search_screen

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import com.example.dictionary.data.retrofit.RepositoryRetrofitImpl
import com.example.dictionary.data.room.RepositoryRoomImpl
import com.example.dictionary.data.room.converter_entity.toAnswerDTO
import com.example.dictionary.data.room.converter_entity.toListMeaningsDTO
import com.example.dictionary.data.room.entity.AnswerWithMeanings
import com.example.dictionary.view.AppState
import com.example.dictionary.view.NetworkState
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

const val QUERY = "query"

class MainViewModel(private val savedStateHandle: SavedStateHandle) :
    MainSearchViewModelContract.MainSearchViewModel(), KoinComponent {
    private val repoRemote: RepositoryRetrofitImpl by inject()
    private val repoLocal: RepositoryRoomImpl by inject()
    private var jobRemote: Job? = null

    override val liveData: MutableLiveData<AppState> = MutableLiveData()
    override val networkLiveData: MutableLiveData<NetworkState> = MutableLiveData()

    override fun getData(text: String, isOnline: Boolean) {
        jobRemote?.let {
            cancelJob(it)
        }
        setQuerySavedState(text)
        if (isOnline) {
            getDataFromRemote(text)
            networkLiveData.postValue(NetworkState.IsOnline)
        } else {
            getDataFromLocal(text)
            networkLiveData.postValue(NetworkState.IsOffline)
        }
    }

    override fun getDataFromRemote(text: String) {
        jobRemote?.cancel()
        jobRemote = viewModelScope.launch {
            val answer = repoRemote.getData(text)
            val answerWithMeanings = AnswerWithMeanings(
                answerDTO = toAnswerDTO(answer[0]),
                listMeanings = toListMeaningsDTO(answer[0].meanings, answer[0].text)
            )
            liveData.postValue(
                AppState.Success(answer[0].meanings)
            )
            saveAnswerToLocal(answerWithMeanings)
        }
    }

    override fun getDataFromLocal(text: String) {
        viewModelScope.launch {
            liveData.postValue(
                AppState.Success(repoLocal.getData(text))
            )
        }
    }

    override fun saveAnswerToLocal(answer: AnswerWithMeanings) {
        viewModelScope.launch {
            repoLocal.insertAnswerWithMeanings(answer)
        }
    }

    override fun getQuerySavedState(key: String): String? {
        return savedStateHandle.get<String>(key)
    }

    override fun handlerError(error: Throwable) {
        liveData.postValue(AppState.Error(error))
    }

    private fun setQuerySavedState(query: String) {
        savedStateHandle.set(QUERY, query)
    }
}