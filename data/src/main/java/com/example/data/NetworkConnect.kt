package com.example.data

import android.content.Context
import com.example.data_impl.NetworkConnectMediator

object NetworkConnect {
    fun checkConnectivity(context: Context): Boolean =
        NetworkConnectMediator.provideNetworkConnect().checkConnectivity(context)
}