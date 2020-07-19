package com.example.catapi.repository

import android.app.Application
import com.example.catapi.db.DatabaseManager
import com.example.catapi.model.Cat
import com.example.catapi.network.NetworkService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CatsRepository(application: Application) {

    private val catApi = NetworkService.getCatApi()
    //private val catDao = DatabaseManager.getInstance(application)?.catDao()


    suspend fun getCats(): List<Cat> {
        return withContext(Dispatchers.IO) {
            catApi.getCats()
        }
    }
}