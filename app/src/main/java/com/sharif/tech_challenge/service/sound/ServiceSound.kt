package com.sharif.tech_challenge.service.sound

import android.app.Service
import android.content.Intent
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.os.IBinder
import androidx.annotation.Nullable
import android.widget.Toast

import android.R
import android.media.AudioManager

private const val ACTION_PLAY: String = "com.example.action.PLAY"

class ServiceSound : Service() {

    var mediaPlayer: MediaPlayer? = MediaPlayer()
    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        mediaPlayer?.setAudioStreamType(AudioManager.STREAM_MUSIC)
        mediaPlayer?.setDataSource(url)
        mediaPlayer?.prepare()
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        mediaPlayer!!.start()
        Toast.makeText(applicationContext, "Playing Bohemian Rashpody in the Background", Toast.LENGTH_SHORT).show()
        return startId
    }

    override fun onStart(intent: Intent, startId: Int) {}

    override fun onDestroy() {
        mediaPlayer!!.stop()
        mediaPlayer!!.release()
    }
}