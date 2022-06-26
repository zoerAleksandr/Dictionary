package com.example.dictionary.view.history_screen

import androidx.lifecycle.SavedStateHandle
import com.example.dictionary.data.room.RepositoryRoomImpl
import com.example.dictionary.view.AppState
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import org.koin.java.KoinJavaComponent.inject

const val HISTORY_QUERY = "historyQuery"

class HistoryViewModel(private val savedStateHandle: SavedStateHandle) :
    HistoryModelContract.BasicViewModel() {

    private val repo: RepositoryRoomImpl by inject(RepositoryRoomImpl::class.java)
    private var jobAllData: Job? = null
    private var jobSearchMeanings: Job? = null
    override val state: MutableStateFlow<AppState> =
        MutableStateFlow(AppState.Loading(true))

    override fun getAllData() {
        jobAllData?.let {
            cancelJob(it)
        }
        jobAllData = viewModelScope.launch {
            state.value = AppState.Success(repo.getAllData())
        }
    }

    override fun getDataFromLocal(text: String) {
        jobSearchMeanings?.let {
            cancelJob(it)
        }
        setQuerySavedState(text)
        jobSearchMeanings = viewModelScope.launch {
            state.value = AppState.Success(repo.getData(text))
        }
    }

    private fun setQuerySavedState(query: String) {
        savedStateHandle.set(HISTORY_QUERY, query)
    }

    override fun getQuerySavedState(key: String): String? {
        return savedStateHandle.get<String>(key)
    }

    override fun handlerError(error: Throwable) {
        state.value = AppState.Error(error)
    }
}