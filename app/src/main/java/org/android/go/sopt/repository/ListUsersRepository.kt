package org.android.go.sopt.repository

import org.android.go.sopt.data.remote.datasource.ListUsersRemoteDataSource
import org.android.go.sopt.data.remote.model.ResponseListUsersDto

class ListUsersRepository(private val listUsersRemoteDataSource: ListUsersRemoteDataSource) {
    suspend fun getListUsers(): Result<ResponseListUsersDto> =
        runCatching {
            listUsersRemoteDataSource.getListUsers()
        }
}