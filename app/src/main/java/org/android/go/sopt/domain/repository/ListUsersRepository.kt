package org.android.go.sopt.domain.repository

import org.android.go.sopt.data.datasource.ListUsersRemoteDataSource
import org.android.go.sopt.data.model.response.ResponseListUsersDto

class ListUsersRepository(private val listUsersRemoteDataSource: ListUsersRemoteDataSource) {
    suspend fun getListUsers(): Result<ResponseListUsersDto> =
        runCatching {
            listUsersRemoteDataSource.getListUsers()
        }
}