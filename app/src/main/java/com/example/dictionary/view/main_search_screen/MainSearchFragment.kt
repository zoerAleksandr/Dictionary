package com.example.dictionary.view.main_search_screen

import android.media.AudioAttributes
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.view.View
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.dictionary.R
import com.example.dictionary.data.retrofit.NetworkConnect
import com.example.dictionary.databinding.FragmentMainSearchBinding
import com.example.dictionary.view.AppState
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.IOException
import kotlin.properties.Delegates

class MainSearchFragment: Fragment(R.layout.fragment_main_search) {
    private val binding: FragmentMainSearchBinding by viewBinding()
    private var isOnline by Delegates.notNull<Boolean>()
    private val viewModel: MainViewModelContract.MainViewModel by viewModel()
    private val adapter by lazy {
        MainAdapter {
            playSong(it?.soundUrl)
        }
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.meaningsLiveData.observe(viewLifecycleOwner) {
            renderData(it)
        }
        viewModel.getQuerySavedState(QUERY)?.let { query ->
            binding.inputEditText.text = SpannableStringBuilder(query)
        }

        binding.inputEditText.addTextChangedListener {
            isOnline = NetworkConnect.checkConnectivity(requireContext())
            if (!it.isNullOrBlank()) {
                viewModel.getData(it.toString(), isOnline)
            } else {
                adapter.setData(null)
            }
        }
        binding.meaningsRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.meaningsRecyclerView.adapter = adapter
    }

    private fun renderData(appState: AppState) {
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
                Toast.makeText(requireContext(), appState.throwable.message, Toast.LENGTH_SHORT).show()
            }
            is AppState.IsOnline -> {
                binding.offlineTextView.visibility = View.GONE
            }

            is AppState.IsOffline -> {
                binding.offlineTextView.visibility = View.VISIBLE
            }
        }
    }

    private fun playSong(songUrl: String?) {
        var player: MediaPlayer? = null
        try {
            player = MediaPlayer.create(requireContext(), Uri.parse(songUrl))

            player.setAudioAttributes(
                AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .build()
            )
            player.start()

        } catch (e: IOException) {
            player?.release()
            Toast.makeText(requireContext(), e.stackTrace.toString(), Toast.LENGTH_SHORT).show()
        }
    }
}