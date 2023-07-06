package org.android.go.sopt.data.repository

import org.android.go.sopt.data.datasource.remote.KakaoSearchDataSource
import org.android.go.sopt.domain.model.SearchDocument
import org.android.go.sopt.domain.repository.KakaoSearchRepository

class KakaoSearchRepositoryImpl(private val kakaoSearchDataSource: KakaoSearchDataSource) :
    KakaoSearchRepository {
    override suspend fun getKakaoSearch(searchWord: String): Result<List<SearchDocument>> =
        runCatching {
            kakaoSearchDataSource.getKakaoSearch(searchWord).toSearchDocument()
        }
}