package org.android.go.sopt.repository

import okhttp3.MultipartBody

interface ImageRepository {
    suspend fun uploadImage(image: MultipartBody.Part)
}