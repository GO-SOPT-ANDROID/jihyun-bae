package org.android.go.sopt.data.remote.service

import org.android.go.sopt.data.remote.model.ResponseListUsersDto
import retrofit2.http.GET
import retrofit2.http.Query

interface ListUsersService {
    @GET("/api/users")
    suspend fun getListUsers(
        @Query("page") num: Int = 2
    ): ResponseListUsersDto
}