package org.android.go.sopt.data.service

import org.android.go.sopt.BuildConfig
import org.android.go.sopt.data.model.response.ResponseKakaoSearchDto
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface KakaoSearchService {
    @Headers("Authorization: KakaoAK ${BuildConfig.REST_API_KEY} ")
    @GET("/v2/search/web")
    suspend fun getKakaoSearch(
        @Query("query") searchWord: String
    ): ResponseKakaoSearchDto
}