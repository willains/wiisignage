package com.example.wiisignageevento.network

import android.icu.text.SimpleDateFormat
import android.os.Build
import androidx.annotation.RequiresApi
import com.example.wiisignageevento.network.model.ExchangeRateResponse
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.engine.okhttp.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*

import kotlinx.serialization.json.Json
import java.time.LocalDate
import java.util.Date
import java.util.Locale

object CurrencyService {
    private const val API_KEY = "37c375dcfb99d95e4a98988903bf8c9a"

    private val client = HttpClient(OkHttp) {
        install(ContentNegotiation) {
            json(Json { ignoreUnknownKeys = true })
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun getDollarToReal(): Double {
        val today = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())//LocalDate.now().toString() // yyyy-MM-dd
        val response: ExchangeRateResponse = client.get("https://api.exchangerate.host/convert") {
            parameter("access_key", API_KEY)
            parameter("from", "USD")
            parameter("to", "BRL")
            parameter("amount", 1) // usamos 1 para obter o valor unitário
            parameter("date", today)
        }.body()

        return response.info.quote
    }
}
