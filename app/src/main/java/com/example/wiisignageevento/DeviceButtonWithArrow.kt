package com.example.wiisignageevento

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun DeviceButtonWithArrow(deviceName: String) {
    // Define a rotação da seta dependendo do nome do dispositivo
    val rotationAngle = when (deviceName) {
        "BAÍA" -> 180f // Seta para a  esquerda
        "ITAIPÚ" -> 0f //Seta para a direita

        "ICARAI" -> 180f //Seta para a esquerda
        "FLECHAS" -> 0f //Seta para a direito

        "SÃO FRANCISCO" -> 90f //Seta para a baixo
        "PIRATININGA" -> 90f // Seta para baixo

        "H REAL" -> 90f // Seta para a baixo
        "H NOBRE" -> 90f // Seta para a baixo

        "INGÁ" -> 90f // Seta para baixo
        "NITERÓI" -> 90f // Seta para baixo


        else -> 0f //Seta para a direita  como padrão
    }

    // Exibe o ícone da seta com a rotação configurada
    Icon(
        imageVector = Icons.AutoMirrored.Filled.ArrowForward, // Seta para a direita como padrão
        contentDescription = "Directional Arrow",
        tint = Color.Yellow, // Cor da seta (azul)
        modifier = Modifier.graphicsLayer(rotationZ = rotationAngle) // Ajusta a rotação da seta
            .size(200.dp) // Tamanho do ícone (ajuste conforme necessário)
    )
}