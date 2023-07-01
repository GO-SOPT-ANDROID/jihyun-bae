package org.android.go.sopt.data.datasource.remote

import org.android.go.sopt.data.api.KakaoSearchServicePool
import org.android.go.sopt.data.model.response.ResponseKakaoSearchDto

class KakaoSearchDataSource {
    private val kakaoSearchService = KakaoSearchServicePool.KakaoSearchService
    suspend fun getKakaoSearch(searchWord: String): ResponseKakaoSearchDto =
        kakaoSearchService.getKakaoSearch(searchWord)
}