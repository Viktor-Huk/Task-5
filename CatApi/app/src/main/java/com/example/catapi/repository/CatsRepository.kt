package com.example.catapi.repository

import android.util.Log
import com.example.catapi.App
import com.example.catapi.model.Cat
import com.example.catapi.repository.db.entity.CatEntity
import com.example.catapi.repository.network.NetworkService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CatsRepository {

    private val catApi = NetworkService.getCatApi()
    private val catDao = App.catDatabase.catDao()

    private var _cats = listOf<Cat>()

    suspend fun getCats(): List<Cat> {

        withContext(Dispatchers.IO) {

            _cats = if (catDao.getAllCats().isEmpty()) {
                loadCatsFromServer()
                catDao.getAllCats().map { it.entityToCat() }
            } else {
                catDao.getAllCats().map { it.entityToCat() }
            }
        }

        Log.i("tag", "REPOSITORY: ${_cats}")
        return _cats
    }

    suspend fun loadCatsFromServer() {
        withContext(Dispatchers.IO) {
            val newCats = catApi.getCats()
            catDao.addCats(newCats.map { CatEntity.catToEntity(it) })
        }
    }
}