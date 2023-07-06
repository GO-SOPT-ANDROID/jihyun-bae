package org.android.go.sopt.util.binding

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.core.text.HtmlCompat
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import coil.api.load

@BindingAdapter("imageUrl")
fun loadImager(view: ImageView, imageurl: String) {
    view.load(imageurl)
}

@BindingAdapter("visibility")
fun View.setVisibility(isVisible: Boolean?) {
    if (isVisible == null) return
    this.isVisible = isVisible
}

@BindingAdapter("imageRes")
fun setImageResource(imageView: ImageView, @DrawableRes imageRes: Int) {
    imageView.setImageResource(imageRes)
}

@BindingAdapter("htmlText")
fun setHtmlText(view: TextView, htmlText: String?) {
    if (htmlText != null) {
        val spannedText = HtmlCompat.fromHtml(htmlText, HtmlCompat.FROM_HTML_MODE_LEGACY)
        view.text = spannedText
    } else {
        view.text = ""
    }
}

@BindingAdapter("textWithoutQuotes")
fun TextView.setTextWithoutQuotes(text: String?) {
    text?.let {
        val textWithoutQuotes = it.substring(1, it.length - 1)
        this.text = textWithoutQuotes
    }
}
