package org.android.go.sopt.data.repository

import org.android.go.sopt.data.model.response.ResponseSignInDto
import org.android.go.sopt.data.model.response.ResponseSignUpDto
import retrofit2.Response

interface AuthRepositoryImpl {
    suspend fun signIn(id: String, pw: String): Response<ResponseSignInDto>
    suspend fun signUp(
        id: String,
        pw: String,
        name: String,
        specialty: String
    ): Response<ResponseSignUpDto>
}