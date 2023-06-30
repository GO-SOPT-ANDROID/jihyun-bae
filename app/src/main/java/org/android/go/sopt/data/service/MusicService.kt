package org.android.go.sopt.data.service

import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.android.go.sopt.data.model.response.ResponseMusicDto
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path

interface MusicService {
    @Multipart
    @POST("music")
    fun uploadMusic(
        @Header("id") id: String,
        @Part image: MultipartBody.Part,
        @Part("title") title: RequestBody,
        @Part("singer") singer: RequestBody
    ): Call<Unit>

    @GET("{id}/music")
    fun getMusicList(
        @Path("id") id: String
    ): Call<ResponseMusicDto>
}