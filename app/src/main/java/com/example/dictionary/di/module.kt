package com.example.dictionary.di

import com.example.dictionary.data.retrofit.RepositoryRetrofitImpl
import com.example.dictionary.data.retrofit.RetrofitImpl
import com.example.dictionary.view.MainViewModel
import com.example.dictionary.view.MainViewModelContract
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val module = module {
    single { RetrofitImpl() }
    single { RepositoryRetrofitImpl(remoteProvider = get()) }
    viewModel<MainViewModelContract.MainViewModel> { MainViewModel() }
}