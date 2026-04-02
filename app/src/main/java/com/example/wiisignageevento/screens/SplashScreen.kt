package com.example.wiisignageevento.screens


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.*
import com.example.wiisignageevento.datastore.*
import com.example.wiisignageevento.navigation.Screens
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first

@Composable
fun SplashScreen(onNavigate: (Screens) -> Unit) {
    val context = LocalContext.current

    LaunchedEffect(true) {
        delay(2000)

        val prefs = context.dataStore.data.first()
        val uuid = prefs[PreferencesKeys.DEVICE_UUID]
        val name = prefs[PreferencesKeys.DEVICE_NAME]

        if (uuid.isNullOrEmpty() || name.isNullOrEmpty()) {
            onNavigate(Screens.Register)
        } else {
            onNavigate(Screens.Player)
        }
    }

    Box(
        modifier = Modifier.fillMaxSize().background(Color.Black),
        contentAlignment = Alignment.Center
    ) {
        Text("WiiSignagePlayer", color = Color.White, fontSize = 32.sp)
    }
}
