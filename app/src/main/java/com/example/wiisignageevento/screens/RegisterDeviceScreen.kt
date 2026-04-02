package com.example.wiisignageevento.screens

import android.content.Intent
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import android.os.Build

import com.example.wiisignageevento.datastore.*
import kotlinx.coroutines.launch

@Composable
fun RegisterDeviceScreen(onRegistered: () -> Unit) {
    val context = LocalContext.current
     val dataStoreManager = DataStoreManager(context)
    var uuid by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }
    var isPortrait by remember { mutableStateOf(true) }
   // var selectedDirectoryUri by remember { mutableStateOf<Uri?>(null) }
    val scope = rememberCoroutineScope()


    // SAF para Android 11+ (API 30+)
    val modernFilePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocument(),
        onResult = { uri ->
            uri?.let {
                try {
                    context.contentResolver.openInputStream(it)?.bufferedReader()?.useLines { lines ->
                        val allLines = lines.toList()
                        val uuidLine = allLines.find { it.startsWith("uuid=") }?.substringAfter("=")?.trim()
                        val nameLine = allLines.find { it.startsWith("name=") }?.substringAfter("=")?.trim()
                        uuidLine?.let { uuid = it }
                        nameLine?.let { name = it }
                    }
                } catch (e: Exception) {
                    Log.e("IMPORTAR", "Erro SAF: ${e.message}")
                }
            }
        }
    )

    // Compatível com Android 10 e abaixo
    val legacyFilePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        val uri = result.data?.data
        uri?.let {
            try {
                context.contentResolver.openInputStream(it)?.bufferedReader()?.useLines { lines ->
                    val allLines = lines.toList()
                    val uuidLine = allLines.find { it.startsWith("uuid=") }?.substringAfter("=")?.trim()
                    val nameLine = allLines.find { it.startsWith("name=") }?.substringAfter("=")?.trim()
                    uuidLine?.let { uuid = it }
                    nameLine?.let { name = it }
                }
            } catch (e: Exception) {
                Log.e("IMPORTAR", "Erro Legacy: ${e.message}")
            }
        }
    }

    //  Gerenciador para abrir seletor de pasta
  /*  val directoryPickerLauncher = rememberLauncherForActivityResult (
        contract = ActivityResultContracts.OpenDocumentTree()
    ) { uri ->
        selectedDirectoryUri = uri
    }*/

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text("Cadastro de Dispositivo", style = MaterialTheme.typography.headlineSmall)
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = uuid, onValueChange = { uuid = it }, label = { Text("UUID EMPRESA") })
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = name, onValueChange = { name = it }, label = { Text("UUID DEVICE") })
        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                modernFilePickerLauncher.launch(arrayOf("*/*"))
            } else {
                val intent = Intent(Intent.ACTION_GET_CONTENT).apply {
                    type = "text/plain"
                    addCategory(Intent.CATEGORY_OPENABLE)
                }
                legacyFilePickerLauncher.launch(intent)
            }
        }) {
            Text("Importar registro.txt")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text("Orientação: ${if (isPortrait) "Retrato" else "Paisagem"}")
            Switch(
                checked = isPortrait,
                onCheckedChange = { isPortrait = it }
            )
        }

        /*Spacer(modifier = Modifier.height(16.dp))
        // Botão para escolher pasta
        Button(onClick = {
            directoryPickerLauncher.launch(null)
        }) {
            Text("Escolher Pasta de Salvamento")
        }

        selectedDirectoryUri?.let { uri ->
            Spacer(modifier = Modifier.height(8.dp))
            Text("Pasta selecionada: ${uri.path}", fontSize = 12.sp, color = Color.Gray)
        }*/

        Spacer(modifier = Modifier.height(24.dp))

        Button(onClick = {
            scope.launch {
                dataStoreManager.saveDeviceInfo(uuid,name)
                dataStoreManager.saveOrientation(context, isPortrait)
                //selectedDirectoryUri?.let { dataStoreManager.saveDirectoryUri(it.toString()) }
                onRegistered()
            }
        }) {
            Text("Salvar")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun Preview() {
    RegisterDeviceScreen {  }
}
