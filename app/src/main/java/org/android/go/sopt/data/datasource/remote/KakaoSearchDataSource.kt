package org.android.go.sopt.data.datasource.remote

import org.android.go.sopt.data.model.response.ResponseKakaoSearchDto
import org.android.go.sopt.data.service.KakaoSearchService
import org.android.go.sopt.data.repository.KakaoSearchRepositoryImpl
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class KakaoSearchDataSource(private val kakaoSearchService: KakaoSearchService) :
    KakaoSearchRepositoryImpl {
    override suspend fun getKakaoSearch(searchWord: String): Response<ResponseKakaoSearchDto> {
        return suspendCoroutine { continuation ->
            kakaoSearchService.getKakaoSearch(searchWord = searchWord)
                .enqueue(object : Callback<ResponseKakaoSearchDto> {
                    override fun onResponse(
                        call: Call<ResponseKakaoSearchDto>,
                        response: Response<ResponseKakaoSearchDto>
                    ) {
                        if (response.isSuccessful) {
                            continuation.resume(Response.success(response.body()))
                        } else {
                            continuation.resumeWithException(Exception("검색에 실패했습니다."))
                        }
                    }

                    override fun onFailure(call: Call<ResponseKakaoSearchDto>, t: Throwable) {
                        continuation.resumeWithException(t)
                    }
                })
        }
    }
}