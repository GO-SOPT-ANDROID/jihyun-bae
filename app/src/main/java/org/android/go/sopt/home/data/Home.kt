package org.android.go.sopt.home.data

import androidx.annotation.DrawableRes

data class Home(
    @DrawableRes val image: Int,
    val name: String,
    val author: String
)
