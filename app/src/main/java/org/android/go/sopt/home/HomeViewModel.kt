package org.android.go.sopt.home

import androidx.lifecycle.ViewModel
import org.android.go.sopt.R
import org.android.go.sopt.home.data.Home

class HomeViewModel : ViewModel() {
    private val mockHomeList = listOf(
        Home(
            image = R.drawable.github,
            name = "jihyunniiii",
            author = "jihyunniiii"
        ),
        Home(
            image = R.drawable.github,
            name = "Kream_Clone_App",
            author = "jihyunniiii"
        ),
        Home(
            image = R.drawable.github,
            name = "DS_Project",
            author = "jihyunniiii"
        ),
        Home(
            image = R.drawable.github,
            name = "Android_Study",
            author = "jihyunniiii"
        ),
        Home(
            image = R.drawable.github,
            name = "MobileSoftwareProject",
            author = "jihyunniiii"
        ),
        Home(
            image = R.drawable.github,
            name = "Android",
            author = "jihyunniiii"
        ),
        Home(
            image = R.drawable.github,
            name = "BOJ",
            author = "jihyunniiii"
        ),
        Home(
            image = R.drawable.github,
            name = "git_exercise",
            author = "jihyunniiii"
        )
    )

    fun getMockHomeList(): List<Home> {
        return this.mockHomeList
    }
}