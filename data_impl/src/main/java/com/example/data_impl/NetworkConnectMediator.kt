package com.example.data_impl

import com.example.data_api.NetworkConnectContract
import com.example.data_api.NetworkConnectMediatorContract

object NetworkConnectMediator: NetworkConnectMediatorContract {
    override fun provideNetworkConnect(): NetworkConnectContract {
        return NetworkConnectImpl
    }
}