package org.android.go.sopt.data.datasource.remote

import org.android.go.sopt.data.api.AuthServicePool
import org.android.go.sopt.data.model.request.RequestSignInDto
import org.android.go.sopt.data.model.request.RequestSignUpDto
import org.android.go.sopt.data.model.response.ResponseSignInDto
import org.android.go.sopt.data.model.response.ResponseSignUpDto

class AuthRemoteDataSource {
    private val authService = AuthServicePool.authService
    suspend fun signIn(requestSignInDto: RequestSignInDto): ResponseSignInDto =
        authService.signIn(requestSignInDto)

    suspend fun signUp(requestSignUpDto: RequestSignUpDto): ResponseSignUpDto =
        authService.signUp(requestSignUpDto)
}