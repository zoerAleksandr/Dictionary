package com.example.dictionary.view

import com.example.dictionary.domain.entity.Meanings

sealed class AppState {
    data class Success(val data: List<Meanings>) : AppState()
    data class Loading(val loading: Boolean) : AppState()
    data class Error(val throwable: Throwable) : AppState()
}