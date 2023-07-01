package org.android.go.sopt.data.service

import okhttp3.MultipartBody
import org.android.go.sopt.data.model.response.ResponseMusicDto
import org.android.go.sopt.data.model.response.ResponseUploadMusicDto
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path

interface MusicService {
    @Multipart
    @POST("music")
    suspend fun uploadMusic(
        @Header("id") id: String,
        @Part image: MultipartBody.Part,
        @Part("title") title: String,
        @Part("singer") singer: String
    ): ResponseUploadMusicDto

    @GET("{id}/music")
    suspend fun getMusicList(
        @Path("id") id: String
    ): ResponseMusicDto
}