package org.android.go.sopt.data.remote.datasource

import org.android.go.sopt.data.remote.model.ResponseListUsersDto
import org.android.go.sopt.data.remote.service.ListUsersService
import org.android.go.sopt.repository.ListUsersRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class ListUsersRemoteDataSource(private val listUsersService: ListUsersService) :
    ListUsersRepository {
    override suspend fun getListUsers(): Response<ResponseListUsersDto> {
        return suspendCoroutine { continuation ->
            listUsersService.getListUsers().enqueue(object : Callback<ResponseListUsersDto> {
                override fun onResponse(
                    call: Call<ResponseListUsersDto>,
                    response: Response<ResponseListUsersDto>
                ) {
                    if (response.isSuccessful) {
                        continuation.resume(Response.success(response.body()))
                    } else {
                        continuation.resumeWithException(Exception("유저 정보를 불러오는데 실패했습니다."))
                    }
                }

                override fun onFailure(call: Call<ResponseListUsersDto>, t: Throwable) {
                    continuation.resumeWithException(t)
                }
            })
        }
    }
}