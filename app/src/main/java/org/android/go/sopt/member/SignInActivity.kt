package org.android.go.sopt.member

import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.MotionEvent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import org.android.go.sopt.R
import org.android.go.sopt.databinding.ActivitySignInBinding
import org.android.go.sopt.home.HomeActivity
import org.android.go.sopt.util.extension.hideKeyboard
import org.android.go.sopt.util.extension.showToast

class SignInActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignInBinding
    private val viewModel by viewModels<SignInViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        clickSignUp()
        signInBtnClickListener()
        signInResultObserver()
        signInMessageObserver()
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

    private fun signInBtnClickListener() {
        binding.btnSignInLogin.setOnClickListener {
            binding.viewModel?.let { viewModel ->
                with(binding) {
                    viewModel.signIn(
                        etSignInId.text.toString(),
                        etSignInPw.text.toString()
                    )
                }
            }
        }
    }

    private fun signInResultObserver() {
        viewModel.signInResult.observe(this) { signInResult ->
            moveHomeActivity()
            saveAutoLoginInfo(
                signInResult.data.name ?: "",
                signInResult.data.skill ?: ""
            )
        }
    }

    private fun signInMessageObserver() {
        viewModel.signInMessage.observe(this) { message ->
            showToast(message)
        }
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