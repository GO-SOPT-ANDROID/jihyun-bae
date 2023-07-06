package org.android.go.sopt.presentation.gallery

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.android.go.sopt.data.api.ImageServicePool
import org.android.go.sopt.data.datasource.remote.ImageRemoteDataSource
import org.android.go.sopt.util.extension.ContentUriRequestBody

class GalleryViewModel : ViewModel() {
    private val _image = MutableLiveData<ContentUriRequestBody>()
    val image: LiveData<ContentUriRequestBody>
        get() = _image

    private val imageService = ImageServicePool.imageService
    private val imageRemoteDataSource = ImageRemoteDataSource(imageService)

    fun setRequestBody(requestBody: ContentUriRequestBody) {
        _image.value = requestBody
    }

    fun uploadImage() {
        if (image.value == null) {
            Log.d("gallery", "이미지가 등록되지 않았습니다.")
        } else {
            viewModelScope.launch {
                try {
                    imageRemoteDataSource.uploadImage(image.value!!.toFormData())
                    Log.d("gallery", "이미지 업로드 요청 완료!")
                } catch (e: Exception) {
                    Log.e("gallery", "이미지 업로드 중 오류 발생: ${e.message}")
                }
            }
        }
    }

}