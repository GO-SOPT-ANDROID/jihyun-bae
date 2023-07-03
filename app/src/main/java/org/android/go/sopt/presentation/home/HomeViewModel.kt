package org.android.go.sopt.presentation.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.android.go.sopt.data.repository.ListUsersRepositoryImpl
import org.android.go.sopt.domain.model.ListUser
import org.android.go.sopt.util.UiState

class HomeViewModel(private val listUsersRepository: ListUsersRepositoryImpl) : ViewModel() {
    private val _getListUserState: MutableLiveData<UiState<List<ListUser>>> = MutableLiveData()
    val getListUserState: LiveData<UiState<List<ListUser>>> = _getListUserState
    private val _isLoading: MutableLiveData<Boolean> = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

    fun getListUsers() {
        _isLoading.value = true

        viewModelScope.launch {
            listUsersRepository.getListUsers()
                .onSuccess { userList ->
                    _getListUserState.value = UiState.Success(userList)
                    _isLoading.value = false
                }
                .onFailure { throwable ->
                    _getListUserState.value = UiState.Error(throwable.message)
                    _isLoading.value = false
                }
        }
    }
}