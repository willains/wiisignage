package com.example.wiisignageevento.network


import com.example.wiisignageevento.constants.Constants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
   // private const val BASE_URL = "http://10.20.42.60/"

    val api: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(Constants.IP_SERVER_API)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}