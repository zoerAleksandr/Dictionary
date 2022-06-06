package com.example.dictionary.view

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.dictionary.R
import com.example.dictionary.data.InteractorImpl
import com.example.dictionary.data.PresenterImpl
import com.example.dictionary.databinding.ActivityMainBinding
import org.koin.core.component.KoinComponent

class MainActivity : AppCompatActivity(), Contract, KoinComponent {
    private val binding: ActivityMainBinding by viewBinding()
    private val isOnline = true
    private val interactor = InteractorImpl()
    private val presenter = PresenterImpl(interactor)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding.inputEditText.addTextChangedListener {
            presenter.getData(it.toString(), isOnline)
        }
    }

    override fun onStart() {
        super.onStart()
        presenter.attach(this)
    }

    override fun onStop() {
        super.onStop()
        presenter.detach(this)
    }

    override fun renderData(appState: AppState) {
        when (appState) {
            is AppState.Loading -> {
                Log.d("Debug", "$appState")

            }
            is AppState.Success -> {
                Log.d("Debug", "${appState.data[0].meanings}")

            }
            is AppState.Error -> {
                Log.d("Debug", "$appState")

            }
        }
    }
}