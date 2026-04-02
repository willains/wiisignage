package com.example.wiisignageevento

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.wiisignageevento.navigation.Screens
import com.example.wiisignageevento.repository.EventoRepository
import com.example.wiisignageevento.screens.*
import com.example.wiisignageevento.utils.enableImmersiveMode
import com.example.wiisignageevento.viewmodel.WifiViewModel
import kotlinx.coroutines.launch
import kotlin.system.exitProcess

class MainActivity : ComponentActivity() {
    private val wifiViewModel: WifiViewModel by viewModels()
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Captura global de exceções na MainActivity
        Thread.setDefaultUncaughtExceptionHandler { thread, throwable ->
            // Log do erro (opcional)
            throwable.printStackTrace()

            // Fecha o app imediatamente
            exitProcess(0)
        }
        enableImmersiveMode(this)

        setContent {

            val isConnected by wifiViewModel.isConnected
            var currentScreen by remember { mutableStateOf(Screens.Splash) }
                if (isConnected) {


                    when (currentScreen) {
                        Screens.Splash -> SplashScreen { currentScreen = it }
                        Screens.Register -> RegisterDeviceScreen { currentScreen = Screens.Player }
                        Screens.Player -> MainScreen()
                    }
                } else {
                    WifiWaitingScreen()
                }

                WeatherFooter()

        }
    }
}

@Composable
fun PlayerPlaceholder() {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    var status by remember { mutableStateOf("Verificando...") }

    LaunchedEffect(true) {
        scope.launch {
            val repo = EventoRepository(context)
            val atualizou = repo.fetchEvento()
            status = if (atualizou) "Atualização detectada!" else "Nada novo."
        }
    }

    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(status)
    }
}


@Composable
fun WifiWaitingScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column (horizontalAlignment = Alignment.CenterHorizontally) {
            CircularProgressIndicator()
            Spacer(modifier = Modifier.height(16.dp))
            Text("Aguardando conexão Wi-Fi...", fontSize = 18.sp)
        }
    }
}