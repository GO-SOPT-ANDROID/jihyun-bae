package org.android.go.sopt.domain.repository

import org.android.go.sopt.domain.model.ListUser

interface ListUsersRepository {
    suspend fun getListUsers(): Result<List<ListUser>>
}