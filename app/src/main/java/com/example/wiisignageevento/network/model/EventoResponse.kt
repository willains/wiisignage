package com.example.wiisignageevento.network.model

data class EventoResponse(
    val data: EventoData
)

data class EventoData(
    val uuid: String,
    val name: String,
    val empresa: String,
    val logo: String,
    val date_begin: String,
    val time_begin: String,
    val date_end: String,
    val time_end: String,
    val updated: String,
    val isMidia: String,
    val midias: List<Midia>,
    val devices: List<Dispositivo>
)

data class Midia(
    val name: String,
    val duration: String,
    val type: String
)

data class Dispositivo(
    val uuid: String,
    val name: String,
    val description: String
)
