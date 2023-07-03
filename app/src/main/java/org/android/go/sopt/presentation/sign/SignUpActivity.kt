package org.android.go.sopt.presentation.sign

import android.os.Bundle
import android.view.MotionEvent
import androidx.activity.viewModels
import org.android.go.sopt.R
import org.android.go.sopt.databinding.ActivitySignUpBinding
import org.android.go.sopt.presentation.common.ViewModelFactory
import org.android.go.sopt.util.UiState
import org.android.go.sopt.util.binding.BindingActivity
import org.android.go.sopt.util.extension.hideKeyboard
import org.android.go.sopt.util.extension.showToast
import timber.log.Timber

class SignUpActivity : BindingActivity<ActivitySignUpBinding>(R.layout.activity_sign_up) {
    private val viewModel: SignViewModel by viewModels { ViewModelFactory(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        addObservers()
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        hideKeyboard(ev)
        return super.dispatchTouchEvent(ev)
    }

    private fun addObservers() {
        viewModel.signUpMessage.observe(this) { message ->
            showToast(message)
        }

        viewModel.signUpState.observe(this) { signUpState ->
            when (signUpState) {
                is UiState.Success -> {
                    finish()
                }

                else -> {
                    Timber.e(getString(R.string.ui_state_false))
                }
            }
        }
    }
}