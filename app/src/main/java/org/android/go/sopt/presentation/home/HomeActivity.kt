package org.android.go.sopt.presentation.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import org.android.go.sopt.R
import org.android.go.sopt.databinding.ActivityHomeBinding
import org.android.go.sopt.databinding.FragmentRepoBinding
import org.android.go.sopt.presentation.home.HomeFragment
import org.android.go.sopt.presentation.music.MusicFragment
import org.android.go.sopt.presentation.mypage.MyPageFragment
import org.android.go.sopt.presentation.repo.RepoFragment
import org.android.go.sopt.presentation.search.SearchFragment
import org.android.go.sopt.util.binding.BindingActivity

class HomeActivity : BindingActivity<ActivityHomeBinding>(R.layout.activity_home) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initFragment(savedInstanceState)
        addListeners()
    }


    private fun initFragment(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                add(R.id.fcv_home_main, HomeFragment())
            }
        }
    }

    private fun addListeners() {
        binding.bnvHomeMain.setOnItemSelectedListener { item ->
            changeFragment(
                when (item.itemId) {
                    R.id.menu_home -> {
                        HomeFragment()
                    }

                    R.id.menu_search -> {
                        SearchFragment()
                    }

                    R.id.menu_gallery -> {
                        MusicFragment()
                    }

                    R.id.menu_repositories -> {
                        RepoFragment()
                    }

                    else -> {
                        MyPageFragment()
                    }
                }
            )
            true
        }

        binding.bnvHomeMain.setOnItemReselectedListener {
            when (it.itemId) {
                R.id.menu_home -> {
                    val currentFragment =
                        supportFragmentManager.findFragmentById(R.id.fcv_home_main)
                    if (currentFragment is RepoFragment) {
                        val repoBinding = FragmentRepoBinding.bind(currentFragment.requireView())
                        repoBinding.rvRepoRepos.scrollToPosition(0)
                    }
                }
            }
        }
    }

    private fun changeFragment(fragment: Fragment) {
        supportFragmentManager.commit {
            replace(R.id.fcv_home_main, fragment)
        }
    }
}