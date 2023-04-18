package org.android.go.sopt.home

import androidx.lifecycle.ViewModel
import org.android.go.sopt.R
import org.android.go.sopt.home.data.Repo
import org.android.go.sopt.home.data.Title

class HomeViewModel : ViewModel() {
    val mockTitleList = listOf<Title>(
        Title(
            "Repositories"
        )
    )

    val mockRepoList = listOf<Repo>(
        Repo(
            image = R.drawable.github,
            id = 1,
            name = "jihyunniiii",
            author = "jihyunniiii"
        ),
        Repo(
            image = R.drawable.github,
            id = 2,
            name = "Kream_Clone_App",
            author = "jihyunniiii"
        ),
        Repo(
            image = R.drawable.github,
            id = 3,
            name = "DS_Project",
            author = "jihyunniiii"
        ),
        Repo(
            image = R.drawable.github,
            id = 4,
            name = "Android_Study",
            author = "jihyunniiii"
        ),
        Repo(
            image = R.drawable.github,
            id = 5,
            name = "MobileSoftwareProject",
            author = "jihyunniiii"
        ),
        Repo(
            image = R.drawable.github,
            id = 6,
            name = "Android",
            author = "jihyunniiii"
        ),
        Repo(
            image = R.drawable.github,
            id = 7,
            name = "BOJ",
            author = "jihyunniiii"
        ),
        Repo(
            image = R.drawable.github,
            id = 8,
            name = "git_exercise",
            author = "jihyunniiii"
        )
    )
}