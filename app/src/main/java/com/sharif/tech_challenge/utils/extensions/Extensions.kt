package com.sharif.tech_challenge.utils.extensions

import android.view.View
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi

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

inline fun <reified T> String.toConvertStringJsonToModel(type: Class<T>): T {
    val moshi = Moshi.Builder().build()
    val adapter: JsonAdapter<T> = moshi.adapter(type)
    return adapter.fromJson(this)!!
}

inline fun <reified T> T.toConvertModelToJson(type: Class<T>): String {
    val moshi = Moshi.Builder().build()
    val adapter: JsonAdapter<T> = moshi.adapter(type)
    return adapter.toJson(this)
}
