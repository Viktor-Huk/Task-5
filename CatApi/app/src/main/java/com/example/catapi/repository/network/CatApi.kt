package com.example.catapi.repository.network

import com.example.catapi.model.Cat
import retrofit2.http.GET

interface CatApi {

    @GET("/v1/images/search?limit=10&page=10&order=DESC&api-key=6651ce0b-b90e-48ea-88ef-69976f1b9fba")
    suspend fun getCats(): List<Cat>
}