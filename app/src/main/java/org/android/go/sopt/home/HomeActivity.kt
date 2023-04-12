package org.android.go.sopt.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import org.android.go.sopt.R
import org.android.go.sopt.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setFragmentContainerView()
        clickNav()
    }

    private fun setFragmentContainerView() {
        val currentFragment = supportFragmentManager.findFragmentById(R.id.fcv_home_main)
        if (currentFragment == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.fcv_home_main, HomeFragment())
                .commit()
        }
    }

    private fun clickNav() {
        binding.bnvHomeMain.setOnItemSelectedListener { item ->
            changeFragment(
                when (item.itemId) {
                    R.id.menu_home -> {
                        HomeFragment()
                        return@setOnItemSelectedListener true
                    }
                    R.id.menu_search -> {
                        SearchFragment()
                        return@setOnItemSelectedListener true
                    }
                    else -> {
                        GalleryFragment()
                        return@setOnItemSelectedListener true
                    }
                }
            )
            false
        }
    }

    private fun changeFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fcv_home_main, fragment)
            .commit()
    }
}