package org.android.go.sopt.data.repository

import org.android.go.sopt.data.datasource.remote.ListUsersRemoteDataSource
import org.android.go.sopt.data.model.response.ResponseListUsersDto

class ListUsersRepository(private val listUsersRemoteDataSource: ListUsersRemoteDataSource) {
    suspend fun getListUsers(): Result<ResponseListUsersDto> =
        runCatching {
            listUsersRemoteDataSource.getListUsers()
        }
}