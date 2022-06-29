package com.example.dictionary.di

import androidx.lifecycle.SavedStateHandle
import androidx.room.Room
import com.example.dictionary.data.retrofit.RepositoryRetrofitImpl
import com.example.dictionary.data.retrofit.RetrofitImpl
import com.example.dictionary.data.room.AnswerDataBase
import com.example.dictionary.data.room.RepositoryRoomImpl
import com.example.dictionary.playSong
import com.example.dictionary.view.history_screen.BasicModelContract
import com.example.dictionary.view.history_screen.HistoryViewModel
import com.example.dictionary.view.main_search_screen.MainAdapter
import com.example.dictionary.view.main_search_screen.MainSearchFragment
import com.example.dictionary.view.main_search_screen.MainSearchViewModelContract
import com.example.dictionary.view.main_search_screen.MainViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

private const val DB_NAME = "Answer.db"
val module = module {
    single { RetrofitImpl() }
    single { RepositoryRetrofitImpl(remoteProvider = get()) }
    single { RepositoryRoomImpl(dao = get()) }
    single {
        Room.databaseBuilder(
            androidContext(),
            AnswerDataBase::class.java,
            DB_NAME
        ).build()
    }
    single { get<AnswerDataBase>().answerDao() }
    viewModel<MainSearchViewModelContract.MainSearchViewModel> { MainViewModel(SavedStateHandle()) }
    viewModel<BasicModelContract.BasicViewModel> { HistoryViewModel(SavedStateHandle()) }

    scope<MainSearchFragment> {
        scoped {
            MainAdapter {
                playSong(androidContext(), it?.soundUrl)
            }
        }
    }
}