package org.android.go.sopt.presentation.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.android.go.sopt.data.repository.ListUsersRepositoryImpl
import org.android.go.sopt.domain.model.ListUser

class HomeViewModel(private val listUsersRepository: ListUsersRepositoryImpl) : ViewModel() {
    private val _getListUserResult: MutableLiveData<List<ListUser>> = MutableLiveData()
    val getListUserResult: LiveData<List<ListUser>> = _getListUserResult
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