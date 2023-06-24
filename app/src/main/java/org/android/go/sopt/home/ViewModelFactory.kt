package org.android.go.sopt.home

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import org.android.go.sopt.data.remote.datasource.ListUsersRemoteDataSource
import org.android.go.sopt.home.home.HomeViewModel
import org.android.go.sopt.repository.ListUsersRepository

class ViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(ListUsersRepository(ListUsersRemoteDataSource())) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}