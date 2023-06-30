package org.android.go.sopt.presentation

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import org.android.go.sopt.data.datasource.ListUsersRemoteDataSource
import org.android.go.sopt.presentation.home.HomeViewModel
import org.android.go.sopt.domain.repository.ListUsersRepository

class ViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(ListUsersRepository(ListUsersRemoteDataSource())) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}