package com.example.dictionary.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.dictionary.R

class MainActivity : AppCompatActivity(), Contract {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun renderData(text: String, isOnline: Boolean) {
        TODO("Not yet implemented")
    }
}