package org.android.go.sopt.data.repository

import org.android.go.sopt.data.model.response.ResponseKakaoSearchDto
import retrofit2.Response

interface KakaoSearchRepositoryImpl {
    suspend fun getKakaoSearch(searchWord: String): Response<ResponseKakaoSearchDto>
}