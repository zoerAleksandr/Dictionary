package com.example.dictionary.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.dictionary.domain.entity.Answer

interface MainViewModelContract {
    abstract class MainViewModel : ViewModel() {
        abstract val meaningsLiveData: LiveData<AppState>
        abstract fun getData(text: String, isOnline: Boolean)
        abstract fun getDataFromRemote(text: String)
        abstract fun getDataFromLocal(text: String)
        abstract fun saveAnswerToLocal(answer: Answer)
    }
}