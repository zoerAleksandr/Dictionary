package com.example.dictionary.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.dictionary.R
import com.example.dictionary.databinding.ActivityMainBinding
import com.example.dictionary.view.favorite_screen.FavoriteListFragment
import com.example.dictionary.view.history_screen.HistoryFragment
import com.example.dictionary.view.main_search_screen.MainSearchFragment

class MainActivity : AppCompatActivity(), Contract {
    private val binding: ActivityMainBinding by viewBinding()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(binding.container.id, MainSearchFragment())
                .commitNow()
        }
    }

    override fun openFragment(fragment: FragmentTag) {
        when (fragment) {
            FragmentTag.MAIN_SEARCH -> {
                getFragment(MainSearchFragment())
            }
            FragmentTag.FAVORITE_LIST -> {
                getFragment(FavoriteListFragment())
            }
            FragmentTag.HISTORY -> {
                getFragment(HistoryFragment())
            }
        }
    }

    private fun getFragment(fragment: Fragment){
        supportFragmentManager.beginTransaction()
            .replace(binding.container.id, fragment)
            .addToBackStack(null)
            .commit()
    }
}