package org.android.go.sopt.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.android.go.sopt.data.remote.api.MemberServicePool
import org.android.go.sopt.data.remote.model.RequestSignInDto
import org.android.go.sopt.data.remote.model.ResponseSignInDto
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignInViewModel : ViewModel() {
    private val _signInResult: MutableLiveData<ResponseSignInDto> = MutableLiveData()
    val signInResult: LiveData<ResponseSignInDto> = _signInResult
    val id: MutableLiveData<String> = MutableLiveData()
    val pw: MutableLiveData<String> = MutableLiveData()
    private val _signInMessage: MutableLiveData<String> = MutableLiveData()
    val signInMessage: LiveData<String> = _signInMessage
    private val loginService = MemberServicePool.loginService

    fun signIn(id: String, pw: String) {
        loginService.signIn(
            RequestSignInDto(
                id,
                pw
            )
        ).enqueue(object : Callback<ResponseSignInDto> {
            override fun onResponse(
                call: Call<ResponseSignInDto>,
                response: Response<ResponseSignInDto>
            ) {
                if (response.isSuccessful) {
                    _signInMessage.value = response.body()?.message ?: "로그인에 성공했습니다."
                    _signInResult.value = response.body()
                } else {
                    _signInMessage.value = response.body()?.message ?: "로그인에 실패했습니다."
                }
            }

            override fun onFailure(call: Call<ResponseSignInDto>, t: Throwable) {
                _signInMessage.value = t.message ?: "서버와 통신이 원활하지 않습니다."
            }

        })
    }
}