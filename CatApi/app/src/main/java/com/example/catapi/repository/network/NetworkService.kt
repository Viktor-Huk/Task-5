package com.example.catapi.repository.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NetworkService {

    private val client = OkHttpClient
        .Builder()
        .build()

    private val catApi = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl("https://api.thecatapi.com")
        .client(client)
        .build()
        .create(CatApi::class.java)

    fun getCatApi(): CatApi = catApi
}