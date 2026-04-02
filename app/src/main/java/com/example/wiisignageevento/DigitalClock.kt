package com.example.wiisignageevento

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import org.threeten.bp.LocalTime

import org.threeten.bp.format.DateTimeFormatter

import androidx.compose.animation.core.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import com.example.wiisignageevento.utils.getNtpTime


@Composable
fun DigitalClock(
    modifier: Modifier = Modifier,
    color: Color = Color.White,
    fontSize: TextUnit = 20.sp,
    fontWeight: FontWeight = FontWeight.Bold
) {
    val timeState = remember { mutableStateOf(LocalTime.now()) }

    // Atualiza o tempo usando NTP a cada segundo
    LaunchedEffect(Unit) {
        while (true) {
            val ntpTime = withContext (Dispatchers.IO) {
                getNtpTime()
            }
            if (ntpTime != null) {
                timeState.value = ntpTime
            }
            delay(1000)
        }
    }

    val formattedTime = timeState.value.format(DateTimeFormatter.ofPattern("HH:mm:ss"))
    val hours = formattedTime.substring(0, 2)
    val minutes = formattedTime.substring(3, 5)
    //val seconds = formattedTime.substring(6, 8)

    // Função para criar o efeito flip de cada número
    @Composable
    fun FlipNumber(number: String) {
        val transitionState = remember { MutableTransitionState(false) }

        // Atualiza o estado da transição para disparar a animação
        val rotation = animateFloatAsState(
            targetValue = if (transitionState.targetState) 360f else 0f, // Flip animation
            animationSpec = tween(800, easing = LinearEasing) // Duração e suavização da animação
        )

        // Quando o número mudar, a animação de flip será disparada
        LaunchedEffect(number) {
            transitionState.targetState = !transitionState.targetState // Alterna entre os estados
        }

        // Atualiza a animação do número
        Box(
            modifier = Modifier
                .graphicsLayer(
                    rotationX = rotation.value, // Aplica a rotação no eixo X
                    transformOrigin = TransformOrigin(0.5f, 0.5f) // Centraliza a rotação
                )
                .background(Color.Black, shape = RoundedCornerShape(8.dp))
                .padding(4.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = number,
                color = color,
                fontSize = fontSize,
                fontWeight = fontWeight,
                fontFamily = FontFamily.Monospace
            )
        }
    }

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Aqui chamamos a função FlipNumber para as horas, minutos e segundos
        FlipNumber(hours)
        Text(text = ":", color = Color.Black, fontSize = fontSize, fontWeight = fontWeight)
        FlipNumber(minutes)
       // Text(text = ":", color = color, fontSize = fontSize, fontWeight = fontWeight)
       // FlipNumber(seconds)
    }
}
