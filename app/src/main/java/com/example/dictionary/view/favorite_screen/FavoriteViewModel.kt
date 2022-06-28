package com.example.dictionary.view.favorite_screen

import androidx.lifecycle.SavedStateHandle
import com.example.dictionary.data.room.RepositoryRoomImpl
import com.example.dictionary.domain.entity.Meanings
import com.example.dictionary.view.AppState
import com.example.dictionary.view.history_screen.BasicModelContract
import com.example.dictionary.view.history_screen.HISTORY_QUERY
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import org.koin.java.KoinJavaComponent

const val FAVORITE_QUERY = "favoriteQuery"
const val IS_NOT_FAVORITE = 0
const val IS_FAVORITE = 1

class FavoriteViewModel(private val savedStateHandle: SavedStateHandle) :
    BasicModelContract.BasicViewModel() {
    private val repo: RepositoryRoomImpl by KoinJavaComponent.inject(RepositoryRoomImpl::class.java)
    private var jobAllData: Job? = null
    private var jobSearchMeanings: Job? = null
    private var jobUpdateMeanings: Job? = null
    override val state: MutableStateFlow<AppState> =
        MutableStateFlow(AppState.Empty)


    override fun getDataFromLocal(text: String) {
        jobSearchMeanings?.let {
            cancelJob(it)
        }
        setQuerySavedState(text)
        jobSearchMeanings = viewModelScope.launch {
            val result = repo.getFavoriteMeaningsByAnswerText(text)
            if (result.isEmpty()) {
                state.value = AppState.Empty
            } else {
                state.value = AppState.Success(result)
            }
        }
    }

    override fun getQuerySavedState(key: String): String? {
        return savedStateHandle.get<String>(key)
    }

    override fun getAllData() {
        jobAllData?.let {
            cancelJob(it)
        }
        jobAllData = viewModelScope.launch {
            val result = repo.getAllFavoritesMeanings(IS_FAVORITE)
            if (result.isEmpty()) {
                state.value = AppState.Empty
            } else {
                state.value = AppState.Success(result)
            }
        }
    }

    override suspend fun updateMeanings(meanings: Meanings) {
        jobUpdateMeanings?.let {
            cancelJob(it)
        }
        jobUpdateMeanings = viewModelScope.launch {
            repo.updateMeanings(meanings)
        }
    }

    private fun setQuerySavedState(query: String) {
        savedStateHandle.set(HISTORY_QUERY, query)
    }

    override fun handlerError(error: Throwable) {
        state.value = AppState.Error(error)
    }
}