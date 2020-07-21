package com.example.catapi.repository

import android.util.Log
import com.example.catapi.App
import com.example.catapi.model.Cat
import com.example.catapi.repository.db.DatabaseManager
import com.example.catapi.repository.db.entity.CatEntity
import com.example.catapi.repository.network.NetworkService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CatsRepository {

    private val catApi = NetworkService.getCatApi()
    private val catDao = DatabaseManager.getInstance(App.INSTANCE)?.catDao()

    private val _cats = mutableListOf<Cat>()

    init {
        Log.i("tag", "repo")
    }

    suspend fun getCats(): MutableList<Cat> {

        if (_cats.isEmpty()) {
            withContext(Dispatchers.IO) {
                loadCatsFromServer()
            }
        }
        return _cats
    }

    private suspend fun loadCatsFromServer() {
        withContext(Dispatchers.IO) {
            val listOfCats = catApi.getCats()
            catDao?.addCats(listOfCats.map { CatEntity.catToEntity(it) })
            _cats.addAll(catDao?.getAllCats()!!.map { it.entityToCat() })
        }
    }
}