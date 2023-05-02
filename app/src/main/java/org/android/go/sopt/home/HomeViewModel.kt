package org.android.go.sopt.home

import androidx.lifecycle.ViewModel
import org.android.go.sopt.R
import org.android.go.sopt.home.data.Home

class HomeViewModel : ViewModel() {
    val mockHomeList = listOf(
        Home(
            image = R.drawable.github,
            id = 0,
            name = "jihyunniiii",
            author = "jihyunniiii"
        ),
        Home(
            image = R.drawable.github,
            id = 1,
            name = "Kream_Clone_App",
            author = "jihyunniiii"
        ),
        Home(
            image = R.drawable.github,
            id = 2,
            name = "DS_Project",
            author = "jihyunniiii"
        ),
        Home(
            image = R.drawable.github,
            id = 3,
            name = "Android_Study",
            author = "jihyunniiii"
        ),
        Home(
            image = R.drawable.github,
            id = 4,
            name = "MobileSoftwareProject",
            author = "jihyunniiii"
        ),
        Home(
            image = R.drawable.github,
            id = 5,
            name = "Android",
            author = "jihyunniiii"
        ),
        Home(
            image = R.drawable.github,
            id = 6,
            name = "BOJ",
            author = "jihyunniiii"
        ),
        Home(
            image = R.drawable.github,
            id = 7,
            name = "git_exercise",
            author = "jihyunniiii"
        )
    )
}