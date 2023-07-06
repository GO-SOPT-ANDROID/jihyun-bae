package org.android.go.sopt.data.service

import org.android.go.sopt.data.model.response.ResponseListUsersDto
import retrofit2.http.GET
import retrofit2.http.Query

interface ListUsersService {
    @GET("/api/users")
    suspend fun getListUsers(
        @Query("page") num: Int = 2
    ): ResponseListUsersDto
}