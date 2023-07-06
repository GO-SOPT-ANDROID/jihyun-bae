package org.android.go.sopt.domain.model

import androidx.annotation.DrawableRes

data class Repo(
    @DrawableRes val image: Int,
    val name: String,
    val author: String
)
