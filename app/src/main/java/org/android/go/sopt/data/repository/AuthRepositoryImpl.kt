package org.android.go.sopt.data.repository

import org.android.go.sopt.data.datasource.remote.AuthRemoteDataSource
import org.android.go.sopt.data.model.request.RequestSignInDto
import org.android.go.sopt.data.model.request.RequestSignUpDto
import org.android.go.sopt.data.model.response.ResponseSignInDto
import org.android.go.sopt.data.model.response.ResponseSignUpDto
import org.android.go.sopt.domain.repository.AuthRepository

class AuthRepositoryImpl(private val authRemoteDataSource: AuthRemoteDataSource) :
    AuthRepository {
    override suspend fun signIn(
        id: String,
        password: String
    ): Result<ResponseSignInDto.SignInData> =
        runCatching {
            authRemoteDataSource.signIn(RequestSignInDto(id, password)).data
        }

    override suspend fun signUp(
        id: String,
        password: String,
        name: String,
        skill: String
    ): Result<ResponseSignUpDto.SignUpData> =
        runCatching {
            authRemoteDataSource.signUp(RequestSignUpDto(id, password, name, skill)).data
        }
}