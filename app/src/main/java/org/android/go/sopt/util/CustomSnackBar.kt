package org.android.go.sopt.util

import android.content.res.Resources
import android.view.LayoutInflater
import android.view.View
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.snackbar.Snackbar.SnackbarLayout
import org.android.go.sopt.databinding.CustomToastBinding

object CustomSnackBar {
    fun makeSnackBar(view: View, message: String) {
        val inflater = LayoutInflater.from(view.context)
        val binding = CustomToastBinding.inflate(inflater, null, false)

        binding.tvCustomToastMessage.text = message

        val snackBar = Snackbar.make(view, message, Snackbar.LENGTH_SHORT)
        val snackBarLayout = snackBar.view as SnackbarLayout

        with(snackBarLayout) {
            removeAllViews()
            setPadding(0, 0, 0, 138.toPx())
            setBackgroundColor(ContextCompat.getColor(context, android.R.color.transparent))
            addView(binding.root)
        }

        snackBar.show()
    }

    private fun Int.toPx(): Int = (this * Resources.getSystem().displayMetrics.density).toInt()
}