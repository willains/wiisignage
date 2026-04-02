package com.example.wiisignageevento.screens

import android.annotation.SuppressLint
import android.net.Uri
import android.os.Build

import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.annotation.RequiresApi

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.wiisignageevento.VideoPlayer
import com.example.wiisignageevento.constants.Constants
import com.example.wiisignageevento.network.model.Midia
import java.io.File
import kotlinx.coroutines.delay
import android.webkit.WebSettings



@SuppressLint("SetJavaScriptEnabled")
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MidiaPlayerScreen(midias: List<Midia>) {
    var currentIndex by remember { mutableIntStateOf(0) }
    val context = LocalContext.current
    val midia = midias.getOrNull(currentIndex)

    // Controle de troca de mídia automática, exceto para vídeo (controlado por callback)
    LaunchedEffect(key1 = currentIndex, key2 = midia?.type) {
        if (midia != null && midia.type != "video") {
            val duration = midia.duration.toLongOrNull() ?: 10000
            delay(duration)
            currentIndex = (currentIndex + 1) % midias.size
        }
    }

    Box(modifier =
        Modifier.fillMaxSize()
        .background(Color.Black)) {
        when (midia?.type) {

            "image" -> {
                val ext = midia.name.substringAfterLast('.', "")
                val file = File(
                    Constants.EVENT_FILES_DIR,
                    Constants.SUB_DIRECTORY+"/midia_${currentIndex}.$ext"
                )

                if (file.exists()) {
                    Image(
                        painter = rememberAsyncImagePainter(model = file),
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.FillBounds
                    )
                } else {
                    Text("Imagem não encontrada: ${file.name}", modifier = Modifier.padding(16.dp))
                }
            }
           /* "image" -> {
                val file =
                    File(
                        context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS),
                    "eventos/midia_${currentIndex}.${midia.name.substringAfterLast('.')}"
                )
                if (file.exists()) {
                    Image(
                        painter = rememberAsyncImagePainter(file),
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.FillBounds
                    )
                } else {
                    Text("Imagem não encontrada")
                }
            }*/





            "web" -> {
                AndroidView(
                    factory = {
                        WebView(it).apply {
                            WebView.setWebContentsDebuggingEnabled(true)
                            webViewClient = WebViewClient()

                            settings.apply {
                                javaScriptEnabled = true
                                domStorageEnabled = true
                                allowFileAccess = true
                                loadsImagesAutomatically = true
                                mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
                                builtInZoomControls = false
                                displayZoomControls = false
                                useWideViewPort = true
                                loadWithOverviewMode = true
                            }
                            loadUrl(midia.name)



                        }
                    },
                    modifier = Modifier.fillMaxSize()
                )
            }

            "video" -> {
                val file = File(
                    Constants.EVENT_FILES_DIR,
                    Constants.SUB_DIRECTORY+"/midia_${currentIndex}.${midia.name.substringAfterLast('.')}"
                )
                if (file.exists()) {
                    VideoPlayer(
                        videoUri = Uri.fromFile(file),
                        onVideoEnded = {
                            currentIndex = (currentIndex + 1) % midias.size
                        }
                    )
                } else {
                    Text("Vídeo não encontrado")
                }
            }

            "pdf" -> {
                Text("PDF ainda não implementado", modifier = Modifier.padding(16.dp))
            }

            else -> {
                Text("Tipo de mídia desconhecido: ${midia?.type}", modifier = Modifier.padding(16.dp))
            }
        }
    }
}


/*@Composable
fun MidiaPlayerScreen(midias: List<Midia>) {
    var currentIndex by remember { mutableStateOf(0) }
    val context = LocalContext.current
    val midia = midias[currentIndex]

    // Só ativa delay se não for vídeo
    LaunchedEffect(currentIndex, midia.type) {
        if (midia.type != "video") {
            val duration = midia.duration.toLongOrNull() ?: 10000
            delay(duration)
            currentIndex = (currentIndex + 1) % midias.size
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        when (midia.type) {
            "image" -> {
                val file = File(
                    Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
                    "eventos/midia_${currentIndex}.${midia.name.substringAfterLast('.')}"
                )
                if (file.exists()) {
                    Image(
                        painter = rememberAsyncImagePainter(file),
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize()
                    )
                } else {
                    Text("Imagem não encontrada")
                }
            }

            "web" -> {
                AndroidView(
                    factory = {
                        WebView(it).apply {
                            settings.javaScriptEnabled = true
                            webViewClient = WebViewClient()
                            loadUrl(midia.name)
                        }
                    },
                    modifier = Modifier.fillMaxSize()
                )
            }

            "video" -> {
                val file = File(
                    Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
                    "eventos/midia_${currentIndex}.${midia.name.substringAfterLast('.')}"
                )

                if (file.exists()) {
                    VideoPlayer(
                        videoUri = Uri.fromFile(file),
                        onVideoEnded = {
                            // Trocar mídia após vídeo terminar
                            currentIndex = (currentIndex + 1) % midias.size
                        }
                    )
                } else {
                    Text("Vídeo não encontrado")
                }
            }

            "pdf" -> {
                Text("PDF ainda não implementado")
            }

            else -> {
                Text("Tipo desconhecido: ${midia.type}")
            }
        }
    }
}*/


/*@Composable
fun MidiaPlayerScreen(midias: List<Midia>) {
    var currentIndex by remember { mutableStateOf(0) }

    val context = LocalContext.current

    LaunchedEffect(currentIndex) {
        if (midia.type != "video") {
            val duration = midia.duration.toLongOrNull() ?: 10000
            delay(duration)
            currentIndex = (currentIndex + 1) % midias.size
        }
        val duration = midias[currentIndex].duration.toLongOrNull() ?: 10000
        delay(duration)
        currentIndex = (currentIndex + 1) % midias.size
    }

    Box(modifier = Modifier.fillMaxSize()) {
        val midia = midias[currentIndex]

        when (midia.type) {
            "image" -> {
                val file = File(
                    Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
                    "eventos/midia_${currentIndex}.${midia.name.substringAfterLast('.')}"
                )
                if (file.exists()) {
                    Image(
                        painter = rememberAsyncImagePainter(file),
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize()
                    )
                } else {
                    Text("Imagem não encontrada")
                }
            }

            "web" -> {
                AndroidView(
                    factory = {
                        WebView(it).apply {
                            settings.javaScriptEnabled = true
                            webViewClient = WebViewClient()
                            loadUrl(midia.name)
                        }
                    },
                    modifier = Modifier.fillMaxSize()
                )
            }

            "pdf" -> {
                Text("Exibição de PDF ainda não implementada")
                // Aqui pode usar PdfRenderer ou biblioteca externa se necessário
            }

            "video" -> {
                val videoFile = File(
                    Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
                    "eventos/midia_${currentIndex}.${midia.name.substringAfterLast('.')}"
                )

                if (videoFile.exists()) {
                    AndroidView(
                        factory = { context ->
                            VideoView(context).apply {
                                setVideoPath(videoFile.absolutePath)
                                setOnPreparedListener { it.isLooping = false }
                                start()
                            }
                        },
                        modifier = Modifier.fillMaxSize()
                    )
                } else {
                    Text("Vídeo não encontrado: ${videoFile.name}")
                }
            }

            else -> {
                Text("Tipo de mídia desconhecido: ${midia.type}")
            }
        }
    }
}*/