package org.android.go.sopt.data.datasource.remote

import okhttp3.MultipartBody
import org.android.go.sopt.data.api.MusicServicePool
import org.android.go.sopt.data.model.response.ResponseMusicDto
import org.android.go.sopt.data.model.response.ResponseUploadMusicDto

class MusicRemoteDataSource {
    private val musicService = MusicServicePool.musicService
    suspend fun uploadMusic(
        id: String,
        image: MultipartBody.Part,
        singer: String,
        title: String
    ): ResponseUploadMusicDto =
        musicService.uploadMusic(id = id, image = image, singer = singer, title = title)

    suspend fun getMusicList(id: String): ResponseMusicDto = musicService.getMusicList(id = id)
}