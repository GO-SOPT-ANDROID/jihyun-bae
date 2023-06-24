package org.android.go.sopt.home.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.android.go.sopt.data.remote.model.ResponseListUsersDto
import org.android.go.sopt.repository.ListUsersRepository

class HomeViewModel(private val listUsersRepository: ListUsersRepository) : ViewModel() {
    private val _getListUserResult: MutableLiveData<ResponseListUsersDto> = MutableLiveData()
    val getListUserResult: LiveData<ResponseListUsersDto> = _getListUserResult
    private val _isLoading: MutableLiveData<Boolean> = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

    fun getListUsers() {
        _isLoading.value = true

        viewModelScope.launch {
            listUsersRepository.getListUsers()
                .onSuccess { userList ->
                    _getListUserResult.value = userList
                    _isLoading.value = false
                }
                .onFailure {
                    _isLoading.value = false
                }
        }
    }
}