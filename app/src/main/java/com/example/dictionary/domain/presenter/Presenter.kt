package com.example.dictionary.domain.presenter

import com.example.dictionary.view.Contract

interface Presenter {
    fun attach(view: Contract)
    fun detach(view: Contract)
    fun getData(text: String)
}