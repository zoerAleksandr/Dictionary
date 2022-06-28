package com.example.dictionary

import android.content.Context
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.net.Uri
import android.widget.Toast
import java.io.IOException

fun playSong(context: Context, songUrl: String?) {
    var player: MediaPlayer? = null
    try {
        player = MediaPlayer.create(context, Uri.parse(songUrl))

        player.setAudioAttributes(
            AudioAttributes.Builder()
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .build()
        )
        player.start()

    } catch (e: IOException) {
        player?.release()
        Toast.makeText(context, e.stackTrace.toString(), Toast.LENGTH_SHORT).show()
    }
}
