package com.example.wiisignageevento.network.model

import kotlinx.serialization.Serializable

@Serializable
data class ExchangeRateResponse(
    val success: Boolean,
    val info: Info
)

@Serializable
data class Info(
    val quote: Double
)

