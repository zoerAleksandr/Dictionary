package com.example.dictionary.view

import android.media.AudioAttributes
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.dictionary.R
import com.example.dictionary.databinding.ActivityMainBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.component.KoinComponent
import java.io.IOException

class MainActivity : AppCompatActivity(), Contract, KoinComponent {
    private val binding: ActivityMainBinding by viewBinding()
    private val isOnline = false
    private val viewModel: MainViewModelContract.MainViewModel by viewModel()
    private val adapter by lazy {
        MainAdapter {
            playSong(it?.soundUrl)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel.meaningsLiveData.observe(this) {
            renderData(it)
        }
        binding.inputEditText.addTextChangedListener {
            if (!it.isNullOrBlank()) {
                viewModel.getData(it.toString(), isOnline)
            } else {
                adapter.setData(null)
            }
        }
        binding.meaningsRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.meaningsRecyclerView.adapter = adapter
    }

    override fun renderData(appState: AppState) {
        when (appState) {
            is AppState.Loading -> {
                binding.loadingLayout.visibility = View.VISIBLE
            }
            is AppState.Success -> {
                binding.loadingLayout.visibility = View.GONE
                if (appState.data.isNotEmpty()) {
                    adapter.setData(appState.data[0].meanings)
                } else {
                    adapter.setData(null)
                }
            }
            is AppState.Error -> {
                binding.loadingLayout.visibility = View.GONE
                Toast.makeText(this, appState.throwable.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun playSong(songUrl: String?) {
        var player: MediaPlayer? = null
        try {
            player = MediaPlayer.create(this, Uri.parse(songUrl))

            player.setAudioAttributes(
                AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .build()
            )
            player.start()

        } catch (e: IOException) {
            player?.release()
            Toast.makeText(this, e.stackTrace.toString(), Toast.LENGTH_SHORT).show()
        }
    }
}