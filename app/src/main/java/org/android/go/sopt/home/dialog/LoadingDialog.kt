package org.android.go.sopt.home.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import org.android.go.sopt.databinding.DialogLoadingBinding

class LoadingDialog(
    context: Context
) : Dialog(context) {
    private lateinit var binding: DialogLoadingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DialogLoadingBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}