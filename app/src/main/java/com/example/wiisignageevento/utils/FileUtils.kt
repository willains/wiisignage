package com.example.wiisignageevento.utils

import android.app.Activity
import android.content.pm.ActivityInfo

import android.util.Log

import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.example.wiisignageevento.constants.Constants
import java.io.File
import org.apache.commons.net.ntp.NTPUDPClient
import java.net.InetAddress


import org.threeten.bp.Instant
import org.threeten.bp.ZoneId
import org.threeten.bp.LocalTime


fun limparPastaEventos() {
    val eventosDir = File(Constants.EVENT_FILES_DIR,  Constants.SUB_DIRECTORY)
    //  Sempre limpar tudo da pasta eventos
    if (eventosDir.exists()) {
        eventosDir.listFiles()?.forEach { it.delete() }
        eventosDir.mkdirs()
        Log.d("DADOS", "PASTA CRIADA? $eventosDir")
    } else {
        eventosDir.mkdirs()
    }
}


fun setOrientation(activity: Activity, isPortrait: Boolean) {
    activity.requestedOrientation = if (isPortrait) {
        ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
    } else {
        ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
    }
}

fun enableImmersiveMode(activity: Activity) {
    val window = activity.window
    WindowCompat.setDecorFitsSystemWindows(window, false)

    val controller = WindowCompat.getInsetsController(window, window.decorView)
    controller.systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
    controller.hide(WindowInsetsCompat.Type.systemBars())
}


/*@RequiresApi(Build.VERSION_CODES.O)
fun getNtpTime(): LocalTime? {
    return try {
        val client = NTPUDPClient()
        client.defaultTimeout = 3000
        val address = InetAddress.getByName("200.160.7.186")
        val info = client.getTime(address)
        val time = info.message.transmitTimeStamp.time

        // Definindo o fuso explicitamente
        val zoneId = ZoneId.of("America/Sao_Paulo")

        Instant.ofEpochMilli(time)
            .atZone(zoneId)
            .toLocalTime()
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}*/



fun getNtpTime(): LocalTime? {
    return try {
        val client = NTPUDPClient()
        client.defaultTimeout = 3000
        val address = InetAddress.getByName("200.160.7.186") // Servidor NTP
        val info = client.getTime(address)
        val time = info.message.transmitTimeStamp.time

        val zoneId = ZoneId.of("America/Sao_Paulo")

        Instant.ofEpochMilli(time)
            .atZone(zoneId)
            .toLocalTime()
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}

