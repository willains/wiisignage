package com.example.wiisignageevento

import android.net.Uri
import android.util.Log
import android.widget.VideoView
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable

import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView


@Composable
fun VideoPlayer(videoUri: Uri, onVideoEnded: () -> Unit) {
    val context = LocalContext.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black) // Fundo preto ajuda com vídeos com proporções diferentes
    ) {
        AndroidView(

            factory = {
                VideoView(context).apply {
                    setVideoURI(videoUri)

                    setOnPreparedListener { mp ->
                        mp.setVolume(0f, 0f) // deixa o vídeo mudo
                        mp.isLooping = false
                        start()
                    }

                    setOnCompletionListener {
                        Log.d("VIDEO", "Terminou o vídeo, indo para próxima mídia")
                        onVideoEnded()
                    }

                    setOnErrorListener { _, what, extra ->
                        Log.e("VIDEO", "Erro ao reproduzir vídeo: what=$what, extra=$extra")
                        onVideoEnded() // vai para a próxima mídia
                        true // true indica que o erro foi tratado
                    }
                }

            },
            update = { videoView ->
                videoView.setVideoURI(videoUri)
                videoView.requestFocus()
            },
            modifier = Modifier.fillMaxSize()
        )
    }
}