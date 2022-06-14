package com.example.dictionary

import android.app.Application
import com.example.dictionary.di.module
import org.koin.core.context.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            modules(module)
        }
    }
}