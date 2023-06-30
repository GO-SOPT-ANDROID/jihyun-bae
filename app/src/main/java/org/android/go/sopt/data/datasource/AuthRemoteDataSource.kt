package org.android.go.sopt.data.datasource

import org.android.go.sopt.data.model.request.RequestSignInDto
import org.android.go.sopt.data.model.request.RequestSignUpDto
import org.android.go.sopt.data.model.response.ResponseSignInDto
import org.android.go.sopt.data.model.response.ResponseSignUpDto
import org.android.go.sopt.data.service.AuthService
import org.android.go.sopt.domain.repository.AuthRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class AuthRemoteDataSource(private val authService: AuthService) : AuthRepository {
    override suspend fun signIn(id: String, pw: String): Response<ResponseSignInDto> {
        return suspendCoroutine { continuation ->
            authService.signIn(
                RequestSignInDto(
                    id,
                    pw
                )
            ).enqueue(object : Callback<ResponseSignInDto> {
                override fun onResponse(
                    call: Call<ResponseSignInDto>,
                    response: Response<ResponseSignInDto>
                ) {
                    if (response.isSuccessful) {
                        continuation.resume(Response.success(response.body()))
                    } else {
                        continuation.resumeWithException(Exception("로그인에 실패했습니다."))
                    }
                }

                override fun onFailure(call: Call<ResponseSignInDto>, t: Throwable) {
                    continuation.resumeWithException(t)
                }
            })
        }
    }

    override suspend fun signUp(
        id: String,
        pw: String,
        name: String,
        specialty: String
    ): Response<ResponseSignUpDto> {
        return suspendCoroutine { continuation ->
            authService.signUp(
                RequestSignUpDto(
                    id,
                    pw,
                    name,
                    specialty
                )
            ).enqueue(object : Callback<ResponseSignUpDto> {
                override fun onResponse(
                    call: Call<ResponseSignUpDto>,
                    response: Response<ResponseSignUpDto>
                ) {
                    if (response.isSuccessful) {
                        continuation.resume(Response.success(response.body()))
                    } else {
                        continuation.resumeWithException(Exception("회원가입에 실패했습니다."))
                    }
                }

                override fun onFailure(call: Call<ResponseSignUpDto>, t: Throwable) {
                    continuation.resumeWithException(t)
                }
            })
        }
    }
}