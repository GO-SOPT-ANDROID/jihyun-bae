package org.android.go.sopt.domain.repository

import okhttp3.MultipartBody
import org.android.go.sopt.data.model.response.ResponseMusicDto
import retrofit2.Response

interface MusicRepository {
    suspend fun uploadMusicInfo(
        id: String,
        image: MultipartBody.Part,
        singer: String,
        title: String
    )

    suspend fun getMusicList(id: String): Response<ResponseMusicDto>
}