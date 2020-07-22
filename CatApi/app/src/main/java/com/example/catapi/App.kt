package com.example.catapi

import android.app.Application
import androidx.room.Room
import com.example.catapi.repository.db.CatRoomDatabase

class App: Application() {

    companion object {
        lateinit var INSTANCE: App
        lateinit var catDatabase: CatRoomDatabase
    }

    init {
        INSTANCE = this
    }

    override fun onCreate() {
        super.onCreate()

        catDatabase = Room.databaseBuilder(
            this,
            CatRoomDatabase::class.java,
            "cats_database"
        ).build()
    }
}