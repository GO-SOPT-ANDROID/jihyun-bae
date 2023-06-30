package org.android.go.sopt.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.android.go.sopt.data.remote.api.MemberServicePool
import org.android.go.sopt.data.remote.datasource.AuthRemoteDataSource
import org.android.go.sopt.data.remote.model.ResponseSignInDto
import org.android.go.sopt.data.remote.model.ResponseSignUpDto

class SignViewModel : ViewModel() {
    val id: MutableLiveData<String> = MutableLiveData()
    val pw: MutableLiveData<String> = MutableLiveData()
    val name: MutableLiveData<String> = MutableLiveData()
    val specialty: MutableLiveData<String> = MutableLiveData()

    val isIdValid: LiveData<Boolean> = id.map { id -> id.matches(Regex(ID_PATTERN)) }
    val isPwValid: LiveData<Boolean> = pw.map { pw -> pw.matches(Regex(PW_PATTERN)) }

    val issignUpEnabled = MediatorLiveData<Boolean>().apply {
        addSource(id) { value = checkSignUpEnabled() }
        addSource(pw) { value = checkSignUpEnabled() }
        addSource(name) { value = checkSignUpEnabled() }
        addSource(specialty) { value = checkSignUpEnabled() }
    }

    private val _signUpResult: MutableLiveData<ResponseSignUpDto> = MutableLiveData()
    val signUpResult: LiveData<ResponseSignUpDto> = _signUpResult
    private val _signUpMessage: MutableLiveData<String> = MutableLiveData()
    val signUpMessage: LiveData<String> = _signUpMessage

    private val _signInResult: MutableLiveData<ResponseSignInDto> = MutableLiveData()
    val signInResult: LiveData<ResponseSignInDto> = _signInResult
    private val _signInMessage: MutableLiveData<String> = MutableLiveData()
    val signInMessage: LiveData<String> = _signInMessage

    private val authService = MemberServicePool.authService
    private val authRemoteDataSource = AuthRemoteDataSource(authService)

    fun checkSignUpEnabled(): Boolean {
        return isIdValid.value == true
                && isPwValid.value == true
                && !name.value.isNullOrBlank()
                && !specialty.value.isNullOrBlank()
    }

    fun signUp() {
        viewModelScope.launch {
            try {
                val response = authRemoteDataSource.signUp(
                    id.value.toString(),
                    pw.value.toString(),
                    name.value.toString(),
                    specialty.value.toString()
                )
                if (response.isSuccessful) {
                    _signUpMessage.value = response.body()?.message ?: "회원가입에 성공했습니다."
                    _signUpResult.value = response.body()
                } else {
                    _signUpMessage.value = "회원가입에 실패했습니다."
                }
            } catch (e: Exception) {
                _signUpMessage.value = e.message ?: "회원가입 중 오류가 발생했습니다."
            }
        }
    }

    fun signIn() {
        viewModelScope.launch {
            try {
                val response = authRemoteDataSource.signIn(id.value.toString(), pw.value.toString())
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

    companion object {
        const val ID_PATTERN = "^(?=.*[a-zA-Z])(?=.*[0-9])[a-zA-Z0-9]{6,10}"
        const val PW_PATTERN =
            "^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[!@#%^&*()])[a-zA-Z0-9!@#%^&*()]{6,12}"
    }
}