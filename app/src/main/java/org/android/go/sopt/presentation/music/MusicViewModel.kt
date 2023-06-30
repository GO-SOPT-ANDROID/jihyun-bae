package org.android.go.sopt.presentation.music

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import org.android.go.sopt.data.api.MusicServicePool
import org.android.go.sopt.data.datasource.MusicRemoteDataSource
import org.android.go.sopt.data.model.response.ResponseMusicDto

class MusicViewModel : ViewModel() {
    private val _getListMusicResult: MutableLiveData<ResponseMusicDto> = MutableLiveData()
    val getListMusicResult: LiveData<ResponseMusicDto> = _getListMusicResult
    private val _isLoading: MutableLiveData<Boolean> = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

    private val musicService = MusicServicePool.musicService
    private val musicRemoteDataSource = MusicRemoteDataSource(musicService)

    fun uploadMusic(id: String, image: MultipartBody.Part, singer: String, title: String) {
        viewModelScope.launch {
            try {
                musicRemoteDataSource.uploadMusicInfo(id, image, singer, title)
                getMusicList(id)
                Log.d("music", "음악 업로드 요청 완료!")
            } catch (e: Exception) {
                Log.e("music", "음악 업로드 중 오류 발생: ${e.message}")
            }
        }
    }

    fun getMusicList(id: String) {
        _isLoading.value = true

        viewModelScope.launch {
            val result = runCatching {
                musicRemoteDataSource.getMusicList(id)
            }

            result.onSuccess { response ->
                if (response.isSuccessful) _getListMusicResult.value = response.body()
            }

            _isLoading.value = false
        }
    }
}