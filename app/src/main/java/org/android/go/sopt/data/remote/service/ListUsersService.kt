package org.android.go.sopt.data.remote.service

import org.android.go.sopt.data.remote.model.ResponseListUsersDto
import retrofit2.Call
import retrofit2.http.GET

interface ListUsersService {
    @GET("/api/users?page=2")
    fun getListUsers(): Call<ResponseListUsersDto>
}