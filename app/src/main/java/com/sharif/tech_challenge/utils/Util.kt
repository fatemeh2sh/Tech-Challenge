package com.sharif.tech_challenge.utils

import android.R
import android.content.Context
import android.graphics.drawable.Drawable
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.material.color.MaterialColors
import com.sharif.tech_challenge.data.model.State
import java.io.IOException
import java.io.InputStream
import android.content.res.AssetFileDescriptor

import android.media.MediaPlayer
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.media.AudioManager


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

fun getAsset(context: Context,name:String): Drawable? {
    return try {
        var ims : InputStream = context.assets.open(name)
        Drawable.createFromStream(ims, null)
    }
    catch(ex: IOException) {
        null
    }
}

fun getArrayState(context: Context): ArrayList<State> {

    var lstState = arrayListOf<State>()

    lstState.add(State(ThemeType.sport,"#009DAE", getAsset(context,"sport.jpg")))
    lstState.add(State(ThemeType.art,"#FF87CA", getAsset(context,"sky.jpg")))
    lstState.add(State(ThemeType.fun1,"#FFCA03", getAsset(context,"")))

    return lstState
}

fun createRandom(): Int {
    return (0..2).random()
}

fun playMusic(context: Context){
    val mediaPlayer = MediaPlayer()
    val afd: AssetFileDescriptor
    try {
        afd = context.assets.openFd("sound.mp3")
        mediaPlayer.setDataSource(afd.fileDescriptor)
        mediaPlayer.prepare()
        mediaPlayer.start()
    } catch (e: IOException) {
        e.printStackTrace()
    }
}

fun vibrate(context: Context){
    val vibrator = context?.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
    if (Build.VERSION.SDK_INT >= 26) {
        vibrator.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE))
    } else {
        vibrator.vibrate(200)
    }
}

fun playMusic(url:String): MediaPlayer {
    val audioUrl = "https://raw.githubusercontent.com/AmirrezaRotamian/Tech-Challenge/master/assets/sound.mp3"
    val mediaPlayer = MediaPlayer()
    mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC)
    try {
        mediaPlayer.setDataSource(url)
        mediaPlayer.prepare()
        mediaPlayer.start()
    } catch (e: IOException) {
        e.printStackTrace()
    }

    return mediaPlayer
}

fun stopMedia(mediaPlayer: MediaPlayer){
    mediaPlayer.stop()
}


