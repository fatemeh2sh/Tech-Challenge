package com.sharif.tech_challenge.utils

import android.R
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.material.color.MaterialColors
import com.sharif.tech_challenge.data.model.State

fun getStringResources(context: Context, @StringRes resId: Int): String =
    context.resources.getString(resId)

fun getColorStateList(context: Context,@ColorRes color:Int) =
    context.resources.getColorStateList(color, context.theme)

fun getColor(context: Context) =
    context.resources.getColor(R.color.white, context.theme)

fun getColorCompat(context: Context) =
    ContextCompat.getColor(context, R.color.white)

fun getDrawableCompat(context: Context,drawable:Int) =
    ContextCompat.getDrawable(context, drawable)

fun getMaterialColor(view: View, color: Int): Int =
    MaterialColors.getColor(view, color)

fun closeKeyboard(activity: AppCompatActivity) {
    val view: View? = activity.currentFocus
    if (view != null) {
        val manager: InputMethodManager? = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
        manager?.hideSoftInputFromWindow(view.windowToken, 0)
    }
}

fun getArrayState(): ArrayList<State> {

    var lstState = arrayListOf<State>()
    lstState.add(State("",2))
    lstState.add(State("",3))
    lstState.add(State("",4))

    return lstState
}


