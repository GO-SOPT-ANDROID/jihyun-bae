package org.android.go.sopt.repository

import okhttp3.MultipartBody
import org.android.go.sopt.data.remote.model.ResponseMusicDto
import retrofit2.Response

interface MusicRepository {
    suspend fun uploadMusicInfo(
        id: String,
        image: MultipartBody.Part,
        title: String,
        singer: String
    )

    suspend fun getMusicList(id: String): Response<ResponseMusicDto>
}