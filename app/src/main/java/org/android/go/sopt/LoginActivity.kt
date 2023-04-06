package org.android.go.sopt

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import org.android.go.sopt.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var savedId: String
    private lateinit var savedPw: String
    private lateinit var savedName: String
    private lateinit var savedSpecialty: String
    private lateinit var startForResult: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setActivityResult()
        clickSignUp()
        clickLogin()
        setAutoLogin()
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        hideKeyboard(ev)
        return super.dispatchTouchEvent(ev)
    }

    private fun setActivityResult() {
        startForResult = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result: ActivityResult ->
            if (result.resultCode == RESULT_OK) {
                savedId = result.data?.getStringExtra("id").toString()
                savedPw = result.data?.getStringExtra("pw").toString()
                savedName = result.data?.getStringExtra("name").toString()
                savedSpecialty = result.data?.getStringExtra("specialty").toString()

                Snackbar.make(
                    binding.root,
                    "회원가입이 완료되었습니다.",
                    Snackbar.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun clickSignUp() {
        binding.btnLoginSignUp.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startForResult.launch(intent)
        }
    }

    private fun clickLogin() {
        binding.btnLoginLogin.setOnClickListener {
            val id = binding.etLoginId.text.toString()
            val pw = binding.etLoginPw.text.toString()

            if (id == savedId && pw == savedPw) {
                Toast.makeText(this, "로그인에 성공했습니다.", Toast.LENGTH_SHORT).show()

                saveAutoLoginInfo()

                val intent = Intent(this, SelfIntroductionActivity::class.java).apply {
                    putExtra("name", savedName)
                    putExtra("specialty", savedSpecialty)
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                }
                startActivity(intent)
            }
        }
    }

    private fun hideKeyboard(ev: MotionEvent?) {
        val imm: InputMethodManager =
            getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
        currentFocus?.clearFocus()
    }

    private fun saveAutoLoginInfo() {
        val sharedPreference = getSharedPreferences("autoLogin", Activity.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sharedPreference.edit()
        editor.putString("id", savedId)
        editor.putString("pw", savedPw)
        editor.putString("name", savedName)
        editor.putString("specialty", savedSpecialty)
        editor.commit()
    }

    private fun setAutoLogin() {
        val autoLoginInfo = getSharedPreferences("autoLogin", MODE_PRIVATE)
        val autoLoginId = autoLoginInfo.getString("id", "").toString()
        val autoLoginPw = autoLoginInfo.getString("pw", "").toString()
        val autoLoginName = autoLoginInfo.getString("name", "").toString()
        val autoLoginSpecialty = autoLoginInfo.getString("specialty", "").toString()

        if (autoLoginId != "" && autoLoginPw != "") {
            val intent = Intent(this, SelfIntroductionActivity::class.java).apply {
                putExtra("name", autoLoginName)
                putExtra("specialty", autoLoginSpecialty)
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }
            startActivity(intent)
            Toast.makeText(this, "자동 로그인되었습니다.", Toast.LENGTH_SHORT).show()
        }
    }
}