package org.android.go.sopt.util

import android.content.Context
import android.content.res.Resources
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.Toast
import org.android.go.sopt.databinding.CustomToastBinding

object CustomToast {
    fun makeToast(context: Context, message: String): Toast? {
        val inflater = LayoutInflater.from(context)
        val binding = CustomToastBinding.inflate(inflater, null, false)

        binding.tvCustomToastMessage.text = message

        return Toast(context).apply {
            setGravity(Gravity.BOTTOM, 0, 146.toPx())
            duration = Toast.LENGTH_SHORT
            view = binding.root
        }
    }

    private fun Int.toPx(): Int = (this * Resources.getSystem().displayMetrics.density).toInt()
}