package com.example.wiisignageevento

import com.example.wiisignageevento.viewmodel.WeatherResponse
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.okhttp.*
import io.ktor.client.request.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
object WeatherService {
    private const val API_KEY = "5d37a01a8e2b8ffd621405e90d5c45bf"

    private val client = HttpClient(OkHttp) {
        install(ContentNegotiation) {
            json(Json { ignoreUnknownKeys = true })
        }
    }

    suspend fun getWeather(city: String): WeatherResponse {
        return client.get("https://api.openweathermap.org/data/2.5/weather") {
            parameter("q", city)
            parameter("appid", API_KEY)
            parameter("units", "metric")
            parameter("lang", "pt_br")
        }.body()
    }
}
