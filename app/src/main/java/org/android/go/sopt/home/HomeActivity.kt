package org.android.go.sopt.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import org.android.go.sopt.R
import org.android.go.sopt.databinding.ActivityHomeBinding
import org.android.go.sopt.databinding.FragmentRepoBinding

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initFragment(savedInstanceState)
        clickNav()
        scrollToTop()
    }

    private fun clickNav() {
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
                        GalleryFragment()
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
    }

    private fun initFragment(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                add(R.id.fcv_home_main, HomeFragment())
            }
        }
    }

    private fun changeFragment(fragment: Fragment) {
        supportFragmentManager.commit {
            replace(R.id.fcv_home_main, fragment)
        }
    }

    private fun scrollToTop() {
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
}