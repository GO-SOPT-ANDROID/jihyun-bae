package org.android.go.sopt.presentation.search

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.android.go.sopt.data.api.KakaoSearchServicePool
import org.android.go.sopt.data.datasource.KakaoSearchDataSource
import org.android.go.sopt.data.model.response.ResponseKakaoSearchDto

class SearchViewModel : ViewModel() {
    private val _getKakaoSearchResult: MutableLiveData<ResponseKakaoSearchDto> = MutableLiveData()
    val getKakaoSearchResult: LiveData<ResponseKakaoSearchDto> = _getKakaoSearchResult

    private val kakaoSearchService = KakaoSearchServicePool.KakaoSearchService
    private val kakaoSearchDataSource = KakaoSearchDataSource(kakaoSearchService)

    fun getKakaoSearch(searchWord: String) {
        viewModelScope.launch {
            try {
                val response = kakaoSearchDataSource.getKakaoSearch(searchWord = searchWord)
                if (response.isSuccessful) {
                    _getKakaoSearchResult.value = response.body()
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error occurred: ${e.message}")
            }
        }
    }
}