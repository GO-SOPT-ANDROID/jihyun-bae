package org.android.go.sopt.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.android.go.sopt.data.remote.api.MemberServicePool
import org.android.go.sopt.data.remote.model.RequestSignUpDto
import org.android.go.sopt.data.remote.model.ResponseSignUpDto
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignUpViewModel : ViewModel() {
    val id: MutableLiveData<String> = MutableLiveData()
    val pw: MutableLiveData<String> = MutableLiveData()
    val name: MutableLiveData<String> = MutableLiveData()
    val specialty: MutableLiveData<String> = MutableLiveData()

    private val _signUpResult: MutableLiveData<ResponseSignUpDto> = MutableLiveData()
    val signUpResult: LiveData<ResponseSignUpDto> = _signUpResult
    private val _signUpMessage: MutableLiveData<String> = MutableLiveData()
    val signUpMessage: LiveData<String> = _signUpMessage
    private val signUpService = MemberServicePool.signUpService

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
        signUpService.signUp(
            RequestSignUpDto(
                id,
                pw,
                name,
                specialty
            )
        ).enqueue(object : Callback<ResponseSignUpDto> {
            override fun onResponse(
                call: Call<ResponseSignUpDto>,
                response: Response<ResponseSignUpDto>
            ) {
                if (response.isSuccessful) {
                    _signUpMessage.value = response.body()?.message ?: "회원가입 완료"
                    _signUpResult.value = response.body()
                } else {
                    _signUpMessage.value = response.body()?.message ?: "회원가입이 실패하였습니다."
                }
            }

            override fun onFailure(call: Call<ResponseSignUpDto>, t: Throwable) {
                _signUpMessage.value = t.message ?: "서버와 통신이 원활하지 않습니다."
            }

        })
    }

    companion object {
        const val ID_PATTERN = "^(?=.*[a-zA-Z])(?=.*[0-9])[a-zA-Z0-9]{6,10}"
        const val PW_PATTERN =
            "^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[!@#%^&*()])[a-zA-Z0-9!@#%^&*()]{6,12}"
    }
}