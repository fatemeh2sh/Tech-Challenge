package com.sharif.tech_challenge.utils.extensions

import android.view.View
import android.widget.ImageView
import com.squareup.picasso.Picasso

fun View.show() {
    visibility = View.VISIBLE
}

fun View.hide() {
    visibility = View.INVISIBLE
}

fun View.gone() {
    visibility = View.GONE
}

fun View.active() {
    isEnabled = true
}

fun View.deActive() {
    isEnabled = false
}

fun ImageView.loadImage(path: String?) {
    path?.let {
        if (it.isNotEmpty())
            Picasso.get()
                .load(path)
                .into(this)

    }
}
