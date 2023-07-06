package org.android.go.sopt.data.repository

import okhttp3.MultipartBody

interface ImageRepositoryImpl {
    suspend fun uploadImage(image: MultipartBody.Part)
}