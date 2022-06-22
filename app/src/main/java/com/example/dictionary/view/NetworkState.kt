package com.example.dictionary.view

sealed class NetworkState {
    object IsOnline : NetworkState()
    object IsOffline : NetworkState()
}