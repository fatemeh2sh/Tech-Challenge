package com.sharif.tech_challenge.data.model

import android.graphics.drawable.Drawable
import com.sharif.tech_challenge.utils.ThemeType

data class State(
    val name:ThemeType,
    val color:String ?= "",
    val icon:Drawable ?= null
)