package org.android.go.sopt.presentation

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import org.android.go.sopt.data.datasource.remote.KakaoSearchDataSource
import org.android.go.sopt.data.datasource.remote.ListUsersRemoteDataSource
import org.android.go.sopt.data.repository.KakaoSearchRepositoryImpl
import org.android.go.sopt.data.repository.ListUsersRepositoryImpl
import org.android.go.sopt.presentation.home.HomeViewModel
import org.android.go.sopt.presentation.search.SearchViewModel

class ViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(ListUsersRepositoryImpl(ListUsersRemoteDataSource())) as T
        } else if (modelClass.isAssignableFrom(SearchViewModel::class.java)) {
            return SearchViewModel(KakaoSearchRepositoryImpl(KakaoSearchDataSource())) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}