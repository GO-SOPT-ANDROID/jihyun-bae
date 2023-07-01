package org.android.go.sopt.presentation.search

import android.content.ContentValues.TAG
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.android.go.sopt.data.repository.KakaoSearchRepositoryImpl
import org.android.go.sopt.domain.model.SearchDocument
import timber.log.Timber

class SearchViewModel(private val kakaoSearchRepository: KakaoSearchRepositoryImpl) : ViewModel() {
    private val _getKakaoSearchResult: MutableLiveData<List<SearchDocument>> = MutableLiveData()
    val getKakaoSearchResult: LiveData<List<SearchDocument>> = _getKakaoSearchResult

    fun getKakaoSearch(searchWord: String) {
        viewModelScope.launch {
            kakaoSearchRepository.getKakaoSearch(searchWord = searchWord)
                .onSuccess { searchList ->
                    _getKakaoSearchResult.value = searchList
                }
                .onFailure {
                    Timber.e(TAG, "Error occurred")
                }
        }
    }
}