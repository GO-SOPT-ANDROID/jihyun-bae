package org.android.go.sopt.home.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.android.go.sopt.data.remote.api.ListUsersServicePool
import org.android.go.sopt.data.remote.model.ResponseListUsersDto
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel : ViewModel() {
    private val _getListUserResult: MutableLiveData<ResponseListUsersDto> = MutableLiveData()
    val getListUserResult: LiveData<ResponseListUsersDto> = _getListUserResult
    private val _isLoading: MutableLiveData<Boolean> = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading
    private val listUsersService = ListUsersServicePool.listUsersService

    fun getListUsers() {
        _isLoading.value = true

        listUsersService.getListUsers().enqueue(object : Callback<ResponseListUsersDto> {
            override fun onResponse(
                call: Call<ResponseListUsersDto>,
                response: Response<ResponseListUsersDto>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _getListUserResult.value = response.body()
                }
            }

            override fun onFailure(call: Call<ResponseListUsersDto>, t: Throwable) {
                _isLoading.value = false
            }

        })
    }
}