package com.varun.pixabayapp.presentation.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import coil.load


object BindingAdapters {

    @JvmStatic
    @BindingAdapter("url")
    fun bindUrl(imageView: ImageView, url: String?) {
        if (url != null) {
            imageView.load(url)
        }
    }
}