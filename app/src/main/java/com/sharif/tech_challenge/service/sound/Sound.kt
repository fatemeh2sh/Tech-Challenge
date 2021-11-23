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
class Sound @Inject constructor(@ApplicationContext var context:Context): MediaPlayer.OnPreparedListener {

    private var mediaPlayer : MediaPlayer ?= MediaPlayer()

    fun start(url:String) {
        try {
            mediaPlayer = MediaPlayer()
            mediaPlayer?.apply {
                setAudioStreamType(AudioManager.STREAM_MUSIC)
                setDataSource(url)
                setOnPreparedListener(this@Sound)
                prepareAsync()
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    fun stop() {
        mediaPlayer?.stop()
        mediaPlayer?.release()
        mediaPlayer = null

    }

    override fun onPrepared(mp: MediaPlayer?) {
        mediaPlayer?.start()
    }
}