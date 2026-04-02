package com.example.wiisignageevento.datastore

import android.content.Context
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

val Context.dataStore by preferencesDataStore(name = "settings")

object PreferencesKeys {
    val DEVICE_UUID = stringPreferencesKey("device_uuid")
    val DEVICE_NAME = stringPreferencesKey("device_name")
    val EVENT_UPDATED = stringPreferencesKey("event_updated") // usaremos na Parte 2
    val IS_PORTRAIT = booleanPreferencesKey("is_portrait")
    val SAVED_DIRECTORY_URI = stringPreferencesKey("saved_directory_uri")

}

class DataStoreManager(context: Context) {

    private val dataStore = context.dataStore

    // Função para obter o UUID e o nome do dispositivo
    suspend fun getDeviceInfo(): Triple<String, String, String> {
        val preferences = dataStore.data.first()
        val uuid = preferences[PreferencesKeys.DEVICE_UUID] ?: ""
        val deviceName = preferences[PreferencesKeys.DEVICE_NAME] ?: ""
        val eventUpdated = preferences[PreferencesKeys.EVENT_UPDATED] ?: ""

        return Triple(uuid, deviceName, eventUpdated)
    }
  /*  suspend fun getDeviceInfo(): Pair<String, String> {
        val preferences = dataStore.data.first()
        val uuid = preferences[PreferencesKeys.DEVICE_UUID] ?: ""
        val deviceName = preferences[PreferencesKeys.DEVICE_NAME] ?: ""

        return Pair(uuid, deviceName)
    }*/

    // Função para salvar as informações do dispositivo no DataStore
    suspend fun saveDeviceInfo(uuid: String, deviceName: String) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.DEVICE_UUID] = uuid
            preferences[PreferencesKeys.DEVICE_NAME] = deviceName
        }
    }

    // Função para salvar as informações do dispositivo no DataStore
    suspend fun saveUpdated(savedUpdated: String) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.EVENT_UPDATED] = savedUpdated

        }
    }

    suspend fun saveOrientation(context: Context, isPortrait: Boolean) {
       dataStore.edit { preferences ->
            preferences[PreferencesKeys.IS_PORTRAIT] = isPortrait
        }
    }

    suspend fun getSavedOrientation(context: Context): Boolean {
        val preferences = context.dataStore.data.first()
        return preferences[PreferencesKeys.IS_PORTRAIT] != false // default: portrait
    }

    suspend fun saveDirectoryUri(uri: String) {
       dataStore.edit { preferences ->
            preferences[PreferencesKeys.SAVED_DIRECTORY_URI] = uri
        }
    }

    val directoryUri: Flow<String?> = context.dataStore.data
        .map { preferences -> preferences[PreferencesKeys.SAVED_DIRECTORY_URI] }





}