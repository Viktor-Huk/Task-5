package com.example.catapi.network

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NetworkService {

    private const val HOST = "https://api.thecatapi.com/v1/images/search"

    private val client = OkHttpClient
        .Builder()
        .build()

    private val catApi = Retrofit.Builder()
        .baseUrl(HOST)
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()
        .create(CatApi::class.java)

    fun getCatApi(): CatApi {
        return catApi
    }
}