package org.android.go.sopt.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.android.go.sopt.data.remote.api.MemberServicePool
import org.android.go.sopt.data.remote.datasource.AuthRemoteDataSource
import org.android.go.sopt.data.remote.model.ResponseSignInDto

class SignInViewModel : ViewModel() {
    private val _signInResult: MutableLiveData<ResponseSignInDto> = MutableLiveData()
    val signInResult: LiveData<ResponseSignInDto> = _signInResult
    val id: MutableLiveData<String> = MutableLiveData()
    val pw: MutableLiveData<String> = MutableLiveData()
    private val _signInMessage: MutableLiveData<String> = MutableLiveData()
    val signInMessage: LiveData<String> = _signInMessage
    private val loginService = MemberServicePool.loginService
    private val authRemoteDataSource = AuthRemoteDataSource(loginService)

    fun signIn(id: String, pw: String) {
        viewModelScope.launch {
            try {
                val response = authRemoteDataSource.signIn(id, pw)
                if (response.isSuccessful) {
                    _signInMessage.value = response.body()?.message ?: "로그인에 성공했습니다."
                    _signInResult.value = response.body()
                } else {
                    _signInMessage.value = "로그인에 실패했습니다."
                }
            } catch (e: Exception) {
                _signInMessage.value = e.message ?: "로그인 중 오류가 발생했습니다."
            }
        }
    }
}