package org.android.go.sopt.data.datasource

import android.util.Log
import okhttp3.MultipartBody
import org.android.go.sopt.data.service.ImageService
import org.android.go.sopt.domain.repository.ImageRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class ImageRemoteDataSource(private val imageService: ImageService) : ImageRepository {
    override suspend fun uploadImage(image: MultipartBody.Part) {
        return suspendCoroutine { continuation ->
            imageService.uploadImage(image).enqueue(object : Callback<Unit> {
                override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                    if (response.isSuccessful) {
                        Log.d("gallery", "이미지 업로드 성공!")
                        continuation.resume(Unit)
                    } else {
                        continuation.resumeWithException(Exception("이미지 업로드 실패!"))
                    }
                }

                override fun onFailure(call: Call<Unit>, t: Throwable) {
                    continuation.resumeWithException(t)
                }

            })
        }
    }
}