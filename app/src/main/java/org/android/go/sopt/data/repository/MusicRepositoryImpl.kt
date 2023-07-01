package org.android.go.sopt.data.repository

import okhttp3.MultipartBody
import org.android.go.sopt.data.datasource.remote.MusicRemoteDataSource
import org.android.go.sopt.data.model.response.ResponseMusicDto
import org.android.go.sopt.data.model.response.ResponseUploadMusicDto
import org.android.go.sopt.domain.repository.MusicRepository

class MusicRepositoryImpl(private val musicRemoteDataSource: MusicRemoteDataSource) :
    MusicRepository {
    override suspend fun getMusicList(id: String): Result<ResponseMusicDto.MusicList> =
        runCatching {
            musicRemoteDataSource.getMusicList(id).data
        }

    override suspend fun uploadMusic(
        id: String,
        image: MultipartBody.Part,
        singer: String,
        title: String
    ): Result<ResponseUploadMusicDto.MusicData> = runCatching {
        musicRemoteDataSource.uploadMusic(id, image, singer, title).data
    }
}