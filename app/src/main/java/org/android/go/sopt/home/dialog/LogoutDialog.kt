package org.android.go.sopt.home.dialog

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.WindowManager
import org.android.go.sopt.databinding.DialogLogoutBinding
import org.android.go.sopt.member.LoginActivity

class LogoutDialog(
    context: Context
) : Dialog(context) {
    private lateinit var binding: DialogLogoutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DialogLogoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViews()
        noBtnClickListener()
        yesBtnClickListener()
    }

    private fun initViews() {
        window!!.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        setCancelable(false)
        setCanceledOnTouchOutside(true)
    }

    private fun noBtnClickListener() {
        binding.btnDialogLogoutNo.setOnClickListener {
            dismiss()
        }
    }

    private fun yesBtnClickListener() {
        binding.btnDialogLogoutYes.setOnClickListener {
            clearAutoLogin()
            dismiss()
            moveLoginActivity()
        }
    }

    private fun clearAutoLogin() {
        val sharedPreference = context.getSharedPreferences("autoLogin", Activity.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sharedPreference.edit()
        editor.clear()
        editor.commit()
    }

    private fun moveLoginActivity() {
        val intent = Intent(context, LoginActivity::class.java)
        context.startActivity(intent)
    }
}