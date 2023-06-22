package org.android.go.sopt.data.remote.datasource

import android.util.Log
import okhttp3.MultipartBody
import org.android.go.sopt.data.remote.model.ResponseMusicDto
import org.android.go.sopt.data.remote.service.MusicService
import org.android.go.sopt.repository.MusicRepository
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
        title: String,
        singer: String
    ) {
        return suspendCoroutine { continuation ->
            musicService.uploadMusic(id, image, title, singer).enqueue(object : Callback<Unit> {
                override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                    if (response.isSuccessful) {
                        Log.d("music", "음악 업로드 성공!")
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
                        Log.e("music", "성공")
                    } else {
                        continuation.resumeWithException(Exception("Music 정보를 불러오는데 실패했습니다."))
                        Log.e("music", "실패")
                    }
                }

                override fun onFailure(call: Call<ResponseMusicDto>, t: Throwable) {
                    continuation.resumeWithException(t)
                }

            })
        }
    }

}