package com.example.catapi.repository

import android.app.Application
import com.example.catapi.repository.network.NetworkService

class CatsRepository(application: Application) {

    private val catApi = NetworkService.getCatApi()
    //private val catDao = DatabaseManager.getInstance(application)?.catDao()

    suspend fun refresh() {
    }
}