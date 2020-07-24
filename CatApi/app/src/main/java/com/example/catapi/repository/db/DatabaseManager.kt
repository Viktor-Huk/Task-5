package com.example.catapi.repository.db

import androidx.room.Room
import com.example.catapi.App

object DatabaseManager {

    private var INSTANCE: CatRoomDatabase? = null

    fun getDatabase(): CatRoomDatabase? {

        if (INSTANCE == null) {

            val instance = Room.databaseBuilder(
                App.INSTANCE,
                CatRoomDatabase::class.java,
                "cats_database"
            ).build()

            INSTANCE = instance
        }

        return INSTANCE
    }
}