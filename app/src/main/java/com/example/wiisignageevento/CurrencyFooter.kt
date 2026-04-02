package com.example.wiisignageevento

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.wiisignageevento.network.CurrencyService
// ESTE É O IMPORT CRÍTICO PARA FUNCIONAR COM "by"
import androidx.compose.runtime.getValue
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material3.Icon
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.rememberCoroutineScope

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CurrencyFooter() {
    var rate by remember { mutableStateOf<Double?>(null) }
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()


    Box(modifier = Modifier.fillMaxSize()) {
        // Seu conteúdo da tela aqui

        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }

/*    LaunchedEffect (Unit) {
        try {
            rate = CurrencyService.getDollarToReal()
        } catch (e: Exception) {
            e.printStackTrace()
            Log.d("DOLAR", "DOLAR: ${e.message}")
            scope.launch {
                snackbarHostState.showSnackbar(
                    message = "Erro ao obter cotação: ${e.message ?: "Erro desconhecido"}",
                    withDismissAction = true
                )
            }
        }
    }*/

    LaunchedEffect(Unit) {
        while (true) {
            try {
                rate = CurrencyService.getDollarToReal()
            } catch (e: Exception) {
                e.printStackTrace()
            }
            delay(12 * 60 * 60 * 1000L)// 12 horas
        }
    }

    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(
            imageVector = Icons.Filled.AttachMoney,
            contentDescription = "Ícone de Dinheiro"
        )

        Spacer(modifier = Modifier.width(6.dp)) // Pequeno espaço entre ícone e texto

        Text(
            text = rate?.let { "USD → BRL: R$ %.2f".format(it) } ?: "Carregando cotação...",
            style = MaterialTheme.typography.bodyMedium
        )
    }



}
