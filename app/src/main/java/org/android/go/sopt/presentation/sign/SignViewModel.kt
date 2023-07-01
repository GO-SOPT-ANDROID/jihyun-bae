package org.android.go.sopt.presentation.sign

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.android.go.sopt.data.model.response.ResponseSignInDto
import org.android.go.sopt.data.model.response.ResponseSignUpDto
import org.android.go.sopt.data.repository.AuthRepositoryImpl

class SignViewModel(private val authRepository: AuthRepositoryImpl) : ViewModel() {
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

    private val _signUpResult: MutableLiveData<ResponseSignUpDto.SignUpData> = MutableLiveData()
    val signUpResult: LiveData<ResponseSignUpDto.SignUpData> = _signUpResult
    private val _signUpMessage: MutableLiveData<String> = MutableLiveData()
    val signUpMessage: LiveData<String> = _signUpMessage

    private val _signInResult: MutableLiveData<ResponseSignInDto.SignInData> = MutableLiveData()
    val signInResult: LiveData<ResponseSignInDto.SignInData> = _signInResult
    private val _signInMessage: MutableLiveData<String> = MutableLiveData()
    val signInMessage: LiveData<String> = _signInMessage

    fun checkSignUpEnabled(): Boolean {
        return isIdValid.value == true
                && isPwValid.value == true
                && !name.value.isNullOrBlank()
                && !specialty.value.isNullOrBlank()
    }

    fun signUp() {
        viewModelScope.launch {
            authRepository.signUp(
                id.value.toString(),
                pw.value.toString(),
                name.value.toString(),
                specialty.value.toString()
            )
                .onSuccess { signUpData ->
                    _signUpMessage.value = "회원가입에 성공했습니다."
                    _signUpResult.value = signUpData
                }
                .onFailure {
                    _signUpMessage.value = "회원가입 중 오류가 발생했습니다."
                }
        }
    }

    fun signIn() {
        viewModelScope.launch {
            authRepository.signIn(id.value.toString(), pw.value.toString())
                .onSuccess { signInData ->
                    _signInMessage.value = "로그인에 성공했습니다."
                    _signInResult.value = signInData
                }
                .onFailure {
                    _signInMessage.value = "로그인 중 오류가 발생했습니다."
                }
        }
    }

    companion object {
        const val ID_PATTERN = "^(?=.*[a-zA-Z])(?=.*[0-9])[a-zA-Z0-9]{6,10}"
        const val PW_PATTERN =
            "^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[!@#%^&*()])[a-zA-Z0-9!@#%^&*()]{6,12}"
    }
}