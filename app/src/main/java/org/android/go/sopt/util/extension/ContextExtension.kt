package org.android.go.sopt.util.extension

import android.content.Context
import android.widget.Toast

fun Context.showToast(msg: String, isLengthShort: Boolean = true) {
    val duration = if (isLengthShort) Toast.LENGTH_SHORT else Toast.LENGTH_LONG
    Toast.makeText(this, msg, duration).show()
}