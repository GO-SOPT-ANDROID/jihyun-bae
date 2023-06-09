package org.android.go.sopt.home.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.android.go.sopt.data.remote.api.ListUsersServicePool
import org.android.go.sopt.data.remote.datasource.ListUsersRemoteDataSource
import org.android.go.sopt.data.remote.model.ResponseListUsersDto

class HomeViewModel : ViewModel() {
    private val _getListUserResult: MutableLiveData<ResponseListUsersDto> = MutableLiveData()
    val getListUserResult: LiveData<ResponseListUsersDto> = _getListUserResult
    private val _isLoading: MutableLiveData<Boolean> = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

    private val listUsersService = ListUsersServicePool.listUsersService
    private val listUsersRemoteDataSource = ListUsersRemoteDataSource(listUsersService)

    fun getListUsers() {
        _isLoading.value = true

        viewModelScope.launch {
            try {
                val response = listUsersRemoteDataSource.getListUsers()
                if (response.isSuccessful) _getListUserResult.value = response.body()
                _isLoading.value = false
            } catch (e: Exception) {
                _isLoading.value = false
            }
        }
    }
}