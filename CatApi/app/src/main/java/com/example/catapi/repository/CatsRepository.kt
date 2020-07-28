package com.example.catapi.repository

import com.example.catapi.model.Cat
import com.example.catapi.repository.db.DatabaseManager
import com.example.catapi.repository.db.entity.CatEntity
import com.example.catapi.repository.network.NetworkService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CatsRepository {

    private val catApi = NetworkService.getCatApi()
    private val catDao = DatabaseManager.getDatabase()?.catDao()

    suspend fun getCats(): List<Cat> {

        var cats = listOf<Cat>()

        withContext(Dispatchers.IO) {
            if (catDao != null) {
                cats = if (catDao.getAllCats().isNullOrEmpty()) {
                    loadCatsFromServer()
                    catDao.getAllCats().map { it.entityToCat() }
                } else {
                    catDao.getAllCats().map { it.entityToCat() }
                }
            }
        }
        return cats
    }

    suspend fun loadCatsFromServer() {
        withContext(Dispatchers.IO) {
            val newCats = catApi.getCats()
            catDao?.addCats(newCats.map { CatEntity(it) })
        }
    }
}
