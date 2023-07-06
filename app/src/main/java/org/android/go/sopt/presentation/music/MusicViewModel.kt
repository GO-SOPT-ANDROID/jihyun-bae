package org.android.go.sopt.presentation.music

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import org.android.go.sopt.data.model.response.ResponseMusicDto
import org.android.go.sopt.domain.repository.MusicRepository
import org.android.go.sopt.util.UiState
import timber.log.Timber

class MusicViewModel(private val musicRepository: MusicRepository) : ViewModel() {
    val title: MutableLiveData<String> = MutableLiveData()
    val singer: MutableLiveData<String> = MutableLiveData()
    private val _getListMusicState: MutableLiveData<UiState<ResponseMusicDto.MusicList>> =
        MutableLiveData()
    val getListMusicState: LiveData<UiState<ResponseMusicDto.MusicList>> = _getListMusicState
    private val _isLoading: MutableLiveData<Boolean> = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

    fun uploadMusic(id: String, image: MultipartBody.Part) {
        viewModelScope.launch {
            musicRepository.uploadMusic(id, image, singer.value.toString(), title.value.toString())
                .onSuccess {
                    getMusicList(id)
                    Timber.d("music", "음악 업로드 요청 완료!")
                }
                .onFailure {
                    Timber.e("music", "음악 업로드 중 오류 발생")
                }
        }
    }

    fun getMusicList(id: String) {
        _isLoading.value = true

        viewModelScope.launch {
            musicRepository.getMusicList(id)
                .onSuccess { musicList ->
                    _getListMusicState.value = UiState.Success(musicList)
                    _isLoading.value = false
                }
                .onFailure { throwable ->
                    _getListMusicState.value = UiState.Error(throwable.message)
                    _isLoading.value = false
                }
        }
    }
}