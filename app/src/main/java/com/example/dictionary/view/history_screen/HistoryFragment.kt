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
import com.example.dictionary.view.delegateUser
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
        val changedMeanings by delegateUser(meanings)
        val value = changedMeanings
        lifecycleScope.launchWhenStarted {
            historyAdapter.updateMeanings(value)
            viewModel.updateMeanings(value)
        }
    }
}
