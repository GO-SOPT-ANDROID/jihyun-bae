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
    private val listUsersService = ListUsersServicePool.listUsersService

    fun getListUsers() {
        listUsersService.getListUsers().enqueue(object : Callback<ResponseListUsersDto> {
            override fun onResponse(
                call: Call<ResponseListUsersDto>,
                response: Response<ResponseListUsersDto>
            ) {
                if (response.isSuccessful) {
                    _getListUserResult.value = response.body()
                }
            }

            override fun onFailure(call: Call<ResponseListUsersDto>, t: Throwable) {

            }

        })
    }
}