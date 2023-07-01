package org.android.go.sopt.data.datasource.remote

import android.util.Log
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.android.go.sopt.data.model.response.ResponseMusicDto
import org.android.go.sopt.data.service.MusicService
import org.android.go.sopt.data.repository.MusicRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class MusicRemoteDataSource(private val musicService: MusicService) : MusicRepository {
    override suspend fun uploadMusicInfo(
        id: String,
        image: MultipartBody.Part,
        singer: String,
        title: String
    ) {
        return suspendCoroutine { continuation ->
            val titleBody = title.toRequestBody("text/plain".toMediaType())
            val singerBody = singer.toRequestBody("text/plain".toMediaType())

            musicService.uploadMusic(id, image, titleBody, singerBody)
                .enqueue(object : Callback<Unit> {
                    override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                        if (response.isSuccessful) {
                            continuation.resume(Unit)
                        } else {
                            continuation.resumeWithException(Exception("음악 업로드 실패!"))
                        }
                    }

                    override fun onFailure(call: Call<Unit>, t: Throwable) {
                        continuation.resumeWithException(t)
                    }

                })
        }
    }

    override suspend fun getMusicList(id: String): Response<ResponseMusicDto> {
        return suspendCoroutine { continuation ->
            musicService.getMusicList(id).enqueue(object : Callback<ResponseMusicDto> {
                override fun onResponse(
                    call: Call<ResponseMusicDto>,
                    response: Response<ResponseMusicDto>
                ) {
                    if (response.isSuccessful) {
                        continuation.resume(Response.success(response.body()))
                        Log.e("music", "Music 정보 불러오기 성공")
                    } else {
                        continuation.resumeWithException(Exception("Music 정보를 불러오는데 실패했습니다."))
                        Log.e("music", "Music 정보 불러오기 실패")
                    }
                }

                override fun onFailure(call: Call<ResponseMusicDto>, t: Throwable) {
                    continuation.resumeWithException(t)
                }

            })
        }
    }

}