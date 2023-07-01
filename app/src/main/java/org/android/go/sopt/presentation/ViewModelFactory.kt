package org.android.go.sopt.presentation

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import org.android.go.sopt.data.datasource.remote.AuthRemoteDataSource
import org.android.go.sopt.data.datasource.remote.KakaoSearchDataSource
import org.android.go.sopt.data.datasource.remote.ListUsersRemoteDataSource
import org.android.go.sopt.data.datasource.remote.MusicRemoteDataSource
import org.android.go.sopt.data.repository.AuthRepositoryImpl
import org.android.go.sopt.data.repository.KakaoSearchRepositoryImpl
import org.android.go.sopt.data.repository.ListUsersRepositoryImpl
import org.android.go.sopt.data.repository.MusicRepositoryImpl
import org.android.go.sopt.presentation.home.HomeViewModel
import org.android.go.sopt.presentation.music.MusicViewModel
import org.android.go.sopt.presentation.search.SearchViewModel
import org.android.go.sopt.presentation.sign.SignViewModel

class ViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(ListUsersRepositoryImpl(ListUsersRemoteDataSource())) as T
        } else if (modelClass.isAssignableFrom(SearchViewModel::class.java)) {
            return SearchViewModel(KakaoSearchRepositoryImpl(KakaoSearchDataSource())) as T
        } else if (modelClass.isAssignableFrom(SignViewModel::class.java)) {
            return SignViewModel(AuthRepositoryImpl(AuthRemoteDataSource())) as T
        } else if (modelClass.isAssignableFrom(MusicViewModel::class.java)) {
            return MusicViewModel(MusicRepositoryImpl(MusicRemoteDataSource())) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}