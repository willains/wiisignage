package com.example.wiisignageevento.screens


import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import com.example.wiisignageevento.network.model.EventoResponse

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TelaDeExibicaoEvento(evento: EventoResponse) {
    val midias = evento.data.midias

    if (midias.isEmpty()) {
        TelaSemMidias(
            nomeEvento = evento.data.name,
            nomeEmpresa = evento.data.empresa,
            logoUrl = evento.data.logo, // se tiver
            deviceName = evento.data.devices[0].name
        )
    } else {
        MidiaPlayerScreen(midias)
    }
}

