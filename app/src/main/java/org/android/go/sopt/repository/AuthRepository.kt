package org.android.go.sopt.repository

import org.android.go.sopt.data.remote.model.ResponseSignInDto
import retrofit2.Response

interface AuthRepository {
    suspend fun signIn(id: String, pw: String): Response<ResponseSignInDto>
}