package org.android.go.sopt.member

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MemberViewModel : ViewModel() {
    val id: MutableLiveData<String> = MutableLiveData()
    val pw: MutableLiveData<String> = MutableLiveData()

    private val _isSignUpEnabled = MutableLiveData(false)
    val isSignUpEnabled: LiveData<Boolean>
        get() = _isSignUpEnabled

    fun isSignUpEnabled() {
        if (!id.value.isNullOrBlank() && !pw.value.isNullOrBlank() && id.value!!.length in 6..10 && pw.value!!.length in 8..12) {
            _isSignUpEnabled.value = true
        }
    }
}