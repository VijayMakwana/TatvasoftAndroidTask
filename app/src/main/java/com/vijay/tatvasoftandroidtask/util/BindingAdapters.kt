package com.vijay.tatvasoftandroidtask.util

import android.widget.ImageView
import androidx.databinding.BindingAdapter

object BindingAdapters {

    @JvmStatic
    @BindingAdapter("imageUrl", "isCircle")
    fun setImageWithUrl(imageView: ImageView, imageUrl: String, isCircle: Boolean) {
        if (isCircle) {
            GlideApp.with(imageView.context)
                .load(imageUrl)
                .circleCrop()
                .into(imageView)
        } else {
            GlideApp.with(imageView.context)
                .load(imageUrl)
                .into(imageView)
        }
    }
}