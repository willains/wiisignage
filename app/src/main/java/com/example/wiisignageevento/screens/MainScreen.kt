package com.example.wiisignageevento.screens

import android.app.Activity
import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.wiisignageevento.datastore.DataStoreManager
import com.example.wiisignageevento.viewmodel.MainViewModel
import com.example.wiisignageevento.repository.EventoRepository
import com.example.wiisignageevento.utils.setOrientation
import kotlinx.coroutines.delay

@Composable
fun MainScreen(
    viewModel: MainViewModel = viewModel()
) {
    val context = LocalContext.current

    val activity = context as Activity
    val dataStoreManager = DataStoreManager(context)
    val isBaixandoMidias by viewModel.isBaixandoMidias.collectAsState()
    val evento by viewModel.eventoAtual.collectAsState()
    val reiniciarPlayer by viewModel.reiniciarPlayer.collectAsState()

    val snackbarHostState = remember { SnackbarHostState() }

    Box(modifier = Modifier.fillMaxSize()) {
        // Seu conteúdo da tela aqui

        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }

    LaunchedEffect(Unit) {
        val savedPortrait = dataStoreManager.getSavedOrientation(context)
        setOrientation(activity, savedPortrait)
    }


    //  Inicia verificação periódica apenas uma vez
    LaunchedEffect(Unit) {
  viewModel.carregarEvento(viewModel.uuid.value, viewModel.deviceName.value)
   }

    //  Inicia verificação periódica a cada 1 minuto
    LaunchedEffect(Unit) {

            while (true) {
                val repo = EventoRepository(context)
                val actualization = repo.fetchEvento()
                Log.d("DADOS", "ATUALIZOU : $actualization" )
               if (actualization){
                   viewModel.carregarEvento(viewModel.uuid.value, viewModel.deviceName.value)
                   snackbarHostState.showSnackbar(
                       message = "Evento atualizado com sucesso!",
                       duration = SnackbarDuration.Short
                   )
               }
                delay(60_000L) // 60 segundos

        }
    }



    // Reiniciar player quando necessário
    LaunchedEffect(reiniciarPlayer) {
        if (reiniciarPlayer) {
            // Você pode fazer qualquer lógica de reinício aqui
            // Ex: resetar índices, recompor player, etc.
            viewModel.resetarReinicioPlayer()
        }
    }
    if (isBaixandoMidias) {
        BaixandoMidiasScreen()
    } else {
        evento?.let {
            TelaDeExibicaoEvento(it)
        } ?: Text("Erro ao carregar evento ou nenhum disponível.")
    }


}