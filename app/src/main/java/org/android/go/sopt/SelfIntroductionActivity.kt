package org.android.go.sopt

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import org.android.go.sopt.databinding.ActivitySelfIntroductionBinding

class SelfIntroductionActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySelfIntroductionBinding
    private lateinit var name: String
    private lateinit var specialty: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySelfIntroductionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSelfIntroduction()
    }

    private fun setSelfIntroduction() {
        if (intent.hasExtra("name")) {
            name = intent.getStringExtra("name").toString()
        }

        if (intent.hasExtra("specialty")) {
            specialty = intent.getStringExtra("specialty").toString()
        }

        binding.tvSelfIntroductionName.text = "이름 : $name"
        binding.tvSelfIntroductionSpecialty.text = "특기 : $specialty"
    }
}