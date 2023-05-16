package org.android.go.sopt.member

import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.MotionEvent
import androidx.appcompat.app.AppCompatActivity
import org.android.go.sopt.R
import org.android.go.sopt.data.remote.api.MemberServicePool
import org.android.go.sopt.data.remote.model.RequestSignInDto
import org.android.go.sopt.data.remote.model.ResponseSignInDto
import org.android.go.sopt.databinding.ActivitySignInBinding
import org.android.go.sopt.home.HomeActivity
import org.android.go.sopt.util.extension.hideKeyboard
import org.android.go.sopt.util.extension.showToast
import retrofit2.Call
import retrofit2.Response

class SignInActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignInBinding
    private val loginService = MemberServicePool.loginService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
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
        binding.btnSignInSignUp.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }
    }

    private fun clickLogin() {
        binding.btnSignInLogin.setOnClickListener {
            completeLogin()
        }
    }

    private fun completeLogin() {
        loginService.signIn(
            with(binding) {
                RequestSignInDto(
                    etSignInId.text.toString(),
                    etSignInPw.text.toString()
                )
            }
        ).enqueue(object : retrofit2.Callback<ResponseSignInDto> {
            override fun onResponse(
                call: Call<ResponseSignInDto>,
                response: Response<ResponseSignInDto>
            ) {
                if (response.isSuccessful) {
                    response.body()?.message?.let { showToast(it) }
                        ?: getString(R.string.login_success).also { showToast(it) }

                    moveHomeActivity()
                    saveAutoLoginInfo(
                        response.body()?.data?.name ?: "",
                        response.body()?.data?.skill ?: ""
                    )

                    if (!isFinishing) finish()
                } else {
                    response.body()?.message?.let { showToast(it) }
                        ?: getString(R.string.login_fail).also { showToast(it) }
                }
            }

            override fun onFailure(call: Call<ResponseSignInDto>, t: Throwable) {
                t.message?.let { showToast(it) }
                    ?: getString(R.string.server_communication_on_failure).also { showToast(it) }
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
        val autoLoginName = autoLoginInfo.getString("name", "").toString()
        val autoLoginSpecialty = autoLoginInfo.getString("specialty", "").toString()

        if (autoLoginName != "" && autoLoginSpecialty != "") {
            moveHomeActivity()
            showToast(getString(R.string.auto_login_success))
        }
    }
}