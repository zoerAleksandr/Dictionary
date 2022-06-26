package com.example.dictionary.view.main_search_screen

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.dictionary.data.room.entity.AnswerWithMeanings
import com.example.dictionary.view.AppState
import com.example.dictionary.view.NetworkState
import kotlinx.coroutines.*

interface MainSearchViewModelContract {
    abstract class MainSearchViewModel : ViewModel() {
        abstract val liveData: LiveData<AppState>
        abstract val networkLiveData: LiveData<NetworkState>
        abstract fun getData(text: String, isOnline: Boolean)
        abstract fun getDataFromRemote(text: String)
        abstract fun saveAnswerToLocal(answer: AnswerWithMeanings)
        abstract fun getDataFromLocal(text: String)
        abstract fun getQuerySavedState(key: String): String?

        protected val viewModelScope = CoroutineScope(
            Dispatchers.IO +
                    SupervisorJob() +
                    CoroutineExceptionHandler { _, throwable ->
                        handlerError(throwable)
                    }
        )

        abstract fun handlerError(error: Throwable)

        protected fun cancelJob(job: Job) {
            job.cancel()
        }

        override fun onCleared() {
            super.onCleared()
            viewModelScope.coroutineContext.cancelChildren()
        }
    }
}