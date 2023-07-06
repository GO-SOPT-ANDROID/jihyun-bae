package org.android.go.sopt.data.datasource.remote

import org.android.go.sopt.data.api.ListUsersServicePool
import org.android.go.sopt.data.model.response.ResponseListUsersDto

class ListUsersRemoteDataSource {
    private val listUsersService = ListUsersServicePool.listUsersService
    suspend fun getListUsers(): ResponseListUsersDto =
        listUsersService.getListUsers()
}