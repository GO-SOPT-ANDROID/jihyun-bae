package org.android.go.sopt.util.extension

import android.view.View
import com.google.android.material.snackbar.Snackbar

fun View.showSnackBar(msg: String, isLengthShort: Boolean = true) {
    val duration = if (isLengthShort) Snackbar.LENGTH_SHORT else Snackbar.LENGTH_LONG
    Snackbar.make(this, msg, duration).show()
}