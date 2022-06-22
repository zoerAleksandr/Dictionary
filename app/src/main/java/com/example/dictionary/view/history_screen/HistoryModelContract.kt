package com.example.dictionary.view.history_screen

import androidx.lifecycle.ViewModel
import com.example.dictionary.view.AppState
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.StateFlow

interface HistoryModelContract {
    abstract class BasicViewModel : ViewModel() {
        abstract val state: StateFlow<AppState>
        abstract fun getDataFromLocal(text: String)
        abstract fun getQuerySavedState(key: String): String?
        abstract fun getAllData()

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