package org.android.go.sopt.presentation.home

import androidx.lifecycle.ViewModel
import org.android.go.sopt.domain.model.Title

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