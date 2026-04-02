package com.example.wiisignageevento.constants

import android.os.Environment
import java.io.File

object Constants {

    const val IP_SERVER_API = "http://10.20.42.60/"
    val EVENT_FILES_DIR: File? = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
    const val SUB_DIRECTORY = "EVENTOS"
    //val eventosDir = File(EVENT_FILES_DIR), SUB_DIRECTORY)
}