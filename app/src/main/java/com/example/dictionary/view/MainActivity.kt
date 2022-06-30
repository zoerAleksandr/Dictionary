package com.example.dictionary.view

import android.animation.ObjectAnimator
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.animation.AnticipateInterpolator
import androidx.appcompat.app.AppCompatActivity
import androidx.core.animation.doOnEnd
import androidx.core.splashscreen.SplashScreen
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.dictionary.R
import com.example.dictionary.databinding.ActivityMainBinding
import com.example.dictionary.view.favorite_screen.FavoriteListFragment
import com.example.dictionary.view.history_screen.HistoryFragment
import com.example.dictionary.view.main_search_screen.MainSearchFragment
import java.util.concurrent.Executors

class MainActivity : AppCompatActivity(), Contract {
    private val binding: ActivityMainBinding by viewBinding()
    private lateinit var splashScreen: SplashScreen

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            splashScreen = installSplashScreen()
            startSplash()
        }
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(binding.container.id, MainSearchFragment())
                .commitNow()
        }

        binding.bottomNavigation.setOnItemSelectedListener { menu ->
            when (menu.itemId) {
                R.id.history -> {
                    openFragment(FragmentTag.HISTORY)
                    true
                }
                R.id.main_search -> {
                    openFragment(FragmentTag.MAIN_SEARCH)
                    true
                }
                R.id.favorite_list -> {
                    openFragment(FragmentTag.FAVORITE_LIST)
                    true
                }
                else -> false

            }
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

    private fun getFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(binding.container.id, fragment)
            .addToBackStack(null)
            .commit()
    }

    private fun startSplash() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            splashScreen.setKeepOnScreenCondition { true }

            Executors.newSingleThreadExecutor().execute {
                Thread.sleep(1000)
                splashScreen.setKeepOnScreenCondition { false }
            }

            splashScreen.setOnExitAnimationListener { provider ->
                ObjectAnimator.ofFloat(
                    provider.view,
                    View.TRANSLATION_X,
                    0f,
                    -provider.view.width.toFloat()
                ).apply {
                    duration = 300
                    interpolator = AnticipateInterpolator()
                    doOnEnd {
                        provider.remove()
                    }
                }.start()
            }
        }
    }
}