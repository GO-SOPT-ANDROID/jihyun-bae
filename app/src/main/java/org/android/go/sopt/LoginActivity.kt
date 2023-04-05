package org.android.go.sopt

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.ActivityResult
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val startForResult = registerForActivityResult(
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

        binding.btnLoginSignUp.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startForResult.launch(intent)
        }

        binding.btnLoginLogin.setOnClickListener {
            val id = binding.etLoginId.text.toString()
            val pw = binding.etLoginPw.text.toString()

            if (id == savedId && pw == savedPw) {
                Toast.makeText(this, "로그인에 성공했습니다.", Toast.LENGTH_SHORT).show()

                val intent = Intent(this, SelfIntroductionActivity::class.java).apply {
                    putExtra("name", savedName)
                    putExtra("specialty", savedSpecialty)
                }
                startActivity(intent)
            }
        }
    }
}