package org.android.go.sopt.presentation.mypage

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.WindowManager
import org.android.go.sopt.presentation.sign.SignInActivity
import org.android.go.sopt.databinding.DialogLogoutBinding

class LogoutDialog(
    context: Context
) : Dialog(context) {
    private lateinit var binding: DialogLogoutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DialogLogoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initLayout()
        addListeners()
    }

    private fun initLayout() {
        window!!.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        setCancelable(false)
        setCanceledOnTouchOutside(true)
    }

    private fun addListeners() {
        binding.btnDialogLogoutNo.setOnClickListener {
            dismiss()
        }

        binding.btnDialogLogoutYes.setOnClickListener {
            clearAutoLogin()
            dismiss()
            val intent = Intent(context, SignInActivity::class.java)
            context.startActivity(intent)
        }
    }

    private fun clearAutoLogin() {
        val sharedPreference = context.getSharedPreferences("autoLogin", Activity.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sharedPreference.edit()
        editor.clear()
        editor.commit()
    }
}