package com.example.wiisignageevento.network

import com.example.wiisignageevento.network.model.EventoResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST


interface ApiService {

    @FormUrlEncoded
    @POST("api/v1/eventos")
    suspend fun getEvento(
        @Field("devices") device: String,
        @Field("uuid") uuid: String
    ): EventoResponse
}