package com.example.wiisignageevento

import android.content.Context

import android.os.Environment

import android.util.Log
import com.example.wiisignageevento.constants.Constants

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.File




object MidiaDownloader {

    suspend fun baixarArquivo(context: Context, url: String, nomeArquivo: String): File? {
        return withContext(Dispatchers.IO) {
            try {
                val eventosDir = File(Constants.EVENT_FILES_DIR,  Constants.SUB_DIRECTORY)



                val file = File(eventosDir, nomeArquivo)



                // ✅ Agora criar e baixar o conteúdo
                val request = Request.Builder().url(url).build()
                val client = OkHttpClient()
                val response = client.newCall(request).execute()

                if (response.isSuccessful) {
                    response.body?.byteStream()?.use { input ->
                        file.outputStream().use { output ->
                            input.copyTo(output)
                        }
                    }
                    Log.d("MIDIA", "Download concluído com sucesso: ${file.name}")
                    return@withContext file
                } else {
                    Log.e("MIDIA", "Falha no download. Código HTTP: ${response.code}")
                    return@withContext null
                }

            } catch (e: Exception) {
                Log.e("MIDIA", "Erro durante download: ${e.message}")
                e.printStackTrace()
                return@withContext null
            }
        }
    }
}





