package org.android.go.sopt.member

import android.os.Bundle
import android.view.MotionEvent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import org.android.go.sopt.R
import org.android.go.sopt.data.remote.api.ServicePool
import org.android.go.sopt.data.remote.model.RequestSignUpDto
import org.android.go.sopt.data.remote.model.ResponseSignUpDto
import org.android.go.sopt.databinding.ActivitySignUpBinding
import org.android.go.sopt.util.extension.hideKeyboard
import org.android.go.sopt.util.extension.showSnackBar
import org.android.go.sopt.util.extension.showToast
import retrofit2.Call
import retrofit2.Response

class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding
    private val viewModel: MemberViewModel by viewModels()
    private val signUpService = ServicePool.signUpService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        clickSignUp()
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        hideKeyboard(ev)
        return super.dispatchTouchEvent(ev)
    }

    private fun clickSignUp() {
        binding.btnSignUpComplete.setOnClickListener {
            if (viewModel.isSignUpEnabled()) {
                completeSignUp()
            } else {
                binding.root.showSnackBar(getString(R.string.sign_up_fail))
            }
        }
    }

    private fun completeSignUp() {
        signUpService.login(
            with(binding) {
                RequestSignUpDto(
                    etSignUpId.text.toString(),
                    etSignUpPw.text.toString(),
                    etSignUpName.text.toString(),
                    etSignUpSpecialty.text.toString()
                )
            }
        ).enqueue(object : retrofit2.Callback<ResponseSignUpDto> {
            override fun onResponse(
                call: Call<ResponseSignUpDto>,
                response: Response<ResponseSignUpDto>
            ) {
                if (response.isSuccessful) {
                    response.body()?.message?.let { showToast(it) } ?: "회원가입에 성공했습니다."

                    if (!isFinishing) finish()
                } else {
                    // 실패한 응답
                    response.body()?.message?.let { showToast(it) } ?: "서버통신 실패(40X)"
                }
            }

            override fun onFailure(call: Call<ResponseSignUpDto>, t: Throwable) {
                t.message?.let { showToast(it) } ?: "서버통신 실패(응답값 X)"
            }

        })
    }
}