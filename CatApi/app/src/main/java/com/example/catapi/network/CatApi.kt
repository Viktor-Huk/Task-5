package com.example.catapi.network

import com.example.catapi.model.Cat
import kotlinx.coroutines.Deferred
import retrofit2.http.GET

private const val ENDPOINT = "?limit=10&page=100&order=DESC"

interface CatApi {

    @GET(ENDPOINT)
    suspend fun getCats(): List<Cat>
}