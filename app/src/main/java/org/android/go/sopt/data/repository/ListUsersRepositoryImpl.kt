package org.android.go.sopt.data.repository

import org.android.go.sopt.data.datasource.remote.ListUsersRemoteDataSource
import org.android.go.sopt.domain.model.ListUser
import org.android.go.sopt.domain.repository.ListUsersRepository

class ListUsersRepositoryImpl(private val listUsersRemoteDataSource: ListUsersRemoteDataSource) :
    ListUsersRepository {
    override suspend fun getListUsers(): Result<List<ListUser>> =
        runCatching {
            listUsersRemoteDataSource.getListUsers().toListUser()
        }
}