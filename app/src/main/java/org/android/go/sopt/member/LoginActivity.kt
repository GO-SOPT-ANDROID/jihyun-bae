package org.android.go.sopt.member

import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.MotionEvent
import androidx.appcompat.app.AppCompatActivity
import org.android.go.sopt.R
import org.android.go.sopt.data.remote.api.ServicePool
import org.android.go.sopt.data.remote.model.RequestLoginDto
import org.android.go.sopt.data.remote.model.ResponseLoginDto
import org.android.go.sopt.databinding.ActivityLoginBinding
import org.android.go.sopt.home.HomeActivity
import org.android.go.sopt.util.extension.hideKeyboard
import org.android.go.sopt.util.extension.showToast
import retrofit2.Call
import retrofit2.Response

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val loginService = ServicePool.loginService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        clickSignUp()
        clickLogin()
        setAutoLogin()
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        hideKeyboard(ev)
        return super.dispatchTouchEvent(ev)
    }

    private fun clickSignUp() {
        binding.btnLoginSignUp.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }
    }

    private fun clickLogin() {
        binding.btnLoginLogin.setOnClickListener {
            completeLogin()
        }
    }

    private fun completeLogin() {
        loginService.login(
            with(binding) {
                RequestLoginDto(
                    etLoginId.text.toString(),
                    etLoginPw.text.toString()
                )
            }
        ).enqueue(object : retrofit2.Callback<ResponseLoginDto> {
            override fun onResponse(
                call: Call<ResponseLoginDto>,
                response: Response<ResponseLoginDto>
            ) {
                if (response.isSuccessful) {
                    response.body()?.message?.let { showToast(it) } ?: "로그인에 성공했습니다."

                    moveHomeActivity()
                    saveAutoLoginInfo(response.body()!!.data.name, response.body()!!.data.skill)

                    if (!isFinishing) finish()
                } else {
                    // 실패한 응답
                    response.body()?.message?.let { showToast(it) } ?: "서버통신 실패(40X)"
                }
            }

            override fun onFailure(call: Call<ResponseLoginDto>, t: Throwable) {
                t.message?.let { showToast(it) } ?: "서버통신 실패(응답값 X)"
            }

        })
    }

    private fun moveHomeActivity() {
        val intent = Intent(this, HomeActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        startActivity(intent)
    }

    private fun saveAutoLoginInfo(savedName: String, savedSpecialty: String) {
        val sharedPreference = getSharedPreferences("autoLogin", Activity.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sharedPreference.edit()
        editor.putString("name", savedName)
        editor.putString("specialty", savedSpecialty)
        editor.commit()
    }

    private fun setAutoLogin() {
        val autoLoginInfo = getSharedPreferences("autoLogin", MODE_PRIVATE)
        val autoLoginId = autoLoginInfo.getString("id", "").toString()

        if (autoLoginId != "") {
            moveHomeActivity()
            showToast(getString(R.string.auto_login_success))
        }
    }
}