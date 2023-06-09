package org.android.go.sopt.data.remote.service

import org.android.go.sopt.data.remote.model.RequestSignInDto
import org.android.go.sopt.data.remote.model.RequestSignUpDto
import org.android.go.sopt.data.remote.model.ResponseSignInDto
import org.android.go.sopt.data.remote.model.ResponseSignUpDto
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {
    @POST("sign-in")
    fun signIn(
        @Body requestLoginDto: RequestSignInDto
    ): Call<ResponseSignInDto>

    @POST("sign-up")
    fun signUp(
        @Body requestSignUpDto: RequestSignUpDto
    ): Call<ResponseSignUpDto>
}