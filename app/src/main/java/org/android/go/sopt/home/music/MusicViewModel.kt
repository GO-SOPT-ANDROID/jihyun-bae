package org.android.go.sopt.home.music

import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import org.android.go.sopt.data.remote.api.MusicServicePool
import org.android.go.sopt.data.remote.datasource.MusicRemoteDataSource
import org.android.go.sopt.data.remote.model.ResponseMusicDto

class MusicViewModel : ViewModel() {
    val image: MutableLiveData<Uri> = MutableLiveData()
    val title: MutableLiveData<String> = MutableLiveData()
    val singer: MutableLiveData<String> = MutableLiveData()
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
                Log.d("music", "음악 업로드 요청 완료!")
            } catch (e: Exception) {
                Log.e("music", "음악 업로드 중 오류 발생: ${e.message}")
            }
        }
    }

    fun getMusicList(id: String) {
        _isLoading.value = true

        viewModelScope.launch {
            try {
                val response = musicRemoteDataSource.getMusicList(id)
                Log.e("music", response.toString())
                if (response.isSuccessful) _getListMusicResult.value = response.body()
                _isLoading.value = false
            } catch (e: Exception) {
                _isLoading.value = false
                Log.e("music", e.toString())
            }
        }
    }
}