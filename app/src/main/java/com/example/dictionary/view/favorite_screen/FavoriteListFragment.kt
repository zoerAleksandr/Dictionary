package com.example.dictionary.view.favorite_screen

import android.os.Bundle
import android.text.SpannableStringBuilder
import android.view.View
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.dictionary.R
import com.example.dictionary.databinding.FragmentFavoriteBinding
import com.example.dictionary.domain.entity.Meanings
import com.example.dictionary.view.AppState
import com.example.dictionary.view.delegateUser
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach

class FavoriteListFragment : Fragment(R.layout.fragment_favorite) {
    private val binding: FragmentFavoriteBinding by viewBinding()
    private val favoriteAdapter: FavoriteAdapter by lazy {
        FavoriteAdapter {
            favoriteClickListener(it)
        }
    }
    private val viewModel: FavoriteViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()

        viewModel.apply {
            getQuerySavedState(FAVORITE_QUERY)?.let { query ->
                binding.inputEditText.text = SpannableStringBuilder(query)
            }
            getAllData()
        }

        lifecycleScope.launchWhenStarted {
            viewModel.state
                .onEach { renderData(it) }
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
                favoriteAdapter.setData(appState.data)
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

    private fun initAdapter() {
        binding.meaningsRecyclerView.apply {
            adapter = favoriteAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun favoriteClickListener(meanings: Meanings) {
        val changedMeanings by delegateUser(meanings)
        val value = changedMeanings
        lifecycleScope.launchWhenStarted {
            favoriteAdapter.changeIconFavorite(value)
            delay(700)
            favoriteAdapter.removeMeanings(value)
            viewModel.updateMeanings(value)
        }
    }
}