package org.android.go.sopt.repository

import org.android.go.sopt.data.remote.model.ResponseKakaoSearchDto
import retrofit2.Response

interface KakaoSearchRepository {
    suspend fun getKakaoSearch(searchWord: String): Response<ResponseKakaoSearchDto>
}