package com.sharif.tech_challenge.service.sound

import android.content.Context
import android.content.res.AssetFileDescriptor
import android.media.AudioManager
import android.media.MediaPlayer
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Sound @Inject constructor(@ApplicationContext var context:Context) {

    private var mediaPlayer : MediaPlayer = MediaPlayer()

    fun start(url:String) {
        try {
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC)
            mediaPlayer.setDataSource(url)
            mediaPlayer.prepare()
            mediaPlayer.start()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    fun stop() {
        mediaPlayer?.stop()
    }
}