package org.android.go.sopt.data.remote.datasource

import org.android.go.sopt.data.remote.api.ListUsersServicePool
import org.android.go.sopt.data.remote.model.ResponseListUsersDto

class ListUsersRemoteDataSource {
    private val listUsersService = ListUsersServicePool.listUsersService
    suspend fun getListUsers(): ResponseListUsersDto =
        listUsersService.getListUsers()
}