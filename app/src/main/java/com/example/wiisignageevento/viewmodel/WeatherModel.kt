package com.example.wiisignageevento.viewmodel

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WeatherResponse(
    val name: String,
    val main: Main,
    val weather: List<Weather>
)

@Serializable
data class Main(
    val temp: Double
)

@Serializable
data class Weather(
    val description: String,
    val icon: String
)

