package com.example.wiisignageevento.utils

import android.content.Context

import com.example.wiisignageevento.MidiaDownloader
import com.example.wiisignageevento.network.model.EventoResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll

import kotlinx.coroutines.withContext

import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

object MidiaManager {


    private val downloadMutex = Mutex()

    suspend fun verificarEAtualizarMidias(context: Context, evento: EventoResponse): Boolean {
        return withContext(Dispatchers.IO) {
            downloadMutex.withLock {
                limparPastaEventos()
                    val resultados = evento.data.midias.mapIndexed { index, midia ->
                        val nomeArquivo = "midia_$index.${obterExtensao(midia.name)}"
                        async {
                            MidiaDownloader.baixarArquivo(context, midia.name, nomeArquivo)
                        }
                    }

                    // Aguarda todos os downloads
                    val arquivosBaixados = resultados.awaitAll()

                    // Verifica se TODOS foram baixados com sucesso
                    val todosBaixaram = arquivosBaixados.all { it != null && it.exists() }

                    if (todosBaixaram) {

                        return@withContext true
                    }


                return@withContext false


            }
        }
    }


    /*suspend fun verificarEAtualizarMidias(context: Context, evento: EventoResponse): Boolean {
        return withContext(Dispatchers.IO) {
            val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
            val updatedLocal = prefs.getString(KEY_UPDATED, null)
            val updatedApi = evento.data.updated

            if (updatedApi != updatedLocal) {
                // Baixar mídias
                evento.data.midias.forEachIndexed { index, midia ->
                    val nomeArquivo = "midia_$index.${obterExtensao(midia.name)}"
                    MidiaDownloader.baixarArquivo(context, midia.name, nomeArquivo)
                }

                // Salva o novo updated
                prefs.edit().putString(KEY_UPDATED, updatedApi).apply()
                return@withContext true
            }

            return@withContext false
        }
    }*/

    private fun obterExtensao(url: String): String {
        val extensao = url.substringAfterLast('.', "").lowercase()
        val extensoesValidas = listOf("jpg", "jpeg", "png", "gif", "webp", "bmp", "svg", "mp4", "avi", "mov", "mkv", "webm", "pdf")

        return if (extensoesValidas.contains(extensao)) extensao else "jpg"
    }
}