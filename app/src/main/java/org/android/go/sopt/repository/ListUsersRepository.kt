package org.android.go.sopt.repository

import org.android.go.sopt.data.remote.model.ResponseListUsersDto
import retrofit2.Response

interface ListUsersRepository {
    suspend fun getListUsers(): Response<ResponseListUsersDto>
}