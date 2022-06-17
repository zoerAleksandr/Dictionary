package com.example.dictionary.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.dictionary.domain.entity.Answer
import kotlinx.coroutines.*

interface MainViewModelContract {
    abstract class MainViewModel : ViewModel() {
        abstract val meaningsLiveData: LiveData<AppState>
        abstract fun getData(text: String, isOnline: Boolean)
        abstract fun getDataFromRemote(text: String)
        abstract fun getDataFromLocal(text: String)
        abstract fun saveAnswerToLocal(answer: Answer)
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