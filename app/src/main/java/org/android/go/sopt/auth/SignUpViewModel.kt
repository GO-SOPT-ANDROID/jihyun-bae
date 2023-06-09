package org.android.go.sopt.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.android.go.sopt.data.remote.api.MemberServicePool
import org.android.go.sopt.data.remote.datasource.AuthRemoteDataSource
import org.android.go.sopt.data.remote.model.ResponseSignUpDto

class SignUpViewModel : ViewModel() {
    val id: MutableLiveData<String> = MutableLiveData()
    val pw: MutableLiveData<String> = MutableLiveData()
    val name: MutableLiveData<String> = MutableLiveData()
    val specialty: MutableLiveData<String> = MutableLiveData()

    private val _signUpResult: MutableLiveData<ResponseSignUpDto> = MutableLiveData()
    val signUpResult: LiveData<ResponseSignUpDto> = _signUpResult
    private val _signUpMessage: MutableLiveData<String> = MutableLiveData()
    val signUpMessage: LiveData<String> = _signUpMessage

    private val authService = MemberServicePool.authService
    private val authRemoteDataSource = AuthRemoteDataSource(authService)

    val idRegex = Regex(ID_PATTERN)
    val pwRegex =
        Regex(PW_PATTERN)

    val signUpEnabled = MediatorLiveData<Boolean>().apply {
        addSource(id) { value = checkSignUpEnabled() }
        addSource(pw) { value = checkSignUpEnabled() }
        addSource(name) { value = checkSignUpEnabled() }
        addSource(specialty) { value = checkSignUpEnabled() }
    }

    fun isIdEnabled(): Boolean {
        return !id.value.isNullOrBlank()
                && id.value?.matches(idRegex) ?: false
    }

    fun isPwEnabled(): Boolean {
        return !pw.value.isNullOrBlank()
                && pw.value?.matches(pwRegex) ?: false
    }

    fun checkSignUpEnabled(): Boolean {
        return isIdEnabled()
                && isPwEnabled()
                && !name.value.isNullOrBlank()
                && !specialty.value.isNullOrBlank()
    }

    fun signUp(id: String, pw: String, name: String, specialty: String) {
        viewModelScope.launch {
            try {
                val response = authRemoteDataSource.signUp(id, pw, name, specialty)
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

    companion object {
        const val ID_PATTERN = "^(?=.*[a-zA-Z])(?=.*[0-9])[a-zA-Z0-9]{6,10}"
        const val PW_PATTERN =
            "^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[!@#%^&*()])[a-zA-Z0-9!@#%^&*()]{6,12}"
    }
}