package org.android.go.sopt.home

import androidx.lifecycle.ViewModel
import org.android.go.sopt.home.data.Title

class TitleViewModel : ViewModel() {
    private val homeTitleList = listOf(
        Title(
            title = "Follower"
        )
    )

    fun getHomeTitleList(): List<Title> {
        return this.homeTitleList
    }
}