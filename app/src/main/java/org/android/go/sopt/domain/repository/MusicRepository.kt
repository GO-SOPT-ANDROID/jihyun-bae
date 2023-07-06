package org.android.go.sopt.domain.repository

import okhttp3.MultipartBody
import org.android.go.sopt.data.model.response.ResponseMusicDto
import org.android.go.sopt.data.model.response.ResponseUploadMusicDto

interface MusicRepository {
    suspend fun uploadMusic(
        id: String,
        image: MultipartBody.Part,
        singer: String,
        title: String
    ): Result<ResponseUploadMusicDto.MusicData>

    suspend fun getMusicList(id: String): Result<ResponseMusicDto.MusicList>
}