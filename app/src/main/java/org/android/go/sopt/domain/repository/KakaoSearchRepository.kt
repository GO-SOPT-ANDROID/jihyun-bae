package org.android.go.sopt.domain.repository

import org.android.go.sopt.domain.model.SearchDocument

interface KakaoSearchRepository {
    suspend fun getKakaoSearch(searchWord: String): Result<List<SearchDocument>>
}