package org.android.go.sopt.member

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
    val signUpResult: MutableLiveData<ResponseSignUpDto> = MutableLiveData()
    val signUpMessage: MutableLiveData<String> = MutableLiveData()
    private val signUpService = MemberServicePool.signUpService

    val signUpEnabled = MediatorLiveData<Boolean>().apply {
        addSource(id) { value = checkSignUpEnabled() }
        addSource(pw) { value = checkSignUpEnabled() }
        addSource(name) { value = checkSignUpEnabled() }
        addSource(specialty) { value = checkSignUpEnabled() }
    }

    fun checkSignUpEnabled(): Boolean {
        return !id.value.isNullOrBlank()
                && !pw.value.isNullOrBlank()
                && id.value!!.length in 6..10
                && pw.value!!.length in 8..12
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
                    signUpMessage.value = response.body()?.message ?: "회원가입 완료"
                    signUpResult.value = response.body()
                } else {
                    signUpMessage.value = response.body()?.message ?: "회원가입이 실패하였습니다."
                }
            }

            override fun onFailure(call: Call<ResponseSignUpDto>, t: Throwable) {
                signUpMessage.value = t.message ?: "서버와 통신이 원활하지 않습니다."
            }

        })
    }
}