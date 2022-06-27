package com.example.dictionary.view.history_screen

import android.os.Bundle
import android.text.SpannableStringBuilder
import android.view.View
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.dictionary.R
import com.example.dictionary.databinding.FragmentHistoryBinding
import com.example.dictionary.domain.entity.Meanings
import com.example.dictionary.view.AppState
import com.example.dictionary.view.favorite_screen.IS_FAVORITE
import com.example.dictionary.view.favorite_screen.IS_NOT_FAVORITE
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import org.koin.androidx.viewmodel.ext.android.viewModel

class HistoryFragment : Fragment(R.layout.fragment_history) {
    private val binding: FragmentHistoryBinding by viewBinding()
    private val viewModel: BasicModelContract.BasicViewModel by viewModel()
    private val historyAdapter: HistoryAdapter by lazy {
        HistoryAdapter {
            favoriteClickListener(it)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.meaningsRecyclerView.apply {
            adapter = historyAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }

        viewModel.apply {
            getQuerySavedState(HISTORY_QUERY)?.let { query ->
                binding.inputEditText.text = SpannableStringBuilder(query)
            }
            getAllData()
        }

        lifecycleScope.launchWhenStarted {
            viewModel.state
                .onEach {
                    renderData(it)
                }
                .collect()
        }
        binding.inputEditText.addTextChangedListener {
            if (!it.isNullOrBlank()) {
                viewModel.getDataFromLocal(it.toString())
            } else {
                viewModel.getAllData()
            }
        }
    }

    private fun renderData(appState: AppState) {
        when (appState) {
            is AppState.Empty -> {}

            is AppState.Success -> {
                binding.emptyDataLayout.visibility = View.GONE
                binding.loadingLayout.visibility = View.GONE
                historyAdapter.setData(appState.data)
            }
            is AppState.Error -> {
                binding.emptyDataLayout.visibility = View.GONE
                binding.loadingLayout.visibility = View.GONE
                Toast.makeText(requireContext(), appState.throwable.message, Toast.LENGTH_SHORT)
                    .show()
            }
            is AppState.Loading -> {
                binding.emptyDataLayout.visibility = View.GONE
                binding.loadingLayout.visibility = View.VISIBLE
            }
        }
    }

    private fun favoriteClickListener(meanings: Meanings) {
        var toastText = ""
        when (meanings.isFavorite) {
            IS_FAVORITE -> {
                meanings.isFavorite = IS_NOT_FAVORITE
                toastText = resources.getString(R.string.remove_from_favorite)
            }
            IS_NOT_FAVORITE -> {
                meanings.isFavorite = IS_FAVORITE
                toastText = resources.getString(R.string.add_in_favorite)
            }
        }
        lifecycleScope.launchWhenStarted {
            historyAdapter.updateMeanings(meanings)
            viewModel.updateMeanings(meanings)
            Toast.makeText(requireContext(), toastText, Toast.LENGTH_SHORT).show()
        }
    }
}
