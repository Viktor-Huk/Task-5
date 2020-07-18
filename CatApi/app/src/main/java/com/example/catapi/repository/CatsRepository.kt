package com.example.catapi.repository

import android.app.Application
import com.example.catapi.db.CatRoomDatabase
import com.example.catapi.model.Cat
import com.example.catapi.network.NetworkService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CatsRepository(application: Application) {

    private val catApi = NetworkService.getCatApi()
    private val catDao = CatRoomDatabase.getDatabase(application)?.catDao

    val data = catDao?.getAllCats()

    suspend fun refresh(): List<Cat> {
        return withContext(Dispatchers.IO) {

            //val cats =
            catApi.getCats()
            //catDao?.addCats(cats)
        }
    }
}