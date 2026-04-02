package com.example.wiisignageevento.repository

import android.content.Context
import android.util.Log
import com.example.wiisignageevento.datastore.DataStoreManager
import com.example.wiisignageevento.network.RetrofitClient



class EventoRepository(private val context: Context) {

    suspend fun fetchEvento(): Boolean {
        val dataStoreManager = DataStoreManager(context)


        val deviceInfo = dataStoreManager.getDeviceInfo()
        val savedUuid = deviceInfo.first // UUID
        val savedDevice = deviceInfo.second // nome do dispositivo
        val savedUpdated = deviceInfo.third
        Log.d("DADOS", "UUID : $savedDevice $savedUuid" )
        val response = RetrofitClient.api.getEvento(savedDevice, savedUuid)


        Log.d("DADOS", "API RESPONSE: $response" )
        val newUpdated = response.data.updated

        if (newUpdated != savedUpdated) {
            // Salvar o novo updated
            dataStoreManager.saveUpdated(newUpdated)

            return true // Houve atualização
        }

        return false // Já está atualizado
    }
}