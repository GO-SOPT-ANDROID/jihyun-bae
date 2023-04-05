package org.android.go.sopt

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import org.android.go.sopt.databinding.ActivitySignUpBinding

class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding
    private var idEnabled = false
    private var pwEnabled = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.etSignUpId.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {
                var id: String = binding.etSignUpId.text.toString()

                if (id.length in 6..10) {
                    idEnabled = true
                } else {
                    idEnabled = false
                }
            }
        })

        binding.etSignUpPw.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {
                var pw: String = binding.etSignUpPw.text.toString()

                if (pw.length in 8..12) {
                    pwEnabled = true
                } else {
                    pwEnabled = false
                }
            }
        })

        binding.btnSignUpComplete.setOnClickListener {
            if (idEnabled == true && pwEnabled == true) {
                val intent = Intent(this, LoginActivity::class.java).apply {
                    putExtra("id", binding.etSignUpId.text.toString())
                    putExtra("pw", binding.etSignUpPw.text.toString())
                    putExtra("name", binding.etSignUpName.text.toString())
                    putExtra("specialty", binding.etSignUpSpecialty.text.toString())
                }
                setResult(RESULT_OK, intent)
                finish()
            }
        }
    }
}