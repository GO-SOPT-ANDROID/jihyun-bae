package org.android.go.sopt.domain.repository

import org.android.go.sopt.data.model.response.ResponseKakaoSearchDto
import retrofit2.Response

interface KakaoSearchRepository {
    suspend fun getKakaoSearch(searchWord: String): Response<ResponseKakaoSearchDto>
}