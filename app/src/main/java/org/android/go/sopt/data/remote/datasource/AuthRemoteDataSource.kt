package org.android.go.sopt.data.remote.datasource

import org.android.go.sopt.data.remote.model.RequestSignInDto
import org.android.go.sopt.data.remote.model.ResponseSignInDto
import org.android.go.sopt.data.remote.service.SignInService
import org.android.go.sopt.repository.AuthRepository
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
}