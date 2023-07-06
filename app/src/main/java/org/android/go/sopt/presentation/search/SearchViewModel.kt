package org.android.go.sopt.presentation.search

import android.content.ContentValues.TAG
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.android.go.sopt.domain.model.SearchDocument
import org.android.go.sopt.domain.repository.KakaoSearchRepository
import org.android.go.sopt.util.UiState
import timber.log.Timber

class SearchViewModel(private val kakaoSearchRepository: KakaoSearchRepository) : ViewModel() {
    private val _getKakaoSearchState: MutableLiveData<UiState<List<SearchDocument>>> =
        MutableLiveData()
    val getKakaoSearchState: LiveData<UiState<List<SearchDocument>>> = _getKakaoSearchState

    fun getKakaoSearch(searchWord: String) {
        viewModelScope.launch {
            kakaoSearchRepository.getKakaoSearch(searchWord = searchWord)
                .onSuccess { searchList ->
                    if (searchList.isEmpty()) _getKakaoSearchState.value = UiState.Empty
                    else _getKakaoSearchState.value = UiState.Success(searchList)
                }
                .onFailure { throwable ->
                    _getKakaoSearchState.value = UiState.Error(throwable.message)
                    Timber.e(TAG, "Error occurred")
                }
        }
    }
}