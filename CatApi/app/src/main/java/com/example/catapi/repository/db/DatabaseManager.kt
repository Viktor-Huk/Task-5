package com.example.catapi.repository.db

import android.content.Context
import androidx.room.Room

object DatabaseManager {

    private var INSTANCE: CatRoomDatabase? = null

    fun getInstance(context: Context): CatRoomDatabase? {

        if (INSTANCE == null) {

            val instance = Room.databaseBuilder(
                context.applicationContext,
                CatRoomDatabase::class.java,
                "cats_database"
            ).build()

            INSTANCE = instance
        }

        return INSTANCE
    }
}