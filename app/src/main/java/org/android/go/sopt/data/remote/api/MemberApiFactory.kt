package org.android.go.sopt.data.remote.api

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import org.android.go.sopt.BuildConfig
import org.android.go.sopt.data.remote.service.LoginService
import org.android.go.sopt.data.remote.service.SignUpService
import retrofit2.Retrofit

object MemberApiFactory {
    private const val BASE_URL = BuildConfig.AUTH_BASE_URL

    val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
            .build()
    }

    inline fun <reified T> create(): T = retrofit.create<T>(T::class.java)
}

object MemberServicePool {
    val signUpService = MemberApiFactory.create<SignUpService>()
    val loginService = MemberApiFactory.create<LoginService>()
}