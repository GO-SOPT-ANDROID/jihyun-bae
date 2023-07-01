package org.android.go.sopt.data.service

import org.android.go.sopt.data.model.request.RequestSignInDto
import org.android.go.sopt.data.model.request.RequestSignUpDto
import org.android.go.sopt.data.model.response.ResponseSignInDto
import org.android.go.sopt.data.model.response.ResponseSignUpDto
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {
    @POST("sign-in")
    suspend fun signIn(
        @Body requestSignInDto: RequestSignInDto
    ): ResponseSignInDto

    @POST("sign-up")
    suspend fun signUp(
        @Body requestSignUpDto: RequestSignUpDto
    ): ResponseSignUpDto
}