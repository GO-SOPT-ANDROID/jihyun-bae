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
            name = "jihyunniiii",
            author = "jihyunniiii"
        ),
        Repo(
            image = R.drawable.github,
            name = "Kream_Clone_App",
            author = "jihyunniiii"
        ),
        Repo(
            image = R.drawable.github,
            name = "DS_Project",
            author = "jihyunniiii"
        ),
        Repo(
            image = R.drawable.github,
            name = "Android_Study",
            author = "jihyunniiii"
        ),
        Repo(
            image = R.drawable.github,
            name = "MobileSoftwareProject",
            author = "jihyunniiii"
        ),
        Repo(
            image = R.drawable.github,
            name = "Android",
            author = "jihyunniiii"
        ),
        Repo(
            image = R.drawable.github,
            name = "BOJ",
            author = "jihyunniiii"
        ),
        Repo(
            image = R.drawable.github,
            name = "git_exercise",
            author = "jihyunniiii"
        )
    )
}