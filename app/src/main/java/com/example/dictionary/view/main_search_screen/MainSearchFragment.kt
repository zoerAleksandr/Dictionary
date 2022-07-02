package com.example.dictionary.view.main_search_screen

import android.os.Bundle
import android.text.SpannableStringBuilder
import android.view.View
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.data.NetworkConnect
import com.example.dictionary.R
import com.example.dictionary.databinding.FragmentMainSearchBinding
import com.example.dictionary.playSong
import com.example.dictionary.view.AppState
import com.example.dictionary.view.NetworkState
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.properties.Delegates

class MainSearchFragment : Fragment(R.layout.fragment_main_search) {
    private val binding: FragmentMainSearchBinding by viewBinding()
    private var isOnline by Delegates.notNull<Boolean>()
    private val viewModel: MainSearchViewModelContract.MainSearchViewModel by viewModel()
    private val adapter by lazy {
        MainAdapter {
            playSong(requireContext(), it?.soundUrl)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.liveData.observe(viewLifecycleOwner) {
            renderData(it)
        }

        viewModel.networkLiveData.observe(viewLifecycleOwner) {
            renderNetworkStatus(it)
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

    private fun renderNetworkStatus(networkState: NetworkState) {
        when (networkState) {
            is NetworkState.IsOnline -> {
                binding.offlineTextView.visibility = View.GONE
            }

            is NetworkState.IsOffline -> {
                binding.offlineTextView.visibility = View.VISIBLE
            }
        }
    }

    private fun renderData(appState: AppState) {
        when (appState) {
            is AppState.Loading -> {
                binding.loadingLayout.visibility = View.VISIBLE
            }
            is AppState.Success -> {
                binding.loadingLayout.visibility = View.GONE
                adapter.setData(appState.data)
            }
            is AppState.Error -> {
                binding.loadingLayout.visibility = View.GONE
                Toast.makeText(requireContext(), appState.throwable.message, Toast.LENGTH_SHORT)
                    .show()
            }
            else -> {}
        }
    }
}