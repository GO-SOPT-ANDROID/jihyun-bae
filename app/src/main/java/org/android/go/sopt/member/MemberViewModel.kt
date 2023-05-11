package org.android.go.sopt.member

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MemberViewModel : ViewModel() {
    val id: MutableLiveData<String> = MutableLiveData()
    val pw: MutableLiveData<String> = MutableLiveData()
    val name: MutableLiveData<String> = MutableLiveData()
    val specialty: MutableLiveData<String> = MutableLiveData()

    fun isSignUpEnabled(): Boolean {
        return !id.value.isNullOrBlank()
                && !pw.value.isNullOrBlank()
                && id.value!!.length in 6..10
                && pw.value!!.length in 8..12
                && !name.value.isNullOrBlank()
                && !specialty.value.isNullOrBlank()
    }
}