package org.android.go.sopt.member

import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import org.android.go.sopt.databinding.ActivitySignUpBinding
import org.android.go.sopt.util.extension.hideKeyboard
import org.android.go.sopt.util.extension.showToast

class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding
    private val viewModel: SignUpViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        clickSignUp()
        setBtnSignUpCompleteEnabled()
        observeBtnSignUpComplete()
        signUpMessageObserver()
        signUpResultObserver()
        idObserver()
        pwObserver()
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        hideKeyboard(ev)
        return super.dispatchTouchEvent(ev)
    }

    private fun clickSignUp() {
        binding.btnSignUpComplete.setOnClickListener {
            if (viewModel.checkSignUpEnabled()) {
                completeSignUp()
            } else {

            }
        }
    }

    private fun completeSignUp() {
        with(binding) {
            viewModel!!.signUp(
                etSignUpId.text.toString(),
                etSignUpPw.text.toString(),
                etSignUpName.text.toString(),
                etSignUpSpecialty.text.toString()
            )
        }
    }

    private fun setBtnSignUpCompleteEnabled() {
        with(binding) {
            btnSignUpComplete.isEnabled = viewModel?.checkSignUpEnabled() ?: false
        }
    }

    private fun observeBtnSignUpComplete() {
        viewModel.signUpEnabled.observe(this) {
            setBtnSignUpCompleteEnabled()
        }
    }

    private fun signUpMessageObserver() {
        viewModel.signUpMessage.observe(this) { message ->
            showToast(message)
        }
    }

    private fun signUpResultObserver() {
        viewModel.signUpResult.observe(this) { signUpResult ->
            finish()
        }
    }

    private fun idObserver() {
        viewModel.id.observe(this) {
            with(binding) {
                if (viewModel!!.isIdEnabled()) tvSignUpIdWaring.visibility =
                    View.INVISIBLE else tvSignUpIdWaring.visibility = View.VISIBLE
            }
        }
    }

    private fun pwObserver() {
        viewModel.pw.observe(this) {
            with(binding) {
                if (viewModel!!.isPwEnabled()) tvSignUpPwWaring.visibility =
                    View.INVISIBLE else tvSignUpPwWaring.visibility = View.VISIBLE
            }
        }
    }
}