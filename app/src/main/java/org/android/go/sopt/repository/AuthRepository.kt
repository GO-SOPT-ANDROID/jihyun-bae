package org.android.go.sopt.repository

import org.android.go.sopt.data.remote.model.ResponseSignInDto
import org.android.go.sopt.data.remote.model.ResponseSignUpDto
import retrofit2.Response

interface AuthRepository {
    suspend fun signIn(id: String, pw: String): Response<ResponseSignInDto>
    suspend fun signUp(
        id: String,
        pw: String,
        name: String,
        specialty: String
    ): Response<ResponseSignUpDto>
}