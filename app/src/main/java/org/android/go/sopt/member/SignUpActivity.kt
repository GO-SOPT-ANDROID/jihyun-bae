package org.android.go.sopt.member

import android.content.Intent
import android.os.Bundle
import android.view.MotionEvent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import org.android.go.sopt.R
import org.android.go.sopt.databinding.ActivitySignUpBinding
import org.android.go.sopt.util.extension.hideKeyboard
import org.android.go.sopt.util.extension.showSnackBar

class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding
    private val viewModel: MemberViewModel by viewModels()

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
            viewModel.isSignUpEnabled()
            if (viewModel.isSignUpEnabled.value == true) {
                val intent = Intent(this, LoginActivity::class.java).apply {
                    putExtra("id", binding.etSignUpId.text.toString())
                    putExtra("pw", binding.etSignUpPw.text.toString())
                    putExtra("name", binding.etSignUpName.text.toString())
                    putExtra("specialty", binding.etSignUpSpecialty.text.toString())
                }
                setResult(RESULT_OK, intent)
                finish()
            } else {
                binding.root.showSnackBar(getString(R.string.sign_up_fail))
            }
        }
    }
}