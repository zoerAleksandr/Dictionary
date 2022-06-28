package com.example.data_api

import android.content.Context

interface NetworkConnectContract {
    fun checkConnectivity(context: Context): Boolean
}