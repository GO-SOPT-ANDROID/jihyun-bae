package org.android.go.sopt.domain.repository

import org.android.go.sopt.data.model.response.ResponseSignInDto
import org.android.go.sopt.data.model.response.ResponseSignUpDto

interface AuthRepository {
    suspend fun signIn(id: String, password: String): Result<ResponseSignInDto.SignInData>

    suspend fun signUp(
        id: String,
        password: String,
        name: String,
        skill: String
    ): Result<ResponseSignUpDto.SignUpData>
}