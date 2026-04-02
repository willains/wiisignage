    package com.example.wiisignageevento.viewmodel


    import android.app.Application
    import android.util.Log
    import androidx.lifecycle.*
    import com.example.wiisignageevento.datastore.DataStoreManager
    import com.example.wiisignageevento.network.RetrofitClient
    import com.example.wiisignageevento.network.model.EventoResponse
    import com.example.wiisignageevento.utils.MidiaManager

    import kotlinx.coroutines.flow.MutableStateFlow
    import kotlinx.coroutines.flow.StateFlow
    import kotlinx.coroutines.launch


    class MainViewModel(application: Application) : AndroidViewModel(application) {
        private val dataStoreManager = DataStoreManager(application)
        private val _isBaixandoMidias = MutableStateFlow(false)
        val isBaixandoMidias: StateFlow<Boolean> get() = _isBaixandoMidias

        private val _eventoAtual = MutableStateFlow<EventoResponse?>(null)
        val eventoAtual: StateFlow<EventoResponse?> get() = _eventoAtual

        private val _reiniciarPlayer = MutableStateFlow(false)
        val reiniciarPlayer: StateFlow<Boolean> get() = _reiniciarPlayer


        private val _uuid = MutableStateFlow("")
        val uuid: StateFlow<String> get() = _uuid

        private val _deviceName = MutableStateFlow("")
        val deviceName: StateFlow<String> get() = _deviceName


        init {
            // Carrega os dados do DataStore
            viewModelScope.launch {
                val deviceInfo = dataStoreManager.getDeviceInfo()
                _uuid.value = deviceInfo.first // UUID
                _deviceName.value = deviceInfo.second // nome do dispositivo


            }
        }

        fun carregarEvento(uuid: String, device: String) {
            viewModelScope.launch {
                try {
                    _isBaixandoMidias.value = true // <<< Início do processo
                    val response = RetrofitClient.api.getEvento(device, uuid)
                    MidiaManager.verificarEAtualizarMidias(getApplication(), response)
                    _eventoAtual.value = response // SEMPRE atualiza
                    _reiniciarPlayer.value = true
                } catch (e: Exception) {
                // Captura qualquer erro durante a requisição ou processamento
                Log.e("DADOS", "Erro ao carregar evento: ${e.message}")
                e.printStackTrace()
            } finally {
                _isBaixandoMidias.value = false // Fim do processo, desativa o estado de "baixando"
            }
            }
        }

        fun resetarReinicioPlayer() {
            _reiniciarPlayer.value = false
        }



    }